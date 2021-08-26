package com.java.editProfile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class jsonCreatorEdit {
    private static final Logger logger = LogManager.getLogger(jsonCreatorEdit.class);
    public JSONObject creator(int results) {
        logger.info("edit and {} got in json",results);
        JSONObject jsonOutput = new JSONObject();
        jsonOutput.put("key","setting").put("result",results);
        return jsonOutput;
    }
}
