package universitysystem.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import universitysystem.model.structs.User;

import java.io.IOException;

import static universitysystem.model.Const.IMAGE_URL;
import static universitysystem.model.Const.SYSTEM_TITLE;

public class NavigationController{




    static void openNewScene(Node node, String window){
        node.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(NavigationController.class.getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(SYSTEM_TITLE);
        stage.setResizable(false);
        stage.getIcons().add(new Image(IMAGE_URL));
        stage.showAndWait();
    }

    static void openNewDependScene(Stage primaryStage, String window, double xPos, double yPos, User user, Controllable self){

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(NavigationController.class.getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
        }
        Controllable ctr = loader.getController();
        ctr.setCurrentUser(user);
        ctr.setControllable(self);

        Parent root = loader.getRoot();
        Scene secondScene = new Scene(root);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);

        // Specifies the modality for new window.
        newWindow.initModality(Modality.WINDOW_MODAL);

        // Specifies the owner Window (parent) for new window
        newWindow.initOwner(primaryStage);
        // Set position of second window, related to primary window.
        newWindow.setX(xPos);
        newWindow.setY(yPos);
        newWindow.setTitle(SYSTEM_TITLE);
        newWindow.setResizable(false);
        newWindow.getIcons().add(new Image(IMAGE_URL));

        newWindow.show();
    }


}
