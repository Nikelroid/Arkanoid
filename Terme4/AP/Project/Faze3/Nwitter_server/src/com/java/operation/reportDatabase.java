package com.java.operation;

import com.java.database.databaseControl;
import com.mongodb.client.MongoCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class reportDatabase {
    private static final Logger logger = LogManager.getLogger(reportDatabase.class);
    databaseControl dataBaseControl = new databaseControl();

    int result = 1;
    public int setter(String reportText,String AuthKey, String serial) {
        try {
            new operateCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of operates");
        }

        MongoCollection<Document> usersCollection = dataBaseControl.getCollection(operateCfg.usersCollection);
        MongoCollection<Document> reportsCollection = dataBaseControl.getCollection(operateCfg.reportsCollection);
        MongoCollection<Document> twitteCollection = dataBaseControl.getCollection(operateCfg.twittesCollection);

        Document user = usersCollection.find(new Document("AuthKey", Integer.parseInt(AuthKey))).first();
        Document twitte = twitteCollection.find(new Document("_id", Integer.parseInt(serial))).first();
        if(user==null || twitte==null ){
            return 0;
        }else {
            try {

                Document report = new Document("_id", new ObjectId());
                report.append("serial", Integer.parseInt(serial))
                        .append("reporter", user.getString("username"))
                        .append("twitteText", twitte.getString("text"))
                        .append("sender", twitte.getString("sender"))
                        .append("Reporttext", reportText);
                reportsCollection.insertOne(report);

            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
            return result;
        }
    }


