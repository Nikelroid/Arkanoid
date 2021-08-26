package com.java.category;

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

public class getFollowingsCat {
    private static final Logger logger = LogManager.getLogger(getFollowingsCat.class);

    JSONObject resultJson = new JSONObject();
    com.java.profile.getProfilePic getProfilePic = new getProfilePic();
    public JSONObject get(int AuthKey,String catName) {

        try {
            new categoriesCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of login");
        }


        try {
            MongoClient mongoClient = MongoClients.create();
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase(categoriesCfg.database);
            MongoCollection<Document> userCollection = sampleTrainingDB.getCollection(
                    categoriesCfg.usersCollection);
            Document user = userCollection.find(new Document("AuthKey", AuthKey)).first();
            List<String> followings = user.getList("followings",String.class);

            if(followings==null){
                logger.error("error in connection with database");
                resultJson.put("result","0");
            }else {
                List<String> username = new ArrayList<>();
                List<String> name = new ArrayList<>();
                List<String> pic = new ArrayList<>();
                List<List<String>> categories =
                        (List<List<String>>) user.get("categoiries");
                for (List<String> category:categories) {
                    if (category.get(0).equals(catName)) {
                        for (int i = 0; i < followings.size(); i++) {
                            if (!category.contains(followings.get(i))) {
                                Document followingsOutOfCats = userCollection.find(
                                        new Document("username", followings.get(i))).first();
                                    if (followingsOutOfCats!=null) {
                                        username.add(followingsOutOfCats.getString("username"));
                                        name.add(followingsOutOfCats.getString("name"));
                                        pic.add(getProfilePic.get(followingsOutOfCats.getString("username")));
                                    }
                            }
                        }
                        break;
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

