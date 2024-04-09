package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InstructionsController {

    // Retourner au menu principal.

    public void menu(ActionEvent event) throws IOException {
        Parent board = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene scene = new Scene(board, 950, 600);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        controllers.BoardController.effet("resources/sounds/click.wav");
        stage.show();
    }

    // Jouer.

    public void jouer(ActionEvent event) throws IOException {
        MenuController.board = FXMLLoader.load(getClass().getResource("Board.fxml"));
        Scene scene = new Scene(MenuController.board, 950, 600);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        controllers.BoardController.effet("resources/sounds/click.wav");
        stage.show();
    }

}
