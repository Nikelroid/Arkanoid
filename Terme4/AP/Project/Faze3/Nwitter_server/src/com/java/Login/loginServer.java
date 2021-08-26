package com.java.Login;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class loginServer {
    loginDatabase databaseConnection = new loginDatabase();
    jsonCreatorLogin jsonCreator = new jsonCreatorLogin();
    int databaseResult = 0;
    JSONObject jsonResult = new JSONObject();
    private static final Logger logger = LogManager.getLogger(loginServer.class);
    public JSONObject login(String username,String password) {
        databaseResult = databaseConnection.database(username,password);
        switch (databaseResult) {
            case 4 -> {

            }

            case 2, 3 -> {
                logger.info("N and {} sent to json creator",databaseResult);
                jsonResult = jsonCreator.creator("N", databaseResult);
            }
            default -> {
                logger.info("Y and {} sent to json creator",databaseResult);
                jsonResult = jsonCreator.creator("Y", databaseResult);
            }
        }
        return jsonResult;
    }
}
