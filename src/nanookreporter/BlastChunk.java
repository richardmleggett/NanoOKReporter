package nanookreporter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BlastChunk {
    private ArrayList<BlastAlignment> alignments = new ArrayList<BlastAlignment>();
    
    public BlastChunk(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;                
            while ((line = br.readLine()) != null) {
                alignments.add(new BlastAlignment(line));
            }
            br.close();
        } catch (Exception e) {
            System.out.println("BlastChunk exception");
            e.printStackTrace();
            System.exit(1);
        }
    }    
    
    public int getNumberOfAlignments() {
        return alignments.size();
    }
    
    public BlastAlignment getAlignment(int n) {
        return alignments.get(n);
    }
}
