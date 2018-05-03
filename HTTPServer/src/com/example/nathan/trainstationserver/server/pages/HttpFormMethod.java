package com.example.nathan.trainstationserver.server.pages;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HttpFormMethod implements HttpHandler {

	@Override
	public void handle(HttpExchange he) throws IOException {
		
		HashMap<String, String> params = new HashMap<String,String>();
		String query = he.getRequestURI().getQuery();
		String[] pairs = query.split("&"); 
		
		for(int i=0; i<pairs.length; i++ ) { 
			String[] halves = pairs[i].split("=");
			if(halves.length < 2) { 
				continue;
			}
			params.put(halves[0], halves[1]);
		}
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
		he.sendResponseHeaders(200, 0);
		String output = "<!DOCTYPE html><html><head><title>Fake HTML page</title></head><body><p><font color=\" "+ params.get("colour")+ "\">This is a web page Message for "+params.get("name")+" in your favourite colour</font></p></body></html>";
		bw.write(output);
		bw.close();
		
	}

}
