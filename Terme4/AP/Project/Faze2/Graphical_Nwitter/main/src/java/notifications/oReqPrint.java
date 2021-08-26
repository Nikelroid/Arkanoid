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

public class oReqPrint {

        public static AnchorPane user;
        jsonNotifs Not = new jsonNotifs();
        List<objNotifs> notifs = Not.get();
        jsonUsers get_users = new jsonUsers();
        ImageView Icon;
        Label Text, Username;
        public static ArrayList<Button> Reject,Accept1,Accept2;

        private void difiner() throws IOException {

            user = FXMLLoader.load(getClass().getResource("/layout/cards/reqs_card.fxml"));
            Username = (Label) user.lookup("#username");
            Reject.add((Button) user.lookup("#reject"));
            Accept1.add((Button) user.lookup("#accept1"));
            Accept2.add((Button) user.lookup("#accept2"));
            Icon = (ImageView)  user.lookup("#icon");

        }

        private void setter(int i) {
            try {
                Icon.setImage(new Image(oReqPrint.class.getResourceAsStream("/profiles/"+
                        notifs.get(i).getUser1()+".png")));
            }catch (NullPointerException ignored){
            }
            Username.setText(notifs.get(i).getUser1());
            }


        private void adder(){
            ScrollPane scrollPane = (ScrollPane) launch.view.scene.lookup("#scobar");
            VBox twitteList = (VBox) scrollPane.lookup("#twittelist");
            twitteList.getChildren().add(user);
        }


        public oReqPrint(int i) throws IOException {
            difiner();
            setter(i);
            adder();
        }

}
