/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioTactileMap;

import processing.core.*;
//TODO: only import what's needed from Processing
/**
 *
 * @author Administrator
 */
public class ATMModel {
    
    String mapName = "no map";
    PImage displayImg; //version of map image used for display in GUI
    String mapDisplay, mapProcess, mapInfo, audioInfo; //Paths to data files
    ImageProcessor imgProcessor; //class to process and segment the map image
//  String audioPath; //path to audio files
//  MapSoundZone [] envSoundZones, impulseSoundZones; 
//  SoundManager envSoundManager, impulseSoundManager; //Stores audio files of different types
//  AudioPlayer [] ttsReplace;// ***TODO: create SoundManager for this
//  float MapSoundZoneSize = 0;
//  boolean showMapSoundZones = false, showMapSoundZoneIndexes = false;
//  boolean useEnvSounds = true, useImpulseSounds = true; //use background & self-produced sounds with map
//  //PVector fingerPosition;
//  int fingerCount = 0;
//  boolean calibrating = false, calibrated = false, calibratePosition =false, calibrateSize = false;
//  int CALIBRATION_TIME = 15000; //time in millis that position, width and height are each set.
//  int calStart = 0; 
//  AudioPlayer routeStart, routeEnd;
//  AudioPlayer [] routeSteps;
//  int routeStepNo = 0;
//  boolean routeMode = false;
//
//  boolean drawCircles = true, drawCoords=false;
//  Rulers rulers;
//
//  MapDisplay(PApplet parentSketch_, int w_, int h_, String mapDisplay_, String mapProcess_, 
//  String mapInfo_, String audioInfo_, String audioPath_ ) {
//    parentSketch=parentSketch_;
//    w=w_;
//    h=h_;
//    //paths...
//    mapDisplay = mapDisplay_; //the digital map image file
//    mapProcess = mapProcess_; //image file to be segmented (if different)
//    mapInfo = mapInfo_; //text file with zone labels
//    audioPath = audioPath_; //this will include the sub folder for either 'campus' or 'queens'
//    audioInfo = audioInfo_; //the text file with sound locations
//  }
    
    
    //Construct with empty map
    ATMModel(){        
    
    }
    
    ATMModel(String mapName_){
        mapName=mapName_;
    }
    
    
    
}
