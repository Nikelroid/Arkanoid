import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class setObjects {
    private static final Logger logger = LogManager.getLogger(setObjects.class);
    private static final String PADDLE_IMAGE= "/img/paddleRed.png";
    private static final String BALL_IMAGE= "/img/ballGrey.png";
    private static final String[] START_BUTTON= new String[]{"/img/element_yellow_rectangle.png"
            , "/img/element_grey_rectangle.png"};

    public void startButton(Main main, Stage stage) {
        logger.info("Button added");
            javafx.scene.image.Image img = new Image(setObjects.START_BUTTON[0]);
            ImageView view = new ImageView(img);
            main.button = new Button();
            main.button.setTranslateX(150);
            main.button.setTranslateY(500);
            main.button.setPadding(Insets.EMPTY);
            main.button.setGraphic(view);
        main.button.setOnMouseEntered((MouseEvent event) -> {
            main.button.setGraphic(new ImageView(main.selected)); });
        main.button.setOnMouseExited((MouseEvent event) -> {
            main.button.setGraphic(new ImageView(main.unselected));
        });
    }

    protected static void setPaddle(Main main, Stage stage) throws IOException {
        logger.info("Paddle added");
                    Image img = new Image(setObjects.PADDLE_IMAGE);
                    main.paddle = new ImageView(img);
                  main.paddle.setImage(img);
                  main.paddle.setX(main.paddleX);
                  main.paddle.setY(500);
            }

    protected static void setBall(Main main, Stage stage) throws IOException {
        logger.info("Ball added");
                Image img = new Image(setObjects.BALL_IMAGE);
                main.ball[0] = new ImageView(img);
                main.ball[0].setImage(img);
                main.ball[0].setFocusTraversable(true);
                main.ball[0].setX(main.ballX);
                main.ball[0].setY(main.ballY);
            }

    protected static void setExteraball(Main main, Stage stage) throws IOException {
        logger.info("Extra added");
        for (int i = 0; i < 3; i++) {
            if (!main.root.getChildren().contains(main.ball[i])) {
                main.dx[i] = Math.random() / 2.5;
                main.dy[i] = -sqrt(0.2 - pow(main.dx[i], 2));
            }


            if (!main.root.getChildren().contains(main.ball[i])) {
                Image img = new Image(setObjects.BALL_IMAGE);
                main.ball[i] = new ImageView(img);
                main.ball[i].setImage(img);
                main.ball[i].setFocusTraversable(true);
                main.ball[i].setX(180 * i);
                main.ball[i].setY(500);
                main.root.getChildren().add(main.ball[i]);
            }
        }

    }

}
