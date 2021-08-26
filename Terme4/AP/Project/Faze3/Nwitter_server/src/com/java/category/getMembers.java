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

public class getMembers {
    com.java.profile.getProfilePic getProfilePic = new getProfilePic();
    private static final Logger logger = LogManager.getLogger(getMembers.class);

    JSONObject resultJson = new JSONObject();

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


            if(user==null){
                logger.error("error in connection with database");
                resultJson.put("result","0");
            }else {
                List<String> username = new ArrayList<>();
                List<String> name = new ArrayList<>();
                List<String> pic = new ArrayList<>();
                List<List<String>> categories = (List<List<String>>) user.get("categoiries");
                for (List<String> category:categories) {
                    if (category.get(0).equals(catName)) {
                        for (int i = 1; i < category.size(); i++) {
                            Document catMember = userCollection.find(
                                    new Document("username", category.get(i))).first();
                            username.add(catMember.getString("username"));
                            name.add(catMember.getString("name"));
                            pic.add(getProfilePic.get(catMember.getString("username")));
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

