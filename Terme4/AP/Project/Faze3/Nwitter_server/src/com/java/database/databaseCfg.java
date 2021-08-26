package com.java.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class databaseCfg {
    protected static String database = "";
    protected static String collection = "";
    private static final Logger logger = LogManager.getLogger(databaseCfg.class);
    public databaseCfg() throws IOException {
        File configFile = new File("databaseCfg.properties");

        try {
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);
            database = props.getProperty("database");
            collection = props.getProperty("collection");
            logger.info("loginCfg red successfully");
            reader.close();
        } catch (FileNotFoundException ex) {
            logger.error("file loginCfg doesnt exists");
        } catch (IOException ex) {
            logger.error("error in reading config loginCfg");
        }

    }


}
