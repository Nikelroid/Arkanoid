package com.java.groups;

import com.java.profile.getProfilePic;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class getFollowingsGps {
    private static final Logger logger = LogManager.getLogger(getFollowingsGps.class);

    JSONObject resultJson = new JSONObject();
    getMembersGps getMembersGps = new getMembersGps();
    com.java.profile.getProfilePic getProfilePic = new getProfilePic();
    public JSONObject get(int AuthKey,String gpName) {

        JSONObject databaseResult = getMembersGps.get (AuthKey, gpName);
        JSONArray existUsername = databaseResult.getJSONArray("usernames");
        List<String> exUsernames = new ArrayList<>();
        for (int i = 0; i < existUsername.length(); i++) {
            exUsernames.add(existUsername.get(i).toString());
        }


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
            List<String> followings = user.getList("followings",String.class);

            if(user==null){
                logger.error("error in connection with database");
                resultJson.put("result","0");
            }else {
                List<String> username = new ArrayList<>();
                List<String> name = new ArrayList<>();
                List<String> pic = new ArrayList<>();

                for (String usernames:followings) {
                    Document members = userCollection.find(new Document("username", usernames)).first();
                    if (members!=null && !exUsernames.contains(members.getString("username"))) {
                        username.add(members.getString("username"));
                        name.add(members.getString("name"));
                        pic.add(getProfilePic.get(members.getString("username")));

                    }

                }
                System.out.println(username.toString());

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

