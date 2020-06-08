package Salkov;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ClientWindow {
    private JFrame jFrame = new JFrame();
    private JPanel jPanel = new JPanel();
    private Vector<Rectangle> rectangleVector = new Vector<>();
    private static Socket socket;
    private DatagramSocket datagramSocket;
    private TransferInThread transferInThread;



    public static void main(String[] args) throws IOException {
        new ClientWindow();
    }


    ClientWindow() throws IOException {
        jPanelInit(jPanel, rectangleVector);
        jFrameInit(jFrame, rectangleVector, jPanel);
        jFrame.add(jPanel);
        socket = new Socket("127.0.0.1", 60000);
        datagramSocket = new DatagramSocket();
        transferInThread = new TransferInThread(datagramSocket, socket, rectangleVector);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeInt(Integer.parseInt(JOptionPane.showInputDialog(jFrame, "Write object id")));
        dos.writeInt(transferInThread.getUDP());
        timer(rectangleVector, jPanel);
    }

    public static void jPanelInit(JPanel jPanel, Vector<Rectangle> rectangleVector){
        jPanel.setSize(800,600);
        jPanel.setBackground(Color.BLACK);
    }
    public static void jFrameInit(JFrame jFrame, Vector<Rectangle> rectangleVector, JPanel jPanel) {
        jFrame.setTitle("Client");
        jFrame.setBounds(550, 150, 800, 600);
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch(e.getKeyCode()){
                    case KeyEvent.VK_RIGHT:
                        if (jPanel.getWidth() >= rectangleVector.get(0).getCenterX() + 15) {
                            rectangleVector.get(0).setCenterX(rectangleVector.get(0).getCenterX() + 10);
                            try {
                                send(4, socket);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (0 <= rectangleVector.get(0).getCenterX() - 15) {
                            rectangleVector.get(0).setCenterX(rectangleVector.get(0).getCenterX() - 10);
                            try {
                                send(3,socket);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (jPanel.getHeight() >= rectangleVector.get(0).getCenterY() + 15){
                            rectangleVector.get(0).setCenterY(rectangleVector.get(0).getCenterY() + 10);
                            try {
                                send(2,socket);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (0 <= rectangleVector.get(0).getCenterY() - 15) {
                            rectangleVector.get(0).setCenterY(rectangleVector.get(0).getCenterY() - 10);
                            try {
                                send(1,socket);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        break;
                    case KeyEvent.VK_ESCAPE:
                        try {
                            send(0,socket);
                            System.exit(0);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        break;

                    default:
                        break;
                }
            }
        });
        jFrame.setVisible(true);
    }

    public static void draw(Vector<Rectangle> vector, JPanel panel) {
        int w = panel.getWidth();
        int h = panel.getHeight();
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < vector.size(); i++) vector.get(i).paint(bufferedImage.getGraphics());
        panel.getGraphics().drawImage(bufferedImage, 0, 0, null);
    }
    public static void timer(Vector<Rectangle> vector, JPanel panel){
        java.util.Timer timer = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                draw(vector, panel);
            }
        };
        timer.schedule(tt, 0, 10);
    }
    public static void send(int command, Socket socket) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeByte(command);
    }
}

