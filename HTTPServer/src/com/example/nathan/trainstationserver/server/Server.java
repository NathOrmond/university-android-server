package com.example.nathan.trainstationserver.server;

import java.util.HashMap;
import java.util.Map;

import com.example.nathan.trainstationserver.server.pages.AbstractHttpRequestHandler;
import com.example.nathan.trainstationserver.server.pages.HttpRequestHandler;
import com.example.nathan.trainstationserver.server.pages.HttpStationsRequestHandler;

public class Server {

	private static Server INSTANCE = null;
	
	private Server() { 
		System.out.println("server created");
	}
	
	public static synchronized Server getInstance() { 
		if(INSTANCE == null) { 
			INSTANCE = new Server();
		}
		return INSTANCE;
	}

	/**
	 * 
	 */
	public void startStationServer() { 
		Map<String, AbstractHttpRequestHandler> urlMappings = new HashMap<String, AbstractHttpRequestHandler>();
		urlMappings.put("/", new HttpRequestHandler());
		urlMappings.put("/stations", new HttpStationsRequestHandler());	
		
		new HttpServerFactory().getHttpServer(urlMappings);
		
	}

}
