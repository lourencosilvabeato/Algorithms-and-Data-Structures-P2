package game;

public interface Team {
	
	public static final int MAX_TEAMS = 8;
	public static final int NOT_VALID = -1;
	
	int searchPlayerIndex(Player player);
	
	int searchBunkerIndex(String bunkerName);
	
	void addPlayer(String type, String bunkerName);
	
	String getTeamName();

	int getNumBunkers();

	int getNumPlayers();
	
	Player getPlayer(int idx);
	
	void addBunker(Bunker bunker);
	
	BunkerIterator bunkerIterator();
	
	PlayerIterator playerIterator();
	
	Player getLatestPlayer();
	
	void changeCoordinates(Player player, int newX, int newY);
	
	void removePlayer(Player player);
	
	void removeBunker(Bunker bunker);

	boolean isAlive();
}
