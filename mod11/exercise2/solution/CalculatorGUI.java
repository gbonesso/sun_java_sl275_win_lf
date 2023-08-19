import java.awt.*;
import java.awt.event.*;

public class CalculatorGUI {
  // Buttons and a place to put them.
  private Button key0, key1, key2, key3, key4;
  private Button key5, key6, key7, key8, key9;
  private Button keyequal, keyplus, keyminus;
  private Button keyperiod, keymult, keydiv;
  private Panel buttonArea;
  // Where answer will be displayed.
  private Label answer;
  // The model
  private Calculator calculator;
  private boolean    readyForNextNumber;

  public CalculatorGUI() {
    calculator = new Calculator();
    readyForNextNumber = true;

    answer = new Label("0.0",Label.RIGHT);

    key0 = new Button("0");
    key1 = new Button("1");
    key2 = new Button("2");
    key3 = new Button("3");
    key4 = new Button("4");
    key5 = new Button("5");
    key6 = new Button("6");
    key7 = new Button("7");
    key8 = new Button("8");
    key9 = new Button("9");
    keyequal = new Button("=");
    keyplus = new Button("+");
    keyminus = new Button("-");
    keymult = new Button("*");
    keydiv = new Button("/");
    keyperiod = new Button(".");
    buttonArea = new Panel();
  }

  public void launchFrame() {
    buttonArea.setLayout(new GridLayout(4,4));

    buttonArea.add(key7);
    buttonArea.add(key8);
    buttonArea.add(key9);
    buttonArea.add(keyplus);
    buttonArea.add(key4);
    buttonArea.add(key5);
    buttonArea.add(key6);
    buttonArea.add(keyminus);
    buttonArea.add(key1);
    buttonArea.add(key2);
    buttonArea.add(key3);
    buttonArea.add(keymult);
    buttonArea.add(key0);
    buttonArea.add(keyperiod);
    buttonArea.add(keyequal);
    buttonArea.add(keydiv);

    // Set up event handling
    OpButtonHanlder op_handler = new OpButtonHanlder();
    NumberButtonHanlder number_handler = new NumberButtonHanlder();
    key0.addActionListener(number_handler);
    key1.addActionListener(number_handler);
    key2.addActionListener(number_handler);
    key3.addActionListener(number_handler);
    key4.addActionListener(number_handler);
    key5.addActionListener(number_handler);
    key6.addActionListener(number_handler);
    key7.addActionListener(number_handler);
    key8.addActionListener(number_handler);
    key9.addActionListener(number_handler);
    keyperiod.addActionListener(number_handler);
    keyplus.addActionListener(op_handler);
    keyminus.addActionListener(op_handler);
    keymult.addActionListener(op_handler);
    keydiv.addActionListener(op_handler);
    keyequal.addActionListener(op_handler);

    // Create a Frame
    Frame f = new Frame("Calculator");
    f.addWindowListener(new WindowAdapter() {
	public void windowClosing(WindowEvent e) {
	  System.exit(0);
	}
    });
    f.setSize(200, 200);

    f.add(answer, BorderLayout.NORTH);
    f.add(buttonArea, BorderLayout.CENTER);
    f.setVisible (true);
  }

  private class OpButtonHanlder implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      char  operator = event.getActionCommand().charAt(0);
      String result = "";
      switch  ( operator ) {
      case '+':
	result = calculator.opAdd(answer.getText());
	break;
      case '-':
	result = calculator.opSubtract(answer.getText());
	break;
      case '*':
	result = calculator.opMultiply(answer.getText());
	break;
      case '/':
	result = calculator.opDivide(answer.getText());
	break;
      case '=':
	result = calculator.opEquals(answer.getText());
	break;
      }
      answer.setText(result);
      readyForNextNumber = true;
    }
  }

  private class NumberButtonHanlder implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      if ( readyForNextNumber ) {
	answer.setText(event.getActionCommand());
	readyForNextNumber = false;
      } else {
	answer.setText(answer.getText() + event.getActionCommand().charAt(0));
      }
    }
  }

  public static void main(String args[]) {
    CalculatorGUI calcGUI = new CalculatorGUI();
    calcGUI.launchFrame();
  }
}
