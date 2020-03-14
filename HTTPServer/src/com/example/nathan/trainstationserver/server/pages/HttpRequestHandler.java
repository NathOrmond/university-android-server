package com.example.nathan.trainstationserver.server.pages;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HttpRequestHandler extends AbstractHttpRequestHandler {

	/**
	 * handles logic for "url/"
	 */
	@Override
	public void handle(HttpExchange he) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
		he.sendResponseHeaders(200, 0);
		bw.write(output());
		bw.close();

	}
	
	/**
	 * HTML for producing a web form with two inputs: latitude and longitude
	 * @return string
	 */
	private String output() { 
		return    "<!DOCTYPE html><html><head>"
				+ "<title>Lat & Lng Input</title></head>"
				+ "<body>"
				+ "<p> A location must be entered in the URL in the format /stations?lat=&lng= "
				+ " <br/> alternatively search below: </p>"
				
				+ "<form method=\"GET\" action=\"/stations\">"
				+ "<label for=\"latLabel\">latitude</label>"
				+ "<input type=\"text\" name=\"lat\" id=\"lat\">"
				+ "<label for=\"lngLabel\">longitude</label>"
				+ "<input type=\"text\" name=\"lng\" id=\"lng\">"
				+ "<input type=\"submit\" value=\"search\">"
				+ "</form>"
				
				+ "</body>"
				+ "</html>";
	}

}
