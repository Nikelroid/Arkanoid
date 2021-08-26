package com.java.loadTwittes;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class savedDatabase {
    private static final Logger logger = LogManager.getLogger(savedDatabase.class);

    ArrayList<String> serialList = new ArrayList<>();

    List<Integer> savedTwittes = new ArrayList<>();

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
                savedTwittes =(user.getList("twittesaved",
                        Integer.class));

                for (int i = 0; i < savedTwittes.size(); i++) {
                    serialList.add(String.valueOf(savedTwittes.get(i)));
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("error in opening database");
        }

            return serialList;
        }

    }

