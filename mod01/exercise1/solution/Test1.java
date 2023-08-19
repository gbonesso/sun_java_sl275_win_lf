public class Test1 {
  public static void main(String[] args) {
    System.out.println("What's wrong with this program?");
  }
}

// If a public class is declared in a source file, the file and the
// public class must share the same root name. This class can be
// compiled if it is made non-public, or moved into a different source
// file

class TestAnother1 {
  public static void main(String[] args) {
    System.out.println("What's wrong with this program?");
  }
}
