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

public class explorerDatabase {
    private static final Logger logger = LogManager.getLogger(explorerDatabase.class);

    ArrayList<String> serialList = new ArrayList<>();

    List<String> mutes = new ArrayList<>();
    List<String> blocks1 = new ArrayList<>();
    List<String> blocks2 = new ArrayList<>();

    public ArrayList<String> get(int AuthKey) {
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
            MongoCollection<Document> reportsCollection =  sampleTrainingDB.getCollection(
                    loadCfg.reportsCollection);
            Document user = userCollection.find(new Document("AuthKey", AuthKey)).first();

            if(user==null){
                logger.error("error in connection with database");
            }else{
                blocks1 =(user.getList("blocks", String.class));
                mutes=(user.getList("mutes", String.class));

            for (Document twitte :  twitterCollection.find()) {
                Document anotherUser = userCollection.find(new Document("username", twitte.get("sender").toString())).first();
                if (anotherUser!=null) {
                    blocks2 = (anotherUser.getList("blocks", String.class));
                    if (!blocks2.contains(user.get("username").toString())
                            && !blocks1.contains(anotherUser.get("username").toString())
                            && !mutes.contains(anotherUser.get("username").toString())) {
                        serialList.add(twitte.get("_id").toString());
                    }
                }
            }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("error in opening database");
        }

            return serialList;
        }

    }

