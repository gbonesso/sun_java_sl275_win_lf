import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class ChatClient {

  private final String DEFAULT_SERVER_IP = "127.0.0.1";
  private final int DEFAULT_SERVER_PORT = 2000;

  private String serverIP = DEFAULT_SERVER_IP;
  private int serverPort = DEFAULT_SERVER_PORT;

  private Socket connection = null;
  private BufferedReader serverIn = null;
  private PrintStream serverOut = null;

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

    // initialize server IP and port information
    String serverIP = System.getProperty("serverip");
    if(serverIP != null) this.serverIP = serverIP;
    String serverPort = System.getProperty("serverport");
    if(serverPort != null) this.serverPort = Integer.parseInt(serverPort);
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

    doConnect();

    frame.setSize(510,210);
    frame.setVisible(true);
  }

  private void doConnect() {
    try {
      connection = new Socket(serverIP,serverPort);
      serverIn = 
        new BufferedReader(new InputStreamReader(connection.getInputStream()));
      serverOut = new PrintStream(connection.getOutputStream());
      Thread t = new Thread(new RemoteReader());
      t.start();
    }
    catch(Exception e) {
      System.out.println("Unable to connect to server!");
      e.printStackTrace();
    }
  }
    
  private void copyText() {
    String text = input.getText();
    serverOut.println(usernames.getSelectedItem()+": "+text);
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
 
   public void actionPerformed(ActionEvent e) {
     setVisible(false);
   }
  }

  private class RemoteReader implements Runnable {
    private boolean keepListening = true;

    public void run() {
      while(keepListening == true) {
        try {
          String nextLine = serverIn.readLine();
          output.append(nextLine+"\n");
        }
        catch(Exception e) {
          keepListening = false;
          System.out.println("Error while reading from server.");
          e.printStackTrace();
        }
      }
    }
  }

  public static void main(String[] args) {
    ChatClient c = new ChatClient();
    c.launchFrame();
  }
}
