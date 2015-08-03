/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioTactileMap;

import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JFileChooser;

/**
 *
 * @author Liam O'Sullivan
 *
 */
public class ATMView extends javax.swing.JFrame {

    ATMController controller;
    MappletView mapplet;

    /**
     * Creates new form ATMFrame
     */
    public ATMView() {
        //ensure menus appear above heavyweight element (e.g. Mapplet in Panel)
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);

        initComponents(); //menus etc.
        mapplet = new MappletView(this); //PApplet to embed in JPanel of ATMView
        mapplet.init();
        jPanel1.add(mapplet);
    }

    //called by ATMController after it has loaded an ATMModel
    public void updateMap() {
        mapplet.setMapImage(controller.getImagePath());
        mapplet.setSoundZones(controller.getSoundZones());
        mapplet.setSegmentedZones(controller.getSegmentedZones());
        mapplet.setMapLoaded(true);
        mapplet.processMapImage();

        //enable menu opitons following loading of map
        //TODO: include check that map has loaded successfully
        saveMenu.setEnabled(true);
        saveMapAsMenuItem.setEnabled(true);
        closeMenu.setEnabled(true);
        audioSettingsMenuItem.setEnabled(true);
        inputSettingsMenuItem.setEnabled(true);
        findZonesMenuItem.setEnabled(true);
        EditMenuCheckbox.setEnabled(true);

    }

    public void registerListener(ATMController c_) {
        controller = c_;
        //colorList.addListSelectionListener(listener);
        //mousePanel.addMouseMotionListener(listener);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        openMenu = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        saveMenu = new javax.swing.JMenuItem();
        saveMapAsMenuItem = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        closeMenu = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        exitMenu = new javax.swing.JMenuItem();
        editMapMenuItem = new javax.swing.JMenu();
        EditMenuCheckbox = new javax.swing.JCheckBoxMenuItem();
        viewMenu = new javax.swing.JMenu();
        SettingsMenu = new javax.swing.JMenu();
        audioSettingsMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        inputSettingsMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        findZonesMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        aboutMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 527, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 621, Short.MAX_VALUE)
        );

        FileMenu.setText("File");

        openMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openMenu.setText("Open Map...");
        openMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuActionPerformed(evt);
            }
        });
        FileMenu.add(openMenu);
        FileMenu.add(jSeparator2);

        saveMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenu.setText("Save Map");
        saveMenu.setEnabled(false);
        saveMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuActionPerformed(evt);
            }
        });
        FileMenu.add(saveMenu);

        saveMapAsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        saveMapAsMenuItem.setText("Save Map As...");
        saveMapAsMenuItem.setEnabled(false);
        saveMapAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMapAsMenuItemActionPerformed(evt);
            }
        });
        FileMenu.add(saveMapAsMenuItem);
        FileMenu.add(jSeparator6);

        closeMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        closeMenu.setText("Close Map");
        closeMenu.setEnabled(false);
        closeMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeMenuActionPerformed(evt);
            }
        });
        FileMenu.add(closeMenu);
        FileMenu.add(jSeparator5);

        exitMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        exitMenu.setText("Exit");
        exitMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuActionPerformed(evt);
            }
        });
        FileMenu.add(exitMenu);

        jMenuBar1.add(FileMenu);

        editMapMenuItem.setText("Edit");
        editMapMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMapMenuItemActionPerformed(evt);
            }
        });

        EditMenuCheckbox.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        EditMenuCheckbox.setText("Edit Map");
        EditMenuCheckbox.setEnabled(false);
        EditMenuCheckbox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                EditMenuCheckboxItemStateChanged(evt);
            }
        });
        EditMenuCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditMenuCheckboxActionPerformed(evt);
            }
        });
        editMapMenuItem.add(EditMenuCheckbox);

        jMenuBar1.add(editMapMenuItem);

        viewMenu.setText("View");
        jMenuBar1.add(viewMenu);

        SettingsMenu.setText("Settings");

        audioSettingsMenuItem.setText("Audio Settings...");
        audioSettingsMenuItem.setEnabled(false);
        audioSettingsMenuItem.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                audioSettingsMenuItemStateChanged(evt);
            }
        });
        audioSettingsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                audioSettingsMenuItemActionPerformed(evt);
            }
        });
        SettingsMenu.add(audioSettingsMenuItem);
        SettingsMenu.add(jSeparator3);

        inputSettingsMenuItem.setText("Input Settings...");
        inputSettingsMenuItem.setEnabled(false);
        inputSettingsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputSettingsMenuItemActionPerformed(evt);
            }
        });
        SettingsMenu.add(inputSettingsMenuItem);
        SettingsMenu.add(jSeparator4);

        findZonesMenuItem.setText("Find Zones...");
        findZonesMenuItem.setEnabled(false);
        findZonesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findZonesMenuItemActionPerformed(evt);
            }
        });
        SettingsMenu.add(findZonesMenuItem);
        SettingsMenu.add(jSeparator1);

        jMenuBar1.add(SettingsMenu);

        aboutMenu.setText("About");

        jMenuItem1.setText("About ATM...");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        aboutMenu.add(jMenuItem1);

        jMenuBar1.add(aboutMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuActionPerformed
//        fc = new JFileChooser();
//        System.out.println("Save File Selected");
//        int returnVal = fc.showSaveDialog(null);
//        System.out.println("val = " + returnVal);
//        if (returnVal == JFileChooser.APPROVE_OPTION) {
//            File file = fc.getSelectedFile();
//            System.out.println("View: Save: " + file.getName() + "." + newline);
//            controller.saveFile(file);
//        } else {
//            System.out.println("Save command cancelled by user." + newline);
//        }
        //TODO: implement save to current map
    }//GEN-LAST:event_saveMenuActionPerformed

    private void closeMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_closeMenuActionPerformed

    private void openMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuActionPerformed

        fc = new JFileChooser();
        fc.setFileFilter(new ATMFileFilter());
        System.out.println("Open File Selected");
        int returnVal = fc.showOpenDialog(null);
        System.out.println("val = " + returnVal);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            System.out.println("View Opening: " + file.getName());
            controller.loadFile(file);
            //sending the selectedFile to loadBgImage() in the PApplet
            //mapplet.loadMapImage(fc.getSelectedFile());           

        } else {
            System.out.println("Open command cancelled by user.");
        }

    }//GEN-LAST:event_openMenuActionPerformed

    private void exitMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuActionPerformed
        // TODO Add confirm save/exit dialog if map has been modified
        System.out.println("Exit command - closing application.");
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

    }//GEN-LAST:event_exitMenuActionPerformed

    private void findZonesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findZonesMenuItemActionPerformed
        // TODO add your handling code here:
        System.out.println("FindZones action performed: ");
        FindZonesSettings fz = new FindZonesSettings();
        fz.setVisible(true);
    }//GEN-LAST:event_findZonesMenuItemActionPerformed

    private void editMapMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMapMenuItemActionPerformed
        // TODO add your handling code here:
        System.out.println("***Edit Map Menu***");
    }//GEN-LAST:event_editMapMenuItemActionPerformed

    private void saveMapAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMapAsMenuItemActionPerformed
        // TODO add your handling code here:
        fc = new JFileChooser();
        System.out.println("Save As Selected");
        int returnVal = fc.showSaveDialog(null);
        System.out.println("val = " + returnVal);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            System.out.println("View: Saving As: " + file.getName() + "." + newline);
            controller.saveFile(file);
        } else {
            System.out.println("View: Save As command cancelled by user." + newline);
        }
    }//GEN-LAST:event_saveMapAsMenuItemActionPerformed

    private void EditMenuCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditMenuCheckboxActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_EditMenuCheckboxActionPerformed

    private void audioSettingsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_audioSettingsMenuItemActionPerformed
        //instantiates audio settings menu
        System.out.println("Audio Settings action performed: ");
        AudioSettings as = new AudioSettings();
        as.setVisible(true);

    }//GEN-LAST:event_audioSettingsMenuItemActionPerformed

    private void inputSettingsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputSettingsMenuItemActionPerformed
        // TODO add your handling code here:
        System.out.println("Input Settings action performed: ");
        InputSettings is = new InputSettings();
        is.setVisible(true);

    }//GEN-LAST:event_inputSettingsMenuItemActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        System.out.println("jMenuItem1ActionPerformed");
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void audioSettingsMenuItemStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_audioSettingsMenuItemStateChanged
        // TODO add your handling code here:
        //if (evt.) {

        //System.out.println("audioSettings change: ");
        //} 
//else {
//                 System.out.println("Save As command cancelled by user." + newline);
//            }
//        
    }//GEN-LAST:event_audioSettingsMenuItemStateChanged

    private void EditMenuCheckboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_EditMenuCheckboxItemStateChanged
        System.out.println("EditMenuCheckboxActionPerformed: "
                + "Value is " + EditMenuCheckbox.getState());
        if (controller.isMapLoaded()) {
            System.out.println("CANNOT EDIT UNTIL MAP HAS BEEN LOADED!)");
            controller.setEditMode(EditMenuCheckbox.getState());

        } else {
            EditMenuCheckbox.setState(false);
        }
    }//GEN-LAST:event_EditMenuCheckboxItemStateChanged

    static private final String newline = "\n";
    JButton openButton, saveButton;
    JFileChooser fc;
    //JComponent scrollP = new ScrollPanel();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem EditMenuCheckbox;
    private javax.swing.JMenu FileMenu;
    private javax.swing.JMenu SettingsMenu;
    private javax.swing.JMenu aboutMenu;
    private javax.swing.JMenuItem audioSettingsMenuItem;
    private javax.swing.JMenuItem closeMenu;
    private javax.swing.JMenu editMapMenuItem;
    private javax.swing.JMenuItem exitMenu;
    private javax.swing.JMenuItem findZonesMenuItem;
    private javax.swing.JMenuItem inputSettingsMenuItem;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JMenuItem openMenu;
    private javax.swing.JMenuItem saveMapAsMenuItem;
    private javax.swing.JMenuItem saveMenu;
    private javax.swing.JMenu viewMenu;
    // End of variables declaration//GEN-END:variables
}
