package com.java.checkExists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class existsServer {
    existsDatabase databaseConnection = new existsDatabase();
    existsGroup existsGroup = new existsGroup();
    jsonCreatorExists jsonCreator = new jsonCreatorExists();

    int databaseResult = 0;
    JSONObject jsonResult = new JSONObject();
    private static final Logger logger = LogManager.getLogger(existsServer.class);
    public JSONObject exists(JSONObject jsonInput) {
        if(!jsonInput.getString("field").equals("group"))
        databaseResult = databaseConnection.database(
                jsonInput.getString("field"),
                jsonInput.getString("input"));
        else
            databaseResult = existsGroup.database(
                    jsonInput.getString("AuthKey"),
                    jsonInput.getString("input"));
        logger.info("Exists and {} sent to json creator",databaseResult);
        jsonResult = jsonCreator.creator(databaseResult);
        return jsonResult;
    }
}
