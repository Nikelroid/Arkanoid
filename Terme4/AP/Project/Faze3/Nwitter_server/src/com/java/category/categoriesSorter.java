package com.java.category;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class categoriesSorter {
    categoriesDatabase databaseConnection = new categoriesDatabase();
    addMemberCat addMemberCat = new addMemberCat();
    deleteMemberCat deleteMemberCat = new deleteMemberCat();
    getFollowingsCat getFollowingsCat = new getFollowingsCat();
    deleteCat deleteCat = new deleteCat();
    addCat addCat = new addCat();
    getMembers getMembers = new getMembers();
    JSONObject databaseResult = new JSONObject();
    private static final Logger logger = LogManager.getLogger(categoriesSorter.class);
    public JSONObject load(JSONObject jsonInput) {
        switch (jsonInput.getString("type")) {
            case "get" -> databaseResult = databaseConnection.get
                    (Integer.parseInt(jsonInput.get("AuthKey").toString()));
            case "getFollowingsCategory" -> databaseResult = getFollowingsCat.get
                    (Integer.parseInt(jsonInput.get("AuthKey").toString()),
                            jsonInput.get("catName").toString());
            case "getMembers" -> databaseResult = getMembers.get
                    (Integer.parseInt(jsonInput.get("AuthKey").toString()),
                            jsonInput.get("catName").toString());
            case "addMembers" -> databaseResult = addMemberCat.get
                    (Integer.parseInt(jsonInput.get("AuthKey").toString()),
                            jsonInput.get("catName").toString(),
                            jsonInput.get("username").toString());
            case "deleteMembers" -> databaseResult = deleteMemberCat.get
                    (Integer.parseInt(jsonInput.get("AuthKey").toString()),
                            jsonInput.get("catName").toString(),
                            jsonInput.get("username").toString());
            case "deleteCat" -> databaseResult = deleteCat.get
                    (Integer.parseInt(jsonInput.get("AuthKey").toString()),
                            jsonInput.get("catName").toString());
            case "addCat" -> databaseResult = addCat.get
                    (Integer.parseInt(jsonInput.get("AuthKey").toString()),
                            jsonInput.get("catName").toString());
        }

        return databaseResult;
    }
}
