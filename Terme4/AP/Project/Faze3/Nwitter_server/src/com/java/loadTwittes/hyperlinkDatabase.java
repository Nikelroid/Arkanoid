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

public class hyperlinkDatabase {
    private static final Logger logger = LogManager.getLogger(hyperlinkDatabase.class);

    ArrayList<String> serialList = new ArrayList<>();

    List<String> followers = new ArrayList<>();
    List<String> blocks = new ArrayList<>();
    boolean isPrivate = true;
    boolean isEnable = true;

    public ArrayList<String> get(int AuthKey,int serial) {
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
            Document user = userCollection.find(new Document("AuthKey", AuthKey)).first();
            Document twitte = twitterCollection.find(new Document("_id", serial)).first();

            if(user==null || twitte==null){
                logger.error("error in connection with database");
                return serialList;
            }else {
                Document sender = userCollection.find(new Document("username", twitte.getString("sender"))).first();
                if (sender == null) {
                    return serialList;
                } else{
                    blocks = (sender.getList("blocks", String.class));
                   followers = (sender.getList("followers", String.class));
                    isPrivate = (sender.getBoolean("account"));
                    isEnable = (sender.getBoolean("enable"));
                    if (user.getString("username").equals(sender.getString("username"))){
                        serialList.add(serial+"");
                        return serialList;
                    }else
                    if (isEnable){
                        if(isPrivate || followers.contains(user.getString("username"))){
                            if (!blocks.contains(user.getString("username"))){
                                serialList.add(serial+"");
                                return serialList;
                            }
                        }
                    }
            }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("error in opening database");
        }
        serialList.add("~");
            return serialList;
        }

    }

