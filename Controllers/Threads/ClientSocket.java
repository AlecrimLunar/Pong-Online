package Controllers.Threads;

public class ClientSocket extends Thread{
    
    private String ip;
    private int porta;
    private String nickname;


    public ClientSocket(String ip, int porta, String nickname) {
        this.ip = ip;
        this.porta = porta;
        this.nickname = nickname;
    }
    
}
