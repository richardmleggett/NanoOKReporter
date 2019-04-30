package nanookreporter;

import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author leggettr
 */
public class BlastFile {
    public final static int TYPE_2D = 0;
    public final static int TYPE_TEMPLATE = 1;
    public final static int READTYPE_FAIL = 0;
    public final static int READTYPE_PASS = 1;
    public final static int CHUNKSET_2D_PASS = 0;
    public final static int CHUNKSET_2D_FAIL = 1;
    public final static int CHUNKSET_TEMPLATE_PASS = 2;
    public final static int CHUNKSET_TEMPLATE_FAIL = 3;  
    public final static int DATABASE_NT = 1;
    public final static int DATABASE_BACTERIA = 2;
    public final static int DATABASE_CARD = 3;        
    public ArrayList<BlastChunkSet> chunkSet = new ArrayList<BlastChunkSet>();
    private NanoOKReporterOptions options;
    public String midfix;
    private int taxonomyTreeId;
    
    public BlastFile(NanoOKReporterOptions o, String directory, String m, int i) {
        midfix = m;
        options = o;
        taxonomyTreeId = i;
        
        System.out.println("BlastFile "+directory+" taxonomy id "+i);
        chunkSet.add(CHUNKSET_2D_PASS, new BlastChunkSet(options, directory, "all_2D_pass", midfix, -1));
        chunkSet.add(CHUNKSET_2D_FAIL, new BlastChunkSet(options, directory, "all_2D_fail", midfix, -1));
        chunkSet.add(CHUNKSET_TEMPLATE_PASS, new BlastChunkSet(options, directory, "all_Template_pass", midfix, taxonomyTreeId));
        chunkSet.add(CHUNKSET_TEMPLATE_FAIL, new BlastChunkSet(options, directory, "all_Template_fail", midfix, -1));

        //chunkSet.add(CHUNKSET_2D_PASS, new BlastChunkSet(directory, "all_2D_pass", midfix));
        //chunkSet.add(CHUNKSET_2D_FAIL, new BlastChunkSet(directory, "bambi_4_20102016_fail_2d", "blast_card"));
        //chunkSet.add(CHUNKSET_TEMPLATE_PASS, new BlastChunkSet(directory, "all_Template_pass", midfix));
        //chunkSet.add(CHUNKSET_TEMPLATE_FAIL, new BlastChunkSet(directory, "all_Template_fail", midfix));
    }
    
    public void rescanAll(NanoOKReporter nor) {
        for (int i=0; i<chunkSet.size(); i++) {
            chunkSet.get(i).scanForChunks(nor);
        }
    }
    
    public void rescan(NanoOKReporter nor, int type, int pf) {
        chunkSet.get(getIndexIntoChunkSet(type, pf)).scanForChunks(nor);
    }
    
    private int getIndexIntoChunkSet(int type, int pf) {
        int index = 0;
        
        if ((type == TYPE_2D) && (pf == READTYPE_PASS)) {
            index = CHUNKSET_2D_PASS;
        } else if ((type == TYPE_2D) && (pf == READTYPE_FAIL)) {
            index = CHUNKSET_2D_FAIL;
        } else if ((type == TYPE_TEMPLATE) && (pf == READTYPE_PASS)) {
            index = CHUNKSET_TEMPLATE_PASS;
        } else if ((type == TYPE_TEMPLATE) && (pf == READTYPE_FAIL)) {
            index = CHUNKSET_TEMPLATE_FAIL;
        }
        
        return index;
    }
    
    public BlastChunkSet getChunkSet(int type, int pf) {
        return chunkSet.get(getIndexIntoChunkSet(type, pf));
    }
    
    public void countSet(int type, int pf, BlastMatchCriteria bmc) {
        int index = getIndexIntoChunkSet(type, pf);
        BlastChunkSet cs = chunkSet.get(index);        
        cs.countHits(cs.getLastChunkNumber(), bmc);
    }
    
    public void countUptoSet(int type, int pf, int n, BlastMatchCriteria bmc) {
        int index = getIndexIntoChunkSet(type, pf);
        BlastChunkSet cs = chunkSet.get(index);        
        cs.countHits(n, bmc);
    }
    
    public void updateTable(JTable table, int type, int pf) {
        int index = getIndexIntoChunkSet(type, pf);
        BlastChunkSet cs = chunkSet.get(index);        
        cs.updateTable(table);
    }
    
    /**
     * Get a type string (Template, Complement, 2D) from an integer.
     * @param n integer to convert
     * @return type String
     */
    public static String getTypeFromInt(int n) {
        String typeString;
        
        switch(n) {
            case TYPE_TEMPLATE: typeString = "Template"; break;
            //case TYPE_COMPLEMENT: typeString = "Complement"; break;
            case TYPE_2D: typeString = "2D"; break;
            default: typeString = "Unknown"; break;
        }
        
        return typeString;
    }
    
    public static String getPassFailFromInt(int n) {
        String typeString;
        
        switch(n) {
            case READTYPE_PASS: typeString = "pass"; break;
            case READTYPE_FAIL: typeString = "fail"; break;
            default: typeString = "Unknown"; break;
        }
        
        return typeString;
    }
}
