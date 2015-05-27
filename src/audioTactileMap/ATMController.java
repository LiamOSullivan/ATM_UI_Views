/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioTactileMap;

/**
 *
 * @author Administrator
 */
public class ATMController {
    
    ATMController(ATMModel m, ATMView v){
        
    
    }
    
    void init(){
        
    
    }
    
    void getMap(String path_){
       String p = path_;
       DataAccessController da = new DataAccessController();
       da.loadXML(p);
       //m.setModel(da.getModel());
       
       //TODO: use a listener to update model AFTER the data has been loaded from XML file.
         
     }
    
}
