package nanookreporter;

import java.awt.Cursor;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.concurrent.ExecutionException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class ChunkLoader extends SwingWorker {
    private NanoOKReporter frame;
    private NanoOKSample sample;
    private int database;
    private int type;
    private int pf;

    public ChunkLoader(NanoOKReporter f, NanoOKSample s, int d, int t, int p) {
        frame = f;
        sample = s;
        database = d;
        type = t;
        pf = p;
    }
    
    @Override
    public String doInBackground() throws IOException {
        frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        sample.readData(database, type, pf, frame);
        
        if (database == BlastFile.DATABASE_CARD) {
            frame.updateTableCard();
        } else if (database == BlastFile.DATABASE_NT) {
            System.out.println("In doInBackground");
            frame.updateSlidersNt();
            frame.updateTableNt();
        } else if (database == BlastFile.DATABASE_BACTERIA) {
            frame.updateTableBacteria();
        }
        
        frame.setStatus("Ok");
        
        return null;
    }  
    
    @Override
    public void done() {
        frame.loadFinished();
        frame.setCursor(Cursor.getDefaultCursor());
    }
}
