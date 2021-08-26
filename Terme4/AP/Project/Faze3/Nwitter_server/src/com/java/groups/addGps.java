package com.java.groups;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class addGps {
    private static final Logger logger = LogManager.getLogger(addGps.class);

    JSONObject resultJson = new JSONObject();

    public JSONObject get(int AuthKey,String gpName) {
        


        try {
            new groupsCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of login");
        }


        try {
            MongoClient mongoClient = MongoClients.create();
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase(groupsCfg.database);
            MongoCollection<Document> userCollection = sampleTrainingDB.getCollection(
                    groupsCfg.usersCollection);
            Document user = userCollection.find(new Document("AuthKey", AuthKey)).first();


            if(user==null){
                logger.error("error in connection with database");
                resultJson.put("result","0");
            }else {

                List<List<String>> groups = (List<List<String>>) user.get("groups");
                List<String> newGp = new ArrayList<>();
                newGp.add(new ObjectId().toString());
                newGp.add(gpName);
                groups.add(newGp);
                userCollection.findOneAndUpdate(eq("AuthKey",AuthKey)
                        , set("groups", groups));
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

