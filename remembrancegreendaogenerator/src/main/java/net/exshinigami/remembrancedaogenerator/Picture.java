package net.exshinigami.remembrancedaogenerator;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;

public class Picture extends UploadEntity{

    private Picture() {}

    // PICTURE TABLE
    public static final String ENTITY = "Picture";
    public static final String NAME = "name";

    public static void addPicture(Entity user, Entity picture) {
        picture.addIdProperty();
        picture.addStringProperty(NAME);
        picture.addBooleanProperty(UPLOADED);

        Property userId = picture.addLongProperty(CommonProperty.USER_ID)
                .notNull()
                .getProperty();

        user.addToMany(picture, userId);
    }
}
