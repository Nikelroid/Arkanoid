package com.java.botImport;

import com.java.botRegister.registerBotDatabase;
import com.java.botRegister.setProfileBotPic;
import com.java.botRunning.botLaunch;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;


public class botFetchUrl {
    public botFetchUrl() {
    }


    public static ArrayList<botLaunch> botLaunchers = new ArrayList<>();
        setProfileBotPic setProfileBotPic = new setProfileBotPic();
    public botFetchUrl(String address) throws IOException,
            ClassNotFoundException,
            NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        URL[] urls = new URL[]{new URL(address)};
        ClassLoader parent;
        URLClassLoader loader = new URLClassLoader(urls);
        Class classObj = loader.loadClass("Main");

        Field botName = classObj.getField("bot_name");
        Field botId = classObj.getField("bot_id");
        Field botPassword = classObj.getField("bot_password");
        Field botBio = classObj.getField("bot_bio");
        Field botPic = classObj.getField("bot_pic");

        String NAME = (String) botName.get(String.class);
        int ID = (Integer) botId.get(int.class);
        String PASSWORD = (String) botPassword.get(String.class);
        String BIO = (String) botBio.get(String.class);
        String pic = (String) botPic.get(String.class);

        String username = NAME+"_bot";
        System.out.println(NAME+" Bot detected successfully ...");

        registerBotDatabase registerBotDatabase = new registerBotDatabase();
        int result = registerBotDatabase.database(NAME+"_bot",NAME + " Robot",PASSWORD,ID,BIO);
        if (result==0)System.out.println(NAME+" exists.");
        if (result==1)System.out.println(NAME+" Registered successfully ...");

        setProfileBotPic.set(NAME+"_bot",pic);

        botLaunch newBot = new botLaunch(username,classObj);
        botLaunchers.add(newBot);
        botLaunchers.get(botLaunchers.size()-1).start();

        System.out.println(NAME+" Bot Started !");
        System.out.println("________________________________________");


    }
}
