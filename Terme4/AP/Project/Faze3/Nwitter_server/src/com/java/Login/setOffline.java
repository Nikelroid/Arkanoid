package com.java.Login;

import com.java.database.databaseControl;
import com.mongodb.client.MongoCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class setOffline {
    private static final Logger logger = LogManager.getLogger(setOffline.class);
    databaseControl dataBaseControl = new databaseControl();
    String collection;
    public int setter(String AuthKey) {
        try {
            try {
                new loginCfg();
            } catch (IOException e) {
                logger.error("couldn't open config of login");
            }
            if (loginCfg.collection == null) {
                collection = "Users";
            } else {
                collection = loginCfg.collection;
            }

            MongoCollection<Document> usersCollection = dataBaseControl.getCollection(collection);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            Bson filter = eq("AuthKey", Integer.parseInt(AuthKey));
            Bson update = set("lastseen", dtf.format(now));
            usersCollection.findOneAndUpdate(filter, update);

            return 1;
        }catch (Exception e){
            logger.error("Error in load database in offlining");
            e.printStackTrace();
            return 0;
        }
        }

    }

