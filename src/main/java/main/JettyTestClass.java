package main;

import org.eclipse.jetty.server.Server;
public class JettyTestClass {

	public void runServer() {
		
		Server server = new Server(8111);
		server.setHandler(new MyTestHandler());
	}
	
}
