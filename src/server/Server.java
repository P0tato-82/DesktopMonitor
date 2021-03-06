package server;
import myutil.Protocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class Server {
	public static Map<String,Socket> client=new HashMap<String,Socket>();
	public static HashSet<String> register_client=new HashSet<String>();
	public static View view=new View();
	public static int checkCode;
	public static String curKey=null;
	public static boolean serverLive=true;
	public static SQLiteJDBC sqLitejdbc = null;
	public static void main(String[] args) {
		try {
			System.out.println(InetAddress.getLocalHost());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		sqLitejdbc= new SQLiteJDBC();
		checkCode = giveCheckCode();
		try {
			ServerSocket serverSocket=new ServerSocket(33000);
			view.create();
			while(serverLive){
				Socket socket=serverSocket.accept();
				new Thread(new HandleClient(socket)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static int giveCheckCode(){
		int min = 10000;
		int max = 99999;
		return min + (int)(Math.random() * (max-min));
	}

}
