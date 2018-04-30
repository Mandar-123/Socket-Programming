import java.io.*; 
import java.net.*; 
import java.util.*;

class Client{ 
	static volatile boolean finished = false;
	static String name;
    public static void main(String[ ] args){
      try { 
		InetAddress serverHost = InetAddress.getByName(args[0]);
		int serverPort = Integer.parseInt((args[1]));
		Socket clientSocket = new Socket(serverHost, serverPort); 
        PrintStream ps = new PrintStream(clientSocket.getOutputStream()); 
		Scanner sc = new Scanner(System.in); 
        System.out.print("Enter your name: ");
        name = sc.nextLine();
		//System.out.println(clientSocket.toString());
		Thread t = new Thread(new ReadThread(clientSocket));
		t.start(); 
		System.out.println("Start Chatting !!");
		l1:while(!finished)
		{
			String message;
            message = sc.nextLine();
			if(finished)
			{
				clientSocket.close();
				ps.close();
				break;
			}
			if(message.equalsIgnoreCase("Exit"))
            {
				ps.println("close");
				finished = true;
				clientSocket.close( );
				ps.close();
				break;
			}
			message = name + ": " + message;
			ps.println(message);
		} 
      } 
      catch(Exception e){e.printStackTrace();}
    }
}

class ReadThread implements Runnable
{
	Socket socket;
	
	ReadThread(Socket socket)
    {
        this.socket = socket;
    }
     
    @Override
    public void run()
    {
		try
        {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(!Client.finished)
			{
				String str = br.readLine();
                if(str.equalsIgnoreCase("exit"))
				{
					Client.finished = true;
					System.out.print("Server Closed the Connection!");
					break;
				}
				System.out.println(str);
            }
            
        }
		catch(Exception e) {
			if(e.toString().equalsIgnoreCase("java.net.SocketException: Socket closed"))
				System.out.print("You closed the connection!");
			else if(e.toString().equalsIgnoreCase("java.net.SocketException: Connection reset"))
				System.out.print("Server Closed the Connection!");
			Client.finished = true;
		}
    }
}