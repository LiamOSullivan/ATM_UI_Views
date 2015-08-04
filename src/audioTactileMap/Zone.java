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
import java.util.*;

class Zone {

    PApplet parent; //parent class is model and extends PApplet
    ArrayList<String> soundFilePaths = new ArrayList();
    ArrayList<File> soundFiles = new ArrayList();
    ArrayList<AudioPlayer> sounds = new ArrayList();
    Minim minim;

    int zoneID;

    //Constructor for zones containing building or location information.
    Zone(PApplet p_, int i_, String s1_, String s2_) {
        parent = p_;
        zoneID = i_;
        soundFilePaths.add(s1_);
        soundFilePaths.add(s2_);
        minim = new Minim(parent);

        System.out.println("Zone #" + zoneID + " checking for audio file...");

        for (int i = 0; i < soundFilePaths.size(); i += 1) {
            if (new File(soundFilePaths.get(i)).exists()) {
                soundFiles.add(new File(soundFilePaths.get(i)));
                sounds.add(minim.loadFile(soundFilePaths.get(i)));
                System.out.println("Zone loaded sound file #" + i+" "+soundFilePaths);
            } else {
                System.out.println("Zone could not find file #" + i);
            }
        }
    }

    void setId(int id_) {
        zoneID = id_;
    }

    int getId() {
        return zoneID;
    }

    ArrayList<String> getSoundFilePaths() {
        return soundFilePaths;

    }

    ArrayList<File> getSoundFiles() {
        return soundFiles;

    }

    void setSoundFile1Path(File f_) {
        System.out.println("Zone #" + zoneID + " Set soundfile 1 to: " + f_.getName());
        soundFilePaths.set(0, f_.toString());
    }

    void setSoundFile2Path(File f_) {
        System.out.println("Zone #" + zoneID + " Set soundfile 2 to: " + f_.getName());
        soundFilePaths.set(1, f_.toString());

    }

    //Sound Management
    public boolean checkIfPlaying(int t_) {
        System.out.println("checking for element #" + t_ + " in arraylist of size " + sounds.size());
        if ((t_) < sounds.size()) {
            return sounds.get(t_).isPlaying();

        } else {
            return false;
        }

    }

    void playSound(int t_) {

        if ((t_) < sounds.size()) {
            sounds.get(t_).play();
        }
    }

    void pauseSound(int t_) {
        if ((t_) < sounds.size()) {
            sounds.get(t_).pause();
        }

    }

    void rewindSound(int t_) {
        if ((t_) < sounds.size()) {
            sounds.get(t_).rewind();
        }

    }

}
