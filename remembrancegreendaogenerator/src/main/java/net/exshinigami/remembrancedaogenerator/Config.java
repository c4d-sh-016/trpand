package net.exshinigami.remembrancedaogenerator;

public class Config {
    private Config(){}

    // DB CONFIG

    private static final String PROJECT_DIR = System.getProperty("user.dir")
            .replace("\\", "/");

    private static final String APP = "/remembrance";
    private static final String DESTENATION_DIR = "/src/main/java";
    public static final String DAO_DIR = PROJECT_DIR + APP + DESTENATION_DIR ;


    public static int DB_VERSION = 1;

    public static String PACKAGE = "net.exshinigami.remembrance.model";
}
