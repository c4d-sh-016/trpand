package net.exshinigami.remembrancedaogenerator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class RemembranceDaoGenerator {

    public static void main(String[] args) throws Exception{

        Schema schema = new Schema(
                Config.DB_VERSION, Config.PACKAGE
        );

        schema.enableKeepSectionsByDefault();

        Entity user = schema.addEntity(User.ENTITY);
        user.setSuperclass("BaseEntity");
        user.implementsInterface("Savable");
        User.addUser(user);

        Entity message = schema.addEntity(Message.ENTITY);
        message.setSuperclass("BaseEntity");
        message.implementsInterface("Savable");
        Message.addMessage(user, message);

        Entity picture = schema.addEntity(Picture.ENTITY);
        picture.setSuperclass("BaseEntity");
        picture.implementsInterface("Savable");
        Picture.addPicture(user, picture);

        new DaoGenerator().generateAll(schema, Config.DAO_DIR);
    }
}
