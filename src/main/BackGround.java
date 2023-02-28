package main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BackGround {
/**
* FileName: Background.java
* This class is used for background and obstacles.
*
* @author  Jianfeng LIU
*/
    // Background Images in current scene
    private BufferedImage backgroundImage = null; 
    // Record the current scene number
    private int sort;
    // Determine whether it is the last scene
    private boolean flag;
    // Restore all the obstacles
    private List<Obstacle> obstacleList = new ArrayList<>();
    // Restore all the enemy
    private List<Enemy> enemyList = new ArrayList<>();
    // Flagpole
    private BufferedImage flagpole = null;
    // Castle tower
    private BufferedImage tower = null;
    // Used for judging whether Mario arrives flagpole
    private boolean isReach = false;
    // Used for judging whether the flag down
    private boolean isDown = false;

    // Initialization constructor
    public BackGround() {
    }

    public BackGround(int sort, boolean flag) {
        this.sort = sort;
        this.flag = flag;
        
        if(flag) {    // If it is the last scene 
            backgroundImage = StaticValue.background2;
        } else {
            backgroundImage = StaticValue.background1;
        }
        
        // Plot the soil ground in all 3 scenes
        for(int i = 0; i <= 4; i ++) {    // 5 floors of base soil
            for(int j = 0; j < 27; j++) {
                obstacleList.add(new Obstacle(j*30, 570-30*i, 1, this));    //soil_base.png(type: 1).
            }
        }

        for(int i = 0; i < 27; i++) {    // The width of frame is 800, the width of soil is 30 (800/30: 27).
            obstacleList.add(new Obstacle(i*30, 420, 0, this));    //soil_up.png (type: 0)
        }

        // Plot the soil ground in all 3 scenes
        for(int i = 0; i < 27; i++) {    // The width of frame is 800, the width of soil is 30 (800/30: 27).
            obstacleList.add(new Obstacle(i*30, 420, 0, this));    //soil_up.png (type: 0)
        }

        for(int i = 0; i <= 4; i ++) {    // 5 floors of base soil
            for(int j = 0; j < 27; j++) {
                obstacleList.add(new Obstacle(j*30, 570-30*i, 1, this));    //soil_base.png(type: 1).
            }
        }

        // The other objects in scenes of the game (3 scenes in total)
        if(sort == 1) {    // The first scene
            // Plot the bricks.
            // Destructible brick: brick1.png (type: 2), Indestructible brick: brick2.png (type: 3)
            for(int i = 120; i <= 150; i += 30) {    // Left floating bricks    
                 obstacleList.add(new Obstacle(i, 360, 2, this));
            }
            obstacleList.add(new Obstacle(180, 360, 3, this));

            for(int i = 300; i <= 570; i += 30) {    // Right floating bricks, lower layer
                if(i == 360 || i == 390 || i == 480 || i == 510 || i == 540) {
                    obstacleList.add(new Obstacle(i, 300, 3, this));
                } else {    
                    obstacleList.add(new Obstacle(i, 300, 2, this));
                }
            }

            // for(int i = 420; i <= 450; i += 30) {    // Right floating bricks, higher layer
            //     obstacleList.add(new Obstacle(i, 240, 3, this));
            // }

            // Plot the waterpipe, the width of image is 30 but 25 will look better
            obstacleList.add(new Obstacle(620, 360, 4, this));    // Top left pipe: pipe1.png (type: 4)
            obstacleList.add(new Obstacle(645, 360, 5, this));    // Top right pipe: pipe2.png (type: 5)
            for(int i = 385; i <= 600; i += 25) {    
                obstacleList.add(new Obstacle(620, i, 6, this));    // Left part pipe without top: pipe3.png (type: 6)
                obstacleList.add(new Obstacle(645, i, 7, this));    // Right part pipe without top: pipe4.png (type: 7)
            }

            // Plot the fungus enemy
            enemyList.add(new Enemy(580, 385, true, 1, this));

            // Plot the flower enemy
            enemyList.add(new Enemy(635, 420, true, 2, 330, 420, this));

        } else if(sort == 2) {    // The second scene
            // Plot the bricks.
            // Destructible brick: brick1.png (type: 2), Indestructible brick: brick2.png (type: 3)
            // Brick triangular mound (3 lays: 1-3-5)
            obstacleList.add(new Obstacle(300, 330, 2, this));    // Top layer of mound (1)
            for(int i = 270; i <= 330; i += 30) {    // Mid layer of mound (3)
                if(i == 270 || i == 330) {
                    obstacleList.add(new Obstacle(i, 360, 2, this));
                } else {
                    obstacleList.add(new Obstacle(i, 360, 3, this));
                }
            }
            for(int i = 240; i <= 360; i += 30) {    // Base layer of mound (5)
                if (i == 240 || i == 360) {
                    obstacleList.add(new Obstacle(i,390, 2, this));
                } else {
                    obstacleList.add(new Obstacle(i,390, 3, this));
                }
            }

            obstacleList.add(new Obstacle(240, 300, 2, this));    // Hinder bricks

            for (int i = 360; i <= 540; i += 60){
                obstacleList.add(new Obstacle(i, 270, 3, this));    // Floating bricks
            }

            // Plot the waterpipes (2), the width of image is 30 but 25 will look better
            obstacleList.add(new Obstacle(60, 360, 4, this));    // Top left pipe: pipe1.png (type: 4)
            obstacleList.add(new Obstacle(85, 360, 5, this));    // Top right pipe: pipe2.png (type: 5)
            for(int i = 385; i <= 600; i += 25) {    
                obstacleList.add(new Obstacle(60, i, 6, this));    // Left part pipe without top: pipe3.png (type: 6)
                obstacleList.add(new Obstacle(85, i, 7, this));    // Right part pipe without top: pipe4.png (type: 7)
            }

            obstacleList.add(new Obstacle(620, 330, 4, this));    // Top left pipe: pipe1.png (type: 4)
            obstacleList.add(new Obstacle(645, 330, 5, this));    // Top right pipe: pipe2.png (type: 5)
            for(int i = 355; i <= 600; i += 25) {    
                obstacleList.add(new Obstacle(620, i, 6, this));    // Left part pipe without top: pipe3.png (type: 6)
                obstacleList.add(new Obstacle(645, i, 7, this));    // Right part pipe without top: pipe4.png (type: 7)
            }

            // Plot the fungus enemy
            enemyList.add(new Enemy(200, 385, true, 1, this));
            enemyList.add(new Enemy(500, 385, true, 1, this));

            // Plot the flower enemy
            enemyList.add(new Enemy(75, 420, true, 2, 330, 420, this));
            enemyList.add(new Enemy(635, 420, true, 2, 300, 390, this));

        } else if(sort == 3) {    // The third scene
            // Plot the bricks.
            // Destructible brick: brick1.png (type: 2), Indestructible brick: brick2.png (type: 3)
            // Right brick (half) triangular mound (5 lays: 1-2-3-4-5)
            int temp = 290;    // Used to restrict x-axis bricks
            for(int i = 390; i >= 270; i -= 30) {    // From base(5) to top (1)
                for(int j = temp; j <= 410; j += 30) {
                    obstacleList.add(new Obstacle(j, i, 3, this));
                }
                temp += 30;
            }

            // Left brick (half) triangular mound (2 lays: 1-2)
            temp = 60;
            for(int i = 390; i >= 360; i -= 30) {
                for(int j = temp; j <= 90; j += 30) {
                    obstacleList.add(new Obstacle(j, i, 3, this));
                }
                temp += 30;
            }

            // Plot the fungus enemy
            enemyList.add(new Enemy(150, 385, true, 1, this));

            // Plot the flagpole
            flagpole = StaticValue.flagpole;

            // Plot the tower
            tower = StaticValue.tower;

            // Plot the flag on the flagpole
            obstacleList.add(new Obstacle(515, 220, 8, this));    // flag.png (type: 8)
        }
    }

    
    // Getter
    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    public int getSort() {
        return sort;
    }

    public boolean getFlag() {
        return flag;
    }
    
    public List<Obstacle> getObstacleList() {
        return obstacleList;
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }

    public BufferedImage getFlagpole(){
        return flagpole;
    }

    public BufferedImage getTower(){
        return tower;
    }

    public boolean isReach() {
        return isReach;
    }

    public boolean isDown() {
        return isDown;
    }

    // Setter
    public void setReach(boolean isReach) {
        this.isReach = isReach;
    }

    public void setDown(boolean isDown) {
        this.isDown = isDown;
    }
}