package com.java.operation;

import com.java.database.databaseControl;
import com.mongodb.client.MongoCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class retwitteDatabase {
    private static final Logger logger = LogManager.getLogger(retwitteDatabase.class);
    databaseControl dataBaseControl = new databaseControl();
    List<String> retwittes ;
    int result = 0;
    public int setter(String AuthKey, String serial) {
        try {
            new operateCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of operates");
        }

        MongoCollection<Document> usersCollection = dataBaseControl.getCollection(operateCfg.usersCollection);
        MongoCollection<Document> twitteCollection = dataBaseControl.getCollection(operateCfg.twittesCollection);
        Document user = usersCollection.find(new Document("AuthKey", Integer.parseInt(AuthKey))).first();
        Document twiite = twitteCollection.find(new Document("_id", Integer.parseInt(serial))).first();
        if(user==null || twiite==null){
            return 0;
        }else{
            try {
                retwittes = twiite.getList("retwittes", String.class);
                if (retwittes.contains(user.get("username").toString())) {
                    retwittes.remove(user.get("username").toString());
                    result = 1;
                } else {
                    retwittes.add(user.get("username").toString());
                    result = 2;
                }
                Bson filter = eq("_id",  Integer.parseInt(serial));
                Bson update = (set("retwittes", retwittes));
                twitteCollection.updateOne(filter,update);
            }catch (Exception e){
                return 0;
            }
            return result;
        }
    }
}

