package com.example.nathan.trainstationserver.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

import com.example.nathan.trainstationserver.server.pages.AbstractHttpRequestHandler;
import com.sun.net.httpserver.HttpServer;

public class HttpServerFactory implements IHttpServerFactory{
	
	private Integer getAvailablePortNum() { 
		return 8080;
	}

	@Override
	public HttpServer getHttpServer(Map<String, AbstractHttpRequestHandler> urlMappings) {
		HttpServer server = null;
		try {
			server = HttpServer.create(new InetSocketAddress(getAvailablePortNum()), 0);
			for(String url : urlMappings.keySet()) { 
				server.createContext(url, urlMappings.get(url));
			}
			server.setExecutor(null);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return server;
	}



}
