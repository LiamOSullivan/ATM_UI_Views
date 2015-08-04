/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioTactileMap;

import processing.core.*;
import ddf.minim.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The MappletView is a PApplet used to display the background map image and
 * animation/annotation placed on top. It is embedded in a JPanel on the main
 * ATMView JFrame.
 *
 * @author Administrator
 */
public class MappletView extends PApplet implements ActionListener {

    Minim minim;
    ATMView parent;
    PImage mapImg = null;

    int w, h;
    PFont font;
    ArrayList<SoundZone> msz; //The zones in the map with associated sound files (e.g. recorded environmental sounds).
    ArrayList<SegmentedZone> zones; //Zones on map as segregated blobs extracted from the image.
    boolean isMapLoaded = false;
    PVector moveToPVector;
    boolean editView = false;

    ////Segmentation variables...
    ImageProcessor imgProcessor;
    int activeBlobIndex = -1, activeBlobLabel = -1;
    boolean showAllContours = true; //highlight all the segmented blobs
    boolean showSegmentedZones = true, showSegmentedLabels = false; //higlight just the stored SegmentedZones 
    int MIN_BLOB_SIZE = 20; //heuristic for ignoring small blobs
    int maxBlobSize, minBlobSize = MIN_BLOB_SIZE; //adjustable from settings GUI
    int segmentationStartTime; //Used to time blob detection and segmentation

    MappletView(ATMView p_) {
        parent = p_;
    }

    @Override
    public void setup() {
        //System.out.println("Mapplet setup called");
        size(800, 600);
        font = createFont("CourierNewPSMT-48.vlw", 48);
        textFont(font, 24);
        fill(0);
        //parent.setJMenuBar(parent.jMenuBar1);

    }

    @Override
    public void draw() {
        if (isMapLoaded) {
            if (mapImg != null) {
                image(mapImg, 0, 0, this.width, this.height);
                if (editView) {
                    imgProcessor.showAllContours(); //highlight all blobs for editing
                } else {
                    if (showSegmentedZones) {
                        drawSegmentedZones(); //highlight just the segmented zones
                    }
                }
            }

            drawSoundZones();

        } else {
            //if map not loaded, the background is painted white w/ message
            background(255);
            text("Please choose a map from File > Open Map...", 12, height / 2);
        }
    }

    //TODO: remove action listener if unused
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
     * methods to set the background map image and pass a reference to zones.
     */
    public void setMapLoaded(boolean b_) {
        isMapLoaded = b_;

    }

    public void setMapImage(String f_) {
        //System.out.println("Mapplet trying " + f_);
        mapImg = loadImage(f_);
        //TODO: scale images which are too big
        size(mapImg.width, mapImg.height);
    }

    void processMapImage() {
        imgProcessor = new ImageProcessor(this, mapImg);
        imgProcessor.process();
        //findSegmentedCentroids();

    }

    void findSegmentedCentroids() {
        for (int i = 0; i < zones.size(); i += 1) {
            System.out.println("Mapplet finding centroids");
            PVector pv = imgProcessor.findCentroid(zones.get(i).getLabel());
            zones.get(i).setCentroid(pv);
            System.out.println("Mapplet successfully set centroid " + i + " @ " + zones.get(i).getCentroid());
        }
    }

    //Loads the array of zones with asscoiated sound files
    public void setSoundZones(ArrayList<SoundZone> sz_) {
        msz = sz_;
    }

    //Loads the array of zones on map image. May have associated sound file or may not.
    public void setSegmentedZones(ArrayList<SegmentedZone> sz_) {
        zones = sz_;
    }

    /**
     * real-time drawing methods
     */
    private void drawSoundZones() {
        for (int i = 0; i < msz.size(); i += 1) {
            fill(200, 0, 0, 150);
            ellipse(msz.get(i).getZonePosition().x, msz.get(i).getZonePosition().y,
                    msz.get(i).getZoneSize(), msz.get(i).getZoneSize());
        }
        if (parent.controller.isInEditMode && parent.controller.isMovingZone) {
            fill(0, 255, 0, 200);
            ellipse(moveToPVector.x, moveToPVector.y, msz.get(0).getZoneSize(), msz.get(0).getZoneSize());
        }
    }

    void drawSegmentedZones() {
        for (int i = 0; i < zones.size(); i += 1) {
            imgProcessor.showContour(zones.get(i).getLabel());
            if (showSegmentedLabels) {
                text(zones.get(i).getLabel(), zones.get(i).getCentroid().x, zones.get(i).getCentroid().y);
            }
        }

    }

    void setEditView(boolean b_) {
        editView = b_;

    }

    void setShowSegmented(boolean b_) {
        showSegmentedZones = b_;
    }

    void setShowSegmentedLabels(boolean b_) {
        showSegmentedLabels = b_;
    }

    /*
     * mouse listeners and interaction handlers
     */
    @Override
    public void mousePressed() {
        if (parent.controller.isInEditMode) {
            println("MousePressed ");
            if (keyPressed && keyCode == SHIFT) {
                action(0, mouseX, mouseY); //start move
            }
        }
    }

    @Override
    public void mouseReleased() {
        if (parent.controller.isInEditMode && parent.controller.isMovingZone) {
            if (keyPressed && keyCode == SHIFT) {
                println("MouseReleased");
                print("Moving zone is " + parent.controller.isMovingZone);
                parent.controller.endMoveZone(new PVector(mouseX, mouseY));
            }
        }

    }

    @Override
    public void mouseDragged() {
        //if in edit mode indicate 'move to' position at location of mouse cursor
        if (parent.controller.isInEditMode
                && keyPressed
                && keyCode == SHIFT
                && parent.controller.isMovingZone) {
            moveToPVector.x = mouseX;
            moveToPVector.y = mouseY;
        } else if (parent.controller.isInEditMode && keyPressed && keyCode == CONTROL) {
            //TODO : resize soundzone
        }
    }

    @Override
    public void mouseClicked() {
        if (!parent.controller.isInEditMode) {
            if (keyPressed && keyCode == SHIFT) {
                println("MouseClicked Action 0");
                action(0, mouseX, mouseY);
            } else if (keyPressed && keyCode == CONTROL) {
                println("MouseClicked Action 2");
                action(2, mouseX, mouseY);
            } else {
                println("MouseClicked Action 1");
                action(1, mouseX, mouseY);
            }
        } else {
            //click in edit mode brings up file selection popup
            System.out.println("Click in Mapplet in Edit Mode ");
            if (!keyPressed) {
                action(1, mouseX, mouseY);
            } else if (keyPressed && keyCode == SHIFT) {

            }

        }
    }

    private void action(int n_, int x_, int y_) {
        int actionNo = n_;
        boolean keepChecking = true;
        if (parent.controller.model.isModelLoaded) {
            //first check if over a sound zone
            for (int i = 0; i < msz.size(); i += 1) {
                if (msz.get(i).checkIfOver(x_, y_)) {
                    println("Over soundZone #" + msz.get(i).getId());
                    if (!parent.controller.isInEditMode) {
                        parent.controller.selectZone(msz, i, actionNo);
                        keepChecking = false;
                    } else {
                        if (actionNo == 0) {
                            println("Moving zone is " + parent.controller.isMovingZone);
                            parent.controller.startMoveZone(msz, i);
                            moveToPVector = new PVector(mouseX, mouseY);

                        } else if (actionNo == 1) {
                            //Click in Edit mode opens JFrame editor for SoundZone
                            SoundZonePopup p = new SoundZonePopup(parent, msz.get(i));

                        } else if (actionNo == 2) {

                        }
                        keepChecking = false;
                        break;
                    }
                }
            }
            //if not over a sound zone, check if over a labelled building
            if (keepChecking) {
                findSegmentedZone(x_, y_, n_); //look for a building in view
            }
        }
    }

    //Called if this has failed to find a SoundZone
    private void findSegmentedZone(int x_, int y_, int act_) {
        int xPos = x_;
        int yPos = y_;
        int actionNo = act_;
        activeBlobIndex = imgProcessor.getBlobIndex(xPos, yPos);
        //activeBlobLabel = bd.getLabel(mouseX, mouseY);
        println("Inside blob #" + activeBlobIndex + " label : " + activeBlobLabel);
        boolean keepChecking = true;
        for (int i = 0; i < zones.size() && keepChecking; i += 1) {
            if (activeBlobIndex == -1) {
                String nString = "There is no building here.";
                String iString = "There is no information for this location.";
                println(nString);
                println(iString);
                keepChecking = false;

            } else if (activeBlobIndex == zones.get(i).getLabel()) {
                println("Building name: " + zones.get(i).getZoneName());
                println("Building info: " + zones.get(i).getZoneInfo());
                if (!parent.controller.isInEditMode) {
                    parent.controller.selectZone(zones, i, actionNo);
                    keepChecking = false;
                } else {
                    if (actionNo == 0) {
                    
                    } else if (actionNo == 1) {
                     //Click in Edit mode opens JFrame editor for SegmentedZone
                     SegmentedZonePopup p = new SegmentedZonePopup(parent, zones.get(i));

                    } else if (actionNo == 2) {

                    }
                }
            }
        }
        if (keepChecking) {
            println("This is not a campus building");

        }
    }

}
