import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


public class Main extends Application {

    boolean win =false;
    LeaderBoard leaderBoard = new LeaderBoard();
    ArrayList<ArrayList<Integer>> test = new ArrayList<>();
    ArrayList<String> submitLeaderBoard = new ArrayList<>();
    String name;
    Text inftx = null;
    int SCORE=0;
    int LIFE=3;
    ImageView[] Life = new ImageView[3];
    boolean fireBall = false;
    int blockCounter = 0;
    ImageView paddle;
    ImageView[] ball = new ImageView[3];
    Stage stage;
    Button button = new Button();
    int paddleX = 218;
    int ballX = 259, ballY = 478;
    boolean Gameover=false;
    double[] dx = new double[3];
    double[] dy = new double[3];
    double PADDLE_LENTH=104;
    double PADDLE_SPEED=10;
    double BALL_SPEED=2;
    boolean right;
    boolean left;
    Group root;
    Image selected = new Image(getClass().getResourceAsStream("/img/buttonSelected.png"));
    Image sborder = new Image(getClass().getResourceAsStream("/img/startBorder.png"));
    Image s0border = new Image(getClass().getResourceAsStream("/img/0lives.png"));
    Image s1border = new Image(getClass().getResourceAsStream("/img/1lives.png"));
    Image s2border = new Image(getClass().getResourceAsStream("/img/2lives.png"));
    Image go = new Image(getClass().getResourceAsStream("/img/gameover.png"));
    Image winpic = new Image(getClass().getResourceAsStream("/img/win.png"));
    Image unselected = new Image(getClass().getResourceAsStream("/img/buttonDefault.png"));
    ImageView[] view = new ImageView[80];
    int[] blockType = new int[80];
    ImageView[] prizes = new ImageView[100];
    int[] prizeType = new int[200];
    int prizenumber = 0;
    double prizeY;
    Timeline[] normalizer = new Timeline[5];
    boolean CONFUSEDPADDLE;
    int ballCount=0;
    Scene scene;
    ImageView start;
    TextField getName = new TextField();
    Button submit = new Button();
    Button cancel= new Button();
    javafx.scene.text.Font font = new javafx.scene.text.Font("RAVIE",23);
    Text score = new Text("Score : 0");
    Text showScore = new Text();
    Text remainBlocks = new Text();
    Text Showpause = new Text();
    boolean lock=false;
    boolean sub = false;
    boolean[] pause = new boolean[10];
    boolean Restart = false;
    int q=0;

void cleanup(){

    if (downer!=null)
    downer.stop();
    if (loop!=null)
    loop.stop();
    if (timeline!=null)
    timeline.stop();
    if (timer!=null)
    timer.stop();

    downer = null;
    loop = null;
    timeline = null;
    timer = null;

    downer = new Timeline();
    loop = new Timeline();
    timeline = new Timeline();
    timer = new AnimationTimer() {
        @Override
        public void handle(long l) {

        }
    };


    for (int i = 0; i < 5; i++) {
        if (normalizer[i] != null)
            normalizer[i].stop();
        normalizer[i] = null;
        normalizer[i] = new Timeline();
    }
}

    void setup() {



        fireBall = false;
        blockCounter = 0;
        ball = new ImageView[3];
        button = new Button();
        paddleX = 218;
        ballX = 259;
        ballY = 478;
        dx = new double[3];
        dy = new double[3];
        PADDLE_LENTH=104;
        PADDLE_SPEED=10;
        BALL_SPEED=2;
        selected = new Image(getClass().getResourceAsStream("/img/buttonSelected.png"));
        unselected = new Image(getClass().getResourceAsStream("/img/buttonDefault.png"));
        view = new ImageView[80];
        blockType = new int[80];
        prizes = new ImageView[100];
        prizeType = new int[100];
        prizenumber = 0;
        cleanup();
        CONFUSEDPADDLE=false;
        ballCount=0;
        lock=false;
        showScore = new Text();
        sub = false;
        pause = new boolean[]{false, false, false, false, false, false, false, false, false,false};
        q = 0;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        ArrayList<Integer> Hi  = new ArrayList<>();
        Hi.add(45);
        Hi.add(11);
        test.add(Hi);
        leaderBoard.saveGame(test);

        if(LIFE!=3 || win || Restart)
        setup();

        if(LIFE==3 && !win && !Restart)
            startDialog();
        if (Restart) win=false;

        Showpause.setFont(font);
        Showpause.setText("Paused. press P to resume");
        Showpause.setFill(Color.ORANGERED);
        Showpause.setX(70);
        Showpause.setY(500);

        score.setFill(Color.DARKTURQUOISE);
        primaryStage.setTitle("Arkanoid");
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        stage = primaryStage;
        stage.getIcons().add(new Image("/icon.png"));
        setObjects.setPaddle(this, primaryStage);
        setObjects.setBall(this, primaryStage);
        blocks.setBlock(this, primaryStage);
        for (int i = 0; i < 3; i++) {
            dx[i] = Math.random() / 2.5;
            dy[i] = -sqrt(0.2 - pow(dx[i], 2));
        }
        root = new Group(paddle, ball[0]);

        for (int i = 0; i < LIFE; i++) {
            Life[i] = new ImageView(new Image("/img/life.png"));
            Life[i].setX(472-(66*i));
            Life[i].setY(570);
            root.getChildren().add(Life[i]);
        }

        score.setFont(font);
        score.setX(10);
        score.setY(590);

        scene = new Scene(root, 540, 600);

        start = new ImageView();
        showScore.setY(100);
        showScore.setX(165);
        showScore.setFont(font);
        if (LIFE!=0)
        showScore.setText("Your score: "+SCORE);


        if(LIFE!=3 || win) {
            root.getChildren().add(showScore);
            root.getChildren().remove(score);
        }



        if (!win) {
            if (Gameover)
                Finish();
            if (LIFE == 1) start.setImage(s1border);
            if (LIFE == 0 && !Gameover) start.setImage(s0border);
            if (LIFE == 2) start.setImage(s2border);
            if (LIFE == 3) start.setImage(sborder);
            start.setX(120);
            start.setY(150);
            root.getChildren().add(start);
            Restart =false;
        }else{
            win=false;
            sub = true;
            SCORE=SCORE*2;
            start.setImage(winpic);
            start.setX(120);
            start.setY(150);
            root.getChildren().add(start);
            showScore.setText("Your score: "+SCORE);
            if (!root.getChildren().contains(showScore))
            root.getChildren().add(showScore);
        }
        scene.setOnKeyPressed(event -> {

            if(Gameover && event.getCode()==KeyCode.ENTER && !lock) {
                submiter();
                start.setImage(go);
                root.getChildren().removeAll(submit, getName, cancel);
            }
            if(Gameover && lock && event.getCode()==KeyCode.SPACE) {
                System.exit(1);
            }

                    switch (event.getCode()) {
                        case RIGHT -> right = true;
                        case LEFT -> left = true;
                        case SPACE -> {
                            if((LIFE!=3 || sub) && !Gameover)
                            root.getChildren().remove(showScore);
                            if(!root.getChildren().contains(score) && !Gameover  ) {
                                root.getChildren().add(score);
                                root.getChildren().remove(start);
                                root.getChildren().remove(inftx);
                                for (int i = 0; i < 80; i++)
                                    if (view[i]!=null &&!root.getChildren().contains(view[i]))
                                        root.getChildren().add(view[i]);
                                difineLoops();
                                loop.setCycleCount(Animation.INDEFINITE);
                                loop.play();
                                timeline.setCycleCount(Animation.INDEFINITE);
                                timeline.play();
                                downer.setCycleCount(Animation.INDEFINITE);
                                downer.play();
                                timer.start();
                                sub =false;
                                Restart = false;
                            }
                        }
                        case P -> {
                            if (pause[0]){
                                if (loop!=null && pause[1]) pauser(1,loop,true);
                                if (timeline!=null && pause[2]) pauser(2,timeline,true);
                                if (downer!=null && pause[3]) pauser(3,downer,true);
                                if (timer!=null && pause[4]) {
                                    pause[4] = false;
                                    timer.start();
                                    root.getChildren().remove(Showpause);
                                }
                                if (normalizer[0]!=null && pause[5]) pauser(5,normalizer[0],true);
                                if (normalizer[1]!=null && pause[6]) pauser(6,normalizer[1],true);
                                if (normalizer[2]!=null && pause[7]) pauser(7,normalizer[2],true);
                                if (normalizer[3]!=null && pause[8]) pauser(8,normalizer[3],true);
                                if (normalizer[4]!=null && pause[9]) pauser(9,normalizer[4],true);
                                pause[0]=false;
                            }else{
                                if (loop!=null) pauser(1,loop,false);
                                if (timeline!=null) pauser(2,timeline,false);
                                if (downer!=null) pauser(3,downer,false);
                                if (timer!=null) {
                                    pause[4] = true;
                                    timer.stop();
                                    root.getChildren().add(Showpause);

                                }
                                if (normalizer[0]!=null) pauser(5,normalizer[0],false);
                                if (normalizer[1]!=null) pauser(6,normalizer[1],false);
                                if (normalizer[2]!=null) pauser(7,normalizer[2],false);
                                if (normalizer[3]!=null) pauser(8,normalizer[3],false);
                                if (normalizer[4]!=null) pauser(9,normalizer[4],false);
                                pause[0]=true;
                            }
                        }
                        case R -> {

                            LIFE=3;
                            SCORE=0;
                            Restart =true;
                            win =false;
                            try {
                                restart();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

        });

        scene.setOnKeyReleased(event -> {

                        switch (event.getCode()) {
                            case RIGHT -> right = false;
                            case LEFT -> left = false;
                        }
        });
        primaryStage.setScene(scene);
        stage.show();
    }
    Timeline loop,downer,timeline;
    AnimationTimer timer;

    public void pauser(int i,Timeline T,boolean on){
        if (on) {
            pause[i] = false;
            T.play();
        }else{
            pause[i] = true;
            T.pause();
        }
    }

    public void difineLoops() {

        loop = new Timeline(new KeyFrame(Duration.millis(2), new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent t) {

                q = 0;
                for (int i = 0; i < 80; i++)
                      if (view[i].getImage()!=null) q++;
                if (q == 0 && !Restart) {
                    try {
                        win = true;
                        restart();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (remainBlocks.getText() != null)
                    remainBlocks.setText(q + " blocks remain");
                    if (q==1 && remainBlocks.getText()!=null)
                        remainBlocks.setText("Just one blocks remain!");

                score.setText("Score: "+SCORE);

                for (int j = 0; j < 3; j++) {
                    if (root.getChildren().contains(ball[j])) {
                        ball[j].setX(ball[j].getX() + dx[j]);
                        ball[j].setY(ball[j].getY() + dy[j]);
                        if (fireBall) ball[j].rotateProperty().setValue(10 * ball[j].getY());


                        final boolean paddlePositionY = ball[j].getY() >= 478 && ball[j].getY() <= 485 &&
                                ball[j].getX() <= PADDLE_LENTH-3 + paddleX && ball[j].getX() >= paddleX - 19;
                        final boolean paddlePositionX = (ball[j].getY() >= 487 && ball[j].getY() <= 524)
                                && ((ball[j].getX() == paddleX - 17 && ball[j].getX() >= paddleX - 22) ||
                                (ball[j].getX() <= PADDLE_LENTH + paddleX && ball[j].getX() >= PADDLE_LENTH-5 + paddleX));
                        final boolean atRightBorder = ball[j].getX() >= (518);
                        final boolean atLeftBorder = ball[j].getX() <= (0);
                        final boolean atBottomBorder = ball[j].getY() >= (578);
                        final boolean atTopBorder = ball[j].getY() <= (0);


                        int posation = 0;
                        double vecolity = pow(dx[j], 2) + pow(dy[j], 2);
                        double distance = (paddleX - (ball[j].getX()+11) + (PADDLE_LENTH/2));

                        for (int i = 0; i < 80; i++)
                            if (root.getChildren().contains(view[i])) {
                                boolean mainBlock = (ball[j].getY() <= view[i].getY() + 32)
                                        && (ball[j].getY() >= view[i].getY() - 22)
                                        && (ball[j].getX() >= view[i].getX() - 22)
                                        && (ball[j].getX() <= view[i].getX() + 64);
                                boolean inBlock = (ball[j].getY() <= view[i].getY() + 28)
                                        && (ball[j].getY() >= view[i].getY() - 18)
                                        && (ball[j].getX() >= view[i].getX() - 18)
                                        && (ball[j].getX() <= view[i].getX() + 60);
                                boolean yBlock = (ball[j].getY() <= view[i].getY() - 20 && ball[j].getY() >= view[i].getY() - 24)
                                        || (ball[j].getY() <= view[i].getY() + 34 && ball[j].getY() >= view[i].getY() + 30);
                                boolean xBlock = (ball[j].getX() <= view[i].getX() - 20 && ball[j].getX() >= view[i].getX() - 24)
                                        || (ball[j].getX() >= view[i].getX() + 62 && ball[j].getX() <= view[i].getX() + 66);


                                if (mainBlock && view[i].getImage() !=null && !view[i].getImage().isError()) {

                                    if (!fireBall) {

                                        if (blockType[i] == 0 || blockType[i] == 6) {

                                            if (xBlock) {
                                                SCORE++;
                                                dx[j] *= -1;
                                                root.getChildren().remove(view[i]);
                                                view[i] = new ImageView();
                                            }
                                            if (yBlock) {
                                                SCORE++;
                                                dy[j] *= -1;
                                                root.getChildren().remove(view[i]);
                                                view[i] = new ImageView();
                                            }
                                        } else if (blockType[i] == 4) {
                                            if (inBlock) {
                                                SCORE++;
                                                dy[j] *= -1;
                                                dx[j] *= -1;
                                                root.getChildren().remove(view[i]);
                                                view[i] = new ImageView();
                                            }
                                            if (xBlock) {
                                                dx[j] *= -1;
                                                blockType[i] = 0;
                                            }
                                            if (yBlock) {
                                                dy[j] *= -1;
                                                blockType[i] = 0;
                                            }
                                        } else if (blockType[i] == 3) {
                                            if (inBlock) {
                                                SCORE++;
                                                dy[j] *= -1;
                                                dx[j] *= -1;
                                                root.getChildren().remove(view[i]);
                                                view[i] = new ImageView();
                                            }
                                            if (xBlock) {
                                                dx[j] *= -1;
                                                blockType[i] = 4;
                                            }
                                            if (yBlock) {
                                                dy[j] *= -1;
                                                blockType[i] = 4;
                                            }
                                        } else if (blockType[i] == 5) {
                                            if (inBlock) {
                                                SCORE++;
                                                dy[j] *= -1;
                                                dx[j] *= -1;
                                                root.getChildren().remove(view[i]);
                                                view[i] = new ImageView();
                                            }
                                            if (xBlock) {
                                                SCORE++;
                                                dy[j] *= -1;
                                                dx[j] *= -1;
                                                root.getChildren().remove(view[i]);
                                                view[i] = new ImageView();
                                            }
                                            if (yBlock) {
                                                SCORE++;
                                                dy[j] *= -1;
                                                dx[j] *= -1;
                                                root.getChildren().remove(view[i]);
                                                view[i] = new ImageView();
                                            }
                                        } else if (blockType[i] == 1 && root.getChildren().contains(view[i])) {

                                            if (xBlock) {
                                                SCORE+=2;
                                                dx[j] *= -1;
                                                root.getChildren().remove(view[i]);
                                                view[i] = new ImageView();
                                            }
                                            if (yBlock) {
                                                SCORE+=2;
                                                dy[j] *= -1;
                                                root.getChildren().remove(view[i]);
                                                view[i] = new ImageView();
                                            }
                                        } else if (blockType[i] == 2) {

                                            if (xBlock) {
                                                SCORE++;
                                                dx[j] *= -1;
                                                root.getChildren().remove(view[i]);
                                                view[i] = new ImageView();
                                            }
                                            if (yBlock) {
                                                SCORE++;
                                                dy[j] *= -1;
                                                root.getChildren().remove(view[i]);
                                                view[i] = new ImageView();

                                            }
                                            setPrizes.setPrize(Main.this, i);
                                        }
                                    } else {

                                        if (xBlock) {
                                            SCORE++;
                                            root.getChildren().remove(view[i]);
                                            view[i] = new ImageView();
                                        }
                                        if (yBlock) {
                                            SCORE++;
                                            root.getChildren().remove(view[i]);
                                            view[i] = new ImageView();
                                        }
                                        if (blockType[i] == 2) setPrizes.setPrize(Main.this, i);
                                    }
                                    break;
                                }
                            }

                        if (atRightBorder || atLeftBorder) {
                            dx[j] *= -1;
                        }
                        if (paddlePositionY) {
                            if (dy[j] > 0) {
                                if(distance==0){
                                double M1;
                                double M2 = dy[j] / dx[j];
                                if ((4500*PADDLE_LENTH/104) > pow(distance, 2))
                                    M1 = (-2 * distance) / (60 * sqrt((4500*PADDLE_LENTH/104) - pow(distance, 2)));
                                else
                                    M1 = (-2 * distance) / (60 * sqrt(pow(distance, 2) - (4500*PADDLE_LENTH/104)));

                                    M1 = -1 / M1;
                                    double M3 = ((2 * M1) + (M2 * pow(M1, 2)) - M2) / (2 * M1 * M2 - pow(M1, 2) + 1);
                                    dy[j] = -sqrt(vecolity / (1 + (1 / pow(M3, 2))));
                                    if (vecolity > pow(dy[j], 2))
                                        dx[j] = sqrt(vecolity - pow(dy[j], 2));
                                    else
                                        dx[j] = sqrt(pow(dy[j], 2) - vecolity);
                                    if ((dx[j] < 0 && distance < 0) || (dx[j] > 0 && distance > 0)) dx[j] *= -1;
                                }else dy[j]*=-1;
                            }
                        } else if (atTopBorder) {
                            dy[j] *= -1;
                        } else if (atBottomBorder) {
                            root.getChildren().remove(ball[j]);
                            ballCount=0;
                            for (int i = 0; i < 3; i++)
                                if (root.getChildren().contains(ball[i])) {
                                    ballCount++;
                                }
                            if (ballCount==0) {
                                gameEnd();
                            }
                        }
                        for (int k = 0; k < prizes.length; k++) {
                            if (root.getChildren().contains(prizes[k])) {
                                try {
                                    boolean prizeTouch =
                                            (paddleX - 25 <= prizes[k].getX() && paddleX + PADDLE_LENTH-25 >= prizes[k].getX()
                                                    && 460 <= prizes[k].getY() && 510 >= prizes[k].getY());
                                    if (root.getChildren().contains(prizes[k]) && prizeTouch) {
                                        root.getChildren().remove(prizes[k]);
                                        givePrize.givePrize(Main.this, prizeType[k]);
                                    }
                                } catch (NullPointerException | IOException ignored) {
                                }
                            }
                        }
                    }
                }
            }
        }));

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            for (int i = 0; i < 80; i++) {
                if (blockType[i] == 1) {
                    if (root.getChildren().contains(view[i])) {
                        root.getChildren().remove(view[i]);
                    } else {
                        for (int j = 0; j < 3; j++)
                            if (ball[j]!=null){
                            boolean mainBlock = (ball[j].getY() <= view[i].getY() + 32)
                                    && (ball[j].getY() >= view[i].getY() - 22)
                                    && (ball[j].getX() >= view[i].getX() - 22)
                                    && (ball[j].getX() <= view[i].getX() + 64);
                            if (mainBlock) {
                                root.getChildren().remove(view[i]);
                                view[i] = new ImageView();
                                SCORE+=2;
                                dx[j] *= -1;
                                dy[j] *= -1;
                            } else
                            if (!root.getChildren().contains(view[i]))
                                root.getChildren().add(view[i]);
                        }
                    }
                }
            }
        }));

        downer = new Timeline(new KeyFrame(Duration.seconds(30), ev -> {
            remainBlocks.setY(25);
            remainBlocks.setX(5);
            remainBlocks.setFont(font);
            remainBlocks.setFill(Color.INDIANRED);
            remainBlocks.setText(q + " block remain");
            if (!root.getChildren().contains(remainBlocks))
            root.getChildren().add(remainBlocks);
            SCORE-=10;
            for (int i = 0; i < 80; i++) {
                if(view[i]!=null)
                    if ( view[i].getY() + 65 >= 500)
                        gameEnd();
                    else {
                        view[i].setY(view[i].getY() + 33);
                        for (int j = 0; j < 3; j++) {
                            if (ball[j]!=null) {
                                boolean existBallIn = (ball[j].getY() >= view[i].getY() - 24 && ball[j].getX() >= view[i].getX() - 24)
                                        && (ball[j].getY() <= view[i].getY() + 34 && ball[j].getX() <= view[i].getX() + 66);

                                if (existBallIn) {
                                    SCORE++;
                                    root.getChildren().remove(view[i]);
                                    view[i] = new ImageView();
                                    dx[j]*=-1; dy[j]*=-1;
                                }
                            }
                        }
                    }
            }
        }));

        timer = new AnimationTimer() {

            @Override
            public void handle(long now) {

                for (int i = 0; i < prizes.length; i++) {
                    if (root.getChildren().contains(prizes[i])) {
                        prizeY = prizes[i].getY();
                        prizeY++;
                        prizes[i].rotateProperty().setValue(5 * prizeY);
                        prizes[i].setY(prizeY);
                    }
                }

                if (right)
                    paddleX += PADDLE_SPEED;
                if (left)
                    paddleX -= PADDLE_SPEED;
                if (paddleX >= 540  - PADDLE_LENTH)
                    paddleX = (int) Math.floor(540  - PADDLE_LENTH);
                if (paddleX <= 0) paddleX=0;
                paddle.setX(paddleX);
            }
        };


    }
    
    void gameEnd() {

        if (LIFE == 0){
            Gameover=true;
            try {
                restart();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            LIFE--;
            root.getChildren().remove(Life[LIFE]);
            try {
                restart();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }





    Alert alert;
    public void startDialog() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Arkanoid");
        alert.setHeaderText("Welcome to Arkanoid!");
        alert.setContentText("Choose your option.");
        Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
        s.getIcons().add(new Image("/icon.png"));

        ButtonType buttonTypeNew = new ButtonType("Start new game");
        ButtonType buttonTypeLoad = new ButtonType("Load last game");
        ButtonType buttonTypeLeaderBoard = new ButtonType("See learerboards");
        ButtonType buttonTypeExit = new ButtonType("Exit",ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeNew
                , buttonTypeLoad, buttonTypeLeaderBoard, buttonTypeExit);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeNew){
            setup();
        } else if (result.get() == buttonTypeLoad) {

        } else if (result.get() == buttonTypeLeaderBoard) {
            int star = 0;
            StringBuilder context= new StringBuilder();
            submitLeaderBoard = leaderBoard.loadLeaderboard();
            if (submitLeaderBoard==null)JOptionPane.showMessageDialog
                    (null, "Nobody submitted score!");
            else {

                for (int i = 0; i < submitLeaderBoard.size(); i++) {
                    for (int j = 0; j < submitLeaderBoard.get(i).length(); j++) {
                        if (submitLeaderBoard.get(i).charAt(j) == '*') {
                            star = j;
                            break;
                        }
                    }
                    context.append(i + 1).
                            append("- ").append(submitLeaderBoard.get(i), 0, star).append(" : ").append(submitLeaderBoard.get(i)
                            .substring(star + 1)).append("\n");
                }

                Alert showlb = new Alert(Alert.AlertType.CONFIRMATION);
                showlb.setTitle("LeaderBoard");
                showlb.setHeaderText(String.valueOf(context));

                ButtonType buttonClear = new ButtonType("Clear the List");
                ButtonType buttonGotogame = new ButtonType("Start");
                ButtonType buttonExit = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);


                showlb.getButtonTypes().setAll(buttonClear, buttonGotogame, buttonExit);
                Stage ss = (Stage) showlb.getDialogPane().getScene().getWindow();
                ss.getIcons().add(new Image("/icon.png"));
                Optional<ButtonType> res = showlb.showAndWait();
                if (res.get() == buttonClear) {
                    File myObj = new File("Leaderboard.txt");
                    if (myObj.delete()) JOptionPane.showMessageDialog(
                            null, "List cleared!");
                    else JOptionPane.showMessageDialog(
                            null, "We couldn't clear the list, try again!");
                    showlb.close();
                }else if (res.get() == buttonGotogame) {
                    setup();
                } else {
                    System.exit(1);
                }
                }
        } else {
            System.exit(1);
        }

    }

    void restart() throws Exception {
        start(stage);
    }

    private void Finish() {
        int star = 0;
        submitLeaderBoard = leaderBoard.loadLeaderboard();
        if (submitLeaderBoard!=null) {
            for (int k = 0; k < submitLeaderBoard.get(0).length(); k++)
                if (submitLeaderBoard.get(0).charAt(k) == '*') {
                    star = k;
                    break;
                }
            if (Integer.parseInt(submitLeaderBoard.get(0).substring(star + 1)) < SCORE)
                JOptionPane.showMessageDialog(null, "Congratulations! "+SCORE +
                                " IS THE HIGHEST SCORE IN GAME EVER!");


        }
        getName.setPromptText("Enter your name");
        getName.setFont(font);
        getName.setLayoutX(85);
        getName.setLayoutY(250);
        getName.setAlignment(Pos.CENTER);


        submit.setPadding(Insets.EMPTY);
        submit.setLayoutX(175);
        submit.setLayoutY(300);
        submit.setText("Submit score");
        submit.setFont(font);

        cancel.setPadding(Insets.EMPTY);
        cancel.setLayoutX(245);
        cancel.setLayoutY(335);
        cancel.setText("Exit");
        cancel.setFont(font);

        root.getChildren().addAll(cancel,getName,submit);




            submit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                   submiter();
                    start.setImage(go);
                    root.getChildren().removeAll(submit,getName,cancel);
                }
            });


            cancel.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    start.setImage(new Image("/img/gameover2.png"));
                    root.getChildren().removeAll(submit,getName,cancel);
                    lock=true;
                }
            });


        }

    private void submiter() {
        int star=0;
        name = getName.getText();
        lock=true;
        int us;
        String un;
        String sub = name + "*" + SCORE;
        submitLeaderBoard = leaderBoard.loadLeaderboard();
                if (submitLeaderBoard==null) {
                    submitLeaderBoard=new ArrayList<>();
                    submitLeaderBoard.add(sub);
                }
                else {
                    if (name.isEmpty() || submitLeaderBoard.contains(sub)) return;

                    showScore.setText(SCORE + " IS YOUR BEST SCORE!");
                    showScore.setX(85);
                    showScore.setFill(Color.RED);

                    for (int j = 0; j < submitLeaderBoard.size(); j++) {

                        for (int k = 0; k < submitLeaderBoard.get(j).length(); k++)
                            if (submitLeaderBoard.get(j).charAt(k) == '*') {
                                star = k;
                                break;
                            }

                        un = submitLeaderBoard.get(j).substring(0, star);
                        us = Integer.parseInt(submitLeaderBoard.get(j).substring(star + 1));

                        if (un.equals(name) && us >= SCORE) {
                            showScore.setText("Your score: " + SCORE);
                            showScore.setX(165);
                            showScore.setFill(Color.BLACK);
                            break;
                        }
                    }

                    for (int i = 0; i < submitLeaderBoard.size(); i++) {
                        for (int j = 0; j < submitLeaderBoard.get(i).length(); j++) {
                            if (submitLeaderBoard.get(i).charAt(j) == '*') {
                                star = j;
                                break;
                            }
                        }

                        int userScore = Integer.parseInt(submitLeaderBoard.get(i).substring(star + 1));


                        if (SCORE >= userScore) {
                            submitLeaderBoard.add(i, sub);
                            break;
                        }
                        if (submitLeaderBoard.size()-1==i) {
                            submitLeaderBoard.add(sub);
                            break;
                        }
                    }
                }


        leaderBoard.saveLeaderboard(submitLeaderBoard);

    }


    public static void main(String[] args) {
        launch(args);
    }
}

