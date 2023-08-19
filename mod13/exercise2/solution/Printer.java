
public class Printer implements Runnable {
  private static final int MILLISEC_PER_PAGE = 500;

  /**
   * The Printer class will also implement the Singleton DP for convience.
   */
  private static final Printer singleton = new Printer();
  public static Printer getPrinter() {
    return singleton;
  }
  private Printer() {}

  private Queue   printQueue = new CircularQueue(10);
  private boolean stateIsRunning = true;

  public synchronized void halt() {
    stateIsRunning = false;
    notify();
  }

  public synchronized void addJob(PrintJob job)
        throws FullQueueException {
    printQueue.addBack(job);
    notify();
  }

  private synchronized PrintJob getJob()
        throws EmptyQueueException {
    return (PrintJob) printQueue.removeFront();
  }

  public void run() {
    PrintJob job = null;

    System.out.println("  C: Print manager is starting up.");
    while (stateIsRunning) {
      job = null;

      synchronized (this) {
	while (stateIsRunning && printQueue.isEmpty()) {
	  try {
	    System.out.println("  C: Waiting on a job to print.");
	    wait();
	  }
	  catch (InterruptedException ie) {
	  } 
	} 
	if (stateIsRunning) { // printQueue can't be empty
	  job = getJob();
	}
      }

      if (job != null) {
	  System.out.println("  C: Starting job '" + job.getJobName() + "'");

	  // process job
	  try {
	    Thread.sleep(job.getNumberOfPages() * MILLISEC_PER_PAGE);
	  } catch (InterruptedException e) {}

	  System.out.println("  C: Completed job '" + job.getJobName() + "'");
      }
    }

    // Process remaining jobs before halting
    while (!printQueue.isEmpty()) {
      job = getJob();
      System.out.println("  C: Starting job '" + job.getJobName() + "'");
      // process job
      try {
	Thread.sleep(job.getNumberOfPages() * MILLISEC_PER_PAGE);
      } catch (InterruptedException e) {}
      System.out.println("  C: Completed job '" + job.getJobName() + "'");
    }

    System.out.println("  C: Print manager is halted.");
  }
}
