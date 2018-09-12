package nanookreporter;

import java.util.TimerTask;

public class RefreshTask extends TimerTask {
    NanoOKReporterOptions options;
    NanoOKReporter reporter;
    
    public RefreshTask(NanoOKReporterOptions o, NanoOKReporter r) {
        options = o;
        reporter = r;
    }

    public void run() {
        System.out.println("Timer");
        reporter.triggerRefresh();
    }    
}
