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
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class addMemberGps {
    private static final Logger logger = LogManager.getLogger(addMemberGps.class);

    JSONObject resultJson = new JSONObject();

    public JSONObject get(int AuthKey,String gpName,String username) {

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
            Document me = userCollection.find(new Document("AuthKey", AuthKey)).first();
            Document user = userCollection.find(new Document("username", username)).first();

            if(user==null || me==null){
                logger.error("error in connection with database");
                resultJson.put("result","0");
            }else {
                List<List<String>> myGps = (List<List<String>>) me.get("groups");
                List<List<String>> userGps = (List<List<String>>) user.get("groups");
                String id = null;
                for (int i = 0; i < myGps.size(); i++) {
                    if (myGps.get(i).get(1).equals(gpName)) {
                        id = myGps.get(i).get(0);
                        break;
                    }
                }
                List<String> newGp = new ArrayList<>();
                newGp.add(id);
                newGp.add(gpName);
                userGps.add(newGp);

                userCollection.findOneAndUpdate(eq("username",username)
                        , set("groups", userGps));
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

