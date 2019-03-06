package de.sprax2013.battleship_game;

public enum Tile {
	EMPTY('_'), SHIP('V'), SHIP_VERTICAL('H'), HIT('X'), MISS('*');

	private final char symbol;

	private Tile(char symbol) {
		this.symbol = symbol;
	}

	public char getSymbol() {
		return this.symbol;
	}

	public boolean isShip() {
		return this == SHIP || this == SHIP_VERTICAL;
	}

	public boolean hasBeenShot() {
		return this == HIT || this == MISS;
	}
}