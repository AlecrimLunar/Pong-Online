package Controllers.Sockets.Threads;

import Controllers.GameController;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.fxml.FXMLLoader;

public class ServerThread implements Runnable{
    private Socket s;
    private boolean player1;

    public ServerThread(Socket s, boolean player1) {
        this.s = s;
        this.player1 = player1;
    }

    public void run(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DataInputStream in;
        DataOutputStream out;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Game.fxml"));
        GameController controller = loader.getController();
        
        try{
            in = new DataInputStream(s.getInputStream());
            out = new DataOutputStream(s.getOutputStream());
        } catch (Exception e){
            return;
        }

        while(true){
            String dados;
            double[] bolaPosition;
            double playerPosition;
            String send = "";

            try {
                dados = in.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            double player = Double.parseDouble(dados);
            controller.setPlayer(player);

            if(player1){
                bolaPosition = controller.getBolaPosition();
                send = send + bolaPosition[0] + " ";
                send = send + bolaPosition[1] + " ";
            }

            playerPosition = controller.getPlayerPosition();
            send = send + playerPosition;
            
            try {
                out.writeUTF(send);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}