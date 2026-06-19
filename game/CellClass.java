package game;

class CellClass implements Cell {

	private Bunker bunker;
	private Player player;

	public CellClass() {
		bunker = null;
		player = null;
	}

	public Player getPlayer() {
		return player;
	}

	public Bunker getBunker() {
		return bunker;
	}

	public boolean hasBunker() {
		return bunker != null;
	}

	public boolean hasPlayer() {
		return player != null;
	}

	public void addBunker(Bunker bunker) {
		this.bunker = bunker;
	}

	public void addPlayer(Player player) {
		this.player = player;
	}

	public void removePlayer() {
		player = null;
	}
}
