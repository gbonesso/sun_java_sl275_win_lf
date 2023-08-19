public class TestPrinter {
  public static void main(String[] agrs) {
    // Fred will produce five small jobs quickly
    Producer fred = new Producer("Fred", 5, 5, 8000);
    // Elizabeth will produce two large jobs in slowly
    Producer elizabeth = new Producer("Elizabeth", 3, 25, 20000);
    // Simon is random
    Producer simon = new Producer("Simon", 3, 5, 10000);

    Thread printer_thread = new Thread(Printer.getPrinter());
    printer_thread.setPriority(Thread.MAX_PRIORITY);
    printer_thread.start();

    Thread t1 = new Thread(fred);
    t1.start();
    Thread t2 = new Thread(elizabeth);
    t2.start();
    Thread t3 = new Thread(simon);
    t3.start();

    // wait until all producer threads have finished,
    // then stop the printer thread
    try {
      t1.join();
      t2.join();
      t3.join();
    } catch (InterruptedException e) {}
    Printer.getPrinter().halt();
  }
}
