package game;

public interface GameSystem {

	boolean isRunning();

	void changeRunning();
	
	void startGame(int width, int height);

	void clearGame();

	void addBunker(int x, int y, int initTreasure, String bunkerName);

	void addTeam(String teamName, String bunkerName);

	void addPlayer(String type, String bunkerName);
	
	boolean isCellOccupied(int x, int y);
	
	boolean lessThanTwoTeams();

	boolean hasBunker(String bunkerName);

	boolean hasBunker(int x, int y);

	boolean hasTeam(String teamName);

	boolean hasPlayer(int x, int y);

	boolean hasFunds(String type, String bunkerName);

	public boolean isTypeValid(String type);
	
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
	
	boolean noTeamPlayers();
	
	boolean noTeamBunkers();

	int getWidth();

	int getHeight();
	
	boolean isRed(int x, int y);
	
	boolean isGreen(int x, int y);

	boolean isBlue(int x, int y);
	
	boolean teamPlayerExists(int x, int y);
	
	boolean insideLimits(int x, int y, String direction);

	boolean canMove(int x, int y, String direction);
	
	void move(int x, int y, int newX, int newY);
	
	int getPlayerX(int x, int y, String direction);

	int getPlayerY(int x, int y, String direction);
	
	Team getCurrentTeam();

	String getCurrentTeamName();
	
	String getPlayerType(int x, int y);
	
	void seizeBunker(int x, int y);
	
	boolean attackerWins(int x, int y, int newX, int newY);

	void fight(int x, int y, int newX, int newY);
	
	void attack();
	
	void skipTurn();

	String winner();
	
	int getAliveTeams();

}