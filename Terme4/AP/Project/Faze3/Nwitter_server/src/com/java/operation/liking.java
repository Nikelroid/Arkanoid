package com.java.operation;

public class liking {
    likeDatabase likeDatabase = new likeDatabase();
    public int set(String AuthKey, String serial) {
        return likeDatabase.setter(AuthKey,serial);
    }

    public liking() { }
}
