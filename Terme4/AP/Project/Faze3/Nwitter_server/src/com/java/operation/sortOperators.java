package com.java.operation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class sortOperators {

    jsonCreatorOperator jsonCreator = new jsonCreatorOperator();

    liking liking = new liking();
    retwitting retwitting = new retwitting();
    saving saving = new saving();
    submitTwitte submitTwitte = new submitTwitte();
    reporting reporting = new reporting();
    JSONObject jsonResult = new JSONObject();
    submitComment submitComment = new submitComment();
    mute mute = new mute();
    follow follow = new follow();
    block block = new block();
    int result=0;
    remove remove = new remove();
    private static final Logger logger = LogManager.getLogger(sortOperators.class);
    public JSONObject sort(JSONObject operation) {

        switch (operation.getString("operator")) {
            case "like" -> result = liking.set(operation.getString("AuthKey"), operation.getString("serial"));
            case "retwitte" -> result = retwitting.set(operation.getString("AuthKey"), operation.getString("serial"));
            case "save_twitte" -> result = saving.set(operation.getString("AuthKey"), operation.getString("serial"));
            case "report" -> result = reporting.set(operation.getString("text"),
                    operation.getString("AuthKey"), operation.getString("serial"));
            case "mute" -> result = mute.setter(operation.get("AuthKey").toString(),
                    operation.get("serial").toString());
            case "follow" -> result = follow.setter(operation.get("AuthKey").toString(),
                    operation.get("serial").toString());
            case "block" -> result = block.setter(operation.get("AuthKey").toString(),
                    operation.get("serial").toString());
            case "remove" -> result = remove.setter(operation.get("AuthKey").toString(),
                    operation.get("serial").toString());
            case "submit_twitte" -> result = submitTwitte.adder(operation.get("AuthKey").toString(),
                    operation.get("text").toString());
            case "submit_comment" -> result = submitComment.adder(operation.get("AuthKey").toString(),
                    operation.get("text").toString(),operation.get("serial").toString());
        }
        jsonResult = jsonCreator.creator(result);
        return jsonResult;
    }
}
