import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CalculatorApp {

  private static void launchFrame() {
    CalculatorGUI calcGUI = new CalculatorGUI();
    Frame f = new Frame("Calculator");

    f.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent event) {
	System.exit(0);
      }
    });
    f.setSize(200, 200);
    f.add(calcGUI.getContentPanel(), BorderLayout.CENTER);
    f.setVisible (true);
  }

  public static void main(String args[]) {
    launchFrame();
  }
}
