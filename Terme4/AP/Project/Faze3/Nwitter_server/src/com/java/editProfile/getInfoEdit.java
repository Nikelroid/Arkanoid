package com.java.editProfile;

import com.java.user.editCfg;
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

public class getInfoEdit {
    JSONObject result = new JSONObject();

    private static final Logger logger = LogManager.getLogger(getInfoEdit.class);

    public JSONObject get(int myAuthKey) {
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
            MongoCollection<Document> notifsCollection = sampleTrainingDB.getCollection(
                    editProfileCfg.notifsCollection);

            Document me = userCollection.find(new Document("AuthKey", myAuthKey)).first();


            if (me == null) {
                logger.error("error in connection with database");
                result.put("result","0");
                return result;
            } else {


                result.put("username", me.get("username").toString())
                        .put("password", me.getString("password"))
                        .put("name", me.getString("name"))
                        .put("bio", me.getString("bio"))
                        .put("birthday", me.get("birthday").toString())
                        .put("email", me.get("email").toString())
                        .put("phonenumber", me.get("phonenumber").toString())
                .put("result","1");

            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error in opening database");
        }
        return result;
    }
}
