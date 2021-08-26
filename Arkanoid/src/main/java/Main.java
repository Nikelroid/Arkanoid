import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Main extends Application {

    leaderBoard leaderBoard = new leaderBoard();
    acctionButtons acctionbuttons = new acctionButtons();
    EndGame endgame = new EndGame();
    saveLoadGame saveloadGame = new saveLoadGame();
    difineLoops dLoop = new difineLoops();
    cleaning clean = new cleaning();

    boolean win = false;
    ArrayList<String> saveData = new ArrayList<>();
    ArrayList<String> submitLeaderBoard = new ArrayList<>();
    String name;
    Text inftx = null;
    int SCORE = 0;
    int LIFE = 3;
    ImageView[] Life = new ImageView[3];
    boolean fireBall = false;
    int blockCounter = 0;
    ImageView paddle;
    ImageView[] ball = new ImageView[3];
    Stage stage;
    Button button = new Button();
    int paddleX = 218;
    int ballX = 259, ballY = 478;
    boolean Gameover = false;
    double[] dx = new double[3];
    double[] dy = new double[3];
    double PADDLE_LENTH = 104;
    double PADDLE_SPEED = 10;
    double BALL_SPEED = 2;
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
    int ballCount = 0;
    Scene scene;
    ImageView start;
    TextField getName = new TextField();
    Button submit = new Button();
    Button cancel = new Button();
    javafx.scene.text.Font font = new javafx.scene.text.Font("RAVIE", 23);
    Text score = new Text("Score : 0");
    Text showScore = new Text();
    Text remainBlocks = new Text();
    Text Showpause = new Text();
    boolean lock = false;
    boolean sub = false;
    boolean[] pause = new boolean[10];
    boolean Restart = false;
    int q = 0;
    int saverSCORE=0;
    int saverLIFE=0;
    Timeline loop, downer, timeline;
    AnimationTimer timer;

    private static final Logger logger = LogManager.getLogger(Main.class);
    @Override
    public void start(Stage primaryStage) throws Exception {



        if (LIFE != 3 || win || Restart)
            clean.setup(this);

        if (saverLIFE+saverSCORE==0) {
            if (LIFE == 3 && !win && !Restart)
                endgame.startDialog(this);
        }
        if (Restart) win = false;

        Showpause.setFont(font);
        Showpause.setText("Paused. press P to resume");
        Showpause.setFill(Color.ORANGERED);
        Showpause.setX(70);
        Showpause.setY(450);
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
            dx[i] = (Math.random() / 1.25)-0.4;
            dy[i] = -sqrt(0.2 - pow(dx[i], 2));
        }
        root = new Group(paddle, ball[0]);

        for (int i = 0; i < LIFE; i++) {
            Life[i] = new ImageView(new Image("/img/life.png"));
            Life[i].setX(472 - (66 * i));
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
        if (LIFE != 0)
            showScore.setText("Your score: " + SCORE);


        if (LIFE != 3 || win) {
            root.getChildren().add(showScore);
            root.getChildren().remove(score);
        }


        if (!win) {
            if (Gameover)
                endgame.Finish(this);
            if (LIFE == 1) start.setImage(s1border);
            if (LIFE == 0 && !Gameover) start.setImage(s0border);
            if (LIFE == 2) start.setImage(s2border);
            if (LIFE == 3) start.setImage(sborder);
            start.setX(120);
            start.setY(150);
            root.getChildren().add(start);
            Restart = false;
        } else {
            win = false;
            sub = true;
            SCORE = SCORE * 2;
            start.setImage(winpic);
            start.setX(120);
            start.setY(150);
            root.getChildren().add(start);
            showScore.setText("Your score: " + SCORE);
            if (!root.getChildren().contains(showScore))
                root.getChildren().add(showScore);
        }
        scene.setOnKeyPressed(event -> {

            if (Gameover && event.getCode() == KeyCode.ENTER && !lock) {
                logger.info("User pressed enter for submit record");
                leaderBoard.submiter(this);
                start.setImage(go);
                root.getChildren().removeAll(submit, getName, cancel);
            }
            if (Gameover && lock && event.getCode() == KeyCode.SPACE) {
                logger.info("User Exited the game by press space");
                System.exit(1);
            }

            switch (event.getCode()) {
                case RIGHT -> right = true;
                case LEFT -> left = true;
                case SPACE -> {
                    logger.info("User pressed spade for start the game");
                    if ((LIFE != 3 || sub) && !Gameover)
                        root.getChildren().remove(showScore);
                    if (!root.getChildren().contains(score) && !Gameover) {
                        root.getChildren().add(score);
                        root.getChildren().remove(start);
                        root.getChildren().remove(inftx);
                        for (int i = 0; i < 80; i++)
                            if (view[i] != null && !root.getChildren().contains(view[i]))
                                root.getChildren().add(view[i]);
                        dLoop.difineLoops(this);
                        loop.setCycleCount(Animation.INDEFINITE);
                        loop.play();
                        timeline.setCycleCount(Animation.INDEFINITE);
                        timeline.play();
                        downer.setCycleCount(Animation.INDEFINITE);
                        downer.play();
                        timer.start();
                        sub = false;
                        Restart = false;
                    }
                }
                case P -> {
                    logger.info("User pressed P for pause game");
                    acctionbuttons.p(this);
                }
                case R ->{
                    logger.info("User pressed R for reset game");
                    acctionbuttons.reset(this);
                }
                case S -> {
                    logger.info("User pressed S for save game");
                    if (!pause[0]) endgame.saver(this);
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


    public static void main(String[] args) {
        launch(args);
    }

}

