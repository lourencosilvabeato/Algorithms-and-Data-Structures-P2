package game;

public interface Cell {

	Player getPlayer();
	
	Bunker getBunker();
	
	boolean hasBunker();
	
	boolean hasPlayer();
	
	void addBunker(Bunker bunker);
	
	void addPlayer(Player player);

	void removePlayer();
}
