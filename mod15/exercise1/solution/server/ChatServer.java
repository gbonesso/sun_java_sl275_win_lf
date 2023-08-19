import java.io.*;
import java.net.*;
import java.util.*;
import java.applet.*;

public class ChatServer implements Runnable {

  public final static int DEFAULT_PORT = 2000;
  public final static int DEFAULT_MAX_BACKLOG = 5;
  public final static int DEFAULT_MAX_CONNECTIONS = 20;
  public final static String DEFAULT_HOST_FILE = "hosts.txt";
  public final static String DEFAULT_SOUND_FILE = "file:gong.au";
  public final static String MAGIC = "Yippy Skippy";

  private String magic = MAGIC;

  private int port = DEFAULT_PORT;
  private int backlog = DEFAULT_MAX_BACKLOG;
  private int numConnections = 0;
  private int maxConnections = DEFAULT_MAX_CONNECTIONS;
  private String hostfile = DEFAULT_HOST_FILE;
  private String soundfile = DEFAULT_SOUND_FILE;
  private Vector connections = null;
  private AudioClip connectSound = null;
  private Hashtable hostInfo = new Hashtable(15);

  public static void main(String[] args) {
    ChatServer cs = new ChatServer();
    cs.go();
  }

  public void go()
   {
    String portString = System.getProperty("port");
    if(portString != null) port = Integer.parseInt(portString);

    String backlogString = System.getProperty("backlog");
    if(backlogString != null) backlog = Integer.parseInt(backlogString);

    String hostFileString = System.getProperty("hostfile");
    if(hostFileString != null) hostfile = hostFileString;

    String soundFileString = System.getProperty("soundfile");
    if(soundFileString != null) soundfile = soundFileString;

    String magicString = System.getProperty("magic");
    if(magicString != null) magic = magicString;

    String connections = System.getProperty("connections");
    if(connections != null) maxConnections = Integer.parseInt(connections);
    this.connections = new Vector(maxConnections);

    System.out.println("Server settings:\n\tPort="+port+"\n\tMax Backlog="+
                       backlog+"\n\tMax Connections="+maxConnections+
	     "\n\tHost File="+hostfile+"\n\tSound File="+soundfile);

    createHostList();

    try {
      URL sound = new URL(soundfile);
      connectSound = Applet.newAudioClip(sound);
    } catch(Exception e) { e.printStackTrace(); }

    this.port = port;
    Thread t = new Thread(this);
    t.start();
   }

  private void createHostList() {
    File hostFile = new File(hostfile);
    try {
      System.out.println("Attempting to read hostfile hosts.txt: ");
      FileInputStream fis = new FileInputStream(hostFile);
      InputStreamReader isr = new InputStreamReader(fis);
      BufferedReader br = new BufferedReader(isr);
      String aLine = null;
      while((aLine = br.readLine()) != null)
        {
          int spaceIndex = aLine.indexOf(' ');
          if(spaceIndex == -1) {
            System.out.println("Invalid line in host file: "+aLine);
            continue;
          }
          String host = aLine.substring(0,spaceIndex);
          String student = aLine.substring(spaceIndex+1);
          System.out.println("Read: "+student+"@"+host);
          hostInfo.put(host,student);
        }
      }
     catch(Exception e) { e.printStackTrace(); }
    }          

  public void run()
   {
    ServerSocket serverSocket = null;
    Socket communicationSocket;    

    try
     {
      System.out.println("Attempting to start server...");
      serverSocket = new ServerSocket(port,backlog);
     } 
    catch (IOException e) 
     { 
      System.out.println("Error starting server: Could not open port "+port);
      e.printStackTrace();
      System.exit(-1);
     }
    System.out.println ("Started server on port "+port);

    // Run the listen/accept loop forever
    while (true)
     {
      try 
       {
        // Wait here and listen for a connection
        communicationSocket = serverSocket.accept();
        HandleConnection(communicationSocket);
       } 
      catch (Exception e)
       { 
        System.out.println("Unable to spawn child socket.");
        e.printStackTrace();
       }
     }
   }

  public void HandleConnection(Socket connection)
   {
    synchronized(this)
     {
      while(numConnections == maxConnections)
       {
        try { wait(); } 
        catch(Exception e) { e.printStackTrace(); }
       }
     }
    numConnections++;
    Connection con = new Connection(connection);
    Thread t = new Thread(con);
    t.start();
    connections.addElement(con);
   }

  public synchronized void connectionClosed(Connection connection)
   {
    connections.removeElement(connection);
    numConnections--;
    notify();
   }

  public void sendToAllClients(String message)
   {
    Enumeration cons = connections.elements();
    while(cons.hasMoreElements())
     {
      Connection c = (Connection)cons.nextElement();
      c.sendMessage(message);
     }
   }

  class Connection implements Runnable 
   {

    private Socket communicationSocket = null;
    private OutputStreamWriter out = null;
    private BufferedReader in = null;

    public void run()
     {
      OutputStream socketOutput = null;
      InputStream socketInput = null;

      try
       {
        socketOutput = communicationSocket.getOutputStream();
        out = new OutputStreamWriter(socketOutput);
        socketInput = communicationSocket.getInputStream();
        in = new BufferedReader(new InputStreamReader(socketInput));

        
        InetAddress address = communicationSocket.getInetAddress();
        String hostname = address.getHostName();

        String welcome = 
          "Connection made from host: "+hostname+"\nEverybody say hello";
        String student = (String)(hostInfo.get(hostname));
        if(student != null) welcome += " to "+student;
        welcome+="!\n";
        ChatServer.this.sendToAllClients(welcome);
        System.out.println("Connection made "+student+"@"+hostname);
        sendMessage("Welcome "+student+" the passphrase is "+magic+"\n");
        String input = null;

        while((input = in.readLine()) != null) {
          if(input.indexOf(magic) != -1 && connectSound != null) 
           {
             connectSound.play();
             sendMessage("Congratulations "+student+" you sent the passphrase!\n");
             System.out.println(student+" sent the passphrase!");
            }
          else ChatServer.this.sendToAllClients(input+"\n");
        }
       }
      catch(Exception e) { }
      finally
       {
        try 
         {
          if(in != null) in.close();
          if(out != null) out.close();
          communicationSocket.close();
         } 
        catch(Exception e) { e.printStackTrace(); }
        ChatServer.this.connectionClosed(this);
       }
     }

    public Connection(Socket s)
     {
      communicationSocket = s;
     }       

    public void sendMessage(String message)
     {
      try
       {
        out.write(message);
        out.flush();
       }
      catch(Exception e) { e.printStackTrace(); }
     }
   }

 }

