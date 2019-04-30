package nanookreporter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;

public class WalkOutChunk {
    private HashMap<String, WalkOutRead> reads = new HashMap<String, WalkOutRead>();
    private Taxonomy taxonomy;
    private NanoOKReporterOptions options;
    int chunkNumber = 0;

    public WalkOutChunk(NanoOKReporterOptions o, Taxonomy t, int c) {
        options = o;
        taxonomy = t;
        chunkNumber = c;
    }
    
    public void loadChunks(File cardFile, File bacteriaFile) {
        try {
            BufferedReader cardReader = new BufferedReader(new FileReader(cardFile));
            BufferedReader bacteriaReader = new BufferedReader(new FileReader(bacteriaFile));
            String line;
            
            // Go through CARD file, storing reads with hits
            while ((line = cardReader.readLine()) != null) {
                if (line.length() > 1) {
                    BlastAlignment ba = new BlastAlignment(line);
                    if (ba.isValidAlignment()) {
                        WalkOutRead wor = reads.get(ba.getQueryId());
                        if (wor == null) {
                            wor = new WalkOutRead(options, taxonomy);
                            reads.put(ba.getQueryId(), wor);
                        }
                        wor.addCardHit(ba);
                    }
                }
            }            
            cardReader.close();

            // Now go through bacteria file
            while ((line = bacteriaReader.readLine()) != null) {
                if (line.length() > 1) {
                    BlastAlignment ba = new BlastAlignment(line);
                    if (ba.isValidAlignment()) {
                        WalkOutRead wor = reads.get(ba.getQueryId());
                        if (wor != null) {
                            wor.addBacteriaHit(ba);
                        }
                    }
                }
            }
            bacteriaReader.close();
            
        } catch (Exception e) {
            System.out.println("Exception in analyseFiles:");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public void processHits(WalkOutResults results, PrintWriter pwIndependent, PrintWriter pwNotIndependent) {
        boolean shortVersion = true;

        System.out.println("Processing chunk "+chunkNumber);
        for (HashMap.Entry<String, WalkOutRead> entry : reads.entrySet())
        {
            String queryId = entry.getKey();
            WalkOutRead wor = entry.getValue();
                        
            if (wor == null) {
                System.out.println("Error: wor is null");
                System.exit(1);
            }
         
            if (wor.getHitSetSize() > 0) {            
                //System.out.println(wor.getBacterialHit());
                //System.out.println(wor.gotIndependentHit());
                //System.out.println(wor.getOverlap());
                String hostHit = wor.getBacterialHit();
                String lcaHit = wor.getLCAHit();
                String lcaShort = lcaHit.substring(lcaHit.lastIndexOf(',')+1);
                //int overlap = wor.getLongestDistance();
                //boolean isIndependent = wor.gotIndependentHit();
                
                for (int i=0; i<wor.getNumberOfGenes(); i++) {
                    PrintWriter pw;
                    //int overlap = wor.getOverlap();
                    int overlap = wor.getCardHit(i).getDistance();
                    boolean isIndependent = overlap >= wor.getMinOverlap() ? true:false;

                    if (isIndependent) {
                        pw = pwIndependent;
                    } else {
                        pw = pwNotIndependent;
                    }
                    
                    //String cardHit = wor.getCardHit();
                    String cardHit = wor.getCardHit(i).getSubjectId();
                    
                    if (shortVersion) {
                        if (cardHit.contains("ARO")) {
                            if (cardHit.contains("ARO:3003730")) {
                                cardHit = "ARO:3003730|Bifidobacterium ileS";
                            } else {
                                cardHit = wor.getCardHit(i).getSubjectId().substring(cardHit.lastIndexOf("ARO"));
                            }
                        }
                    }
                                             
                    //results.addHit(hostHit, isIndependent, overlap);
                    results.addHit(lcaShort, isIndependent, overlap);
                    //System.out.println(lcaShort);
                    pw.print(queryId);
                    pw.print("\t" + chunkNumber);
                    pw.print("\t" + hostHit);
                    pw.print("\t" + cardHit);
                    pw.print("\t" + wor.getCardHit(i).getPercentIdentity());
                    pw.print("\t" + wor.getCardHit(i).getLength());
                    pw.print("\t" + overlap);
                    if (shortVersion) {
                        pw.print("\t" + lcaShort);
                    } else {
                        pw.print("\t" + lcaHit);
                    }
                    pw.println("");

                    //System.out.println(queryId + " = " + wor.getBacterialHit());                    
                }
            }
        }
    }
}
