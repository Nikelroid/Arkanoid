package com.java.operation;

public class reporting {
    reportDatabase reportDatabase = new reportDatabase();
    public int set(String reptext,String AuthKey, String serial) {
        return reportDatabase.setter(reptext,AuthKey,serial);
    }
    public reporting() { }
}
