package com.java.category;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class deleteCat {
    private static final Logger logger = LogManager.getLogger(addCat.class);

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

                List<List<String>> categories = (List<List<String>>) user.get("categoiries");

                for (int i = 0; i < categories.size(); i++)
                    if (categories.get(i).get(0).equals(catName)) {
                        categories.remove(i);
                        break;
                    }
                userCollection.findOneAndUpdate(eq("AuthKey",AuthKey)
                        , set("categoiries", categories));
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

