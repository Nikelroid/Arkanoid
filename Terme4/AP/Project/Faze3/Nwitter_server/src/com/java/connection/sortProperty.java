package com.java.connection;

import com.java.category.categoriesSorter;
import com.java.checkExists.existsServer;
import com.java.editProfile.sortEdit;
import com.java.list.listSorter;
import com.java.loadTwittes.loadSorter;
import com.java.messenger.messengerSorter;
import com.java.notifications.notifsSorter;
import com.java.operation.sortOperators;
import com.java.profile.sortProfile;
import com.java.register.registerServer;
import com.java.setting.sortSetting;
import com.java.user.userSorter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import com.java.Login.loginServer;

import java.io.IOException;

public class sortProperty {
    loginServer loginServer = new loginServer();
    loadSorter loadServer = new loadSorter();
    sortOperators operateServer = new sortOperators();
    registerServer registerServer = new registerServer();
    existsServer existsServer = new existsServer();
    listSorter listSorter = new listSorter();
    categoriesSorter categoriesSorter = new categoriesSorter();
    com.java.notifications.notifsSorter notifsSorter = new notifsSorter();
    com.java.editProfile.sortEdit sortEdit = new sortEdit();
    com.java.setting.sortSetting sortSetting = new sortSetting();
    com.java.user.userSorter userSorter = new userSorter();
    com.java.profile.sortProfile sortProfile = new sortProfile();
    messengerSorter messengerSorter = new messengerSorter();
    com.java.groups.groupsSorter groupsSorter = new com.java.groups.groupsSorter();
    JSONObject resultJson = new JSONObject();
    private static final Logger logger = LogManager.getLogger(sortProperty.class);
    public sortProperty() {
    }

    public JSONObject sort(JSONObject jsonInput) throws IOException {
        logger.info("request got in sort class");
        switch (jsonInput.get("key").toString()) {
            case "connection" -> resultJson.put("result","1");
            case "login" -> resultJson = loginServer.login(
                    jsonInput.get("username").toString(),
                    jsonInput.get("password").toString());
            case "exists" -> resultJson = existsServer.exists(jsonInput);
            case "register" -> resultJson = registerServer.register(jsonInput);
            case "load" -> resultJson = loadServer.load(jsonInput);
            case "operate" -> resultJson = operateServer.sort(jsonInput);
            case "list" -> resultJson = listSorter.load(
                    jsonInput.getString("list"),jsonInput.getString("code"));
            case "user" -> resultJson = userSorter.load(jsonInput);
            case "setting" -> resultJson = sortSetting.sort(jsonInput);
            case "edit" -> resultJson = sortEdit.sort(jsonInput);
            case "category" -> resultJson = categoriesSorter.load(jsonInput);
            case "messages" -> resultJson = messengerSorter.load(jsonInput);
            case "groups" -> resultJson = groupsSorter.load(jsonInput);
            case "notifs" -> resultJson = notifsSorter.load(jsonInput);
            case "image" -> resultJson = sortProfile.sort(jsonInput);




        }
        return resultJson;
    }
}
