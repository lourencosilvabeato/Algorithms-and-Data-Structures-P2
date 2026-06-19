package game;

class PlayerIteratorClass implements PlayerIterator {

	Player[] players;
	private int size;
	private int nextIdx;

	public PlayerIteratorClass(Player[] players, int size) {
		this.size = size;
		this.players = players;
		nextIdx = 0;
	}

	public boolean hasNext() {
		return nextIdx < size;
	}

	public Player next() {
		return players[nextIdx++];
	}
	
}
