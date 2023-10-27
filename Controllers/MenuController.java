package Controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private Label Error;
    @FXML
    private TextField Nickname;
    @FXML
    private TextField controle;

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    void Jogar(ActionEvent e) throws IOException {
        if (Nickname.getText() == controle.getText()) {
            Error.setText("Escolha um nickname");
        } else {
            File arquivo = new File("C:/Users/Alecrim/Documents/GitHub/Pong-Online/Temp/Nickname.txt");
            try {
                if (arquivo.createNewFile())
                    System.out.println("Arquivo criado com sucesso.");
            } catch (IOException er) {
                System.out.println("Ocorreu um erro ao criar o arquivo: " + er.getMessage());
            }
            try (FileWriter escritor = new FileWriter("C:/Users/Alecrim/Documents/GitHub/Pong-Online/Temp/Nickname.txt")) {
                escritor.write(Nickname.getText());
            } catch (IOException er) {
                System.out.println("Ocorreu um erro ao escrever no arquivo: " + er.getMessage());
            }
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
    }

    @FXML
    void Sair(ActionEvent e) {
        Platform.exit();
    }
}
