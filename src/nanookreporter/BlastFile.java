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
    public final static int TYPE_FAIL = 0;
    public final static int TYPE_PASS = 1;
    public final static int CHUNKSET_2D_PASS = 0;
    public final static int CHUNKSET_2D_FAIL = 1;
    public final static int CHUNKSET_TEMPLATE_PASS = 2;
    public final static int CHUNKSET_TEMPLATE_FAIL = 3;  
    public final static int DATABASE_NT = 1;
    public final static int DATABASE_BACTERIA = 2;
    public final static int DATABASE_CARD = 3;        
    public ArrayList<BlastChunkSet> chunkSet = new ArrayList<BlastChunkSet>();
    public String midfix;
    
    public BlastFile(String directory, String m) {
        midfix = m;
        chunkSet.add(CHUNKSET_2D_PASS, new BlastChunkSet(directory, "all_2D_pass", midfix));
        chunkSet.add(CHUNKSET_2D_FAIL, new BlastChunkSet(directory, "all_2D_fail", midfix));
        chunkSet.add(CHUNKSET_TEMPLATE_PASS, new BlastChunkSet(directory, "all_Template_pass", midfix));
        chunkSet.add(CHUNKSET_TEMPLATE_FAIL, new BlastChunkSet(directory, "all_Template_fail", midfix));
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
        
        if ((type == TYPE_2D) && (pf == TYPE_PASS)) {
            index = CHUNKSET_2D_PASS;
        } else if ((type == TYPE_2D) && (pf == TYPE_FAIL)) {
            index = CHUNKSET_2D_FAIL;
        } else if ((type == TYPE_TEMPLATE) && (pf == TYPE_PASS)) {
            index = CHUNKSET_TEMPLATE_PASS;
        } else if ((type == TYPE_TEMPLATE) && (pf == TYPE_FAIL)) {
            index = CHUNKSET_TEMPLATE_FAIL;
        }
        
        return index;
    }
    
    public BlastChunkSet getChunkSet(int type, int pf) {
        return chunkSet.get(getIndexIntoChunkSet(type, pf));
    }
    
    public void countSet(int type, int pf) {
        int index = getIndexIntoChunkSet(type, pf);
        BlastChunkSet cs = chunkSet.get(index);        
        cs.countHits(cs.getNumberOfChunks());
    }
    
    public void updateTable(JTable table, int type, int pf) {
        int index = getIndexIntoChunkSet(type, pf);
        BlastChunkSet cs = chunkSet.get(index);        
        cs.updateTable(table);
    }
    
}
