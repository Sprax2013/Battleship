package de.sprax2013.battleship_game;

public enum Ship {
	BATTLESHIP(4, 1, "Schlachtschiff"), CRUISER(3, 2, "Kreuzer"), DESTROYER(2, 3, "Zerstörer"),
	SUBMARINE(1, 4, "U-Boot");

	private final int size, count;
	private final String germanName;

	private Ship(int size, int count, String germanName) {
		this.size = size;
		this.count = count;

		this.germanName = germanName;
	}

	/**
	 * @return The size of the ship
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * @return The amount of this ship per player
	 */
	public int getCount() {
		return this.count;
	}

	/**
	 * @return The German name of the ship
	 */
	public String getName() {
		return this.germanName;
	}
}