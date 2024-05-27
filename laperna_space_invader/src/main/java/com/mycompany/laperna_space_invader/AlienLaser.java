package com.mycompany.laperna_space_invader;

import java.awt.*;

public class AlienLaser {
    private int x;
    private int y;

    public AlienLaser(int x, int y) {
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
        y += 20;
    }

    public boolean playerCollision(Spaceship player) {
    if (getBounds().intersects(player.getBounds())) {
        player.removeLife();
        return true;
    }
    return false;
}

    public Rectangle getBounds() {
        return new Rectangle(x, y, 5, 20);
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

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, 4, 10);
    }
}