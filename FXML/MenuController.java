package FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MenuController {

    @FXML
    Label Title;
    Button Jogar;

    @FXML
    void EscreveCU(ActionEvent e){
        Title.setText(Title.getText()+" CU");
    }
    
}
