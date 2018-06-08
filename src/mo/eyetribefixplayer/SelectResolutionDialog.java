/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mo.eyetribefixplayer;

import com.theeyetribe.clientsdk.data.GazeData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static mo.analysis.NotesAnalysisPlugin.logger;

/**
 *
 * @author gustavo
 */
public class SelectResolutionDialog extends javax.swing.JDialog {

    /**
     * Creates new form SelectResolutionDialog
     */
    public SelectResolutionDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public SelectResolutionDialog(java.awt.Frame parent, boolean modal, File sourceFile) {
        super(parent, modal);
        initComponents();
        this.sourceFile = sourceFile;
        this.selectedWidth = 1280;
        this.selectedHeight = 769;
    }    

    public SelectResolutionDialog(java.awt.Frame parent, File sourceFile) {
        super(parent, ModalityType.APPLICATION_MODAL);
        initComponents();
        this.sourceFile = sourceFile;
        this.selectedWidth = 1280;
        this.selectedHeight = 769;        
    }  
    
    public SelectResolutionDialog(File sourceFile) {
        super(null, ModalityType.APPLICATION_MODAL);
        this.setLocationRelativeTo(null);
        initComponents();
        this.setLocationRelativeTo(null); 
        this.sourceFile = sourceFile;
        this.selectedWidth = 1280;
        this.selectedHeight = 769;        
    }
    
    public SelectResolutionDialog(ArrayList<GazeData> sourceData) {
        super(null, ModalityType.APPLICATION_MODAL);
        initComponents();
        //this.sourceFile = sourceFile;
        this.selectedWidth = 1280;
        this.selectedHeight = 769;  
        this.sourceData = sourceData;
    }    
    
    public void findResolution(){
        Double maxX =0.0 , maxY = 0.0;
        
        FileReader fr;
        BufferedReader br;  
        GazeData aux;
        
        try {
            
            fr = new FileReader(this.sourceFile);
            br = new BufferedReader(fr);    
            String line;
            
            line = br.readLine();
            
            while(line!=null){

                 aux = this.parseDataFromLine(line);
                 if(aux.isFixated){
                     if(aux.smoothedCoordinates.x>maxX){maxX=aux.smoothedCoordinates.x;}
                     if(aux.smoothedCoordinates.y>maxY){maxY=aux.smoothedCoordinates.y;}
                 }
                 line = br.readLine();
            }
            
            
            this.widthSpinner.setValue(maxX.intValue());
            this.heightSpinner.setValue(maxY.intValue());
            
            this.selectedWidth = maxX.intValue()+25;
            this.selectedHeight = maxY.intValue()+25;            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EyeTribeFixPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EyeTribeFixPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }
    
    private GazeData parseDataFromLine(String line) {
        String[] parts = line.split(" ");
        GazeData data = new GazeData();
        for (String part : parts) {
            
            try {

                String[] keyNValue = part.split(":");
                String k = keyNValue[0];
                String v = keyNValue[1];

                switch (k) {
                    case "fx":
                        data.isFixated = Boolean.parseBoolean(v);
                        break;
                    case "sm":
                        data.smoothedCoordinates.x = Double.parseDouble(v.split(";")[0]);
                        data.smoothedCoordinates.y = Double.parseDouble(v.split(";")[1]);
                        break;
                    case "rw":
                        data.rawCoordinates.x = Double.parseDouble(v.split(";")[0]);
                        data.rawCoordinates.y = Double.parseDouble(v.split(";")[1]);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                logger.log(
                        Level.WARNING,
                        "Error reading part <{0}> line <{1}>:{2}",
                        new Object[]{part, line, e});
            }
        }

        return data;
    }        
    
    
    public int getSelectedWidth(){
        return this.selectedWidth;
    }
    
    public int getSelectedHeight(){
        return this.selectedHeight;
    }
    
    public boolean showDialog(){
        this.setVisible(true);
        return this.accepted;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        acceptButton = new javax.swing.JButton();
        detectFixationsButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        widthSpinner = new javax.swing.JSpinner();
        heightSpinner = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Seleccione una Resolución");

        jLabel2.setText("x");

        jLabel3.setText("Personalizada");

        acceptButton.setText("Aceptar");
        acceptButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                acceptButtonMouseClicked(evt);
            }
        });

        detectFixationsButton.setText("Detectar desde el archivo de Fijaciones");
        detectFixationsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                detectFixationsButtonMouseClicked(evt);
            }
        });

        cancelButton.setText("Cancelar");
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cancelButtonMouseEntered(evt);
            }
        });

        widthSpinner.setModel(new javax.swing.SpinnerNumberModel(1280, 0, null, 11));
        widthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                widthSpinnerStateChanged(evt);
            }
        });

        heightSpinner.setModel(new javax.swing.SpinnerNumberModel(768, 0, null, 11));
        heightSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                heightSpinnerStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(widthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(heightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(detectFixationsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(acceptButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(widthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(heightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(detectFixationsButton)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acceptButton)
                    .addComponent(cancelButton))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void widthSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_widthSpinnerStateChanged
        this.selectedWidth = (int)this.widthSpinner.getValue();
    }//GEN-LAST:event_widthSpinnerStateChanged

    private void heightSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_heightSpinnerStateChanged
        this.selectedHeight = (int) this.widthSpinner.getValue();
    }//GEN-LAST:event_heightSpinnerStateChanged

    private void detectFixationsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_detectFixationsButtonMouseClicked
        this.findResolution();
    }//GEN-LAST:event_detectFixationsButtonMouseClicked

    private void acceptButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_acceptButtonMouseClicked
        this.accepted = true;
        this.dispose();
    }//GEN-LAST:event_acceptButtonMouseClicked

    private void cancelButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelButtonMouseEntered
        this.accepted = false;
        this.dispose();
    }//GEN-LAST:event_cancelButtonMouseEntered

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SelectResolutionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SelectResolutionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SelectResolutionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SelectResolutionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SelectResolutionDialog dialog = new SelectResolutionDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acceptButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton detectFixationsButton;
    private javax.swing.JSpinner heightSpinner;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSpinner widthSpinner;
    // End of variables declaration//GEN-END:variables
    private File sourceFile;
    private int selectedWidth;
    private int selectedHeight;
    private ArrayList<GazeData> sourceData;
    private boolean accepted;
}
