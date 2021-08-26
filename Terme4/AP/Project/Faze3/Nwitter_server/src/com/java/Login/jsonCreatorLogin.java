package com.java.Login;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class jsonCreatorLogin {
    private static final Logger logger = LogManager.getLogger(jsonCreatorLogin.class);
    public JSONObject creator(String response , int results) {
        logger.info("Login and {} and {} got in json",response,results);
        JSONObject jsonOutput = new JSONObject();
        jsonOutput.put("key","login").put("response",response).put("result",results);
        return jsonOutput;
    }
}
