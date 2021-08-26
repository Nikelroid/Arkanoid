package com.java.launch;

import com.java.botImport.botInputThread;
import com.java.connection.getter;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;


public class Main {
    private static final Logger logger = LogManager.getLogger(launchCfg.class);
    public static void main(String[] args) throws IOException {

            Thread mainThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        new getter();
                    } catch (IOException | InterruptedException e) {
                        logger.error("error in load getter class");
                    }
                }
            });
            mainThread.start();

        botInputThread botThread = new botInputThread();
        botThread.start();

//        Path simple = Paths.get("simple.png");
//        File outputFile = new File("simple.png");
//
//        byte[] fileContent = new byte[0];
//        String encodedString = null;
//
//
//            File file = new File("simple.png");
//
//
//            try {
//                fileContent = FileUtils.readFileToByteArray(file);
//                encodedString = Base64.getEncoder().encodeToString(fileContent);
//            } catch (IOException ioException) {
//                encodedString = "";
//            }
//            System.out.println(encodedString);



//        int width = 240;
//        int height = 240;
//        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        Graphics2D g2d = bufferedImage.createGraphics();
//        g2d.setColor(Color.white);
//        g2d.fillRect(0, 0, width, height);
//        g2d.setColor(Color.black);
//        g2d.drawLine(0, 80, 240, 80);
//        g2d.drawLine(0, 160, 240, 160);
//        g2d.drawLine(80, 0, 80, 240);
//        g2d.drawLine(160, 0, 160, 240);
//        g2d.setColor(Color.orange);
//        g2d.setFont(g2d.getFont().deriveFont(80f));
//        int count = 1;
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                g2d.drawString("X", 12+(80*j), 70+(80*i));
//                count++;
//            }
//        }
//        g2d.dispose();
//        File file = new File("start.png");
//        ImageIO.write(bufferedImage, "png", file);

        }
}
