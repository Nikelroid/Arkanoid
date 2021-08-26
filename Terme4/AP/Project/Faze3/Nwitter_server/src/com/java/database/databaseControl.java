package com.java.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.io.IOException;

public class databaseControl {
    private static MongoClient mongoClient;
    private static MongoDatabase sampleTrainingDB;
    private static MongoCollection<Document> result;
    String database;
    String collection;
    private static final Logger logger = LogManager.getLogger(databaseControl.class);
    public MongoCollection<Document> getCollection(String collection) {
        this.collection = collection;
    try {
        new databaseCfg();
    } catch (
    IOException e) {
        e.printStackTrace();
        logger.error("error in loading login cfg");
    }
        if (databaseCfg.database==null){
        database = "Nwitter";
    }else{
            database = databaseCfg.database ;
    }

        try {
            mongoClient = MongoClients.create();
            sampleTrainingDB = mongoClient.getDatabase(database);
            result = sampleTrainingDB.getCollection(collection);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("error in opening database");
        }

    return result;
    }
}

