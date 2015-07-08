/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioTactileMap;

import processing.core.*;
import ddf.minim.*;
import java.awt.Color;
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

    PImage bgImg = null;
    int w, h;
    PFont font;
    MapSoundZone[] msz;
    boolean isMapLoaded = false;
    Minim minim;

    MappletView() {

    }

    @Override
    public void setup() {
        size(800, 600);
        font = createFont("CourierNewPSMT-48.vlw", 48);
        textFont(font, 24);
        fill(0);
        
    }

    @Override
    public void draw() {
        if (isMapLoaded) {
            if (bgImg != null) {
                image(bgImg, 0, 0, this.width, this.height);
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
        bgImg = loadImage(f_);
    }

    public void setSoundZones(MapSoundZone[] m_) {
        msz = m_;
    }

    /**
     * real-time drawing methods
     */
    private void drawSoundZones() {
        for (int i = 0; i < msz.length; i += 1) {
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
     * mouse listeners
     */
    @Override
    public void mouseClicked() {
        for (int i = 0; i < msz.length; i += 1) {
            if (msz[i].checkIfOver(mouseX, mouseY)) {
                println("Over soundZone #" + msz[i].getIndex());
                playSound(i);
            }
        }

    }
}
