package net.exshinigami.remembrancedaogenerator;

import de.greenrobot.daogenerator.Entity;

public class User extends BaseEntity{
    private User(){}

    // USER TABLE
    public static final String ENTITY = "User";
    public static final String MOBILE_NUMBER = "mobileNumber";
    public static final String TYPE = "type";
    public static final String SMS_COUNT = "smsCount";
    public static final String SPAM_COUNT = "spamCount";

    public static void addUser(Entity user) {
        user.addIdProperty().autoincrement();
        user.addStringProperty(MOBILE_NUMBER).notNull().unique();
        user.addStringProperty(TYPE).notNull();
        user.addLongProperty(SMS_COUNT).notNull();
        user.addLongProperty(SPAM_COUNT).notNull();
        user.addDateProperty(CREATED).notNull();
        user.addDateProperty(UPDATED).notNull();
        user.addBooleanProperty(ENABLED).notNull();
    }
}
