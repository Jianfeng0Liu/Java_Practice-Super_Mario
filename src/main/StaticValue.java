package main;

import java.util.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class StaticValue {
/**
* FileName: StaticValue.java
* This class is used for images of objects.
*
* @author  Jianfeng LIU
*/
    // Background
    public static BufferedImage background1 = null;
    public static BufferedImage background2 = null;

    // Castle tower
    public static BufferedImage tower = null;
    // Flagpole
    public static BufferedImage flagpole = null;
    // Obstacle
    public static List<BufferedImage> obstacle = new ArrayList<>();    // Multiple obstacles

    // Mario stands forward left
    public static BufferedImage standL = null;
    // Mario stands forward right
    public static BufferedImage standR = null;
    // Mario jumps to left
    public static BufferedImage jumpL = null;
    // Mario jumps to right
    public static BufferedImage jumpR = null;
    // Mario runs to left
    public static List<BufferedImage> runL = new ArrayList<>();    // 2 pictures    
    // Mario runs to right
    public static List<BufferedImage> runR = new ArrayList<>();

    // Fungus enemy
    public static List<BufferedImage> fungus = new ArrayList<>();    // 3 pictures: 2 walk, 1 dead
    // Flower enemy
    public static List<BufferedImage> flower = new ArrayList<>();    // 2 pictures: mouth open and close

    // The prefix of the images path
    public static String path = System.getProperty("user.dir")+"/src/images/";


    // Initialization constructor
    public static void init() {
        try {
            // Background
            background1 = ImageIO.read(new File(path+"bg1.png"));
            background2 = ImageIO.read(new File(path+"bg2.png"));

            // Castle tower
            tower = ImageIO.read(new File(path+"tower.png"));
            // Flagpole
            flagpole = ImageIO.read(new File(path+"flagpole.png"));
            // Obstacle
            obstacle.add(ImageIO.read(new File(path+"soil_up.png")));
            obstacle.add(ImageIO.read(new File(path+"soil_base.png")));
            obstacle.add(ImageIO.read(new File(path+"brick1.png")));
            obstacle.add(ImageIO.read(new File(path+"brick2.png")));    // Indestructible brick
            for(int i = 1; i <= 4; i++) {    // Pipe: 4 pictures
                obstacle.add(ImageIO.read(new File(path+"pipe"+i+".png")));    
            }
            obstacle.add(ImageIO.read(new File(path+"flag.png")));
            

            // Mario stands forward left
            standL = ImageIO.read(new File(path+"s_mario_stand_L.png"));
            // Mario stands forward right
            standR = ImageIO.read(new File(path+"s_mario_stand_R.png"));
            // Mario jumps to left
            jumpL = ImageIO.read(new File(path+"s_mario_jump_L.png"));
            // Mario jumps to right
            jumpR = ImageIO.read(new File(path+"s_mario_jump_R.png"));
            // Mario runs to left
            for(int i = 1; i <= 2; i++) {
                runL.add(ImageIO.read(new File(path+"s_mario_run"+i+"_L.png")));
            }
            // Mario runs to right
            for(int i = 1; i <= 2; i++) {
                runR.add(ImageIO.read(new File(path+"s_mario_run"+i+"_R.png")));
            }

            // Fungus enemy
            for(int i = 1; i <= 3; i++) {
                fungus.add(ImageIO.read(new File(path+"fungus"+i+".png")));
            }

            // Flower enemy
            for(int i = 1; i <= 2; i++) {
                flower.add(ImageIO.read(new File(path+"flower"+i+".png")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } // finally {...}
    }
}