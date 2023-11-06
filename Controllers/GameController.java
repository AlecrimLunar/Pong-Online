package Controllers;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;

import Controllers.Sockets.Threads.BolaThread;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

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
    private Rectangle GoalPlayer1;
    @FXML
    private Rectangle GoalPlayer2;
    @FXML
    private Text NickPlayer1;
    @FXML
    private Text NickPlayer2;
    @FXML
    private Text PPlayer1;
    @FXML
    private Text PPlayer2;

    @FXML
    void initialize() {

        try (BufferedReader leitor = new BufferedReader(new FileReader("Temp/Jogador1.txt"))) {

            String linha = leitor.readLine();
            if (linha.equalsIgnoreCase("true")) {
                setJogador1(true);
            } else {
                setJogador1(false);
            }

        } catch (IOException er) {
            System.out.println("Ocorreu um erro ao ler o arquivo: " + er.getMessage());
        }

        /*
         * try {
         * Thread.sleep(2000);
         * } catch (InterruptedException e) {
         * e.printStackTrace();
         * }
         */

        if (jogador1) {
            Player1.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent e) {
                    moverRetangulo(e, Player1);
                }
            });
            Player1.setFocusTraversable(true);
        } else {
            Player2.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent e) {
                    moverRetangulo(e, Player2);
                }
            });
            Player2.setFocusTraversable(true);
        }
        if (jogador1) {

            Thread server = new Thread(new Runnable() {
                @Override
                public void run() {

                    ServerSocket s;
                    int port;

                    try (BufferedReader leitor = new BufferedReader(new FileReader("Temp/Porta.txt"))) {

                        port = Integer.parseInt(leitor.readLine());

                    } catch (IOException er) {
                        System.out.println("Ocorreu um erro ao ler o arquivo: " + er.getMessage());
                        return;
                    }

                    try {
                        s = new ServerSocket(port);
                    } catch (IOException e) {
                        System.out.println("Erro na porta (server)");
                        return;
                    }
                    try {
                        Socket ns = s.accept();
                        serverSocket(ns);
                    } catch (IOException e) {
                        System.out.println("Erro na conexão");
                    }
                }
            });
            server.start();

            Thread movimentoThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    boolean bateuX = false;
                    boolean bateuY = false;
                    double xInicial = Bola.getX();
                    double yInicial = Bola.getY();

                    while (true) {

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

                        // y
                        if (Bola.getBoundsInParent().intersects(BarreiraTop.getBoundsInParent())) {
                            bateuY = true;
                        }
                        if (Bola.getBoundsInParent().intersects(BarreiraBai.getBoundsInParent())) {
                            bateuY = false;
                        }

                        // x
                        if (Bola.getBoundsInParent().intersects(Player1.getBoundsInParent())) {
                            bateuX = true;
                        }
                        if (Bola.getBoundsInParent().intersects(Player2.getBoundsInParent())) {
                            bateuX = false;
                        }

                        if (Bola.getBoundsInParent().intersects(GoalPlayer1.getBoundsInParent())) {
                            Bola.setX(xInicial);
                            Bola.setY(yInicial);
                            bateuX = false;
                        }
                        if (Bola.getBoundsInParent().intersects(GoalPlayer2.getBoundsInParent())) {
                            Bola.setX(xInicial);
                            Bola.setY(yInicial);
                            bateuX = true;
                        }
                    }
                }
            });
            movimentoThread.start();
        } else {
            Thread client = new Thread(new Runnable() {

                @Override
                public void run() {

                    String ip = "192.168.0.111";
                    int port = 123;

                    try (BufferedReader leitor = new BufferedReader(new FileReader("Temp/Ip.txt"))) {

                        ip = leitor.readLine();
                        port = Integer.parseInt(leitor.readLine());

                    } catch (IOException er) {
                        System.out.println("Ocorreu um erro ao ler o arquivo: " + er.getMessage());
                    }

                    clientSocket(ip, port);
                }
            });
            client.start();
        }

        try (BufferedReader leitor = new BufferedReader(new FileReader("Temp/Nickname.txt"))) {
            if (jogador1) {
                NickPlayer1.setText(leitor.readLine());
            } else {
                NickPlayer2.setText(leitor.readLine());
            }

        } catch (IOException er) {
            System.out.println("Ocorreu um erro ao ler o arquivo: " + er.getMessage());
            return;
        }
        Thread pontuacao = new Thread(new Runnable() {
            @Override
            public void run() {
                int pPlayer1 = 0;
                int pPlayer2 = 0;

                while (jogador1) {
                    // pontuação
                    if (Bola.getBoundsInParent().intersects(GoalPlayer1.getBoundsInParent())) {
                        PPlayer1.setText("Pontos: " + (pPlayer1 += 1));
                    }
                    if (Bola.getBoundsInParent().intersects(GoalPlayer2.getBoundsInParent())) {
                        PPlayer2.setText("Pontos: " + (pPlayer2 += 1));
                    }
                }
            }
        });
        pontuacao.start();

    }

    @FXML
    void moverPlayer(KeyEvent e) {
        double movimento = 10;

        switch (e.getCode()) {
            case UP:
                if (Player1.getBoundsInParent().intersects(BarreiraTop.getBoundsInParent())) {
                } else {
                    Player1.setY(Player1.getY() - movimento);
                }
                break;
            case DOWN:
                if (Player1.getBoundsInParent().intersects(BarreiraBai.getBoundsInParent())) {
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
                if (rectangle.getBoundsInParent().intersects(BarreiraTop.getBoundsInParent())) {
                } else {
                    rectangle.setY(rectangle.getY() - movimento);
                }
                break;
            case DOWN:
                if (rectangle.getBoundsInParent().intersects(BarreiraBai.getBoundsInParent())) {
                } else {
                    rectangle.setY(rectangle.getY() + movimento);
                }
                break;
            default:
                break;
        }
    }

    private void serverSocket(Socket ns) {
        Thread serverThread = new Thread(new Runnable() {

            @Override
            public void run() {

                DataInputStream in;
                DataOutputStream out;

                try {
                    in = new DataInputStream(ns.getInputStream());
                    out = new DataOutputStream(ns.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                try {
                    NickPlayer2.setText(in.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try (BufferedReader leitor = new BufferedReader(new FileReader("Temp/Nickname.txt"))) {
                    out.writeUTF(leitor.readLine());
                } catch (IOException er) {
                    System.out.println("Ocorreu um erro ao ler o arquivo: " + er.getMessage());
                    return;
                }

                while (true) {

                    StringBuilder sb = new StringBuilder();
                    String dados;

                    sb.append(Bola.getX() + " ");
                    sb.append(Bola.getY() + " ");
                    sb.append(Player1.getY());
                    try {
                        out.writeUTF(sb.toString());
                    } catch (IOException e) {
                        System.out.println("Erro ao enviar (jogador 1)");
                        return;
                    }

                    try {
                        dados = in.readUTF();
                    } catch (IOException e) {
                        System.out.println("Erro ao receber (jogador 1)");
                        return;
                    }

                    double position = Double.parseDouble(dados);
                    Player2.setY(position);
                }
            }
        });
        serverThread.start();
    }

    private void clientSocket(String ip, int porta) {
        Thread client = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket s;
                try {
                    s = new Socket(ip, porta);
                } catch (IOException e) {
                    System.out.println("Erro na conexão (cliente)");
                    return;
                }

                DataInputStream in;
                DataOutputStream out;

                try {

                    in = new DataInputStream(s.getInputStream());
                    out = new DataOutputStream(s.getOutputStream());

                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                try (BufferedReader leitor = new BufferedReader(new FileReader("Temp/Nickname.txt"))) {
                    out.writeUTF(leitor.readLine());
                } catch (IOException er) {
                    System.out.println("Ocorreu um erro ao ler o arquivo: " + er.getMessage());
                    return;
                }

                try {
                    NickPlayer1.setText(in.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                while (true) {
                    String dados;

                    try {
                        dados = in.readUTF();
                    } catch (IOException e) {
                        System.out.println("Erro ao ler (cliente)");
                        return;
                    }

                    String[] sd = dados.split(" ");
                    double[] dado = new double[sd.length];

                    for (int i = 0; i < sd.length; i++) {
                        dado[i] = Double.parseDouble(sd[i]);
                    }

                    Bola.setX(dado[0]);
                    Bola.setY(dado[1]);
                    Player1.setY(dado[2]);

                    String send = Player2.getY() + "";

                    try {
                        out.writeUTF(send);
                    } catch (IOException e) {
                        System.out.println("Erro ao escrever (cliente)");
                    }
                }

            }
        });
        client.start();
    }

    public void setJogador1(boolean jogador1) {
        this.jogador1 = jogador1;
    }
}