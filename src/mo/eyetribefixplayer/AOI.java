/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mo.eyetribefixplayer;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

public class AOI {

    private int id;
    private int width;
    private int height;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private Double relativeX;
    private Double relativeY;
    private Double relativeWidth;
    private Double reltiveHeight;
    private Double opacity;
    private Color color;
    private Fixation lastFixation;
    private Fixation firstFixation;

    private ArrayList<Fixation> fixations;
    private Double fixationDensity;
    private long timeToFirstFixation;
    private int fixationsBefore;
    private long firstFixationDuration;
    private int fixationCount;
    private long totalFixationDuration;
    private long offset;
    private boolean visible;
    private Double onTarget;
    private int postTarget;
    private String name;
    private int totalFixCount;
    private FixationMap fixMap;

    public AOI(Double relativeX, Double relativeY, Double relativeWidth, Double relativeHeight, Color color) {
        this.relativeWidth = relativeWidth;
        this.reltiveHeight = relativeHeight;
        this.relativeX = relativeX;
        this.relativeY = relativeY;
        this.color = color;
        this.opacity = 0.5;
        this.lastFixation = null;
        this.firstFixation = null;
        this.fixationCount = 0;
        this.fixations = new ArrayList<Fixation>();

        this.width = 0;
        this.height = 0;

        this.offset = -1;
        this.fixationDensity = 0.0;
        this.timeToFirstFixation = 0;
        this.fixationsBefore = 0;
        this.firstFixationDuration = 0;
        this.fixationCount = 0;
        this.totalFixationDuration = 0;
        this.visible = true;
        this.onTarget = 0.0;
        this.postTarget = 0;
        this.fixMap = null;

        this.name = "";
    }

    public AOI(int id, String name, Double relativeX, Double relativeY, Double relativeWidth, Double relativeHeight, Color color) {
        this.relativeWidth = relativeWidth;
        this.reltiveHeight = relativeHeight;
        this.relativeX = relativeX;
        this.relativeY = relativeY;
        this.color = color;
        this.opacity = 0.5;
        this.lastFixation = null;
        this.firstFixation = null;
        this.fixationCount = 0;
        this.fixations = new ArrayList<Fixation>();
        this.id = id;
        this.name = name;

        this.width = 0;
        this.height = 0;

        this.offset = -1;
        this.fixationDensity = 0.0;
        this.timeToFirstFixation = 0;
        this.fixationsBefore = 0;
        this.firstFixationDuration = 0;
        this.fixationCount = 0;
        this.totalFixationDuration = 0;
        this.visible = true;
        this.totalFixCount = 0;
        this.fixMap = null;
        this.postTarget = 0;
        this.onTarget = 0.0;

    }

    public void setFixMap(FixationMap fixMap) {
        this.fixMap = fixMap;
    }

    public void setRelativeSize(Double width, Double height) {
        this.relativeWidth = width;
        this.reltiveHeight = height;
    }

    public void setRelativeXY(Double x, Double y) {
        this.relativeX = x;
        this.relativeY = y;
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public Color getColor() {
        return color;
    }

    public Double getRelativeX() {
        return relativeX;
    }

    public Double getRelativeY() {
        return relativeY;
    }

    public Double getRelativeWidth() {
        return relativeWidth;
    }

    public Double getReltiveHeight() {
        return reltiveHeight;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLastFixation(Fixation fixation) {
        this.lastFixation = fixation;
    }

    public void setFirstFixation(Fixation fixation) {
        this.firstFixation = fixation;
    }

    public Fixation getLastFixation() {
        return lastFixation;
    }

    public int getFixationCount() {
        //return fixationCount;
        return this.fixations.size();
    }

    public void setFixationCount(int fixationCount) {
        this.fixationCount = fixationCount;
    }

    public void incrementFixationCount() {
        this.fixationCount++;
    }

    public void addFixation(Fixation fixation) {

        if (fixation == null) {
            return;
        }

        if (this.firstFixation == null) {

            //this.lastFixation = fixation;
            this.firstFixation = fixation;
            if (this.offset > 0) {
                this.timeToFirstFixation = fixation.getStartUixTimeStamp() - this.offset;
            } else {
                this.timeToFirstFixation = fixation.getStartUixTimeStamp();
            }
            this.obtainPreviosFixations();

        }

        this.fixations.add(fixation);
        this.lastFixation = fixation;
        this.fixationCount++;

        this.postTarget = 0;
        this.fixationDensity = new Double(this.fixationCount) / (this.width * this.height);

        if (this.fixationCount - 2 >= 0) {
            this.totalFixationDuration = this.fixations.get(this.fixationCount - 2).getDuration() + this.totalFixationDuration;
        }
    }

    public Double getOntarget() {
        if (this.fixMap != null) {
            this.onTarget = new Double(this.fixationCount) / this.fixMap.getFixationCount();
        }
        return this.onTarget;
    }

    public int getPostTarget() {
        if (this.fixMap != null) {
            this.postTarget = this.fixMap.getFixationCount() - this.lastFixation.getId() + 1;
        }
        return this.postTarget;

    }

    public Fixation getFirstFixation() {
        return firstFixation;
    }

    public Double getFixationDensity() {
        return fixationDensity;
    }

    public long getTimeToFirstFixation() {
        //this.timeToFirstFixation = this.firstFixation.getStartUixTimeStamp() - this.offset;
        return timeToFirstFixation;
    }

    public int getFixationsBefore() {
        return fixationsBefore;
    }

    public long getFirstFixationDuration() {
        if (this.firstFixation != null) {
            return this.firstFixation.getDuration();
        } else {
            return 0;
        }
    }

    public long getTotalFixationDuration() {
        if (this.lastFixation != null) {
            return totalFixationDuration + this.lastFixation.getDuration();
        } else {
            return this.totalFixationDuration;
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void toFile(File file) {

    }

    public void reset() {

        this.lastFixation = null;
        this.firstFixation = null;
        this.fixationCount = 0;
        this.fixations = new ArrayList<Fixation>();
        this.fixationDensity = 0.0;
        this.timeToFirstFixation = 0;
        this.fixationsBefore = 0;
        this.firstFixationDuration = 0;
        this.fixationCount = 0;
        this.totalFixationDuration = 0;
    }

    public void setActualsize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    private void obtainPreviosFixations() {
        Fixation fix = this.firstFixation;
        this.fixationsBefore = 0;

        while (fix != null) {
            this.fixationsBefore++;
            fix = fix.getPrevious();
        }
        this.fixationsBefore--;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return this.visible;
    }

}
