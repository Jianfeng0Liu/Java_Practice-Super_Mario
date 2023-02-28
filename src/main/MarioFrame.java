package main;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javazoom.jl.decoder.JavaLayerException;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.*;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class MarioFrame extends JFrame implements KeyListener,Runnable {
    // restore all the background
    private List<BackGround> allBackgroundImage = new ArrayList<>();
    // record the current background
    private BackGround nowBackgroundImage = new BackGround();
    // Double buffering variable
    private Image offScreenImage = null;
    // Mario Object
    private Mario mario = new Mario();
    // Thread object to realize Mario's movement
    private Thread thread = new Thread(this);

    public MarioFrame() throws FileNotFoundException, JavaLayerException {
        // Size: 800*600
        this.setSize(800, 600);
        // Location: Middle
        this.setLocationRelativeTo(null);
        // Visiability
        this.setVisible(true);
        // Shut Down
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Immutable frame size 
        this.setResizable(false);
        // keyboard monitor
        this.addKeyListener(this);
        // Name of frame
        this.setTitle("Super Mario Java @Jianfeng LIU");
        // Initialize images
        StaticValue.init();
        // Initialize Mario
        mario = new Mario(10,355);    // A little bit higher to make sure Mario falls on the first ground

        // Create all scenes
        for (int i = 1; i<= 3; i++) {    // 3 scenes
            allBackgroundImage.add(new BackGround(i, i == 3));   // Parameters: int sort, boolean flag(Judge whether it is the 3rd scene (last scene))
        }
        // Set the first scene as the current scene
        nowBackgroundImage = allBackgroundImage.get(0);    // get() method: arraylist.get(int index)
        mario.setBackground(nowBackgroundImage);
        // Plot
        repaint();    // repaint() used the paint() method
        thread.start();
        // Play the music
        new Music();
    }
    
    @Override
    public void paint(Graphics g) {    // Override the paint() method, which is used in repaint() method
        if(offScreenImage == null) {
            offScreenImage = createImage(800,600);    // createImage(): creates an off-screen drawable image to be used for double buffering.
        }

        Graphics graphics = offScreenImage.getGraphics();    // getGraphics(): creates a graphics context for drawing to an off-screen image.
        graphics.fillRect(0,0,800,600);    // Fill the Image

        // Draw the background (in the buffer)
        graphics.drawImage(nowBackgroundImage.getBackgroundImage(),0,0,this);
        
        // Draw the enemis
        for(Enemy enemy: nowBackgroundImage.getEnemyList()) {
            graphics.drawImage(enemy.getShow(), enemy.getX(), enemy.getY(), this);
        }

        // Draw the obstacle (in the buffer)
        // The flowers need to be occluded, so draw the obstacle later
        for(Obstacle obstacle: nowBackgroundImage.getObstacleList()) {
            graphics.drawImage(obstacle.getShow(), obstacle.getX(), obstacle.getY(), this);
        }

        // Draw the flagpole and castle tower (in the buffer)
        graphics.drawImage(nowBackgroundImage.getFlagpole(), 500, 220, this);
        graphics.drawImage(nowBackgroundImage.getTower(), 620, 270, this);

        // Draw the Mario
        graphics.drawImage(mario.getShow(), mario.getX(), mario.getY(), this);

        // Draw the score
        Color c = graphics.getColor();
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Times New Roman", Font.BOLD, 25));
        graphics.drawString("Your score:"+mario.getScore(), 600, 80);
        graphics.setColor(c);

        // Draw the graphs in the frame
        g.drawImage(offScreenImage,0,0,this);
    }

    public static void main(String[] args) throws FileNotFoundException, JavaLayerException {
        MarioFrame marioFrame = new MarioFrame();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Move to left
        if(e.getKeyCode() == 65) {    // A 65
            mario.leftMove();
        }
        
        // Move to right
        if(e.getKeyCode() == 68) {    // D 68
            mario.rightMove();
        }

        // Jump
        if(e.getKeyCode() == 87) {    // W 87
            mario.jump();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Stop to left
        if(e.getKeyCode() == 65) {
            mario.leftStop();
        }
        // Stop to right
        if(e.getKeyCode() == 68) {
            mario.rightStop();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void run() {
        while(true) {
            repaint();
            try {
                Thread.sleep(50);
                if(mario.getX() >= 800-25) {
                    nowBackgroundImage = allBackgroundImage.get(nowBackgroundImage.getSort());    
                    // sort = 1  when in the first scene (index 0), which could be next scenes index
                    mario.setBackground(nowBackgroundImage);
                    mario.setX(10);
                    mario.setY(355);
                }

                // Judge if Mario dead
                if(mario.isDeath() == true) {
                    JOptionPane.showMessageDialog(this, "Sorry! Your Mario is dead!!!");
                    System.exit(0);    // End the game
                }

                // Judge if the game ended
                if(mario.isGate()) {
                    JOptionPane.showMessageDialog(this, "Congratulation! You passed the game!");
                    System.exit(0);    // End the game
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}