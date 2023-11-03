package Controllers;

import java.io.IOException;

import Controllers.Sockets.ServidorSocket;
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
    void Iniciar(ActionEvent e) {
        int port = 0;
        try {
            port = Integer.parseInt(Porta.getText());
        } catch (NumberFormatException er) {
            Error.setText("Digite uma porta válida");
            return;
        }
        
        ServidorSocket server = new ServidorSocket(port);
        server.start();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Game.fxml"));
        try {

            root = loader.load();

            GameController controller = loader.getController();
            controller.setJogador1(true);

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
            return;
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
