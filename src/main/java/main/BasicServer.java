package main;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import com.sun.net.httpserver.HttpExchange;


public class BasicServer {
	
	public int port;
	public static int unAckedApks;
	public static List <String> metaData = new ArrayList<String>();
	
	public BasicServer(int port) {
		this.port = port;
	}
	
	public static void incrUnackedApks() {
		unAckedApks++;
	}
	
	public static int getUnAckedApks() {
		return unAckedApks;
	}
	
	public  int getPort() {
		return this.port;
	}
	
	public static void resetAcks() {
		unAckedApks = 0;
	}
	
	public static void handleInit(HttpExchange exchange) throws IOException {
		String response = String.valueOf(getUnAckedApks());
		resetAcks();
		exchange.sendResponseHeaders(200, response.getBytes().length);
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
		
	}
	public static void sendJson(HttpExchange exchange) throws IOException{
		String response = "";
		for(String responses: metaData) {
			response += responses;
			response += "\n";
		}
		metaData.clear();
		exchange.sendResponseHeaders(200, response.getBytes().length);
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}
