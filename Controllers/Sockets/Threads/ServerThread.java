package Controllers.Sockets.Threads;

import Controllers.GameController;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class ServerThread implements Runnable{
    private Socket s;


    public ServerThread(Socket s) {
        this.s = s;
    }

    public void run(){
        DataInputStream in;
        DataOutputStream out;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Game.fxml"));
        try {
            AnchorPane root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GameController controller = loader.getController();
        try{
            in = new DataInputStream(s.getInputStream());
            out = new DataOutputStream(s.getOutputStream());
        } catch (Exception e){
            return;
        }
        while(true){
            try {
                String dados = in.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.writeUTF("");
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    }
}