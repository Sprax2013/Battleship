package de.sprax2013.battleship_game;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class ConsoleUtils {
	private static Scanner sc;

	/**
	 * Prints some text to the console and blocks the thread until return is pressed
	 */
	public static void waitUntilReturnIsPressed() {
		System.out.println(System.lineSeparator() + "<Drücke Enter, um fortzufahren>");

		try {
			System.in.read();
			System.in.skip(System.in.available());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Gets a String from the console
	 *
	 * @return The input string, never null
	 */
	public static String getUserInput() {
		if (sc == null) {
			sc = new Scanner(System.in);
		}

		String result = null;

		while (result == null || result.trim().isEmpty()) {
			System.out.print("> ");

			String in = sc.nextLine().trim();

			if (in.equalsIgnoreCase("!Debug")) {
				System.out.println("//TODO Debug-Funktion einbauen");
			} else {
				result = in;
			}
		}

		return result;
	}

	public static void clearConsole() {
		try {
			if (System.getProperty("os.name").contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				Runtime.getRuntime().exec("clear");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void forceConsoleWindow() {
		if (System.console() == null
				&& !new File(".").getAbsolutePath().startsWith("C:\\Users\\Christian\\EclipseWorkspace\\")) {
			JOptionPane.showMessageDialog(null, "Dieses Spiel muss über die System-Konsole gestartet werden!",
					"Keine Konsole", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	}
}