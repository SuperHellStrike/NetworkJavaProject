package Salkov;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Rectangle {
    int width = 10;
    int height = 10;
    int centerX = 5 ;
    int centerY= 5;

    public Rectangle(){

    }


    public void paint(Graphics g){
        g.setColor(Color.GREEN);
        g.drawRect(centerX - 5 , centerY - 5 , width, height);
        g.fillRect(centerX - 5 , centerY - 5 , width, height);
    }

    public void move(JPanel jPanel){
    }


}