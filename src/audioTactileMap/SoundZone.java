package audioTactileMap;

/**
 *
 * @author Administrator
 *
 * A SoundZone is a subclass of Zone which usually has an associated set of
 * sound files but no additional (textual) information. It is found using
 * boundary checking from its coordinate on the map and is usually circular (so
 * has a size).
 * 
*/
import processing.core.*;
import processing.core.PApplet;
import ddf.minim.*;
import java.io.File;

class SoundZone extends Zone {

    PVector loc;
    float size;
    
    SoundZone(PApplet p_, int i_, PVector pv_) {
        this(p_, i_, pv_, 20.0F, "none", "none"); //supply default size and placeholder String for soundFiles

    }

    SoundZone(PApplet p_, int i_, PVector pv_, float s_) {
        this(p_, i_, pv_, s_, "none", "none");
    }

    SoundZone(PApplet p_, int i_, PVector pv_, float s_, String f1_, String f2_) {
        super(p_, i_, f1_, f2_);
        loc = pv_;
        size = s_;
        
    }

    void setZoneLocation(PVector p_) {
        loc = p_;
    }

    void setZoneSize(float size_) {
        size = size_;
    }

    public PVector getZonePosition() {
        return loc;
    }

    public float getZoneSize() {
        return size;
    }
    
    public boolean hasEnvAudioFile() {
        return (sounds.get(0) != null);

    }
    
    public boolean hasSelfAudioFile() {
        return (sounds.get(1) != null);

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

    
}
