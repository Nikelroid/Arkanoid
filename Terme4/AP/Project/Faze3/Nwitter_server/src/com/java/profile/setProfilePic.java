package com.java.profile;

import com.java.setting.settingCfg;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

public class setProfilePic {
    JSONObject result = new JSONObject();

    private static final Logger logger = LogManager.getLogger(setProfilePic.class);

    public JSONObject set(int myAuthKey,String picture) throws IOException {

        try {
            new profileCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of login");
        }



        try {
            MongoClient mongoClient = MongoClients.create();
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase(profileCfg.database);
            MongoCollection<Document> userCollection = sampleTrainingDB.getCollection(
                    profileCfg.usersCollection);

            Document me = userCollection.find(new Document("AuthKey", myAuthKey)).first();


            if (me == null) {
                logger.error("error in connection with database");
                result.put("result","0");
                return result;
            } else {


                byte[] fileContent = new byte[0];
                String encodedString = null;


                    File file = new File("simple.png");


                    try {
                        fileContent = FileUtils.readFileToByteArray(file);
                        encodedString = Base64.getEncoder().encodeToString(fileContent);
                    } catch (IOException ioException) {
                        encodedString = "";
                    }
                    System.out.println(encodedString);



                result.put("result",1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error in opening database");
        }
        return result;
    }
}
