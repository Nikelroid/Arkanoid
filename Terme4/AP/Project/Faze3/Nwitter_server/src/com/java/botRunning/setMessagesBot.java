package com.java.botRunning;

import com.java.messenger.getMessages;
import com.java.messenger.messengerCfg;
import com.java.profile.setMessagePic;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
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
import static com.mongodb.client.model.Updates.set;

public class setMessagesBot {

    private static final Logger logger = LogManager.getLogger(setMessagesBot.class);
    int result;
    setMessagePic setMessagePic = new setMessagePic();
    public static ReentrantLock messageLock = new ReentrantLock();
    public int sender(String text,String sender, String receiver) {


        try {
            messageLock.lock();
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
                Document target = userCollection.find(new Document("username", receiver)).first();

                if (target == null) {
                    logger.error("error in connection with database");
                    result = 0;
                } else {

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();


                    int ID = 0;
                    for (Document messages : messagesCollection.find()) {
                        if (messages.getInteger("_id") > ID) {
                            ID = messages.getInteger("_id");
                        }
                    }
                    ID++;

                    int codeNum = 0;
                    boolean hasPic = false;
                    int myChar = 0;
                    String myText = text;

                    int last = 1;

                    if (myText.charAt(0) == '~') {
                        hasPic = true;
                        while (myText.charAt(last) != '~') {
                            last++;
                        }
                        myChar = Integer.parseInt(myText.substring(1, last));
                        myText = text.substring(last + 1, last + myChar + 1);
                    }





                    Document newUser = new Document("_id", ID);
                    newUser.append("text", myText).
                            append("sender", sender).
                            append("receiver", receiver).
                            append("time", dtf.format(now)).
                            append("seen", false).
                            append("sent", true).
                            append("delivered", false);
                    messagesCollection.insertOne(newUser);

                    if (hasPic)
                        setMessagePic.set(ID, text.substring(last + myChar + 1));
                }


                result = 1;


            } catch (Exception e) {
                e.printStackTrace();
                logger.error("error in opening database");
                result = 0;
            }
        }finally {
            messageLock.unlock();
        }
        return result;
        }
    }

