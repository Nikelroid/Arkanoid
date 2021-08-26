package com.java.user;

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

public class userDatabase {
    private static final Logger logger = LogManager.getLogger(userDatabase.class);

    ArrayList<String> serialList = new ArrayList<>();
    List<String> followings = new ArrayList<>();
    List<String> mutes = new ArrayList<>();

    public ArrayList<String> get(int AuthKey) {
        serialList = new ArrayList<>();
        try {
            new editCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of login");
        }


        try {
            MongoClient mongoClient = MongoClients.create();
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase(editCfg.database);
            MongoCollection<Document> userCollection = sampleTrainingDB.getCollection(
                    editCfg.usersCollection);
            MongoCollection<Document> twitterCollection = sampleTrainingDB.getCollection(
                    editCfg.twittesCollection);
            MongoCollection<Document> reportsCollection =  sampleTrainingDB.getCollection(
                    editCfg.reportsCollection);
            Document user = userCollection.find(new Document("AuthKey", AuthKey)).first();

            if(user==null){
                logger.error("error in connection with database");
            }else{
                followings =(user.getList("followings", String.class));
                mutes=(user.getList("mutes", String.class));
            }
            for (Document twitte :  twitterCollection.find()) {
                int reports;

                    Bson filter = eq("serial", twitte.getInteger("_id"));
                    reports = (int) reportsCollection.count(filter);
                List<String> retwitters = twitte.getList("retwittes",String.class);
                boolean isReted = false;

                for (int i = 0; i < retwitters.size(); i++) {
                    if(followings.contains(retwitters.get(i)) && !mutes.contains(retwitters.get(i))){
                        isReted=true;
                        break;
                    }
                }

                    if((followings.contains(twitte.get("sender").toString())||isReted)
                && !mutes.contains(twitte.get("sender").toString())
                && reports< editCfg.reportsNumLimit){
                    serialList.add(twitte.get("_id").toString());
                    }
            }

        }catch (Exception e){
            e.printStackTrace();
            logger.error("error in opening database");
        }

            return serialList;
        }

    }

