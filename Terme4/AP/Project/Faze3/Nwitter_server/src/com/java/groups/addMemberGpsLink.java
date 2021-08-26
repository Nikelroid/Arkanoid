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

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class addMemberGpsLink {
    private static final Logger logger = LogManager.getLogger(addMemberGpsLink.class);

    JSONObject resultJson = new JSONObject();

    public JSONObject get(int AuthKey,String gpId) {

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


            if(me==null){
                logger.error("error in connection with database");
                resultJson.put("result","0");
            }else {
                String name = null;
                for (Document user:userCollection.find()) {
                    List<List<String>> userGps = (List<List<String>>) user.get("groups");
                    for (int i = 0; i < userGps.size(); i++) {
                        if (userGps.get(i).get(0).equals(gpId)) {
                            name = userGps.get(i).get(1);
                            break;
                        }
                    }
                    if (name!=null)break;
                }
                if(name==null){
                    resultJson.put("result","3");
                    return resultJson;
                }
                List<List<String>> myGps = (List<List<String>>) me.get("groups");

                for (int i = 0; i < myGps.size(); i++) {
                    if (myGps.get(i).get(0).equals(gpId)) {
                        resultJson.put("result","2").put("name",name);
                        return resultJson;

                    }
                }
                List<String> newGp = new ArrayList<>();
                newGp.add(gpId);
                newGp.add(name);
                myGps.add(newGp);

                userCollection.findOneAndUpdate(eq("AuthKey",AuthKey)
                        , set("groups", myGps));
                resultJson.put("result","1").put("name",name);


            }


        }catch (Exception e){
            e.printStackTrace();
            logger.error("error in opening database");
            resultJson.put("result","0");
        }

            return resultJson;
        }

    }

