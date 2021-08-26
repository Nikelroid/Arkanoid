package com.java.botRegister;

import com.java.profile.profileCfg;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class setProfileBotPic {
    JSONObject result = new JSONObject();

    private static final Logger logger = LogManager.getLogger(setProfileBotPic.class);

    public JSONObject set(String username,String picture) throws IOException {

        try {
            new profileCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of login");
        }



        try {
            MongoClient mongoClient = MongoClients.create();
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase(botRegisterCfg.database);
            MongoCollection<Document> userCollection = sampleTrainingDB.getCollection(
                    botRegisterCfg.usersCollection);

            Document me = userCollection.find(new Document("username", username)).first();


            if (me == null) {
                logger.error("error in connection with database");
                result.put("result","0");
                return result;
            } else {


                Path simple = Paths.get("simple.png");
                File outputFile = new File(
                simple.toAbsolutePath().getParent()+
                        botRegisterCfg.profilePicPath+
                        username+ ".png");

                byte[] decodedBytes = Base64.getDecoder().decode(picture);
                FileUtils.writeByteArrayToFile(outputFile, decodedBytes);



                result.put("result",1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error in opening database");
        }
        return result;
    }
}
