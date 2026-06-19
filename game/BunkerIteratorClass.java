package game;

public class BunkerIteratorClass implements BunkerIterator {
	
	Bunker[] bunkers;
	private int size;
	private int nextIdx;

	public BunkerIteratorClass(Bunker[] bunkers, int size) {
		this.size = size;
		this.bunkers = bunkers;
		nextIdx = 0;
	}

	public boolean hasNext() {
		return nextIdx < size;
	}

	public Bunker next() {
		return bunkers[nextIdx++];
	}
}
