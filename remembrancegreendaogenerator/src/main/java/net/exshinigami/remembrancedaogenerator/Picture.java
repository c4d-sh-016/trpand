package net.exshinigami.remembrancedaogenerator;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;

public class Picture extends UploadEntity{

    private Picture() {}

    // PICTURE TABLE
    public static final String ENTITY = "Picture";
    public static final String NAME = "name";

    public static void addPicture(Entity user, Entity picture) {
        picture.addIdProperty().autoincrement();
        picture.addStringProperty(NAME).notNull().unique();
        picture.addBooleanProperty(UPLOADED).notNull();
        picture.addDateProperty(CREATED).notNull();
        picture.addDateProperty(UPDATED).notNull();
        picture.addBooleanProperty(ENABLED).notNull();

        Property userId = picture.addLongProperty(CommonProperty.USER_ID)
                .notNull()
                .getProperty();

        user.addToMany(picture, userId);
    }
}
