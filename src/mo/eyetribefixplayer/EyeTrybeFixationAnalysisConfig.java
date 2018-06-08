package mo.eyetribefixplayer;

import bibliothek.util.xml.XElement;
import bibliothek.util.xml.XIO;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import mo.analysis.PlayableAnalyzableConfiguration;
import mo.organization.Configuration;
import mo.organization.Participant;
import mo.organization.ProjectOrganization;
import mo.visualization.Playable;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.logging.Logger;
import static mo.analysis.NotesAnalysisPlugin.logger;

/**
 *
 * @author gustavo
 */
public class EyeTrybeFixationAnalysisConfig implements PlayableAnalyzableConfiguration {

    //MO data
    String name;
    private final String[] creators = {"mo.eyetracker.capture.TheEyeTribeRecorder"};
    private ArrayList<File> files;
    private EyeTribeFixPlayer player;
    private File dir;
    private File mediaFile;
    private File projectFolder;

    //preferences data
    private Color fixationsColor;
    private Double fixationsOpacity;
    private int fixationsLimit;
    private File aoisFile;
    private File configFile;

    public EyeTrybeFixationAnalysisConfig(String name) {

        //deafult settings
        this.name = name;
        this.files = new ArrayList<File>();
        this.fixationsColor = Color.BLUE;
        this.fixationsLimit = -1;
        this.fixationsOpacity = 0.39215686274509803;
        this.aoisFile = null;
        this.configFile = null;
        this.projectFolder = null;
        //this.dialogBundle = java.util.ResourceBundle.getBundle("i18n/mo/analysis/fixPlugin/fixPLuginDialogs");

    }

    public EyeTrybeFixationAnalysisConfig() {
        this.files = new ArrayList<File>();
    }

    @Override
    public void setupAnalysis(File stageFolder, ProjectOrganization org, Participant p) {

        File[] dirs = org.getLocation().listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                if (name.equals(p.folder)) {
                    return true;
                }
                return false;
            }
        });

        this.projectFolder = org.getLocation();

        if (dirs.length == 1) {
            this.dir = dirs[0];
        } else {
            this.dir = null;
        }

        java.util.ResourceBundle dialogBundle = java.util.ResourceBundle.getBundle("i18n/mo/analysis/fixPlugin/fixPluginDialogs");
        JOptionPane.showMessageDialog(null, dialogBundle.getString("selectVideoScreenDialog"));
        //JOptionPane.showMessageDialog(null, "sad") ;

        SelectMediaFileDialog s = new SelectMediaFileDialog(this.dir);
        Boolean accepted = s.showDialog();

        if (accepted) {
            this.mediaFile = s.getSelectedFile();
        }
    }

    @Override
    public void startAnalysis() {
        if (this.mediaFile != null) {
            //this.player =  new EyeTribeFixPlayer(this.files.get(this.files.size()-1), mediaFile);
            this.player = new EyeTribeFixPlayer(this.files.get(this.files.size() - 1), mediaFile, this);

        } else {
            SelectResolutionDialog s = new SelectResolutionDialog(this.files.get(this.files.size() - 1));
            int windowWidth;
            int windowHeight;
            Boolean accepted = s.showDialog();

            if (accepted) {
                //this.player =  new EyeTribeFixPlayer(this.files.get(this.files.size()-1),s.getSelectedWidth(),s.getSelectedHeight());                              
                this.player = new EyeTribeFixPlayer(this.files.get(this.files.size() - 1), s.getSelectedWidth(), s.getSelectedHeight(), this);
            } else {
                //this.player =  new EyeTribeFixPlayer(this.files.get(this.files.size()-1),s.getSelectedWidth(),s.getSelectedHeight());             
                this.player = new EyeTribeFixPlayer(this.files.get(this.files.size() - 1), s.getSelectedWidth(), s.getSelectedHeight(), this);
            }
        }
    }

    @Override
    public void cancelAnalysis() {
        Thread.currentThread().interrupt();
    }

    @Override
    public String getId() {
        return this.name;
    }

    @Override
    public File toFile(File parent) {

        this.configFile = new File(parent, "eyetribe-fixation-analysis_" + this.name + ".xml");
        this.aoisFile = new File(parent, "eyetribe-fixation-analysis_" + this.name + "_aois" + ".csv");

        try {

            configFile.createNewFile();
            this.aoisFile.createNewFile();

            FileWriter fw = new FileWriter(configFile);
            try (BufferedWriter writer = new BufferedWriter(fw)) {

                if (this.player != null) {
                    this.fixationsColor = this.player.getFixationsColor();
                    this.fixationsOpacity = this.player.getFixationsOpacity();
                    this.fixationsLimit = this.player.getFixationsLimit();
                }

                writer.write("<?xml version='1.0'?>");
                writer.newLine();
                writer.write("<preferences>");
                writer.newLine();
                writer.write("  <fixationsColorRGB>" + this.fixationsColor.getRGB() + "</fixationsColorRGB>");
                writer.newLine();
                writer.write("  <fixationsOpacity>" + this.fixationsOpacity + "</fixationsOpacity>");
                writer.newLine();
                writer.write("  <fixationsLimit>" + this.fixationsLimit + "</fixationsLimit>");
                writer.newLine();
                writer.write("</preferences>");
                writer.newLine();

            }

        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        return configFile;
    }

    @Override
    public Configuration fromFile(File file) {
        String fileName = file.getName();

        if (fileName.contains("_") && fileName.contains(".")) {
            String name = fileName.substring(fileName.indexOf("_") + 1, fileName.lastIndexOf("."));
            EyeTrybeFixationAnalysisConfig config = new EyeTrybeFixationAnalysisConfig(name);
            config.configFile = file;
            try {

                XElement root = XIO.readUTF(new FileInputStream(file));
                config.fixationsColor = new Color(root.getElement("fixationsColorRGB").getInt());
                config.fixationsOpacity = root.getElement("fixationsOpacity").getDouble();
                config.fixationsLimit = root.getElement("fixationsLimit").getInt();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(EyeTrybeFixationAnalysisConfig.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(EyeTrybeFixationAnalysisConfig.class.getName()).log(Level.SEVERE, null, ex);
            }

            //agregar datos
            return config;
        }
        return null;

    }

    @Override
    public List<String> getCompatibleCreators() {
        return asList(creators);
    }

    @Override
    public void addFile(File file) {
        if (!files.contains(file)) {
            files.add(file);
        }
    }

    @Override
    public void removeFile(File file) {
        File toRemove = null;
        for (File f : files) {
            if (f.equals(file)) {
                toRemove = f;
            }
        }

        if (toRemove != null) {
            files.remove(toRemove);
        }
    }

    @Override
    public Playable getPlayer() {
        if (this.player == null) {
            this.player = new EyeTribeFixPlayer(this.files.get(this.files.size() - 1), mediaFile, this);
        }
        return this.player;
    }

    public File getConfigFile() {
        return this.configFile;
    }

    public void saveChanges() {
        this.toFile(configFile.getParentFile());
    }

    public Color getFixationsColor() {
        return fixationsColor;
    }

    public Double getFixationsOpacity() {
        return fixationsOpacity;
    }

    public int getFixationsLimit() {
        return fixationsLimit;
    }

}
