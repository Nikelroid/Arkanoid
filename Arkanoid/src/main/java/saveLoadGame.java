import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class saveLoadGame {
    private static final Logger logger = LogManager.getLogger(saveLoadGame.class);
    private static final File savegame = new File("Savedgames.txt");;


    public saveLoadGame() {
    }

    public static void saveGame(ArrayList<String> submitText) {
        try {
            FileWriter writeData = new FileWriter(savegame);
            Writer output = new BufferedWriter(writeData);

            for (int i = 0; i < submitText.size(); i++) {
                output.write(submitText.get(i) + "\n");
            }
            output.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "We cant save game! Sorry!");
            logger.error("Error in saving game");
        }

    }

    public static ArrayList<String> loadGame() {
        ArrayList<String> leaderBoard = new ArrayList<>();
        String records;
        try {
            BufferedReader input = new BufferedReader(new FileReader(savegame));
            if (!input.ready())
                throw new IOException();
            while ((records = input.readLine()) != null) {
                leaderBoard.add(records);
            }
            input.close();
            return leaderBoard;
        } catch (IOException e) {
            logger.error("Error in loading game");
            return null;
        }
    }


    public void LoadGame(Main main) {


        StringBuilder context = new StringBuilder();
        main.saveData = loadGame();
        int[] star1 = new int[main.saveData.size()];
        int[] star2 = new int[main.saveData.size()];


        for (int i = 0; i < main.saveData.size(); i++) {
            for (int j = 0; j < main.saveData.get(i).length(); j++) {
                if (main.saveData.get(i).charAt(j) == '*')
                    star1[i] = j;
                    if (main.saveData.get(i).charAt(j) == '#') {
                        star2[i] = j;
                        break;
                    }
            }
            context.append(i + 1).append("- ").append(main.saveData.get(i).substring( 0, star1[i]))
                    .append(" - Score :").append(main.saveData.get(i).substring(star1[i] + 1,star2[i]))
            .append(" - Life :").append(main.saveData.get(i).substring(star2[i]+1)).append("\n");
        }

        main.saveData = loadGame();
        String chooser;
        chooser = JOptionPane.showInputDialog(context);
        if (chooser!=null) {

            Object[] options = {"Clear", "Creat new save","Play in this save"};
            JPanel panel = new JPanel();
            panel.add(new JLabel("Choice your option"));
            int result = JOptionPane.showOptionDialog(null, panel, "Selected!",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null
                    , options, null);
            if (result == JOptionPane.CANCEL_OPTION) {
                logger.info("load started");
                logger.error("Error in clearing loads");
                main.saveloadGame.startLoaded(chooser, star1, star2, true, main);
            } else if (result == JOptionPane.NO_OPTION) {
                logger.info("Save overwrite");
                logger.info("load started");
                main.saveloadGame.startLoaded(chooser, star1, star2, false, main);
            } else if (result == JOptionPane.YES_OPTION) {
                main.saveData = new ArrayList<>();
                File myObj = new File("Savedgames.txt");
                if (myObj.delete()){
                    JOptionPane.showMessageDialog(
                            null, "Saved games cleared!");
                    logger.info("loads cleared");
                }
                else{
                    JOptionPane.showMessageDialog(
                            null, "We couldn't clear the list, try again!");
                    logger.error("Error in clearing loads");
                }
            } else {
                logger.error("User exited");
                System.exit(1);
            }
        }


    }

    public void setLoadConfig(Main main){
            main.SCORE= main.saverSCORE;
            main.LIFE= main.saverLIFE;
            main.saverSCORE=0;
            main.saverLIFE=0;
    }

    public void startLoaded(String chooser, int[] star1, int[] star2, boolean ovetwrite, Main main) {
        int choosenum = Integer.parseInt(chooser) - 1;
        try {
            main.saverLIFE = Integer.parseInt(main.saveData.get(choosenum).substring(star2[choosenum] + 1));
            main.saverSCORE = Integer.parseInt(main.saveData.get(choosenum).
                    substring(star1[choosenum] + 1, star2[choosenum]));
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "An error sourced!");
        }
        if (ovetwrite){
            main.saveData.remove(choosenum);
            saveGame(main.saveData);
        }
        setLoadConfig(main);
        main.clean.setup(main);

    }
}
