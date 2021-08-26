import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class difineLoops {

    public difineLoops() {
    }
    private static final Logger logger = LogManager.getLogger(difineLoops.class);
    public void difineLoops(Main main) {

        main.loop = new Timeline(new KeyFrame(Duration.millis(2), new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent t) {

                main.q = 0;
                for (int i = 0; i < 80; i++)
                    if (main.view[i].getImage() != null) main.q++;
                if (main.q == 0 && !main.Restart) {
                    try {
                        logger.info("User wins the game");
                        main.win = true;
                        main.start(main.stage);
                    } catch (Exception e) {
                        logger.error("An error when user wins the game happend");
                    }
                }
                if (main.remainBlocks.getText() != null)
                    main.remainBlocks.setText(main.q + " blocks remain");
                if (main.q < 11 && main.remainBlocks.getText() != null)
                    main.remainBlocks.setText("Just " + main.q + " blocks remain!");
                if (main.q == 1 && main.remainBlocks.getText() != null)
                    main.remainBlocks.setText("Just one blocks remain!");

                main.score.setText("Score: " + main.SCORE);

                for (int j = 0; j < 3; j++) {
                    if (main.root.getChildren().contains(main.ball[j])) {
                        main.ball[j].setX(main.ball[j].getX() + main.dx[j]);
                        main.ball[j].setY(main.ball[j].getY() + main.dy[j]);
                        if (main.fireBall) main.ball[j].rotateProperty().setValue(5 * main.ball[j].getY());


                        final boolean paddlePositionY = main.ball[j].getY() >= 478 && main.ball[j].getY() <= 485 &&
                                main.ball[j].getX() <= main.PADDLE_LENTH - 3 + main.paddleX && main.ball[j].getX() >= main.paddleX - 19;
                        final boolean paddlePositionX = (main.ball[j].getY() >= 487 && main.ball[j].getY() <= 524)
                                && ((main.ball[j].getX() == main.paddleX - 17 && main.ball[j].getX() >= main.paddleX - 22) ||
                                (main.ball[j].getX() <= main.PADDLE_LENTH + main.paddleX && main.ball[j].getX() >= main.PADDLE_LENTH - 5 + main.paddleX));
                        final boolean atRightBorder = main.ball[j].getX() >= (518);
                        final boolean atLeftBorder = main.ball[j].getX() <= (0);
                        final boolean atBottomBorder = main.ball[j].getY() >= (578);
                        final boolean atTopBorder = main.ball[j].getY() <= (0);


                        int posation = 0;
                        double vecolity = pow(main.dx[j], 2) + pow(main.dy[j], 2);
                        double distance = -(main.paddleX + (main.PADDLE_LENTH / 2) - 11 - (main.ball[j].getX()));

                        for (int i = 0; i < 80; i++)
                            if (main.root.getChildren().contains(main.view[i])) {
                                boolean mainBlock = (main.ball[j].getY() <= main.view[i].getY() + 32)
                                        && (main.ball[j].getY() >= main.view[i].getY() - 22)
                                        && (main.ball[j].getX() >= main.view[i].getX() - 22)
                                        && (main.ball[j].getX() <= main.view[i].getX() + 64);
                                boolean inBlock = (main.ball[j].getY() <= main.view[i].getY() + 28)
                                        && (main.ball[j].getY() >= main.view[i].getY() - 18)
                                        && (main.ball[j].getX() >= main.view[i].getX() - 18)
                                        && (main.ball[j].getX() <= main.view[i].getX() + 60);
                                boolean yBlock = (main.ball[j].getY() <= main.view[i].getY() - 20 && main.ball[j].getY() >= main.view[i].getY() - 24)
                                        || (main.ball[j].getY() <= main.view[i].getY() + 34 && main.ball[j].getY() >= main.view[i].getY() + 30);
                                boolean xBlock = (main.ball[j].getX() <= main.view[i].getX() - 20 && main.ball[j].getX() >= main.view[i].getX() - 24)
                                        || (main.ball[j].getX() >= main.view[i].getX() + 62 && main.ball[j].getX() <= main.view[i].getX() + 66);


                                if (mainBlock && main.view[i].getImage() != null && !main.view[i].getImage().isError()) {

                                    if (!main.fireBall) {

                                        if (main.blockType[i] == 0 || main.blockType[i] == 6) {
                                            logger.info("Normal ball destroyed a block");
                                            if (xBlock) {
                                                main.SCORE++;
                                                main.dx[j] *= -1;
                                                main.root.getChildren().remove(main.view[i]);
                                                main.view[i] = new ImageView();
                                            }
                                            if (yBlock) {
                                                main.SCORE++;
                                                main.dy[j] *= -1;
                                                main.root.getChildren().remove(main.view[i]);
                                                main.view[i] = new ImageView();
                                            }
                                        } else if (main.blockType[i] == 4) {
                                            logger.info("Normal ball could not destroy a block");
                                            if (inBlock) {
                                                main.SCORE++;
                                                main.dy[j] *= -1;
                                                main.dx[j] *= -1;
                                                main.root.getChildren().remove(main.view[i]);
                                                main.view[i] = new ImageView();
                                            }
                                            if (xBlock) {
                                                main.dx[j] *= -1;
                                                main.blockType[i] = 0;
                                            }
                                            if (yBlock) {
                                                main.dy[j] *= -1;
                                                main.blockType[i] = 0;
                                            }
                                        } else if (main.blockType[i] == 3) {
                                            logger.info("Normal ball could not destroy a block");
                                            if (inBlock) {
                                                main.SCORE++;
                                                main.dy[j] *= -1;
                                                main.dx[j] *= -1;
                                                main.root.getChildren().remove(main.view[i]);
                                                main.view[i] = new ImageView();
                                            }
                                            if (xBlock) {
                                                main.dx[j] *= -1;
                                                main.blockType[i] = 4;
                                            }
                                            if (yBlock) {
                                                main.dy[j] *= -1;
                                                main.blockType[i] = 4;
                                            }
                                        } else if (main.blockType[i] == 5) {
                                            logger.info("Normal ball destroy a mirror block");
                                            if (inBlock) {
                                                main.SCORE++;
                                                main.dy[j] *= -1;
                                                main.dx[j] *= -1;
                                                main.root.getChildren().remove(main.view[i]);
                                                main.view[i] = new ImageView();
                                            }
                                            if (xBlock) {
                                                main.SCORE++;
                                                main.dy[j] *= -1;
                                                main.dx[j] *= -1;
                                                main.root.getChildren().remove(main.view[i]);
                                                main.view[i] = new ImageView();
                                            }
                                            if (yBlock) {
                                                main.SCORE++;
                                                main.dy[j] *= -1;
                                                main.dx[j] *= -1;
                                                main.root.getChildren().remove(main.view[i]);
                                                main.view[i] = new ImageView();
                                            }
                                        } else if (main.blockType[i] == 1 && main.root.getChildren().contains(main.view[i])) {
                                            logger.info("Normal ball destroy a blinky block");
                                            if (xBlock) {
                                                main.SCORE += 2;
                                                main.dx[j] *= -1;
                                                main.root.getChildren().remove(main.view[i]);
                                                main.view[i] = new ImageView();
                                            }
                                            if (yBlock) {
                                                main.SCORE += 2;
                                                main.dy[j] *= -1;
                                                main.root.getChildren().remove(main.view[i]);
                                                main.view[i] = new ImageView();
                                            }
                                        } else if (main.blockType[i] == 2) {
                                            logger.info("Normal ball destroy a prize block");

                                            if (xBlock) {
                                                main.SCORE++;
                                                main.dx[j] *= -1;
                                                main.root.getChildren().remove(main.view[i]);
                                                main.view[i] = new ImageView();
                                            }
                                            if (yBlock) {
                                                main.SCORE++;
                                                main.dy[j] *= -1;
                                                main.root.getChildren().remove(main.view[i]);
                                                main.view[i] = new ImageView();

                                            }
                                            setPrizes.setPrize(main, i);
                                        }
                                    } else {
                                        logger.info("Fire ball destroyed a block");
                                        if (xBlock) {
                                            main.SCORE++;
                                            main.root.getChildren().remove(main.view[i]);
                                            main.view[i] = new ImageView();
                                        }
                                        if (yBlock) {
                                            main.SCORE++;
                                            main.root.getChildren().remove(main.view[i]);
                                            main.view[i] = new ImageView();
                                        }
                                        if (main.blockType[i] == 2) setPrizes.setPrize(main, i);
                                    }
                                    break;
                                }
                            }

                        if (atRightBorder || atLeftBorder) {
                            logger.info("Ball Touched borders");
                            main.dx[j] *= -1;
                        }
                        if (paddlePositionY) {
                            logger.info("Ball Touched Paddle");
                            if (main.dy[j] > 0) {
                                if (distance != 0) {
                                    double M1;
                                    double temp = main.dx[j];
                                    double M2 = main.dy[j] / main.dx[j];
                                    if ((4500 * main.PADDLE_LENTH / 104) > pow(distance, 2))
                                        M1 = (-15 * distance) / (60 * sqrt((4500 * main.PADDLE_LENTH / 104) - pow(distance, 2)));
                                    else {
                                        System.out.println("HI");
                                        M1 = (-15 * distance) / (60 * sqrt(pow(distance, 2) - (4500 * main.PADDLE_LENTH / 104)));
                                    }

                                    M1 = 1 / M1;
                                    double M3 = ((2 * M1) + (M2 * pow(M1, 2)) - M2) / (2 * M1 * M2 - pow(M1, 2) + 1);
                                    main.dy[j] = -sqrt(vecolity / (1 + (1 / pow(M3, 2))));
                                    if (main.dy[j] > -0.02) main.dy[j] = 0.02;
                                    if (vecolity > pow(main.dy[j], 2))
                                        main.dx[j] = -sqrt(vecolity - pow(main.dy[j], 2));
                                    else {
                                        System.out.println("HI");
                                        main.dx[j] = -sqrt(pow(main.dy[j], 2) - vecolity);
                                    }


                                    if (temp > 0) main.dx[j] *= -1;
                                    if ((temp > -0.1 && temp < 0 && distance - (main.PADDLE_LENTH / 2) + 35 > 0 && main.dx[j] < 0) ||
                                            (temp < 0.1 && temp > 0 && distance + (main.PADDLE_LENTH / 2) - 35 < 0 && main.dx[j] > 0))
                                        main.dx[j] *= -1;

                                } else main.dy[j] *= -1;
                            }
                        } else if (atTopBorder) {
                            logger.info("Ball Touched Top of border");
                            main.dy[j] *= -1;
                        } else if (atBottomBorder) {
                            logger.info("A ball fell");
                            main.root.getChildren().remove(main.ball[j]);
                            main.ballCount = 0;
                            for (int i = 0; i < 3; i++)
                                if (main.root.getChildren().contains(main.ball[i])) {
                                    main.ballCount++;
                                }
                            if (main.ballCount == 0) {
                                logger.info("User lose");
                                main.endgame.gameEnd(main);
                            }
                        }
                        for (int k = 0; k < main.prizes.length; k++) {
                            if (main.root.getChildren().contains(main.prizes[k])) {
                                try {
                                    boolean prizeTouch =
                                            (main.paddleX - 25 <= main.prizes[k].getX() && main.paddleX + main.PADDLE_LENTH - 25 >= main.prizes[k].getX()
                                                    && 460 <= main.prizes[k].getY() && 510 >= main.prizes[k].getY());
                                    if (main.root.getChildren().contains(main.prizes[k]) && prizeTouch) {
                                        logger.info("User got prize!");
                                        main.root.getChildren().remove(main.prizes[k]);
                                        givePrize.givePrize(main, main.prizeType[k]);
                                    }
                                } catch (NullPointerException | IOException ignored) {
                                }
                            }
                        }
                    }
                }
            }
        }));

        main.timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            for (int i = 0; i < 80; i++) {
                if (main.blockType[i] == 1) {
                    if (main.root.getChildren().contains(main.view[i])) {
                        main.root.getChildren().remove(main.view[i]);
                    } else {
                        for (int j = 0; j < 3; j++)
                            if (main.ball[j]!=null){
                            boolean mainBlock = (main.ball[j].getY() <= main.view[i].getY() + 32)
                                    && (main.ball[j].getY() >= main.view[i].getY() - 22)
                                    && (main.ball[j].getX() >= main.view[i].getX() - 22)
                                    && (main.ball[j].getX() <= main.view[i].getX() + 64);
                            if (mainBlock) {
                                main.root.getChildren().remove(main.view[i]);
                                main.view[i] = new ImageView();
                                main.SCORE+=2;
                                main.dx[j] *= -1;
                                main.dy[j] *= -1;
                                logger.error("Ball was in blinky block");
                            } else
                            if (!main.root.getChildren().contains(main.view[i]))
                                main.root.getChildren().add(main.view[i]);
                        }
                    }
                }
            }
        }));

        main.downer = new Timeline(new KeyFrame(Duration.seconds(30), ev -> {
            logger.info("blockes came down");
            main.remainBlocks.setY(25);
            main.remainBlocks.setX(5);
            main.remainBlocks.setFont(main.font);
            main.remainBlocks.setFill(Color.INDIANRED);
            main.remainBlocks.setText(main.q + " block remain");
            if (!main.root.getChildren().contains(main.remainBlocks))
            main.root.getChildren().add(main.remainBlocks);
            main.SCORE-=10;
            for (int i = 0; i < 80; i++) {
                if(main.view[i]!=null)
                    if ( main.view[i].getY() + 65 >= 500)
                        main.endgame.gameEnd(main);
                    else {
                        main.view[i].setY(main.view[i].getY() + 33);
                        for (int j = 0; j < 3; j++) {
                            if (main.ball[j]!=null) {
                                boolean existBallIn = (main.ball[j].getY() >= main.view[i].getY() - 24 && main.ball[j].getX() >= main.view[i].getX() - 24)
                                        && (main.ball[j].getY() <= main.view[i].getY() + 34 && main.ball[j].getX() <= main.view[i].getX() + 66);

                                if (existBallIn) {
                                    main.SCORE++;
                                    main.root.getChildren().remove(main.view[i]);
                                    main.view[i] = new ImageView();
                                    main.dx[j]*=-1; main.dy[j]*=-1;
                                    logger.error("Ball was in a block");
                                }
                            }
                        }
                    }
            }
        }));

        main.timer = new AnimationTimer() {

            @Override
            public void handle(long now) {

                for (int i = 0; i < main.prizes.length; i++) {
                    if (main.root.getChildren().contains(main.prizes[i])) {
                        main.prizeY = main.prizes[i].getY();
                        main.prizeY++;
                        main.prizes[i].rotateProperty().setValue(5 * main.prizeY);
                        main.prizes[i].setY(main.prizeY);
                    }
                }

                if (main.right)
                    main.paddleX += main.PADDLE_SPEED;
                if (main.left)
                    main.paddleX -= main.PADDLE_SPEED;
                if (main.paddleX >= 540  - main.PADDLE_LENTH)
                    main.paddleX = (int) Math.floor(540  - main.PADDLE_LENTH);
                if (main.paddleX <= 0) main.paddleX=0;
                main.paddle.setX(main.paddleX);
            }
        };


    }
}
