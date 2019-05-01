
package nanookreporter;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;

public class NanoOKReporterOptions {
    private Taxonomy taxonomy;
    private String prefsFile = System.getProperty("user.home") + File.separator + ".nanook_reporter.prefs";
    private String lastSample = "";
    private NanoOKSample sample;
    private String sampleName;
    private String cardPath;
    private int chunksToLoad = 0;
    private int refreshTime = 1;
    private double lcaMaxE = 0.001;
    private double lcaMinID = 70.0;
    private int lcaMinLength = 100;
    private int lcaMaxMatches = 10; // This was set to 5 for the BAMBI paper analysis
    private double walkoutMaxE = 0.001;
    private double walkoutMinID = 70.0;
    private int walkoutMinLength = 100;
    private int walkoutMaxChunk = 202;    
    private double cardMaxE = 0.001;
    private double cardMinID = 70.0;
    private int cardMinLength = 100;
    private double hitMaxE = 0.001;
    private double hitMinID = 50.0;
    private int hitMinLength = 10;
    
    public NanoOKReporterOptions() {
        String taxonomyPath = System.getenv("NANOOK_TAXONOMY");
        cardPath = System.getenv("NANOOK_CARD");

        if (System.getProperty("user.name").equals("leggettr")) {
            System.out.println("WARNING: DEBUGGING PATHS USED");
            taxonomyPath = "/Users/leggettr/Documents/Projects/BAMBI/taxdump";
            cardPath = "/Users/leggettr/Documents/Databases/CARD_1.1.1_Download_17Oct16";
        }
        
        if (cardPath == null) {
            System.out.println("ERROR: You must ensure that the environment variable NANOOK_CARD points to CARD files.");
            System.exit(1);
        }
        
        //taxonomyPath = "/Users/leggettr/Documents/Projects/BAMBI/taxdump";
        
        if (taxonomyPath == null) {
            System.out.println("ERROR: You must ensure that the environment variable NANOOK_TAXONOMY points to NCBI taxonomy files.");
            System.exit(1);
        }
        
        System.out.println("NANOOK_TAXONOMY set to "+taxonomyPath);
        System.out.println("NANOOK_CARD set to "+cardPath);
        
        taxonomy = new Taxonomy(taxonomyPath + File.separator + "nodes.dmp",
                                taxonomyPath + File.separator + "names.dmp");
        
        //                        "/Users/leggettr/Documents/Projects/BAMBI/accession2taxid/nucl_gb.accession2taxid");
        this.readPrefs();
    }
    
    public String getCardPath() {
        return cardPath;
    }
    
    public Taxonomy getTaxonomy() {
        return taxonomy;
    }
    
    public void setSample(NanoOKSample s) {
        sample = s;
    }
    
    public NanoOKSample getSample() {
        return sample;
    }
    
    public int getRefreshTime() {
        return refreshTime;
    }
    
    public void setRefreshTime(int t) {
        refreshTime = t;
    }
    
    public void setSampleName(String s) {
        sampleName = s;
    }
    
    public String getSampleName() {
        return sampleName;
    }
    
    public void readPrefs() {
        System.out.println("Reading preferences...");
        try {
            File f = new File(prefsFile);
            
            if (f.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(f));            
                String line;

                while ((line = br.readLine()) != null) {
                    String[] tokens = line.split(":");
                    
                    if (tokens.length == 2) {
                        String value = tokens[1];

                        if (tokens[0].compareToIgnoreCase("LastSample") == 0) {
                            lastSample = value;
                        } else if (tokens[0].compareToIgnoreCase("LCAMaxE") == 0) {
                            lcaMaxE = Double.parseDouble(value);
                        } else if (tokens[0].compareToIgnoreCase("LCAMinID") == 0) {
                            lcaMinID = Double.parseDouble(value);
                        } else if (tokens[0].compareToIgnoreCase("LCAMinLength") == 0) {
                            lcaMinLength = Integer.parseInt(value);
                        } else if (tokens[0].compareToIgnoreCase("LCAMaxMatches") == 0) {
                            lcaMaxMatches = Integer.parseInt(value);
                        } else if (tokens[0].compareToIgnoreCase("WalkoutMaxE") == 0) {
                            walkoutMaxE = Double.parseDouble(value);
                        } else if (tokens[0].compareToIgnoreCase("WalkoutMinID") == 0) {
                            walkoutMinID = Double.parseDouble(value);
                        } else if (tokens[0].compareToIgnoreCase("WalkoutMinLength") == 0) {
                            walkoutMinLength = Integer.parseInt(value);
                        } else if (tokens[0].compareToIgnoreCase("WalkoutMaxChunk") == 0) {
                            walkoutMaxChunk = Integer.parseInt(value);    
                        } else if (tokens[0].compareToIgnoreCase("CARDMaxE") == 0) {
                            cardMaxE = Double.parseDouble(value);
                        } else if (tokens[0].compareToIgnoreCase("CARDMinID") == 0) {
                            cardMinID = Double.parseDouble(value);
                        } else if (tokens[0].compareToIgnoreCase("CARDMinLength") == 0) {
                            cardMinLength = Integer.parseInt(value);
                        } else if (tokens[0].compareToIgnoreCase("HitMaxE") == 0) {
                            hitMaxE = Double.parseDouble(value);
                        } else if (tokens[0].compareToIgnoreCase("HitMinID") == 0) {
                            hitMinID = Double.parseDouble(value);
                        } else if (tokens[0].compareToIgnoreCase("HitMinLength") == 0) {
                            hitMinLength = Integer.parseInt(value);
                        }
                    } else {
                        System.out.println("Error: wrong number of tokens in "+line);
                    }
                }
                br.close();
            }
        } catch (Exception e) {
            System.out.println("BlastChunk exception");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public String getLastSample() {
        return lastSample;
    }
    
    public void writePrefs(String s) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(prefsFile)); 
            pw.println("LastSample:"+s);
            pw.println("LCAMaxE:"+lcaMaxE);
            pw.println("LCAMinID:"+lcaMinID);
            pw.println("LCAMaxMatches:"+lcaMaxMatches);
            pw.println("LCAMinLength:"+lcaMinLength);            
            pw.println("WalkoutMaxE:"+walkoutMaxE);
            pw.println("WalkoutMinID:"+walkoutMinID);
            pw.println("WalkoutMinLength:"+walkoutMinLength);
            pw.println("WalkoutMaxChunk:"+walkoutMaxChunk);
            pw.println("CARDMaxE:"+cardMaxE);
            pw.println("CARDMinID:"+cardMinID);
            pw.println("CARDMinLength:"+cardMinLength);
            pw.println("HitMaxE:"+hitMaxE);
            pw.println("HitMinID:"+hitMinID);
            pw.println("HitMinLength:"+hitMinLength);
            pw.close();
        } catch (IOException e) {
            System.out.println("writeCardSummaries exception:");
            e.printStackTrace();
            System.exit(1);
        }    
    }
    
    public Double getLCAMaxE() {
        return lcaMaxE;
    }
    
    public Double getLCAMinID() {
        return lcaMinID;
    }
    
    public int getLCAMinLength() {
        return lcaMinLength;
    }
    
    public int getLCAMaxMatches() {
        return lcaMaxMatches;
    }
    
    public double getWalkoutMaxE() {
        return walkoutMaxE;
    }
    
    public double getWalkoutMinID() {
        return walkoutMinID;
    }
    
    public int getWalkoutMinLength() {
        return walkoutMinLength;
    }
    
    public int getWalkoutMaxChunk() {
        return walkoutMaxChunk;
    }

    public Double getCARDMaxE() {
        return cardMaxE;
    }
    
    public Double getCARDMinID() {
        return cardMinID;
    }
    
    public int getCARDMinLength() {
        return cardMinLength;
    }

    public Double getHitMaxE() {
        return hitMaxE;
    }
    
    public Double getHitMinID() {
        return hitMinID;
    }
    
    public int getHitMinLength() {
        return hitMinLength;
    }
   
    public void setChunksToLoad(int l) {
        chunksToLoad = l;
    }
    
    public int getChunksToLoad() {
        return chunksToLoad;
    }
    
    public void setLCAMaxE(double e) {
        lcaMaxE = e;
    }
    
    public void setLCAMinID(double id) {
        lcaMinID = id;
    }
    
    public void setLCAMinLength(int l) {
        lcaMinLength = l;
    }
    
    public void setLCAMaxMatches(int m) {
        lcaMaxMatches = m;
    }
    
    public void setWalkoutMaxE(double e) {
        walkoutMaxE = e;
    }
    
    public void setWalkoutMinID(double id) {
        walkoutMinID = id;
    }
    
    public void setWalkoutMinLength(int l) {
        walkoutMinLength = l;
    }
    
    public void setWalkoutMaxChunk(int m) {
        walkoutMaxChunk = m;
    }

    public void setCARDMaxE(double e) {
        cardMaxE = e;
    }
    
    public void setCARDMinID(double id) {
        cardMinID = id;
    }
    
    public void setCARDMinLength(int l) {
        cardMinLength = l;
    }

    public void setHitMaxE(double e) {
        hitMaxE = e;
    }
    
    public void setHitMinID(double id) {
        hitMinID = id;
    }
    
    public void setHitMinLength(int l) {
        hitMinLength = l;
    }    
    
    public BlastMatchCriteria getHitCriteria() {
        return new BlastMatchCriteria(hitMaxE, hitMinID, hitMinLength);
    }

    public BlastMatchCriteria getLCACriteria() {
        return new BlastMatchCriteria(lcaMaxE, lcaMinID, lcaMinLength);
    }
    
    public BlastMatchCriteria getCardMatchCriteria() {
        return new BlastMatchCriteria(cardMaxE, cardMinID, cardMinLength);
    }
}
