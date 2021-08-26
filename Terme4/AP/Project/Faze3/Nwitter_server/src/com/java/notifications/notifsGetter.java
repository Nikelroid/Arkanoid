package com.java.notifications;

import com.java.profile.getProfilePic;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class notifsGetter {
    private static final Logger logger = LogManager.getLogger(notifsGetter.class);

    com.java.profile.getProfilePic getProfilePic = new getProfilePic();
    List<String> user1 = new ArrayList<>();
    List<String> user2 = new ArrayList<>();
    List<Integer> type = new ArrayList<>();
    List<String> id = new ArrayList<>();
    List<String> usersProfile = new ArrayList<>();
    List<String> pics = new ArrayList<>();

    JSONObject resultJson = new JSONObject();

    public JSONObject get(int AuthKey) {

        try {
            new notificationsCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of login");
        }


        try {
            MongoClient mongoClient = MongoClients.create();
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase(notificationsCfg.database);
            MongoCollection<Document> userCollection = sampleTrainingDB.getCollection(
                    notificationsCfg.usersCollection);
            MongoCollection<Document> notifsCollection = sampleTrainingDB.getCollection(
                    notificationsCfg.notifsCollection);
            Document user = userCollection.find(new Document("AuthKey", AuthKey)).first();

            if(user==null){
                logger.error("error in connection with database");
                resultJson.put("result","0");
            }else {
                user1 = new ArrayList<>();
                user2 = new ArrayList<>();
                id = new ArrayList<>();
                type = new ArrayList<>();
                String username = user.getString("username");
                for (Document notif:notifsCollection.find()) {
                    if(notif.getString("user1").equals(username)
                    || notif.getString("user2").equals(username)){
                        user1.add(notif.getString("user1"));
                        user2.add(notif.getString("user2"));
                        id.add(notif.getObjectId("_id").toString());
                        type.add(notif.getInteger("type"));

                        if (notif.get("user1").toString().equals(username)){
                            if (!usersProfile.contains(notif.get("user2").toString()))
                                usersProfile.add(notif.get("user2").toString());
                        }else {
                            if (!usersProfile.contains(notif.get("user1").toString()))
                                usersProfile.add(notif.get("user1").toString());
                        }
                    }
                }

                for (int i = 0; i < usersProfile.size(); i++) {
                    pics.add(getProfilePic.get(usersProfile.get(i)));
                }

            }
            resultJson.put("result",1)
                    .put("user1",user1)
                    .put("user2",user2)
                    .put("type",type)
                    .put("_id",id)
                    .put("profile",usersProfile)
                    .put("profile_pics",pics);



        }catch (Exception e){
            e.printStackTrace();
            logger.error("error in opening database");
            resultJson.put("result","0");
        }

            return resultJson;
        }

    }

