package com.java.messenger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class messengerSorter {
    getMessages atFirst = new getMessages();
    setMessages setMessages  = new setMessages();
    deleteChat deleteChat  = new deleteChat();
    JSONObject databaseResult = new JSONObject();
    private static final Logger logger = LogManager.getLogger(messengerSorter.class);
    public JSONObject load(JSONObject jsonInput) {
        switch (jsonInput.getString("type")) {
            case "get" -> databaseResult = atFirst.get
                    (Integer.parseInt(jsonInput.get("AuthKey").toString()));
            case "set" -> databaseResult = setMessages.setter(jsonInput);
            case "deleteChat" -> databaseResult = deleteChat.delete(
                    (Integer.parseInt(jsonInput.get("AuthKey").toString())),
                    jsonInput.get("username").toString());
        }
        return databaseResult;
    }
}
