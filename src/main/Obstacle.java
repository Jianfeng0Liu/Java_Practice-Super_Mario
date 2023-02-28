package main;

import java.awt.image.BufferedImage;

public class Obstacle implements Runnable{
/**
* FileName: Obstacle.java
* This class is used for obstacles class.
*
* @author  Jianfeng LIU
*/
    // Current coordinates
    private int x;
    private int y;
    // The type of obstacle
    private int type;
    // Display image
    private BufferedImage show= null;
    // Current scene
    private BackGround background = null;
    // Define a thread object
    private Thread thread = new Thread(this);

    // Initialization constructor
    public Obstacle (int x, int y, int type, BackGround background) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.background = background;
        show = StaticValue.obstacle.get(type);    // Get the image of specific obstacle
        if(type == 8) {    //flag.png (type: 8)
            thread.start();
        }
    }

    // Getter
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }

    public BufferedImage getShow() {
        return show;
    }

    @Override
    public void run() {
        while(true) {
            if(this.background.isReach()) {    // Mario reaches flagpole
                if(this.y < 374) {
                    this.y += 5;    // let the flag down
                } else {
                    this.background.setDown(true);
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } 
    }
}
