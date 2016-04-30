package net.exshinigami.remembrance.model;

import android.database.sqlite.SQLiteDatabase;
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
public class MessageTest extends AndroidTestCase {

    private DaoSession daoSession;
    private MessageDao messageDao;
    private String text = "some-mesaage";
    private String command = "some-command";
    private long userID = 1;

    @Before
    public void setUp() throws Exception {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(RuntimeEnvironment.application, null, null);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        daoSession = new DaoMaster(db).newSession();
        messageDao = daoSession.getMessageDao();
    }

    @Test
    public void testGet() throws Exception {
        Message message = new Message();
        message.setDefault();
        message.setText(text);
        message.setCommand(command);
        message.setUserId(userID);
        message.save(daoSession);

        Message message1 = Message.get(daoSession, message.getId());
        assertNotNull(message1);
        assertEquals(message1.getId(), message.getId());
        assertEquals(message1.getUserId(), message.getUserId());
        assertEquals(message1.getText(), message.getText());
        assertEquals(message1.getCommand(), message.getCommand());
        assertEquals(1, messageDao.count());
        assertEquals(1, daoSession.loadAll(Message.class).size());
    }

    @Test
    public void testGetDoesNotExist() throws Exception {
        try {
            Message.get(daoSession, 24151313);
            fail();
        }catch (DoesNotExist e){

        }
    }

    @Test
    public void testGetOrCreate() throws Exception {
        Message message = new Message();
        message.setDefault();
        message.setText(text);
        message.setCommand(command);
        message.setUserId(userID);
        message.save(daoSession);

        Message message1 = Message.getOrCreate(
                daoSession, message.getId(), message.getText(),
                message.getCommand(), message.getUserId()
        );

        assertNotNull(message1);
        assertEquals(message1.getId(), message.getId());
        assertEquals(message1.getUserId(), message.getUserId());
        assertEquals(message1.getText(), message.getText());
        assertEquals(message1.getCommand(), message.getCommand());
        assertEquals(1, messageDao.count());
        assertEquals(1, daoSession.loadAll(Message.class).size());

    }

    @Test
    public void testGetOrCreateDoesNotExist() throws Exception {
        Message message = Message.getOrCreate(
                daoSession, 2124124124, text, command, userID
        );

        assertNotNull(message);
        assertNotNull(message.getId());
        assertEquals(message.getUserId(), userID);
        assertEquals(message.getText(), text);
        assertEquals(message.getCommand(), command);
        assertEquals(1, messageDao.count());
        assertEquals(1, daoSession.loadAll(Message.class).size());
    }

    @Test
    public void testCreate() throws Exception {
        Message message = Message.create(text, command, userID);
        message.save(daoSession);
        assertNotNull(message);
        assertNotNull(message.getId());
        assertEquals(message.getUserId(), userID);
        assertEquals(message.getText(), text);
        assertEquals(message.getCommand(), command);
        assertEquals(1, messageDao.count());
        assertEquals(1, daoSession.loadAll(Message.class).size());
    }

    @Test
    public void testSave() throws Exception {
        Message message = new Message();
        message.setDefault();
        message.setText(text);
        message.setCommand(command);
        message.setUserId(userID);
        message.save(daoSession);

        assertNotNull(message);
        assertNotNull(message.getId());
        assertEquals(message.getUserId(), userID);
        assertEquals(message.getText(), text);
        assertEquals(message.getCommand(), command);
        assertEquals(1, messageDao.count());
        assertEquals(1, daoSession.loadAll(Message.class).size());

        Message message1 = new Message();
        message1.setDefault();
        message1.setText(text);
        message1.setCommand(command);
        message1.setUserId(userID);
        message1.save(daoSession);

        assertNotNull(message1);
        assertNotNull(message1.getId());
        assertEquals(message1.getUserId(), userID);
        assertEquals(message1.getText(), text);
        assertEquals(message1.getCommand(), command);
        assertEquals(2, messageDao.count());
        assertEquals(2, daoSession.loadAll(Message.class).size());
    }
}