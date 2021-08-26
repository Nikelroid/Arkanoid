import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Optional;


public class EndGame {
    private static final Logger logger = LogManager.getLogger(EndGame.class);
    leaderBoard lb = new leaderBoard();
    public EndGame() {
    }
    void Finish(Main main) {
        logger.info("User finnished game");
        int star = 0;
        main.submitLeaderBoard = lb.loadLeaderboard();
        if (main.submitLeaderBoard!=null) {
            for (int k = 0; k < main.submitLeaderBoard.get(0).length(); k++)
                if (main.submitLeaderBoard.get(0).charAt(k) == '*') {
                    star = k;
                    break;
                }
            if (Integer.parseInt(main.submitLeaderBoard.get(0).substring(star + 1)) < main.SCORE) {
                JOptionPane.showMessageDialog(null, "Congratulations! " + main.SCORE +
                        " IS THE HIGHEST SCORE IN GAME EVER!");
                logger.info("User got best score ever");
            }


        }
        main.getName.setPromptText("Enter your name");
        main.getName.setFont(main.font);
        main.getName.setLayoutX(85);
        main.getName.setLayoutY(250);
        main.getName.setAlignment(Pos.CENTER);


        main.submit.setPadding(Insets.EMPTY);
        main.submit.setLayoutX(175);
        main.submit.setLayoutY(300);
        main.submit.setText("Submit score");
        main.submit.setFont(main.font);

        main.cancel.setPadding(Insets.EMPTY);
        main.cancel.setLayoutX(245);
        main.cancel.setLayoutY(335);
        main.cancel.setText("Exit");
        main.cancel.setFont(main.font);

        main.root.getChildren().addAll(main.cancel, main.getName, main.submit);




        main.submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                lb.submiter(main);
                main.start.setImage(main.go);
                main.root.getChildren().removeAll(main.submit, main.getName, main.cancel);
                logger.info("User submit record");
            }
        });


        main.cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                main.start.setImage(new Image("/img/gameover2.png"));
                main.root.getChildren().removeAll(main.submit, main.getName, main.cancel);
                main.lock=true;
                logger.info("User does not submit record");
            }
        });


    }

    public void startDialog(Main main) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Arkanoid");
        alert.setHeaderText("به آرکانوید خوش آمدید!\n" +
                "بازی در ثانیه های اول شاید کند باشد، صبور باشید...\n"
               + "راهنما: \n" );
        alert.setContentText(
                "بلوک ها:\n" +
                "بلوک آبی شیشه ای 1 ضربه\n" +
                "بلوک زرد چوبی 2 ضربه\n" +
                "بلوک قرمز فلزی 3 ضربه\n" +
                "بلوک خاکستری زبر است  توپ را در هر دو جهت میچرخانند 1 ضربه\n" +
                "بلوک سبز چشمک زن 1 ضربه\n" +
                "بلوک بنفش جایزه دار 1 ضربه\n" +
                "بلوک نامرئی  1 ضربه\n" +
                "فضاهای خالی که برای عدم قطیت بلوک های نامرئی طاحی شده اند!\n" +
                "\n" +
                "جایزه ها:\n" +
                "لوزی قرمز: توپ آتشین - پنج ضلعی قرمز : توپ سه تایی\n" +
                "لوزی زرد: تخته بزرگ - پنج ضلعی زرذ : تخته کوچک\n"+
                "لوزی سبز: توپ سریع - پنج ضلعی سبز : توپ آهسته\n" +
                "لوزی بنفش: تخته سریع - پنج ضلعی بنفش : تخته آهسته\n" +
                "پنج ضلعی آبی: تخته گیج - پنج ضلعی خاکستری : جایزه رندوم\n" +
                "\n" +
                "امتیاز ها:\n" +
                        "نابود کردن بلوک سبز: 2 امتیاز\n" +
                        " بقیه بلوک ها 1 امتیاز\n" +
                "گرفتن جایزه 5 امتیاز\n" +
                        "بردن راند: 2 برابر شدن امتیاز بدون کم شدن جان\n" +
                "\n" +
                "کلید ها:\n" +
                "P: توقف موقف بازی\n" +
                "R: شروع بازی از اول\n" +
                "S: ذخیره کردن بازی در موقعیت فعلی\n" +
                "\nموفق باشید :))))" +
                "\n" +
                "\n");
        Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
        s.getIcons().add(new Image("/icon.png"));
        ButtonType buttonTypeNew = new ButtonType("Start new Game");
        ButtonType buttonTypeLoad = new ButtonType("Load a Game");
        ButtonType buttonTypeLeaderBoard = new ButtonType("See Leaderboard");
        ButtonType buttonTypeExit = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeNew
                , buttonTypeLoad, buttonTypeLeaderBoard, buttonTypeExit);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeNew){
            logger.info("User selected New game");
            main.clean.setup(main);
        } else if (result.get() == buttonTypeLoad) {
            logger.info("User selected Load game");
            main.saveloadGame.LoadGame(main);
        } else if (result.get() == buttonTypeLeaderBoard) {
            logger.info("User selected Leaderboard");
            main.leaderBoard.LeaderBoard(main.leaderBoard, main);
        } else {
            logger.info("User exited");
            System.exit(1);
        }

    }

    void gameEnd(Main main) {

        if (main.LIFE == 0){
            main.Gameover=true;
            try {
                main.start(main.stage);
            } catch (Exception e) {
                logger.error("System crashed in overing game");
            }
        }else{
            main.LIFE--;
            main.root.getChildren().remove(main.Life[main.LIFE]);
            try {
                main.start(main.stage);
            } catch (Exception e) {
                logger.error("System crashed in end of round");
            }
        }

    }

    public void saver(Main main) {
        logger.info("Save box opened");
        main.acctionbuttons.p(main);
        String saveName;
        saveName = JOptionPane.showInputDialog("Insert save name");
        if (saveName!=null && !saveName.isEmpty()) {
            if (saveLoadGame.loadGame() != null) main.saveData = saveLoadGame.loadGame();
            else main.saveData = new ArrayList<>();
            main.saveData.add(saveName + "*" + main.SCORE + "#" + main.LIFE);
            saveLoadGame.saveGame(main.saveData);

            if (saveLoadGame.loadGame() != null && saveName != null && !saveName.isEmpty()) {
                Object[] options = {"Resume game", "Restart", "Exit game"};
                JPanel panel = new JPanel();
                panel.add(new JLabel("Game saved successfully!"));
                int result = JOptionPane.showOptionDialog(null, panel, "Saved!",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null
                        , options, null);
                if (result == JOptionPane.CANCEL_OPTION) {
                    logger.info("User Restarted the game");
                    System.exit(1);
                } else if (result == JOptionPane.NO_OPTION) {

                    main.acctionbuttons.p(main);
                    main.acctionbuttons.reset(main);
                } else if (result == JOptionPane.YES_OPTION) {
                    logger.info("User Resumed the game");
                }
            }
        }else{
            logger.info("User canceled saving");
        }
    }
}
