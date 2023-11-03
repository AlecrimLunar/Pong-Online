package Controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;


import Controllers.Sockets.Threads.BolaThread;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

public class GameController {
    
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
    void initialize(){

        Player1.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent e){
                moverRetangulo(e, Player1);
            }
        });

        /*Player2.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent e) {
                moverRetangulo(e, Player2);
            }
        });*/

        Player1.setFocusTraversable(true);
        //Player2.setFocusTraversable(true);

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

        try (BufferedReader leitor = new BufferedReader(new FileReader("C:/Users/Alecrim/Documents/GitHub/Pong-Online/Temp/Nickname.txt"))){
                    
            NickPlayer1.setText(leitor.readLine());

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
}