public class Producer implements Runnable {
  private int sizeOfJobs;
  private int numberOfJobs;
  private int delayBetweenJobs;
  private String producerName;

  public Producer(String name, int number, int size, int delay) {
    producerName = name;
    numberOfJobs = number;
    sizeOfJobs = size;
    delayBetweenJobs = delay;
  }

  public void run() {
    Printer   printer = Printer.getPrinter();
    PrintJob job = null;
    boolean  job_posted = false;

    for ( int i = 1; i <= numberOfJobs; i++ ) {

      // generate new print job
      job = new PrintJob(producerName + "#" + i, sizeOfJobs);

      do {
	try {
	  System.out.println("P: Adding job '" + job.getJobName() + "' to the queue");
	  printer.addJob(job);
	  job_posted = true;
	} catch (FullQueueException e) {
	  System.out.println("P: Print queue is full, trying again...");
	}
      } while ( ! job_posted );

      // sleep between jobs
      try {
	Thread.sleep(delayBetweenJobs);
      } catch (InterruptedException e) {
	System.out.println("P." + producerName + " was interrupted.");
      }

    }
  }
}
