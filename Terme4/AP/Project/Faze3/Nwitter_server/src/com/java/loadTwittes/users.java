package com.java.loadTwittes;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class users {
    private static final Logger logger = LogManager.getLogger(users.class);

    ArrayList<String> serialList = new ArrayList<>();
    List<Integer> comments = new ArrayList<>();

    public ArrayList<String> get(boolean self, String username) {
        serialList = new ArrayList<>();
        try {
            new loadCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of login");
        }


        try {
            MongoClient mongoClient = MongoClients.create();
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase(loadCfg.database);
            MongoCollection<Document> userCollection = sampleTrainingDB.getCollection(
                    loadCfg.usersCollection);
            MongoCollection<Document> twitterCollection = sampleTrainingDB.getCollection(
                    loadCfg.twittesCollection);
            MongoCollection<Document> reportsCollection = sampleTrainingDB.getCollection(
                    loadCfg.reportsCollection);
            Document user ;
            if (self) {
                user = userCollection.find(new Document("AuthKey", Integer.parseInt(username))).first();
            }else {
                user = userCollection.find(new Document("username", username)).first();
            }
            if (user == null) {
                logger.error("error in connection with database");
            } else {

                    for (Document twitte : twitterCollection.find()) {
                        List<String> reTwittes = twitte.getList("retwittes", String.class);
                        if (twitte.get("sender").equals(user.get("username").toString())
                                ||reTwittes.contains(user.get("username").toString())){

                            serialList.add(twitte.get("_id").toString());
                        }
                    }
                }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("error in opening database");
                }

        return serialList;
    }

}

