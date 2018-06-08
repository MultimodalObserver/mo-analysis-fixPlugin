package mo.eyetribefixplayer;

import java.awt.BorderLayout;
import javafx.scene.media.Media;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrackingFixPanel extends javax.swing.JPanel {

    public TrackingFixPanel() {
        initComponents();
    }

    public void setupMedia(Media media) throws FileNotFoundException {

        this.jfxPanel = new JFXPanelFixations();
        this.setLayout(new BorderLayout());
        this.add(jfxPanel, BorderLayout.CENTER);
        this.jfxPanel.setColorFixations(Color.BLUE);

        this.jfxPanel.addVideo(media);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(500, 500));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 673, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 498, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public void playVideo() {
        this.jfxPanel.playVideo();
    }

    public void pauseVideo() {
        this.jfxPanel.pause();
    }

    public void stopVideo() {
        this.jfxPanel.stop();
    }

    public void seekVideo(long time) {
        this.jfxPanel.seek(time);
    }
    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        this.jfxPanel.correctSize();
    }//GEN-LAST:event_formComponentResized

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

    }//GEN-LAST:event_formMouseClicked

    public JFXPanelFixations getFxPanel() {
        return this.jfxPanel;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private JFXPanelFixations jfxPanel;

    void setWhite() {

        try {
            this.jfxPanel = new JFXPanelFixations();
            this.setLayout(new BorderLayout());
            this.add(jfxPanel, BorderLayout.CENTER);
            this.jfxPanel.setColorFixations(Color.BLUE);
            this.jfxPanel.useVideo(false);
            this.jfxPanel.setBackground(Color.white);
            this.setBackground(Color.WHITE);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TrackingFixPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void setWhite(int width, int height) {

        try {
            this.jfxPanel = new JFXPanelFixations();
            this.setLayout(new BorderLayout());
            this.add(jfxPanel, BorderLayout.CENTER);
            this.jfxPanel.setColorFixations(Color.BLUE);
            this.jfxPanel.useVideo(false, width, height);
            this.jfxPanel.setBackground(Color.white);
            this.setBackground(Color.WHITE);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TrackingFixPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
