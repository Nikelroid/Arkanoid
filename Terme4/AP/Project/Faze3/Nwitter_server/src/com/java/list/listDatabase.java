package com.java.list;

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

public class listDatabase {
    private static final Logger logger = LogManager.getLogger(listDatabase.class);

    ArrayList<String> serialList = new ArrayList<>();
    List<String> followings = new ArrayList<>();
    List<String> mutes = new ArrayList<>();

    public ArrayList<String> get(String type, String serial) {
        serialList = new ArrayList<>();
        try {
            new listCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of login");
        }



            MongoClient mongoClient = MongoClients.create();
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase(listCfg.database);
            MongoCollection<Document> userCollection = sampleTrainingDB.getCollection(
                    listCfg.usersCollection);
            MongoCollection<Document> twitterCollection = sampleTrainingDB.getCollection(
                    listCfg.twittesCollection);

            Document twitte = twitterCollection.find(new Document("_id", serial)).first();

            if (twitte == null) {
                logger.error("error in connection with database");
            } else {





        }

            return serialList;
        }

    }

