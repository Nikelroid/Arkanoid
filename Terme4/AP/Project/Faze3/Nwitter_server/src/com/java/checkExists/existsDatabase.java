package com.java.checkExists;

import com.java.database.databaseControl;
import com.mongodb.client.MongoCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.security.SecureRandom;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class existsDatabase {
    private static final Logger logger = LogManager.getLogger(existsDatabase.class);
    databaseControl dataBaseControl = new databaseControl();
    String collection;
    public int database(String field,String input) {
        try {
            new existsCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of login");
        }
        if(existsCfg.collection==null){
            collection = "Users";
        }else{
            collection = existsCfg.collection;
        }

        MongoCollection<Document> usersCollection = dataBaseControl.getCollection(collection);
            Document user = usersCollection.find(new Document(field, input)).first();
            if(user==null)
                return 1;
            else
                return 0;
            }
        }


