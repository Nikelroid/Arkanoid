package com.java.register;

import com.java.database.databaseControl;
import com.java.operation.operateCfg;
import com.mongodb.client.MongoCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class registerDatabase {
    private static final Logger logger = LogManager.getLogger(registerDatabase.class);
    databaseControl dataBaseControl = new databaseControl();
    String collection;
    public int database(String name,
                        String username,
                        String password,
                        String birthday,
                        String email,
                        String phonenumber,
                        String bio) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        try {
            new registerCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of login");
        }
        if(registerCfg.collection==null){
            collection = "Users";
        }else{
            collection = registerCfg.collection;
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


        int ID = 0;
        for (Document twitte:usersCollection.find()) {
            ID = twitte.getInteger("_id");
        }

        ID++;

        Document newUser = new Document("_id", ID);
        newUser.append("name", name).
                append("username", username).
                append("password", password).
                append("birthday", birthday).
                append("email", email).
                append("phonenumber", phonenumber).
                append("bio", bio).
                append("lastseen", dtf.format(now)).
                append("account", false).
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

        usersCollection.insertOne(newUser);
        return 1;
        }

    }

