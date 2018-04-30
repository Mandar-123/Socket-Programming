import java.io.*; 
import java.net.*; 
class Client{ 
    public static void main(String[ ] args){ 
      try { 
		InetAddress serverHost = InetAddress.getByName(args[0]); 
        int serverPort = Integer.parseInt(args[1]); 
        long startTime = System.currentTimeMillis(); 
		Socket clientSocket = new Socket(serverHost, serverPort); 
        PrintStream ps = new PrintStream(clientSocket.getOutputStream());  
        BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
        int ctr = 0;
		while(true)
		{
			ps.println(ctr);
			if(ctr == 100)
				break;
			int Reversed = Integer.parseInt(br.readLine());
			System.out.println(ctr + " --> " + Reversed);
			ctr++;
		} 
        long endTime = System.currentTimeMillis(); 
        System.out.println(" Time consumed for receiving the feedback from the server: "+(endTime-startTime)+" milliseconds"); 
        clientSocket.close( ); 
      } 
      catch(Exception e){e.printStackTrace( );} 
    } 
} 
