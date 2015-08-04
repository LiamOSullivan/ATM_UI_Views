package audioTactileMap;

import processing.core.*;
import processing.core.PConstants;
import blobscanner.Detector;

/**
 *
 * @author Administrator
 */
class ImageProcessor {

    PApplet parent;
    PImage processImg;
    String imagePath;
    Detector bd;
    PGraphics contours;
    int segmentationStartTime;

    ImageProcessor(PApplet parent_, PImage iPath_) { //Initialise with path to file
        parent = parent_;
        processImg = iPath_.get();//make a copy for processing
    }

//    void loadFile() {
//        System.out.println("Image processor loading image from " + imagePath);
//        processImg = parent.loadImage(imagePath);
//    }

    void process() {
        if (processImg != null) {
            // analyse processImg
            processImg.filter(PConstants.THRESHOLD);
            //processImg.filter(PConstants.INVERT);
            processImg.loadPixels();
            scanForBlobs();
            int val = findLargestBlobWeight();
            //bd.findCentroids();
            //settingsGUI.minWeightSlider.setRange(minBlobSize, val);
            //settingsGUI.minWeightSlider.setValue(minBlobSize);
        }
    }

    void scanForBlobs() {
        bd = new Detector(parent, 0);
        System.out.println("***Image processor started***");
        segmentationStartTime = parent.millis(); 
        System.out.println("Finding blobs...");
        bd.findBlobs(processImg.pixels, processImg.width, processImg.height);
        //bd.imageFindBlobs(processImg);
        System.out.println("Blobs found in " + (parent.millis() - segmentationStartTime) + " ms");
        System.out.println("Total # of blobs is " + bd.getBlobsNumber());
        //bd.drawSelectBox(wMax, color(0, 255, 0), 2.0); //draw largest blob bounds (blob features required)
        //System.out.println("Drawing done in "+(millis()-segmentationStartTime)+" ms"); 
        //activeBlobVectors = bd.getBlobPixelsLocation(maxBlobIndex);
    }

    int findLargestBlobWeight() {
        //segmentationStartTime=millis(); 
        bd.loadBlobsFeatures(); //not always needed e.g. for drawing all contours
        //System.out.println("Blob features loaded in "+(millis()-segmentationStartTime)+" ms"); 
        //segmentationStartTime=millis(); 
        System.out.println("Find blob weights and largest blob...");
        //segmentationStartTime=millis(); 
        bd.weightBlobs(true); //true to use a min weight for selected blobs
        //find max and min blob weights
        int wMax = 0, wMin = processImg.width * processImg.height;
        int maxBlobIndex = 0; //the number of the largest blob
        for (int i = 0; i < bd.getBlobsNumber(); i += 1) {
            int w = bd.getBlobWeight(i);
            if (w > wMax) {
                wMax = w;
                maxBlobIndex = i;
            }
            if (w < wMin) {
                wMin = w;
            }
        }

        //System.out.println("Weights calculated in "+(millis()-segmentationStartTime)+" ms"); 
        System.out.println("wMax is "+wMax+"\t wMin is "+wMin); 
        return wMax;
    }

    void showAllContours() {
      bd.drawSelectContours(20, parent.color(255, 0, 0), 2.0F); //draw select blob outline
    }
    
    void showContour(int lbl_) {
      bd.drawBlobContour(lbl_, parent.color(255, 0, 0), 2.0F); //draw select blob outline
    }
    
    PVector findCentroid(int lbl_){
        return new PVector (bd.getCentroidX(lbl_), bd.getCentroidY(lbl_));
        
    }
      
    ///////////////////////////////////////////////////////Getter/ Setter////////////
    PImage getProcessedImage() {
        return processImg;
    }

    PImage getContourImage() {
        return contours;
    }

    int getBlobIndex(int xPos_, int yPos_) {
        return bd.getBlobNumberAt(xPos_, yPos_);
    }
}
