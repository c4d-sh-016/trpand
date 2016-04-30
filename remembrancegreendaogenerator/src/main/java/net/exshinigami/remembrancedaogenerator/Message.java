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
        message.addIdProperty().autoincrement();
        message.addStringProperty(TEXT).notNull();
        message.addStringProperty(COMMAND).notNull();
        message.addBooleanProperty(UPLOADED).notNull();
        message.addDateProperty(CREATED).notNull();
        message.addDateProperty(UPDATED).notNull();
        message.addBooleanProperty(ENABLED).notNull();

        Property userId = message.addLongProperty(CommonProperty.USER_ID)
                .notNull()
                .getProperty();

        user.addToMany(message, userId);
    }
}
