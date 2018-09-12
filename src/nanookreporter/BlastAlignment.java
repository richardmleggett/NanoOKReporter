package nanookreporter;

import java.util.ArrayList;

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
    private int taxonId = -1;
    private boolean validAlignment = false;
    private long leafNode = 0;
    private ArrayList<Long> taxonIdPath;
    
    public BlastAlignment(String a) {
        String fields[] = a.split("\t", 14);
        
        if (fields.length >= 13) {
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
                if (fields.length == 14) {
                    taxonId = Integer.parseInt(fields[13]);
                } else {
                    //System.out.println("No taxonid");
                }
            } catch (Exception e) {
                System.out.println("Error parsing alignment - incomplete file?");
            }
        } else {
            System.out.println("Unknown alignment - incomplete file? ["+a+"]");
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
    
    public Double getBitScore() {
        return bitscore;
    }
    
    public void cacheTaxonIdPath(Taxonomy t) {
        if (taxonId == -1) {
            Long id = t.parseTaxonomyToId(stitle);

            if (id != null) {
                leafNode = id;
            } else {
                leafNode = 0;
            }
        } else {
            leafNode = taxonId;
        }

        taxonIdPath = t.getTaxonIdPathFromId(leafNode);
    }
    
    public ArrayList getTaxonIdPath() {
        return taxonIdPath;
    }
    
    public int getTaxonLevel() {
        if (taxonIdPath != null) {
            return taxonIdPath.size(); // 1-offset
        }
        return 0;
    }
    
    public long getLeafNode() {
        return leafNode;
    }
    
    // Note level is 1-offset
    public long getTaxonNode(int level) {
        if (level <= taxonIdPath.size()) {
            return taxonIdPath.get(taxonIdPath.size() - level);
        }
        return 0;
    }
    
    public int getQueryStart() {
        return qstart;
    }

    public int getQueryEnd() {
        return qend;
    }

    public int getSubjectStart() {
        return sstart;
    }

    public int getSubjectEnd() {
        return send;
    }

}
