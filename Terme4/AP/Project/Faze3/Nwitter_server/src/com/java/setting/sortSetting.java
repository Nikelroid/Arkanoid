package com.java.setting;

import com.java.operation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class sortSetting {
    getInfoSetting getInfoSetting = new getInfoSetting();
    jsonCreatorSetting jsonCreator = new jsonCreatorSetting();
    accountEnableSetting accountEnableSetting = new accountEnableSetting();
    privacySetting privacySetting = new privacySetting();
    JSONObject jsonResult = new JSONObject();
    delete delete = new delete();
    int result = 0;
    private static final Logger logger = LogManager.getLogger(sortSetting.class);
    public JSONObject sort(JSONObject settingJson) {

        if (settingJson.getString("type").equals("get")){
            return getInfoSetting.get(Integer.parseInt(settingJson.getString("AuthKey")));
        }else
        switch (settingJson.getString("type")) {
            case "delete" -> result = delete.setter(settingJson.getString("AuthKey"));

            case "account","enable" -> result = accountEnableSetting.setter(
                    Integer.parseInt(settingJson.getString("AuthKey")),
                    settingJson.getString("type"),
                    settingJson.getString("value"));

            case "lastseen","phonenumber","email","birthday" ->
                    result = privacySetting.setter(
                    Integer.parseInt(settingJson.getString("AuthKey")),
                    settingJson.getString("type"),
                            settingJson.getString("value"));

        }
        jsonResult = jsonCreator.creator(result);
        return jsonResult;
    }
}
