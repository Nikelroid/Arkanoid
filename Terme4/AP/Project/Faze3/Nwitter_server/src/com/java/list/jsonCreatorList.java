package com.java.list;

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


public class jsonCreatorList {
    JSONObject jsonOutput = new JSONObject();
    MongoCollection<Document> usersCollection;
    getProfilePic getProfilePic = new getProfilePic();

    private static final Logger logger = LogManager.getLogger(jsonCreatorList.class);

    public JSONObject creator(ArrayList<String> serialOutput) throws IOException {
        try {
            new listCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of list");
        }
        try {
            MongoClient mongoClient = MongoClients.create();
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase(listCfg.database);
            usersCollection = sampleTrainingDB.getCollection(
                    listCfg.usersCollection);
        } catch (Exception e) {
            logger.error("couldn't open database of load");
        }


        ArrayList<String> username = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> serial = new ArrayList<>();
        ArrayList<String> pic = new ArrayList<>();

        for (int i = serialOutput.size() - 1; i >= 0; i--) {
            Document user = usersCollection.find(new Document("_id", Integer.parseInt(serialOutput.get(i)))).first();

            if (user == null) {
                logger.warn("error maybe in connection with database");
                jsonOutput.put("key", "list").
                        put("result", "0");
                return jsonOutput;
            } else {
                username.add(user.getString("username"));
                name.add(user.getString("name"));
                pic.add(getProfilePic.get(user.getString("username")));
            }
        }



        logger.info("load json crated");
        jsonOutput = new JSONObject();
        jsonOutput.put("key", "list").
                put("result", "1").
                put("username", username).
                put("name", name).
                put("serial", serial).
                put("pic", pic);

        return jsonOutput;
    }
}
