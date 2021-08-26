package com.java.operation;

import com.java.database.databaseControl;
import com.mongodb.client.MongoCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class submitComment {
    private static final Logger logger = LogManager.getLogger(submitComment.class);
    databaseControl dataBaseControl = new databaseControl();
    List<String> likes ;
    int result = 0;
    public int adder(String AuthKey, String text,String serial) {
        try {
            new operateCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of operates");
        }

        MongoCollection<Document> twitteCollection = dataBaseControl.getCollection(operateCfg.twittesCollection);
        int ID = 0;
        for (Document twitte:twitteCollection.find()) {
            ID = twitte.getInteger("_id");
        }

        ID++;

        MongoCollection<Document> userCollection = dataBaseControl.getCollection(operateCfg.usersCollection);
        Document user = userCollection.find(new Document("AuthKey", Integer.parseInt(AuthKey))).first();

        ArrayList<String> likes = new ArrayList<>(Collections.singleton("likes:"));
        ArrayList<Integer> comments = new ArrayList<>(Collections.singleton(0));
        ArrayList<String> retwittes = new ArrayList<>(Collections.singleton("retwittes:"));



        if(user!=null){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            Document newTwitte = new Document("_id", ID);
            newTwitte.append("text", text).
                    append("sender", user.get("username").toString()).
                    append("likes", likes).
                    append("comments", comments).
                    append("retwittes", retwittes).
                    append("time", dtf.format(now));
                    twitteCollection.insertOne(newTwitte);
                    result = ID;

            Document twitte = twitteCollection.find(new Document("_id", Integer.parseInt(serial))).first();
            List<Integer> twitteComments = twitte.getList("comments",Integer.class);
            twitteComments.add(ID);
            Bson filter = eq("_id", Integer.parseInt(serial));
            Bson update = set("comments",twitteComments);
            twitteCollection.findOneAndUpdate(filter, update);

        }
        return result;
    }
}

