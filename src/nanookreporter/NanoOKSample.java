package nanookreporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

public class NanoOKSample {
    String sampleDirectory;
    String cardDir;
    String ntDir;
    BlastFile cardFile;
    BlastFile ntFile;
    BlastFile bacteriaFile;
    NanoOKReporterOptions options;
            
    public NanoOKSample(NanoOKReporterOptions o, String directory) {
        options = o;
        sampleDirectory = directory;
        File sampleDir = new File(sampleDirectory);
        if (dirExists(sampleDirectory)) {
            cardFile = new BlastFile(options, sampleDirectory + File.separator + "blastn_card", "blastn_card");
            ntFile = new BlastFile(options, sampleDirectory + File.separator + "blastn_nt", "blastn_nt");
            bacteriaFile = new BlastFile(options, sampleDirectory + File.separator + "blastn_bacteria", "blastn_bacteria");
        }
    }
    
    public void readData(int db, int type, int pf, NanoOKReporter nor) {
        if (db == BlastFile.DATABASE_CARD) {
            nor.setStatus("Reading CARD chunks...");
            cardFile.rescan(nor, type, pf);
        } else if (db == BlastFile.DATABASE_NT) {
            nor.setStatus("Reading NT chunks...");
            ntFile.rescan(nor, type, pf);
            options.getTaxonomy().prepareTreePlot();
            //options.getTaxonomy().displayTaxonomy();
        } else if (db == BlastFile.DATABASE_BACTERIA) {
            nor.setStatus("Reading bacteria chunks...");
            bacteriaFile.rescan(nor, type, pf);
        }
    }
    
    private boolean dirExists(String directory) {
        boolean isDir = true;
        File dir = new File(directory);
        if (dir.exists()) {
            if (!dir.isDirectory()) {
                isDir = false;
                System.out.println("Error: " + directory + " not a directory");
            }
        } else {
            System.out.println("Error: directory " + directory + " doesn't exist");
            isDir = false;
        }
        
        return isDir;
    }
        
    public BlastFile getCardFile() {
        return cardFile;
    }

    public BlastFile getNtFile() {
        return ntFile;
    }

    public BlastFile getBacteriaFile() {
        return bacteriaFile;
    }
    
    public void writeCardSummaries(int type, int pf) {
        BlastChunkSet chunkSet = cardFile.getChunkSet(type, pf); 
        String reporterDir = sampleDirectory + File.separator + "reporter";
        File f = new File(reporterDir);
        File sd = new File(sampleDirectory);
        
        if ((!f.exists()) || (!f.isDirectory())) {
            f.mkdir();
        }
        
        
        for (int c=0; c<=chunkSet.getNumberOfChunks(); c++) {
            String filename = reporterDir + File.separator + sd.getName() + "_" + BlastFile.getTypeFromInt(type) + "_" + BlastFile.getPassFailFromInt(pf) + "_card_summary_" + c + ".txt";
            chunkSet.countHits(c);
            chunkSet.writeSummaryFile(filename);            
        }        
    }

    public void writeChunkTimesFile(int type, int pf) {
        BlastChunkSet chunkSet = cardFile.getChunkSet(type, pf); 
        String reporterDir = sampleDirectory + File.separator + "reporter";
        File f = new File(reporterDir);
        File sd = new File(sampleDirectory);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        if ((!f.exists()) || (!f.isDirectory())) {
            f.mkdir();
        }
        
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(reporterDir + File.separator + "chunktimes.txt")); 

            for (int c=0; c<=chunkSet.getNumberOfChunks(); c++) {
                long lastModified = chunkSet.getChunk(c).getLastModified();
                pw.println(c + "\t" + dateFormat.format(lastModified));
            }
            
            pw.close();
        } catch (IOException e) {
            System.out.println("writeCardSummaries exception:");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
}
