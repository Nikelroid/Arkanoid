import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class blocks {

    private static final String[] BLOCKS_IMAGE= new String[]{"/img/element_blue_rectangle_glossy.png"
            , "/img/element_green_rectangle_glossy.png", "/img/element_purple_rectangle_glossy.png"
            , "/img/element_red_rectangle_glossy.png", "/img/element_yellow_rectangle_glossy.png"
            , "/img/element_grey_rectangle_glossy.png" , "/img/element_null.png"};
    private static final Logger logger = LogManager.getLogger(blocks.class);
    protected static void setBlock(Main main, Stage stage) throws IOException {
        for (int j = 0; j < 10; j++) {
        for (int i = 0; i < 8; i++) {
                int random_int = (int) Math.floor(Math.random() * 8);
                main.blockType[main.blockCounter] = random_int;
                if (random_int < 7) {
                    Image img = new Image(BLOCKS_IMAGE[random_int]);
                    main.view[main.blockCounter] = new ImageView(img);
                    main.view[main.blockCounter].setImage(img);
                    main.view[main.blockCounter].setX(10 + (65 * i));
                    main.view[main.blockCounter].setY(10 + (33 * j));
                } else {
                    main.view[main.blockCounter] = new ImageView();
                }
                main.blockCounter++;
            }
        }
        logger.info("Playground made");
    }
}
