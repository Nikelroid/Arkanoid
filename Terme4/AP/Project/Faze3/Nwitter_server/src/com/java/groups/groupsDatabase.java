package com.java.groups;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class groupsDatabase {
    private static final Logger logger = LogManager.getLogger(groupsDatabase.class);

    JSONObject resultJson = new JSONObject();

    public JSONObject get(int AuthKey) {

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
                List<String> groupNameList = new ArrayList<>();
                List<String> groupCountList = new ArrayList<>();
                List<List<String>> groups = (List<List<String>>) user.get("groups");
                for (List<String> group:groups) {
                    groupNameList.add(group.get(1));
                    groupCountList.add(group.get(0));
                }

                resultJson.put("names",groupNameList);
                resultJson.put("id",groupCountList);
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

