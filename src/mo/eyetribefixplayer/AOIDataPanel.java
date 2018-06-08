/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mo.eyetribefixplayer;

import java.awt.Color;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.TableModel;

/**
 *
 * @author gustavo
 */
public class AOIDataPanel extends javax.swing.JPanel {

    private AOI aoi;
    private AOISFrame AOIJFrameParent;
    private JPanel AOIJPanelParrent;
    private java.util.ResourceBundle dialogBundle;
    
    
    public AOIDataPanel() {
        initComponents();
        TableModel model = this.dataTable.getModel();   
    }
    
    public AOIDataPanel(AOISFrame parent,AOI aoi) {
        initComponents();
        this.aoi = aoi;
        this.AOIJFrameParent = parent;
        TableModel model = this.dataTable.getModel();
        model.setValueAt(aoi.getId(), 0, 1);
        updateData();       
        this.dataTable.getTableHeader().setVisible(false);
        this.AOIJPanelParrent = parent.getMainPanel();
        this.dialogBundle = java.util.ResourceBundle.getBundle("i18n/mo/analysis/fixPlugin/fixPluginDialogs");
        
       // this.dataTable.setShowGrid(false);
        //this.dataTable.set
    } 
    
    public AOIDataPanel(JPanel parent,AOI aoi) {
        initComponents();
        this.aoi = aoi;
        this.AOIJPanelParrent = parent;
        TableModel model = this.dataTable.getModel();
        model.setValueAt(aoi.getId(), 0, 1);
        updateData();       
        this.dataTable.getTableHeader().setVisible(false);
        this.dialogBundle = java.util.ResourceBundle.getBundle("i18n/mo/analysis/fixPlugin/fixPluginDialogs");
        
       // this.dataTable.setShowGrid(false);
        //this.dataTable.set
        
    }     
    
    
    
   /* 
    public AOIDataPanel(AOI aoi) {
        initComponents();
        this.aoi = aoi;
        TableModel model = this.dataTable.getModel();
        model.setValueAt(aoi.getId(), 0, 1);
        updateData();       
        this.dataTable.getTableHeader().setVisible(false);
       // this.dataTable.setShowGrid(false);
        //this.dataTable.set
        
    }   */     

    public void updateData(){
        
        TableModel model = this.dataTable.getModel();
        model.setValueAt(aoi.getName(), 1, 1);        
        model.setValueAt(aoi.getFixationCount()+ " (fix)", 2, 1);
        model.setValueAt(aoi.getFixationDensity().toString() + " (fix/px)", 3, 1);
        model.setValueAt(aoi.getTimeToFirstFixation()+" (ms)", 4, 1);
        model.setValueAt(aoi.getFixationsBefore()+ " (fix)", 5, 1);
        model.setValueAt(aoi.getFirstFixationDuration()+ " (ms)", 6, 1);
        model.setValueAt(aoi.getTotalFixationDuration()+ " (ms)", 7, 1);
        
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();
        setVisibleButton = new javax.swing.JToggleButton();
        jButton2 = new javax.swing.JButton();
        setColorButton = new javax.swing.JButton();
        cleanAOIButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        exportDataButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        java.util.ResourceBundle aoiBundle = java.util.ResourceBundle.getBundle("i18n/mo/analysis/fixPlugin/aoiDataPanel");
        dataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"ID", null},
                {aoiBundle.getString("name"), null},
                {aoiBundle.getString("fc"), null},
                {aoiBundle.getString("fixDensity"), null},
                {aoiBundle.getString("ttf"), null},
                {aoiBundle.getString("bfb"), null},
                {aoiBundle.getString("ffd"), null},
                {aoiBundle.getString("tfd"), null}
            },
            new String [] {
                "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                if(columnIndex==1&&rowIndex==1){return true;}
                return canEdit [columnIndex];
            }
        });
        dataTable.setGridColor(new java.awt.Color(240, 240, 240));
        dataTable.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dataTablePropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(dataTable);

        setVisibleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/setVisibleIcon.png"))); // NOI18N
        setVisibleButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setVisibleButtonMouseClicked(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Pie Chart.png"))); // NOI18N

        setColorButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/setColorIcon.png"))); // NOI18N
        setColorButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setColorButtonMouseClicked(evt);
            }
        });

        cleanAOIButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cleanIcon.png"))); // NOI18N
        cleanAOIButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cleanAOIButtonMouseClicked(evt);
            }
        });

        deleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        deleteButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteButtonMouseClicked(evt);
            }
        });

        exportDataButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("i18n/mo/analysis/fixPlugin/aoiDataPanel"); // NOI18N
        exportDataButton.setText(bundle.getString("exportData")); // NOI18N
        exportDataButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exportDataButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(setColorButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(setVisibleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cleanAOIButton, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exportDataButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(setVisibleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(setColorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cleanAOIButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exportDataButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void deleteButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteButtonMouseClicked
        int resp = JOptionPane.showConfirmDialog(this, this.dialogBundle.getString("deleteAoiDialog"), "Alerta!", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        
        if(resp==0){
            this.AOIJPanelParrent.remove(this);
            this.AOIJFrameParent.getAOIs().set(aoi.getId()-1, null);   
            this.AOIJFrameParent.getAOIMap().cleanMap();               
            this.AOIJFrameParent.getAOIMap().repaint();               
            this.AOIJFrameParent.setSize(this.AOIJFrameParent.getWidth(),this.AOIJFrameParent.getHeight()-100);
            this.AOIJFrameParent.repaintVis();
            this.AOIJFrameParent.getAOIMap().saveChanges();

        }else{
        
        }

        
    }//GEN-LAST:event_deleteButtonMouseClicked

    private void setColorButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_setColorButtonMouseClicked
        
        Color c = JColorChooser.showDialog(this, "Seleccion color" , Color.white);
        aoi.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 70));
        this.AOIJFrameParent.getAOIMap().cleanMap();               
        this.AOIJFrameParent.getAOIMap().repaint();   
        this.AOIJFrameParent.repaintVis();
        this.AOIJFrameParent.getAOIMap().saveChanges();
        
    }//GEN-LAST:event_setColorButtonMouseClicked

    private void setVisibleButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_setVisibleButtonMouseClicked
        aoi.setVisible(!aoi.isVisible());
        this.AOIJFrameParent.getAOIMap().cleanMap();               
        this.AOIJFrameParent.getAOIMap().repaint();          
        this.AOIJFrameParent.repaintVis();
        
    }//GEN-LAST:event_setVisibleButtonMouseClicked

    private void cleanAOIButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cleanAOIButtonMouseClicked
        aoi.reset();
        updateData();
    }//GEN-LAST:event_cleanAOIButtonMouseClicked

    private void exportDataButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exportDataButtonMouseClicked
        this.AOIJFrameParent.aoiToFile(aoi);
    }//GEN-LAST:event_exportDataButtonMouseClicked

    private void dataTablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dataTablePropertyChange
        if(evt.getPropertyName().equals("tableCellEditor")){
            this.aoi.setName((String)this.dataTable.getModel().getValueAt(1, 1));
            this.AOIJFrameParent.getAOIMap().cleanMap();               
            this.AOIJFrameParent.getAOIMap().repaint(); 
            this.AOIJFrameParent.repaintVis();
            this.AOIJFrameParent.getAOIMap().saveChanges();
        }
    }//GEN-LAST:event_dataTablePropertyChange

    public void saveChanges(){
    
    } 
    
    public AOI getAOI(){
        return this.aoi;
    }

    
//public 
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cleanAOIButton;
    private javax.swing.JTable dataTable;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton exportDataButton;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton setColorButton;
    private javax.swing.JToggleButton setVisibleButton;
    // End of variables declaration//GEN-END:variables

}
