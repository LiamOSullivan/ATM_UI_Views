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

import processing.core.*;
import ddf.minim.*;

class Zone {
  PApplet parent;
  String name, info, nameAudioiFile;
  int zoneID, zoneLabel;
  PVector loc;
  int size =-1;
  
  AudioPlayer nameAudio, infoAudio;

  Zone(int i_, PVector p_, int s_) {
    zoneID = i_;
    loc = p_;
    size=s_;
  }
  
  //Constructor for zones containing building or location information.
  Zone(int i_, int l_, String n_, String info_) {
    zoneID = i_;
    zoneLabel = l_;
    name = n_;
    info = info_;    
  }
  
    //Constructor for zones containing building or location information and an associated verbalised audio file.
  Zone(int i_, int l_, String n_, String info_, String na_) {
    zoneID = i_;
    zoneLabel = l_;
    name = n_;
    info = info_; 
    nameAudioiFile = na_;
  }
  
  Zone(PApplet p_, int id_) {
    parent = p_;
    zoneID = id_;
    name= "N/A";
    info= "N/A";
  }
  
  

  void setId(int id_) {
    zoneID=id_;
  }
  
  void setLabel(int l_) {
    zoneLabel=l_;
  }

  void setName(String n_) {
    name=n_;
  }

  void setInfo(String i_) {
    info=i_;
  }

  String getName() {
    return name;
  }

  String getInfo() {
    return info;
  }

  int getId() {
    return zoneID;
  }
  int getLabel() {
    return zoneLabel;
  }
  
}
