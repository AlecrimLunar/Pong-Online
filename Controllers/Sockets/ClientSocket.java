package Controllers.Sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import Controllers.GameController;
import javafx.fxml.FXMLLoader;

public class ClientSocket extends Thread{
    
    private String ip;
    private int porta;
    private Socket s;
    private boolean jogador1;

    public ClientSocket(String ip, int porta, boolean jogador1) {
        this.ip = ip;
        this.porta = porta;
        this.jogador1 = jogador1;
    }
    
    public void run(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            s = new Socket(ip, porta);
        } catch (IOException e) {
            System.out.println("Erro na conexão");
        }
        DataInputStream in;
        DataOutputStream out;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Game.fxml"));
        GameController controller = loader.getController();
        controller.setJogador1(jogador1);

        try{
            in = new DataInputStream(s.getInputStream());
            out = new DataOutputStream(s.getOutputStream());
        } catch (Exception e){
            return;
        }

        while (true) {
            String dados;
            String[] dado;

            try {
                dados = in.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            dado = dados.split(" ");
            double[] position = new double[dado.length];

            if(dado.length > 1){
                for(int i=0; i<dado.length; i++){
                    position[i] = Double.parseDouble(dado[i]);
                }
                controller.setBolaPosition(position[0], position[1]);
                controller.setPlayer(position[2]);
            } else {
                position[0] = Double.parseDouble(dado[0]);
                controller.setPlayer(position[0]);
            }

            String send = "" + controller.getPlayerPosition();
            
            try {
                out.writeUTF(send);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}