package com.java.setting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class jsonCreatorSetting {
    private static final Logger logger = LogManager.getLogger(jsonCreatorSetting.class);
    public JSONObject creator(int results) {
        logger.info("setting and {} got in json",results);
        JSONObject jsonOutput = new JSONObject();
        jsonOutput.put("key","setting").put("result",results);
        return jsonOutput;
    }
}
