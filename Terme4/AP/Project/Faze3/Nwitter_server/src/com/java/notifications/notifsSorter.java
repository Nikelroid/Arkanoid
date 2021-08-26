package com.java.notifications;

import com.java.category.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class notifsSorter {


    deleteNotifs deleteNotifs = new deleteNotifs();
    notifsGetter notifsGetter = new notifsGetter();
    clearNotifs clearNotifs = new clearNotifs();
    responseRequest responseRequest = new responseRequest();
    JSONObject databaseResult = new JSONObject();
    private static final Logger logger = LogManager.getLogger(notifsSorter.class);
    public JSONObject load(JSONObject jsonInput) {
        switch (jsonInput.getString("type")) {
            case "get" -> databaseResult = notifsGetter.get
                    (Integer.parseInt(jsonInput.get("AuthKey").toString()));
            case "delete" -> databaseResult = deleteNotifs.setter (jsonInput.get("serial").toString());
            case "clear" -> databaseResult = clearNotifs.setter (Integer.parseInt(
                    jsonInput.get("AuthKey").toString()));
            case "accept1","accept2","reject" -> databaseResult = responseRequest.setter
                    (jsonInput.getString("type"),
                    Integer.parseInt(jsonInput.get("AuthKey").toString()),
                            jsonInput.get("serial").toString());

        }

        return databaseResult;
    }
}
