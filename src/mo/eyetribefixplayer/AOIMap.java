package mo.eyetribefixplayer;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AOIMap {

    private BufferedImage map;
    private ArrayList<AOI> AOIS;
    private int width;
    private int height;
    private int[][] southcourt;
    private int AOICount;
    private long offset;
    private File AOIFile;
    private FixationMap fixMap;

    public AOIMap(int width, int height) {

        this.map = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.width = width;
        this.height = height;
        this.AOIS = new ArrayList<AOI>();
        this.map.createGraphics();
        this.southcourt = new int[width][height];
        this.AOICount = 0;
        fillMatriz(this.southcourt, width, height);
        this.offset = -1;
        this.AOIFile = null;
        this.fixMap = null;

    }

    public AOIMap(int width, int height, FixationMap fixMap) {

        this.map = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.width = width;
        this.height = height;
        this.AOIS = new ArrayList<AOI>();
        this.map.createGraphics();
        this.southcourt = new int[width][height];
        this.AOICount = 0;
        fillMatriz(this.southcourt, width, height);
        this.offset = -1;
        this.AOIFile = null;
        this.fixMap = null;
        this.fixMap = fixMap;

    }

    public void addAOI(AOI aoi) {
        this.AOIS.add(aoi);
        Graphics2D g = (Graphics2D) this.map.getGraphics();

        int x = new Double(aoi.getRelativeX() * this.width).intValue();
        int y = new Double(aoi.getRelativeY() * this.height).intValue();
        int width = new Double(aoi.getRelativeWidth() * this.width).intValue();
        int height = new Double(aoi.getReltiveHeight() * this.height).intValue();
        int centerX = (x + width / 2);
        int centerY = (y + height / 2);

        g.setColor(aoi.getColor());
        g.fillRect(x,
                y,
                width,
                height);

        if (width + x > this.width) {
            width = this.width - x;
        }
        if (height + y > this.height) {
            height = this.height - y;
        }
        if (x < 0) {
            width = width + x;
            x = 0;
        }
        if (y < 0) {
            height = height + y;
            y = 0;
        }

        centerX = (x + width / 2);
        centerY = (y + height / 2);

        g.setColor(new Color(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 0.5f));
        g.setFont(new Font("default", Font.BOLD, 20));
        g.drawString(String.valueOf(aoi.getId()), centerX, centerY);
        g.drawString(String.valueOf(aoi.getName()), centerX, centerY - 20);

        rectToMatriz(southcourt,
                x,
                y,
                width,
                height,
                aoi.getId());

        aoi.setActualsize(width, height);
        this.AOICount++;

        if (this.AOIS != null & this.AOIFile != null) {
            if (this.AOIS.size() > 0) {
                this.aoisToCsvFile(AOIFile);
            }
        }

    }

    //getters        
    public BufferedImage getMap() {
        return map;
    }

    public AOI getAOI(int x, int y) {

        int id;
        if (this.AOIS != null) {
            if (x > this.width || y > this.height) {
                return null;
            }

            id = this.southcourt[x][y];

            if (id != 0) {
                return this.AOIS.get(id - 1);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public int getAOICount() {
        return AOICount;
    }

    //resize
    public void resize(int width, int height) {

        if (this.width != width || this.height != height) {

            this.width = width;
            this.height = height;

            this.southcourt = new int[width][height];
            fillMatriz(this.southcourt, width, height);

            //f rd aoi!!!!
            cleanMap();
            repaint();
        }

    }

    public void cleanMap() {
        this.map = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public void repaint() {

        if (this.AOIS != null) {

            Graphics2D g = (Graphics2D) this.map.getGraphics();
            for (AOI aoi : this.AOIS) {
                if (aoi != null) {
                    if (aoi.isVisible()) {
                        g.setColor(aoi.getColor());

                        int x = new Double(aoi.getRelativeX() * this.width).intValue();
                        int y = new Double(aoi.getRelativeY() * this.height).intValue();
                        int width = new Double(aoi.getRelativeWidth() * this.width).intValue();
                        int height = new Double(aoi.getReltiveHeight() * this.height).intValue();
                        int centerX = (x + width / 2);
                        int centerY = (y + height / 2);

                        g.setColor(aoi.getColor());
                        g.fillRect(x,
                                y,
                                width,
                                height);

                        g.setColor(new Color(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 0.5f));
                        g.setFont(new Font("default", Font.BOLD, 20));
                        g.drawString(String.valueOf(aoi.getId()), centerX, centerY);
                        g.setFont(new Font("default", Font.BOLD, 14));
                        int ofst = aoi.getName().length() * 2;
                        g.drawString(String.valueOf(aoi.getName()), centerX - ofst, centerY + 22);

                        rectToMatriz(southcourt,
                                x,
                                y,
                                width,
                                height,
                                aoi.getId());

                        aoi.setActualsize(width, height);
                    }
                }
            }
        }

    }

    public void setOffset(long offset) {
        for (AOI aoi : this.AOIS) {
            aoi.setOffset(offset);
        }
    }

    //auxiliar functions
    private void rectToMatriz(int matriz[][], int x, int y, int width, int height, int value) {

        int limx = width + x;
        int limy = height + y;

        if (limx > this.width) {
            limx = this.width - 1;
        }
        if (limy > this.height) {
            limy = this.height - 1;
        }
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }

        for (int i = x; i < limx; i++) {
            for (int j = y; j < limy; j++) {
                matriz[i][j] = value;
            }
        }
    }

    public ArrayList<AOI> getAOIs() {
        return this.AOIS;
    }

    public void fillMatriz(int matriz[][], int width, int height) {

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                matriz[i][j] = 0;
            }
        }

    }

    public void reset() {
        if (this.AOIS != null) {
            for (AOI aoi : this.AOIS) {
                if (aoi != null) {
                    aoi.reset();
                }
            }
        }
    }

    public void setAoisFile(File file) {
        this.AOIFile = file;
    }

    public void aoisToCsvFile(File file) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(file));
            String[] header = new String[]{"id", "name", "relativeX", "relativeY", "relativewidth", "relativeheight", "rgbColor"};
            writer.writeNext(header);
            List<String[]> allData = new ArrayList<String[]>();

            for (AOI aoi : this.AOIS) {
                if (aoi != null) {
                    String[] data = new String[]{String.valueOf(aoi.getId()),
                        aoi.getName(),
                        aoi.getRelativeX().toString(),
                        aoi.getRelativeY().toString(),
                        aoi.getRelativeWidth().toString(),
                        aoi.getReltiveHeight().toString(),
                        String.valueOf(aoi.getColor().getRGB())
                    };
                    allData.add(data);
                }
            }
            writer.writeAll(allData);
            writer.close();

        } catch (IOException ex) {
            Logger.getLogger(AOIMap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveChanges() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(this.AOIFile));
            String[] header = new String[]{"id", "name", "relativeX", "relativeY", "relativewidth", "relativeheight", "rgbColor"};
            writer.writeNext(header);
            List<String[]> allData = new ArrayList<String[]>();

            for (AOI aoi : this.AOIS) {
                if (aoi != null) {
                    String[] data = new String[]{String.valueOf(aoi.getId()),
                        aoi.getName(),
                        aoi.getRelativeX().toString(),
                        aoi.getRelativeY().toString(),
                        aoi.getRelativeWidth().toString(),
                        aoi.getReltiveHeight().toString(),
                        String.valueOf(aoi.getColor().getRGB())
                    };
                    allData.add(data);
                }
            }
            writer.writeAll(allData);
            writer.close();

        } catch (IOException ex) {
            Logger.getLogger(AOIMap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadAoisFromFile(File file) {
        this.AOIFile = file;
        if (file != null) {
            if (file.exists()) {
                try {
                    CSVReader csvReader = new CSVReader(new FileReader(file));
                    List<String[]> allData = csvReader.readAll();

                    for (String[] data : allData) {
                        if (!data[0].equals("id")) {
                            Color color = new Color(Integer.parseInt(data[6]));
                            AOI aoi = new AOI(Integer.parseInt(data[0]),
                                    data[1],
                                    Double.parseDouble(data[2]),
                                    Double.parseDouble(data[3]),
                                    Double.parseDouble(data[4]),
                                    Double.parseDouble(data[5]),
                                    new Color(color.getRed(), color.getGreen(), color.getBlue(), 70)
                            );
                            this.addAOI(aoi);
                        }
                    }
                    csvReader.close();

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(AOIMap.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(AOIMap.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public void deleteAll() {

        this.southcourt = new int[width][height];
        this.AOICount = 0;
        this.AOIS = new ArrayList<AOI>();
        this.map = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

}
