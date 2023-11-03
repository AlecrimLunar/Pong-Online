package Controllers.Sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Controllers.Sockets.Threads.ServerThread;

public class ServidorSocket extends Thread{
    private int port;


    public ServidorSocket(int port) {
        this.port = port;
    }

    public void run(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ServerSocket serverSocket;
        ExecutorService exec = Executors.newCachedThreadPool();
        
        try {

            serverSocket = new ServerSocket(port);

        } catch (IOException e) {

            System.out.println("Erro na porta");
            return;

        }
        for(int i=1; i<=2; i++){
            try {
                if(i == 1){
                    Socket ns = serverSocket.accept();
                    exec.execute(new ServerThread(ns, true));
                } else {
                    Socket ns = serverSocket.accept();
                    exec.execute(new ServerThread(ns, false));
                }

            } catch (IOException e) {
                System.out.println("Erro na conexão");
            }
        }
    }
}
