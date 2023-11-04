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
    private GameController controller;

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

        try {
            loader.load();
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo FXML");
            return;
        } catch (Exception e) {
            System.out.println("Erro ao carregar o arquivo FXML: " + e.getMessage());
            return;
        }

        controller = loader.getController();
        
        try{
            in = new DataInputStream(s.getInputStream());
            out = new DataOutputStream(s.getOutputStream());
        } catch (Exception e){
            return;
        }
        
        boolean controle = true;
        while(true){
            String dados;
            double[] bolaPosition;
            double playerPosition;
            StringBuilder send = new StringBuilder();

            if(player1 && controle){

                bolaPosition = controller.getBolaPosition();
                send.append(bolaPosition[0] + " ");
                send.append(bolaPosition[1] + " ");
                playerPosition = controller.getPlayerPosition();
                send.append(playerPosition);
                System.out.println("Server send: " + send.toString());
                try {
                    out.writeUTF(send.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            try {
                dados = in.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            System.out.println("Server revice: " + dados);
            double player = Double.parseDouble(dados);
            controller.setPlayer(player);
            send = new StringBuilder();
            if(player1){
                bolaPosition = controller.getBolaPosition();
                send.append(bolaPosition[0] + " ");
                send.append(bolaPosition[1] + " ");
            }

            playerPosition = controller.getPlayerPosition();
            send.append(playerPosition);

            System.out.println("Server send: " + send.toString());
            try {
                out.writeUTF(send.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            controle = false;
        }
    }
}