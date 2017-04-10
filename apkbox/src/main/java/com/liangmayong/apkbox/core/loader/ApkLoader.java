package com.liangmayong.apkbox.core.loader;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.os.Build;

import com.liangmayong.apkbox.core.ApkLoaded;
import com.liangmayong.apkbox.core.context.ApkContext;
import com.liangmayong.apkbox.core.context.ApkContextModifier;
import com.liangmayong.apkbox.core.resources.ApkNative;
import com.liangmayong.apkbox.core.resources.ApkResources;
import com.liangmayong.apkbox.core.resources.ApkSignture;
import com.liangmayong.apkbox.reflect.ApkMethod;
import com.liangmayong.apkbox.reflect.ApkReflect;
import com.liangmayong.apkbox.utils.ApkLogger;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LiangMaYong on 2017/3/30.
 */
public class ApkLoader {

    // appinfo constructor
    private static Constructor<ApkLoaded> mConstructor = null;

    /**
     * loadApk
     *
     * @param context context
     * @param apkPath apkPath
     * @return
     */
    public static ApkLoaded loadApk(Context context, String apkPath) {
        File file = new File(apkPath);
        if (file.exists() && apkPath.endsWith(".apk")) {
            try {
                PackageManager pm = context.getPackageManager();
                PackageInfo pkg = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
                if (pkg != null) {
                    if (mConstructor == null) {
                        mConstructor = ApkLoaded.class.getDeclaredConstructor();
                    }
                    mConstructor.setAccessible(true);
                    ApkLoaded loaded = mConstructor.newInstance();
                    loaded.setHostContext(context.getApplicationContext());
                    ApplicationInfo info = pkg.applicationInfo;
                    if (Build.VERSION.SDK_INT >= 8) {
                        info.sourceDir = apkPath;
                        info.publicSourceDir = apkPath;
                    }
                    info.nativeLibraryDir = ApkNative.copyNativeLibrary(apkPath);
                    String applicationName = ApkManifestParser.getApplicationName(apkPath);
                    if (applicationName != null && !"".equals(applicationName)) {
                        applicationName = parserClassName(pkg.packageName, applicationName);
                        info.className = applicationName;
                    }
                    loaded.setApkPath(apkPath);
                    loaded.setApkName(pm.getApplicationLabel(info).toString());
                    loaded.setApkName(pm.getApplicationLabel(info).toString());
                    loaded.setApkIcon(info.loadIcon(pm));
                    loaded.setApkSignture(ApkSignture.getSignture(context, apkPath));
                    loaded.setApkInfo(pkg);
                    loaded.setApkVersionCode(pkg.versionCode);
                    loaded.setApkVersionName(pkg.versionName);
                    loaded.setApkPackageManager(pm);
                    loaded.setConfigures(ApkConfigure.getConfigure(ApkResources.getAssets(context, apkPath)));
                    loaded.setFilters(ApkManifestParser.getIntentFilter(apkPath));
                    loaded.setApkLauncher(getMainActivityName(loaded, info.packageName));
                    loaded.setPermissions(parserPermissions(context, apkPath));
                    loaded.setProviders(parseProviders(apkPath));
                    ApkReceiver.unregisterReceiver(loaded);
                    loaded.setApkApplication(createApplication(context, loaded, applicationName));
                    ApkReceiver.registerReceiver(context, loaded);
                    ApkLogger.get().debug("Load apk success :" + loaded, null);
                    return loaded;
                } else {
                    ApkLogger.get().debug("Load apk fail : package info is null", null);
                }
            } catch (Exception e) {
                ApkLogger.get().debug("Load apk fail", e);
            }
        } else {
            ApkLogger.get().debug("Invalid apk file : " + apkPath, null);
        }
        return null;
    }

    /**
     * parserClassName
     *
     * @param className className
     * @return className
     */
    public static String parserClassName(String packageName, String className) {
        String newClassName = "";
        if (className.startsWith(".")) {
            newClassName = packageName + className;
        } else if (className.indexOf(".") == -1) {
            newClassName = packageName + "." + className;
        } else {
            newClassName = className;
        }
        return newClassName;
    }

    /**
     * getApkLauncher
     *
     * @return main
     */
    private static String getMainActivityName(ApkLoaded loaded, String packageName) {
        String main = loaded.getConfigure("main");
        if (main == null || "".equals(main)) {
            Map<String, IntentFilter> filters = loaded.getFilters();
            for (Map.Entry<String, IntentFilter> entry : filters.entrySet()) {
                IntentFilter intentFilter = entry.getValue();
                if (intentFilter.countCategories() > 0) {
                    for (int i = 0; i < intentFilter.countCategories(); i++) {
                        String category = intentFilter.getCategory(i);
                        if (category.equals("android.intent.category.LAUNCHER")) {
                            return parserClassName(packageName, entry.getKey());
                        }
                    }
                }
            }
            return "";
        }
        return parserClassName(packageName, main);
    }


    /**
     * createApplication
     *
     * @param context         context
     * @param applicationName applicationName
     * @return application
     */
    private static Application createApplication(Context context, ApkLoaded loaded, String applicationName) {
        Application application = null;
        Context ctx = ApkContext.get(context, loaded);
        try {
            if (ApkProcess.validateApkProcessName(context)) {
                application = (Application) loaded.getClassLoader().loadClass(applicationName)
                        .newInstance();

                ApkMethod method = new ApkMethod(Application.class, application, "attach", Context.class);
                method.invoke(ctx);

                ApkReflect.setField(Application.class, application, "mBase", ctx);
                application.onCreate();

                ApkContextModifier.setOuterContext(context, application);
            } else {
                application = (Application) ctx;
            }
        } catch (Exception e) {
            application = (Application) ctx;
        }
        ApkLogger.get().debug("make Application : " + application, null);
        return application;
    }

    private static List<String> parserPermissions(Context context, String apkPath) {
        List<String> permissions = new ArrayList<>();
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pkgInfo = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_PERMISSIONS);
            String[] perms = pkgInfo.requestedPermissions;
            if (perms != null) {
                for (String permName : perms) {
                    permissions.add(permName);
                }
            }
        } catch (Exception e) {
        }
        return permissions;
    }

    /**
     * parseProviders
     *
     * @param apkPath apkPath
     * @return providers
     */
    public static List<ProviderInfo> parseProviders(String apkPath) {
        List<ProviderInfo> ret = new ArrayList<>();
        File apkFile = new File(apkPath);
        if (apkFile.exists()) {
            try {
                Class<?> packageParserClass = Class.forName("android.content.pm.PackageParser");
                Method parsePackageMethod = packageParserClass.getDeclaredMethod("parsePackage", File.class, int.class);

                Object packageParser = packageParserClass.newInstance();

                Object packageObj = parsePackageMethod.invoke(packageParser, apkFile, PackageManager.GET_PROVIDERS);

                Field providersField = packageObj.getClass().getDeclaredField("providers");
                List providers = (List) providersField.get(packageObj);

                Class<?> packageParser$ProviderClass = Class.forName("android.content.pm.PackageParser$Provider");
                Class<?> packageUserStateClass = Class.forName("android.content.pm.PackageUserState");
                Class<?> userHandler = Class.forName("android.os.UserHandle");
                Method getCallingUserIdMethod = userHandler.getDeclaredMethod("getCallingUserId");
                int userId = (Integer) getCallingUserIdMethod.invoke(null);
                Object defaultUserState = packageUserStateClass.newInstance();

                Method generateProviderInfo = packageParserClass.getDeclaredMethod("generateProviderInfo",
                        packageParser$ProviderClass, int.class, packageUserStateClass, int.class);
                for (Object service : providers) {
                    ProviderInfo info = (ProviderInfo) generateProviderInfo.invoke(packageParser, service, 0, defaultUserState, userId);
                    ret.add(info);
                }
            } catch (Exception e) {
            }
        }
        return ret;
    }
}
