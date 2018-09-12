package nanookreporter;

import java.awt.Cursor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.SwingWorker;
import org.knowm.xchart.*;
import org.knowm.xchart.BitmapEncoder.*;

public class WalkOutAnalyser extends SwingWorker {
    NanoOKReporter reporter;
    NanoOKReporterOptions options;
    NanoOKSample sample;
    WalkOutResults results;
    String walkoutDir;
    
    public WalkOutAnalyser(NanoOKReporter r, NanoOKReporterOptions o, NanoOKSample s) {
        reporter = r;
        options = o;
        sample = s;        
        results = new WalkOutResults(options);
        createWalkoutDirectory();
    }
    
    private void createWalkoutDirectory() {
        walkoutDir = sample.getSampleDir() + File.separator + "walkout";
        File walkoutFile = new File (walkoutDir);
        
        if (walkoutFile.exists()) {
            if (! walkoutFile.isDirectory()) {
                System.out.println("Error: "+walkoutDir+" is a file!");
            }
        } else {
            walkoutFile.mkdir();
        }
    }
    
    public String doInBackground() throws Exception {
        String cardDir = sample.getCardDir();
        String bacteriaDir = sample.getBacteriaDir();
        String prefix = "all_Template_pass";
        String midfix_card = "blastn_card";
        String midfix_bacteria = "blastn_bacteria";
        String summaryFilename = walkoutDir + File.separator + "walkout_results.txt";
        PrintWriter pw = null;
        int chunk = 0;
        boolean moreChunks = true;
        
        reporter.setCursor(new Cursor(Cursor.WAIT_CURSOR));

        try {
            pw = new PrintWriter(new FileWriter(summaryFilename)); 
            pw.println("ReadId\tHostHit\tOverlap\tIndependent\tCARDhit\tChunk");
        } catch (Exception e) {
            System.out.println("Exception:");
            e.printStackTrace();
            System.exit(1);
        }
        
        while (moreChunks) {
            String cardFilename = cardDir + File.separator + prefix + "_" + chunk + "_" + midfix_card + ".txt";
            String bacteriaFilename = bacteriaDir + File.separator + prefix + "_" + chunk + "_" + midfix_bacteria + ".txt";
            File cardFile = new File(cardFilename);
            File bacteriaFile = new File(bacteriaFilename);
            if (cardFile.exists() && cardFile.isFile()) {
                if (bacteriaFile.exists() && bacteriaFile.isFile()) {
                    //System.out.println("Reading chunk "+chunk);
                    reporter.setStatus("Reading chunk " + chunk);
                    WalkOutChunk woc = new WalkOutChunk(chunk);
                    woc.loadChunks(cardFile, bacteriaFile);
                    woc.processHits(results, pw);

                    chunk++;
                    if ((chunk > 844) || (chunk > options.getChunksToLoad())) {
                        moreChunks=false;
                    }
                } else {
                    System.out.println("Error: can't find " + bacteriaFilename);
                    moreChunks = false;
                }
            } else {
                System.out.print("Warning: can't find " + cardFilename);
                moreChunks = false;
            }
        }        
        
        if (pw != null) {
            pw.close();
        }
        
        reporter.setStatus("Writing walkout results...");
        results.writeResults(reporter, walkoutDir);
        reporter.setStatus("Ok");
        
        return null;
    }
    
    @Override
    public void done() {
        reporter.walkoutFinished();
        reporter.setCursor(Cursor.getDefaultCursor());
    }    
}
