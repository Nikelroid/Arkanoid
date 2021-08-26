package com.java.messenger;

import com.java.botRunning.getMessagesBot;
import com.java.botRunning.setMessagesBot;
import com.java.profile.setMessagePic;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.BSON;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class setMessages {

    JSONArray localId = new JSONArray();
    JSONArray text = new JSONArray();
    JSONArray sender = new JSONArray();
    JSONArray receiver = new JSONArray();
    JSONArray time = new JSONArray();
    JSONArray isSent = new JSONArray();
    JSONArray isDelivered = new JSONArray();
    JSONArray isSeen = new JSONArray();

    private static final Logger logger = LogManager.getLogger(setMessages.class);

    JSONObject resultJson = new JSONObject();
    setMessagePic setMessagePic = new setMessagePic();
    public JSONObject setter(JSONObject jsonInput) {

        setMessagesBot.messageLock.lock();
        try {

            localId = jsonInput.getJSONArray("localId");
            text = jsonInput.getJSONArray("text");
            sender = jsonInput.getJSONArray("sender");
            receiver = jsonInput.getJSONArray("receiver");
            time = jsonInput.getJSONArray("time");
            isSent = jsonInput.getJSONArray("isSent");
            isDelivered = jsonInput.getJSONArray("isDelivered");
            isSeen = jsonInput.getJSONArray("isSeen");

            List<Integer> idS = new ArrayList<>();
            for (int i = 0; i < localId.length(); i++) {
                idS.add(Integer.parseInt(localId.get(i).toString()));
            }

            try {
                new messengerCfg();
            } catch (IOException e) {
                logger.error("couldn't open config of login");
            }


            try {
                MongoClient mongoClient = MongoClients.create();
                MongoDatabase sampleTrainingDB = mongoClient.getDatabase(messengerCfg.database);
                MongoCollection<Document> userCollection = sampleTrainingDB.getCollection(
                        messengerCfg.usersCollection);
                MongoCollection<Document> messagesCollection = sampleTrainingDB.getCollection(
                        messengerCfg.messagesCollection);
                Document user = userCollection.find(new Document("AuthKey",
                        Integer.parseInt(jsonInput.get("AuthKey").toString()))).first();

                if (user == null) {
                    logger.error("error in connection with database");
                    resultJson.put("result", "0");
                } else {
                    String username = user.getString("username");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();

                    for (Document messages : messagesCollection.find()) {
                        if (messages.getString("sender").equals(username)
                                && !idS.contains(messages.getInteger("_id"))) {

                            Date messageTime = null;
                            Date currentTime = null;
                            try {
                                messageTime = sdf.parse(messages.get("time").toString());
                                currentTime = sdf.parse(dtf.format(now));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            assert messageTime != null;
                            assert currentTime != null;
                            if (messageTime.getTime() + 3000 < currentTime.getTime()) {
                                Bson filter = eq("_id", messages.getInteger("_id"));
                                messagesCollection.findOneAndDelete(filter);
                            }
                        }
                    }

                    int ID = 0;
                    for (Document messages : messagesCollection.find()) {
                        if (messages.getInteger("_id") > ID) {
                            ID = messages.getInteger("_id");
                        }
                    }

                    for (int i = 0; i < localId.length(); i++) {
                        if (sender.get(i).toString().equals(username)) {
                            if ((Boolean) isSent.get(i)) {
                                Bson filter = eq("_id", Integer.parseInt(localId.get(i).toString()));
                                Bson update = set("text", text.get(i).toString());
                                messagesCollection.findOneAndUpdate(filter, update);

                            } else {
                                int codeNum = 0;
                                boolean hasPic = false;
                                int myChar = 0;
                                String myText = text.get(i).toString();
                                String Text = text.get(i).toString();
                                int last = 1;

                                if (Text.charAt(0) == '~') {
                                    hasPic = true;
                                    while (Text.charAt(last) != '~') {
                                        last++;
                                    }
                                    myChar = Integer.parseInt(Text.substring(1, last));
                                    myText = Text.substring(last + 1, last + myChar + 1);
                                }
                                ID++;

                                Document newUser = new Document("_id", ID);
                                newUser.append("text", myText).
                                        append("sender", sender.get(i).toString()).
                                        append("receiver", receiver.get(i).toString()).
                                        append("time", time.get(i).toString()).
                                        append("seen", isSeen.get(i)).
                                        append("sent", true).
                                        append("delivered", isDelivered.get(i));
                                messagesCollection.insertOne(newUser);

                                if (hasPic)
                                    setMessagePic.set(ID, Text.substring(last + myChar + 1));

                            }
                        } else {
                            Bson filter = eq("_id", Integer.parseInt(localId.get(i).toString()));
                            Bson update = set("seen", true);
                            messagesCollection.findOneAndUpdate(filter, update);
                        }
                    }

                    getMessages atFirst = new getMessages();
                    return atFirst.get(Integer.parseInt(jsonInput.get("AuthKey").toString()));

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

