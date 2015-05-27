/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioTactileMap;

import processing.core.*;
import ddf.minim.*;
import processing.core.PConstants;
/**
 *
 * @author Administrator
 */
/*SoundManager
 
 Holds a pool of sounds that can be accessed by Zones and Routes.
 For now, file name is index number
 */

class SoundManager {
  PApplet parent;
  Minim minim; //may need to change this to ref in parent
  AudioPlayer [] environSounds;
  int slot = 0; //current index in array (to be loaded)
  int fileCount;
  String soundPath; //sound subfolder of asset path
  boolean isPlaying = false; //is any sound currently playing

  SoundManager(PApplet parent_, int fileCount_, String path_) {
    parent=parent_;
    soundPath = path_;
    fileCount = fileCount_;
    System.out.println("Sound Path is: "+soundPath+" and there are "+fileCount+" files to load");
    environSounds = new AudioPlayer[fileCount];
    System.out.println("environSounds.length is "+environSounds.length);
  }

  void addSound(int id_) {
    System.out.println("Sound manager is loading "+soundPath+id_+".wav");
    environSounds[slot] = minim.loadFile(soundPath+id_+".wav"); //loads a file named with the index provided
    if (environSounds[slot]!=null) {
      System.out.println(soundPath+id_+".wav loaded successfully");
    }
    slot+=1;
  }

  void play(int index_) {
    //Check if a AP is playing, pause, rewind and play new sound
    int index = index_;
    int playingID=-1;
    boolean playing = false;
    //find the index of sound currently playing
    for (int i=0; i<environSounds.length; i+=1) {
      System.out.println("check if playing #"+i);
      if (environSounds[i].isPlaying()) {
        System.out.println("sound playing is #"+i);
        playingID=i;
        playing = true;
        break;
      } else {
        System.out.println("file # "+i+" isn't playing");
      }
    }
    //if trying to play a different sound, pause the current playing sound
    if (playing) {
      if (index != playingID) {
        System.out.println("Stopping sound: "+ playingID);   
        environSounds[playingID].pause();
        environSounds[playingID].rewind();
        System.out.println("And playing sound: "+index);
        environSounds[index].play(); //play the new sound
        environSounds[index].rewind();
      }
    } else {
      System.out.println("SoundManager trying to play # "+index);
      environSounds[index].play(); //play the sound
      environSounds[index].rewind();
    }
  }
}

