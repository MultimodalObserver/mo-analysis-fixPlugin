/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mo.eyetribefixplayer;

import com.theeyetribe.clientsdk.data.GazeData;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javafx.embed.swing.JFXPanel;
import javax.swing.JPanel;

/**
 *
 * @author gustavo
 */
public class FixationMap {
    
    private BufferedImage map;
    private Color color;
    private Graphics2D graphics;
    private int opacity;

    private int initialRadio;
    private int radioIncrement;
    
    private Fixation lastFixation;
    private Fixation firstFixation;
    private int fixationCount;
    private Double lastSaccadeAmplitude;
    private Double lastLastsaccadeAmplitude;
    private Double saccade3;
    
    private Double originalWidth;
    private Double originalHeight;
    private Double width;
    private Double height;
    
    private boolean fixationIsActive;
    private int limit;
    private boolean fixationAdded;
    private int directionChanges;
    private int regresionCount;
    
    public FixationMap(int width, int height, Color color){
        
        this.map = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.color = color;
        this.graphics = this.map.createGraphics();
        this.graphics.setColor(color);
        this.originalWidth = new Double(width);
        this.originalHeight = new Double(height);
        this.width = this.originalWidth;
        this.height = this.originalHeight;
        this.lastFixation = null;
        this.fixationCount = 0;
        this.fixationIsActive = false;
        this.initialRadio = 10;
        this.radioIncrement = 2;
        this.opacity = 100;
        this.limit = -1;
        this.fixationAdded = false;
        this.directionChanges = 0;
        this.lastLastsaccadeAmplitude = 0.0;
        this.saccade3 = 0.0;
        this.lastSaccadeAmplitude = 0.0;
        this.regresionCount = 0;
    }
    
    
    public FixationMap(Double width, Double height, Color color){
        
        this.map = new BufferedImage(width.intValue(), height.intValue(), BufferedImage.TYPE_INT_ARGB);
        this.color = color;
        this.graphics = this.map.createGraphics();
        this.graphics.setColor(color);
        this.originalWidth = width;
        this.originalHeight = height;
        this.width = this.originalWidth;
        this.height = this.originalHeight;
        this.lastFixation = null;
        this.fixationCount = 0;
        this.fixationIsActive = false;
        this.initialRadio = 10;
        this.radioIncrement = 2;        
        this.opacity = 100;
        this.limit = -1 ;
        this.fixationAdded = false;
        this.directionChanges = 0;
        this.lastLastsaccadeAmplitude = 0.0;
        this.saccade3 = 0.0;      
        this.lastSaccadeAmplitude = 0.0;
        this.regresionCount = 0;
    }    
    
    public void setInitialRadio(int initialRadio){
        this.initialRadio = initialRadio;
    }
    
    
    public void addData(GazeData data){
        
        if(data.isFixated){
            if(this.lastFixation == null||!this.fixationIsActive){
                addFixation(data);
                this.lastLastsaccadeAmplitude = this.lastSaccadeAmplitude;
                this.lastSaccadeAmplitude = this.fixationsDistance(lastFixation, lastFixation.getPrevious());
                if(this.lastFixation!=null){
                    Fixation lastLastFixation = this.lastFixation.getPrevious();
                    if(lastLastFixation!=null){
                        Fixation lastLastLastFixation = lastLastFixation.getPrevious();
                        if(lastLastLastFixation!=null){
                        if(this.isDirectionalChange(lastLastLastFixation,
                                                  lastLastFixation,
                                                  this.lastFixation,
                                                  this.lastSaccadeAmplitude, this.lastLastsaccadeAmplitude)){
                                                    this.directionChanges++;
                                                    if(this.lastSaccadeAmplitude<this.lastLastsaccadeAmplitude&&lastLastFixation!=null){
                                                        if(this.lastFixation.getOriginalX()<lastLastFixation.getOriginalX()){
                                                         this.regresionCount ++ ;
                                                        }
                                                    }
                                                   }
                        
                        }
                    }
                }
                
                this.fixationIsActive = true;
            }
            else{
                incrementCurrentFixation(data);
            }
            
        }else{
            this.fixationIsActive = false;
        }
        
    }
    
    public void addFixation(GazeData data){
        
        this.fixationAdded = true;
        this.fixationCount ++ ;
        
        if(this.lastFixation==null){
            this.lastFixation = new Fixation(
                                data.smoothedCoordinates.x/this.originalWidth, //deberian ser orginal wiidth y height
                                data.smoothedCoordinates.y/this.originalHeight,
                                data.smoothedCoordinates.x,
                                data.smoothedCoordinates.y,
                                data.timeStamp
                                );
            if(this.limit == -1){
                this.lastFixation.paint(map, new Color(color.getRed(),color.getGreen(),color.getBlue(),opacity));
            }else{
                this.cleanMap();
                this.lastFixation.paintLast(map, new Color(color.getRed(),color.getGreen(),color.getBlue(),opacity), opacity, limit);
            }
        }
        else{
            this.lastFixation.setNext( new Fixation(
                                    data.smoothedCoordinates.x/this.originalWidth,
                                    data.smoothedCoordinates.y/this.originalHeight,
                                    data.smoothedCoordinates.x,
                                    data.smoothedCoordinates.y,                    
                                    data.timeStamp
                                    )
            );
            this.lastFixation= this.lastFixation.getNext();
            if(this.limit == -1){
                this.lastFixation.paint(map, new Color(color.getRed(),color.getGreen(),color.getBlue(),opacity));
            }
            else{
                this.cleanMap();
                this.lastFixation.paintLast(map, new Color(color.getRed(),color.getGreen(),color.getBlue(),opacity), opacity, limit);
            }
        }
        
    }
    
    private void incrementCurrentFixation(GazeData data){
        this.fixationAdded = false;
        this.lastFixation.increment(1);
        this.lastFixation.setEndUixTimeStamp(data.timeStamp);
        this.lastFixation.update(map, new Color(color.getRed(),color.getGreen(),color.getBlue(),opacity/4));
    }
    
    private Double getRelativeX(int x){
        return x*this.width/this.originalWidth;    
    }
    
    private Double getRelativeY(int y){
        return y*this.height/this.originalWidth;
    }
    
    private Double getRelativeX(Double x){
        return x*this.width/this.originalWidth;    
    }
    
    private Double getRelativeY(Double y){
        return y*this.height/this.originalWidth;
    }    
    
    
    public void resize(int width, int height){
     //   this.map = resizeImage(this.map, width, height);
     
     if(this.width != width || this.height != height){
        this.width = new Double(width);
        this.height = new Double(height);
     
     // this.cleanMap();
        repaint();
     }
    }
    
    public void repaint(){
    
        this.cleanMap();
     
        if(this.lastFixation!=null){

            if(this.limit==-1){
                Fixation fix = this.lastFixation;
                if(fix == null){return;}

                while(fix!=null){

                        fix.paint(this.map, new Color(color.getRed(),color.getGreen(),color.getBlue(),opacity));
                        for(int i=0; i<fix.getIncrement(); i++){
                            fix.update2(this.map, new Color(color.getRed(),color.getGreen(),color.getBlue(), opacity/4),i);
                        }     
                        fix =  fix.getPrevious();
                }
            }else{
                this.lastFixation.paintLast(map, new Color(color.getRed(),color.getGreen(),color.getBlue(),opacity), opacity, limit);
            }
        }
        
    }
    
    
    public void paintToPanel(JPanel panel){
        
        Graphics2D panelGraphics = (Graphics2D)panel.getGraphics();
        panelGraphics.drawImage(map, 0, 0, panel);
    }
    
    public void paintToPanel(JFXPanel panel){
        
        Graphics2D panelGraphics = (Graphics2D)panel.getGraphics();
        panelGraphics.drawImage(map, 0, 0, panel);
    }    
    
    public void paintToImage(BufferedImage image){
        Graphics2D imageGraphics = (Graphics2D) image.getGraphics();
        if(imageGraphics == null){imageGraphics= image.createGraphics();}
        imageGraphics.drawImage(map, 0, 0, null);
    
    }

    private BufferedImage resizeImage(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        //g2d.dispose();
        return dimg;
    }
    
    public void cleanMap(){
        this.map =  new BufferedImage(this.width.intValue(), this.height.intValue(), BufferedImage.TYPE_INT_ARGB);
    }

    public Fixation getLastFixation() {
        return lastFixation;
    }
    
    public BufferedImage getMap() {
        return map;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        this.repaint();
    }    
    
    public void setOpacity(Double opacity){
        this.opacity = new  Double(255 * opacity).intValue();
        this.repaint();
    }
    
    public Double getOpacity(){
        return new Double(this.opacity)/255;
    }

    public void setLimit(int limit) {
        this.limit = limit;
        
        if(limit==-1&&this.lastFixation!=null){
         //this.cleanMap();
         this.repaint();
        }
    }
   
    public int getLimit(){
        return this.limit;
    }
    
    public void reset(){
        this.cleanMap();
        this.lastFixation = null;
        this.firstFixation = null;
    }
    
    private Double fixationsDistance(Fixation fix1, Fixation fix2){
        if(fix1==null || fix2 == null){return 0.0;}
        Double distance;
        Double distx = fix1.getOriginalX() - fix2.getOriginalX();
        Double disty = fix2.getOriginalY() - fix2.getOriginalY();
        distance = Math.sqrt(distx*distx + disty*disty);
        return distance;
    
    }
    
    
    public boolean fixationWasAdded(){
        return this.fixationAdded;
    }

    public int getFixationCount() {
        return this.fixationCount;
    }

    public Double getLastSaccadeAmplitude() {
        return lastSaccadeAmplitude;
    }
 
    
    private Boolean isDirectionalChange(Fixation fix1, Fixation fix2, Fixation fix3, Double saccade1, Double saccade2){
    
        Double mod = saccade1* saccade2;
        //fix1 is pivote
        Double xPiv = fix1.getOriginalX();
        Double yPiv = fix1.getOriginalY();
        Double x = (fix2.getOriginalX() - xPiv)*(fix3.getOriginalX() - xPiv);
        Double y = (fix2.getOriginalY() - yPiv)*(fix3.getOriginalY() - yPiv);
        
        Double cos = (x+y)/mod;
        
        if(x+y<0){
            return true;
        }else{
            return false;
        }
        
    }
    
    private Boolean isDirectionChange(Double r1, Double r2){
    
        Double cos;
        if(Math.atan(r1/r2)<0){return true;};
        return false;
    }
    
    public int getDirectionChanges(){
        return this.directionChanges;
    }
    

    
}
