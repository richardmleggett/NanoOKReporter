package nanookreporter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BlastChunk {
    private ArrayList<BlastHitSet> allAlignments = new ArrayList<BlastHitSet>();
    private long lastModified = 0;
    private NanoOKReporterOptions options;
    
    public BlastChunk(NanoOKReporterOptions o, String filename) {
        String lastId = "";
        Double lastE = 0.0;
        options = o;
        
        File f = new File(filename);
        lastModified = f.lastModified();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            BlastHitSet bhs = null;
            while ((line = br.readLine()) != null) {
                BlastAlignment ba = new BlastAlignment(line);
                if (ba.isValidAlignment()) {
                    if (ba.getQueryId().equals(lastId)) {
                        if (ba.getEValue() < lastE) {
                            System.out.println("Error: e value less!");
                            System.exit(1);
                        }
                    } else {                       
                        if (bhs != null) {
                            allAlignments.add(bhs);
                            options.getTaxonomy().findAncestorAndStore(bhs);
                        }
                        lastId = ba.getQueryId();
                        lastE = ba.getEValue();
                        //options.getTaxonomy().parseTaxonomyAndCount(ba.getSubjectTitle());
                        bhs = new BlastHitSet(options.getTaxonomy());   
                    }
                    bhs.addAlignment(ba);
                }
            }
            
            // Deal with the bhs we won't have processed
            if (bhs != null) {
                allAlignments.add(bhs);
                options.getTaxonomy().findAncestorAndStore(bhs);
            }
        
            br.close();
        } catch (Exception e) {
            System.out.println("BlastChunk exception");
            e.printStackTrace();
            System.exit(1);
        }
    }    
    
    public long getLastModified() {
        return lastModified;
    }
    
    public int getNumberOfAlignments() {
        return allAlignments.size();
    }
    
    public BlastAlignment getTopHit(int n) {
        return allAlignments.get(n).getAlignment(0);
    }
    
    public BlastHitSet getHitSet(int n) {
        return allAlignments.get(n);
    }
}
