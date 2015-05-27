/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioTactileMap;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Administrator
 */
public class ATMFileFilter  extends FileFilter{
        
    /**
        * define which file extension is accepted
        */
        @Override
        public boolean accept(File f) {
                if (f.isDirectory()) {
                        return true;
                }

                String extension = Utils.getExtension(f);
                if (extension != null) {
                        if (extension.equals(Utils.atm)
                        || extension.equals(Utils.ATM)
                        || extension.equals(Utils.xml)
                        || extension.equals(Utils.XML)
                        ) {
                                return true;
                        } else {
                                return false;
                        }
                }

                return false;
        }

        /**
        * define the description of this fileFilter
        * for the JFileChooser
        */
        @Override
        public String getDescription() {
                return "Audio Tactile Map Files";
        }

        /**
        * Simple utility to compare file extensions
        */
        private static class Utils {
                public final static String atm = "atm";
                public final static String xml = "xml";
                 public final static String ATM = "ATM";
                public final static String XML = "XML";
                

                /*
                * Get the extension of a file.
                */
                public static String getExtension(File f) {
                        String ext = null;
                        String s = f.getName();
                        int i = s.lastIndexOf('.');

                        if (i > 0 &&  i < s.length() - 1) {
                                ext = s.substring(i+1).toLowerCase();
                        }
                        return ext;
                }
        }
    
    
    
}
