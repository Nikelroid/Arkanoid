package com.java.operation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class jsonCreatorOperator {
    private static final Logger logger = LogManager.getLogger(jsonCreatorOperator.class);
    public JSONObject creator(int results) {
        logger.info("operator and {} got in json",results);
        JSONObject jsonOutput = new JSONObject();
        jsonOutput.put("key","operator").put("result",results);
        return jsonOutput;
    }
}
