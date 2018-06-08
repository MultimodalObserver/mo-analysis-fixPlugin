/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mo.eyetribefixplayer;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author gustavo
 */
public class AOISFrame extends javax.swing.JFrame {

    private ArrayList<AOIDataPanel> dataPanels;
    private ArrayList<AOI> aois;
    private AOIMap aoiMap;
    private JFXPanelFixations fixPanel;
    private File outputDir;

    public AOISFrame() {
        initComponents();
    }

    public AOISFrame(JFXPanelFixations parent, AOIMap aoiMap, File outputDir) {
        initComponents();

        this.resumeButton.setVisible(false); //remove this line to implement resume button
        this.fixPanel = parent;
        this.aoiMap = aoiMap;
        this.aois = aoiMap.getAOIs();
        this.dataPanels = new ArrayList<AOIDataPanel>();
        this.outputDir = outputDir;

        for (AOI aoi : aois) {
            if (aoi != null) {
                AOIDataPanel p = new AOIDataPanel(this, aoi);
                this.mainPanel.add(p);
                this.dataPanels.add(p);
            }
        }

        this.setSize(250, 500);
        this.setAlwaysOnTop(true);
        this.setLocation(parent.getX() + parent.getRealWidth(), parent.getY());
    }

    public void updateData() {

        for (AOIDataPanel p : this.dataPanels) {
            p.updateData();
        }
    }

    public void addAoi(AOI aoi) {
        AOIDataPanel p = new AOIDataPanel(this, aoi);
        this.mainPanel.add(p);
        this.dataPanels.add(p);
        this.setSize(this.getWidth(), this.getHeight() + 100);
    }

    public ArrayList<AOI> getAOIs() {
        return aois;
    }

    public AOIMap getAOIMap() {
        return this.aoiMap;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void repaintVis() {
        this.fixPanel.paint(this.fixPanel.getGraphics());
    }

    public File getOuputDir() {
        return this.outputDir;
    }

    public void aoiToFile(AOI aoi) {
        this.fixPanel.AOItoFile(aoi, outputDir);
    }

    public void saveChenges() {

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainScrollPanel = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();
        generalOptionsPanel = new javax.swing.JPanel();
        resumeButton = new javax.swing.JButton();
        cleanAllButton = new javax.swing.JButton();
        deleteAllButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        mainPanel.setLayout(new javax.swing.BoxLayout(mainPanel, javax.swing.BoxLayout.Y_AXIS));

        generalOptionsPanel.setBackground(new java.awt.Color(255, 255, 255));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("i18n/mo/analysis/fixPlugin/aoiDataPanel"); // NOI18N
        resumeButton.setText(bundle.getString("resume")); // NOI18N
        generalOptionsPanel.add(resumeButton);

        cleanAllButton.setText(bundle.getString("cleanAll")); // NOI18N
        cleanAllButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cleanAllButtonMouseClicked(evt);
            }
        });
        generalOptionsPanel.add(cleanAllButton);

        deleteAllButton.setText(bundle.getString("deleteAll")); // NOI18N
        deleteAllButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteAllButtonMouseClicked(evt);
            }
        });
        generalOptionsPanel.add(deleteAllButton);

        mainPanel.add(generalOptionsPanel);

        mainScrollPanel.setViewportView(mainPanel);

        getContentPane().add(mainScrollPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cleanAllButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cleanAllButtonMouseClicked
        for (AOIDataPanel dataPanel : this.dataPanels) {
            dataPanel.getAOI().reset();
            dataPanel.updateData();
        }
    }//GEN-LAST:event_cleanAllButtonMouseClicked

    private void deleteAllButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteAllButtonMouseClicked

        java.util.ResourceBundle dialogBundle = java.util.ResourceBundle.getBundle("i18n/mo/analysis/fixPlugin/fixPluginDialogs");
        int resp = JOptionPane.showConfirmDialog(this, dialogBundle.getString("deleteAllAoiDialog"), "Alerta!", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

        if (resp == 0) {
            for (AOIDataPanel dataPanel : this.dataPanels) {
                this.mainPanel.remove(dataPanel);
            }
            this.aoiMap.deleteAll();
            this.repaintVis();
            this.setSize(this.getWidth(), this.getHeight() - 100);
            this.aoiMap.saveChanges();
        }

    }//GEN-LAST:event_deleteAllButtonMouseClicked

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cleanAllButton;
    private javax.swing.JButton deleteAllButton;
    private javax.swing.JPanel generalOptionsPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JScrollPane mainScrollPanel;
    private javax.swing.JButton resumeButton;
    // End of variables declaration//GEN-END:variables
}
