import java.io.*; 
import java.net.*;
import java.util.*;
 
class ServerThread extends Thread {
    
    Socket clientSocket;
	
    ServerThread(Socket cs){
		clientSocket = cs;
    }
	
    public void run(){ 
      try{
		String str;
        BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
		while(true)
		{
			str = br.readLine( );
			if(str.equals("close"))
			{
				for(int i=0;i<Server.num;i++) {
					if(Server.arr[i] == clientSocket)
						for(int j=i;j<Server.num - 1;j++)
							Server.arr[j] = Server.arr[j+1];
					Server.num--;
				}
				clientSocket.close();
				break;
			}
			System.out.println(str);	
			for(int i=0;i<Server.num;i++) {
				Socket temp = Server.arr[i];
				SendToAll thread = new SendToAll(temp,str);
				thread.start();
			}
		}
      }
       catch(Exception e){
		   try{
			   for(int i=0;i<Server.num;i++) {
					if(Server.arr[i] == clientSocket)
						for(int j=i;j<Server.num - 1;j++)
							Server.arr[j] = Server.arr[j+1];
					Server.num--;
				}
				clientSocket.close();
	   }catch(Exception ex){ex.printStackTrace();}} 
    } 
}

class SendToAll extends Thread {
	
	Socket s;
	String msg;
	SendToAll(Socket s,String msg)
	{
		this.s = s;
		this.msg = msg;
	}
	
	public void run() {
		try{
			//System.out.println(s.toString());
			PrintStream ps = new PrintStream(s.getOutputStream( ));
			ps.println(msg);
			if(msg.equalsIgnoreCase("exit"))
				for(int i=0;i<Server.num;i++) {
					if(Server.arr[i] == s)
					{
						s.close();
						break;
					}
				}
		}catch(Exception e){System.out.println("Error while sending to a client");}
	}
}

class ServerSendThread extends Thread {
	public void run(){
	  Scanner sc = new Scanner(System.in);
      try{
		System.out.print("Enter your name: ");
		String name = sc.nextLine();
		System.out.println("Start Chatting !!");
		while(true)
		{
			String message;
            message = sc.nextLine();
			if(message.equalsIgnoreCase("exit"))
			{
				for(int i=0;i<Server.num;i++) {
					Socket temp = Server.arr[i];
					SendToAll thread = new SendToAll(temp,message);
					thread.start();
				}
				System.out.print("You Closed the Connection!!");
				System.exit(0);
				break;
			}
			message = name + ": " + message;
			System.out.println(message);
			for(int i=0;i<Server.num;i++) {
				Socket temp = Server.arr[i];
				SendToAll thread = new SendToAll(temp,message);
				thread.start();
			}
		}
	  }catch(Exception e){System.out.println("You Closed The Connection! Mostly ! or there is a bug");}            
    }
}
class Server {
	static Socket arr[] = new Socket[100];
	static int num = 0;
    public static void main(String[] args){ 
       try{ 
          int serverPort = Integer.parseInt(args[0]);
          ServerSocket ss = new ServerSocket(serverPort);
          ServerSendThread sst = new ServerSendThread();
		  sst.start();
		  while (true){
              Socket clientSocket = ss.accept( ); 
              ServerThread thread = new ServerThread(clientSocket);
              arr[num++] = clientSocket;
			  thread.start( );
           }
        }
        catch(Exception e){e.printStackTrace( );}
	}
}