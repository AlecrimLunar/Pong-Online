package Controllers.Sockets;

import java.io.IOException;
import java.net.Socket;

public class ClientSocket extends Thread{
    
    private String ip;
    private int porta;
    private String nickname;
    private Socket s;


    public ClientSocket(String ip, int porta, String nickname) {
        this.ip = ip;
        this.porta = porta;
        this.nickname = nickname;
    }
    
    public void run(){
        try {
            s = new Socket(ip, porta);
        } catch (IOException e) {
            System.out.println("Erro na conexão");
        }
    }
}
