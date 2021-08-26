package com.java.botRegister;

import com.java.database.databaseControl;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class registerBotDatabase {
    private static final Logger logger = LogManager.getLogger(registerBotDatabase.class);
    databaseControl dataBaseControl = new databaseControl();
    String collection;
    public int database(String username,
                        String name,
                        String password,
                        int id,
                        String bio) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        try {
            new botRegisterCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of login");
        }
        if(botRegisterCfg.usersCollection==null){
            collection = "Users";
        }else{
            collection = botRegisterCfg.usersCollection;
        }

        MongoCollection<Document> usersCollection = dataBaseControl.getCollection(collection);


        ArrayList<String> followers = new ArrayList<String>(Collections.singleton("Followers:"));
        ArrayList<String> followings = new ArrayList<String>(Collections.singleton("Followings:"));
        ArrayList<String> mutes = new ArrayList<String>(Collections.singleton("Mutes:"));
        ArrayList<String> blocks = new ArrayList<String>(Collections.singleton("Block:"));
        ArrayList<Integer> twittesaved = new ArrayList<Integer>(Collections.singleton(1));
        ArrayList<Integer> pmsaved = new ArrayList<Integer>(Collections.singleton(1));
        ArrayList<Integer> privacy = new ArrayList<Integer>();
        privacy.add(2);
        for (int i = 0; i < 3; i++)
            privacy.add(1);
        ArrayList<ArrayList<String>> groups = new ArrayList<>();
        ArrayList<ArrayList<String>> categories = new ArrayList<>();



        Document newUser = new Document("_id", id);
        newUser.append("name", name).
                append("username", username).
                append("password", password).
                append("birthday", dtf.format(now)).
                append("email", "BOT").
                append("phonenumber", "0").
                append("bio", bio).
                append("lastseen", dtf.format(now)).
                append("account", true).
                append("enable", true).
                append("followers",followers).
                append("followings",followings).
                append("mutes",mutes).
                append("blocks",blocks).
                append("categoiries",categories).
                append("groups",groups).
                append("privacy",privacy).
                append("twittesaved",twittesaved).
                append("pmsaved",pmsaved).
                append("AuthKey",0);

        try {
            usersCollection.insertOne(newUser);
            return 1;
        }catch (MongoWriteException e){
            return 0;
        }


        }

    }

