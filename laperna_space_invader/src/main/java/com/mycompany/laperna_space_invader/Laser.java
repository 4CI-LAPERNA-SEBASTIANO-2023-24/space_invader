package com.mycompany.laperna_space_invader;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Laser {

    private int x;
    private int y;
    private final int WIDTH = 5;
    private final int HEIGHT = 20;

    public Laser(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move() {
        y -= 20;
    }

    public void pew() {
        try {
            File f = new File(".\\src\\main\\java\\com\\mycompany\\laperna_space_invader\\shoot.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) { }
    }

    public boolean shieldCollision(Shield[] shields) {
    for (int i = 0; i < shields.length; i++) {
        if (shields[i] != null && getBounds().intersects(shields[i].getBounds())) {
            shields[i].reduceHealth();
            if (shields[i].getHealth() <= 0) {
                shields[i] = null;
            }
            return true;
        }
    }
    return false;
}
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, 5, 20);
    }

    public boolean alienCollision(Alien[][] aliens, Spaceship s) {
        for (int row = 0; row < aliens.length; row++) {
            for (int col = 0; col < aliens[row].length; col++) {
                if (aliens[row][col] != null && 
                    this.x >= aliens[row][col].getX() && 
                    this.x <= aliens[row][col].getX() + aliens[row][col].getWidth() && 
                    this.y >= aliens[row][col].getY() && 
                    this.y <= aliens[row][col].getY() + aliens[row][col].getHeight()) {
                    aliens[row][col] = null;
                    s.addScore();
                    return true;
                }
            }
        }
        return false;
    }

   
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }
}