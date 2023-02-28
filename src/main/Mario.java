package main;

import java.awt.image.BufferedImage;

public class Mario implements Runnable{
/**
* FileName: Mario.java
* This class is used for Mario show, control.
*
* @author  Jianfeng LIU
*/
    // Current coordinates
    private int x;
    private int y;
    // Current Status
    private String status;
    // Image corresponding to the current status
    private BufferedImage show = null;
    // BackGround object to gain the information of Obstacles
    private BackGround background = new BackGround();
    // Actions of Mario
    private Thread thread = null;
    // Speed of moving (in x-axis)
    private int xSpeed;
    // Speed of jumping (in y_axis)
    private int ySpeed;
    // Index, to get the running images of Mario
    private int index;
    // The time of jumping up
    private int upTime = 0;
    // Judge whether Mario reaches the gate of castle
    private boolean isGate;
    // Judge whether Mario dead
    private boolean isDeath = false;
    // Score
    private int score = 0;

    // Initialization constructor
    public Mario() {
    }

    public Mario(int x, int y) {
        this.x = x;
        this.y = y;
        show = StaticValue.standR;    // Initially stand to the right
        this.status = "stand--right";
        thread = new Thread(this);
        thread.start();
    }

    // Mario moves to left
    public void leftMove() {
        xSpeed = -5;    // Change the moving speed
        // Judge if Mario reaches flag, then he can't move
        if(background.isReach()) {
            xSpeed = 0;
        }
        // Judge if Mario is not on the ground
        if (status.indexOf("jump") != -1) {
            status = "jump--left";
        } else {
            status = "move--left";
        }
    }

    // Mario moves to right
    public void rightMove() {
        xSpeed = 5;    // Change the moving speed
        // Judge if Mario reaches flag, then he can't move
        if(background.isReach()) {
            xSpeed = 0;
        }
        // Judge if Mario is not on the ground
        if (status.indexOf("jump") != -1) {
            status = "jump--right";
        } else {
            status = "move--right";
        }
    }

    // Mario stops to left
    public void leftStop() {
        xSpeed = 0;    // change the moving speed
        // Judge if Mario is not on the ground
        if (status.indexOf("jump") != -1) {
            status = "jump--left";
        } else {
            status = "stop--left";
        }
        // System.out.println("stop left:" + status);
    }

    // Mario stops to right
    public void rightStop() {
        xSpeed = 0;    // Change the moving speed
        // Judge if Mario is not on the ground
        if (status.indexOf("jump") != -1) {
            status = "jump--right";
        } else {
            status = "stop--right";
        }
        // System.out.println("stop right:" + status);
    }

    // Mario jumps up
    public void jump() {
        if(status.indexOf("jump") == -1) {
            if (status.indexOf("left") != -1) {
                status = "jump--left";
            } else {
                status = "jump--right";
            }
            ySpeed = -10;
            upTime = 7;
        }
        // Judge if Mario reaches flag, then he can't move
        if(background.isReach()) {
            ySpeed = 0;
        }
        // System.out.println("Jump:" + status);
    }

    // Mario falls
    public void fall() {
        if(status.indexOf("left") != -1) {
            status = "jump--left";
        } else {
            status = "jump--right";
        }
        ySpeed = 10;
    }

    // Mario dead
    public void death() {
        isDeath = true;
    }


    @Override    // Starting the thread causes the object's run method to be called in that separately executing thread.
    public void run() {
        while(true) {
            boolean onObstacle = false;    // Used to judge whether Mario is on the obstacles
            boolean canRight = true;    // Used to judge whether obstacle on the right side of Mario
            boolean canLeft = true;    // Used to judge whether obstacle on the left side of Mario

            // Judge whether Mario arrives the location of flagpole
            if(background.getFlag() && this.x >= 500) {
                this.background.setReach(true);    // Mario arrives flagpole

                // Judge whether the flag down
                if(this.background.isDown()) {    // If the flag is down, Mario move to castle
                    status = "move--right";
                    if(x < 690) {
                        x += 5;
                    } else {
                        isGate = true;
                    }
                } else {
                    if(y < 395) {    // Mario is in the air
                        xSpeed = 0;
                        this.y += 5;
                        status = "jump--right";
                    } else {    // Mario is on the ground
                        this.y = 395;
                        status = "stop--right";
                    }
                }
            } else {
                // Traversing all obstacles in the scene
                for(int i = 0; i < background.getObstacleList().size(); i ++) {
                    Obstacle ob = background.getObstacleList().get(i);
                    // Judge whether Mario is on the obstacles
                    if(ob.getY() == this.y+25 && (ob.getX() > this.x-30 && ob.getX() <this.x+25)){
                        onObstacle = true;
                    }

                    // Judge whether Mario jumps and touch the brick
                    if((ob.getY() >= this.y-30 && ob.getY() <= this.y-20) && (ob.getX() > this.x-30 && ob.getX() < this.x+25)) {
                        if(ob.getType() == 2) {    // Destructible brick (type: 2)
                            background.getObstacleList().remove(ob);
                            score += 1;
                        }
                        upTime = 0;    // Mario falls directly after touching the brick
                    }

                    // Judge whether Mario can walk toward right
                    if((ob.getY() > this.y-30 && ob.getY() < this.y+25) && ob.getX() == this.x+25) {
                        canRight = false;
                    }

                    // Judge whether Mario can walk toward left
                    if((ob.getY() > this.y-30 && ob.getY() < this.y+25) && ob.getX() == this.x-30) {
                        canLeft = false;
                    }
                }

                // Mario dead or kill fungus
                for(int i = 0; i < background.getEnemyList().size(); i++) {
                    Enemy e = background.getEnemyList().get(i);
                    if(e.getY() == this.y+20 && (e.getX() < this.x+25 && e.getX() > this.x-35)) {    // If Mario is on the enemy
                        if(e.getType() == 1) {    // Fungus enemy will be killed
                            e.death();
                            upTime = 3;
                            ySpeed = -10;    // Mario bounces
                            score += 2;
                        } else if (e.getType() == 2) {
                            death();    // Mario dead
                        }
                    }

                    if((e.getY() > this.y-35 && e.getY() < this.y+20) && (e.getX() > this.x-35 && e.getX() < this.x+25)) {
                        death();    // Mario dead
                    }
                }

                // Mario jump and fall
                if(onObstacle && upTime == 0) {    // Mario on the obstacles
                    if(status.indexOf("left") != -1) {
                        if(xSpeed != 0) {
                            status = "move--left";
                        } else {
                            status = "stop-left";
                        }
                    } else {
                        if(xSpeed != 0) {
                            status = "move--right";
                        } else {
                            status = "stop--right";
                        }
                    }
                } else {
                    if(upTime != 0) {
                        upTime --;
                    } else {
                        fall();
                    }
                    y += ySpeed;
                }
            }


            
            // Judge whether Mario is moving or not
            if((canLeft && xSpeed < 0) || (canRight && xSpeed > 0)) {    
                x += xSpeed;
                if(x < 0) {    // Judge if Mario moves to the most left of the scene
                    x = 0;    // Mario cannot move to left anymore
                } 
            }

            // When Mario is moving, toggle between 0 and 1 to form a Mario motion image (there are 2 images of running)
            if(status.contains("move")) {    
                index = (index == 0? 1:0);    
            }
            
            // Judge whether Mario moves to left or right
            if("move--left".equals(status)) {
                show = StaticValue.runL.get(index);
            } else if ("move--right".equals(status)) {
                show = StaticValue.runR.get(index);
            }

            // Judge whether Mario stops to left or right
            if("stop--left".equals(status)) {
                show = StaticValue.standL;
            } else if ("stop--right".equals(status)) {
                show = StaticValue.standR;
            }

            // Judge whether Mario jumps to left or right
            if("jump--left".equals(status)) {
                show = StaticValue.jumpL;
            } else if("jump--right".equals(status)) {
                show = StaticValue.jumpR;
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
    
    public boolean isGate() {
        return isGate;
    }

    public boolean isDeath() {
        return isDeath;
    }

    public int getScore() {
        return score;
    }

    // Setter
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setShow(BufferedImage show) {
        this.show = show;
    }

    public void setBackground(BackGround background) {
        this.background = background;
    }
}
