/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioTactileMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import processing.core.*;

/**
 *
 * @author Administrator
 */
public class ATMController extends PApplet {

    ATMView view;
    ATMModel model;
    boolean MapEditMode = false;
    

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
        //view.setVisible(true);

        //model.set(da.getModel()); //get the data loaded by the DAC and put in model       
        //TODO: use a listener to update model AFTER the data has been loaded from XML file.
    }
    
    void saveFile(File f_){
        File file = f_;
        System.out.println("Controller Saving: " + file.getName());
        try {
            model.saveFile(file);
        } catch (IOException ex) {
            Logger.getLogger(ATMController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public String getImagePath() {
        return model.getImageFilePath();
    }

    public ArrayList <SoundZone> getSoundZones() {
        return model.getSoundZones();
            }

    public ArrayList <SegmentedZone> getSegmentedZones() {
        return model.getSegmentedZones();
    }
    
    public void selectZone(ArrayList z_, int zi_, int act_){
       playZoneSound(z_, zi_, act_);
       
   }
    
    public void playZoneSound(ArrayList z_, int i_, int st_) {
        //Check if a SoundZone is playing, pause, rewind and play new sound
        ArrayList<Zone> z =z_;
//        ArrayList <SoundZone> soundZ;
//        ArrayList <SegmentedZone> segZ;
        int index = i_;
        int soundType = st_;
        int playingID = -1;
        boolean playing = false;
        Zone mszStop, mszPlay;
//        if(z.get(0) instanceof SoundZone){
//            for(int i=0; i<z.size();i+=1){
//            soundZ.add((SoundZone) z.get(i));
//            }
//        
//        }
//        else{
//            for(int i=0; i<z.size();i+=1){
//            segZ.add((SegmentedZone) z.get(i));
//            }
//        }
        
        //find the index of sound currently playing
        for (int i = 0; i < model.getSoundZones().size(); i += 1) {
            System.out.println("check if playing #" + i);
            if (z.get(i).checkIfPlaying(soundType)) {
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
                mszStop = z.get(playingID);
                mszStop.pauseSound(soundType);
                mszStop.rewindSound(soundType);
                System.out.println("And playing sound: " + index);
                mszPlay = z.get(index);
                mszPlay.playSound(soundType); //play the new sound
                mszPlay.rewindSound(soundType);
            }
            else{
            //if selecting an already playing sound or if clicking in space, stop currently playing sound    
                System.out.println("Stopping sound: " + playingID);
                mszStop = z.get(playingID);
                mszStop.pauseSound(soundType);
                mszStop.rewindSound(soundType);
            }
        } else {
            //if nothihng is playing, just play the selected sound
            System.out.println("SoundManager trying to play # " + index);
            z.get(index).playSound(soundType); //play the sound
            z.get(index).rewindSound(soundType);
        }

    }

}
