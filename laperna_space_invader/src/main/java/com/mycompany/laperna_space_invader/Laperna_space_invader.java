package com.mycompany.laperna_space_invader;

import javax.swing.*;
import java.awt.Toolkit;

public class Laperna_space_invader extends JFrame {

    public Laperna_space_invader() {       
        setTitle("Spaceship Shooter");

        // panel for initializating the game enter the nickname
                
       startGame();
    }
    
    public void startGame() {
        Spaceship spaceship = new Spaceship();
        add(spaceship);

        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width/2) - 450, (Toolkit.getDefaultToolkit().getScreenSize().height/2) - 300);
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Laperna_space_invader::new);
    }
}
