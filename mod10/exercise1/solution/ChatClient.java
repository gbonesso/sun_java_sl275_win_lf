import java.awt.*;

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
    Frame f = new Frame("Chat Room");

    // Use the Border Layout for the frame
    f.setLayout(new BorderLayout());

    f.add(output, BorderLayout.WEST);
    f.add(input, BorderLayout.SOUTH);

    // Create the button panel
    Panel p1 = new Panel(); 
    p1.add(sendButton);
    p1.add(quitButton);

    // Add the button panel to the center
    f.add(p1, BorderLayout.CENTER);

    f.setSize(440,210);  // Yeah!  Why are we doing this?
    f.setVisible(true);
  }

  public static void main(String[] args) {
    ChatClient c = new ChatClient();
    c.launchFrame();
  }
}
