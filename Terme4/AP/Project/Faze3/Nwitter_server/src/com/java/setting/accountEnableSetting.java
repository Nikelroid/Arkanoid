package com.java.setting;

import com.java.editProfile.editProfileCfg;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import java.io.IOException;

import static com.mongodb.client.model.Filters.bitsAnySet;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class accountEnableSetting {
    JSONObject result = new JSONObject();

    private static final Logger logger = LogManager.getLogger(accountEnableSetting.class);

    public int setter(int AuthKey,String type,String value) {
        boolean target = value.equals("true");
        try {
            new settingCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of login");
        }


        try {
            MongoClient mongoClient = MongoClients.create();
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase(settingCfg.database);
            MongoCollection<Document> userCollection = sampleTrainingDB.getCollection(
                    settingCfg.usersCollection);

            Document me = userCollection.find(new Document("AuthKey", AuthKey)).first();


            if (me == null) {
                logger.error("error in connection with database");
                return 0;
            } else {
                Bson filter = eq("AuthKey", AuthKey);
                Bson update = set(type,target);
                userCollection.findOneAndUpdate(filter, update);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error in opening database");
        }
        return 1;
    }
}
