import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class leaderBoard {
    private static final Logger logger = LogManager.getLogger(leaderBoard.class);
    private final File leaderboard = new File("Leaderboard.txt");

    public leaderBoard() {
    }


    public void saveLeaderboard(ArrayList<String> submitText) {
        try {
            FileWriter writeData = new FileWriter(leaderboard);
            Writer output = new BufferedWriter(writeData);

            for (int i = 0; i < submitText.size(); i++) {
                output.write(submitText.get(i) + "\n");
            }
            output.close();

        } catch (IOException e) {
            logger.error("error in save score");
            JOptionPane.showMessageDialog(null, "We cant submit score! Sorry!");
        }
    }

    public ArrayList<String> loadLeaderboard() {
        ArrayList<String> leaderBoard = new ArrayList<>();
        String records;
        try {
            BufferedReader input = new BufferedReader(new FileReader(leaderboard));
            if (!input.ready())
                throw new IOException();
            while ((records = input.readLine()) != null) {
                leaderBoard.add(records);
            }
            input.close();
            return leaderBoard;
        } catch (IOException e) {
            logger.error("error in load score");
            return null;
        }
    }


    public void LeaderBoard(leaderBoard leaderBoard, Main main){
    int star = 0;
    StringBuilder context = new StringBuilder();
    main.submitLeaderBoard = leaderBoard.loadLeaderboard();
    if (main.submitLeaderBoard == null) JOptionPane.showMessageDialog
            (null, "Nobody submitted score!");
    else {

        for (int i = 0; i < main.submitLeaderBoard.size(); i++) {
            for (int j = 0; j < main.submitLeaderBoard.get(i).length(); j++) {
                if (main.submitLeaderBoard.get(i).charAt(j) == '*') {
                    star = j;
                    break;
                }
            }
            context.append(i + 1).
                    append("- ").append(main.submitLeaderBoard.get(i), 0, star).append(" : ").append(main.submitLeaderBoard.get(i)
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
            main.submitLeaderBoard = new ArrayList<>();
            File myObj = new File("Leaderboard.txt");
            if (myObj.delete()){
                JOptionPane.showMessageDialog(
                        null, "List cleared!");
                logger.info("List cleared");
            }
            else{
                JOptionPane.showMessageDialog(
                        null, "We couldn't clear the list, try again!");
                logger.error("error in clearing list");
            }
            showlb.close();
        } else if (res.get() == buttonGotogame) {
            logger.info("User Started game");
            main.clean.setup(main);
        } else {
            logger.info("exited");
            System.exit(1);
        }
    }
}

    public void submiter(Main main) {
        int star=0;
        main.name = main.getName.getText();
        main.lock=true;
        int us;
        String un;
        String sub = main.name + "*" + main.SCORE;
        main.submitLeaderBoard = loadLeaderboard();
                if (main.submitLeaderBoard==null) {
                    main.submitLeaderBoard=new ArrayList<>();
                    main.submitLeaderBoard.add(sub);
                }
                else {
                    if (main.name.isEmpty() || main.submitLeaderBoard.contains(sub)) return;

                    main.showScore.setText(main.SCORE + " IS YOUR BEST SCORE!");
                    main.showScore.setX(85);
                    main.showScore.setFill(Color.RED);

                    for (int j = 0; j < main.submitLeaderBoard.size(); j++) {

                        for (int k = 0; k < main.submitLeaderBoard.get(j).length(); k++)
                            if (main.submitLeaderBoard.get(j).charAt(k) == '*') {
                                star = k;
                                break;
                            }

                        un = main.submitLeaderBoard.get(j).substring(0, star);
                        us = Integer.parseInt(main.submitLeaderBoard.get(j).substring(star + 1));

                        if (un.equals(main.name) && us >= main.SCORE) {
                            main.showScore.setText("Your score: " + main.SCORE);
                            main.showScore.setX(165);
                            main.showScore.setFill(Color.BLACK);
                            break;
                        }
                    }

                    for (int i = 0; i < main.submitLeaderBoard.size(); i++) {
                        for (int j = 0; j < main.submitLeaderBoard.get(i).length(); j++) {
                            if (main.submitLeaderBoard.get(i).charAt(j) == '*') {
                                star = j;
                                break;
                            }
                        }

                        int userScore = Integer.parseInt(main.submitLeaderBoard.get(i).substring(star + 1));


                        if (main.SCORE >= userScore) {
                            main.submitLeaderBoard.add(i, sub);
                            break;
                        }
                        if (main.submitLeaderBoard.size()-1==i) {
                            main.submitLeaderBoard.add(sub);
                            break;
                        }
                    }
                }


        saveLeaderboard(main.submitLeaderBoard);
        logger.info("Leaderboard updated");
    }

}
