package com.example.nathan.trainstationserver.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.example.nathan.trainstationserver.server.pages.HttpRequestHandler;
import com.example.nathan.trainstationserver.server.pages.HttpStationsRequestHandler;
import com.sun.net.httpserver.*;

public class Server {

	private HttpServer server = null;

	/**
	 * 
	 * Takes integer of port to start on
	 * returns true if successfully started, false if not
	 * 
	 * @param iPort
	 * @return result
	 */
	public boolean open(int iPort) {

		boolean result = false;

		try {
			this.server = HttpServer.create(new InetSocketAddress(iPort), 0);
			this.server.createContext("/", new HttpRequestHandler());
			this.server.createContext("/stations", new HttpStationsRequestHandler());
			this.server.setExecutor(null);
			this.server.start();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}
	
	//not used

	/**
	 * takes integer of port to close, 
	 * returns true if successfully closed and false if not
	 * 
	 * @param Iport
	 * @return result
	 */
	public boolean close(int Iport) {
		boolean result = false;
		if (server != null) {
			this.server.stop(Iport);
		}
		return result;
	}

}
