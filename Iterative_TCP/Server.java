import java.io.*; 
import java.net.*; 
class Server{
    static String reverseString(String st) {
		StringBuilder stb = new StringBuilder(st);
		return stb.reverse().toString();
    }	 
    public static void main(String[] args){ 
       try{ 
		int serverPort = Integer.parseInt(args[0]); 
        ServerSocket calcServer = new ServerSocket(serverPort); 
        while (true){ 
        Socket clientSocket = calcServer.accept( ); 
        BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
        PrintStream ps = new PrintStream(clientSocket.getOutputStream( )); 
        while(true) {
			int ctr = Integer.parseInt(br.readLine());
			if(ctr == 100)
				break;
			Integer inttemp = new Integer(ctr); 
			String temp = inttemp.toString();
			String revString = reverseString(temp); 
			Thread.sleep(200); 
			ps.println(revString);
		}  
        ps.flush( ); 
        clientSocket.close( ); 
        } 
       } 
       catch(Exception e){e.printStackTrace( );} 
    } 
} 
