/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioTactileMap;

/**
 *
 * @author Administrator
 * 
 * A SegmentedZone is a subclass of Zone which can have associated information such as name and additional information.
 * It is given an index and/or label for lookup rather than a coordinate on the map.
 * A SegmentedZone can include audio files that may be used to verbalize information or may use a TTS engine.
 */
import processing.core.*;
import ddf.minim.*;
import java.io.File;

class SegmentedZone extends Zone {

    String name, info;
    int zoneLabel;
    
    //Constructor for zones with only a reference to the parent model and an index number
    SegmentedZone(PApplet p_, int i_) {
        this(p_, i_, -1, "none", "none", "none", "none");
    }

    //Constructor for zones containing building or location information.
    SegmentedZone(PApplet p_, int i_, int l_, String n_, String info_) {
        this(p_, i_, l_, n_, info_, "none", "none");
    }
    
    //Constructor for zones containing building or location information and an associated verbalised audio file for zone name.
    SegmentedZone(PApplet p_, int i_, int l_, String n_, String info_, String na_) {
        this(p_, i_, l_, n_, info_, na_, "none");
    }
    
    //Constructor for zones containing building or location information and associated verbalised audio files for zone name and info.
    SegmentedZone(PApplet p_, int i_, int l_, String n_, String info_, String na_, String ia_) {
        super(p_, i_, na_, ia_);
        zoneID = i_;
        zoneLabel = l_;
        name = n_;
        info = info_;        
    }

    public boolean hasNameAudioFile() {
        return (sound1 != null);

    }
    
    public boolean hasInfoAudioFile() {
        return (sound2 != null);

    }

    void setLabel(int l_) {
        zoneLabel = l_;
    }
    
    void setZoneName(String n_) {
        name = n_;
    }

    void setZoneInfo(String i_) {
        info = i_;
    }
    
    int getLabel() {
        return zoneLabel;
    }
    
    String getZoneName() {
        return name;
    }

    String getZoneInfo() {
        return info;
    }
    
    

    

}
