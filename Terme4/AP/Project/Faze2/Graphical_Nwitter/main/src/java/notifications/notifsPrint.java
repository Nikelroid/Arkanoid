package notifications;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import jsonContoller.jsonNotifs;
import jsonContoller.jsonUsers;
import objects.objNotifs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class notifsPrint {

    public static AnchorPane user;
    jsonNotifs Not = new jsonNotifs();
    List<objNotifs> notifs = Not.get();
    jsonUsers get_users = new jsonUsers();
    ImageView Icon;
    Label Text, Username;
    public static ArrayList<Button> Dismiss;

    private void difiner(int counter) throws IOException {

        user = FXMLLoader.load(getClass().getResource("/layout/cards/notifs_card.fxml"));
        Username = (Label) user.lookup("#username");
        Text = (Label) user.lookup("#text");
        Dismiss.add((Button) user.lookup("#dismiss"));
        Icon = (ImageView) user.lookup("#icon");

    }

    private void setter(int i,int type,int flag) {

        if (flag==1) {
            try {
                Icon.setImage(new Image(notifsPrint.class.getResourceAsStream("/profiles/"+
                        notifs.get(i).getUser1()+".png")));
            }catch (NullPointerException ignored){
            }
            Username.setText(notifs.get(i).getUser1());
        }else {
            try {
                Icon.setImage(new Image(notifsPrint.class.getResourceAsStream("/profiles/"+
                        notifs.get(i).getUser2()+".png")));
            }catch (NullPointerException ignored){
            }
            Username.setText(notifs.get(i).getUser2());
        }
        switch (type) {
            case 1 -> {
                Text.setText(" started following you ");
                Text.getStyleClass().add("accept");
            }
            case 2 -> {
                Text.setText(" unfollowed you ");
                Text.getStyleClass().add("wrong");
            }
            case 3 -> {
                Text.setText(" blocked you ");
                Text.getStyleClass().add("wrong");
            }
            case 4 -> {
                Text.setText(" unblocked you ");
                Text.getStyleClass().add("accept");
            }
            case 5 -> {
                Text.setText(" muted you ");
                Text.getStyleClass().add("wrong");
            }
            case 6 -> {
                Text.setText(" unmuted you ");
                Text.getStyleClass().add("accept");
            }
            case 7 -> {
                Text.setText(" deleted you ");
                Text.getStyleClass().add("wrong");
            }
            case 8 -> {
                Text.setText(" recieved your request and doesn't decided accept or reject you ");
                Text.getStyleClass().add("username");
                Dismiss.get(Dismiss.size()-1).setVisible(false);
            }
            case 9 -> {
                Text.setText(" accepted your follow request ");
                Text.getStyleClass().add("accept");
            }
            case 10 -> {
                Text.setText(" rejected your follow request ");
                Text.getStyleClass().add("wrong");
            }
        }

    }
    private void adder(int counter){
        ScrollPane scrollPane = (ScrollPane) launch.view.scene.lookup("#scobar");
        VBox twitteList = (VBox) scrollPane.lookup("#twittelist");
        twitteList.getChildren().add(user);
    }


    public notifsPrint(int i,int type,int counter,int flag) throws IOException {
        difiner(counter);
        setter(i,type,flag);
        adder(counter);
    }
}
