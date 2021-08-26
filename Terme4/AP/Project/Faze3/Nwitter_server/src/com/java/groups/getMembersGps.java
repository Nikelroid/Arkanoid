package com.java.groups;

import com.java.profile.getProfilePic;
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

public class getMembersGps {
    private static final Logger logger = LogManager.getLogger(getMembersGps.class);
    com.java.profile.getProfilePic getProfilePic = new getProfilePic();
    JSONObject resultJson = new JSONObject();

    public JSONObject get(int AuthKey,String gpName) {

        try {
            new groupsCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of login");
        }

        List<String> username = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<String> pic = new ArrayList<>();

        try {
            MongoClient mongoClient = MongoClients.create();
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase(groupsCfg.database);
            MongoCollection<Document> userCollection = sampleTrainingDB.getCollection(
                    groupsCfg.usersCollection);
            Document user = userCollection.find(new Document("AuthKey", AuthKey)).first();

            String gpId = null;

            if(user==null){
                logger.error("error in connection with database");
                resultJson.put("result","0");
            }else {

                List<List<String>> groups = (List<List<String>>) user.get("groups");
                for (int i = 0; i < groups.size(); i++) {
                    if (groups.get(i).get(1).equals(gpName))
                        gpId = groups.get(i).get(0);
                }
                for (Document member:userCollection.find()) {
                    List<List<String>> memberGp = (List<List<String>>) member.get("groups");
                    for (int i = 0; i < memberGp.size(); i++) {
                        if (memberGp.get(i).get(0).equals(gpId)){
                            username.add(member.getString("username"));
                            name.add(member.getString("name"));
                            pic.add(getProfilePic.get(member.getString("username")));
                            break;
                        }
                    }
                }

                resultJson.put("result","1");
                resultJson.put("usernames",username);
                resultJson.put("names",name);
                resultJson.put("pic",pic);
            }


        }catch (Exception e){
            e.printStackTrace();
            logger.error("error in opening database");
            resultJson.put("result","0");
        }

            return resultJson;
        }

    }

