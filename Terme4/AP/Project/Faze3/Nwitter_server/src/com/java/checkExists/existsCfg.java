package com.java.checkExists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class existsCfg {
    protected static String database = "";
    protected static String collection = "";
    protected static String messages = "";
    private static final Logger logger = LogManager.getLogger(existsCfg.class);
    public existsCfg() throws IOException {
        File configFile = new File("existsCfg.properties");

        try {
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);
            database = props.getProperty("database");
            collection = props.getProperty("collection");
            messages = props.getProperty("messages");
            logger.info("existsCfg red successfully");
            reader.close();
        } catch (FileNotFoundException ex) {
            logger.error("file existsCfg doesnt exists");
        } catch (IOException ex) {
            logger.error("error in reading config existsCfg");
        }
    }

}
