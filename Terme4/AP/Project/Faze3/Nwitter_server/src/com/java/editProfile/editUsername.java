package com.java.editProfile;

import com.java.database.databaseControl;
import com.java.setting.settingCfg;
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

public class editUsername {
    private static final Logger logger = LogManager.getLogger(editUsername.class);
    databaseControl dataBaseControl = new databaseControl();

    public int setter(int AuthKey,String newUsername) {
        try {

            try {
                new editProfileCfg();
            } catch (IOException e) {
                logger.error("couldn't open config of operates");
            }


            MongoCollection<Document> usersCollection = dataBaseControl.getCollection(editProfileCfg.usersCollection);
            MongoCollection<Document> twittesCollection = dataBaseControl.getCollection(editProfileCfg.twittesCollection);
            MongoCollection<Document> reportCollection = dataBaseControl.getCollection(editProfileCfg.reportsCollection);
            MongoCollection<Document> notifsCollection = dataBaseControl.getCollection(editProfileCfg.notifsCollection);
            MongoCollection<Document> messagesCollection = dataBaseControl.getCollection(editProfileCfg.messagesCollection);

            Document me = usersCollection.find(new Document("AuthKey", AuthKey)).first();
            String username = me.get("username").toString();


            for (Document twitte1 : twittesCollection.find()) {
                Bson filer = eq("_id",Integer.parseInt(twitte1.get("_id").toString()));

                List<String> twitteLikes = twitte1.getList("likes",String.class);
                List<String> twitteRetwittes = twitte1.getList("retwittes",String.class);
                if (twitteLikes.contains(username)) {
                    twitteLikes.remove(username);
                    twitteLikes.add(newUsername);
                }
                if (twitteRetwittes.contains(username)) {
                    twitteRetwittes.remove(username);
                    twitteRetwittes.add(newUsername);
                }

                twittesCollection.findOneAndUpdate(filer, set("retwittes", twitteRetwittes));
                twittesCollection.findOneAndUpdate(filer, set("likes", twitteLikes));
            }

            for (Document user : usersCollection.find()) {
                String Username = user.getString("username");

                List<List<String>> userGroups = (List<List<String>>) user.get("groups");
                for (List<String> userGroup : userGroups) {
                    if (userGroup.get(1).equals(username))
                        userGroup.set(1, newUsername);
                }
                Bson filer = eq("username",Username);
                usersCollection.findOneAndUpdate(filer, set("groups", userGroups));
            }


            for (Document users : usersCollection.find()) {

                String Username = users.getString("username");

                List<String> followers = users.getList("followers",String.class);
                List<String> followings = users.getList("followings",String.class);
                List<String> mutes = users.getList("mutes",String.class);
                List<String> blocks = users.getList("blocks",String.class);

                List<ArrayList<String>> categories = (List<ArrayList<String>>)users.get("categoiries");

                if (followers.contains(username)){
                    followers.remove(username);
                    followers.add(newUsername);
                }
                if (followings.contains(username)){
                    followings.remove(username);
                    followings.add(newUsername);
                }
                if (mutes.contains(username)){
                    mutes.remove(username);
                    mutes.add(newUsername);
                }
                if (blocks.contains(username)){
                    blocks.remove(username);
                    blocks.add(newUsername);
                }


                for (int i = categories.size() - 1; i >= 0; i--) {
                    for (int j = 0; j < categories.get(i).size(); j++) {
                       if(categories.get(i).get(j).equals(username)) {
                           categories.get(i).remove(j);
                           categories.get(i).add(newUsername);
                           break;
                       }
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
            Bson filter5 = eq("receiver", username);

            Bson update1 = set("sender",newUsername);
            Bson update2 = set("reporter", newUsername);
            Bson update3 = set("user1", newUsername);
            Bson update4 = set("user2", newUsername);
            Bson update5 = set("receiver", newUsername);



            twittesCollection.updateMany(filter1, update1);
            reportCollection.updateMany(filter1, update1);
            reportCollection.updateMany(filter2,update2);
            notifsCollection.updateMany(filter3, update3);
            notifsCollection.updateMany(filter4, update4);
            messagesCollection.updateMany(filter1, update1);
            messagesCollection.updateMany(filter5, update5);


            return 1;
        } catch (Exception e) {
            logger.error("error in database collection");
            e.printStackTrace();
            return 0;
        }
    }
}

