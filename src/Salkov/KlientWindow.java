package Salkov;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class KlientWindow{
    private JFrame jFrame = new JFrame();
    private JPanel jPanel = new JPanel();
    private Vector<Rectangle> rectangleVector = new Vector<>();
    private byte aByte;


    public static void main(String[] args) {
        new KlientWindow();
    }


    KlientWindow()
    {
      jPanelInit(jPanel, rectangleVector);
      jFrameInit(jFrame, rectangleVector, jPanel);
      rectangleVector.add(new Rectangle());
      timer(rectangleVector, jPanel);
        jFrame.add(jPanel);
    }

    public static void jPanelInit(JPanel jPanel, Vector<Rectangle> rectangleVector){
        jPanel.setSize(800,600);
        jPanel.setBackground(Color.BLACK);
    }
    public static void jFrameInit(JFrame jFrame, Vector<Rectangle> rectangleVector, JPanel jPanel) {
        jFrame.setTitle("Klient");
        jFrame.setBounds(550, 150, 800, 600);
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                    if (jPanel.getWidth() >=  rectangleVector.get(0).centerX + 15) rectangleVector.get(0).centerX += 10;

                if (e.getKeyCode() == KeyEvent.VK_LEFT)
                    if (0 <= rectangleVector.get(0).centerX - 15) rectangleVector.get(0).centerX -= 10;

                if (e.getKeyCode() == KeyEvent.VK_DOWN)
                    if (jPanel.getHeight() >= rectangleVector.get(0).centerY + 15) rectangleVector.get(0).centerY += 10;

                if (e.getKeyCode() == KeyEvent.VK_UP)
                    if (0 <= rectangleVector.get(0).centerY - 15) rectangleVector.get(0).centerY -= 10;
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
}

