package nanookreporter;

import java.io.File;

public class NanoOKSample {
    String sampleDirectory;
    String cardDir;
    String ntDir;
    BlastFile cardFile;
    BlastFile ntFile;
    
    public NanoOKSample(String directory) {
        sampleDirectory = directory;
        File sampleDir = new File(sampleDirectory);
        if (dirExists(sampleDirectory)) {
            cardFile = new BlastFile(sampleDirectory + File.separator + "blastn_card", "blastn_card");
            ntFile = new BlastFile(sampleDirectory + File.separator + "blastn_nt", "blastn_nt");
        }
    }
    
    public void readData(NanoOKReporter nor) {
        //File sampleDir = new File(sampleDirectory);
        //if (dirExists(sampleDirectory)) {
        //    cardDir = sampleDirectory + File.separator + "blastn_card";
            nor.setStatus("Reading CARD chunks...");
            readCardData(nor);
      //      if (dirExists(cardDir)) {
      //          ntDir = sampleDirectory + File.separator + "blastn_card";                
      //      }

     //       ntDir = sampleDirectory + File.separator + "blastn_nt";
            nor.setStatus("Reading NT chunks...");
            readNtData(nor);
     //       if (dirExists(ntDir)) {
      //          ntDir = sampleDirectory + File.separator + "blastn_nt";                
      //      }

      //  }
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
    
    private void readCardData(NanoOKReporter nor) {
        cardFile.rescanAll(nor);
    }

    private void readNtData(NanoOKReporter nor) {
        ntFile.rescanAll(nor);
    }

    
    public BlastFile getCardFile() {
        return cardFile;
    }

    public BlastFile getNtFile() {
        return ntFile;
    }
}
