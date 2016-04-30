package net.exshinigami.remembrance;

import android.database.sqlite.SQLiteDatabase;

import net.exshinigami.remembrance.model.DaoMaster;
import net.exshinigami.remembrance.model.DaoSession;

public class RemembranceApp extends android.app.Application {

    private static final String DATABASE_NAME = "remembrance-db";

    private static RemembranceApp mInstance;

    private DaoSession daoSession;
    private DaoMaster daoMaster;
    private SQLiteDatabase db;

    public RemembranceApp() {
        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(
                RemembranceApp.getInstance(), DATABASE_NAME, null
        );
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static RemembranceApp getInstance() {
        return mInstance;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
