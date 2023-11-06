package Controllers;

import java.io.File;
import java.io.FileWriter;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HostController {

    private Parent root;
    private Scene scene;
    private Stage stage;

    @FXML
    private TextField Porta;
    @FXML
    private Text Error;

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
    void Iniciar(ActionEvent e) throws IOException {
        int port = 0;
        String s = "a";
        try {

            s = Porta.getText();
            port = Integer.parseInt(s);

        } catch (NumberFormatException er) {
            Error.setText("Digite uma porta válida");
            return;
        }
        
        File Jogador1 = new File("Temp/Jogador1.txt");
        try {

            if (Jogador1.createNewFile())
                System.out.println("Arquivo criado com sucesso.");

        } catch (IOException er) {
            System.out.println("Ocorreu um erro ao criar o arquivo: " + er.getMessage());
        } 

        try (FileWriter escritor = new FileWriter("Temp/Jogador1.txt")) {

            escritor.write("true");

        } catch (IOException er) {
            System.out.println("Ocorreu um erro ao escrever no arquivo: " + er.getMessage());
        }

        File porta = new File("Temp/Porta.txt");
        try {

            if (porta.createNewFile())
                System.out.println("Arquivo criado com sucesso.");

        } catch (IOException er) {
            System.out.println("Ocorreu um erro ao criar o arquivo: " + er.getMessage());
        } 

        try (FileWriter escritor = new FileWriter("Temp/Porta.txt")) {

            escritor.write(port + "");

        } catch (IOException er) {
            System.out.println("Ocorreu um erro ao escrever no arquivo: " + er.getMessage());
        }
        
        try {

            root = FXMLLoader.load(getClass().getResource("/FXML/Game.fxml"));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(root);

            Scene scene = new Scene(stackPane);
            scene.getStylesheets().add(getClass().getResource("/CSS/GameStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(event -> {
            event.consume();
            Sair(stage);
            });

        } catch (IOException er) {
            System.out.println(er.toString());
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
