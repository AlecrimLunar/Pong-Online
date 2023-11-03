package Controllers.Sockets.Threads;


import java.util.concurrent.Callable;

import javafx.scene.shape.Rectangle;

public class BolaThread implements Callable<double[]>{
    
    private Rectangle bola;
    private double movimentoX;
    private double movimentoY;
    private boolean bateuX;
    private boolean bateuY;

    public BolaThread(Rectangle bola, boolean bateuX, boolean bateuY) {
        this.bola = bola;
        this.bateuX = bateuX;
        this.bateuY = bateuY;
        movimentoX = -2.5;
        movimentoY = -2.5;
    }

    public double x(){
        if(bateuX){
            movimentoX = movimentoX*-1;
        }
        return bola.getX()+movimentoX;
    }

    public double y(){
        if(bateuY){
            movimentoY = movimentoY*-1;
        }
            return bola.getY()+movimentoY;
    }

    @Override
    public double[] call() throws Exception {
        double[] a = new double[2];
        a[0] = x();
        a[1] = y();
        return a;
    }
}
