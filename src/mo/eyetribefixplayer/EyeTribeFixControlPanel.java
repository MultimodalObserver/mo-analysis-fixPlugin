/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mo.eyetribefixplayer;

import com.theeyetribe.clientsdk.data.GazeData;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import javafx.scene.media.Media;
import javax.swing.JColorChooser;

public class EyeTribeFixControlPanel extends javax.swing.JPanel {

    private AOISFrame aoisFrame;
    private File Dir;
    private EyeTrybeFixationAnalysisConfig config;

    public EyeTribeFixControlPanel(File dataFile, File mediaFile) throws FileNotFoundException {

        initComponents();
        this.Dir = new File(mediaFile.getParentFile().getParentFile(), "analysis");
        Media media = new Media(mediaFile.toURI().toString());

        TrackingFixPanel videoPanel = (TrackingFixPanel) this.frontPanel;
        videoPanel.setupMedia(media);
        this.trackingPanel = (TrackingFixPanel) this.frontPanel;

        this.aoisFrame = null;

    }

    public EyeTribeFixControlPanel(File dataFile) throws FileNotFoundException {

        initComponents();
        this.Dir = new File(dataFile.getParentFile().getParentFile(), "analysis");

        TrackingFixPanel videoPanel = (TrackingFixPanel) this.frontPanel;
        videoPanel.setWhite();
        this.trackingPanel = (TrackingFixPanel) this.frontPanel;

        this.aoisFrame = null;

    }

    public EyeTribeFixControlPanel(File dataFile, int width, int height) throws FileNotFoundException {

        initComponents();
        this.Dir = new File(dataFile.getParentFile().getParentFile(), "analysis");

        TrackingFixPanel videoPanel = (TrackingFixPanel) this.frontPanel;
        videoPanel.setWhite(width, height);
        this.trackingPanel = (TrackingFixPanel) this.frontPanel;

        this.aoisFrame = null;

    }

    public EyeTribeFixControlPanel(File dataFile, File mediaFile, EyeTrybeFixationAnalysisConfig config) throws FileNotFoundException {

        initComponents();
        this.config = config;
        this.Dir = new File(mediaFile.getParentFile().getParentFile(), "analysis");
        Media media = new Media(mediaFile.toURI().toString());

        TrackingFixPanel videoPanel = (TrackingFixPanel) this.frontPanel;
        videoPanel.setupMedia(media);
        this.trackingPanel = (TrackingFixPanel) this.frontPanel;

        this.aoisFrame = null;

    }

    public EyeTribeFixControlPanel(File dataFile, EyeTrybeFixationAnalysisConfig config) throws FileNotFoundException {

        initComponents();
        this.config = config;
        this.Dir = new File(dataFile.getParentFile().getParentFile(), "analysis");

        TrackingFixPanel videoPanel = (TrackingFixPanel) this.frontPanel;
        videoPanel.setWhite();
        this.trackingPanel = (TrackingFixPanel) this.frontPanel;

        this.aoisFrame = null;

    }

    public EyeTribeFixControlPanel(File dataFile, int width, int height, EyeTrybeFixationAnalysisConfig config) throws FileNotFoundException {

        initComponents();
        this.config = config;
        this.Dir = new File(dataFile.getParentFile().getParentFile(), "analysis");

        TrackingFixPanel videoPanel = (TrackingFixPanel) this.frontPanel;
        videoPanel.setWhite(width, height);
        this.trackingPanel = (TrackingFixPanel) this.frontPanel;

        this.aoisFrame = null;

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        frontPanel = new mo.eyetribefixplayer.TrackingFixPanel();
        colorButton = new javax.swing.JButton();
        opacityChooser = new javax.swing.JSlider();
        fixationsChooser = new javax.swing.JSpinner();
        fixationsNumberChooser = new javax.swing.JCheckBox();
        AOIsDataButton = new javax.swing.JButton();
        totalFixationDataLabel = new javax.swing.JLabel();
        lastFixationDurationTextLabel = new javax.swing.JLabel();
        lastFixDurationDataLabel = new javax.swing.JLabel();
        lastSacadeAmplitudeTextLabel = new javax.swing.JLabel();
        lastSacadeAmplitudeLabel = new javax.swing.JLabel();
        totalFixCountNameLabel = new javax.swing.JLabel();
        pxLabel = new javax.swing.JLabel();
        msLabel = new javax.swing.JLabel();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        frontPanel.setMinimumSize(new java.awt.Dimension(200, 200));
        frontPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                frontPanelComponentResized(evt);
            }
        });

        javax.swing.GroupLayout frontPanelLayout = new javax.swing.GroupLayout(frontPanel);
        frontPanel.setLayout(frontPanelLayout);
        frontPanelLayout.setHorizontalGroup(
            frontPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 811, Short.MAX_VALUE)
        );
        frontPanelLayout.setVerticalGroup(
            frontPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 402, Short.MAX_VALUE)
        );

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("i18n/mo/analysis/fixPlugin/fixControlPanel"); // NOI18N
        colorButton.setText(bundle.getString("selectColor")); // NOI18N
        colorButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                colorButtonMouseClicked(evt);
            }
        });

        opacityChooser.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                opacityChooserMouseDragged(evt);
            }
        });
        opacityChooser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                opacityChooserMouseReleased(evt);
            }
        });

        fixationsChooser.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        fixationsChooser.setEnabled(false);
        fixationsChooser.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                fixationsChooserStateChanged(evt);
            }
        });
        fixationsChooser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fixationsChooserMouseClicked(evt);
            }
        });

        fixationsNumberChooser.setText(bundle.getString("limitFixations")); // NOI18N
        fixationsNumberChooser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fixationsNumberChooserMouseClicked(evt);
            }
        });

        AOIsDataButton.setText("AOIs");
        AOIsDataButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AOIsDataButtonMouseClicked(evt);
            }
        });

        totalFixationDataLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        totalFixationDataLabel.setText("0");

        lastFixationDurationTextLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lastFixationDurationTextLabel.setText(bundle.getString("lastFixationDuration")); // NOI18N

        lastFixDurationDataLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lastFixDurationDataLabel.setText("0");

        lastSacadeAmplitudeTextLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lastSacadeAmplitudeTextLabel.setText(bundle.getString("saccadeAmplitude")); // NOI18N

        lastSacadeAmplitudeLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lastSacadeAmplitudeLabel.setText("0");

        totalFixCountNameLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        totalFixCountNameLabel.setText(bundle.getString("totalFixations")); // NOI18N

        pxLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        pxLabel.setText("px");

        msLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        msLabel.setText("ms");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(frontPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(colorButton)
                        .addGap(18, 18, 18)
                        .addComponent(fixationsNumberChooser)
                        .addGap(18, 18, 18)
                        .addComponent(fixationsChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(opacityChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(AOIsDataButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(totalFixCountNameLabel)
                        .addGap(18, 18, 18)
                        .addComponent(totalFixationDataLabel)
                        .addGap(27, 27, 27)
                        .addComponent(lastFixationDurationTextLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lastFixDurationDataLabel)
                        .addGap(52, 52, 52)
                        .addComponent(msLabel)
                        .addGap(58, 58, 58)
                        .addComponent(lastSacadeAmplitudeTextLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lastSacadeAmplitudeLabel)
                        .addGap(29, 29, 29)
                        .addComponent(pxLabel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalFixationDataLabel)
                    .addComponent(lastFixationDurationTextLabel)
                    .addComponent(lastFixDurationDataLabel)
                    .addComponent(lastSacadeAmplitudeTextLabel)
                    .addComponent(lastSacadeAmplitudeLabel)
                    .addComponent(totalFixCountNameLabel)
                    .addComponent(pxLabel)
                    .addComponent(msLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(AOIsDataButton)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(frontPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(colorButton)
                                .addComponent(fixationsChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(fixationsNumberChooser))
                            .addComponent(opacityChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void frontPanelComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_frontPanelComponentResized

    }//GEN-LAST:event_frontPanelComponentResized

    private void colorButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colorButtonMouseClicked
        Color c = JColorChooser.showDialog(this, "Seleccion color", Color.white);
        if (c != null) {
            this.trackingPanel.getFxPanel().setFixationsColor(c);
            if (this.config != null) {
                config.saveChanges();
            }
        }
        // this.trackingPanel.getFxPanel().getFijationMap().cleanMap();
        // this.trackingPanel.getFxPanel().getFijationMap().repaint();
        this.repaint();
    }//GEN-LAST:event_colorButtonMouseClicked

    private void opacityChooserMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opacityChooserMouseDragged
        /*this.trackingPanel.getFxPanel().setFixationsOpacity(new Double(this.opacityChooser.getValue())/100);
       // this.trackingPanel.getFxPanel().getFijationMap().cleanMap();
      //  this.trackingPanel.getFxPanel().getFijationMap().repaint();
        if(this.config!=null){config.saveChanges();}
        this.repaint();*/
    }//GEN-LAST:event_opacityChooserMouseDragged

    private void fixationsNumberChooserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fixationsNumberChooserMouseClicked
        if (!this.fixationsChooser.isEnabled()) {
            this.fixationsChooser.setEnabled(true);
            this.trackingPanel.getFxPanel().getFixationMap().setLimit((int) this.fixationsChooser.getValue());
            this.trackingPanel.getFxPanel().getFixationMap().cleanMap();
            this.trackingPanel.getFxPanel().getFixationMap().repaint();
        } else {
            this.fixationsChooser.setEnabled(false);
            this.trackingPanel.getFxPanel().getFixationMap().setLimit(-1);
            this.trackingPanel.getFxPanel().getFixationMap().cleanMap();
            this.trackingPanel.getFxPanel().getFixationMap().repaint();
        }
        if (this.config != null) {
            config.saveChanges();
        }
        this.repaint();
    }//GEN-LAST:event_fixationsNumberChooserMouseClicked

    private void fixationsChooserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fixationsChooserMouseClicked
        if (this.fixationsChooser.isEnabled()) {
            this.trackingPanel.getFxPanel().getFixationMap().setLimit((int) this.fixationsChooser.getValue());
        }
        if (this.config != null) {
            config.saveChanges();
        }
    }//GEN-LAST:event_fixationsChooserMouseClicked

    private void fixationsChooserStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_fixationsChooserStateChanged
        this.trackingPanel.getFxPanel().getFixationMap().setLimit((int) this.fixationsChooser.getValue());
        this.trackingPanel.getFxPanel().getFixationMap().cleanMap();
        this.trackingPanel.getFxPanel().getFixationMap().repaint();
        if (this.config != null) {
            config.saveChanges();
        }
        this.repaint();
    }//GEN-LAST:event_fixationsChooserStateChanged

    private void AOIsDataButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AOIsDataButtonMouseClicked

        if (this.trackingPanel.getFxPanel().getAOIMap() == null) {
        } else {
            this.aoisFrame = this.trackingPanel.getFxPanel().toDoAoisFrame(this.Dir);
        }
    }//GEN-LAST:event_AOIsDataButtonMouseClicked

    private void opacityChooserMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opacityChooserMouseReleased
        this.trackingPanel.getFxPanel().setFixationsOpacity(new Double(this.opacityChooser.getValue()) / 100);
        if (this.config != null) {
            config.saveChanges();
        }
        this.repaint();
    }//GEN-LAST:event_opacityChooserMouseReleased

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        int realWidth = this.trackingPanel.getFxPanel().getRealWidth();
        int realHeight = this.trackingPanel.getFxPanel().getRealHeight();
        this.trackingPanel.setSize(realWidth, realHeight);
        this.trackingPanel.getFxPanel().setSize(realWidth, realHeight);
        this.trackingPanel.setPreferredSize(new Dimension(realWidth, realHeight));
    }//GEN-LAST:event_formComponentResized

    public void setOffset(long offset) {
        this.trackingPanel.getFxPanel().setOffset(offset);
    }

    public void playVideo() {
        this.trackingPanel.getFxPanel().playVideo();
    }

    public void playData(long time) {

    }

    public void addData(GazeData data) {
        this.trackingPanel.getFxPanel().addData(data);
        if (this.aoisFrame != null) {
            this.aoisFrame.updateData();
        }
        this.updateGeneralData();
    }

    public void addDataWithoutAois(GazeData data) {
        this.trackingPanel.getFxPanel().addDataWithoutAois(data);
        this.updateGeneralData();
    }

    public void playWhitLimit(Long limit) {
        this.trackingPanel.getFxPanel().playToLimit(limit);
    }

    public void pauseVideo() {
        this.trackingPanel.getFxPanel().pauseVideo();
    }

    public void seekVideo(long time) {
        this.trackingPanel.getFxPanel().seek(time);
    }

    public void stop() {
        this.trackingPanel.getFxPanel().stop();
    }

    public void cleanLastLimit() {
        this.trackingPanel.getFxPanel().cleanLastPlayLimit();
    }

    private void updateGeneralData() {
        FixationMap jfx = this.trackingPanel.getFxPanel().getFixationMap();
        DecimalFormat df = new DecimalFormat("#.0000");

        this.totalFixationDataLabel.setText(String.valueOf(jfx.getFixationCount()));
        if (jfx.getLastFixation() != null) {
            this.lastFixDurationDataLabel.setText(String.valueOf(jfx.getLastFixation().getDuration()));
        }
        this.lastSacadeAmplitudeLabel.setText(df.format(jfx.getLastSaccadeAmplitude()));
    }

    public void reset() {
        this.trackingPanel.getFxPanel().reset();
    }

    public Color getFixationsColor() {
        return this.trackingPanel.getFxPanel().getFixationColor();
    }

    public double getFixationsOpacity() {
        return this.trackingPanel.getFxPanel().getFixationsOpacity();
    }

    public int getFixationsLimit() {
        return this.trackingPanel.getFxPanel().getFixationMap().getLimit();
    }

    public void setFixationsColor(Color color) {
        this.trackingPanel.getFxPanel().getFixationMap().setColor(color);
    }

    public void setFixationsOpacity(Double opacity) {
        this.trackingPanel.getFxPanel().getFixationMap().setOpacity(opacity);
        Double intValue = opacity * 100;
        this.opacityChooser.setValue(intValue.intValue());
    }

    public void setFixationsLimit(int limit) {
        this.trackingPanel.getFxPanel().getFixationMap().setLimit(limit);
        if (limit < 0) {
            this.fixationsNumberChooser.setSelected(false);
            this.fixationsChooser.setEnabled(false);
        } else {
            this.fixationsNumberChooser.setSelected(true);
            this.fixationsChooser.setValue(limit);
            this.fixationsChooser.setEnabled(true);
        }
    }

    public void setAoisFile(File file) {
        this.trackingPanel.getFxPanel().getAOIMap().setAoisFile(file);
    }

    public void loadAoisFromFile(File file) {
        this.trackingPanel.getFxPanel().getAOIMap().loadAoisFromFile(file);
    }

    public void saveAOIS(File file) {
        this.trackingPanel.getFxPanel().getAOIMap().aoisToCsvFile(file);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AOIsDataButton;
    private javax.swing.JButton colorButton;
    private javax.swing.JSpinner fixationsChooser;
    private javax.swing.JCheckBox fixationsNumberChooser;
    private javax.swing.JPanel frontPanel;
    private javax.swing.JLabel lastFixDurationDataLabel;
    private javax.swing.JLabel lastFixationDurationTextLabel;
    private javax.swing.JLabel lastSacadeAmplitudeLabel;
    private javax.swing.JLabel lastSacadeAmplitudeTextLabel;
    private javax.swing.JLabel msLabel;
    private javax.swing.JSlider opacityChooser;
    private javax.swing.JLabel pxLabel;
    private javax.swing.JLabel totalFixCountNameLabel;
    private javax.swing.JLabel totalFixationDataLabel;
    // End of variables declaration//GEN-END:variables
    private TrackingFixPanel trackingPanel;

}
