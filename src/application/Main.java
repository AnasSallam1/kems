package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = FXMLLoader.load(getClass().getResource("/controllers/Menu.fxml"));
        Scene scene = new Scene(root, 950, 600);
        scene.getStylesheets().add("/application/application.css");
        stage.setTitle("Kems");
        stage.getIcons().add(new Image(new File("resources/icon.png").toURI().toString()));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}