package main;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;;

public class Music {
    public Music() throws FileNotFoundException, JavaLayerException {
        String str = System.getProperty("user.dir")+"/src/music/music.wav"; 
        BufferedInputStream music = new BufferedInputStream(new FileInputStream(str));
        Player player = new Player(music);
        player.play();
    }
}
