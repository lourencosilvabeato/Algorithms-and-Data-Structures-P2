package game;

class TeamClass implements Team {

	private Bunker[] teamBunkers;
	private int numBunkers;
	private Player[] players;
	private int numPlayers;
	private String teamName;
	private int maxSize;

	TeamClass(String teamName) {
		teamBunkers = new Bunker[Game.DEFAULT_CAPACITY];
		numBunkers = 0;
		maxSize = 50;
		players = new Player[maxSize];
		numPlayers = 0;
		this.teamName = teamName;
	}

	public int searchPlayerIndex(Player player) {

		int i = 0;
		int result = NOT_VALID;
		boolean found = false;
		while (i < numPlayers && !found) {
			if (players[i] == player)
				found = true;
			else
				i++;
		}
		if (found)
			result = i;

		return result;
	}

	public int searchBunkerIndex(String bunkerName) {

		int i = 0;
		int result = NOT_VALID;
		boolean found = false;
		while (i < numBunkers && !found) {
			if (teamBunkers[i].getName().equals(bunkerName))
				found = true;
			else
				i++;
		}
		if (found)
			result = i;

		return result;
	}

	public void addPlayer(String type, String bunkerName) {
		int idx = searchBunkerIndex(bunkerName);
		int x = teamBunkers[idx].getX();
		int y = teamBunkers[idx].getY();
		teamBunkers[idx].changeTreasury(type);

		if (type.equals(Player.BLUE))
			players[numPlayers++] = new BluePlayerClass(x, y);
		else if (type.equals(Player.GREEN))
			players[numPlayers++] = new GreenPlayerClass(x, y);
		else
			players[numPlayers++] = new RedPlayerClass(x, y);
	}

	public void addBunker(Bunker bunker) {
		teamBunkers[numBunkers++] = bunker;
	}

	public String getTeamName() {
		return teamName;
	}

	public int getNumBunkers() {
		return numBunkers;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public Player getPlayer(int idx) {
		return players[idx];
	}

	public BunkerIterator bunkerIterator() {
		return new BunkerIteratorClass(teamBunkers, numBunkers);
	}

	public PlayerIterator playerIterator() {
		return new PlayerIteratorClass(players, numPlayers);
	}

	public Player getLatestPlayer() {
		return players[numPlayers - 1];
	}

	public void changeCoordinates(Player player, int newX, int newY) {
		player.changeX(newX);
		player.changeY(newY);
	}

	public void removePlayer(Player player) {

		int idx = searchPlayerIndex(player);
		for (int i = idx; i < numPlayers; i++)
			players[i] = players[i + 1];
		numPlayers--;
	}

	public void removeBunker(Bunker bunker) {

		int idx = searchBunkerIndex(bunker.getName());
		for (int i = idx; i < numBunkers; i++)
			teamBunkers[i] = teamBunkers[i + 1];
		numBunkers--;
	}

	public boolean isAlive() {
		boolean alive = true;
		if (numBunkers == 0 && numPlayers == 0)
			alive = false;
		return alive;
	}

}
