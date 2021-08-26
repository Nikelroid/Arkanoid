package com.java.register;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class jsonCreatorRegister {
    private static final Logger logger = LogManager.getLogger(jsonCreatorRegister.class);
    public JSONObject creator(int results) {
        logger.info("Rgister and {} got in json",results);
        JSONObject jsonOutput = new JSONObject();
        jsonOutput.put("key","register").put("result",results);
        return jsonOutput;
    }
}
