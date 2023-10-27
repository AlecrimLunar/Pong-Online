package Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JogarMenuController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    void Client(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/FXML/ClientMenu.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(root);
        scene = new Scene(stackPane);
        scene.getStylesheets().add(getClass().getResource("/CSS/ClientMenuStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("PONG");
        stage.show();
    }

    @FXML
    void Cancelar(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/FXML/Menu.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(root);
        scene = new Scene(stackPane);
        scene.getStylesheets().add(getClass().getResource("/CSS/MenuStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("PONG");
        stage.show();
    }

}
