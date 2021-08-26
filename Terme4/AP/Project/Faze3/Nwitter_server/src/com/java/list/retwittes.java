package com.java.list;

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

public class retwittes {
    private static final Logger logger = LogManager.getLogger(retwittes.class);

    ArrayList<String> serialList = new ArrayList<>();

    public ArrayList<String> get(String serial) {
        serialList = new ArrayList<>();
        try {
            new listCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of login");
        }


        try {
            MongoClient mongoClient = MongoClients.create();
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase(listCfg.database);

            MongoCollection<Document> userCollection = sampleTrainingDB.getCollection(
                    listCfg.usersCollection);
            MongoCollection<Document> twitterCollection = sampleTrainingDB.getCollection(
                    listCfg.twittesCollection);

                    Document twitte = twitterCollection.find(new Document("_id", Integer.parseInt(serial))).first();

            if(twitte==null){
                logger.error("error in connection with database");
            }else {
                List<String> likes = twitte.getList("retwittes",String.class);

                for (String username:likes) {
                    Document user = userCollection.find(new Document("username", username)).first();
                    if(user!=null)
                        serialList.add(user.getInteger("_id")+"");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("error in opening database");
        }

            return serialList;
        }

    }

