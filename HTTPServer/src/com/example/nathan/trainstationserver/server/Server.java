package com.example.nathan.trainstationserver.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.example.nathan.trainstationserver.server.pages.HttpFormMethod;
import com.example.nathan.trainstationserver.server.pages.HttpRequestHandler;
import com.sun.net.httpserver.*;

public class HttpServerClass {

	private HttpServer server = null;

	public boolean open(int iPort) {

		boolean result = false;

		try {
			this.server = HttpServer.create(new InetSocketAddress(iPort), 0);
			this.server.createContext("/", new HttpRequestHandler());
			this.server.createContext("/action", new HttpFormMethod());
			this.server.setExecutor(null);
			this.server.start();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}

	public boolean close(int Iport) {
		boolean result = false;
		if (server != null) {
			this.server.stop(Iport);
		}
		return result;
	}

}