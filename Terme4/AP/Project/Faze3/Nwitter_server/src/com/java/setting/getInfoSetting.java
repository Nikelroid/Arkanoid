package com.java.setting;

import com.java.editProfile.editProfileCfg;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.json.JSONObject;

import java.io.IOException;

public class getInfoSetting {
    JSONObject result = new JSONObject();

    private static final Logger logger = LogManager.getLogger(getInfoSetting.class);

    public JSONObject get(int myAuthKey) {
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

            Document me = userCollection.find(new Document("AuthKey", myAuthKey)).first();


            if (me == null) {
                logger.error("error in connection with database");
                result.put("result","0");
                return result;
            } else {


                result.put("privacy", me.getList("privacy",Integer.class))
                        .put("account", me.getBoolean("account"))
                        .put("enable", me.getBoolean("enable"))
                        .put("result",1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error in opening database");
        }
        return result;
    }
}
