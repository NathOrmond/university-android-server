package com.example.nathan.trainstationserver.run;

import java.util.Scanner;

import com.example.nathan.trainstationserver.server.Server;

public class Main {

	public static void main(String[] args) {
		startServer();
	}

	/**
	 * Attempts to start server prints a successful or unsuccessful attempt
	 */
	private static void startServer() {
		Scanner scanner = new Scanner(System.in);

		do {
			System.out.println("Start station server? : [y/n]");
		} while (!scanner.next().toLowerCase().equals("y"));

		Server.getInstance().startStationServer();
		
	}

}
