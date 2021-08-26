package com.java.botRunning;

import com.java.messenger.messengerCfg;
import com.java.profile.getMessagePic;
import com.java.profile.getProfilePic;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class getMessagesBot {

    private static final Logger logger = LogManager.getLogger(getMessagesBot.class);
    com.java.profile.getProfilePic getProfilePic = new getProfilePic();
    JSONObject resultJson = new JSONObject();
    getMessagePic getMessagePic = new getMessagePic();
    public JSONObject get(String username) {

        setMessagesBot.messageLock.lock();
        try {


            try {
                new botRunningCfg();
            } catch (IOException e) {
                logger.error("couldn't open config of login");
            }


            try {
                MongoClient mongoClient = MongoClients.create();
                MongoDatabase sampleTrainingDB = mongoClient.getDatabase(botRunningCfg.database);
                MongoCollection<Document> userCollection = sampleTrainingDB.getCollection(
                        botRunningCfg.usersCollection);
                MongoCollection<Document> messagesCollection = sampleTrainingDB.getCollection(
                        botRunningCfg.messagesCollection);
                Document user = userCollection.find(new Document("username", username)).first();
                List<String> groupNames = new ArrayList<>();
                List<List<String>> groups = (List<List<String>>) user.get("groups");
                for (List<String> group : groups) {
                    groupNames.add("*" + group.get(1));
                }
                if (user == null) {
                    logger.error("error in connection with database");
                    resultJson.put("result", "0");
                } else {


                    List<Integer> _id = new ArrayList<>();
                    List<String> text = new ArrayList<>();
                    List<String> sender = new ArrayList<>();
                    List<String> receiver = new ArrayList<>();
                    List<String> time = new ArrayList<>();
                    List<Boolean> seen = new ArrayList<>();

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                    for (Document message : messagesCollection.find()) {

                        Date messageTime = null;
                        Date currentTime = null;
                        try {
                            messageTime = sdf.parse(message.get("time").toString());
                            currentTime = sdf.parse(dtf.format(now));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        assert messageTime != null;
                        assert currentTime != null;
                        if ((message.get("sender").toString().equals(username)
                                || message.get("receiver").toString().equals(username)
                                || groupNames.contains(message.get("receiver").toString()))
                                && (messageTime.getTime() < currentTime.getTime())) {


                            int id = message.getInteger("_id");
                            _id.add(Integer.parseInt(message.get("_id").toString()));
                            text.add(message.get("text").toString());
                            sender.add(message.get("sender").toString());
                            receiver.add(message.get("receiver").toString());
                            time.add(message.get("time").toString());
                            seen.add(message.getBoolean("seen"));


                            if (!message.get("sender").toString().equals(username)) {
                                Bson filter = eq("_id", id);
                                Bson update1 = set("delivered", true);
                                Bson update2 = set("seen", true);
                                Bson updates = combine(update1, update2);
                                messagesCollection.updateMany(filter, updates);
                            }
                            if (message.get("sender").toString().equals(username) &&
                                    message.get("receiver").toString().charAt(0) != '*') {

                            } else if (message.get("receiver").toString().equals(username)) {

                            }
                        }

                    }


                    List<Integer> orderedId = new ArrayList<>();
                    List<String> orderedText = new ArrayList<>();
                    List<String> orderedSender = new ArrayList<>();
                    List<String> orderedReceiver = new ArrayList<>();
                    List<String> orderedTime = new ArrayList<>();
                    List<Boolean> orderedSeen = new ArrayList<>();
                    List<Boolean> orderedDelivered = new ArrayList<>();
                    List<Boolean> orderedSent = new ArrayList<>();

                    List<String> orderedMessagePics = new ArrayList<>();

                    for (int i = 0; i < _id.size(); i++) {
                        boolean exported = false;
                        for (int j = 0; j < orderedId.size(); j++) {

                            Date messageJ = null;
                            Date messageI = null;
                            try {
                                messageJ = sdf.parse(orderedTime.get(j));
                                messageI = sdf.parse(time.get(i));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            assert messageJ != null;
                            assert messageI != null;
                            if (messageI.getTime() < messageJ.getTime()) {

                                orderedId.add(j, _id.get(i));
                                orderedText.add(j, text.get(i));
                                orderedSender.add(j, sender.get(i));
                                orderedReceiver.add(j, receiver.get(i));
                                orderedTime.add(j, time.get(i));
                                orderedSeen.add(j, seen.get(i));

                                exported = true;
                                break;
                            }

                        }
                        if (!exported) {
                            orderedId.add(_id.get(i));
                            orderedText.add(text.get(i));
                            orderedSender.add(sender.get(i));
                            orderedReceiver.add(receiver.get(i));
                            orderedTime.add(time.get(i));
                            orderedSeen.add(seen.get(i));
                        }
                    }

                    resultJson.put("result", "1")
                            .put("_id", orderedId)
                            .put("text", orderedText)
                            .put("sender", orderedSender)
                            .put("receiver", orderedReceiver)
                            .put("seen", orderedSeen);
                }


            } catch (Exception e) {
                e.printStackTrace();
                logger.error("error in opening database");
                resultJson.put("result", "0");
            }
        }finally {
            setMessagesBot.messageLock.unlock();
        }
            return resultJson;
        }

    }

