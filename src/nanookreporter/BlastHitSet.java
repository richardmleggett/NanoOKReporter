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
        if (thisBitScore > highestBitScore) {
            if (alignments.size() > 0) {
                System.out.println("Unexpected new highest bitscore");
            }
            highestBitScore = thisBitScore;
            bitScoreThreshold = highestBitScore * 0.9;
        }
        
        if (thisBitScore >= bitScoreThreshold) {
            b.cacheTaxonIdPath(taxonomy);
            alignments.add(b);
        } else {
            ignored++;
        }
    }
    
    public int getNumberOfAlignments() {
        return alignments.size();
    }
    
    public BlastAlignment getAlignment(int i) {
        return alignments.get(i);
    }
    
    public void displayInfo() {
        System.out.println("Kept "+alignments.size() + "\tIgnored "+ignored);
    }
    
    public void findCommonAncestor(Taxonomy t) {
    }
}
