package game;

public interface Game {

	public static final int MAX_TEAMS = 8;
	public static final int DEFAULT_CAPACITY = 8;
	public static final int NOT_VALID = -1;

	void createMap(int width, int height);

	void clearGame();
	
	void addBunker(int x, int y, int initTreasure, String bunkerName);

	void addTeam(String teamName, String bunkerName);

	void addPlayer(String type, String bunkerName);

	boolean isCellOccupied(int x, int y);

	boolean hasBunker(String bunkerName);

	boolean hasBunker(int x, int y);
	
	boolean hasTeam(String teamName);

	boolean hasPlayer(int x, int y);

	boolean hasFunds(String type, String bunkerName);

	boolean belongsToTeam(String bunkerName);
	
	boolean belongsToTeam(int x, int y);

	boolean occupiedByPlayer(String bunkerName);

	boolean isAbandoned(String bunkerName);

	TeamIterator teamIterator();

	BunkerIterator bunkerIterator();
	
	BunkerIterator allBunkerIterator();

	PlayerIterator playerIterator();

	MapIterator mapIterator();

	int getNumBunkers();

	int getNumTeams();

	int getTeamBunkers();

	int getTeamPlayers();

	int getWidth();

	int getHeight();

	Team getCurrentTeam();

	String getCurrentTeamName();

	boolean isRed(int x, int y);

	boolean isGreen(int x, int y);

	boolean isBlue(int x, int y);

	boolean teamPlayerExists(int x, int y);

	boolean insideLimits(int x, int y, String direction);

	boolean canMove(int x, int y, String direction);

	void move(int x, int y, int newX, int newY);

	int[] getCoordinates(int x, int y, String direction);

	int getPlayerX(int x, int y, String direction);

	int getPlayerY(int x, int y, String direction);

	String getPlayerType(int x, int y);

	void seizeBunker(int x, int y);

	boolean attackerWins(int x, int y, int newX, int newY);

	void fight(int x, int y, int newX, int newY);

	void attack();

	void blueAttack(Player player);

	void greenAttack(Player player);

	void redAttack(Player player);

	void conquer(int x1, int y1, int x2, int y2);
			
	void skipTurn();

	boolean hasWinner();

	String winner();
	
	int getAliveTeams();
}
