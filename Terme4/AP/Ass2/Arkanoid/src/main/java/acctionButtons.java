import javafx.animation.Timeline;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class acctionButtons {
    public acctionButtons() {
    }
    private static final Logger logger = LogManager.getLogger(acctionButtons.class);
    public void p(Main main){

        if (main.pause[0]){
            logger.info("User unpaused game");
            if (main.loop!=null && main.pause[1]) main.acctionbuttons.pauser(1, main.loop,true, main);
            if (main.timeline!=null && main.pause[2]) main.acctionbuttons.pauser(2, main.timeline,true, main);
            if (main.downer!=null && main.pause[3]) main.acctionbuttons.pauser(3, main.downer,true, main);
            if (main.timer!=null && main.pause[4]) {
                main.pause[4] = false;
                main.timer.start();
                main.root.getChildren().remove(main.Showpause);
            }
            if (main.normalizer[0]!=null && main.pause[5]) main.acctionbuttons.pauser(5, main.normalizer[0],true, main);
            if (main.normalizer[1]!=null && main.pause[6]) main.acctionbuttons.pauser(6, main.normalizer[1],true, main);
            if (main.normalizer[2]!=null && main.pause[7]) main.acctionbuttons.pauser(7, main.normalizer[2],true, main);
            if (main.normalizer[3]!=null && main.pause[8]) main.acctionbuttons.pauser(8, main.normalizer[3],true, main);
            if (main.normalizer[4]!=null && main.pause[9]) main.acctionbuttons.pauser(9, main.normalizer[4],true, main);
            main.pause[0]=false;
        }else{
            logger.info("User paused game");
            if (main.loop!=null) main.acctionbuttons.pauser(1, main.loop,false, main);
            if (main.timeline!=null) main.acctionbuttons.pauser(2, main.timeline,false, main);
            if (main.downer!=null) main.acctionbuttons.pauser(3, main.downer,false, main);
            if (main.timer!=null) {
                main.pause[4] = true;
                main.timer.stop();
                main.root.getChildren().add(main.Showpause);

            }
            if (main.normalizer[0]!=null) main.acctionbuttons.pauser(5, main.normalizer[0],false, main);
            if (main.normalizer[1]!=null) main.acctionbuttons.pauser(6, main.normalizer[1],false, main);
            if (main.normalizer[2]!=null) main.acctionbuttons.pauser(7, main.normalizer[2],false, main);
            if (main.normalizer[3]!=null) main.acctionbuttons.pauser(8, main.normalizer[3],false, main);
            if (main.normalizer[4]!=null) main.acctionbuttons.pauser(9, main.normalizer[4],false, main);
            main.pause[0]=true;
        }
    }

    public void pauser(int i, Timeline T, boolean on, Main main){
        if (on) {
            main.pause[i] = false;
            T.play();
        }else{
            main.pause[i] = true;
            T.pause();
        }
    }

    public void reset(Main main){
        logger.info("User reseted game");
        main.LIFE=3;
        main.SCORE=0;
        main.Restart =true;
        main.win =false;
        try {
            main.start(main.stage);
        } catch (Exception e) {
            logger.error("An error happend in reseting game");
        }

    }
}
