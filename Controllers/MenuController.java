package Controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MenuController {

    private Parent root;
    private Stage stage;
    private Scene scene;
    
    @FXML
    Label Title;
    Button Jogar;

    @FXML
    void Jogar(ActionEvent e) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/FXML/JogarMenu.fxml"));
        stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(root);
        scene = new Scene(stackPane);
        scene.getStylesheets().add(getClass().getResource("/CSS/MenuStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("PONG");
        stage.show();
    }

    @FXML
    void Sair(ActionEvent e){
        Platform.exit();
    }
}
