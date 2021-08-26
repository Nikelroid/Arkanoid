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

public class saveDatabase {
    private static final Logger logger = LogManager.getLogger(saveDatabase.class);
    databaseControl dataBaseControl = new databaseControl();
    List<Integer> saved;

    int result = 0;
    public int setter(String AuthKey, String serial) {
        try {
            new operateCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of operates");
        }

        MongoCollection<Document> usersCollection = dataBaseControl.getCollection(operateCfg.usersCollection);
        Document user = usersCollection.find(new Document("AuthKey", Integer.parseInt(AuthKey))).first();

        if(user==null ){
            return 0;
        }else{
            try {
                saved = user.getList("twittesaved", Integer.class);

                if (saved.contains(Integer.parseInt(serial))){
                    saved.remove(Integer.valueOf(Integer.parseInt(serial)));
                    result = 1;
                } else {
                    saved.add(Integer.parseInt(serial));
                    result = 2;
                }
                Bson filter = eq("AuthKey",  Integer.parseInt(AuthKey));
                Bson update = (set("twittesaved", saved));
                usersCollection.updateOne(filter,update);
            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
            return result;
        }
    }
}

