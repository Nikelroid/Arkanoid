package com.java.category;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class categoriesDatabase {
    private static final Logger logger = LogManager.getLogger(categoriesDatabase.class);

    JSONObject resultJson = new JSONObject();

    public JSONObject get(int AuthKey) {

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
                List<String> categoryList = new ArrayList<>();
                List<Integer> categoryCounts = new ArrayList<>();
                List<List<String>> categories = (List<List<String>>) user.get("categoiries");
                for (List<String> category:categories) {
                    categoryList.add(category.get(0));
                    categoryCounts.add(category.size()-1);
                }
                resultJson.put("result","1");
                resultJson.put("names",categoryList);
                resultJson.put("counts",categoryCounts);
            }


        }catch (Exception e){
            e.printStackTrace();
            logger.error("error in opening database");
            resultJson.put("result","0");
        }

            return resultJson;
        }

    }

