package com.java.checkExists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class jsonCreatorExists {
    private static final Logger logger = LogManager.getLogger(jsonCreatorExists.class);
    public JSONObject creator(int results) {
        logger.info("Exists and {} got in json",results);
        JSONObject jsonOutput = new JSONObject();
        jsonOutput.put("key","login").put("result",results);
        return jsonOutput;
    }
}
