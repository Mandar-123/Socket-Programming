import java.io.*; 
import java.net.*;
 
class ServerThread extends Thread {
    
    Socket clientSocket;
    ServerThread(Socket cs){       
		clientSocket = cs;
    }
	
	static String reverseString(String st) {
		StringBuilder stb = new StringBuilder(st);
		return stb.reverse().toString();
    }
 
    public void run(){ 
      try{ 
		int ctr;
        BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
        PrintStream ps = new PrintStream(clientSocket.getOutputStream( ));
		while(true)
		{
			ctr = Integer.parseInt(br.readLine( ));
			if(ctr == 100)
				break;
			Integer inttemp = new Integer(ctr); 
            String temp = inttemp.toString();
			String revString = reverseString(temp); 
			Thread.sleep(200); 
			ps.println(revString);
		}            
        ps.flush();
        clientSocket.close( );
      }
       catch(Exception e){e.printStackTrace( );} 
    } 
} 
class Server { 
     public static void main(String[] args){ 
       try{ 
          int serverPort = Integer.parseInt(args[0]); 
          ServerSocket calcServer = new ServerSocket(serverPort); 
          while (true){ 
              Socket clientSocket = calcServer.accept( ); 
              ServerThread thread = new ServerThread(clientSocket);
              thread.start( ); 
           } 
         } 
       catch(Exception e){e.printStackTrace( );} 
    } 
} 
