package com.java.user;

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

public class jsonCreatorUser {
    JSONObject jsonOutput = new JSONObject();
    MongoCollection<Document> twitterCollection;
    MongoCollection<Document> usersCollection;
    List<Integer> saved = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(jsonCreatorUser.class);

    public JSONObject creator(ArrayList<String> serialOutput, int AuthKey) {
        try {
            new editCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of load");
        }
        try {
            MongoClient mongoClient = MongoClients.create();
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase(editCfg.database);
            usersCollection = sampleTrainingDB.getCollection(
                    editCfg.usersCollection);
            twitterCollection = sampleTrainingDB.getCollection(
                    editCfg.twittesCollection);
        } catch (Exception e) {
            logger.error("couldn't open database of load");
        }

        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<String> text = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<Integer> serials = new ArrayList<>();
        ArrayList<Integer> likes = new ArrayList<>();
        ArrayList<Integer> comments = new ArrayList<>();
        ArrayList<Integer> retwittes = new ArrayList<>();
        ArrayList<Boolean> isSaved = new ArrayList<>();
        ArrayList<Boolean> isLiked = new ArrayList<>();
        ArrayList<Boolean> isRetwitted = new ArrayList<>();
        Document user = usersCollection.find(new Document("AuthKey", AuthKey)).first();

        if (user == null) {
            logger.error("error in connection with database");
        } else {
            saved = (user.getList("twittesaved", Integer.class));
            if (twitterCollection == null)
                logger.error("error in loading database");
            else
                for (int i = serialOutput.size() - 1; i >= 0; i--) {
                    Document twitte = twitterCollection.find(new Document("_id",
                            Integer.parseInt(serialOutput.get(i)))).first();
                    usernames.add(twitte.getString("sender"));
                    text.add(twitte.getString("text"));
                    date.add(twitte.getString("time"));
                    serials.add(twitte.getInteger("_id"));
                    likes.add(twitte.getList("likes", String.class).size());
                    comments.add(twitte.getList("comments", Integer.class).size());
                    retwittes.add(twitte.getList("retwittes", String.class).size());
                    if (saved.contains(twitte.getInteger("_id")))
                        isSaved.add(true);
                    else
                        isSaved.add(false);
                    if(twitte.getList("likes", String.class).
                            contains(user.getString("username")))
                        isLiked.add(true);
                    else
                        isLiked.add(false);
                    if(twitte.getList("retwittes", String.class).
                            contains(user.getString("username")))
                        isRetwitted.add(true);
                    else
                        isRetwitted.add(false);
                }


            logger.info("load json crated");
            jsonOutput = new JSONObject();
            jsonOutput.put("key", "load").
                    put("usernames", usernames).
                    put("text", text).
                    put("date", date).
                    put("serials", serials).
                    put("likes", likes).
                    put("comments", comments).
                    put("retwittes", retwittes).
                    put("isSaved", isSaved).
                    put("isLiked", isLiked).
                    put("isRetwitted", isRetwitted);
        }
        return jsonOutput;
    }
}
