package com.java.checkExists;

import com.java.database.databaseControl;
import com.mongodb.client.MongoCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class existsGroup {
    private static final Logger logger = LogManager.getLogger(existsGroup.class);
    databaseControl dataBaseControl = new databaseControl();
    String collection;
    String messages;
    public int database(String AuthKey,String group) {
        try {
            new existsCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of login");
        }

        collection = Objects.requireNonNullElse(existsCfg.collection, "Users");
        messages = Objects.requireNonNullElse(existsCfg.messages, "Messages");

        MongoCollection<Document> usersCollection = dataBaseControl.getCollection(collection);
        MongoCollection<Document> messagesCollection = dataBaseControl.getCollection(messages);
        Document user = usersCollection.find(new Document("AuthKey", Integer.parseInt(AuthKey))).first();
            List<List<String>> groups = (List<List<String>>)user.get("groups");
            for (int i = 0; i < groups.size(); i++) {
                if (groups.get(i).get(1).equals(group)){
                    return 0;
            }
        }
        for (Document message:messagesCollection.find()) {
            if (message.get("sender").equals("*"+group)
                    || message.get("receiver").equals("*"+group)){
                return 2;
            }
        }
        for (Document person:usersCollection.find()) {
            List<List<String>> personGp = (List<List<String>>)person.get("groups");
            for (int i = 0; i < personGp.size(); i++) {
                if (personGp.get(i).get(1).equals(group)){
                    return 2;
                }
            }
        }
                return 1;
            }
        }


