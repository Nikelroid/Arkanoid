package com.java.Login;

import com.java.database.databaseControl;
import com.mongodb.client.MongoCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.security.SecureRandom;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

public class loginDatabase {
    private static final Logger logger = LogManager.getLogger(loginDatabase.class);
    databaseControl dataBaseControl = new databaseControl();
    setOffline setOffline = new setOffline();
    String collection;
    public int database(String username,String password) {

        if (password.equals("-")) return setOffline.setter(username);

        try {
            new loginCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of login");
        }
        if(loginCfg.collection==null){
            collection = "Users";
        }else{
            collection = loginCfg.collection;
        }

        MongoCollection<Document> usersCollection = dataBaseControl.getCollection(collection);
            Document user = usersCollection.find(new Document("username", username)).first();
            if(user==null){
                return 3;
            }else if(user.get("password").equals(password)){
                SecureRandom rand = new SecureRandom();
            int authKey = rand.nextInt(2147483647);

                Bson filter = eq("username", username);
                Bson updates = combine(set("lastseen","Online"), set("AuthKey",authKey));
                usersCollection.findOneAndUpdate(filter, updates);
                return authKey;
            }else{
                return 2;
            }
        }

    }

