import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class setPrizes {
    private static final Logger logger = LogManager.getLogger(setPrizes.class);
    private static final String[] PRIZE_IMAGE= new String[]{"/img/element_red_diamond_glossy.png"
            , "/img/element_red_polygon_glossy.png", "/img/element_yellow_diamond_glossy.png"
            , "/img/element_yellow_polygon_glossy.png", "/img/element_green_polygon_glossy.png"
            , "/img/element_green_diamond_glossy.png" , "/img/element_blue_polygon_glossy.png"
             ,"/img/element_purple_diamond_glossy.png" ,"/img/element_purple_polygon_glossy.png"
   , "/img/element_grey_polygon_glossy.png"};

    protected static void setPrize(Main main, int i) {
        logger.info("Prize added");
        int random_int = (int) Math.floor(Math.random() * 10);
        main.prizeType[main.prizenumber] = random_int;
        int row = i/8;
        int column  = i%8;
            Image img = new Image(PRIZE_IMAGE[random_int]);
            main.prizes[main.prizenumber] = new ImageView(img);
            main.prizes[main.prizenumber].setImage(img);
            main.prizes[main.prizenumber].setX(18 + (65 * column));
            main.prizes[main.prizenumber].setY(10 + (33 * row));
            main.root.getChildren().add(main.prizes[main.prizenumber]);
        main.prizenumber++;
    }
}
