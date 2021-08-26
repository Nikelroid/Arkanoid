package com.java.list;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class listSorter {
    likes likes = new likes();
    retwittes retwittes = new retwittes();
    userLists userLists = new userLists();
    jsonCreatorList jsonCreator = new jsonCreatorList();
    ArrayList<String> databaseResult = new ArrayList<>();
    JSONObject jsonResult = new JSONObject();
    private static final Logger logger = LogManager.getLogger(listSorter.class);
    public JSONObject load(String type,String serial) throws IOException {
        switch (type) {
            case "likes" -> databaseResult = likes.get(serial);
            case "retwittes" -> databaseResult = retwittes.get(serial);
            case "followings","followers","mutes","blocks" -> databaseResult = userLists.get(serial,type);
        }

        jsonResult = jsonCreator.creator(databaseResult);
        return jsonResult;
    }
}
