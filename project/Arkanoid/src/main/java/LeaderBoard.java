import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class LeaderBoard {
    private final File leaderboard = new File("Leaderboar.txt");
    private final File savegame = new File("Savedgames.txt");
    public LeaderBoard() {
    }

    public void saveGame(ArrayList<ArrayList<Integer>> data) {
        try {
            FileWriter writeData = new FileWriter(savegame);
            Writer output = new BufferedWriter(writeData);

            for (int i = 0; i < data.size(); i++)
                output.write("hi" + "\n");
               // for (int j = 0; j < submitText.get(i).size(); j++)


            output.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "We cant submit score! Sorry!");
        }
    }

    public ArrayList<String> leadgame() {
        ArrayList<String> data = new ArrayList<>();
        String records;
        try {
            BufferedReader input = new BufferedReader(new FileReader(savegame));
            if (!input.ready())
                throw new IOException();
            while ((records = input.readLine()) != null) {
                data.add(records);
            }
            input.close();
            return data;
        } catch (IOException e) {

            return null;
        }
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

            return null;
        }
    }


}
