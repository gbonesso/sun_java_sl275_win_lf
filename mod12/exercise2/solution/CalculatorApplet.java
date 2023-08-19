// <APPLET CODE="CalculatorApplet.class" WIDTH=200 HEIGHT=200></APPLET>
import java.applet.Applet;
import java.awt.BorderLayout;

public class CalculatorApplet extends Applet {
  public void init() {
    CalculatorGUI calcGUI = new CalculatorGUI();
    setLayout(new BorderLayout());
    add(calcGUI.getContentPanel(), BorderLayout.CENTER);
  }
}
