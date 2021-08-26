package com.java.operation;

import com.java.database.databaseControl;
import com.java.notifications.notificationsSet;
import com.mongodb.client.MongoCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class remove {
    private static final Logger logger = LogManager.getLogger(remove.class);
    databaseControl dataBaseControl = new databaseControl();
    List<String> followings;
    notificationsSet notificationsSet = new notificationsSet();
    int result = 0;
    public int setter(String AuthKey, String username) {
        try {
            new operateCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of operates");
        }

        MongoCollection<Document> usersCollection = dataBaseControl.getCollection(operateCfg.usersCollection);
        Document me = usersCollection.find(new Document("AuthKey", Integer.parseInt(AuthKey))).first();
        Document user = usersCollection.find(new Document("username", username)).first();
        if(user==null || me==null){

            return 0;
        }else{
            try {
                followings = user.getList("followings", String.class);
                    followings.remove(me.get("username").toString());
                notificationsSet.setter(me.get("username").toString(),
                        user.get("username").toString(),7);
                Bson filter = eq("username",  username);
                Bson update = (set("followings", followings));
                usersCollection.updateOne(filter,update);
                result = 1;
            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
            return result;
        }
    }
}

