package com.java.notifications;

import com.java.editProfile.editUsername;
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

import static com.mongodb.client.model.Filters.eq;

public class clearNotifs {
    JSONObject result = new JSONObject();
    com.java.editProfile.editUsername editUsername = new editUsername();
    private static final Logger logger = LogManager.getLogger(clearNotifs.class);

    public JSONObject setter(int AuthKey) {
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
            Document user = usersCollection.find(new Document("AuthKey", AuthKey)).first();
            if (user==null){
                logger.error("error in opening database");
                result.put("result",0);
            }else{
                String username = user.getString("username");
                Bson filter = eq("user2", username);
                notifsCollection.deleteMany(filter);
                result.put("result",1);
            }


        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error in opening database");
            result.put("result",0);
        }
        return result;
    }
}
