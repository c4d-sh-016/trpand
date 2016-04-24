package net.exshinigami.remembrance.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.concurrent.atomic.AtomicInteger;

public class DaoHelper {
    private volatile static DaoHelper mInstance;

    private static final String DATABASE_NAME = "remembrance-db";

    private DaoSession daoSession;
    private DaoMaster daoMaster;
    private SQLiteDatabase db;
    private Context context;
    AtomicInteger openDbCounter;

    public static DaoHelper getInstance(Context context) {
        if(mInstance == null) {
            synchronized (DaoHelper.class) {
                if (mInstance == null) {
                    mInstance = new DaoHelper(context);
                }
            }
        }
        return mInstance;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    private DaoHelper(Context context) {
        this.context = context;
        openDbCounter = new AtomicInteger();
    }

    private void initDB() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(
                context, DATABASE_NAME, null
        );
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public void openDataBase() {
        if (openDbCounter.getAndIncrement() == 0) {
            initDB();
        }
    }

    public void closeDataBase() {
        if(openDbCounter.getAndDecrement() == 1) {
            close();
        }
    }

    public void close() {
        db.close();
        daoMaster.getDatabase().close();
        daoSession.getDatabase().close();
        db = null;
        daoMaster=null;
        daoSession = null;
    }
}
