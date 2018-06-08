package mo.eyetribefixplayer;

import com.theeyetribe.clientsdk.data.GazeData;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import java.io.File;
import javafx.application.Platform;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaMarkerEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;


/**
 *
 * @author gustavo
 */
public class JFXPanelFixations extends JFXPanel {

    private Color fixationsColor;

    private int originalWidth;
    private int originalHeight;

    private int propotionCase;

    private DoubleProperty mvw;
    private DoubleProperty mvh;

    private MediaPlayer player;
    private FixationMap fixationMap;

    private int initXDrag;
    private int initYDrag;
    private int endXDrag;
    private int endYDrag;
    private boolean isDraged;

    private AOI aoiCreating;
    private AOIMap aoiMap;
    private long offset;
    private AOISFrame aoiFrame;

    private int realWidth;
    private int realHeight;
    private Media mf;
    
    private boolean useVideo;
    private ObservableMap<String, Duration> markers;

    public JFXPanelFixations() throws FileNotFoundException {

        super();
        this.aoiCreating = null;
        this.offset = -1;
        this.aoiFrame = null;

        /////////////////////////////////////////////////7
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelMouseDragged(evt);
            }
        });

        addMouseListener(new java.awt.event.MouseAdapter() {
            /*    public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }*/
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                panelMouseReleased(evt);
            }
        });
        
        
        

    }

    ///////////////////////////////////////////////////////////
    //paint and importants methods
    ////////////////////////////////////////////////////////////
    @Override
    public void paint(Graphics g) {

        correctSize();
        super.paint(g);

        if (fixationMap == null) {
            this.fixationMap = new FixationMap(this.originalWidth, this.originalHeight, this.fixationsColor);
        }

        g.drawImage(fixationMap.getMap(), 0, 0, this);

        if (this.aoiCreating != null) {
            g.setColor(this.aoiCreating.getColor());
            g.fillRect(new Double(this.aoiCreating.getRelativeX() * this.realWidth).intValue(),//new Double(this.aoiCreating.getRelativeX()*this.getWidth()).intValue(),
                    new Double(this.aoiCreating.getRelativeY() * this.realHeight).intValue(),//new Double(this.aoiCreating.getRelativeY()*this.getHeight()).intValue(),
                    new Double(this.aoiCreating.getRelativeWidth() * this.realWidth).intValue(),//new Double(this.aoiCreating.getRelativeWidth()*this.getWidth()).intValue(),
                    new Double(this.aoiCreating.getReltiveHeight() * this.realHeight).intValue());//new Double(this.aoiCreating.getReltiveHeight()*this.getHeight()).intValue());
        }

        if (this.aoiMap != null) {
            g.drawImage(this.aoiMap.getMap(), 0, 0, this);
        }

        correctSize();
        this.fixationMap.resize(this.realWidth, this.realHeight);//this.fijationMap.resize(this.getWidth(), this.getHeight());

        if (this.aoiMap != null) {
            this.aoiMap.resize(this.realWidth, this.realHeight);    //this.aoiMap.resize(this.getWidth(), this.getHeight());    
        }
        
    }
    
    public void paintWhithoutVideo(Graphics g) {

        correctSize();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        if (fixationMap == null) {
            this.fixationMap = new FixationMap(this.originalWidth, this.originalHeight, this.fixationsColor);
        }

        g.drawImage(fixationMap.getMap(), 0, 0, this);

        if (this.aoiCreating != null) {
            g.setColor(this.aoiCreating.getColor());
            g.fillRect(new Double(this.aoiCreating.getRelativeX() * this.realWidth).intValue(),//new Double(this.aoiCreating.getRelativeX()*this.getWidth()).intValue(),
                    new Double(this.aoiCreating.getRelativeY() * this.realHeight).intValue(),//new Double(this.aoiCreating.getRelativeY()*this.getHeight()).intValue(),
                    new Double(this.aoiCreating.getRelativeWidth() * this.realWidth).intValue(),//new Double(this.aoiCreating.getRelativeWidth()*this.getWidth()).intValue(),
                    new Double(this.aoiCreating.getReltiveHeight() * this.realHeight).intValue());//new Double(this.aoiCreating.getReltiveHeight()*this.getHeight()).intValue());
        }

        if (this.aoiMap != null) {
            g.drawImage(this.aoiMap.getMap(), 0, 0, this);
        }

        correctSize();
        this.fixationMap.resize(this.realWidth, this.realHeight);//this.fijationMap.resize(this.getWidth(), this.getHeight());

        if (this.aoiMap != null) {
            this.aoiMap.resize(this.realWidth, this.realHeight);    //this.aoiMap.resize(this.getWidth(), this.getHeight());    
        }
        
    }    
    

    public void addVideo(Media media) {

        Platform.setImplicitExit(false);
        mf = media;
        
        player = new MediaPlayer(media);
        MediaView mediaView = new MediaView(player);

        Scene scene = new Scene(new Group(mediaView), 1920, 1080);
        this.setScene(scene);
        this.setSize(this.getSize());

        player.setVolume(0.5);
        player.setCycleCount(MediaPlayer.INDEFINITE);

        mvw = mediaView.fitWidthProperty();
        mvh = mediaView.fitHeightProperty();

        mvw.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        mvh.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));

        mediaView.setPreserveRatio(true);
        
        while (media.getWidth() == 0 || media.getHeight() == 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(JFXPanelFixations.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("getting video dimensions: " +media.getWidth() + "x" + media.getHeight());
        }

        this.originalWidth = media.getWidth();
        this.originalHeight = media.getHeight();
        this.propotionCase = this.getProportionCase(this.originalWidth, this.originalHeight);
        this.fixationMap = new FixationMap(this.originalWidth, this.originalHeight, Color.BLUE);
        this.aoiMap = new AOIMap(this.originalWidth, this.originalWidth);
        this.aoiMap.setOffset(offset);
        this.useVideo = true;
        
        this.markers = media.getMarkers();
        initSyncControl();        
    }

    public void addData(GazeData data) {

        if (data.state != GazeData.STATE_TRACKING_FAIL
                && data.state != GazeData.STATE_TRACKING_LOST
                && !(data.smoothedCoordinates.x == 0 && data.smoothedCoordinates.y == 0)
                && data.smoothedCoordinates.x > 0
                && data.smoothedCoordinates.y > 0) {
            this.fixationMap.addData(data);

            this.correctSize();

            Double actualX;
            Double actualY;

            actualX = (data.smoothedCoordinates.x / this.originalWidth) * this.realWidth;
            actualY = (data.smoothedCoordinates.y / this.originalHeight) * this.realHeight;

            if (this.aoiMap != null) {
                if (this.fixationMap.fixationWasAdded()) {

                    AOI ax = this.aoiMap.getAOI(actualX.intValue(),
                            actualY.intValue());

                    if (ax != null) {
                        if (ax.getLastFixation() == null) {
                            if (this.fixationMap != null) {
                                if (this.fixationMap.getLastFixation() != null) {
                                    ax.addFixation(this.fixationMap.getLastFixation());
                                }
                            }
                        } else {
                            if (ax.getLastFixation().getId() < this.fixationMap.getLastFixation().getId()) {
                                ax.addFixation(this.fixationMap.getLastFixation());
                            }
                        }
                    }
                }
            }
        }
        if(!this.useVideo){
            //this.paintWhithoutVideo(this.getGraphics());
            repaint();
        }
    }   

    public void addDataWithoutAois(GazeData data) {
        this.fixationMap.addData(data);
    }

    public void correctSize() {

        if (this.propotionCase == -1) {
            Double changeValue = new Double(this.getHeight()) / this.originalHeight;
            this.realWidth = (int) (changeValue * this.originalWidth);
            this.realHeight = this.getHeight();
        }

        if (this.propotionCase == 1) {
            Double changeValue = new Double(this.getWidth()) / this.originalWidth;
            this.realHeight = (int) (changeValue * this.getHeight());
            this.realWidth = this.getWidth();
        }

    }

    /////////////////////////////////////////////
    ////////////////auxiliar methods
    //////////////////////////////////////////////
    private int getProportionCase(int width, int height) {

        if (width > height) {
            return -1;
        }
        if (width == height) {
            return 0;
        }
        if (width < height) {
            return 1;
        }
        return -2;
    }

    ////////////////////////////////////////////////////////
    //events control/////////////////////////////////
    ///////////////////////////////////////////////////////7
    public void panelMouseDragged(java.awt.event.MouseEvent evt) {

        if (!this.isDraged) {
            this.initXDrag = evt.getX();
            this.initYDrag = evt.getY();
            isDraged = true;
            this.aoiCreating = new AOI(
                    new Double(initXDrag) / this.realWidth,//new Double(initXDrag)/this.getWidth(),
                    new Double(initYDrag) / this.realHeight,//new Double(initYDrag)/this.getHeight(),
                    0.0,
                    0.0,
                    new Color(this.fixationsColor.getRed(), this.fixationsColor.getGreen(), this.fixationsColor.getBlue(), 70)
            );
        }

        this.endXDrag = evt.getX();
        this.endYDrag = evt.getY();

        int widthDrag = endXDrag - initXDrag;
        int heightDrag = endYDrag - initYDrag;

        this.aoiCreating.setRelativeSize(new Double(widthDrag) / this.realWidth,//this.getWidth(),
                new Double(heightDrag) / this.realHeight//this.getHeight()
        );

        BufferedImage fin = new BufferedImage(this.realWidth, this.realHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) fin.getGraphics();
        paint(g2);

        Graphics2D g = (Graphics2D) this.getGraphics();

        if (widthDrag > 0 && heightDrag > 0) {
            g2.setColor(this.aoiCreating.getColor());
            g2.fillRect(new Double(this.aoiCreating.getRelativeX() * this.realWidth).intValue(),//new Double(this.aoiCreating.getRelativeX()*this.getWidth()).intValue(),
                    new Double(this.aoiCreating.getRelativeY() * this.realHeight).intValue(),//new Double(this.aoiCreating.getRelativeY()*this.getHeight()).intValue(),
                    new Double(this.aoiCreating.getRelativeWidth() * this.realWidth).intValue(), //new Double(this.aoiCreating.getRelativeWidth()*this.getWidth()).intValue(),
                    new Double(this.aoiCreating.getReltiveHeight() * this.realHeight).intValue());//new Double(this.aoiCreating.getReltiveHeight()*this.getHeight()).intValue());
        }

        if (widthDrag < 0 && heightDrag < 0) {

            this.aoiCreating.setRelativeXY(new Double(this.endXDrag) / this.realWidth, new Double(this.endYDrag) / this.realHeight);
            this.aoiCreating.setRelativeSize((-1) * new Double(widthDrag) / this.realWidth,//this.getWidth(),
                    (-1) * new Double(heightDrag) / this.realHeight//this.getHeight()
            );

            g2.setColor(this.aoiCreating.getColor());
            g2.fillRect(new Double(this.aoiCreating.getRelativeX() * this.realWidth).intValue(),//new Double(this.aoiCreating.getRelativeX()*this.getWidth()).intValue(),
                    new Double(this.aoiCreating.getRelativeY() * this.realHeight).intValue(),//new Double(this.aoiCreating.getRelativeY()*this.getHeight()).intValue(),
                    new Double(this.aoiCreating.getRelativeWidth() * this.realWidth).intValue(), //new Double(this.aoiCreating.getRelativeWidth()*this.getWidth()).intValue(),
                    new Double(this.aoiCreating.getReltiveHeight() * this.realHeight).intValue());//new Double(this.aoiCreating.getReltiveHeight()*this.getHeight()).intValue());                         
        }

        if (widthDrag < 0 && heightDrag < 0) {

            this.aoiCreating.setRelativeXY(new Double(this.endXDrag) / this.realWidth, new Double(this.endYDrag) / this.realHeight);
            this.aoiCreating.setRelativeSize((-1) * new Double(widthDrag) / this.realWidth,//this.getWidth(),
                    (-1) * new Double(heightDrag) / this.realHeight//this.getHeight()
            );

            g2.setColor(this.aoiCreating.getColor());
            g2.fillRect(new Double(this.aoiCreating.getRelativeX() * this.realWidth).intValue(),//new Double(this.aoiCreating.getRelativeX()*this.getWidth()).intValue(),
                    new Double(this.aoiCreating.getRelativeY() * this.realHeight).intValue(),//new Double(this.aoiCreating.getRelativeY()*this.getHeight()).intValue(),
                    new Double(this.aoiCreating.getRelativeWidth() * this.realWidth).intValue(), //new Double(this.aoiCreating.getRelativeWidth()*this.getWidth()).intValue(),
                    new Double(this.aoiCreating.getReltiveHeight() * this.realHeight).intValue());//new Double(this.aoiCreating.getReltiveHeight()*this.getHeight()).intValue());

        }

        if (widthDrag < 0 && heightDrag > 0) {
            this.aoiCreating.setRelativeXY(new Double(this.endXDrag) / this.realWidth, new Double(this.initYDrag) / this.realHeight);
            this.aoiCreating.setRelativeSize((-1) * new Double(widthDrag) / this.realWidth,//this.getWidth(),
                    new Double(heightDrag) / this.realHeight//this.getHeight()
            );

            g2.setColor(this.aoiCreating.getColor());
            g2.fillRect(new Double(this.aoiCreating.getRelativeX() * this.realWidth).intValue(),//new Double(this.aoiCreating.getRelativeX()*this.getWidth()).intValue(),
                    new Double(this.aoiCreating.getRelativeY() * this.realHeight).intValue(),//new Double(this.aoiCreating.getRelativeY()*this.getHeight()).intValue(),
                    new Double(this.aoiCreating.getRelativeWidth() * this.realWidth).intValue(), //new Double(this.aoiCreating.getRelativeWidth()*this.getWidth()).intValue(),
                    new Double(this.aoiCreating.getReltiveHeight() * this.realHeight).intValue());//new Double(this.aoiCreating.getReltiveHeight()*this.getHeight()).intValue());

        }

        if (widthDrag > 0 && heightDrag < 0) {

            this.aoiCreating.setRelativeXY(new Double(this.initXDrag) / this.realWidth, new Double(this.endYDrag) / this.realHeight);
            this.aoiCreating.setRelativeSize(new Double(widthDrag) / this.realWidth,//this.getWidth(),
                    (-1) * new Double(heightDrag) / this.realHeight//this.getHeight()
            );

            g2.setColor(this.aoiCreating.getColor());
            g2.fillRect(new Double(this.aoiCreating.getRelativeX() * this.realWidth).intValue(),//new Double(this.aoiCreating.getRelativeX()*this.getWidth()).intValue(),
                    new Double(this.aoiCreating.getRelativeY() * this.realHeight).intValue(),//new Double(this.aoiCreating.getRelativeY()*this.getHeight()).intValue(),
                    new Double(this.aoiCreating.getRelativeWidth() * this.realWidth).intValue(), //new Double(this.aoiCreating.getRelativeWidth()*this.getWidth()).intValue(),
                    new Double(this.aoiCreating.getReltiveHeight() * this.realHeight).intValue());//new Double(this.aoiCreating.getReltiveHeight()*this.getHeight()).intValue());

        }

        g.drawImage(fin, 0, 0, this);
    }

    public void panelMouseReleased(java.awt.event.MouseEvent evt) {
        if (this.aoiCreating != null) {
            this.isDraged = false;
            if (this.aoiMap == null) {
                this.aoiMap = new AOIMap(this.realWidth, this.realHeight);//this.aoiMap = new AOIMap(this.getWidth(),this.getHeight());
                this.aoiMap.setOffset(this.offset);
            }
            this.aoiCreating.setId(this.aoiMap.getAOICount() + 1);
            this.aoiCreating.setOffset(offset);
            this.aoiMap.addAOI(aoiCreating);
            if (this.aoiFrame != null) {
                this.aoiFrame.addAoi(aoiCreating);
            }

            Graphics2D g = (Graphics2D) this.getGraphics();
            g.setColor(new Color(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 0.5f));
            g.setFont(new Font("default", Font.BOLD, 20));

            int x = new Double(this.aoiCreating.getRelativeX() * this.realWidth).intValue();
            int y = new Double(this.aoiCreating.getRelativeY() * this.realHeight).intValue();
            int width = new Double(this.aoiCreating.getRelativeWidth() * this.realWidth).intValue();
            int height = new Double(this.aoiCreating.getReltiveHeight() * this.realHeight).intValue();
            int centerX = (x + width / 2);
            int centerY = (y + height / 2);
            g.drawString(String.valueOf(this.aoiCreating.getId()), centerX, centerY);
            this.aoiCreating = null;
        }
    }

/////////////////////////////////////////////////////////////////
/////////////////reproduction control
////////////////////////////////////////////////////////////////
    public void play() {
        this.playVideo();
    }

    public void playVideo() {
        if(this.player!=null){
            this.player.play();
        }
    }

    public void pauseVideo() {
        if(this.player!=null){        
            this.player.pause();
        }
    }

    public void pause() {
        this.pauseVideo();
    }

    public void stop() {
        if(this.player!=null){
            this.player.stop();
        }
        this.reset();
    }

    public void seek(long millis) {
        if(this.player!=null){
            this.player.seek(Duration.millis(millis));
        }
    }

    public void reset() {
        this.fixationMap.reset();
        this.aoiMap.reset();
    }

    public void AOItoFile(AOI aoi, File outputDir) {

        Date d = new Date(Calendar.YEAR);
        Date now = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss.SSS");

        String reportDate = df.format(now);
        //String path = outputDir.getPath()+"\\"+d+"-"+Calendar.MONTH+"-"+Calendar.DAY_OF_MONTH+"_"+Calendar.HOUR_OF_DAY+"."+Calendar.MINUTE+"."+Calendar.SECOND+".txt";
        String path = outputDir.getPath() + "\\" + reportDate + ".txt";
        //File outputFile = new File(path);
        File outputFile = new File(outputDir,reportDate+"_AOI"+ aoi.getId() +".txt");
        if(!outputFile.exists()){try {
            outputFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(JFXPanelFixations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            BufferedWriter outputWritter = new BufferedWriter(new FileWriter(outputFile));
            outputWritter.write("XY:" + this.originalWidth * aoi.getRelativeX() + "," + this.originalHeight * aoi.getRelativeY() + "\n");
            outputWritter.write("WidthHeight:" + this.originalWidth * aoi.getRelativeWidth() + "," + this.realHeight * aoi.getReltiveHeight() + "\n");
            outputWritter.write("FC:" + aoi.getFixationCount() + "\n");
            outputWritter.write("FD:" + aoi.getFixationDensity().toString() + "\n");
            outputWritter.write("TTF:" + aoi.getTimeToFirstFixation() + "\n");
            outputWritter.write("BFB:" + aoi.getFixationsBefore() + "\n");
            outputWritter.write("FFD:" + aoi.getFirstFixationDuration() + "\n");
            outputWritter.write("TFD:" + aoi.getTotalFixationDuration() + "\n");
            outputWritter.close();

        } catch (IOException ex) {
            Logger.getLogger(JFXPanelFixations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initSyncControl(){
        
    player.setOnMarker(new javafx.event.EventHandler<MediaMarkerEvent>() {
      @Override
      public void handle(final MediaMarkerEvent event) {
        Platform.runLater(new Runnable() {
          @Override public void run() {
              player.pause();
          }
        });
      }
    });
    
    }
    
    public void setNextPauseTime(Long millis){
        markers.put("nextPause", Duration.millis(millis));
    }
    
    public void playToLimit(Long millis){
        this.markers.clear();
        setNextPauseTime(millis);
        //if(player.getStatus().name().equals(Status.PLAYING.name())){
            this.player.play();
        //}
    }
    
    public void cleanLastPlayLimit(){
        this.markers.clear();
    }
    
    
    
    /////////////////////////////////////////
    ///////setters and getters
    //////////////////////////////////////
    public void setFixationsColor(Color color) {
        this.fixationsColor = color;
        this.fixationMap.setColor(color);
    }
    
    public Color getFixationColor(){
        return this.fixationsColor;
    }

    public void setFixationsOpacity(Double value) {
        this.fixationMap.setOpacity(value);
    }
    
    public Double getFixationsOpacity(){
        return this.fixationMap.getOpacity();
    }
    
    public FixationMap getFixationMap() {
        return fixationMap;
    }

    public void setColorFixations(Color color) {
        this.fixationsColor = color;
    }

    public AOIMap getAOIMap() {
        return this.aoiMap;
    }

    public void setOffset(long offset) {
        this.offset = offset;
        if (this.aoiMap != null) {
            this.aoiMap.setOffset(offset);
        }
    }

    public AOISFrame toDoAoisFrame() {
        this.aoiFrame = new AOISFrame(this, this.aoiMap, null);
        this.aoiFrame.setVisible(true);
        return this.aoiFrame;
    }

    public AOISFrame toDoAoisFrame(File file) {
        this.aoiFrame = new AOISFrame(this, this.aoiMap, file);
        this.aoiFrame.setVisible(true);
        return this.aoiFrame;
    }

    public int getRealWidth() {
        return this.realWidth;
    }
    
    public int getRealHeight() {
        return this.realHeight;
    }    
    
    public void useVideo(boolean useVideo){
        if(!useVideo){
            
            Platform.setImplicitExit(false); 
        
            this.originalWidth = 1028;
            this.originalHeight = 720;
            this.propotionCase = this.getProportionCase(this.originalWidth, this.originalHeight);
            this.fixationMap = new FixationMap(this.originalWidth, this.originalHeight, Color.BLUE);
            this.aoiMap = new AOIMap(this.originalWidth, this.originalWidth);
            this.aoiMap.setOffset(offset); 
            this.useVideo = false;
            

        }
    }
    
    public void useVideo(boolean useVideo, int width, int height){
        if(!useVideo){
            
            Platform.setImplicitExit(false); 
        
            this.originalWidth = width;
            this.originalHeight = height;
            this.propotionCase = this.getProportionCase(this.originalWidth, this.originalHeight);
            this.fixationMap = new FixationMap(this.originalWidth, this.originalHeight, Color.BLUE);
            this.aoiMap = new AOIMap(this.originalWidth, this.originalWidth);
            this.aoiMap.setOffset(offset); 
            this.useVideo = false;
            this.setPreferredSize(new Dimension(width, height));
            

        }
    }    

    public Media getMf() {
        return mf;
    }
    

}
