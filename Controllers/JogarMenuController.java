package Controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
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

        stage.setOnCloseRequest(event -> {
            event.consume();
            Sair(stage);
        });
    }

    @FXML
    void Host(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/FXML/HostMenu.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(root);
        scene = new Scene(stackPane);
        scene.getStylesheets().add(getClass().getResource("/CSS/HostMenuStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("PONG");
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            Sair(stage);
        });
    }

    @FXML
    void Cancelar(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/FXML/Menu.fxml"));
        TextField textField = (TextField) root.lookup("#Nickname");
        try (BufferedReader leitor = new BufferedReader(new FileReader("Temp/Nickname.txt"))) {

            String linha;
            if ((linha = leitor.readLine()) != null) {
                textField.setText(linha);
            }

        } catch (IOException er) {
            System.out.println("Ocorreu um erro ao ler o arquivo: " + er.getMessage());
        }

        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(root);
        scene = new Scene(stackPane);
        scene.getStylesheets().add(getClass().getResource("/CSS/MenuStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("PONG");
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            Sair(stage);
        });
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
