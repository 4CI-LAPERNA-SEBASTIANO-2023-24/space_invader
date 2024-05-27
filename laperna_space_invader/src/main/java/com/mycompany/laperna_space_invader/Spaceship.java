package com.mycompany.laperna_space_invader; // https://www.classicgaming.cc/classics/space-invaders/sounds

import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.*;

public class Spaceship extends JPanel implements KeyListener {

    private int score;
    private int life;
    private int x;
    private int y;
    private Laser laser;
    private Timer timer;
    private Timer timerAlien;
    private Timer createLaser;
    private Timer timerAlienLaser;
    private Shield[] shields = {new Shield(75, 400), new Shield(275, 400), new Shield(500, 400), new Shield(700, 400)};
    private Alien[][] aliens = new Alien[4][7];
    Image image = Toolkit.getDefaultToolkit().getImage(".\\src\\main\\java\\com\\mycompany\\laperna_space_invader\\spaceship.png");
    Image dead = Toolkit.getDefaultToolkit().getImage(".\\src\\main\\java\\com\\mycompany\\laperna_space_invader\\spaceDead.png");

    public Spaceship() {
        setFocusable(true);
        addKeyListener(this);
        score = 100;
        life = 3;
        x = 400;
        y = 500;
        laser = null;
        
        timer = new Timer(20, (ActionEvent e) -> {
            moveLaser();
        });
        
        timerAlien = new Timer(750, (ActionEvent e) -> {
            checkEdgeAndMove();
            repaint();
            try {
                this.pew();
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) { }
        });
        
        timerAlienLaser = new Timer(300, (ActionEvent e) -> {
            moveAlienLasers();
            repaint();
        });
        
        createLaser = new Timer(650, (ActionEvent e) -> {
            shootRandomly();
            repaint();
        });
        
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 7; col++) {
                aliens[row][col] = new Alien(50 + col * 80, 50 + row * 60, shields, aliens, this);
            }
        }

        timerAlien.start();
        createLaser.start();
        timerAlienLaser.start();
    }
    
    public void moveAlienLasers() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 7; col++) {
                if (aliens[row][col] != null) {
                    moveLasers(aliens[row][col]);
                }
            }
        }
    }
    
    public void moveLasers(Alien a) {
        if (a.getLaser() != null) {
            a.getLaser().move();
            if (a.getLaser().getY() > 600 || a.getLaser().shieldCollision(shields) || a.getLaser().playerCollision(this)) {
                a.destroyLaser();
            }
            if (a.getLaser() != null && a.getLaser().playerCollision(this)) {
                removeLife();
                a.destroyLaser();
            }
            repaint();
        }
    }
    
    public void shootRandomly() {
        Random random = new Random();
        int row = random.nextInt(4);
        int col = random.nextInt(7);
        Alien targetAlien = aliens[row][col];
        if (targetAlien != null && targetAlien.getLaser() == null) {
            targetAlien.shoot();
        }
    }
    

    public void pew() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        try {
            File f = new File(".\\src\\main\\java\\com\\mycompany\\laperna_space_invader\\fastinvader1.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) { }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        try {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    if (x > 0) {
                        x -= 10;
                    } else {
                        x += 10;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (x < 827) {
                        x += 10;
                    } else {
                        x -= 10;
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    shoot();
                    break;
                default:
                    break;
            }
            repaint();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {}
    }
    
    public void shoot() throws IOException, UnsupportedAudioFileException, UnsupportedAudioFileException, LineUnavailableException, LineUnavailableException, LineUnavailableException {
        if (laser == null) {
            laser = new Laser(x + 30, y - 10);
            laser.pew();
            timer.start();
        }
    }
    
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 100, 25);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (life == 1) {    
            g.drawImage(dead, x, y, this);
        } else {
            g.drawImage(image, x, y, this);
        }
        
        
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 7; col++) {
                if (aliens[row][col] != null) {
                    aliens[row][col].draw(g, this);
                }
            }
        }
        
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 7; col++) {
                if (aliens[row][col] != null) {
                    AlienLaser alienLaser = aliens[row][col].getLaser();
                    if (alienLaser != null) {
                        alienLaser.draw(g);
                    }
                }
            }
        }

        if (laser != null) {
            laser.draw(g);
        }

        for (Shield s : shields) {
            if (s != null) {
                s.draw(g, this);
            }
        }
        
        this.setBackground(Color.black);
    }
    
    private boolean moveRight = true; 

    public void moveAllAliens() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 7; col++) {
                if (aliens[row][col] != null) {
                    if (moveRight) {
                        aliens[row][col].moveRight();
                    } else {
                        aliens[row][col].moveLeft();
                    }
                }
            }
        }
    }

    public void moveAliensDown() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 7; col++) {
                if (aliens[row][col] != null) {
                    aliens[row][col].moveDown();
                }
            }
        }
    }

    public void checkEdgeAndMove() {
        boolean reachedEdge = false;

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 7; col++) {
                if (aliens[row][col] != null) {
                    if (moveRight && aliens[row][col].getX() >= getWidth() - aliens[row][col].getWidth()) {
                        reachedEdge = true;
                        break;
                    } else if (!moveRight && aliens[row][col].getX() <= 0) {
                        reachedEdge = true;
                        break;
                    }
                }
            }
            if (reachedEdge) {
                break;
            }
        }

        if (reachedEdge) {
            moveRight = !moveRight;
            moveAliensDown();
        } else {
            moveAllAliens();
        }
    }

    public void moveLaser() {
        if (laser != null) {
            laser.move();
            if (laser.getY() <= 0 || laser.shieldCollision(shields) || laser.alienCollision(aliens, this)) {
                laser = null;
            }
            repaint();
        }
    }
    
    public void addScore() {
        if (score < 2800) {
            score += 100;
        } else {
            gameOver('s');
        }
    }
    
    public void removeLife() {
        if (life > 1) {
            life -= 1;
        } else {
            gameOver('d');
        }
    }
    
    private void gameOver(char s) {
        Object[] options = {"Exit", "Play Again!"};

        timerAlien.stop();
        createLaser.stop();
        timerAlienLaser.stop();

        int choice;
        if (s == 's') {
            choice = JOptionPane.showOptionDialog(
                this, 
                "Game Over! Score: " + score, 
                "Game Over", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.PLAIN_MESSAGE, 
                null, 
                options, 
                options[1]
            );
        } else {
            choice = JOptionPane.showOptionDialog(
                this, 
                "Game Over! Score: " + score, 
                "You Lose!!!", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.PLAIN_MESSAGE, 
                null, 
                options, 
                options[1]
            );
        }

        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        } else if (choice == JOptionPane.NO_OPTION) {
            restartGame();
        }
    }

    private void restartGame() {
        Container parent = this.getParent();
        parent.removeAll();
        parent.revalidate();
        parent.repaint();
        Laperna_space_invader laperna_space_invader = new Laperna_space_invader();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}