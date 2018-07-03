package nanookreporter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;

public class WalkOutChunk {
    private HashMap<String, WalkOutRead> reads = new HashMap<String, WalkOutRead>();
    int chunkNumber = 0;

    public WalkOutChunk(int c) {
        chunkNumber = c;
    }
    
    public void loadChunks(File cardFile, File bacteriaFile) {
        try {
            BufferedReader cardReader = new BufferedReader(new FileReader(cardFile));
            BufferedReader bacteriaReader = new BufferedReader(new FileReader(bacteriaFile));
            String line;
            
            while ((line = cardReader.readLine()) != null) {
                BlastAlignment ba = new BlastAlignment(line);
                if (ba.isValidAlignment()) {
                    WalkOutRead wor = reads.get(ba.getQueryId());
                    if (wor == null) {
                        wor = new WalkOutRead();
                        reads.put(ba.getQueryId(), wor);
                    }
                    wor.addCardHit(ba);
                }
            }            
            cardReader.close();

            while ((line = bacteriaReader.readLine()) != null) {
                BlastAlignment ba = new BlastAlignment(line);
                if (ba.isValidAlignment()) {
                    WalkOutRead wor = reads.get(ba.getQueryId());
                    if (wor != null) {
                        wor.addBacteriaHit(ba);
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
    
    public void processHits(WalkOutResults results, PrintWriter pw) {
        for (HashMap.Entry<String, WalkOutRead> entry : reads.entrySet())
        {
            String queryId = entry.getKey();
            WalkOutRead wor = entry.getValue();
            
            if (wor == null) {
                System.out.println("Error: wor is null");
                System.exit(1);
            }
            
            
            //System.out.println(wor.getBacterialHit());
            //System.out.println(wor.gotIndependentHit());
            //System.out.println(wor.getOverlap());
            
            String hostHit = wor.getBacterialHit();
            boolean isIndependent = wor.gotIndependentHit();
            int overlap = wor.getOverlap();
            
            results.addHit(hostHit, isIndependent, overlap);
            pw.print(queryId);
            pw.print("\t" + hostHit);
            pw.print("\t" + overlap);
            pw.print("\t" + (isIndependent?"Y":"N"));
            pw.print("\t" + wor.getCardHit());
            pw.print("\t" + chunkNumber);
            pw.println("");
            
            //System.out.println(queryId + " = " + wor.getBacterialHit());
        }
    }
}
