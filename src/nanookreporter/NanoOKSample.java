package nanookreporter;

import java.io.File;

public class NanoOKSample {
    String cardDir;
    String ntDir;
    BlastFile cardFile;
    BlastFile ntFile;
    
    public NanoOKSample(String directory) {
        File sampleDir = new File(directory);
        if (dirExists(directory)) {
            cardDir = directory + File.separator + "blastn_card";
            readCardData(cardDir);
            if (dirExists(cardDir)) {
                ntDir = directory + File.separator + "blastn_card";                
            }
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
    
    private void readCardData(String directory) {
        cardFile = new BlastFile(directory, "blastn_card");
        cardFile.rescanAll();
    }
    
    public BlastFile getCardFile() {
        return cardFile;
    }
}
