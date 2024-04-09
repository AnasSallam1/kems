package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class MenuController {

    // Infos sur le jeu.

    public static StackPane board = new StackPane();

    // Instructions de jeu.

    public void about() {
        //BoardController.effet("resources/sounds/click.wav");
        String javafxVersion = System.getProperty("javafx.version");
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About Kems");
        Stage icon = (Stage) alert.getDialogPane().getScene().getWindow();
        icon.getIcons().add(new Image(new File("resources/icon.png").toURI().toString()));
        ImageView imageView = new ImageView(new Image(new File("resources/icon.png").toURI().toString()));
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        VBox vbox = new VBox();
        vbox.getChildren().add(imageView);
        alert.setGraphic(vbox);
        alert.setHeaderText("Version : 1.0.7 (2021-01)\n" + "Developed with JavaFX " + javafxVersion + ".");
        alert.setContentText("Copyright");

        TextArea textArea = getTextArea();
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane content = new GridPane();
        content.setMaxWidth(Double.MAX_VALUE);
        content.add(textArea, 0, 1);
        alert.getDialogPane().setContent(content);
        alert.showAndWait();
    }

    private static TextArea getTextArea() {
        TextArea textArea = new TextArea("KEMS is an interactif card game." +
                "\n\nCreated by : Anas Sallam" +
                "\n\nCopyright  2021 Moreands. All rights reserved.");
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        return textArea;
    }

    public void instructions(ActionEvent event) throws IOException {
        //BoardController.effet("resources/sounds/click.wav");
        Parent board = FXMLLoader.load(getClass().getResource("Instructions.fxml"));
        Scene scene = new Scene(board, 950, 600);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // Jouer.

    public void play(ActionEvent event) throws IOException {
        //BoardController.effet("resources/sounds/click.wav");
        board = FXMLLoader.load(getClass().getResource("Board.fxml"));
        Scene scene = new Scene(board, 950, 600);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // Quitter le jeu.

    public void exit() {
        //BoardController.effet("resources/sounds/click.wav");
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Exit");
        alert.setHeaderText("Are you sure?");
        Stage icon = (Stage) alert.getDialogPane().getScene().getWindow();
        icon.getIcons().add(new Image(new File("resources/icon.png").toURI().toString()));
        ButtonType noButton = new ButtonType("No", ButtonData.NO);
        ButtonType yesButton = new ButtonType("Yes", ButtonData.YES);
        alert.getButtonTypes().setAll(noButton, yesButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yesButton) {
            // Si l'utilisateur a choisit "Oui".
            System.exit(0);
        } else {
            // Si l'utilisateur a choisit "Non".
            alert.close();
        }
    }

}