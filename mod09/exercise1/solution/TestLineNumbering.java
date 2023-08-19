import java.io.*;

public class TestLineNumbering {
  public static void main (String args[]) {
    // Verify that at least one argument (the file) was specified
    if ( args.length == 0 ) {
      System.err.println("Error: no file specified on the command-line.");
      System.exit(-1);
    }

    // Declare the file variable
    File file = new File(args[0]);

    // Declare keyboard input string
    String s;

    // Declare input line counter
    int counter = 1;

    try {
      // Create a buffered reader to read
      // each line from the keyboard.
      InputStreamReader ir = new InputStreamReader(System.in);
      BufferedReader in = new BufferedReader(ir);

      // Create output file and 
      PrintWriter out = new PrintWriter(new FileWriter(file));

      System.out.print("Enter file text.  ");
      System.out.println("Type ctrl-d (or ctrl-z) to exit.");

      // Read each input line and echo it to the screen.
      while ((s = in.readLine()) != null) {
	out.println("" + counter + ": " + s);
	counter++;
      }

      // Close the buffered reader.
      in.close();
      // Close the file print writer.
      out.close();

    } catch (IOException e) { // Catch any IO exceptions.
      e.printStackTrace();
    }
  }
}
