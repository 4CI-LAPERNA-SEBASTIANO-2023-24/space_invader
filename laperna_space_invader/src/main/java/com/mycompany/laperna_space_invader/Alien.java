package com.mycompany.laperna_space_invader;

import java.awt.*;

public class Alien {
    private int x;
    private int y;
    private int width;
    private int height;
    private Image image;
    private Image imageMoving;
    private AlienLaser laser;

    public Alien(int x, int y, Shield[] shields, Alien[][] aliens, Spaceship spaceship) {
        this.x = x;
        this.y = y;
        this.width = 41;
        this.height = 42;
        this.image = Toolkit.getDefaultToolkit().getImage(".\\src\\main\\java\\com\\mycompany\\laperna_space_invader\\first_alien_staying.png");
        this.imageMoving = Toolkit.getDefaultToolkit().getImage(".\\src\\main\\java\\com\\mycompany\\laperna_space_invader\\first_alien_moving.png");
        this.laser = null;
    }

    public void moveRight() {
        x += 5;
    }

    public void moveLeft() {
        x -= 5;
    }

    public void moveDown() {
        y += 30;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void draw(Graphics g, Spaceship spaceship) {
        if (x % 2 == 0) {
            g.drawImage(image, x, y, width, height, spaceship);
        } else {
            g.drawImage(imageMoving, x, y, width, height, spaceship);
        }
    }

    public void shoot() {
        if (laser == null) {
           laser = new AlienLaser(x + width / 2, y + height);
        }
    }

    public AlienLaser getLaser() {
        return laser;
    }
    
    public void destroyLaser() {
        this.laser = null;
        
    }
}
