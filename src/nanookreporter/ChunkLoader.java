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

    public NanoOKReporter frame;
    public NanoOKSample sample;

    public ChunkLoader(NanoOKReporter f, NanoOKSample s) {
        frame = f;
        sample = s;
    }
    
    @Override
    public String doInBackground() throws IOException {
        frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        sample.readData(frame);
        frame.updateStats();
        frame.updateTableAmr();
        frame.updateTableTaxon();
        frame.setStatus("Ok");
        
        return null;
    }  
    
    @Override
    public void done() {
        frame.loadFinished();
        frame.setCursor(Cursor.getDefaultCursor());
    }
}
