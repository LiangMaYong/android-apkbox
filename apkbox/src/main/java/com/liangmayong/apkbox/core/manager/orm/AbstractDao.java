package com.liangmayong.apkbox.core.manager.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiangMaYong on 2017/3/15.
 */
public abstract class AbstractDao {

    public interface Converter<D, T> {
        D convert(T t);
    }

    /**
     * getHelper
     *
     * @param context context
     * @return SQLiteOpenHelper
     */
    protected abstract SQLiteOpenHelper getHelper(Context context);

    /**
     * handleException
     *
     * @param e e
     */
    protected void handleException(Exception e) {
        e.printStackTrace();
    }

    /**
     * getDataList
     *
     * @param context   context
     * @param tableName tableName
     * @param where     where
     * @param orderBy   orderBy
     * @param limit     limit
     * @param converter converter
     * @param <D>       dtype
     * @return list d
     */
    protected <D> List<D> getDataList(Context context, String tableName, String where, String orderBy, String limit, Converter<D, Cursor> converter) {
        List<D> list = new ArrayList<D>();
        try {
            SQLiteDatabase db = getHelper(context).getReadableDatabase();
            Cursor cursor = db.query(false, tableName, null, where, null, null, null, orderBy, limit);
            try {
                while (cursor.moveToNext()) {
                    D data = converter.convert(cursor);
                    if (data != null) {
                        list.add(data);
                    }
                }
            } finally {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                db.close();
                db = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return list;
    }

    /**
     * getData
     *
     * @param context   context
     * @param tableName tableName
     * @param where     where
     * @param orderBy   orderBy
     * @param converter converter
     * @param <D>       dtype
     * @return d
     */
    protected <D> D getData(Context context, String tableName, String where, String orderBy, Converter<D, Cursor> converter) {
        D data = null;
        try {
            SQLiteDatabase db = getHelper(context).getReadableDatabase();
            Cursor cursor = db.query(false, tableName, null, where, null, null, null, orderBy, null);
            try {
                if (cursor.moveToNext()) {
                    data = converter.convert(cursor);
                }
            } finally {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                db.close();
                db = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return data;
    }

    /**
     * getTotal
     *
     * @param context   context
     * @param tableName tableName
     * @param field     field
     * @param where     where
     * @return total
     */
    protected int getTotal(Context context, String tableName, String field, String where) {
        int sum = 0;
        try {
            SQLiteDatabase db = getHelper(context).getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT SUM(" + field + ") FROM " + tableName + " where " + where, null);
            try {
                cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    sum = cursor.getInt(0);
                }
            } finally {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                db.close();
                db = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return sum;
    }

    /**
     * updateData
     *
     * @param context   context
     * @param tableName tableName
     * @param d         d
     * @param converter converter
     * @param where     where
     * @param <D>       dtype
     * @return update count
     */
    protected <D> int updateData(Context context, String tableName, D d, Converter<ContentValues, D> converter, String where) {
        int update = 0;
        try {
            SQLiteDatabase db = getHelper(context).getWritableDatabase();
            try {
                update = db.update(tableName, converter.convert(d), where, null);
            } finally {
                db.close();
                db = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return update;
    }

    /**
     * updateData
     *
     * @param context   context
     * @param tableName tableName
     * @param values    values
     * @param where     where
     * @return update count
     */
    protected int updateData(Context context, String tableName, ContentValues values, String where) {
        int update = 0;
        try {
            SQLiteDatabase db = getHelper(context).getWritableDatabase();
            try {
                update = db.update(tableName, values, where, null);
            } finally {
                db.close();
                db = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return update;
    }

    /**
     * insertData
     *
     * @param context   context
     * @param tableName tableName
     * @param d         d
     * @param converter converter
     * @param <D>       dtype
     * @return id
     */
    protected <D> long insertData(Context context, String tableName, D d, Converter<ContentValues, D> converter) {
        long id = 0;
        try {
            SQLiteDatabase db = getHelper(context).getWritableDatabase();
            try {
                id = db.insert(tableName, null, converter.convert(d));
            } finally {
                db.close();
                db = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return id;
    }

    /**
     * insertData
     *
     * @param context   context
     * @param tableName tableName
     * @param ds        ds
     * @param converter converter
     * @param <D>       type
     * @return insert count
     */
    protected <D> int insertDataList(Context context, String tableName, List<D> ds, Converter<ContentValues, D> converter) {
        int count = 0;
        try {
            SQLiteDatabase db = getHelper(context).getWritableDatabase();
            try {
                db.beginTransaction();
                for (int i = 0; i < ds.size(); i++) {
                    long id = db.insert(tableName, null, converter.convert(ds.get(i)));
                    if (id > 0) {
                        count++;
                    }
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
                db.close();
                db = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return count;
    }

    /**
     * insertData
     *
     * @param context   context
     * @param tableName tableName
     * @param values    values
     * @return id
     */
    protected long insertData(Context context, String tableName, ContentValues values) {
        long id = 0;
        try {
            SQLiteDatabase db = getHelper(context).getWritableDatabase();
            try {
                id = db.insert(tableName, null, values);
            } finally {
                db.close();
                db = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return id;
    }

    /**
     * insertData
     *
     * @param context   context
     * @param tableName tableName
     * @param values    values
     * @return id
     */
    protected int insertDataList(Context context, String tableName, List<ContentValues> values) {
        int count = 0;
        try {
            SQLiteDatabase db = getHelper(context).getWritableDatabase();
            try {
                db.beginTransaction();
                for (int i = 0; i < values.size(); i++) {
                    long id = db.insert(tableName, null, values.get(i));
                    if (id > 0) {
                        count++;
                    }
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
                db.close();
                db = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return count;
    }

    /**
     * deleteData
     *
     * @param context   context
     * @param tableName tableName
     * @param where     where
     */
    protected int deleteData(Context context, String tableName, String where) {
        int delete = 0;
        try {
            SQLiteDatabase db = getHelper(context).getWritableDatabase();
            try {
                delete = db.delete(tableName, where, null);
            } finally {
                db.close();
                db = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return delete;
    }

    /**
     * getCount
     *
     * @param context   context
     * @param tableName tableName
     * @param where     where
     * @return count
     */
    protected int getCount(Context context, String tableName, String where) {
        int count = 0;
        try {
            SQLiteDatabase db = getHelper(context).getReadableDatabase();
            Cursor cursor = db.query(false, tableName, null, where, null, null, null, null, null);
            try {
                count = cursor.getCount();
            } finally {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                db.close();
                db = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return count;
    }

    /**
     * hasData
     *
     * @param context   context
     * @param tableName tableName
     * @param where     where
     * @return bool
     */
    protected boolean hasData(Context context, String tableName, String where) {
        int count = 0;
        try {
            SQLiteDatabase db = getHelper(context).getWritableDatabase();
            Cursor cursor = db.query(false, tableName, null, where, null, null, null, null, null);
            try {
                count = cursor.getCount();
            } finally {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                db.close();
                db = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return count > 0;
    }

    protected String readString(String key, Cursor cursor) {
        String value = "";
        try {
            int columnIndex = cursor.getColumnIndex(key);
            value = cursor.getString(columnIndex);
        } catch (Exception e) {
        }
        return value;
    }

    protected int readInt(String key, Cursor cursor) {
        int value = 0;
        try {
            int columnIndex = cursor.getColumnIndex(key);
            value = cursor.getInt(columnIndex);
        } catch (Exception e) {
        }
        return value;
    }

    protected long readLong(String key, Cursor cursor) {
        long value = 0;
        try {
            int columnIndex = cursor.getColumnIndex(key);
            value = cursor.getLong(columnIndex);
        } catch (Exception e) {
        }
        return value;
    }

    protected byte[] readBytes(String key, Cursor cursor) {
        byte[] value = new byte[0];
        try {
            int columnIndex = cursor.getColumnIndex(key);
            value = cursor.getBlob(columnIndex);
        } catch (Exception e) {
        }
        return value;
    }
}
