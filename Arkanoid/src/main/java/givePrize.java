import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static java.lang.Math.pow;

public class givePrize {
    private static final Logger logger = LogManager.getLogger(givePrize.class);
    public static void givePrize(Main main, int prizenumber) throws IOException {
        main.SCORE+=5;
        logger.info("User want to fet a prize");
        if (prizenumber == 9)
            prizenumber = (int) Math.floor(Math.random() * 9);
        switch (prizenumber) {

            case 0:
                logger.info("Fire ball given to user");
                prizeAction.setFireBall(main);
                break;
            case 1:
                logger.info("3 ball given to user");
                setObjects.setExteraball(main, main.stage);
                break;

            case 2:
                logger.info("Tall paddle given to user");
                if (main.PADDLE_LENTH != 104)
                    if(main.normalizer[1]!=null)
                    main.normalizer[1].stop();
                prizeAction.setPaddletall(main);
                main.normalizer[1] = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
                    logger.info("Tall paddle Normalized");
                    prizeAction.setPaddlenormal(main);
                    if(main.normalizer[1]!=null)
                    main.normalizer[1].stop();
                }));
                main.normalizer[1].setCycleCount(0);
                main.normalizer[1].playFromStart();
                break;
            case 3:
                logger.info("Short paddle given to user");
                if (main.PADDLE_LENTH != 104)
                    if(main.normalizer[1]!=null)
                    main.normalizer[1].stop();
                prizeAction.setPaddleshort(main);
                main.normalizer[1] = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
                    logger.info("Short paddle Normalized");
                    prizeAction.setPaddlenormal(main);
                    if(main.normalizer[1]!=null)
                    main.normalizer[1].stop();
                }));
                main.normalizer[1].setCycleCount(0);
                main.normalizer[1].playFromStart();
                break;
            case 4:
                logger.info("Fast ball given to user");
                checkBalls(main);
                prizeAction.setBallSpeed(main, 3);
                main.normalizer[2] = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
                    logger.info("Fast ball  Normalized");
                    prizeAction.setBallSpeed(main, 2);
                    if(main.normalizer[2]!=null)
                    main.normalizer[2].stop();
                }));
                main.normalizer[2].setCycleCount(0);
                main.normalizer[2].playFromStart();
                break;
            case 5:
                logger.info("Slow ball given to user");
                checkBalls(main);
                if(main.normalizer[2]!=null)
                main.normalizer[2].stop();
                prizeAction.setBallSpeed(main, 1);
                main.normalizer[2] = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
                    logger.info("Slow ball normalized");
                    prizeAction.setBallSpeed(main, 2);
                    if(main.normalizer[2]!=null)
                    main.normalizer[2].stop();
                }));
                main.normalizer[2].setCycleCount(0);
                main.normalizer[2].playFromStart();
                break;

            case 6:
                logger.info("Confused paddle given to user");
                if (main.CONFUSEDPADDLE)
                    if(main.normalizer[4]!=null)
                    main.normalizer[4].stop();
                prizeAction.setPaddleConfuse(main, true);
                main.normalizer[4] = new Timeline(new KeyFrame(Duration.seconds(7), ev -> {
                    logger.info("Confused paddle normalized");
                    prizeAction.setPaddleConfuse(main, false);
                    if(main.normalizer[4]!=null)
                    main.normalizer[4].stop();
                }));
                main.normalizer[4].setCycleCount(0);
                main.normalizer[4].playFromStart();
                break;

            case 7:
                logger.info("Fast paddle given to user");
                if (main.PADDLE_SPEED != 10)
                    if(main.normalizer[3]!=null)
                    main.normalizer[3].stop();
                prizeAction.setPaddleSpeed(main, 3);
                main.normalizer[3] = new Timeline(new KeyFrame(Duration.seconds(10), ev -> {
                    logger.info("Fast paddle normalized");
                    prizeAction.setPaddleSpeed(main, 2);
                    if(main.normalizer[3]!=null)
                    main.normalizer[3].stop();
                }));
                main.normalizer[3].setCycleCount(0);
                main.normalizer[3].playFromStart();
                break;

            case 8:
                logger.info("Slow paddle given to user");
                if (main.PADDLE_SPEED != 10)
                    if(main.normalizer[3]!=null)
                    main.normalizer[3].stop();
                prizeAction.setPaddleSpeed(main, 1);
                main.normalizer[3] = new Timeline(new KeyFrame(Duration.seconds(10), ev -> {
                    logger.info("Slow paddle normalized");
                    prizeAction.setPaddleSpeed(main, 3);
                    if(main.normalizer[3]!=null)
                    main.normalizer[3].stop();
                }));
                main.normalizer[3].setCycleCount(0);
                main.normalizer[3].playFromStart();
                break;


        }
    }

    private static void checkBalls(Main main) {
        for (int i = 0; i < 3; i++)
            if (main.root.getChildren().contains(main.ball[i])) {
                if (pow(main.dy[i], 2) + pow(main.dx[i], 2) < 2.1 && pow(main.dy[i], 2) + pow(main.dx[i], 2) > 1.9)
                    if(main.normalizer[2]!=null)
                    main.normalizer[2].stop();
            }
    }
}
