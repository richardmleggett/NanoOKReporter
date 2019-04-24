/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nanookreporter;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author leggettr
 */
public class WalkOutRead {
    //private BlastAlignment cardAlignment = null;
    private ArrayList<BlastAlignment> cardAlignments = new ArrayList<BlastAlignment>();
    private BlastAlignment bacteriaAlignment = null;
    private BlastHitSet blastHitSet;
    private boolean gotIndependentHit = false;
    private Taxonomy taxonomy;
    private int minOverlap = 50;
    private int longestDistance = 0;
    private CoordinateList cl = new CoordinateList();
    private double highestBitScore = 0;
    private double bitScoreThreshold = 0;

        
    public WalkOutRead(Taxonomy t) {
        taxonomy = t;
        blastHitSet = new BlastHitSet(taxonomy);
    }
    
    public void addCardHit(BlastAlignment ba) {
        //if (cardAlignment == null) {
        //    cardAlignment = ba;
        //} else {
        //    if (ba.getEValue() < cardAlignment.getEValue()) {
        //        System.out.println("Note: Got better CARD score");
        //        cardAlignment = ba;
        //    }
        //}

        if ((ba.getPercentIdentity() >= 70) && (ba.getLength() >= 100)) {        
            int overlap = cl.getOverlap(ba.getQueryStart(), ba.getQueryEnd());
            if (overlap < (ba.getLength() / 10)) {
                cl.add(ba.getQueryStart(), ba.getQueryEnd());
                cardAlignments.add(ba);                                  
            }        
        }
    }
    
    public void addBacteriaHit(BlastAlignment ba) {
        //boolean independent = false;
        
        // Check if overlaps by minimum overlap
        //if ((ba.getQueryEnd() >= (cardAlignment.getQueryEnd() + minOverlap)) ||
        //    (ba.getQueryStart() <= (cardAlignment.getQueryStart() - minOverlap))) {
        //    independent = true;
        //}
        
        boolean independent = false;
        for (int i=0; i<cardAlignments.size(); i++) {
            BlastAlignment ca = cardAlignments.get(i);
            ca.storeDistance(ba);
            if (ca.getDistance() > minOverlap) {                
                independent = true;
            }
        }
        
        if (ba.getBitScore() > highestBitScore) {
            if (highestBitScore > 0) {
                System.out.println("Warning: Got higher bitscore than expected");
                highestBitScore = ba.getBitScore();
                bitScoreThreshold = highestBitScore * 0.9;
            }
        }
        
        if (ba.getBitScore() >= bitScoreThreshold) { //&& (blastHitSet.getNumberOfAlignments() < 10)) {
            //if (independent) {
            //            
            blastHitSet.addAlignment(ba);
        }
        
        // If this hit is an indepdent hit
        if (independent) {
            // If we already have indepdent hit (shouldn't get here, as file should be sorted), then we'll take the new hit if it's got a better e value.
            // If we don't have one, then we'll keep it
            if (gotIndependentHit) {
                if (ba.getEValue() < bacteriaAlignment.getEValue()) {
                    System.out.println("Note: Got better host score");
                    bacteriaAlignment = ba;
                }
            } else {
                bacteriaAlignment = ba;
                gotIndependentHit = true;
            }
        // If this hit not an indepdent hit and we don't have an indepdent hit yet, then we want to store the best so far
        } else if (gotIndependentHit == false) {
            // If not already got indepdent hit, still store the best hit, but don't set the indepdence flag
            if (bacteriaAlignment != null) {
                if (ba.getEValue() < bacteriaAlignment.getEValue()) {
                    System.out.println("Note: Got better host score");
                    bacteriaAlignment = ba;
                }
            } else {
                // Don't already have a hit, so store it
                bacteriaAlignment = ba;
            }
        }
    }
    
    public String getBacterialHit() {
        String hit = "";
        if (bacteriaAlignment != null) {
            Pattern pattern = Pattern.compile("(\\S+) (\\S+)");
            Matcher matcher = pattern.matcher(bacteriaAlignment.getSubjectTitle());
            if (matcher.find()) {
                hit = matcher.group(1) + " " + matcher.group(2);
            } else {
                hit = bacteriaAlignment.getSubjectId();
            }
        } else {
            hit = "No hit";
        }
        
        if (hit == null) {
            System.out.println("Something went wrong");
            System.exit(1);
        }
        
        return hit;
    }
    
    public String getLCAHit() {
        String hit = "";
        String s;
        
        if (blastHitSet.getNumberOfAlignments() == 0) {
            System.out.println("Er... no alignments in getLCAHit");
            System.exit(0);
        }
        
        long id = taxonomy.findAncestor(blastHitSet, 0, 100, false);
        s = taxonomy.getTaxonomyStringFromId(id);
        return s;
    }
    
    public int getHitSetSize() {
        return blastHitSet.getNumberOfAlignments();
    }
    
    //public String getCardHit() {
    //    return cardAlignment.getSubjectId();
    //}
    
    public boolean gotIndependentHit() {
        return gotIndependentHit;
    }
    
    public int getNumberOfGenes() {
        return cardAlignments.size();
    }
    
    public BlastAlignment getCardHit(int n) {
        return cardAlignments.get(n);
    }
    
    //public int getOverlap() {
    //    int overlap = 0;
    //    
    //    if (bacteriaAlignment != null) {
    //        if (bacteriaAlignment.getQueryEnd() > cardAlignment.getQueryEnd()) {
    //            overlap = bacteriaAlignment.getQueryEnd() - cardAlignment.getQueryEnd();
    //        }
   
    //        if (bacteriaAlignment.getQueryStart() < cardAlignment.getQueryStart()) {
    //            int startOverlap = cardAlignment.getQueryStart() - bacteriaAlignment.getQueryStart();

    //            if (startOverlap > overlap) {
    //                overlap = startOverlap;
    //            }
    //        }
    //    }
        
    //    return overlap;
    //}
    
    public int getLongestDistance() {
        return longestDistance;
    }

    public int getMinOverlap() {
        return minOverlap;
    }


}
