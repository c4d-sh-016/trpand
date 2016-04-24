package net.exshinigami.remembrancedaogenerator;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;

public class Message extends UploadEntity {
    private Message() {}

    // MESSAGE TABLE
    public static final String ENTITY = "Message";
    public static final String TEXT = "text";
    public static final String COMMAND = "command";

    public static void addMessage(Entity user, Entity message) {
        message.addIdProperty();
        message.addStringProperty(TEXT);
        message.addStringProperty(COMMAND);
        message.addBooleanProperty(UPLOADED);

        Property userId = message.addLongProperty(CommonProperty.USER_ID)
                .notNull()
                .getProperty();

        user.addToMany(message, userId);
    }
}
