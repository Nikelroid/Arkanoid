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

public class getComments {
    private static final Logger logger = LogManager.getLogger(getComments.class);

    ArrayList<String> serialList = new ArrayList<>();
    List<Integer> comments = new ArrayList<>();

    public ArrayList<String> get(int serial) {
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
            Document twitte = twitterCollection.find(new Document("_id", serial)).first();

            if(twitte==null){
                logger.error("error in connection with database");
            }else{
                comments =(twitte.getList("comments", Integer.class));
            }
            for (Integer commentID :  comments) {

                Document myTwitte = twitterCollection.find(new Document("_id", commentID)).first();

                if (myTwitte == null) {
                    logger.warn("myTwitte was null");
                } else {
                    int reports;

                    Bson filter = eq("serial", myTwitte.getInteger("_id"));
                    reports = (int) reportsCollection.count(filter);


                    if (reports < loadCfg.reportsNumLimit) {
                        serialList.add(myTwitte.get("_id").toString());
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

