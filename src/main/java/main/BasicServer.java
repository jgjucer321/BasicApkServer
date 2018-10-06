package main;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sun.net.httpserver.HttpExchange;


public class BasicServer {
	
	public int port;
	public static int unAckedApks;
	public static Queue<String> metaData = new LinkedBlockingQueue<String>();
	
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
		if(!metaData.isEmpty()) {
			 response = metaData.peek();
		}
		else response = "";
		exchange.sendResponseHeaders(200, response.getBytes().length);
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}
