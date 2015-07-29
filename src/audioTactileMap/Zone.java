/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioTactileMap;

/**
 *
 * @author Administrator
 */
import processing.core.*;
import ddf.minim.*;
import java.io.File;

class Zone {

    PApplet parent; //parent class is model and extends PApplet
    String sound1File, sound2File;
    Minim minim;
    AudioPlayer sound1, sound2;
        
    int zoneID;

    //Constructor for zones containing building or location information.
    Zone(PApplet p_, int i_, String s1_, String s2_) {
        parent = p_;
        zoneID = i_;
        sound1File  = s1_;
        sound2File  = s2_;
        minim = new Minim(parent);
        
         System.out.println("Zone #" + zoneID + " checking for audio file...");
//        if ("none".equals(sound1File)) {
//            System.out.println("No associated audio file found for building name. ");
//
//        } else {
//            System.out.println("Zone #" + zoneID + " loading audio file: " + sound1File);
//            minim = new Minim(parent);
//            sound1 = minim.loadFile(sound1File);
//        }
//        if ("none".equals(sound2File)) {
//            
//
//        } else {
//            System.out.println("Zone #" + zoneID + " loading audio file: " + sound2File);
//            sound2 = minim.loadFile(sound2File);
//        }
        
        if(new File(sound1File).exists()){
        sound1 = minim.loadFile(sound1File);
        System.out.println("Zone loaded sound file #1");
        }
        else{
            System.out.println("Zone could not find file 1");
        }
        if(new File(sound2File).exists()){
        sound2 = minim.loadFile(sound2File);
        System.out.println("Zone loaded sound file #2");
        }
        else{
            System.out.println("Zone could not find file 2");
         
        }
    }

    void setId(int id_) {
        zoneID = id_;
    }

    int getId() {
        return zoneID;
    }
    
    //Sound Management
    public boolean checkIfPlaying(int t_) {
        if ((t_==0) && (sound1!=null)){
            return sound1.isPlaying();
        } else if ((t_ == 1) && (sound2!=null)) {
            return sound2.isPlaying();
        } else {
            return false;
        }

    }

    void playSound(int t_) {
        if ((t_==0) && (sound1!=null)) {
        sound1.play();
        }
        else if ((t_ == 1) && (sound2!=null)) {
          sound2.play();
        }
    }

    void pauseSound(int t_) {
          if ((t_==0) && (sound1!=null)) {
        sound1.pause();
        }
        else if ((t_ == 1) && (sound2!=null)) {
          sound2.pause();
        }

    }

    void rewindSound(int t_) {
          if (t_ == 0) {
        sound1.rewind();
        }
        else if ((t_ == 1) && (sound2!=null)) {
          sound2.rewind();
        }

    }


}
