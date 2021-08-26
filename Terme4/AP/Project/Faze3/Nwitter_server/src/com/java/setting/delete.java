package com.java.setting;

import com.java.database.databaseControl;
import com.java.operation.operateCfg;
import com.mongodb.client.MongoCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class delete {
    private static final Logger logger = LogManager.getLogger(delete.class);
    databaseControl dataBaseControl = new databaseControl();

    public int setter(String AuthKey) {
        try {

            try {
                new settingCfg();
            } catch (IOException e) {
                logger.error("couldn't open config of operates");
            }

            MongoCollection<Document> usersCollection = dataBaseControl.getCollection(settingCfg.usersCollection);
            MongoCollection<Document> twittesCollection = dataBaseControl.getCollection(settingCfg.twittesCollection);
            MongoCollection<Document> reportCollection = dataBaseControl.getCollection(settingCfg.reportsCollection);
            MongoCollection<Document> notifsCollection = dataBaseControl.getCollection(settingCfg.notifsCollection);
            MongoCollection<Document> messagesCollection = dataBaseControl.getCollection(settingCfg.messagesCollection);

            Document user = usersCollection.find(new Document("AuthKey", Integer.parseInt(AuthKey))).first();
            String username = user.get("username").toString();

            for (Document twitte1 : twittesCollection.find()) {
                if (twitte1.get("sender").toString().equals(username)) {
                    int ID = Integer.parseInt(twitte1.get("_id").toString());
                    for (Document twitte2 : twittesCollection.find()) {

                        int ID2 = Integer.parseInt(twitte2.get("_id").toString());
                        Bson filer = eq("_id",ID2);
                        List<Integer> comments = twitte2.getList("comments", Integer.class);
                        comments.remove(Integer.valueOf(ID));


                        List<String> retwittes = twitte2.getList("retwittes", String.class);
                        List<String> likes = twitte2.getList("likes", String.class);
                        retwittes.remove(username);
                        likes.remove(username);

                        usersCollection.findOneAndUpdate(filer, set("comments", comments));
                        usersCollection.findOneAndUpdate(filer, set("retwittes", retwittes));
                        usersCollection.findOneAndUpdate(filer, set("likes", likes));

                    }
                }
            }
            List<List<String>> userGroups = (List<List<String>>)user.get("groups");
            String toUsername = "";
            for (int i = 0; i < userGroups.size(); i++) {
                String id = userGroups.get(i).get(0);
                String admin = userGroups.get(i).get(1);
                if (admin.equals(username)) {
                    for (Document users : usersCollection.find()) {
                        String Username = users.getString("username");
                        List<List<String>> groups = (List<List<String>>) users.get("groups");
                        for (int j = 0; j < groups.size(); j++) {
                            if (groups.get(j).get(0).equals(id)) {
                                groups.remove(j);
                                break;
                            }
                        }
                        Bson filer = eq("username", Username);
                        usersCollection.findOneAndUpdate(filer, set("groups", groups));
                    }
                }

            }


            for (Document users : usersCollection.find()) {

                String Username = users.getString("username");

                List<String> followers = users.getList("followers",String.class);
                List<String> followings = users.getList("followings",String.class);
                List<String> mutes = users.getList("mutes",String.class);
                List<String> blocks = users.getList("blocks",String.class);

                List<ArrayList<String>> categories = (List<ArrayList<String>>)users.get("categoiries");

                followers.remove(username);
                followings.remove(username);
                mutes.remove(username);
                blocks.remove(username);

                for (int i = categories.size() - 1; i >= 0; i--) {
                    for (int j = 0; j < categories.get(i).size(); j++) {
                       if(categories.get(i).toString().equals(username))
                           categories.get(i).remove(j);
                       break;
                    }
                }

                Bson filer = eq("username",Username);


                usersCollection.findOneAndUpdate(filer, set("followers", followers));
                usersCollection.findOneAndUpdate(filer, set("followings", followings));
                usersCollection.findOneAndUpdate(filer, set("mutes", mutes));
                usersCollection.findOneAndUpdate(filer, set("blocks", blocks));
                usersCollection.findOneAndUpdate(filer, set("categoiries", categories));

            }

            Bson filter1 = eq("sender", username);
            Bson filter2 = eq("reporter", username);
            Bson filter3 = eq("user1", username);
            Bson filter4 = eq("user2", username);
            Bson filter5 = eq("sender", username);
            Bson filter6 = eq("receiver", username);
            Bson filter7 = eq("username", username);

            twittesCollection.deleteMany(filter1);
            reportCollection.deleteMany(filter1);
            reportCollection.deleteMany(filter2);
            notifsCollection.deleteMany(filter3);
            notifsCollection.deleteMany(filter4);
            messagesCollection.deleteMany(filter5);
            messagesCollection.deleteMany(filter6);
            usersCollection.deleteMany(filter7);

            return 1;
        } catch (Exception e) {
            logger.error("error in database collection");
            e.printStackTrace();
            return 0;
        }
    }
}

