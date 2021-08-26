package com.java.messenger;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class deleteChat {
    private static final Logger logger = LogManager.getLogger(deleteChat.class);

    JSONObject resultJson = new JSONObject();

    public JSONObject delete(int AuthKey,String anotherUsername) {

        try {
            new messengerCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of login");
        }


        try {
            MongoClient mongoClient = MongoClients.create();
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase(messengerCfg.database);
            MongoCollection<Document> userCollection = sampleTrainingDB.getCollection(
                    messengerCfg.usersCollection);
            MongoCollection<Document> messagesCollection = sampleTrainingDB.getCollection(
                    messengerCfg.messagesCollection);
            Document user = userCollection.find(new Document("AuthKey", AuthKey)).first();

            if(user==null){
                logger.error("error in connection with database");
                resultJson.put("result","0");
            }else {
                String username = user.getString("username");


                for (Document message:messagesCollection.find()) {
                    if ((message.get("sender").toString().equals(anotherUsername)
                    && message.get("receiver").toString().equals(username)) ||
                    (message.get("receiver").toString().equals(anotherUsername)
                            && message.get("sender").toString().equals(username))){
                        int id = message.getInteger("_id");
                        messagesCollection.deleteMany(eq("_id",id));
                    }

                }
                resultJson.put("result","1");
            }

        }catch (Exception e){
            e.printStackTrace();
            logger.error("error in opening database");
            resultJson.put("result","0");
        }

            return resultJson;
        }

    }

