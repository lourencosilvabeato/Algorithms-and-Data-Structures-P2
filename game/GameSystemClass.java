package game;

public class GameSystemClass implements GameSystem {

	private Game game;
	private boolean gameRunning;
	private boolean quitGame;

	public GameSystemClass() {
		game = new GameClass();
		gameRunning = false;
		quitGame = false;
	}

	public boolean isRunning() {
		if(game.hasWinner())
			gameRunning = false;
		return gameRunning;
	}

	public void changeRunning() {
		gameRunning = false;
	}

	public void startGame(int width, int height) {
		gameRunning = true;
		game.createMap(width, height);
	}

	public void clearGame() {
		game.clearGame();
	}

	public boolean quitGame() {
		return quitGame;
	}

	public void changeQuit() {
		quitGame = true;
		gameRunning = false;
	}

	public void addBunker(int x, int y, int initTreasure, String bunkerName) {
		game.addBunker(x, y, initTreasure, bunkerName);
	}

	public void addTeam(String teamName, String bunkerName) {
		game.addTeam(teamName, bunkerName);
	}

	public void addPlayer(String type, String bunkerName) {
		game.addPlayer(type, bunkerName);
	}

	public boolean isCellOccupied(int x, int y) {
		return game.isCellOccupied(x, y);
	}

	public boolean lessThanTwoTeams() {
		return game.getNumTeams() < 2;
	}

	public boolean hasBunker(String bunkerName) {
		return game.hasBunker(bunkerName);
	}
	public 	boolean hasBunker(int x, int y) {
		return game.hasBunker(x, y);
	}

	public boolean hasTeam(String teamName) {
		return game.hasTeam(teamName);
	}

	public boolean hasPlayer(int x, int y) {
		return game.hasPlayer(x, y);
	}

	public boolean hasFunds(String type, String bunkerName) {
		return game.hasFunds(type, bunkerName);
	}

	public boolean isTypeValid(String type) {
		boolean valid = false;
		if (type.equals(Player.BLUE) || type.equals(Player.GREEN) || type.equals(Player.RED))
			valid = true;
		return valid;
	}

	public boolean belongsToTeam(int x, int y) {
		return game.belongsToTeam(x, y);
	}

	public boolean belongsToTeam(String bunkerName) {
		return game.belongsToTeam(bunkerName);
	}

	public boolean occupiedByPlayer(String bunkerName) {
		return game.occupiedByPlayer(bunkerName);
	}

	public boolean isAbandoned(String bunkerName) {
		return game.isAbandoned(bunkerName);
	}

	public TeamIterator teamIterator() {
		return game.teamIterator();
	}

	public BunkerIterator bunkerIterator() {
		return game.bunkerIterator();
	}
	
	public BunkerIterator allBunkerIterator() {
		return game.allBunkerIterator();
	}

	public PlayerIterator playerIterator() {
		return game.playerIterator();
	}

	public 	MapIterator mapIterator() {
		return game.mapIterator();
	}


	public int getNumBunkers() {
		return game.getNumBunkers();
	}

	public int getNumTeams() {
		return game.getNumTeams();
	}

	public int getWidth() {
		return game.getWidth();
	}

	public int getHeight() {
		return game.getHeight();
	}

	public Team getCurrentTeam() {
		return game.getCurrentTeam();
	}

	public String getCurrentTeamName() {
		return game.getCurrentTeamName();
	}

	public int getTeamBunkers() {
		return game.getTeamBunkers();
	}

	public int getTeamPlayers() {
		return game.getTeamPlayers();
	}

	public boolean isRed(int x, int y) {
		return game.isRed(x, y);
	}

	public boolean isGreen(int x, int y) {
		return game.isGreen(x, y);
	}

	public boolean isBlue(int x, int y) {
		return game.isBlue(x, y);
	}

	public boolean teamPlayerExists(int x, int y) {
		return game.teamPlayerExists(x, y);
	}

	public 	boolean insideLimits(int x, int y, String direction) {
		return game.insideLimits(x, y, direction);
	}

	public boolean canMove(int x, int y, String direction) {
		return game.canMove(x, y, direction);
	}

	public void move(int x, int y, int newX, int newY) {
		game.move(x, y, newX, newY);
	}

	public int getPlayerX(int x, int y, String direction) {
		return game.getPlayerX(x, y, direction);
	}

	public int getPlayerY(int x, int y, String direction) {
		return game.getPlayerY(x, y, direction);
	}

	public boolean noTeamPlayers() {
		return getTeamPlayers() == 0;
	}

	public boolean noTeamBunkers() {
		return getTeamBunkers() == 0;
	}

	public String getPlayerType(int x, int y) {
		return game.getPlayerType(x, y);
	}

	public void seizeBunker(int x, int y) {
		game.seizeBunker(x, y);
	}

	public boolean attackerWins(int x, int y, int newX, int newY) {
		return game.attackerWins(x, y, newX, newY);
	}

	public void fight(int x, int y, int newX, int newY) {
		game.fight(x, y, newX, newY);
	}

	public void attack() {
		game.attack();		
	}

	public void skipTurn() {
		game.skipTurn();
	}
	
	public String winner() {
		return game.winner();
	}
	
	public int getAliveTeams() {
		return game.getAliveTeams();
	}
}