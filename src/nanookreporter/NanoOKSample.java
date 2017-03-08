package nanookreporter;

import java.io.File;

public class NanoOKSample {
    String sampleDirectory;
    String cardDir;
    String ntDir;
    BlastFile cardFile;
    BlastFile ntFile;
    BlastFile bacteriaFile;
    
    public NanoOKSample(String directory) {
        sampleDirectory = directory;
        File sampleDir = new File(sampleDirectory);
        if (dirExists(sampleDirectory)) {
            cardFile = new BlastFile(sampleDirectory + File.separator + "blastn_card", "blastn_card");
            ntFile = new BlastFile(sampleDirectory + File.separator + "blastn_nt", "blastn_nt");
            bacteriaFile = new BlastFile(sampleDirectory + File.separator + "blastn_bacteria", "blastn_bacteria");
        }
    }
    
    public void readData(int db, int type, int pf, NanoOKReporter nor) {
        if (db == BlastFile.DATABASE_CARD) {
            nor.setStatus("Reading CARD chunks...");
            cardFile.rescan(nor, type, pf);
        } else if (db == BlastFile.DATABASE_NT) {
            nor.setStatus("Reading NT chunks...");
            ntFile.rescan(nor, type, pf);
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

}
