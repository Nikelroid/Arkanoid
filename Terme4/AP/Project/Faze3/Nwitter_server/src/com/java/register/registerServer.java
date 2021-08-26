package com.java.register;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class registerServer {
    registerDatabase databaseConnection = new registerDatabase();
    jsonCreatorRegister jsonCreator = new jsonCreatorRegister();
    int databaseResult = 0;
    JSONObject jsonResult = new JSONObject();
    private static final Logger logger = LogManager.getLogger(registerServer.class);
    public JSONObject register(JSONObject regInfo) {

        databaseResult = databaseConnection.database(
                regInfo.getString("name"),
                regInfo.getString("username"),
                regInfo.getString("password"),
                regInfo.getString("birthday"),
                regInfo.getString("email"),
                regInfo.getString("phonenumber"),
                regInfo.getString("bio")
        );

                logger.info("Y and {} sent to json creator",databaseResult);
                jsonResult = jsonCreator.creator(databaseResult);

        return jsonResult;
    }
}
