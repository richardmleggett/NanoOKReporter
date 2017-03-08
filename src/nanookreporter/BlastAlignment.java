package nanookreporter;

public class BlastAlignment {
    // qseqid sseqid pident length mismatch gapopen qstart qend sstart send evalue bitscore stitle
    private String qseqid;
    private String sseqid;
    private float pident;
    private int length;
    private int mismatch;
    private int gapopen;
    private int qstart;
    private int qend;
    private int sstart;
    private int send;
    private double evalue;
    private double bitscore;
    private String stitle;
    private boolean validAlignment = false;
    
    public BlastAlignment(String a) {
        String fields[] = a.split("\\s+", 13);
        
        if (fields.length == 13) {
            try {
                qseqid = fields[0];
                sseqid = fields[1];
                pident = Float.parseFloat(fields[2]);
                length = Integer.parseInt(fields[3]);
                mismatch = Integer.parseInt(fields[4]);
                gapopen = Integer.parseInt(fields[5]);
                qstart = Integer.parseInt(fields[6]);
                qend = Integer.parseInt(fields[7]);
                sstart = Integer.parseInt(fields[8]);
                send = Integer.parseInt(fields[9]);
                evalue = Double.parseDouble(fields[10]);
                bitscore = Double.parseDouble(fields[11]);
                stitle = fields[12];
                validAlignment = true;
            } catch (Exception e) {
                System.out.println("Error parsing alignment - incomplete file?");
            }
        } else {
            System.out.println("Unknown alignment - incomplete file?");
        }
    }
    
    public boolean isValidAlignment() {
        return validAlignment;
    }
    
    public String getQueryId() {
        return qseqid;
    }
    
    public double getEValue() {
        return evalue;
    }
    
    public float getPercentIdentity() {
        return pident;
    }
    
    public String getSubjectId() {
        return sseqid;
    }
    
    public String getSubjectTitle() {
        return stitle;
    }
}
