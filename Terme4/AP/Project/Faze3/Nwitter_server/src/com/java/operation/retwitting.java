package com.java.operation;

public class retwitting {
    retwitteDatabase database = new retwitteDatabase();
    public int set(String AuthKey, String serial) {
        return database.setter(AuthKey,serial);
    }

    public retwitting() { }
}
