package com.example.nathan.trainstationserver.server;

import java.util.Map;

import com.example.nathan.trainstationserver.server.pages.AbstractHttpRequestHandler;
import com.sun.net.httpserver.HttpServer;

public interface IHttpServerFactory {
	
	public HttpServer getHttpServer(Map<String, AbstractHttpRequestHandler> urlMappings);

}
