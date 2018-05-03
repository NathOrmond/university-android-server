package com.example.nathan.trainstationserver.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DatabaseInteractions {

	/**
	 * connects to a local database whose file location is given as a string
	 * 
	 * @param url
	 * @return connection
	 */
	private Connection connect(String url) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			System.out.println("Connection to SQLite has been established.");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	/**
	 * runs SQL query on database and returns results as JSONArray
	 * 
	 * @param sql
	 * @return JSONArray
	 * @throws JSONException
	 */

	
	@SuppressWarnings("unchecked")
	public JSONArray runSQLQuery(String sql) {
		Connection conn = connect(getTrainStationDBURL());
		JSONArray jsons = new JSONArray();
		try {
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = resultSet.getMetaData();

			while (resultSet.next()) {
				jsons.add(parseResultsRowToJSON(resultSet, rsmd));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
		System.out.println(jsons);
		return jsons;
	}

	/**
	 * Extracts relevant results for each row from result set and returns them in
	 * JSON object
	 * 
	 * @param resultSet
	 * @return JSONObject
	 * @throws SQLException
	 * @throws JSONException
	 */
	@SuppressWarnings("unchecked")
	private JSONObject parseResultsRowToJSON(ResultSet resultSet, ResultSetMetaData rsmd) throws SQLException {
		JSONObject object = new JSONObject();

		// Order Latitude, Longitude, StationName

		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			if (rsmd.getColumnName(i).equals("Latitude") 
			 || rsmd.getColumnName(i).equals("Longitude")
			 || rsmd.getColumnName(i).equals("StationName")) {
				object.put(rsmd.getColumnName(i), resultSet.getString(i));
			}
		}

		return object;
	}

	/**
	 * returns as a string the file path to the directory containing the train
	 * stations database file
	 * 
	 * @return file path
	 */
	private String getTrainStationDBURL() {
		return "jdbc:sqlite:" + System.getProperty("user.dir") + "..\\..\\database\\trainstations.db";
	}

	/**
	 * takes the latitude and longitude and forms an SQL statement to query SQLite
	 * database, returns 5 nearest stations to lat and lng.
	 * 
	 * @param lat
	 * @param lng
	 * @return SQL statement
	 */
	public String sqlStmt(String lat, String lng) {
		return "SELECT " + "Latitude, Longitude, StationName," + "(((" + lat + " - Latitude) * (" + lat
				+ " - Latitude)) + " + "(0.59 * ((" + lng + " - Longitude) * (" + lng + " - Longitude)))) "
				+ "AS DistanceMetric " + "FROM stations " + "ORDER BY DistanceMetric " + "LIMIT 5;";
	}
}
