public class TestThreeThreads {
  public static void main(String[] args) {
    Runnable prog = new PrintMe();
    Thread t1 = new Thread(prog);
    Thread t2 = new Thread(prog);
    Thread t3 = new Thread(prog);

    t1.setName("Larry");
    t2.setName("Curly");
    t3.setName("Moe");

    t1.start();
    t2.start();
    t3.start();
  }
}
