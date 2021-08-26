package com.java.botRunning;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class botLaunch extends Thread{


    public String username;
    public Class classObj;
    public boolean isAlive = true;
    setMessagesBot setMessages = new setMessagesBot();
    public botLaunch(String username,Class classObj) {
        this.username = username;
        this.classObj = classObj;

    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public void run() {
        while (isAlive) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (this) {
                getMessagesBot getMessages = new getMessagesBot();
                JSONObject jsonResult = getMessages.get(username);

                if (jsonResult.get("result").equals("0")) JOptionPane.showMessageDialog(
                        null, "Error in connection get message robot");
                else {
                    JSONArray _idJson = jsonResult.getJSONArray("_id");
                    JSONArray textJson = jsonResult.getJSONArray("text");
                    JSONArray senderJson = jsonResult.getJSONArray("sender");
                    JSONArray receiverJson = jsonResult.getJSONArray("receiver");
                    JSONArray seenJson = jsonResult.getJSONArray("seen");

                    Method mainMethod = null;
                    try {
                        mainMethod = classObj.getMethod("main", JSONObject.class);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                    for (int i = 0; i < _idJson.length(); i++) {
                        if (!(Boolean) seenJson.get(i) && receiverJson.get(i).toString().equals(this.username)) {
                            try {
                                JSONObject jsonInput = new JSONObject();
                                jsonInput.put("text", textJson.get(i).toString())
                                        .put("sender", senderJson.get(i).toString())
                                        .put("receiver", receiverJson.get(i).toString());

                                JSONObject jsonOutput = (JSONObject) mainMethod.invoke(null, jsonInput);

                                try {
                                    try {

                                        setMessages.sender(
                                                jsonOutput.getString("text"),
                                                this.username,
                                                jsonOutput.getString("user"));
                                    } catch (Exception e) {
                                        JSONArray texts = jsonOutput.getJSONArray("text");
                                        JSONArray users = jsonOutput.getJSONArray("user");
                                        for (int j = 0; j < texts.length(); j++) {
                                            setMessages.sender(
                                                    texts.get(j).toString(),
                                                    this.username,
                                                    users.get(j).toString());
                                        }
                                    }
                                }catch (Exception error){
                                    error.printStackTrace();
                                    setMessages.sender(
                                            "Can't define message",
                                            this.username,
                                            senderJson.get(i).toString());
                                }

                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
}
