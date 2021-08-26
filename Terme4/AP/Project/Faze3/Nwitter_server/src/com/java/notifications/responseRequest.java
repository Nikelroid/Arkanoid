package com.java.notifications;

import com.java.editProfile.editUsername;
import com.java.operation.follow;
import com.java.operation.request;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class responseRequest {
    com.java.operation.follow follow = new follow();
    notificationsSet notificationSet = new notificationsSet();
    JSONObject result = new JSONObject();
    com.java.editProfile.editUsername editUsername = new editUsername();
    private static final Logger logger = LogManager.getLogger(responseRequest.class);

    public JSONObject setter(String type,int AuthKey,String serial) {
        ObjectId id = new ObjectId(serial);
        try {
            new notificationsCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of login");
        }

        try {

            MongoClient mongoClient = MongoClients.create();
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase(notificationsCfg.database);
            MongoCollection<Document> notifsCollection = sampleTrainingDB.getCollection(
                    notificationsCfg.notifsCollection);
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection(
                    notificationsCfg.usersCollection);
            Document me = usersCollection.find(new Document("AuthKey", AuthKey)).first();
            Document request = notifsCollection.find(new Document("_id", id)).first();

             if(me==null || request==null){
                 logger.error("error in opening database");
                 result.put("result",0);
             }else {
                 String userUsername = request.getString("user1");
                 Document user = usersCollection.find(new Document("username", userUsername)).first();
                 if (user == null) {
                     logger.error("error in opening database");
                     result.put("result",0);
                 } else {
                     List<String> followings = user.getList("followings", String.class);
                     List<String> followers = me.getList("followers", String.class);

                     switch (type) {
                         case "accept1" -> {
                             follow.setter(AuthKey + "", userUsername);
                             Document newNotif = new Document("_id", new ObjectId());
                             newNotif.append("user1", me.getString("username")).
                                     append("user2", userUsername).
                                     append("type", 9);
                             notifsCollection.insertOne(newNotif);


                             follow(AuthKey,
                                     usersCollection,
                                     me, userUsername,
                                     user, followings,
                                     followers);
                         }
                         case "accept2" -> {
                             follow(AuthKey,
                                     usersCollection,
                                     me, userUsername,
                                     user, followings,
                                     followers);
                         }
                         case "reject" -> {
                             Document newNotif = new Document("_id", new ObjectId());
                             newNotif.append("user1", me.getString("username")).
                                     append("user2", userUsername).
                                     append("type", 10);
                             notifsCollection.insertOne(newNotif);
                         }
                     }
                     Bson filter = eq("_id", id);
                     notifsCollection.deleteOne(filter);
                 }
             }
            result.put("result",1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error in opening database");
            result.put("result",0);
        }
        return result;
    }

    private void follow(int AuthKey, MongoCollection<Document> usersCollection, Document me, String userUsername, Document user, List<String> followings, List<String> followers) {
        followers.add(user.get("username").toString());
        followings.add(me.get("username").toString());
        notificationSet.setter(user.get("username").toString(),
                me.get("username").toString(), 1);

        usersCollection.updateOne(eq("username", userUsername),
                set("followings", followings));

        usersCollection.updateOne(eq("AuthKey", AuthKey),
                set("followers", followers));
    }
}
