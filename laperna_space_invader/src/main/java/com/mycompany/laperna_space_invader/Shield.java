package com.mycompany.laperna_space_invader;

import java.awt.*;
import javax.swing.JPanel;

public class Shield {

    private int x;
    private int y;
    private int width;
    private int high;
    Image image = Toolkit.getDefaultToolkit().getImage(".\\src\\main\\java\\com\\mycompany\\laperna_space_invader\\shieldFull.png");
    Image image90 = Toolkit.getDefaultToolkit().getImage(".\\src\\main\\java\\com\\mycompany\\laperna_space_invader\\shield90.png");
    Image image80 = Toolkit.getDefaultToolkit().getImage(".\\src\\main\\java\\com\\mycompany\\laperna_space_invader\\shield80.png");
    Image image70 = Toolkit.getDefaultToolkit().getImage(".\\src\\main\\java\\com\\mycompany\\laperna_space_invader\\shield70.png");
    Image image60 = Toolkit.getDefaultToolkit().getImage(".\\src\\main\\java\\com\\mycompany\\laperna_space_invader\\shield60.png");
    int health;

    public Shield(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 100;
        this.high = 75;
        this.health = 100;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getHealth() {
        return health;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, high);
    }
    
    public void reduceHealth() {
    if (health > 60) {
        health -= 10;
    } else {
        health = 0;
    }
}

    public void draw(Graphics g, JPanel t) {
        switch (health) {
            case 100:
                g.drawImage(image, x, y, width, high, t);
                break;
            case 90:
                g.drawImage(image90, x, y, width, high, t);
                break;
            case 80:
                g.drawImage(image80, x, y, width, high, t);
                break;
            case 70:
                g.drawImage(image70, x, y, width, high, t);
                break;
            case 60:
                g.drawImage(image60, x, y, width, high, t);
                break;
            default:
                break;
        }
        
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return high;
    }
}