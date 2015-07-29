/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioTactileMap;

import processing.core.*;
import ddf.minim.*;
import blobscanner.*; //Blobscanner library v. 0.1-a by Antonio Molinaro
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * The MappletView is a PApplet used to display the background map image and
 * animation/annotation placed on top. It is embedded in a JPanel on the main
 * ATMView JFrame.
 *
 * @author Administrator
 */
public class MappletView extends PApplet implements ActionListener {

    Minim minim;
    ATMView parent;
    PImage mapImg = null;
    int w, h;
    PFont font;
    MapSoundZone[] msz; //The zones in the map with associated sound files (e.g. recorded environmental sounds).
    Zone [] zones; //Zones on map as segregated blobs extracted from the image.
    boolean isMapLoaded = false;

    ////Segmentation variables...
    ImageProcessor imgProcessor;
    
    boolean showAllContours = false;
    int MIN_BLOB_SIZE = 20; //heuristic for ignoring small blobs
    int maxBlobSize, minBlobSize = MIN_BLOB_SIZE; //adjustable from settings GUI
    int segmentationStartTime; //Used to time blob detection and segmentation

    MappletView(ATMView p_) {
        parent = p_;
    }

    @Override
    public void setup() {
        //System.out.println("Mapplet setup called");
        size(800, 600);
        font = createFont("CourierNewPSMT-48.vlw", 48);
        textFont(font, 24);
        fill(0);

    }

    @Override
    public void draw() {
        if (isMapLoaded) {
            if (mapImg != null) {
                image(mapImg, 0, 0, this.width, this.height);
            }
            drawSoundZones();

        } else {
            //if map not loaded, the background is painted white w/ message
            background(255);
            text("Please choose a map from File > Open Map...", 12, height / 2);
        }
    }

    //TODO: remove action listener if unused
    /**
     * implementation from interface ActionListener method is called from the
     * Application the String being compared is the ActionCommand from the
     * button
     */
    public void actionPerformed(ActionEvent evt) {
        if (evt.getActionCommand().equals("actionPerformed")) {

        } else {
            println("actionPerformed(): can't handle " + evt.getActionCommand());
        }
    }

    /**
     * methods to set the background map image and pass a reference to zones.
     */
    public void setMapLoaded(boolean b_) {
        isMapLoaded = b_;

    }

    public void setMapImage(String f_) {
        //System.out.println("Mapplet trying " + f_);
        mapImg = loadImage(f_);
        size(mapImg.width, mapImg.height);
    }

    void processMapImage() {
        imgProcessor = new ImageProcessor(this, mapImg);
        imgProcessor.process();
    }

    //Loads the array of zones with asscoiated sound files
    public void setSoundZones(MapSoundZone[] m_) {
        msz = m_;
    }
    //Loads the array of zones on map image. May have associated sound file or may not.
    public void setZones(Zone[] z_){
        zones=z_;
    }

    /**
     * real-time drawing methods
     */
    private void drawSoundZones() {
        for (int i = 0; i < msz.length; i += 1) {
            fill(200, 0, 0, 150);
            ellipse(msz[i].getZonePosition().x, msz[i].getZonePosition().y,
                    msz[i].getZoneSize(), msz[i].getZoneSize());
        }
    }

    private void playSound(int i_) {
        //Check if a MapSoundZone is playing, pause, rewind and play new sound
        int index = i_;
        int playingID = -1;
        boolean playing = false;
        //find the index of sound currently playing
        for (int i = 0; i < msz.length; i += 1) {
            System.out.println("check if playing #" + i);
            if (msz[i].checkIfPlaying()) {
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
                msz[playingID].pauseSound();
                msz[playingID].rewindSound();
                System.out.println("And playing sound: " + index);
                msz[index].playSound(); //play the new sound
                msz[index].rewindSound();
            }
        } else {
            System.out.println("SoundManager trying to play # " + index);
            msz[index].playSound(); //play the sound
            msz[index].rewindSound();
        }

    }

    /*
     * mouse listeners and interaction handlers
     */
    @Override
    public void mouseClicked() {
        if (keyPressed && keyCode == SHIFT) {
            println("MouseClicked Action 1");
            action(1, mouseX, mouseY);
        } else if (keyPressed && keyCode == CONTROL) {
            println("MouseClicked Action 2");
            action(2, mouseX, mouseY);
        } else {
            println("MouseClicked Action 0");
            action(0, mouseX, mouseY);
        }

    }

    void action(int n_, int x_, int y_) {
        int actionNo = n_;
        boolean keepChecking = true;
        //first check if over a sound zone

        for (int i = 0; i < msz.length; i += 1) {
            if (msz[i].checkIfOver(x_, y_)) {
                println("Over soundZone #" + msz[i].getIndex());
                playSound(i);
                keepChecking = false;
                break;
            }
        }
        //if not over a sound zone, check if over a labelled building
        if (keepChecking) {
            parent.findZone(x_, y_); //look for a building

        }

    }

}
