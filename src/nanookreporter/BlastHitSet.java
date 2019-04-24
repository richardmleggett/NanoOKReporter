package nanookreporter;

import java.util.ArrayList;

public class BlastHitSet {
    private ArrayList<BlastAlignment> alignments = new ArrayList<BlastAlignment>();
    private double highestBitScore = 0.0;
    private double bitScoreThreshold = 0.0;
    private int ignored = 0;
    private Taxonomy taxonomy;

    public BlastHitSet(Taxonomy t) {
        taxonomy = t;
    }
    
    public void addAlignment(BlastAlignment b) {
        double thisBitScore = b.getBitScore();
        boolean debug = false;
        
        if (b.getQueryId().equals("944fca64-4f5e-48dc-ac6d-af4655ad8c06")) {
            debug = true;
        }
        
        if (thisBitScore > highestBitScore) {
            if (alignments.size() > 0) {
                System.out.println("Unexpected new highest bitscore");
            }
            
            if (highestBitScore > 0) {
                System.out.println("WARNING: Highest bitscore detected later than expected");
            }
            
            highestBitScore = thisBitScore;
            bitScoreThreshold = highestBitScore * 0.9;
        }
        
        if (thisBitScore >= bitScoreThreshold) {
            b.cacheTaxonIdPath(taxonomy);
            alignments.add(b);
        } else {
            if (debug) {
                System.out.println("Ignored bit score " + thisBitScore + " threshold " + bitScoreThreshold);
            }
            ignored++;
        }
        
        if (debug) {
            System.out.println("Got "+alignments.size()+ " alignments");
        }
    }
    
    public int getNumberOfAlignments() {
        return alignments.size();
    }
    
    public BlastAlignment getAlignment(int i) {        
        if (i >= alignments.size()) {
            return null;
        }
        
        return alignments.get(i);
    }
    
    public void displayInfo() {
        System.out.println("Kept "+alignments.size() + "\tIgnored "+ignored);
    }
    
    public void findCommonAncestor(Taxonomy t) {
    }
}
