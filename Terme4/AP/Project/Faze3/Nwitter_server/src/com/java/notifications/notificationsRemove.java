package com.java.notifications;

import com.java.editProfile.editUsername;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONObject;

import java.io.IOException;

import static com.mongodb.client.model.Filters.eq;

public class notificationsRemove {
    JSONObject result = new JSONObject();
    com.java.editProfile.editUsername editUsername = new editUsername();
    private static final Logger logger = LogManager.getLogger(notificationsRemove.class);

    public int setter(String user1,String user2,int type) {
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

                Bson filter1 = eq("user1", user1);
                Bson filter2 = eq("user2", user2);
                Bson filter3 = eq("type", type);
                Bson filters = Filters.and(filter1,filter2,filter3);
                notifsCollection.deleteOne(filters);



        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error in opening database");
        }
        return 1;
    }
}
