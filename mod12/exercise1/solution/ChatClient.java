import java.awt.*;
import java.awt.event.*;

public class ChatClient {

  private TextArea output;
  private TextField input;
  private Button sendButton;
  private Button quitButton;
  private Choice usernames;
  private MenuItem aboutMenuItem;
  private MenuItem quitMenuItem;
  private Dialog aboutDialog;

  public ChatClient() {
    output = new TextArea(10,50);
    input = new TextField(50);
    sendButton = new Button("Send");
    quitButton = new Button("Quit");
    usernames = new Choice();
    aboutMenuItem = new MenuItem("About");
    quitMenuItem = new MenuItem("Quit");
  }

  public void launchFrame() {
    Frame frame = new Frame("Chat Room");

    frame.setLayout(new BorderLayout());

    frame.add(output,BorderLayout.WEST);
    frame.add(input,BorderLayout.SOUTH);

    usernames.add("Grand Poobah");
    usernames.add("The Man");
    usernames.add("Java Geek");

    Panel p1 = new Panel(); 
    p1.add(sendButton);
    p1.add(quitButton);
    p1.add(usernames);
    frame.add(p1,BorderLayout.CENTER);

    MenuBar mb = new MenuBar();
    Menu file = new Menu("File");
    file.add(quitMenuItem);
    Menu help = new Menu("Help");
    help.add(aboutMenuItem);
    mb.add(file);
    mb.setHelpMenu(help);
    frame.setMenuBar(mb);

    // Create the aboutDialog once, it will be reused later
    aboutDialog = new AboutDialog(frame,"About",true);

    // Attach listener to the appropriate components
    sendButton.addActionListener(new SendHandler());
    frame.addWindowListener(new CloseHandler());
    input.addActionListener(new InputHandler());
    // Use an anonymous inner class for variety.
    quitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	System.exit(0);
      }
    });
    // Attach listeners to menu items
    quitMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	System.exit(0);
      }
    });
    aboutMenuItem.addActionListener(new AboutHandler());

    frame.setSize(440,210);
    frame.setVisible(true);
  }

  private void copyText() {
    String text = input.getText();
    output.setText(output.getText()+usernames.getSelectedItem()+": "+text+"\n");
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

  private class AboutHandler implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      aboutDialog.setVisible(true);
    }
  }

  private class AboutDialog extends Dialog implements ActionListener  {
    public AboutDialog(Frame parent, String title, boolean modal) {
      super(parent,title,modal);
      add(new Label("The ChatClient is a neat tool that allows you to talk " +
                  "to other ChatClients via a ChatServer"),BorderLayout.NORTH);
      Button b = new Button("OK");
      add(b,BorderLayout.SOUTH);
      pack();
      b.addActionListener(this);
    }
    // Closes itself when the OK button is pushed
    public void actionPerformed(ActionEvent e) {
      setVisible(false);
    }
  }

  public static void main(String[] args) {
    ChatClient c = new ChatClient();
    c.launchFrame();
  }
}
