import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application{

    @Override
    public void start(Stage stage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Menu.fxml"));
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(root);
        Scene scene = new Scene(stackPane);
        scene.getStylesheets().add(getClass().getResource("/CSS/MenuStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("PONG");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}