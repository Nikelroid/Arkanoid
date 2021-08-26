package profiles;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class getImageFile {
    BufferedImage image;
    public File profile(String username) {
        Path simple = Paths.get("simple.png");
        File file = new File(simple.toAbsolutePath().getParent()+
                "\\main\\src\\resources\\profiles\\"+
                username+ ".png");

        return file;
    }
    public File twitte(String username) {
        Path simple = Paths.get("simple.png");
        File file = new File(simple.toAbsolutePath().getParent()+
                "\\main\\src\\resources\\twittes\\"+
                username+ ".png");

        return file;
    }
    public File message(String username) {
        Path simple = Paths.get("simple.png");
        File file = new File(simple.toAbsolutePath().getParent()+
                "\\main\\src\\resources\\messages\\"+
                username+ ".png");

        return file;
    }
}
