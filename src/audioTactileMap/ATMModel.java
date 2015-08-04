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
    XML[] zoneElement; //nested in 'segmentedZones'
    XML[] settingsElement;
    XML[] ipElement;
    XML[] pathElement;//default directory path (e.g. in "My Documents");

    String imageFileName = "No image loaded", imageFilePath = "null";
    String mapName = "No map loaded";
    //PImage mapDisplayImg; //version of map image used for display in GUI
    //String mapDisplayPath;//, mapProcess, mapInfo, audioInfo; //Paths to data files
    PImage bg;
    //TODO: use accessors instead
    public ArrayList<SoundZone> soundZones = new ArrayList();
    public ArrayList<SegmentedZone> segmentedZones = new ArrayList();

    String localIPString = "null";
    String defaultDir = "null";
    String envSoundDir = "null"; //path to audio files
    String spokenSoundDir = "null"; //path to audio files
    String selfSoundDir = "null";
    String imageDir = "null";

    boolean isModelLoaded = false, editMode = false;
    
    //Construct with empty map
    ATMModel() {

    }

    ATMModel(String mapName_) {
        mapName = mapName_;
    }
//TODO: catch all file load/ NPE errors

    public File getFile() {
        return file;
    }

    void loadFile(File f_) {
        file = f_;
        openFilePath = file.getAbsolutePath();
        openFileDir = openFilePath.substring(0, openFilePath.lastIndexOf(File.separator));
        System.out.println("Model is opening file: " + openFilePath);
        parseXMLFile(openFilePath);
        isModelLoaded = true;
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
                for (int i = 0; i < soundZoneElement.length; i++) {
                    System.out.print("soundzone #" + i);
                    int size = soundZoneElement[i].getInt("size");
                    System.out.print("\t size: " + size);
                    int xPos = soundZoneElement[i].getInt("xPos");
                    int yPos = soundZoneElement[i].getInt("yPos");
                    String f1 = soundZoneElement[i].getString("envfile");
                    System.out.print("\t pos: " + xPos + ", " + yPos + "\t uses env file '" + f1 + "' ");
                    String envSP = "" + openFileDir + envSoundDir + f1;
                    String f2 = soundZoneElement[i].getString("selffile");
                    System.out.println("\t uses self file '" + f2 + "' ");
                    String selfSP = "" + openFileDir + selfSoundDir + f2;
                    System.out.println("Full path: " + envSP);
                    soundZones.add(new SoundZone(this, i, new PVector(xPos, yPos), size, envSP, selfSP));

                }
            }
            //get Zones data (buildings, areas) and instantiate SegmentedZone objects in array
            zonesElement = xml.getChildren("zones");
            println("There are " + zonesElement.length + " children with tag 'zones'");
            if (zonesElement.length > 0) {
                zoneElement = zonesElement[0].getChildren("zone");
                println("There are " + zoneElement.length + " children with tag 'zone'");
                for (int i = 0; i < zoneElement.length; i++) {
                    System.out.print("zone #" + i);
                    int label = zoneElement[i].getInt("label");
                    System.out.print("\t expected label: " + label);
                    String name = zoneElement[i].getString("name");
                    System.out.println("\t name: " + name);
                    String info = zoneElement[i].getString("info");
                    System.out.println("Zone info: " + info);
                           

                    String f1 = zoneElement[i].getString("nameaudiofile");
                    String nameSP = "" + openFileDir + spokenSoundDir + "//name//" + f1;
                    System.out.println("This segmented zone has an associated name audio file: " + nameSP);
                    String f2 = zoneElement[i].getString("infoaudiofile");
                    String infoSP = "" + openFileDir + spokenSoundDir + "//info//" + f2;
                    System.out.println("This segmented zone has an associated info audio file: " + infoSP);
                    segmentedZones.add(new SegmentedZone(this, i, label, name, info, nameSP, infoSP));
                    
//                    PVector pv = new PVector(zoneElement[i].getFloat("centroidX"), zoneElement[i].getFloat("centroidX"));
//                    segmentedZones.get(i).setCentroid(pv);
//                    System.out.println("Segmented zone has centroid: " + segmentedZones.get(i).getCentroid());
                }
                //xml.close();
                System.out.println("***");

            }
        }
    }

    void saveFile() throws IOException {
        
        System.out.println("Save to: " + openFilePath);
        writeXMLFile(openFilePath); //write changes to the current XML file
    }

    void saveFileAs(File f_) throws IOException {
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
                new File(saveFilePath + "//images").mkdirs();
                new File(saveFilePath + "//sounds//spoken//name").mkdirs();
                new File(saveFilePath + "//sounds//spoken//info").mkdirs();
                new File(saveFilePath + "//sounds//environment").mkdirs();
                new File(saveFilePath + "//sounds//self-produced").mkdirs();
                new File(saveFilePath + "//sounds//spoken").mkdirs();

                //if a map hasn't been opened already, use the default resources
                if (!isModelLoaded) {
                    loadFile(new File(defaultXMLPath));
                }
                copyAsset(imageFilePath, saveFilePath + "//images//" + imageFileName);
                //copy all assets associated with SoundZones
                for (int i = 0; i < soundZones.size(); i += 1) {
                    ArrayList<File> s = soundZones.get(i).getSoundFiles();
                    //TODO: use enum to look up sound types, rather than hard coding in order
                    if (s.size() > 0) {
                        copyAsset(s.get(0).getAbsolutePath(), saveFilePath + "//sounds//environment//" + s.get(0).getName());
                    }
                    if (s.size() > 1) {
                        copyAsset(s.get(1).getAbsolutePath(), saveFilePath + "//sounds//self-produced//" + s.get(1).getName());
                    }
                }
                //copy all assets associated with SegmentedZones
                for (int i = 0; i < segmentedZones.size(); i += 1) {
                    ArrayList<File> s = segmentedZones.get(i).getSoundFiles();
                    //TODO: use enum to look up sound types, rather than hard coding in order
                    if (s.size() > 0) {
                        copyAsset(s.get(0).getAbsolutePath(), saveFilePath + "//sounds//spoken//name//" + s.get(0).getName());
                    }
                    if (s.size() > 1) {
                        copyAsset(s.get(1).getAbsolutePath(), saveFilePath + "//sounds//spoken//info//" + s.get(1).getName());
                    }
                }

                //copyAsset(imageFilePath, saveFilePath + "//sounds//spoken//" + imageFileName);
                String newFilePath = saveFilePath + "//" + file.getName() + ".xml";
                System.out.println("Creating file: " + newFilePath);
                writeXMLFile(newFilePath);
                
            } else {
                System.out.println("Could not create path: " + file.getPath());
            }
        } else {
            System.out.println("Path exists: " + file.getPath());
        }

    }

    //record any changes to model in XML file
    XML updateXML(String fileToUpdate_) {
        XML updatedFile = loadXML(fileToUpdate_);
        if (updatedFile.hasChildren()) {
            //get soundZones data and instantiate SoundZone objects in array
            XML[] szse = updatedFile.getChildren("soundzones");
            //println("Updating children with tag 'soundzones'");
            if (szse.length > 0) {
                XML[] sze = szse[0].getChildren("soundzone");
                //println("There are " + soundZoneElement.length + " children with tag 'soundzone'");
                for (int i = 0; i < sze.length; i++) {
                    System.out.print("updating soundzone #" + i);
                    int s = soundZones.get(i).getZoneSize();
                    sze[i].setInt("size", s);
                    //System.out.print("\t with size: " + s);
                    float xPos = soundZones.get(i).getZonePosition().x;
                    sze[i].setInt("xPos", (int) xPos);
                    float yPos = soundZones.get(i).getZonePosition().y;
                    sze[i].setInt("yPos", (int) yPos);
                }
            }
        }

        return updatedFile;
    }

    void writeXMLFile(String npf_) {
        String newFileP = npf_;
        //BufferedWriter out=o_;
        if (!isModelLoaded && defaultXMLPath != null) {
            XML newXML = loadXML(defaultXMLPath);
            if (newXML != null) {
                println("Saving default xml file to " + newFileP);
                saveXML(newXML, newFileP);
            }
        } else if (isModelLoaded) {
            println("Saving xml file to " + newFileP);
            XML newXML = updateXML(openFilePath);
            saveXML(newXML, openFilePath);
        }
    }

    void copyAsset(String s_, String d_) {
        System.out.println("Copy from " + s_);
        System.out.println(" to " + d_);
        File src = new File(s_);
        File target = new File(d_);
        Path srcP = src.toPath();
        Path targetP = src.toPath();
        try {
            Files.copy(src.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {
            Logger.getLogger(ATMModel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    String getImageFilePath() {
        return imageFilePath;
    }

    public ArrayList<SoundZone> getSoundZones() {
        return soundZones;
    }

    public ArrayList<SegmentedZone> getSegmentedZones() {
        return segmentedZones;
    }

}
