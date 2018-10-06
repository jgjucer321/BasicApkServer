package main;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

public class Main {

	public static void main (String args[]) {
		
		BasicServer bServer = new BasicServer (13337);
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(bServer.getPort()), 0);
			HttpContext initContext = server.createContext("/");
			initContext.setHandler(BasicServer::handleInit);
			HttpContext finalContext = server.createContext("/json/");
			finalContext.setHandler(BasicServer::sendJson);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DirListener dl = null;
		if(args.length > 0) {
			File file = new File(args[0]);
			
			dl = new DirListener(file);
			dl.start();
		}
		else {
			System.out.println("no arguments found. exiting program.");
			System.exit(1);
		}
	}
}

