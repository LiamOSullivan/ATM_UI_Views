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
public class ATMController extends PApplet{
    ATMView view;
    ATMModel model;
    ATMController(ATMModel m_, ATMView v_){
        model=m_;
        view=v_;    
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
    
    void loadFile(File f_){
        
       File file = f_;
       System.out.println("Controller Opening: " + file.getName());
       DataAccessController da = new DataAccessController();
       da.loadFile(file);
       //m.setModel(da.getModel());       
       //TODO: use a listener to update model AFTER the data has been loaded from XML file.
         
     }
    
    private PImage getImage(){
        PImage mi = loadImage("CampusMap_A4_Print.png");
    return mi;
    }
    
    private void setViewImage(PImage i){
        //view.setMapImage(i);    
    }
    
}
