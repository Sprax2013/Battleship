package de.sprax2013.battleship_game;

import java.util.Random;

public class Utils {
	/**
	 * Creates a clone of an Tile[][]
	 *
	 * @param input The array to clone
	 * 
	 * @return A cloned Tile[][]
	 */
	public static Tile[][] deepCopyTileMatrix(Tile[][] input) {
		Tile[][] result = new Tile[input.length][];

		for (int i = 0; i < input.length; i++) {
			result[i] = input[i].clone();
		}

		return result;
	}

	/**
	 * This Method uses a {@link Random} to return a random number between
	 * <b>min</b> and <b>max</b>
	 *
	 * @param min lowest possible value
	 * @param max highest possible value
	 *
	 * @return The random integer
	 *
	 * @see #secureRandomInteger(int, int)
	 */
	public static Integer randomInteger(int min, int max) {
		if (min == max) {
			return Integer.valueOf(min);
		}

		return new Random().nextInt(max - min + 1) + min;
	}

	/**
	 * Sleeps the current Thread.<br>
	 * Avoids all the <i>try'n catch</i> trees
	 *
	 * @param millis The milliseconds to sleep
	 * 
	 * @see Thread#sleep(long)
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}