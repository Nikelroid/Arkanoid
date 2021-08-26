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

public class followers {
    private static final Logger logger = LogManager.getLogger(followers.class);

    ArrayList<String> serialList = new ArrayList<>();

    public ArrayList<String> get(String username) {
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

            Document user = userCollection.find(new Document("username", username)).first();


            if(user==null){
                logger.error("error in connection with database");
            }else {
                List<String> followings = user.getList("followings",String.class);
                for (String following:followings) {
                    Document followingUser = userCollection.find(new Document("username", following)).first();
                    if (followingUser!=null)
                    serialList.add(followingUser.get("_id").toString());
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("error in opening database");
        }

            return serialList;
        }

    }

