package com.liangmayong.apkbox.core.resources;

import com.liangmayong.apkbox.utils.ApkLogger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by liangmayong on 2016/9/18.
 */
public final class ApkNative {

    private ApkNative() {
    }

    /**
     * getNativePath
     *
     * @param apkPath appPath
     * @return library Path
     */
    public static String getNativePath(String apkPath) {
        File apkFile = new File(apkPath);
        if (!apkFile.exists()) {
            return "";
        }
        try {
            String libraryDir = apkFile.getParent() + "/libs/";
            File file = new File(libraryDir);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file.getPath();
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * copyNativeLibrary
     *
     * @param apkPath apkPath
     * @return libs path
     */
    public static String copyNativeLibrary(String apkPath) {
        return copyNativeLibrary(apkPath, false);
    }

    /**
     * copyNativeLibrary
     *
     * @param apkPath apkPath
     * @param force   force
     * @return libs path
     */
    public static String copyNativeLibrary(String apkPath, boolean force) {
        ApkLogger.get().debug("=======> copyNativeLibrary", null);
        long startTime = System.currentTimeMillis();
        if (apkPath != null && !"".equals(apkPath) && (new File(apkPath)).exists()) {
            String targetDir = getNativePath(apkPath);
            File file = new File(targetDir, "");
            if (force) {
                deleteFile(file);
            }
            if (!file.exists()) {
                file.mkdirs();
            }
            unzipLibFiles(apkPath, ApkABI.getWithABIs(), getLibFiles(apkPath, targetDir));
        }
        long endTime = System.currentTimeMillis();
        ApkLogger.get().debug("Copyed native *.so library " + apkPath + " libs(" + (endTime - startTime) + "ms)", null);
        return getNativePath(apkPath);
    }

    private static void deleteFile(File file) {
        if (file.exists() == false) {
            return;
        } else {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }
                for (File f : childFile) {
                    deleteFile(f);
                }
                file.delete();
            }
        }
    }

    /**
     * getLibFiles
     *
     * @param zipFile   zipFile
     * @param targetDir targetDir
     */
    private final static Map<String, Map<String, String>> getLibFiles(String zipFile, String targetDir) {
        Map<String, Map<String, String>> entryFiles = new HashMap<String, Map<String, String>>();
        try {
            ZipFile zf = new ZipFile(zipFile);
            Enumeration<?> entries = zf.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                if (entry.getName().startsWith("lib/")) {
                    String[] entrys = entry.getName().split("/");
                    if (entrys.length >= 3) {
                        String abi = entrys[1];
                        String targetEntry = entrys[entrys.length - 1];
                        String libraryEntry = targetDir + "/" + targetEntry;
                        if (entryFiles.containsKey(abi)) {
                            entryFiles.get(abi).put(entry.getName(), libraryEntry);
                        } else {
                            Map<String, String> libs = new HashMap<String, String>();
                            libs.put(entry.getName(), libraryEntry);
                            entryFiles.put(abi, libs);
                        }
                    }
                }
            }
            zf.close();
        } catch (Exception e) {
        }
        return entryFiles;
    }


    /**
     * unzipLibFiles
     *
     * @param zipFile  zipFile
     * @param abis     abis
     * @param libFiles libFiles
     */
    private final static void unzipLibFiles(String zipFile, String[] abis, Map<String, Map<String, String>> libFiles) {
        for (int i = 0; i < abis.length; i++) {
            Map<String, String> files = libFiles.get(abis[i]);
            if (files != null && !files.isEmpty()) {
                for (Map.Entry<String, String> entry : files.entrySet()) {
                    unzipSingleFile(zipFile, entry.getKey(), entry.getValue());
                }
            }
        }
    }

    /**
     * unzipSingleFile
     *
     * @param zipFile    zipFile
     * @param entryFile  entryFile
     * @param targetFile targetFile
     */
    private final static void unzipSingleFile(String zipFile, String entryFile, String targetFile) {
        File soFile = new File(targetFile);
        if (!soFile.exists()) {
            try {
                realUnzipSingleFile(zipFile, entryFile, targetFile);
            } catch (Exception e) {
            }
        }
    }

    /**
     * realUnzipSingleFile
     *
     * @param zipFile    zipFile
     * @param entryPath  entryPath
     * @param targetPath targetPath
     * @throws IOException IOException
     */
    public static void realUnzipSingleFile(String zipFile, String entryPath, String targetPath) throws IOException {
        long startTime = System.currentTimeMillis();
        try {
            ZipFile zf = new ZipFile(zipFile);
            Enumeration<?> entries = zf.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                if (entry.getName().equals(entryPath)) {
                    try {
                        InputStream in = zf.getInputStream(entry);
                        File targetFile = new File(targetPath);
                        if (!targetFile.exists()) {
                            File fileParentDir = targetFile.getParentFile();
                            if (!fileParentDir.exists()) {
                                fileParentDir.mkdirs();
                            }
                            targetFile.createNewFile();
                        }
                        OutputStream out = new FileOutputStream(targetFile);
                        byte buffer[] = new byte[4096];
                        int realLength;
                        while ((realLength = in.read(buffer)) > 0) {
                            out.write(buffer, 0, realLength);
                        }
                        in.close();
                        out.close();
                    } catch (Exception e) {
                    }
                    continue;
                }
            }
            zf.close();
        } catch (Exception e) {
        }
        long endTime = System.currentTimeMillis();
        ApkLogger.get().debug("=======> Unzip lib " + entryPath + " >> " + targetPath + " (" + (endTime - startTime) + "ms)", null);
    }

}
