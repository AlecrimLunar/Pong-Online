package Controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;
import Controllers.Sockets.ClientSocket;
import Controllers.Sockets.Threads.BolaThread;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;


public class GameController {
    
    private boolean jogador1;

    @FXML
    private Rectangle Player1;
    @FXML
    private Rectangle Player2;
    @FXML
    private Rectangle BarreiraTop;
    @FXML
    private Rectangle BarreiraBai;
    @FXML
    private Rectangle Bola;
    @FXML
    private Label NickPlayer1;
    @FXML
    private Label NickPlayer2;

    @FXML
    void initialize(){
        String porta;
        try (BufferedReader leitor = new BufferedReader(new FileReader("Temp/Nickname.txt"))) {
            porta = leitor.readLine();
        } catch (IOException er) {
            System.out.println("Ocorreu um erro ao ler o arquivo: " + er.getMessage());
            return;
        }
        ClientSocket cs = new ClientSocket("localhost", Integer.parseInt(porta), true);
        cs.start();

        if(jogador1){
            Player1.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent e){
                    moverRetangulo(e, Player1);
                }
            }); Player1.setFocusTraversable(true);
        } else {
            Player2.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent e) {
                    moverRetangulo(e, Player2);
                }
            });Player2.setFocusTraversable(true);
        }
        if(jogador1){
            Thread movimentoThread = new Thread(new Runnable() {
                @Override
                public void run(){

                    boolean bateuX = false;
                    boolean bateuY = false;

                    while(true){

                        Callable<double[]> callable = new BolaThread(Bola, bateuX, bateuY);
                        double[] movimento;

                        try {

                            movimento = callable.call();
                            Bola.setX(movimento[0]);
                            Bola.setY(movimento[1]);
                            Thread.sleep(10);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //y
                        if(Bola.getBoundsInParent().intersects(BarreiraTop.getBoundsInParent())){
                            bateuY = true;
                        }
                        if(Bola.getBoundsInParent().intersects(BarreiraBai.getBoundsInParent())){
                            bateuY = false;
                        }
                        
                        //x
                        if(Bola.getBoundsInParent().intersects(Player1.getBoundsInParent())){
                            bateuX = true;
                        }
                        if(Bola.getBoundsInParent().intersects(Player2.getBoundsInParent())){
                            bateuX = false;
                        }
                    }
                }
            }); movimentoThread.start();
        }

        try (BufferedReader leitor = new BufferedReader(new FileReader("Temp/Nickname.txt"))){
            if(jogador1){
                NickPlayer1.setText(leitor.readLine());
            } else {
                NickPlayer2.setText(leitor.readLine());
            }
        } catch (IOException er) {
            System.out.println("Ocorreu um erro ao ler o arquivo: " + er.getMessage());
            return;
        }
        
    }

    @FXML
    void moverPlayer(KeyEvent e){
        double movimento = 10;

        switch (e.getCode()) {
            case UP:
                if(Player1.getBoundsInParent().intersects(BarreiraTop.getBoundsInParent())){
                } else {
                    Player1.setY(Player1.getY() - movimento);
                }
                break;
            case DOWN:
                if(Player1.getBoundsInParent().intersects(BarreiraBai.getBoundsInParent())){
                } else {
                    Player1.setY(Player1.getY() + movimento);
                }
                break;
            default:
                break;
        }
    }

    private void moverRetangulo(KeyEvent e, Rectangle rectangle) {
        double movimento = 10;

        switch (e.getCode()) {
            case UP:
                if(rectangle.getBoundsInParent().intersects(BarreiraTop.getBoundsInParent())){
                } else {
                    rectangle.setY(rectangle.getY() - movimento);
                }
                break;
            case DOWN:
                if(rectangle.getBoundsInParent().intersects(BarreiraBai.getBoundsInParent())){
                } else {
                    rectangle.setY(rectangle.getY() + movimento);
                }
                break;
            default:
                break;
        }
    }

    public void setJogador1(boolean jogador1){
        this.jogador1 = jogador1;
    }


    public double[] getBolaPosition(){
        double[] bolaPosition = new double[2];
        bolaPosition[0] = Bola.getX();
        bolaPosition[1] = Bola.getY();
        return bolaPosition;
    }

    public void setPlayer(double position){
        if(!jogador1){
            Player1.setY(position);
        } else {
            Player2.setY(position);
        }
    }

    public double getPlayerPosition(){
        if(jogador1){
            return Player1.getY();
        } else {
            return Player2.getY();
        }
    }

    public void setBolaPosition(double x, double y){
        Bola.setX(x);
        Bola.setY(y);
    }
}