package com.java.profile;

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

public class setTwitteImage {
    JSONObject result = new JSONObject();

    private static final Logger logger = LogManager.getLogger(setTwitteImage.class);

    public JSONObject set(int serial,String picture) throws IOException {

        try {
            new profileCfg();
        } catch (IOException e) {
            logger.error("couldn't open config of login");
        }



        try {




                Path simple = Paths.get("simple.png");
                File outputFile = new File(
                simple.toAbsolutePath().getParent()+
                        profileCfg.twittePicPath+
                        serial+ ".png");

                byte[] decodedBytes = Base64.getDecoder().decode(picture);
                FileUtils.writeByteArrayToFile(outputFile, decodedBytes);



                result.put("result",1);


        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error in opening database");
        }
        return result;
    }
}
