package com.java.category;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class categoriesCfg {
    protected static String database = "";
    protected static String usersCollection = "";

    private static final Logger logger = LogManager.getLogger(categoriesCfg.class);
    public categoriesCfg() throws IOException {
        File configFile = new File("categoriesCfg.properties");

        try {
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);
            database = props.getProperty("database");
            usersCollection = props.getProperty("users_collection");

            logger.info("loadCfg red successfully");
            reader.close();
        } catch (FileNotFoundException ex) {
            logger.error("file loadCfg doesnt exists");
        } catch (IOException ex) {
            logger.error("error in reading config loadCfg");
        }
        if (database.isEmpty())database="Nwitter";
        if (usersCollection.isEmpty())usersCollection="Users";
    }

}
