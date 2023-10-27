package Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ClientController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField IP;
    @FXML
    private TextField Porta;

    @FXML
    void Cancelar(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/FXML/JogarMenu.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(root);
        scene = new Scene(stackPane);
        scene.getStylesheets().add(getClass().getResource("/CSS/JogarMenuStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("PONG");
        stage.show();
    }

    @FXML
    void Entrar(ActionEvent e){
        String ip = IP.getText();
        int port = Integer.parseInt(Porta.getText());

    }
}
