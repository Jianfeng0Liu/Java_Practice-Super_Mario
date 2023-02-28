package main;

import java.awt.image.BufferedImage;

public class Enemy implements Runnable{
/**
* FileName: Mario.java
* This class is used for enemy show, interaction.
*
* @author  Jianfeng LIU
*/
    // Current coordinates
    private int x;
    private int y;
    // The type of enemy
    private int type;
    // The direction of movement (left and up: true, right and down: false)
    private boolean faceL = true;
    // Enemy image
    private BufferedImage show = null;
    // Index, to get the shifting images of fungus and flower
    private int enemyIndex = 0;
    // BackGround object to gain the information of Obstacles
    private BackGround background = new BackGround();
    // Flower movement scope
    private int maxUp = 0;
    private int maxDown = 0;
    // Define a thread object
    private Thread thread = new Thread(this);
    
    // The fungus enemy
    public Enemy(int x, int y, boolean faceL, int type, BackGround background) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.faceL = faceL;
        this.background = background;
        show = StaticValue.fungus.get(0);
        thread.start();

    }

    // The flower enemy
    public Enemy(int x, int y, boolean faceL, int type, int maxUp, int maxDown, BackGround background) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.faceL = faceL;
        this.maxUp = maxUp;
        this.maxDown = maxDown;
        this.background = background;
        show = StaticValue.flower.get(0);
        thread.start();
    }

    // Fungus death method
    public void death() {
        show = StaticValue.fungus.get(2);    // Get the death image of fungus
        this.background.getEnemyList().remove(this);
    }

    @Override
    public void run() {
        while(true) {
            // Judge whether fungus enemy
            if(type == 1) {
                if (faceL) {
                    this.x -= 2;    // Move to left
                } else {
                    this.x += 2;    // Move to right
                }

                enemyIndex = (enemyIndex == 1? 0:1);    // To use to shift the walking image of fungus
                show = StaticValue.fungus.get(enemyIndex);

            }

            // Judge whether fungus can move to left or right
            boolean canL = true;
            boolean canR = true;

            for(int i = 0; i < background.getObstacleList().size(); i++) {
                Obstacle ob = background.getObstacleList().get(i);
                if((ob.getY() > this.y-65 && ob.getY() < this.y+35) && ob.getX() == this.x+40) {    // Meet the right obstacles
                    canR = false;
                }

                if((ob.getY() > this.y-65 && ob.getY() < this.y+35) && ob.getX() == this.x-40) {    // Meet the left obstacles
                    canL = false;
                }
            }

            if(faceL && !canL || this.x == 0) {    // If fungus moves to left and alrealdy meets the obstacle or move to the most left
                faceL = false;
            }

            if((!faceL && !canR) || this.x == 765) {    // If fungus moves to right and alrealdy meets the obstacle or move to the most right
                faceL = true;
            }

            // Judge whether flower enemy
            if(type == 2) {
                if (faceL) {    // faceL means move up
                    this.y -= 2;    // Move to up
                } else {
                    this.y += 2;    // Move to low
                }

                enemyIndex = (enemyIndex == 1? 0:1);    // To use to shift the image of flower
                
                // Judge whether flower can move to up or down
                if(faceL && this.y == maxUp) {    // If the flower moves to up and moves to highest
                    faceL = false;
                }

                if(!faceL && this.y == maxDown) {    // If the flower moves to down and moves to lowest
                    faceL = true;
                }
                show = StaticValue.flower.get(enemyIndex);
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Getter
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getShow() {
        return show;
    }

    public int getType() {
        return type;
    }
}
