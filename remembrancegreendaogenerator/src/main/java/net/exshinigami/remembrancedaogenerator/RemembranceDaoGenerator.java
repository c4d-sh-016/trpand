package net.exshinigami.remembrancedaogenerator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class RemembranceDaoGenerator {



    public static void main(String[] args) throws Exception{

        Schema schema = new Schema(
                Config.DB_VERSION, Config.PACKAGE
        );

        Entity user = schema.addEntity(User.ENTITY);
        User.addUser(user);

        Entity message = schema.addEntity(Message.ENTITY);
        Message.addMessage(user, message);

        Entity picture = schema.addEntity(Picture.ENTITY);
        Picture.addPicture(user, picture);


        new DaoGenerator().generateAll(schema, Config.DAO_DIR);
    }
}
