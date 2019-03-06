package de.sprax2013.battleship_game;

public class Player {
	private final String username;

	private Tile[][] ownTiles, enemyTiles;

	public Player(String username) {
		this(username, false);
	}

	public Player(String username, boolean isEnemy) {
		this.username = username;

		if (!isEnemy) {
			resetGrids();
		}
	}

	/**
	 * @return The username set by the player
	 */
	public String getUsername() {
		return this.username;
	}

	public void setOwnTile(int x, int y, Tile tile) {
		ownTiles[x][y] = tile;
	}

	public Tile getOwnTile(int x, int y) {
		if (x >= 0 && x < ownTiles.length && y >= 0 && y < ownTiles[0].length) {
			return ownTiles[x][y];
		}

		return null;
	}

	public void setEnemyTile(int x, int y, Tile tile) {
		enemyTiles[x][y] = tile;
	}

	public Tile getEnemyTile(int x, int y) {
		if (x < enemyTiles.length && y < enemyTiles[0].length) {
			return enemyTiles[x][y];
		}

		return null;
	}

	/**
	 * @return true, if at least one Ship-Tile is left
	 * 
	 * @see Tile#isShip()
	 */
	public boolean areShipsLeft() {
		for (int x = 0; x < ownTiles.length; x++) {
			for (int y = 0; y < ownTiles[0].length; y++) {
				if (ownTiles[x][y].isShip()) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * 
	 * @return true, if these coordinates have not been shot before
	 */
	public boolean canShoot(int x, int y) {
		return !getEnemyTile(x, y).hasBeenShot();
	}

	/**
	 * Shoots at the enemy
	 *
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * 
	 * @return true, if it was a hit
	 * 
	 * @see #canShoot(int, int)
	 */
	public boolean shoot(int x, int y) {
		boolean hit = Main.getOpponent(this).enemyShot(x, y);

		setEnemyTile(x, y, hit ? Tile.HIT : Tile.MISS);
		return hit;
	}

	/**
	 * Called when the enemy shot at the player.
	 *
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * 
	 * @return true, if it was a hit
	 */
	public boolean enemyShot(int x, int y) {
		if (getOwnTile(x, y).isShip()) {
			setOwnTile(x, y, Tile.HIT);
			return true;
		}

		setOwnTile(x, y, Tile.MISS);
		return false;
	}

	/**
	 * Reset the grid and randomly places all own Ships
	 * 
	 * {@see Ship}
	 */
	private void resetGrids() {
		// Standard-Werte
		ownTiles = new Tile[Main.GRID_SIZE][Main.GRID_SIZE];
		for (int x = 0; x < ownTiles.length; x++) {
			for (int y = 0; y < ownTiles.length; y++) {
				ownTiles[x][y] = Tile.EMPTY;
			}
		}

		enemyTiles = Utils.deepCopyTileMatrix(ownTiles);

		// Eigene Schiffe platzieren
		for (Ship s : Ship.values()) {
			for (int i = 0; i < s.getCount(); i++) {
				int x = Utils.randomInteger(0, Main.GRID_SIZE - 1), y = Utils.randomInteger(0, Main.GRID_SIZE - 1);
				boolean vertical = Utils.randomInteger(0, 1) == 1;

				boolean place = true;
				if (vertical) {
					for (int j = 0; j < s.getSize(); j++) {
						if (getOwnTile(x + j, y) == null || getOwnTile(x + j, y) != Tile.EMPTY
								|| getOwnTile(x + j + 1, y) != Tile.EMPTY || getOwnTile(x + j - 1, y) != Tile.EMPTY
								|| getOwnTile(x + j, y + 1) != Tile.EMPTY || getOwnTile(x + j, y - 1) != Tile.EMPTY) {
							place = false;
							i--;
							break;
						}
					}

					if (place) {
						for (int j = 0; j < s.getSize(); j++) {
							setOwnTile(x + j, y, Tile.SHIP_VERTICAL);
						}
					}
				} else {
					for (int j = 0; j < s.getSize(); j++) {
						if (getOwnTile(x, y + j) == null || getOwnTile(x, y + j) != Tile.EMPTY
								|| getOwnTile(x + 1, y + j) != Tile.EMPTY || getOwnTile(x - 1, y + j) != Tile.EMPTY
								|| getOwnTile(x, y + j + 1) != Tile.EMPTY || getOwnTile(x, y + j - 1) != Tile.EMPTY) {
							place = false;
							i--;
							break;
						}
					}

					if (place) {
						for (int j = 0; j < s.getSize(); j++) {
							setOwnTile(x, y + j, Tile.SHIP);
						}
					}
				}
			}
		}
	}
}