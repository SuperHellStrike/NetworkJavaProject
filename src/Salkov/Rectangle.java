package Salkov;

import javax.swing.*;
import java.awt.*;


public class Rectangle {
    private int width = 10;
    private int height = 10;
    private int centerX;
    private int centerY;
    private int id;

    public Rectangle(int id, int x, int y){
        this.id = id;
        centerX = x;
        centerY = y;
    }


    public void paint(Graphics g){
        g.setColor(Color.GREEN);
        g.drawRect(centerX - 5 , centerY - 5 , width, height);
        g.fillRect(centerX - 5 , centerY - 5 , width, height);
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public int getId() {
        return id;
    }
}