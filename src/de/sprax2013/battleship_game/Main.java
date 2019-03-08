package de.sprax2013.battleship_game;

import java.awt.Point;

public class Main {
	public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static final int GRID_SIZE = 10;

	private static Player player, opponent;

	public static void main(String[] args) {
		ConsoleUtils.forceConsoleWindow();

		System.out.println("@Spieler 1, wie ist dein Name?");
		player = new Player(ConsoleUtils.getUserInput());

		System.out.println("@Spieler 2, wie ist dein Name?");
		opponent = new Player(ConsoleUtils.getUserInput());

		Player last = null;
		Player next = player;
		while (player.areShipsLeft() && opponent.areShipsLeft()) {
			ConsoleUtils.clearConsole();

			if (last != next) {
				System.out.println(next.getUsername() + " ist nun am Zug!");
				ConsoleUtils.waitUntilReturnIsPressed();
			}

			last = next;

			printGrid(next);

			boolean nextDidShoot = false;

			while (!nextDidShoot) {
				Point point = null;
				while (point == null) {
					System.out.println("Auf welche Koordinaten soll gefeuert werden?");

					String input = ConsoleUtils.getUserInput();

					if (input.length() <= 4) {
						String letter = "";
						String digit = "";

						for (char c : input.toCharArray()) {
							if (Character.isDigit(c)) {
								digit += c;
							} else if (Character.isAlphabetic(c)) {
								letter += c;
							}
						}

						int x, y;
						if (!digit.isEmpty() && (y = Integer.parseInt(digit) - 1) < GRID_SIZE && letter.length() == 1
								&& (x = ALPHABET.indexOf(letter.toUpperCase())) >= 0) {
							point = new Point(x, y);
						} else {
							System.out.println("Die Koordinaten sind ungültig!");
						}
					}
				}

				nextDidShoot = next.canShoot(point.x, point.y);

				if (nextDidShoot) {
					boolean hit = next.shoot(point.x, point.y);

					for (int i = 0; i < 3; i++) {
						System.out.print(". ");

						Utils.sleep(750);
					}
					System.out.println();

					if (hit) {
						System.out.println("Treffer!!");
					} else {
						System.out.println("Nichts getroffen..");
						next = getOpponent(next);
					}

					Utils.sleep(1250);
				} else {
					System.out.println("Diese Koordinaten haben wir bereits beschossen!");
				}
			}
		}

		if (player.areShipsLeft()) {
			System.out.println(System.lineSeparator() + player.getUsername() + " hat das Spiel gewonnen!");
		} else {
			System.out.println(System.lineSeparator() + opponent.getUsername() + " hat das Spiel gewonnen!");
		}
	}

	/**
	 * Gets the opponent of a player
	 *
	 * @param p The player
	 * 
	 * @return The opponent
	 */
	public static Player getOpponent(Player p) {
		if (p == player) {
			return opponent;
		}

		return player;
	}

	/**
	 * Prints the grid for a specific player
	 *
	 * @param p The player
	 */
	public static void printGrid(Player p) {
		String[] lines = new String[GRID_SIZE + 2];

		lines[0] = "      +++ Dein Spielfeld +++  \t\t\t    +++ " + getOpponent(p).getUsername() + "s Spielfeld +++"
				+ System.lineSeparator();

		// X-Achse (Beschriftung)
		lines[1] = " ";
		for (int i = 0; i < GRID_SIZE; i++) {
			lines[1] += "  " + (i + 1);
		}

		lines[1] += "\t|\t ";

		for (int i = 0; i < GRID_SIZE; i++) {
			lines[1] += "  " + (i + 1);
		}

		// Y-Achse + Eigenes Spielfeld
		for (int x = 0; x < GRID_SIZE; x++) {
			lines[2 + x] = Character.toString(Main.ALPHABET.charAt(x));

			for (int y = 0; y < GRID_SIZE; y++) {
				lines[2 + x] += "  " + p.getOwnTile(x, y).getSymbol();
			}

			lines[2 + x] += "\t\t|\t";
		}

		// Gegnerisches Spielfeld
		for (int x = 0; x < GRID_SIZE; x++) {
			lines[2 + x] += Character.toString(Main.ALPHABET.charAt(x));

			for (int y = 0; y < GRID_SIZE; y++) {
				lines[2 + x] += "  " + p.getEnemyTile(x, y).getSymbol();
			}
		}

		for (String line : lines) {
			System.out.println(line);
		}
	}
}