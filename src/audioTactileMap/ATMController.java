/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template fileName, choose Tools | Templates
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
    boolean isInEditMode = false, isMovingZone;
    int soundZoneToMove = -1;
    File fileName;

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

        fileName = f_;
        System.out.println("Controller Opening: " + fileName.getName());
        model.loadFile(fileName);
        view.updateMap(); //tells the view to update the ATM based on data in model
        //TODO: use a listener to update model AFTER the data has been loaded from XML fileName.
        
    }

    void saveFile() {

        System.out.println("Controller Save");
        try {
            model.saveFile();
        } catch (IOException ex) {
            Logger.getLogger(ATMController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void saveFileAs(File f_) {
        File file = f_;
        System.out.println("Controller Save As: " + file.getName());
        try {
            model.saveFileAs(file);
        } catch (IOException ex) {
            Logger.getLogger(ATMController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //loadFile(file);//After 'Save As' open new saved map

    }
    
    public String getFileName(){
        return fileName.getName();
    }

    public String getImagePath() {
        return model.getImageFilePath();
    }

    public ArrayList<SoundZone> getSoundZones() {
        return model.getSoundZones();
    }

    public ArrayList<SegmentedZone> getSegmentedZones() {
        return model.getSegmentedZones();
    }

    public boolean isMapLoaded() {
        return model.isModelLoaded;
    }

    public boolean isInEditMode() {
        return isInEditMode;
    }

    void setEditMode(boolean b_) {
        isInEditMode = b_;
    }

    void setIsMovingZone(boolean b_) {
        isMovingZone = b_;
    }

    public void resizeZone(ArrayList z_, int i_, int act_) {

//        if (act_ == 2) {
//            //start resize Soundzone     
//            println("start resize sound zone #" + i_);
//        }
//        else if (act_ == 3) {
//            //end move Soundzone     
//            println("end resize sound zone #" + i_);
//        }
    }

    void startMoveZone(ArrayList z_, int i_) {
        println("start move sound zone #" + i_);
        isMovingZone = true;
        soundZoneToMove = i_;
    }

    void endMoveZone(PVector endpt_) {
        println("end move sound zone #" + soundZoneToMove);
        model.getSoundZones().get(soundZoneToMove).setZoneLocation(endpt_);
        isMovingZone = false;
        soundZoneToMove = -1;

    }

    public void selectZone(ArrayList z_, int zi_, int act_) {
        if (!isInEditMode) {
            playZoneSound(z_, zi_, act_);
        }
    }

    public void playZoneSound(ArrayList z_, int i_, int st_) {
        //Check if a Zone's sound is playing, pause, rewind and play new sound as required
        ArrayList<Zone> z = z_;
        int index = i_;
        int soundType = st_;
        int playingID = -1;
        boolean playing = false;
        Zone mszStop, mszPlay;

        //find the index of sound currently playing
        for (int i = 0; i < z.size(); i += 1) {
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
            } else {
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
