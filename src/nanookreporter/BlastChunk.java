package nanookreporter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BlastChunk {
    private ArrayList<BlastAlignment> alignments = new ArrayList<BlastAlignment>();
    private long lastModified = 0;
    
    public BlastChunk(String filename) {
        String lastId = "";
        Double lastE = 0.0;
        
        File f = new File(filename);
        lastModified = f.lastModified();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;                
            while ((line = br.readLine()) != null) {
                BlastAlignment ba = new BlastAlignment(line);
                if (ba.isValidAlignment()) {
                    if (ba.getQueryId().equals(lastId)) {
                        if (ba.getEValue() < lastE) {
                            System.out.println("Error: e value less!");
                            System.exit(1);
                        }
                    } else {
                        alignments.add(ba);
                        lastId = ba.getQueryId();
                        lastE = ba.getEValue();
                    }
                }
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
        return alignments.size();
    }
    
    public BlastAlignment getAlignment(int n) {
        return alignments.get(n);
    }
}
