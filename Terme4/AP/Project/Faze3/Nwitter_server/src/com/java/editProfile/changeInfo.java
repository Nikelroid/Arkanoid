package com.java.editProfile;

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

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class changeInfo {
    JSONObject result = new JSONObject();
    editUsername editUsername = new editUsername();
    private static final Logger logger = LogManager.getLogger(changeInfo.class);

    public int setter(int AuthKey,String type,String newOne) {
        try {
            new editProfileCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of login");
        }


        try {

            MongoClient mongoClient = MongoClients.create();
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase(editProfileCfg.database);
            MongoCollection<Document> userCollection = sampleTrainingDB.getCollection(
                    editProfileCfg.usersCollection);

            Document me = userCollection.find(new Document("AuthKey", AuthKey)).first();


            if (me == null) {
                logger.error("error in connection with database");
                return 0;
            } else {
                if (type.equals("username")){
                    editUsername.setter(AuthKey,newOne);
                }
                Bson filter = eq("AuthKey", AuthKey);
                Bson update = set(type, newOne);
                userCollection.findOneAndUpdate(filter, update);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error in opening database");
        }
        return 1;
    }
}
