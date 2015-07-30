/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioTactileMap;

import java.io.File;
import processing.core.*;

/**
 *
 * @author Administrator
 */
public class ATMController extends PApplet {

    ATMView view;
    ATMModel model;
    

    ATMController(ATMModel m_, ATMView v_) {
        model = m_;
        view = v_;
    }

//    @Override
//    void setup(){
//        
//    
//    }
//    
//    void draw(){
//        
//    
//    }
    void loadFile(File f_) {

        File file = f_;
        System.out.println("Controller Opening: " + file.getName());
        model.loadFile(file);
        view.updateMap(); //tells the view to update the ATM based on data in model

        //model.set(da.getModel()); //get the data loaded by the DAC and put in model       
        //TODO: use a listener to update model AFTER the data has been loaded from XML file.
    }

    public String getImagePath() {
        return model.getImageFilePath();
    }

    public SoundZone[] getSoundZones() {
        return model.getSoundZones();
            }

    public SegmentedZone[] getSegmentedZones() {
        return model.getSegmentedZones();
    }
    
    public void selectZone(SoundZone [] z_, int zi_, int act_){
       playSoundZone(0, zi_, act_);
   }
    
    public void selectZone(SegmentedZone [] z_, int zi_, int act_){
        playSoundZone(1, zi_, act_);        
    }

    public void playSoundZone(int zt_, int i_, int st_) {
        //Check if a SoundZone is playing, pause, rewind and play new sound
        int zoneType = zt_;
        Zone [] msz;
        if(zoneType == 0){
        msz = model.getSoundZones();
        }
        else {
        msz = model.getSegmentedZones();
        }
        int index = i_;
        int soundType = st_;
        
        int playingID = -1;
        boolean playing = false;
        Zone mszStop, mszPlay;
        //find the index of sound currently playing
        for (int i = 0; i < model.getSoundZones().length; i += 1) {
            System.out.println("check if playing #" + i);
            if (msz[i].checkIfPlaying(soundType)) {
                System.out.println("sound playing is #" + i);
                playingID = i;
                playing = true;
                break;
            } else {
                System.out.println("file # " + i + " isn't playing");
            }
        }
        //if trying to play a different sound, pause the current playing sound
        if (playing) {
            if (index != playingID) {
                System.out.println("Stopping sound: " + playingID);
                mszStop = msz[playingID];
                mszStop.pauseSound(soundType);
                mszStop.rewindSound(soundType);
                System.out.println("And playing sound: " + index);
                mszPlay = msz[index];
                mszPlay.playSound(soundType); //play the new sound
                mszPlay.rewindSound(soundType);
            }
            else{
            //if selecting an already playing sound or if clicking in space, stop currently playing sound    
                System.out.println("Stopping sound: " + playingID);
                mszStop = msz[playingID];
                mszStop.pauseSound(soundType);
                mszStop.rewindSound(soundType);
            }
        } else {
            //if nothihng is playing, just play the selected sound
            System.out.println("SoundManager trying to play # " + index);
            msz[index].playSound(soundType); //play the sound
            msz[index].rewindSound(soundType);
        }

    }

}
