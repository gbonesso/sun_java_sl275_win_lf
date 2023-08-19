public class PrintJob {
  private int pages;
  private String jobName;

  public PrintJob(String name, int pages) {
    this.jobName = name;
    this.pages = pages;
  }

  public String getJobName() {
    return jobName;
  }
  public int getNumberOfPages() {
    return pages;
  }
}
