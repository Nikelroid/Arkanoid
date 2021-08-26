import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class prizeAction {
    private static final Logger logger = LogManager.getLogger(prizeAction.class);
    public static void setPaddleConfuse(Main main, boolean setcon) {
        if (setcon != main.CONFUSEDPADDLE) main.PADDLE_SPEED *= -1;
        main.CONFUSEDPADDLE = setcon;

    }

    public static void setFireBall(Main main) {
        for (int j = 0; j < 3; j++)
            if (main.root.getChildren().contains(main.ball[j])) {
                if (main.fireBall) {
                    if(main.normalizer[0]!=null)
                    main.normalizer[0].stop();
                }
                main.ball[j].setImage(new Image("/img/fireBall.png"));
                main.fireBall = true;
                main.normalizer[0] = new Timeline(new KeyFrame(Duration.seconds(2), ev -> {
                    logger.info("Fireball normalized");
                    for (int i = 0; i < 3; i++)
                        if (main.root.getChildren().contains(main.ball[i])) {
                            main.ball[i].setImage(new Image("/img/ballGrey.png"));
                            main.fireBall = false;
                        }
                    if(main.normalizer[0]!=null)
                    main.normalizer[0].stop();
                }));
                main.normalizer[0].setCycleCount(0);
                main.normalizer[0].play();
            }
    }

    public static void setPaddletall(Main main) {
        main.paddle.setImage(new Image(main.getClass().getResourceAsStream("/img/paddleTall.png")));

        if(main.PADDLE_LENTH ==104) main.paddleX = main.paddleX -34;
        if(main.PADDLE_LENTH ==70) main.paddleX = main.paddleX -51;
        main.PADDLE_LENTH = 172;
        if (main.paddleX >=368) main.paddleX =368;
        if (main.paddleX <=0) main.paddleX =0;
    }

    public static void setPaddleshort(Main main) {
        main.paddle.setImage(new Image(main.getClass().getResourceAsStream("/img/paddleShort.png")));
        if(main.PADDLE_LENTH ==104) main.paddleX = main.paddleX +17;
        if(main.PADDLE_LENTH ==172) main.paddleX = main.paddleX +51;
        main.PADDLE_LENTH = 70;
    }

    public static void setPaddlenormal(Main main) {
        main.paddle.setImage(new Image(main.getClass().getResourceAsStream("/img/paddleRed.png")));
        if(main.PADDLE_LENTH ==172) main.paddleX = main.paddleX +34;
        if(main.PADDLE_LENTH ==70) main.paddleX = main.paddleX -17;
        main.PADDLE_LENTH = 104;
        if (main.paddleX >=436) main.paddleX =436;
        if (main.paddleX <=0) main.paddleX =0;
    }

    public static void setBallSpeed(Main main, int speed) {
        switch (speed){
            case 3:
                for (int i = 0; i < 3; i++)
                    if (main.root.getChildren().contains(main.ball[i])) {
                        main.dx[i]*=1.5;
                        main.dy[i]*=1.5;
                    }
               break;
            case 1:
                for (int i = 0; i < 3; i++)
                if (main.root.getChildren().contains(main.ball[i])) {
                    main.dx[i]/=1.5;
                    main.dy[i]/=1.5;
                }
                break;

            case 2:
                for (int i = 0; i < 3; i++)
                    if (main.root.getChildren().contains(main.ball[i])) {
                        if (main.dx[i]<0) main.dx[i]=-sqrt(0.2/(1+pow((main.dy[i]/ main.dx[i]),2)));
                        else main.dx[i]=sqrt(0.2/(1+pow((main.dy[i]/ main.dx[i]),2)));

                        if (main.dy[i]<0) {
                            if (0.2 > pow(main.dx[i], 2)) main.dy[i] = -sqrt(0.2 - pow(main.dx[i], 2));
                            else main.dy[i] = -sqrt(pow(main.dx[i], 2) - 0.2);
                        }else{
                            if (0.2 > pow(main.dx[i], 2)) main.dy[i] = sqrt(0.2 - pow(main.dx[i], 2));
                            else main.dy[i] = sqrt(pow(main.dx[i], 2) - 0.2);
                            }
                    }
                break;
        }
    }

    public static void setPaddleSpeed(Main main, int speed) {

        switch (speed){
            case 3:
                if (main.CONFUSEDPADDLE)main.PADDLE_SPEED -=2;
                else main.PADDLE_SPEED +=2;
                break;
            case 1:
                if (main.CONFUSEDPADDLE)main.PADDLE_SPEED +=2;
                else main.PADDLE_SPEED -=2;
                if(main.PADDLE_SPEED <=0) main.PADDLE_SPEED =0.5;
                break;
            case 2:
                main.PADDLE_SPEED =10;
                break;
        }
    }
}
