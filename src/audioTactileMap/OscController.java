/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioTactileMap;

import oscP5.*;
import netP5.*;
import java.net.InetAddress;
import processing.core.*;
//import processing.core.PrintWriter;
import processing.core.PConstants;

/**
 *
 * @author Administrator
 */
public class OscController {
       
    //Variables for OSC mode
OscP5 oscP5; 
//int listenPort = 7400;
int listenPort = 3334; //for use with bridge.js/ node server
PImage map;
boolean oscDebug = false, debugToFile = false;
PVector currentP;  //store the current/latest touch position
boolean touchOn = false;
float screenScale = 0.5F;
//PrintWriter debugLog;

int mapChoice=  0;

OscController(){
    

}


void oscEvent(OscMessage mess) {
  /* check if mess has the address pattern we are looking for. */
  oscDebug("Received an OSC message with address pattern: "+mess.addrPattern());
  oscDebugln(" | TypeTag: "+mess.typetag());

  if (mapChoice == 2 || mapChoice ==3) {
    checkIPadMessage(mess);
  } else if (mapChoice == 4 || mapChoice ==5) {
    checkKinectMessage(mess);
  }
}

void checkIPadMessage(OscMessage mess) {
  if (mess.checkAddrPattern("/tbcast/view/height")) {
    /* parse mess and extract the values from the osc message arguments. */
    float firstValue = mess.get(0).floatValue();  
    oscDebugln("Height: "+firstValue);
  } else if (mess.checkAddrPattern("/tbcast/view/width")) {
    /* parse mess and extract the values from the osc message arguments. */
    float firstValue = mess.get(0).floatValue();
    oscDebugln("Width: "+firstValue);
  } else if (mess.checkAddrPattern("/tbcast/touch/happening")) {
    /* parse mess and extract the values from the osc message arguments. */
    int firstValue = mess.get(0).intValue(); 
    if (firstValue==1) {
      touchOn=true;
    } else {
      touchOn=false;
    }
    oscDebugln("Touch Value: "+firstValue);
  } else if (mess.checkAddrPattern("/tbcast/touch/x")) {
    /* parse mess and extract the values from the osc message arguments. */
    float firstValue = mess.get(0).floatValue();
    oscDebugln("TouchX Raw: "+firstValue);
    if (mapChoice==2) {
      //Portrait
      currentP.x = firstValue*screenScale;
    } else if (mapChoice==3) {
      //Need to map for Landscape
      currentP.x = firstValue*screenScale;
      //currentP.y = 512 -(firstValue*screenScale);
    }
  } else if (mess.checkAddrPattern("/tbcast/touch/y")) {
    /* parse mess and extract the values from the osc message arguments. */
    float firstValue = mess.get(0).floatValue();  
    oscDebugln("TouchY Raw: "+firstValue);
    if (mapChoice==2) {
      //Portrait
      currentP.y = firstValue*screenScale;
    } else if (mapChoice==3) {
      //Need to map for Landscape
      currentP.y = firstValue*screenScale;
    }
  } else if (mess.checkAddrPattern("/tbcast/event/singleTap")) {
    int firstValue = mess.get(0).intValue();  
    if (firstValue==1) {
      oscDebugln("***Single Tap On***");
    } else {
      oscDebugln("***Single Tap Off***");
      //mapDisplay.action(0, int(currentP.x), int(currentP.y));
    }
  } else if (mess.checkAddrPattern("/tbcast/event/doubleTap")) {
    /* parse mess and extract the values from the osc message arguments. */
    int firstValue = mess.get(0).intValue();  
    if (firstValue==1) {
      oscDebugln("***Double Tap On***");
    } else {
      oscDebugln("***Double Tap Off***");
      //mapDisplay.action(1, int(currentP.x), int(currentP.y));
    }
  } else if (mess.checkAddrPattern("/tbcast/event/tripleTap")) {
    /* parse mess and extract the values from the osc message arguments. */
    //    float firstValue = mess.get(0).floatValue();  
    oscDebugln("***Triple Tap***");
  }
}


void checkKinectMessage(OscMessage oMsg) {
  if (oMsg.checkAddrPattern("/touchStart")) {
    //touchOn=true;
    System.out.println("Touch Start");
  }

  if (oMsg.checkAddrPattern("/touchMove")) {
    touchOn=true;
    if (oMsg.checkTypetag("ff")) {
      float xPos = oMsg.get(0).floatValue();
      float yPos = oMsg.get(1).floatValue();
      System.out.println("Raw xy : "+xPos+", "+yPos);
      //currentP.x = map(xPos, 10, 780, 0, MAP4_WIDTH); //place the min and max achieveable here
      //currentP.y = map(yPos, 20, 590, 0, MAP4_HEIGHT);
      System.out.println("Scaled xy : "+xPos+", "+yPos);
    }
  }

  if (oMsg.checkAddrPattern("/touchEnd")) {
    //touchOn=false;
    System.out.println("Touch End");
  }
}


void oscDebug(String s_) {
  if (oscDebug) {
    //print(s_);
  }
  if (debugToFile) {
    //System.out.println("Printing to file");
    //debugLog.print(s_);
  }
}

void oscDebugln(String s_) {
  if (oscDebug) {
    System.out.println(s_);
  }
  if (debugToFile) {
    //System.out.println("Printing to file");
    //debugLog.System.out.println(s_);
  }
}

    
}
