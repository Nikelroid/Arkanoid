package com.java.editProfile;

import com.java.setting.delete;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class sortEdit {
    changeInfo changeInfo = new changeInfo();
    jsonCreatorEdit jsonCreator = new jsonCreatorEdit();
    getInfoEdit getInfoEdit = new getInfoEdit();
    com.java.setting.delete delete = new delete();
    JSONObject jsonResult = new JSONObject();
    int result = 0;
    private static final Logger logger = LogManager.getLogger(sortEdit.class);
    public JSONObject sort(JSONObject editJson) {

        if (editJson.getString("type").equals("get")){
            return getInfoEdit.get(Integer.parseInt(editJson.getString("AuthKey")));
        }else
        result = changeInfo.setter(Integer.parseInt
                (editJson.getString("AuthKey")),editJson.getString("type"),
                editJson.getString("new"));

        jsonResult = jsonCreator.creator(result);
        return jsonResult;
    }
}
