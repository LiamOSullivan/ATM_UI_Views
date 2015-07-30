/*
 * Reads and writes all ATM model data from/to XML file Has knowledge of the
 * specific structure of the ATM XML file format. 
 */
package audioTactileMap;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.BufferedWriter;
import java.nio.file.StandardCopyOption;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import processing.core.*;
import processing.data.XML;
//TODO: only import what's needed from Processing

/**
 *
 * @author Administrator
 */
public class ATMModel extends PApplet {

    File file;
    String openFilePath = "null", openFileDir = "null";
    //TODO: remove hard-coded default path
    String defaultXMLPath = "C:\\Users\\Administrator\\Documents\\My ATM Files\\default\\default.xml";
    String[] tags = {"soundzones", "soundzone", "mapImage", "settings", "oscaddress"}; //list all tags to be parse from XML here
    //XML Elements to parse from file
    //TODO: remove need to hold these in memory and only store parsed data
    XML xml;
    XML[] imageElement;
    XML[] soundZonesElement;
    XML[] soundZoneElement; //nested in 'soundzones'
    XML[] zonesElement;
    XML[] zoneElement; //nested in 'zones'
    XML[] settingsElement;
    XML[] ipElement;
    XML[] pathElement;//default directory path (e.g. in "My Documents");

    String imageFileName = "No image loaded", imageFilePath = "null";
    String mapName = "No map loaded";
    //PImage mapDisplayImg; //version of map image used for display in GUI
    //String mapDisplayPath;//, mapProcess, mapInfo, audioInfo; //Paths to data files
    PImage bg;
    //TODO: use accessors instead
    public SoundZone[] soundZones;
    public SegmentedZone[] zones;

    String localIPString = "null";
    String defaultDir = "null";
    String envSoundDir = "null"; //path to audio files
    String spokenSoundDir = "null"; //path to audio files
    String selfSoundDir = "null";
    String imageDir = "null";
    
    boolean isModelLoaded = false;

    //ImageProcessor imgProcessor; //class to process and segment the map image

//  SoundZone [] soundZones, impulseSoundZones; 
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
    ATMModel() {

    }

    ATMModel(String mapName_) {
        mapName = mapName_;
    }
//TODO: catch all file load/ NPE errors

    void loadFile(File f_) {
        file = f_;
        openFilePath = file.getAbsolutePath();
        openFileDir = openFilePath.substring(0, openFilePath.lastIndexOf(File.separator));
        System.out.println("Model is opening file: " + openFilePath);
        parseXMLFile(openFilePath);
    }

    void parseXMLFile(String f_) {
        String filepath_ = f_;
        xml = loadXML(filepath_);
        //parse map data...
        //used specifc tags in code, could generalise but would need to create recursive/ multilevel
        //accesss to elements and create named arrays/ hashmaps
        //get top-level elements
        println("Top Level Elements in xml file: ");
        if (xml.hasChildren()) {
            //get settings
            settingsElement = xml.getChildren("settings");
            println("Found " + settingsElement.length + " 'settings' element");
            if (settingsElement.length > 0) {
                ipElement = settingsElement[0].getChildren("oscaddress");
                localIPString = ipElement[0].getString("ip");
                println("Local IP address is " + localIPString);
                //TODO: get IP address of network adapter, not from xml
                //get path data

                pathElement = settingsElement[0].getChildren("path");
                defaultDir = pathElement[0].getString("defaultdir");
                envSoundDir = pathElement[0].getString("envsounddir");
                spokenSoundDir = pathElement[0].getString("spokensounddir");
                selfSoundDir = pathElement[0].getString("selfsounddir");
                imageDir = pathElement[0].getString("imagedir");
                println("Default directory is " + defaultDir);
                println("Environment Sound directory is " + envSoundDir);
                println("Self-produced Sound directory is " + selfSoundDir);
                println("Spoken Information Sound directory is " + spokenSoundDir);
                println("Image directory is " + imageDir);
            }
            //get image data
            imageElement = xml.getChildren("mapImage");
            if (imageElement.length > 0) {
                imageFileName = imageElement[0].getString("filename");
                System.out.println("Image file from xml: " + imageFileName);
                imageFilePath = openFileDir + imageDir + imageFileName;
            }

            //get soundZones data and instantiate SoundZone objects in array
            soundZonesElement = xml.getChildren("soundzones");
            println("There are " + soundZonesElement.length + " children with tag 'soundzones'");
            if (soundZonesElement.length > 0) {
                soundZoneElement = soundZonesElement[0].getChildren("soundzone");
                println("There are " + soundZoneElement.length + " children with tag 'soundzone'");
                soundZones = new SoundZone[soundZoneElement.length];
                for (int i = 0; i < soundZones.length; i++) {
                    System.out.print("soundzone #" + i);
                    int size = soundZoneElement[i].getInt("size");
                    System.out.print("\t size: " + size);
                    int xPos = soundZoneElement[i].getInt("xPos");
                    int yPos = soundZoneElement[i].getInt("yPos");
                    String f = soundZoneElement[i].getString("file");
                    System.out.println("\t pos: " + xPos + ", " + yPos + "\t uses file '" + f + "' ");
                    String envSP = "" + openFileDir + envSoundDir + f;
                    String selfSP = "" + openFileDir + selfSoundDir + f;
                    System.out.println("Full path: " + envSP);
                    soundZones[i] = new SoundZone(this, i, new PVector(xPos, yPos), size, envSP, selfSP);

                }
            }
            //get Zones data (buildings, areas) and instantiate SegmentedZone objects in array
            zonesElement = xml.getChildren("zones");
            println("There are " + zonesElement.length + " children with tag 'zones'");
            if (zonesElement.length > 0) {
                zoneElement = zonesElement[0].getChildren("zone");
                println("There are " + zoneElement.length + " children with tag 'zone'");
                zones = new SegmentedZone[zoneElement.length];
                for (int i = 0; i < zones.length; i++) {
                    System.out.print("zone #" + i);
                    int label = zoneElement[i].getInt("label");
                    System.out.print("\t expected label: " + label);
                    String name = zoneElement[i].getString("name");
                    System.out.println("\t name: " + name);
                    String info = zoneElement[i].getString("info");
                    System.out.println("Zone info: " + info);
                    String f = zoneElement[i].getString("audiofile");
                    if (f.equals("none")) {
                        System.out.println("This zone has no associated audio file");
                        //TODO: Instantiate when no audio file alternative (subsequent use of TTS)
                        //Zone(int i_, int l_, String n_, String info_)
                        zones[i] = new SegmentedZone(this, i, label, name, info);
                    } else {
                        String zoneSP = "" + openFileDir + spokenSoundDir + f;
                        System.out.println("This zone has an associated audio file: " + zoneSP);
                        //Zone(int i_, int l_, String n_, String info_, String audiofile)
                        zones[i] = new SegmentedZone(this, i, label, name, info, zoneSP);
                    }
                    System.out.println("***");

                }
            }
        }
    }

    void saveFile(File f_) throws IOException {
        System.out.println("Saving file to Model");
        file = f_;
        String saveFilePath = file.getAbsolutePath();
        String saveFileDir = saveFilePath.substring(0, saveFilePath.lastIndexOf(File.separator));
        System.out.println("Model is saving file: " + saveFilePath);
        BufferedWriter output = null;
        if (!file.isDirectory()) {
            boolean success = file.mkdirs();
            if (success) {
                System.out.println("Created path: " + saveFilePath);
                //create sub-directories
                new File(saveFilePath + "//sounds//").mkdirs();
                new File(saveFilePath + "//images//").mkdirs();
                new File(saveFilePath + "//sounds//environment").mkdirs();
                new File(saveFilePath + "//sounds//self-produced").mkdirs();
                new File(saveFilePath + "//sounds//spoken").mkdirs();
                
                //if a map hasn't been opened already, use the default resources
                if(!isModelLoaded){
                    loadFile(new File(defaultXMLPath));                   
                }
                copyAsset(imageFilePath, saveFilePath + "//images//" + imageFileName);
                
//                String newFilePath = saveFilePath + "//" + file.getName();
//                System.out.println("Creating file: " + newFilePath);
//                writeXMLFile(newFilePath);
//                try {
//                    //output = new BufferedWriter(
//                     //       new FileWriter(saveFilePath + "//" + file.getName() + ".xml"));
//                    writeXMLFile(output);
//                    System.out.println("Created file: " + saveFilePath + "//" + file.getName() + ".txt");
//                } catch (IOException e) {
//                    System.out.println("Error creating file: ");
//                    e.printStackTrace();
//                } finally {
//                    if (output != null) {
//                        System.out.println("Closing file ");
//                        output.close();
//                    }
//                }

            } else {
                System.out.println("Could not create path: " + file.getPath());
            }
        } else {
            System.out.println("Path exists: " + file.getPath());
        }

    }

    void writeXMLFile(String npf_) {
        String newFileP = npf_;
        //BufferedWriter out=o_;
        if (defaultXMLPath != null) {
            XML newXML = loadXML(defaultXMLPath);
            if (newXML != null) {
                println("Writing xml file");
                saveXML(newXML, newFileP);
            }
        }
    }

    void copyAsset(String s_, String d_) {
        System.out.print("Copy from " + s_);
        System.out.println(" to " + d_);
        File src = new File(s_);
        File target = new File(d_);
        Path srcP = src.toPath();
        Path targetP = src.toPath();
        try {
            Files.copy(src.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(ATMModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    String getImageFilePath() {
        return imageFilePath;
    }

    public SoundZone[] getSoundZones() {
        return soundZones;
    }

    public SegmentedZone[] getSegmentedZones() {
        return zones;
    }

}
