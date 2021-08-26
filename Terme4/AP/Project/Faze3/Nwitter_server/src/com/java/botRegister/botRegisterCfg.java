package com.java.botRegister;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class botRegisterCfg {
    protected static String database = "";
    protected static String usersCollection = "";
    protected static String twittesCollection = "";
    protected static String reportsCollection = "";
    protected static String notifsCollection = "";
    protected static String messagesCollection = "";
    protected static String profilePicPath = "";
    protected static String twittePicPath = "";
    protected static String messagePicPath = "";

    private static final Logger logger = LogManager.getLogger(botRegisterCfg.class);
    public botRegisterCfg() throws IOException {
        File configFile = new File("botRegisterCfg.properties");

        try {
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);
            database = props.getProperty("database");
            usersCollection = props.getProperty("users_collection");
            twittesCollection = props.getProperty("twittes_collection");
            reportsCollection = props.getProperty("reports_collection");
            notifsCollection = props.getProperty("notifs_collection");
            messagesCollection = props.getProperty("messages_collection");
            profilePicPath = props.getProperty("profile_pic_path");
            twittePicPath = props.getProperty("twitte_pic_path");
            messagePicPath = props.getProperty("message_pic_path");

            logger.info("loadCfg red successfully");
            reader.close();
        } catch (FileNotFoundException ex) {
            logger.error("file botResiterCfg doesnt exists");
        } catch (IOException ex) {
            logger.error("error in reading config loadCfg");
        }
        if (database.isEmpty())database="Nwitter";
        if (usersCollection.isEmpty())usersCollection="Users";
        if (twittesCollection.isEmpty())twittesCollection="Twittes";
        if (reportsCollection.isEmpty())reportsCollection="Reports";
        if (messagesCollection.isEmpty())messagesCollection="Messages";
        if (notifsCollection.isEmpty())notifsCollection="Notifs";
        if (profilePicPath.isEmpty())profilePicPath="\\src\\resources\\profiles\\";
        if (twittePicPath.isEmpty())twittePicPath="\\src\\resources\\twittes\\";
        if (messagePicPath.isEmpty())messagePicPath="\\src\\resources\\messages\\";
    }

}
