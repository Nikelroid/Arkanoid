package com.java.operation;

import com.java.database.databaseControl;
import com.java.notifications.notificationsSet;
import com.mongodb.client.MongoCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class block {
    private static final Logger logger = LogManager.getLogger(block.class);
    databaseControl dataBaseControl = new databaseControl();
    List<String> blocks;
    notificationsSet notificationsSet = new notificationsSet();
    int result = 0;
    public int setter(String AuthKey, String username) {
        try {
            new operateCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of operates");
        }

        MongoCollection<Document> usersCollection = dataBaseControl.getCollection(operateCfg.usersCollection);
        Document me = usersCollection.find(new Document("AuthKey", Integer.parseInt(AuthKey))).first();
        Document user = usersCollection.find(new Document("username", username)).first();
        if(user==null || me==null){
            return 0;
        }else{
            try {
                blocks = me.getList("blocks", String.class);
                List<String> myFollowings = me.getList("followings", String.class);
                List<String> myFollowers = me.getList("followers", String.class);
                List<String> userFollowings = user.getList("followings", String.class);
                List<String> userFollowers = user.getList("followers", String.class);

                MongoCollection<Document> notifsCollection = dataBaseControl.getCollection(operateCfg.notifsCollection);

                if (blocks.contains(user.get("username").toString())) {
                    blocks.remove(user.get("username").toString());
                    notificationsSet.setter(me.get("username").toString(),
                            user.get("username").toString(),4);
                    result = 1;
                } else {
                    Document myReq = new Document();
                    myReq.append("type", 8).append("user1", me.get("username")).append("user2", user.get("username"));
                    notifsCollection.findOneAndDelete(myReq);

                    myFollowers.remove(user.get("username").toString());
                    myFollowings.remove(user.get("username").toString());
                    userFollowers.remove(me.get("username").toString());
                    userFollowers.remove(me.get("username").toString());
                    blocks.add(user.get("username").toString());
                    notificationsSet.setter(me.get("username").toString(),
                            user.get("username").toString(),3);
                    result = 2;
                }
                Bson myFilter = eq("AuthKey",  Integer.parseInt(AuthKey));
                Bson userFilter = eq("username",  username);
                Bson update1 = (set("blocks", blocks));
                Bson update2 = (set("followers", myFollowers));
                Bson update3 = (set("followings", myFollowings));
                Bson update4 = (set("followers", userFollowers));
                Bson update5 = (set("followings", userFollowings));
                Bson myUpdates = combine(update1,update2,update3);
                Bson userUpdates = combine(update4,update5);

                usersCollection.updateOne(myFilter,myUpdates);

                usersCollection.updateOne(userFilter,userUpdates);

            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
            return result;
        }
    }
}

