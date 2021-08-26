package com.java.groups;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class groupsSorter {
    groupsDatabase databaseConnection = new groupsDatabase();
    addMemberGps addMemberGps = new addMemberGps();
    deleteMemberGps deleteMemberGps = new deleteMemberGps();
    getFollowingsGps getFollowingsGps = new getFollowingsGps();
    addMemberGpsLink addMemberGpsLink = new addMemberGpsLink();
    deleteGps deleteGps = new deleteGps();
    addGps addGps = new addGps();
    getMembersGps getMembersGps = new getMembersGps();
    JSONObject databaseResult = new JSONObject();
    private static final Logger logger = LogManager.getLogger(groupsSorter.class);
    public JSONObject load(JSONObject jsonInput) {
        switch (jsonInput.getString("type")) {
            case "get" -> databaseResult = databaseConnection.get //!
                    (Integer.parseInt(jsonInput.get("AuthKey").toString()));
            case "getFollowingsGps" -> databaseResult = getFollowingsGps.get //!
                    (Integer.parseInt(jsonInput.get("AuthKey").toString()),
                            jsonInput.get("gpName").toString());
            case "getMembers" -> databaseResult = getMembersGps.get //!
                    (Integer.parseInt(jsonInput.get("AuthKey").toString()),
                            jsonInput.get("gpName").toString());
            case "addMembers" -> databaseResult = addMemberGps.get //!
                    (Integer.parseInt(jsonInput.get("AuthKey").toString()),
                            jsonInput.get("gpName").toString(),
                            jsonInput.get("username").toString());
            case "deleteMembers" -> databaseResult = deleteMemberGps.get //!
                    (Integer.parseInt(jsonInput.get("AuthKey").toString()),
                            jsonInput.get("gpName").toString(),
                            jsonInput.get("username").toString());
            case "deleteGps" -> databaseResult = deleteGps.get //!
                    (Integer.parseInt(jsonInput.get("AuthKey").toString()),
                            jsonInput.get("gpName").toString());
            case "addGps" -> databaseResult = addGps.get //!
                    (Integer.parseInt(jsonInput.get("AuthKey").toString()),
                            jsonInput.get("gpName").toString());
            case "addMembersLink" -> databaseResult = addMemberGpsLink.get
                    (Integer.parseInt(jsonInput.get("AuthKey").toString()),
                            jsonInput.get("gpName").toString());
        }

        return databaseResult;
    }
}
