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


class Zone {
  PApplet parent;
  String name, info;
  int zoneID, zoneLabel;
  //AudioSnippet nameAudio, infoAudio;

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
