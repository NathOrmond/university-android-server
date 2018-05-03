package com.example.nathan.trainstationserver.server.pages;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.HashMap;


import com.example.nathan.trainstationserver.db.DatabaseInteractions;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HttpStationsRequestHandler implements HttpHandler {

	private String latitude, longitude;

	/**
	 * handles logic for "url/stations"
	 */
	@Override
	public void handle(HttpExchange he) throws IOException {

		HashMap<String, String> params = new HashMap<String, String>();
		String query = he.getRequestURI().getQuery();
		String[] pairs = query.split("&");
		params = createMap(params, pairs);
		latitude = params.get("lat");
		longitude = params.get("lng");

		if (latitude == null) {
			errorURLOutput(he, "lat");
		} else if (longitude == null) {
			errorURLOutput(he, "lng");
		} else {
			validURLOutput(he, params);
		}
	}

	/**
	 * writes the response for a valid URL query
	 * 
	 * @param he
	 * @param params
	 * @throws IOException
	 */
	private void validURLOutput(HttpExchange he, HashMap<String, String> params) throws IOException {
		OutputStream outputStream = he.getResponseBody();
		he.sendResponseHeaders(200, 0);
		DatabaseInteractions db = new DatabaseInteractions();
		byte[] b;
		b = db.runSQLQuery(db.sqlStmt(latitude, longitude)).toString().getBytes(Charset.forName("UTF-8"));
		outputStream.write(b);
		outputStream.close();
	}

	/**
	 * writes the output to anything listening on port in the event an erroneous URL
	 * is entered
	 * 
	 * @param he
	 * @param latlng
	 * @throws IOException
	 */
	private void errorURLOutput(HttpExchange he, String latlng) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
		he.sendResponseHeaders(200, 0);
		bw.write(errorMessage(latlng));
		bw.close();
	}

	/**
	 * returns a string specifying whether the latitude or longitude was misspelled
	 * in the URL
	 * 
	 * @param latlng
	 * @return string
	 */
	private String errorMessage(String latlng) {
		return "Error in URL; \nPlease remember to set the " + latlng + " parameter.";
	}

	/**
	 * populates a string:string hashmap with results from the URL
	 * 
	 * @param params
	 * @param pairs
	 * @return params
	 */
	private HashMap<String, String> createMap(HashMap<String, String> params, String[] pairs) {
		for (int i = 0; i < pairs.length; i++) {
			String[] halves = pairs[i].split("=");
			if (halves.length < 2) {
				continue;
			}
			params.put(halves[0], halves[1]);
		}
		return params;
	}

}
