/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioTactileMap;

import java.io.File;
import processing.core.*;
import processing.data.*;

/**
 * Reads and writes all ATM model data from/to XML file
 *
 * @author Administrator
 */
public class DataAccessController extends PApplet{

    File file;
    String fileName = null;
    XML xml;
    XML[] soundzones;
    XML[] imageElement;
    String imageFileName = null;
    PImage bg;

    DataAccessController() {

    }
    

    void loadFile(File f_) {
        file = f_;
        fileName = file.getAbsolutePath();
        System.out.println("DAO Opening: " + fileName); 
        xml = loadXML(fileName);
        //parse map image data
        imageElement = xml.getChildren("mapImage");
        imageFileName = imageElement[0].getString("filename");
        System.out.println("Image file from xml: " + imageFileName);

      //parse soundzone data
      soundzones = xml.getChildren("soundzone");
      System.out.println("There are " + soundzones.length + " children with tag 'soundzone'");
        //sounds = new SoundZone[soundzones.length];
//        for (int i = 0; i < soundzones.length; i++) {
//            System.out.println("soundzone #" + i);
//
//            int size = soundzones[i].getInt("size");
//            System.out.println("\t size: " + size);
//
//            int xPos = soundzones[i].getInt("xPos");
//            int yPos = soundzones[i].getInt("yPos");
//            System.out.println("\t pos: " + xPos + ", " + yPos);
//            System.out.println();
//            //sounds[i] = new SoundZone(i, new PVector(xPos, yPos), size);
//        }
    }

    void getModel() {

    }

}
