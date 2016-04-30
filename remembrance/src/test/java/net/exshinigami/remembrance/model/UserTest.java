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
public class UserTest extends AndroidTestCase {


    private DaoSession daoSession;
    private UserDao userDao;

    private String mobileNumber = "09123434513";

    @Before
    public void setUp() throws Exception {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(RuntimeEnvironment.application, null, null);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        daoSession = new DaoMaster(db).newSession();
        userDao = daoSession.getUserDao();
    }

    @Test
    public void testGetById() throws Exception {
        User user = new User();
        user.setMobileNumber(mobileNumber);
        user.setType(UserType.CLIENT.toString());
        user.setDefault();
        user.save(daoSession);

        User user1 = User.get(daoSession, user.getId());
        assertNotNull(user1);
        assertNotNull(user1.getId());
        assertEquals(user1.getMobileNumber(), user.getMobileNumber());
        assertEquals(user1.getType(), user.getType());
        assertEquals(1, userDao.count());
        assertEquals(1, daoSession.loadAll(User.class).size());
    }

    @Test
    public void testGetByIdDoesNotExist() throws Exception {
        try {
            User.get(daoSession, 4141341);
            fail();
        }catch (DoesNotExist e) {

        }

    }

    @Test
    public void testGetByMobileNumber() throws Exception {
        User user = new User();
        user.setMobileNumber(mobileNumber);
        user.setType(UserType.CLIENT.toString());
        user.setDefault();
        user.save(daoSession);

        User user1 = User.get(daoSession, user.getMobileNumber());
        assertNotNull(user1);
        assertNotNull(user1.getId());
        assertEquals(user1.getMobileNumber(), user.getMobileNumber());
        assertEquals(user1.getType(), user.getType());
        assertEquals(1, userDao.count());
        assertEquals(1, daoSession.loadAll(User.class).size());
    }

    @Test
    public void testGetByMobileMobileNumberDoesNotExist() {
        try {
            User.get(daoSession, "1341341341134");
            fail();
        } catch (DoesNotExist e) {

        }
    }

    @Test
    public void testGetOrCreateByMobileNumber() throws Exception {
        String type = UserType.ADMIN.getType();
        User user = new User();
        user.setDefault();
        user.setMobileNumber(mobileNumber);
        user.setType(type);
        user.save(daoSession);

        User user1 = User.getOrCreate(daoSession, user.getMobileNumber(), type);

        assertNotNull(user1);
        assertEquals(user1.getId(), user.getId());
        assertEquals(user1.getMobileNumber(), user.getMobileNumber());
        assertEquals(user1.getType(), user.getType());
        assertEquals(1, userDao.count());
        assertEquals(1, daoSession.loadAll(User.class).size());

        String randomNum = "134135135153";
        String type2 = UserType.CLIENT.getType();
        User user2 = User.getOrCreate(daoSession, randomNum, type2);

        assertNotNull(user2);
        assertNotNull(user2.getId());
        assertEquals(user2.getMobileNumber(), randomNum);
        assertEquals(user2.getType(), type2);
        assertEquals(2, userDao.count());
        assertEquals(2, daoSession.loadAll(User.class).size());

    }


    @Test
    public void testGetOrCreateById() throws Exception {
        String type = UserType.ADMIN.getType();
        User user = new User();
        user.setDefault();
        user.setMobileNumber(mobileNumber);
        user.setType(type);
        user.save(daoSession);

        User user1 = User.getOrCreate(
                daoSession, user.getId(), user.getMobileNumber(),
                user.getType()
        );

        assertNotNull(user1);
        assertEquals(user1.getId(), user.getId());
        assertEquals(user1.getMobileNumber(), user.getMobileNumber());
        assertEquals(user1.getType(), user.getType());
        assertEquals(1, userDao.count());
        assertEquals(1, daoSession.loadAll(User.class).size());

        try {
            User user2 = User.getOrCreate(daoSession, 121241, mobileNumber, type);
            fail();
        }catch (SQLiteException e) {

        }
    }

    @Test
    public void testCreate() throws Exception {
        String type = UserType.ADMIN.getType();
        User user = User.create(mobileNumber, type);
        user.save(daoSession);

        assertNotNull(user.getId());
        assertNotNull(User.get(daoSession, user.getId()));
        assertNotNull(User.get(daoSession, mobileNumber));
        assertEquals(user.getType(), type);
        assertEquals(1, userDao.count());
        assertEquals(1, daoSession.loadAll(User.class).size());

    }

    @Test
    public void testSave() throws Exception {
        String type = UserType.ADMIN.getType();

        User user = new User();
        user.setDefault();
        user.setMobileNumber(mobileNumber);
        user.setType(type);
        user.save(daoSession);

        assertNotNull(user.getId());
        assertNotNull(User.get(daoSession, user.getId()));
        assertNotNull(User.get(daoSession, mobileNumber));
        assertEquals(user.getType(), type);
        assertEquals(1, userDao.count());
        assertEquals(1, daoSession.loadAll(User.class).size());

        User user1 = new User();
        user1.setDefault();
        user1.setMobileNumber(mobileNumber);
        user1.setType(type);
        try {
            user1.save(daoSession);
            fail();
        }catch (SQLiteException e){

        }
    }

}