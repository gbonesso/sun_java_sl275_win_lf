import java.awt.*;
import java.awt.event.*;

public class ChatClient {

  private TextArea output;
  private TextField input;
  private Button sendButton;
  private Button quitButton;

  public ChatClient() {
    output = new TextArea(10,50);
    input = new TextField(50);
    sendButton = new Button("Send");
    quitButton = new Button("Quit");
  }

  public void launchFrame() {
    Frame frame = new Frame("Chat Room");

    frame.setLayout(new BorderLayout());

    frame.add(output,BorderLayout.WEST);
    frame.add(input,BorderLayout.SOUTH);

    Panel p1 = new Panel(); 
    p1.add(sendButton);
    p1.add(quitButton);
    frame.add(p1,BorderLayout.CENTER);

    // Attach listener to the appropriate components
    sendButton.addActionListener(new SendHandler());
    frame.addWindowListener(new CloseHandler());
    input.addActionListener(new InputHandler());
    quitButton.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
	  System.exit(0);
	}
    });

    frame.setSize(440,210);
    frame.setVisible(true);
  }

  private void copyText() {
    String text = input.getText();
    output.setText(output.getText()+text+"\n");
    input.setText("");
  }

  private class SendHandler implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      copyText();
    }
  }

  private class CloseHandler extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
      System.exit(0);
    }
  }
    
  private class InputHandler implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      copyText(); 
    }
  }

  public static void main(String[] args) {
    ChatClient c = new ChatClient();
    c.launchFrame();
  }
}
