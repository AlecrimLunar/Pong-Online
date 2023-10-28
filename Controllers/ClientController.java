package Controllers;

import java.io.IOException;

import Controllers.Threads.ClientSocket;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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
    private TextField controle;
    @FXML
    private Label IpError;
    @FXML
    private Label PortaError;

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

        stage.setOnCloseRequest(event -> {
            event.consume();
            Sair(stage);
        });
    }

    @FXML
    void Entrar(ActionEvent e) {
        PortaError.setText("");
        IpError.setText("");
        String ip = IP.getText();
        int port = 0;
        try {
            port = Integer.parseInt(Porta.getText());
            
            if (ip.equals(controle.getText())) {
                IpError.setText("Digite um IP diferente de controle.");
            } else {
                ClientSocket cs = new ClientSocket(ip, port, "Nicolas");
                cs.start();
            }
        } catch (NumberFormatException er) {
            PortaError.setText("Digite uma porta válida");
        }
    }

    void Sair(Stage stage) {
        Alert sairAlert = new Alert(AlertType.CONFIRMATION);

        sairAlert.setTitle("Sair");
        sairAlert.setHeaderText("Você está saindo do Pong.");
        sairAlert.setContentText("Deseja mesma sair?");

        if (sairAlert.showAndWait().get() == ButtonType.OK) {
            stage.close();
        }
    }

}
