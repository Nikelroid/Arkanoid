import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class cleaning
{
    public cleaning() {
    }
    private static final Logger logger = LogManager.getLogger(cleaning.class);
    void setup(Main main) {


        main.fireBall = false;
        main.ball = new ImageView[3];
        main.button = new Button();
        main.paddleX = 218;
        main.ballX = 259;
        main.ballY = 478;
        main.dx = new double[3];
        main.dy = new double[3];
        main.PADDLE_LENTH = 104;
        main.PADDLE_SPEED = 10;
        main.BALL_SPEED = 2;
        main.view = new ImageView[80];
        main.blockType = new int[80];
        main.prizes = new ImageView[100];
        main.prizeType = new int[100];
        main.prizenumber = 0;
        main.clean.cleanup(main);
        main.CONFUSEDPADDLE = false;
        main.ballCount = 0;
        main.lock = false;
        main.showScore = new Text();
        main.blockCounter = 0;
        main.sub = false;
        main.pause = new boolean[]{false, false, false, false, false, false, false, false, false, false};
        main.q = 0;
        logger.info("Whole game cleaned up");
    }

    void cleanup(Main main) {

        if (main.downer != null)
            main.downer.stop();
        if (main.loop != null)
            main.loop.stop();
        if (main.timeline != null)
            main.timeline.stop();
        if (main.timer != null)
            main.timer.stop();

        main.downer = null;
        main.loop = null;
        main.timeline = null;
        main.timer = null;

        main.downer = new Timeline();
        main.loop = new Timeline();
        main.timeline = new Timeline();
        main.timer = new AnimationTimer() {
            @Override
            public void handle(long l) {

            }
        };


        for (int i = 0; i < 5; i++) {
            if (main.normalizer[i] != null)
                main.normalizer[i].stop();
            main.normalizer[i] = null;
            main.normalizer[i] = new Timeline();
        }
        logger.info("Timers and animations reset");
    }
}
