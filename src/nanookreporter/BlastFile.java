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
    public ArrayList<BlastChunkSet> chunkSet = new ArrayList<BlastChunkSet>();
    
    public BlastFile(String directory, String midfix) {
        chunkSet.add(new BlastChunkSet(directory, "all_2D_pass", midfix));
        chunkSet.add(new BlastChunkSet(directory, "all_2D_fail", midfix));
        chunkSet.add(new BlastChunkSet(directory, "all_Template_pass", midfix));
        chunkSet.add(new BlastChunkSet(directory, "all_Template_fail", midfix));
    }
    
    public void rescanAll() {
        for (int i=0; i<chunkSet.size(); i++) {
            chunkSet.get(i).scanForChunks();
        }
    }
    
    private int getIndexIntoChunkSet(int type, int pf) {
        int index = 0;
        
        if ((type == TYPE_2D) && (pf == TYPE_PASS)) {
            index = 0;
        } else if ((type == TYPE_2D) && (pf == TYPE_FAIL)) {
            index = 1;
        } else if ((type == TYPE_TEMPLATE) && (pf == TYPE_PASS)) {
            index = 2;
        } else if ((type == TYPE_TEMPLATE) && (pf == TYPE_FAIL)) {
            index = 3;
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
