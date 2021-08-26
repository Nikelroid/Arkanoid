package com.java.operation;

import com.java.database.databaseControl;
import com.java.notifications.notificationsRemove;
import com.java.notifications.notificationsSet;
import com.mongodb.client.MongoCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class request {
    private static final Logger logger = LogManager.getLogger(request.class);
    databaseControl dataBaseControl = new databaseControl();
    com.java.notifications.notificationsSet notificationsSet = new notificationsSet();
    com.java.notifications.notificationsRemove notificationsRemove = new notificationsRemove();
    List<String> blocks;
    int result = 0;
    public int setter(String AuthKey, String username) {
        try {
            new operateCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of operates");
        }

        MongoCollection<Document> usersCollection = dataBaseControl.getCollection(operateCfg.usersCollection);
        MongoCollection<Document> notifsCollection = dataBaseControl.getCollection(operateCfg.notifsCollection);

        Document me = usersCollection.find(new Document("AuthKey", Integer.parseInt(AuthKey))).first();
        Document user = usersCollection.find(new Document("username", username)).first();
        Document myReq;
        try {
            if(user==null || me==null){
            return 0;
        }else {
                myReq = new Document();
                myReq.append("type", 8).append("user1", me.get("username")).append("user2", user.get("username"));
                Document request = notifsCollection.find(myReq).first();

                if (request == null) {
                    Document newRequest = new Document("_id", new ObjectId());
                    newRequest.append("type", 8).
                            append("user1", me.get("username")).
                            append("user2", user.get("username"));
                    notifsCollection.insertOne(newRequest);
                    notificationsSet.setter(me.get("username").toString(),
                            user.get("username").toString(),8);
                } else {
                    notificationsRemove.setter(me.get("username").toString(),
                            user.get("username").toString(),8);
                    notifsCollection.findOneAndDelete(myReq);
                }
            }

            }catch (Exception e){
                return 0;
            }
            return result;
        }
    }


