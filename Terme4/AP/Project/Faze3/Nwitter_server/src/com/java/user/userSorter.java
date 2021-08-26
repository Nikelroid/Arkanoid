package com.java.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.util.ArrayList;

public class userSorter {
    userProfileGetter userProfileGetter = new userProfileGetter();
    JSONObject databaseResult = new JSONObject();
    private static final Logger logger = LogManager.getLogger(userSorter.class);
    public JSONObject load(JSONObject jsonInput) {

            databaseResult = userProfileGetter.get(Integer.parseInt(jsonInput.get("myAuthKey").toString())
            ,jsonInput.get("userAuthKey").toString());

        return databaseResult;
    }
}
