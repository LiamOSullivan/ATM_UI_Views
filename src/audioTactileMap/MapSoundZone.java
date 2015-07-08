package audioTactileMap;

/**
 *
 * @author Administrator
 */
/* A circular zone on a map with associated index to a sound in a sound manager. 
 Has boundary-chceking method to see if a point falls within. 
 */
//TODO: Make subclass of Zone, rename 
import processing.core.*;
import processing.core.PApplet;

import ddf.minim.*;
import processing.core.PConstants;

class MapSoundZone extends PApplet{

    //PApplet parent;
    int index;
    PVector loc;
    float size = 20;
    String filePath = "null";
    Minim minim; //may need to change this to ref in parent
    AudioPlayer ap;

    MapSoundZone() {
        index = -1; //no index at instantiation
        loc = new PVector(-1, -1); //no location
    }

    MapSoundZone(int i_) {
        index = i_;
        loc = new PVector(-1, -1); //no location
    }

    MapSoundZone(int i_, PVector p_) {
        index = i_;
        loc = p_;
    }

    MapSoundZone(int i_, PVector p_, int s_) {
        index = i_;
        loc = p_;
        size = s_;
    }

    MapSoundZone(int i_, PVector p_, int s_, String f_) {
        //parent = par_;
        index = i_;
        loc = p_;
        size = s_;
        filePath = f_;
        System.out.println("SoundZone #" + index + " loading file: " + filePath);
        minim = new Minim(this);
        ap = minim.loadFile(filePath);
    }

    void setIndex(int i_) {
        index = i_;
    }

    void setLocation(PVector p_) {
        loc = p_;
    }

    void setSize(float size_) {
        size = size_;
    }

    void setSoundFile(String f_) {
        filePath = f_;
        //ap = minim.loadFile(filePath);

    }

    int getIndex() {
        return index;
    }


    public PVector getZonePosition() {
        return loc;
    }

 
    public float getZoneSize() {
        return size;
    }

    boolean checkIfOver(int xIn_, int yIn_) {
        int xIn = (int) xIn_;
        int yIn = (int) yIn_;

        //(x - a)^2 + (y - b)^2 = R^2 -eqn to check if on a circle
        //System.out.println("currentX: "+x+" currentY: "+y);
        System.out.println("xIn: " + xIn + " yIn: " + yIn + " size" + this.size);

        float xOn = PApplet.sq(xIn - loc.x);
        float yOn = PApplet.sq(yIn - loc.y);
        float rOn = PApplet.sq(this.size);
//    System.out.println("xOn + yOn = rOn : "+xOn+" | "+yOn+" | "+rOn);
        if ((xOn + yOn) < rOn) {
            System.out.println("***********Over Map Sound! **************");
            return true;
        } else {
            return false;
        }
    }

    //Sound Management
    public boolean checkIfPlaying() {
        return ap.isPlaying();

    }

    void playSound() {
        ap.play();

    }

    void pauseSound() {
        ap.pause();

    }

    void rewindSound() {
        ap.rewind();

    }
}
