package com.java.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;


import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;



public class getter
{
    String address;
    int port;
    private static final Logger logger = LogManager.getLogger(getter.class);

    public static int onlineCount;
    public ArrayList<Socket> socket = new ArrayList<>();
    public ServerSocket server = null;
    public ArrayList<DataInputStream> in = new ArrayList<>();
    public ArrayList<DataOutputStream> out = new ArrayList<>();
    sortProperty sortProperty = new sortProperty();
    String inputString = "";
    String code = "";
    public getter() throws IOException, InterruptedException {
        address = "localhost";
        port = 8000;
        System.out.println("Server started");
        new connectionCfg();
        if (!connectionCfg.address.isEmpty())
            address = connectionCfg.address;
        if (connectionCfg.port!=0)
            port = connectionCfg.port;


            server = new ServerSocket(port);
            Thread start = new Thread(() -> {

                while (true) {


                    try {
                        socket.add(server.accept());
                        out.add(new DataOutputStream(socket.get(onlineCount).getOutputStream()));

                        in.add(new DataInputStream(new BufferedInputStream
                                (socket.get(onlineCount).getInputStream())));
                        byte[] bytes = new byte[1024];
                        int len;
                        StringBuilder sb = new StringBuilder();
                        while ((len = in.get(onlineCount).read(bytes)) != -1) {
                            sb.append(new String(bytes, 0, len, StandardCharsets.UTF_8));
                        }
                        inputString = sb.toString();
                        JSONObject inputJson = new JSONObject(inputString);
                        synchronized (socket) {
                                JSONObject result = sortProperty.sort(inputJson);
                                String title = inputJson.getString("key");
                                logger.info(" request {} received",title);
                                output(onlineCount, result);
                                onlineCount++;
                            }
                } catch (Exception i) {
                        i.printStackTrace();
                        logger.error("error: " + i);
                    }
                }
            });
                        start.start();
                        start.join();
                        logger.info("java.connection closed request received");
                        System.out.println("Closing connection");
                }

    public void output(int number, JSONObject result) throws IOException {
        String s = result.toString();
        out.get(number).write(s.getBytes(StandardCharsets.UTF_8));
        out.get(number).close();
    }


}