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
import static com.mongodb.client.model.Updates.set;

public class follow {
    private static final Logger logger = LogManager.getLogger(follow.class);
    databaseControl dataBaseControl = new databaseControl();
    notificationsSet notificationSet = new notificationsSet();
    List<String> followings;
    List<String> followers;
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
                followings = me.getList("followings", String.class);
                followers = user.getList("followers", String.class);
                if (followings.contains(user.get("username").toString())) {
                    List<String> blocks = me.getList("blocks", String.class);
                    blocks.remove(user.get("username").toString());
                    followers.remove(me.get("username").toString());
                    followings.remove(user.get("username").toString());
                    notificationSet.setter(me.get("username").toString(),
                            user.get("username").toString(),2);
                    result = 1;
                } else {
                    if (!user.getBoolean("account")){
                        request request = new request();
                        request.setter(AuthKey,username);

                    }else {
                        followers.add(me.get("username").toString());
                        followings.add(user.get("username").toString());
                        notificationSet.setter(me.get("username").toString(),
                                user.get("username").toString(),1);
                        result = 2;
                    }
                }


                usersCollection.updateOne(eq("AuthKey",  Integer.parseInt(AuthKey)),
                        set("followings", followings));

                usersCollection.updateOne(eq("username",  username),
                        set("followers", followers));

            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
            return result;
        }
    }
}

