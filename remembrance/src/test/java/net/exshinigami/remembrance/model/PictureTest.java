package net.exshinigami.remembrance.model;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.test.AndroidTestCase;

import net.exshinigami.remembrance.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class PictureTest extends AndroidTestCase{

    private DaoSession daoSession;
    private PictureDao pictureDao;

    private String pictureName = "some-name";
    private int userId = 1;

    @Before
    public void setUp() throws Exception {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(RuntimeEnvironment.application, null, null);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        daoSession = new DaoMaster(db).newSession();
        pictureDao = daoSession.getPictureDao();
    }

    @Test
    public void testSave() throws Exception {
        Picture picture = new Picture();
        picture.setDefault();
        picture.setUserId(userId);
        picture.setName(pictureName);
        picture.save(daoSession);

        assertNotNull(picture);
        assertNotNull(picture.getId());
        assertEquals(picture.getName(), pictureName);
        assertEquals(picture.getUserId(), userId);
        assertEquals(1, pictureDao.count());
        assertEquals(1, daoSession.loadAll(Picture.class).size());

        Picture picture1 = new Picture();
        picture1.setDefault();
        picture1.setUserId(userId);
        picture1.setName(pictureName);
        try {
            picture1.save(daoSession);
            fail();
        }catch (SQLiteException e) {

        }

    }

    @Test
    public void testGet() throws Exception {

        Picture picture = new Picture();
        picture.setDefault();
        picture.setName(pictureName);
        picture.setUserId(userId);
        picture.save(daoSession);

        Picture picture1 = Picture.get(daoSession, userId);
        assertNotNull(picture1);
        assertNotNull(picture1.getId());
        assertEquals(picture1.getName(), picture.getName());
        assertEquals(picture1.getUserId(), picture.getUserId());
        assertEquals(picture1.getId(), picture.getId());
        assertEquals(1, pictureDao.count());
        assertEquals(1, daoSession.loadAll(Picture.class).size());
    }

    @Test
    public void testGetUserDoseNotExist() throws Exception {
        try {
            Picture.get(daoSession, 123123);
            fail();
        } catch (DoesNotExist e) {

        }

    }

    @Test
    public void testGetOrCreate() throws Exception {
        Picture picture = new Picture();
        picture.setDefault();
        picture.setName(pictureName);
        picture.setUserId(userId);
        picture.save(daoSession);

        Picture picture1 = Picture.getOrCreate(
                daoSession, picture.getId(), pictureName, userId
        );

        assertNotNull(picture1);
        assertNotNull(picture1.getId());
        assertEquals(picture1.getName(), picture.getName());
        assertEquals(picture1.getUserId(), picture.getUserId());
        assertEquals(picture1.getId(), picture.getId());
        assertEquals(1, pictureDao.count());
        assertEquals(1, daoSession.loadAll(Picture.class).size());

    }

    @Test
    public void testGetOrCreatePictureDoesNotExist() throws Exception {
        Picture picture1 = Picture.getOrCreate(
                daoSession, 12313l, pictureName, userId
        );

        assertNotNull(picture1);
        assertNotNull(picture1.getId());
        assertEquals(picture1.getName(), pictureName);
        assertEquals(picture1.getUserId(), userId);
        assertEquals(1, pictureDao.count());
        assertEquals(1, daoSession.loadAll(Picture.class).size());


    }

    @Test
    public void testCreate() throws Exception {
        Picture picture = Picture.create(pictureName, userId);
        picture.save(daoSession);
        assertNotNull(picture);
        assertNotNull(picture.getId());
        assertEquals(1, pictureDao.count());
        assertEquals(1, daoSession.loadAll(Picture.class).size());
    }
}