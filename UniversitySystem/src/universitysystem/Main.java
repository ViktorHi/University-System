package universitysystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
       // Parent root = FXMLLoader.load(getClass().getResource("/universitysystem/views/pollView.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/universitysystem/views/login.fxml"));
        primaryStage.setTitle("University System");
        Scene scene = new Scene(root, 900, 600);

        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/universitysystem/assets/images/icon.png"));
        //primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
