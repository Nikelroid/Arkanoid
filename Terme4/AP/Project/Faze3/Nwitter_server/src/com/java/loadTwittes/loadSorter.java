package com.java.loadTwittes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class loadSorter {
    loadDatabase databaseConnection = new loadDatabase();
    explorerDatabase explorerDatabase = new explorerDatabase();
    getComments getComments = new getComments();
    savedDatabase savedDatabase = new savedDatabase();
    users users = new users();
    hyperlinkDatabase hyperlinkDatabase = new hyperlinkDatabase();
    jsonCreatorLoad jsonCreator = new jsonCreatorLoad();
    ArrayList<String> databaseResult = new ArrayList<>();
    JSONObject jsonResult = new JSONObject();
    private static final Logger logger = LogManager.getLogger(loadSorter.class);
    public JSONObject load(JSONObject jsonInput) throws IOException {
        switch (jsonInput.getString("type")) {
            case "feed" -> databaseResult = databaseConnection.get(Integer.parseInt(jsonInput.get("AuthKey").toString()));
            case "explorer" -> databaseResult = explorerDatabase.get(Integer.parseInt(jsonInput.get("AuthKey").toString()));

            case "comments" -> databaseResult = getComments.get(Integer.parseInt(jsonInput.get("serial").toString()));
            case "myTwittes","userTwittes" -> databaseResult = users.get
                    (jsonInput.getString("type").equals("myTwittes"), jsonInput.get("AuthKey").toString());
            case "saved" -> databaseResult = savedDatabase.get(Integer.parseInt(jsonInput.get("AuthKey").toString()));
            case "hyperlink" -> databaseResult = hyperlinkDatabase.get(
                    Integer.parseInt(jsonInput.get("AuthKey").toString()),
                    Integer.parseInt(jsonInput.get("serial").toString()));

        }

        if (jsonInput.getString("type").equals("userTwittes")) {
            jsonResult = jsonCreator.creator(databaseResult,
                    Integer.parseInt(jsonInput.get("serial").toString()));
        }else {
            jsonResult = jsonCreator.creator(databaseResult,
                    Integer.parseInt(jsonInput.get("AuthKey").toString()));
        }
        return jsonResult;
    }
}
