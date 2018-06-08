package mo.eyetribefixplayer;

import bibliothek.gui.dock.common.event.CVetoClosingEvent;
import bibliothek.gui.dock.common.event.CVetoClosingListener;
import com.theeyetribe.clientsdk.data.GazeData;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import static mo.analysis.NotesAnalysisPlugin.logger;
import mo.core.ui.dockables.DockableElement;
import mo.core.ui.dockables.DockablesRegistry;
import mo.visualization.Playable;

/**
 *
 * @author gustavo
 */
public class EyeTribeFixPlayer implements Playable {

    private long start;
    private long end;
    private EyeTribeFixControlPanel mainPanel;
    private File dataFile;
    private File mediaFile;
    private ArrayList<GazeData> data;
    private GazeData current;
    private int currentCount;
    private boolean videoIsPlaying;
    private boolean isFinalized;
    private long offset;
    private boolean sync;
    private ArrayList<Long> frames;
    private int frameCount;

    public EyeTribeFixPlayer(File dataFile, File mediaFile) {

        this.dataFile = dataFile;
        this.mediaFile = mediaFile;
        //cargar frames al arreglo

        this.data = readData(dataFile);
        this.start = this.data.get(0).timeStamp;
        this.end = this.data.get(this.data.size() - 1).timeStamp;
        this.current = this.data.get(0);
        this.currentCount = 0;
        this.videoIsPlaying = false;
        this.isFinalized = false;
        this.offset = -1;
        this.frameCount = 0;

        this.sync = false;
        String framesFileName = mediaFile.getName().substring(0, mediaFile.getName().lastIndexOf(".")) + "-frames.txt";

        File framesFile = new File(mediaFile.getParentFile(), framesFileName);
        if (framesFile != null) {
            if (framesFile.exists()) {
                this.frames = this.loadFrames(framesFile);
            }
        }

        EyeTribeFixControlPanel panel;
        try {

            panel = new EyeTribeFixControlPanel(dataFile, mediaFile);
            this.mainPanel = panel;

            SwingUtilities.invokeLater(() -> {
                try {
                    DockableElement e = new DockableElement();
                    e.add(panel);
                    e.setTitleText(this.dataFile.getName() + "-" + this.mediaFile.getName());

                    e.addVetoClosingListener(new CVetoClosingListener() {
                        @Override
                        public void closing(CVetoClosingEvent cvce) {
                        }

                        @Override
                        public void closed(CVetoClosingEvent cvce) {

                        }
                    }
                    );

                    DockablesRegistry.getInstance().addAppTemporalWideDockable(e);
                } catch (Exception ex) {
                    logger.log(Level.INFO, null, ex);
                }
            });

        } catch (FileNotFoundException ex) {
            Logger.getLogger(EyeTribeFixPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public EyeTribeFixPlayer(File dataFile) {

        this.dataFile = dataFile;
        this.mediaFile = null;

        this.data = readData(dataFile);
        this.start = this.data.get(0).timeStamp;
        this.end = this.data.get(this.data.size() - 1).timeStamp;
        this.current = this.data.get(0);
        this.currentCount = 0;
        this.videoIsPlaying = false;
        this.isFinalized = false;
        this.offset = -1;
        this.sync = false;
        this.frameCount = 0;

        EyeTribeFixControlPanel panel;
        try {

            panel = new EyeTribeFixControlPanel(dataFile);
            this.mainPanel = panel;

            SwingUtilities.invokeLater(() -> {
                try {
                    DockableElement e = new DockableElement();
                    e.add(panel);
                    e.setTitleText(this.dataFile.getName());
                    e.addVetoClosingListener(new CVetoClosingListener() {
                        @Override
                        public void closing(CVetoClosingEvent cvce) {
                        }

                        @Override
                        public void closed(CVetoClosingEvent cvce) {

                        }
                    }
                    );

                    DockablesRegistry.getInstance().addAppTemporalWideDockable(e);
                } catch (Exception ex) {
                    logger.log(Level.INFO, null, ex);
                }
            });

        } catch (FileNotFoundException ex) {
            Logger.getLogger(EyeTribeFixPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public EyeTribeFixPlayer(File dataFile, int width, int height) {

        this.dataFile = dataFile;
        this.mediaFile = null;

        this.data = readData(dataFile);
        this.start = this.data.get(0).timeStamp;
        this.end = this.data.get(this.data.size() - 1).timeStamp;
        this.current = this.data.get(0);
        this.currentCount = 0;
        this.videoIsPlaying = false;
        this.isFinalized = false;
        this.offset = -1;
        this.sync = false;
        this.frameCount = 0;

        EyeTribeFixControlPanel panel;
        try {

            panel = new EyeTribeFixControlPanel(dataFile, width, height);
            this.mainPanel = panel;

            SwingUtilities.invokeLater(() -> {
                try {
                    DockableElement e = new DockableElement();
                    e.add(panel);
                    e.setTitleText(this.dataFile.getName());
                    e.addVetoClosingListener(new CVetoClosingListener() {
                        @Override
                        public void closing(CVetoClosingEvent cvce) {
                        }

                        @Override
                        public void closed(CVetoClosingEvent cvce) {

                        }
                    }
                    );

                    DockablesRegistry.getInstance().addAppTemporalWideDockable(e);
                } catch (Exception ex) {
                    logger.log(Level.INFO, null, ex);
                }
            });

        } catch (FileNotFoundException ex) {
            Logger.getLogger(EyeTribeFixPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public EyeTribeFixPlayer(File dataFile, File mediaFile, EyeTrybeFixationAnalysisConfig config) {

        this.dataFile = dataFile;
        this.mediaFile = mediaFile;
        this.sync = false;

        this.data = readData(dataFile);
        this.start = this.data.get(0).timeStamp;
        this.end = this.data.get(this.data.size() - 1).timeStamp;
        this.current = this.data.get(0);
        this.currentCount = 0;
        this.videoIsPlaying = false;
        this.isFinalized = false;
        this.offset = -1;
        this.frameCount = 0;

        String framesFileName = mediaFile.getName().substring(0, mediaFile.getName().lastIndexOf(".")) + "-frames.txt";
        File framesFile = new File(mediaFile.getParentFile(), framesFileName);
        if (framesFile != null) {
            if (framesFile.exists()) {
                this.frames = this.loadFrames(framesFile);
            }
        }

        EyeTribeFixControlPanel panel;
        try {

            panel = new EyeTribeFixControlPanel(dataFile, mediaFile, config);
            this.mainPanel = panel;
            this.mainPanel.setFixationsColor(config.getFixationsColor());
            this.mainPanel.setFixationsLimit(config.getFixationsLimit());
            this.mainPanel.setFixationsOpacity(config.getFixationsOpacity());
            this.mainPanel.loadAoisFromFile(new File(config.getConfigFile().getParentFile(), config.getConfigFile().getName().substring(0, config.getConfigFile().getName().lastIndexOf(".")) + "_aois.csv"));

            SwingUtilities.invokeLater(() -> {
                try {
                    DockableElement e = new DockableElement();
                    e.add(panel);
                    e.setTitleText(this.dataFile.getName() + "-" + this.mediaFile.getName());
                    e.addVetoClosingListener(new CVetoClosingListener() {
                        @Override
                        public void closing(CVetoClosingEvent cvce) {
                        }

                        @Override
                        public void closed(CVetoClosingEvent cvce) {

                        }
                    }
                    );

                    DockablesRegistry.getInstance().addAppTemporalWideDockable(e);
                } catch (Exception ex) {
                    logger.log(Level.INFO, null, ex);
                }
            });

        } catch (FileNotFoundException ex) {
            Logger.getLogger(EyeTribeFixPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public EyeTribeFixPlayer(File dataFile, EyeTrybeFixationAnalysisConfig config) {

        this.dataFile = dataFile;
        this.mediaFile = null;
        this.sync = false;

        this.data = readData(dataFile);
        this.start = this.data.get(0).timeStamp;
        this.end = this.data.get(this.data.size() - 1).timeStamp;
        this.current = this.data.get(0);
        this.currentCount = 0;
        this.videoIsPlaying = false;
        this.isFinalized = false;
        this.offset = -1;
        this.frameCount = 0;

        EyeTribeFixControlPanel panel;
        try {

            panel = new EyeTribeFixControlPanel(dataFile, config);
            this.mainPanel = panel;
            this.mainPanel.setFixationsColor(config.getFixationsColor());
            this.mainPanel.setFixationsLimit(config.getFixationsLimit());
            this.mainPanel.setFixationsOpacity(config.getFixationsOpacity());
            this.mainPanel.loadAoisFromFile(new File(config.getConfigFile().getParentFile(), config.getConfigFile().getName().substring(0, config.getConfigFile().getName().lastIndexOf(".")) + "_aois.csv"));

            SwingUtilities.invokeLater(() -> {
                try {
                    DockableElement e = new DockableElement();
                    e.add(panel);
                    e.setTitleText(this.dataFile.getName());
                    e.addVetoClosingListener(new CVetoClosingListener() {
                        @Override
                        public void closing(CVetoClosingEvent cvce) {
                        }

                        @Override
                        public void closed(CVetoClosingEvent cvce) {

                        }
                    }
                    );

                    DockablesRegistry.getInstance().addAppTemporalWideDockable(e);
                } catch (Exception ex) {
                    logger.log(Level.INFO, null, ex);
                }
            });

        } catch (FileNotFoundException ex) {
            Logger.getLogger(EyeTribeFixPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public EyeTribeFixPlayer(File dataFile, int width, int height, EyeTrybeFixationAnalysisConfig config) {

        this.dataFile = dataFile;
        this.mediaFile = null;
        this.sync = false;

        this.data = readData(dataFile);
        this.start = this.data.get(0).timeStamp;
        this.end = this.data.get(this.data.size() - 1).timeStamp;
        this.current = this.data.get(0);
        this.currentCount = 0;
        this.videoIsPlaying = false;
        this.isFinalized = false;
        this.offset = -1;
        this.frameCount = 0;

        EyeTribeFixControlPanel panel;
        try {

            panel = new EyeTribeFixControlPanel(dataFile, width, height, config);
            this.mainPanel = panel;
            this.mainPanel.setFixationsColor(config.getFixationsColor());
            this.mainPanel.setFixationsLimit(config.getFixationsLimit());
            this.mainPanel.setFixationsOpacity(config.getFixationsOpacity());
            this.mainPanel.loadAoisFromFile(new File(config.getConfigFile().getParentFile(), config.getConfigFile().getName().substring(0, config.getConfigFile().getName().lastIndexOf(".")) + "_aois.csv"));

            SwingUtilities.invokeLater(() -> {
                try {
                    DockableElement e = new DockableElement();
                    e.add(panel);
                    e.setTitleText(this.dataFile.getName());
                    e.addVetoClosingListener(new CVetoClosingListener() {
                        @Override
                        public void closing(CVetoClosingEvent cvce) {
                        }

                        @Override
                        public void closed(CVetoClosingEvent cvce) {

                        }
                    }
                    );

                    DockablesRegistry.getInstance().addAppTemporalWideDockable(e);
                } catch (Exception ex) {
                    logger.log(Level.INFO, null, ex);
                }
            });

        } catch (FileNotFoundException ex) {
            Logger.getLogger(EyeTribeFixPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private ArrayList<GazeData> readData(File file) {

        FileReader fr;
        BufferedReader br;
        ArrayList<GazeData> data = new ArrayList<GazeData>();

        try {

            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;

            line = br.readLine();

            while (line != null) {

                data.add(this.parseDataFromLine(line));
                line = br.readLine();
            }

            return data;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(EyeTribeFixPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EyeTribeFixPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
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
                    case "t":
                        data.timeStamp = Long.parseLong(v);
                        break;
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

    @Override
    public long getStart() {
        return start;
    }

    @Override
    public long getEnd() {
        return end;
    }

    @Override
    public void play(long millis) {

        if (this.offset < 0) {
            this.offset = millis;
            this.mainPanel.setOffset(offset);
        }

        //if no sync -> normal
        if (!sync) {
            if (!this.videoIsPlaying) {
                this.mainPanel.playVideo();
                //this.mainPanel.seekVideo(millis - this.offset);
                this.videoIsPlaying = true;
            }
        } else {
            if (frameCount < 0) {
                frameCount = this.getActualFrameCount(millis);
            }
            if (frameCount < frames.size()) {
                if (millis == frames.get(frameCount)) {
                    this.mainPanel.playWhitLimit(frames.get(frameCount) - offset);
                    frameCount++;
                }
            }
        }
        //else -> 
        /*  // if(millis==frames.get(fcount)){
                play
                    if(millis>frames.get(fcount)){fcount++;}
        else{  pause }
        
         }
         */
        if (!this.isFinalized) {

            if (millis == this.current.timeStamp) {

            } else {

                while (millis > this.current.timeStamp) {

                    this.currentCount++;
                    if (this.currentCount < this.data.size()) {
                        this.current = this.data.get(this.currentCount);
                        this.mainPanel.addData(current);

                    } else {
                        millis = 0;
                    }
                }
            }
        } else {
            this.isFinalized = false;
            this.frameCount = 0;
            play(this.start);
        }
    }

    @Override
    public void pause() {
        if (this.videoIsPlaying & !sync) {
            this.mainPanel.pauseVideo();
            this.videoIsPlaying = false;
        }
        if (this.sync) {
            this.mainPanel.pauseVideo();
        }
    }

    @Override
    public void seek(long millis) {
        this.mainPanel.reset();
        this.current = this.getCompatibleData(data, millis);
        if (!this.videoIsPlaying) {
            this.mainPanel.playVideo();
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(EyeTribeFixPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.mainPanel.pauseVideo();
        }
        if (sync) {
            this.mainPanel.cleanLastLimit();
            this.frameCount = this.getActualFrameCount(millis);
        }
        this.mainPanel.seekVideo(millis - offset);

    }

    @Override
    public void stop() {
        this.mainPanel.stop();
        this.videoIsPlaying = false;
        this.isFinalized = true;
        this.currentCount = 0;
        this.current = this.data.get(currentCount);
        this.frameCount = 0;
        if (this.sync) {
            this.mainPanel.cleanLastLimit();
        }
    }

    ////////////////////////////////7
    private long t;

    public long getT() {
        return t;
    }

    public void sumT(long i) {
        t = t + i;
    }

    public GazeData getCompatibleData(ArrayList<GazeData> data, long millis) {

        for (int i = 0; i < data.size(); i++) {

            this.mainPanel.addDataWithoutAois(data.get(i));
            if (data.get(i).timeStamp >= millis) {
                this.currentCount = i;
                return data.get(i);
            }
        }

        return null;
    }

    public int getActualFrameCount(Long currentTime) {
        int i = 0;
        for (Long frame : frames) {
            if (currentTime <= frame) {
                return i;
            }
            i++;
        }
        return i;
    }

    public ArrayList<BufferedImage> generateHeatMaps(ArrayList<GazeData> data) {

        ArrayList<BufferedImage> maps = new ArrayList<BufferedImage>();
        int infLimit = 0;
        ArrayList<Point> points = new ArrayList<Point>();

        for (int i = 10; i <= 100; i = i + 10) {

            int supLimit = (data.size() * i) / 100;

            for (int j = infLimit; j < supLimit; j++) {

                GazeData g = data.get(j);

                final int x = (int) new Double((g.smoothedCoordinates.x / 1920) * 1920).intValue();
                final int y = (int) new Double((g.smoothedCoordinates.y / 1080) * 1200).intValue();

                if (x == 0 && y == 0) {
                } else {
                    final Point p = new Point(x, y);
                    points.add(p);
                }

                infLimit = j;
            }

        }

        return maps;

    }

    @Override
    public void sync(boolean bln) {
        this.sync = bln;
        if (this.videoIsPlaying && this.sync) {
            this.mainPanel.pauseVideo();
            this.mainPanel.cleanLastLimit();
            this.videoIsPlaying = false;
        }
    }

    //setters and getters
    public Color getFixationsColor() {
        return this.mainPanel.getFixationsColor();
    }

    public double getFixationsOpacity() {
        return this.mainPanel.getFixationsOpacity();
    }

    public int getFixationsLimit() {
        return this.mainPanel.getFixationsLimit();
    }

    public void setFixationsColor(Color color) {
        this.mainPanel.setFixationsColor(color);
    }

    public void setFixationsOpacity(Double opacity) {
        this.mainPanel.setFixationsOpacity(opacity);
    }

    public void setFixationsLimit(int limit) {
        this.mainPanel.setFixationsLimit(limit);
    }

    private ArrayList<Long> loadFrames(File file) {

        ArrayList<Long> framesReads = new ArrayList<Long>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line = reader.readLine();

            while (line != null) {

                framesReads.add(Long.parseLong(line));
                line = reader.readLine();
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(EyeTribeFixPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EyeTribeFixPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return framesReads;
    }

}
