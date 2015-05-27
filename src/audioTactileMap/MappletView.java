/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioTactileMap;

import processing.core.*;
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
    
    int w,h;

    MappletView() {

    }

    @Override
    public void setup() {
        size(w, h);

    }

    @Override
    public void draw() {

        //check if the background image is already loaded
        //if not, the background is painted white
        if (bgImg == null) {
            background(255);
        } else {
            image(bgImg, 0, 0, width, height);
        }

    }

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
     * this method is called by the ActionListener assigned to the JButton
     * buttonLoad in Application
     */

    public void loadMapImage(File selectedFile) {
        bgImg = loadImage(selectedFile.getAbsolutePath());
    }

    //    
//    public void getImageFile(){
//            try{
//            bi = ImageIO.read(new File("C:\\OneDrive\\Projects\\Leicester\\Phase_2\\Code\\NetbeansGUI\\ATMDisplayGUI\\ATM_UI_Views\\src\\resources\\CampusMap_A4_Print.png"));
//            System.out.println("Image loaded: "+"CampusMap_A4_Print.png");
//            }
//            catch(IOException e){
//            System.out.println("Image file not found");
//            }
//    }
    
}
