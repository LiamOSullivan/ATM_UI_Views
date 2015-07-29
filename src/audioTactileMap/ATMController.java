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

    public MapSoundZone[] getSoundZones() {
        return model.getSoundZones();

    }

    public Zone[] getZones() {

        return model.getZones();
    }

}
