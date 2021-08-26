package com.java.botImport;

import com.java.botRegister.deleteBots;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.Scanner;

public class botInputThread extends Thread{
    com.java.botRegister.deleteBots deleteBots = new deleteBots();
    @Override
    public void run() {
        while (true) {

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            //commands
            System.out.println("Insert Bot Url:");
            Scanner input = new Scanner(System.in);
            String address = input.next();
            if (address.equals("DELETE")){
                System.out.println("Are you sure?");
                if (input.next().equals("YES")){
                    deleteBots.deleter();
                }
            }else {
                try {
                    new botFetchUrl(address);
                } catch (ClassNotFoundException |
                        NoSuchFieldException |
                        IllegalAccessException |
                        NoSuchMethodException |
                        InvocationTargetException |
                        IOException e) {
                    JOptionPane.showMessageDialog(null,
                            "Error in add robot. check URL and try again.");
                    e.printStackTrace();
                }
            }

        }
    }

}