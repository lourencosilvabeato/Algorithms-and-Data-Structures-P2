package game;

class GameClass implements Game {

	private Map map;
	private Team[] teams;
	private int numTeams;
	private Bunker[] bunkers;
	private int numBunkers;
	private int maxBunkers;
	private int turnCount;

	public GameClass() {
		teams = new Team[GameClass.MAX_TEAMS];
		numTeams = 0;
		maxBunkers = GameClass.DEFAULT_CAPACITY;
		bunkers = new Bunker[maxBunkers];
		numBunkers = 0;
		turnCount = 0;
	}

	public void createMap(int width, int height) {
		map = new MapClass(width, height);
		maxBunkers = width * height;
	}

	public int searchBunkerIndex(String bunkerName) {

		int i = 0;
		int result = NOT_VALID;
		boolean found = false;
		while (i < numBunkers && !found) {
			if (bunkers[i].getName().equals(bunkerName))
				found = true;
			else
				i++;
		}
		if (found)
			result = i;

		return result;
	}

	public int searchTeamIndex(String teamName) {

		int i = 0;
		int result = NOT_VALID;
		boolean found = false;
		while (i < numTeams && !found) {
			if (teams[i].getTeamName().equals(teamName))
				found = true;
			else
				i++;
		}
		if (found)
			result = i;

		return result;
	}

	public void clearGame() {
		for (int i = 0; i < numBunkers; i++) {
			bunkers[i] = null;
		}
		numBunkers = 0;
		for (int i = 0; i < numTeams; i++) {
			teams[i] = null;
		}
		numTeams = 0;
		turnCount = 0;
		map = null;
	}

	public void addBunker(int x, int y, int initTreasure, String bunkerName) {
		Bunker bunker = new BunkerClass(x, y, initTreasure, bunkerName);
		bunkers[numBunkers++] = bunker;
		map.getCell(x, y).addBunker(bunker);
	}

	public void addTeam(String teamName, String bunkerName) {
		Team team = new TeamClass(teamName);
		teams[numTeams++] = team;
		int idx2 = searchBunkerIndex(bunkerName);

		team.addBunker(bunkers[idx2]);
		bunkers[idx2].setTeam(team);
	}

	public void addPlayer(String type, String bunkerName) {
		int idx = searchBunkerIndex(bunkerName);
		Bunker bunker = bunkers[idx];
		getCurrentTeam().addPlayer(type, bunkerName);
		map.getCell(bunker.getX(), bunker.getY()).addPlayer(getCurrentTeam().getLatestPlayer());
		getCurrentTeam().getLatestPlayer().setTeam(getCurrentTeam());
	}

	public boolean isCellOccupied(int x, int y) {
		return map.hasBunker(x, y) || map.hasPlayer(x, y);
	}

	public boolean hasBunker(String bunkerName) {
		int idx = searchBunkerIndex(bunkerName);
		return idx != -1;
	}

	public boolean hasBunker(int x, int y) {
		return map.hasBunker(x, y);
	}

	public boolean hasPlayer(int x, int y) {
		return map.hasPlayer(x, y);
	}

	public boolean hasTeam(String teamName) {
		int idx = searchTeamIndex(teamName);
		return idx != -1;
	}

	public boolean hasFunds(String type, String bunkerName) {
		boolean valid = true;
		int idx = searchBunkerIndex(bunkerName);
		int funds = bunkers[idx].getTreasury();

		if (type.equals(Player.BLUE) && funds < Player.BLUE_GREEN_COST
				|| type.equals(Player.GREEN) && funds < Player.BLUE_GREEN_COST
				|| type.equals(Player.RED) && funds < Player.RED_COST)
			valid = false;
		return valid;
	}

	public boolean belongsToTeam(String bunkerName) {
		int idx = searchBunkerIndex(bunkerName);
		Bunker bunker = bunkers[idx];
		return getCurrentTeam().equals(bunker.getTeam());
	}

	public boolean belongsToTeam(int x, int y) {
		Bunker bunker = map.getCell(x, y).getBunker();
		return getCurrentTeam().equals(bunker.getTeam());
	}

	public boolean occupiedByPlayer(String bunkerName) {
		int idx = searchBunkerIndex(bunkerName);
		Bunker bunker = bunkers[idx];
		int x = bunker.getX();
		int y = bunker.getY();
		return map.getCell(x, y).hasPlayer();
	}

	public boolean isAbandoned(String bunkerName) {
		int idx = searchBunkerIndex(bunkerName);
		return bunkers[idx].getTeam() == null;
	}

	public TeamIterator teamIterator() {
		return new TeamIteratorClass(teams, numTeams);
	}

	public BunkerIterator bunkerIterator() {
		return getCurrentTeam().bunkerIterator();
	}

	public BunkerIterator allBunkerIterator() {
		return new BunkerIteratorClass(bunkers, numBunkers);
	}

	public PlayerIterator playerIterator() {
		return getCurrentTeam().playerIterator();
	}

	public MapIterator mapIterator() {
		int width = map.getWidth();
		int height = map.getHeight();
		return new MapIteratorClass(map, getCurrentTeam(), width, height);
	}

	public int getNumTeams() {
		return numTeams;
	}

	public int getNumBunkers() {
		return numBunkers;
	}

	public int getTeamBunkers() {
		return getCurrentTeam().getNumBunkers();
	}

	public int getTeamPlayers() {
		return getCurrentTeam().getNumPlayers();
	}

	public int getWidth() {
		return map.getWidth();
	}

	public int getHeight() {
		return map.getHeight();
	}

	public Team getCurrentTeam() {
		return teams[turnCount];
	}

	public String getCurrentTeamName() {
		return getCurrentTeam().getTeamName();
	}

	public boolean isRed(int x, int y) {
		return map.getCell(x, y).getPlayer() instanceof RedPlayerClass;
	}

	public boolean isGreen(int x, int y) {
		return map.getCell(x, y).getPlayer() instanceof GreenPlayerClass;
	}

	public boolean isBlue(int x, int y) {
		return map.getCell(x, y).getPlayer() instanceof BluePlayerClass;
	}

	public boolean teamPlayerExists(int x, int y) {
		Player player = map.getCell(x, y).getPlayer();
		return player != null && player.getTeam().equals(getCurrentTeam());
	}

	public boolean insideLimits(int x, int y, String direction) {
		boolean inside = false;
		Player player = map.getCell(x, y).getPlayer();

		switch (direction) {
		case "north" -> inside = player.getY() - 1 > 0;
		case "south" -> inside = player.getY() + 1 <= map.getHeight();
		case "east" -> inside = player.getX() + 1 <= map.getWidth();
		case "west" -> inside = player.getX() - 1 > 0;
		}

		return inside;
	}

	public boolean canMove(int x, int y, String direction) {
		boolean canMove = false;

		switch (direction) {
		case "north" -> canMove = !teamPlayerExists(x, y - 1);
		case "south" -> canMove = !teamPlayerExists(x, y + 1);
		case "east" -> canMove = !teamPlayerExists(x + 1, y);
		case "west" -> canMove = !teamPlayerExists(x - 1, y);
		}
		return canMove;
	}

	public void move(int x, int y, int newX, int newY) {
		Player player = map.getCell(x, y).getPlayer();

		if (hasPlayer(x, y)) {
			map.getCell(newX, newY).addPlayer(player);
			getCurrentTeam().changeCoordinates(player, newX, newY);
		}
		map.getCell(x, y).removePlayer();
	}

	public int[] getCoordinates(int x, int y, String direction) {

		int[] coords = new int[2];
		coords[0] = x;
		coords[1] = y;
		switch (direction) {
		case "north" -> coords[1] = y - 1;
		case "south" -> coords[1] = y + 1;
		case "east" -> coords[0] = x + 1;
		case "west" -> coords[0] = x - 1;
		}
		return coords;
	}

	public int getPlayerX(int x, int y, String direction) {
		return getCoordinates(x, y, direction)[0];
	}

	public int getPlayerY(int x, int y, String direction) {
		return getCoordinates(x, y, direction)[1];
	}

	public String getPlayerType(int x, int y) {
		Player player = map.getCell(x, y).getPlayer();
		return Player.playerType(player);
	}

	public void seizeBunker(int x, int y) {

		Bunker bunker = map.getCell(x, y).getBunker();
		if (bunker.getTeam() != null)
			bunker.getTeam().removeBunker(bunker);		
		bunker.setTeam(getCurrentTeam());
		getCurrentTeam().addBunker(bunker);
	}

	public void fight(int x, int y, int newX, int newY) {

		if (hasPlayer(newX, newY)) {
			Team defendingTeam = map.getCell(newX, newY).getPlayer().getTeam();
			Player defendingPlayer = map.getCell(newX, newY).getPlayer();
			Player player = map.getCell(x, y).getPlayer();

			if (attackerWins(x, y, newX, newY) && hasBunker(newX, newY)) {
				defendingTeam.removePlayer(defendingPlayer);
				map.getCell(newX, newY).removePlayer();
				seizeBunker(newX, newY);
			} else if (attackerWins(x, y, newX, newY) && !hasBunker(newX, newY)) {
				defendingTeam.removePlayer(defendingPlayer);
				map.getCell(newX, newY).removePlayer();
			} else {
				getCurrentTeam().removePlayer(player);
				map.getCell(x, y).removePlayer();
			}
		}
	}

	public boolean attackerWins(int x, int y, int newX, int newY) {

		boolean wins = false;
		String type1 = getPlayerType(x, y);
		String type2 = getPlayerType(newX, newY);

		if (type1.equals(type2) || (type1.equals(Player.RED) && type2.equals(Player.BLUE))
				|| (type1.equals(Player.BLUE) && type2.equals(Player.GREEN))
				|| (type1.equals(Player.GREEN) && type2.equals(Player.RED)))
			wins = true;

		return wins;
	}

	public void attack() {

		for (int i = 0; i < getCurrentTeam().getNumPlayers(); i++) {
			Player player = getCurrentTeam().getPlayer(i);
			if (Player.playerType(player).equals(Player.BLUE))
				blueAttack(player);
			else if (Player.playerType(player).equals(Player.GREEN))
				greenAttack(player);
			else
				redAttack(player);

		}
	}

	public void blueAttack(Player player) {

		int x = player.getX();
		int y = player.getY();
		boolean goingLeft = true;
		int left = 1;
		int right = 1;

		while (x - left > 0 || x + right <= map.getWidth()) {
			if (goingLeft) {
				int i = x - left;
				if (i > 0) {
					conquer(x, y, i, y);
					if (!hasPlayer(x, y))
						break;
					left++;
				}
				goingLeft = false;
			} else {
				int i = x + right;
				if (i <= map.getWidth()) {
					conquer(x, y, i, y);
					if (!hasPlayer(x, y))
						break;
					right++;
				}
				goingLeft = true;
			}
		}
	}

	public void greenAttack(Player player) {

		int x = player.getX();
		int y = player.getY();
		boolean goingLeft = true;
		boolean goingUp = true;
		int d1 = 1;
		int d2 = 1;
		int d3 = 1;
		int d4 = 1;
		while ((x - d1 > 0 && y - d1 > 0) || (x + d2 <= map.getWidth() && y - d2 > 0)
				|| (x - d3 > 0 && y + d3 <= map.getHeight())
				|| (x + d4 <= map.getWidth() && y + d4 <= map.getHeight())) {
			if (goingLeft && goingUp) {
				int i = x - d1;
				int j = y - d1;
				if (i > 0 && j > 0) {
					conquer(x, y, i, j);
					if (!hasPlayer(x, y))
						break;
					d1++;
				}
				goingLeft = false;
			} else if (!goingLeft && goingUp) {
				int i = x + d2;
				int j = y - d2;
				if (i <= map.getWidth() && j > 0) {
					conquer(x, y, i, j);
					if (!hasPlayer(x, y))
						break;
					d2++;
				}
				goingLeft = true;
				goingUp = false;
			} else if (goingLeft && !goingUp) {
				int i = x - d3;
				int j = y + d3;
				if (i > 0 && j <= map.getHeight()) {
					conquer(x, y, i, j);
					if (!hasPlayer(x, y))
						break;
					d3++;
				}
				goingLeft = false;
			} else {
				int i = x + d4;
				int j = y + d4;
				if (i <= map.getWidth() && j <= map.getHeight()) {
					conquer(x, y, i, j);
					if (!hasPlayer(x, y))
						break;
					d4++;
				}
				goingLeft = true;
				goingUp = true;
			}
		}
	}

	public void redAttack(Player player) {

		int x = player.getX();
		int y = player.getY();
		int i = x + 1;
		int j = y;

		while (i <= map.getWidth() && j <= map.getHeight()) {
			conquer(x, y, i, j);
			if (!hasPlayer(x, y))
				break;
			if (i == map.getWidth()) {
				j++;
				i = x;
			}
			i++;
		}
	}

	public void conquer(int x1, int y1, int x2, int y2) {
		if (hasPlayer(x2, y2)) {
			fight(x1, y1, x2, y2);
		} else if (hasBunker(x2, y2))
			seizeBunker(x2, y2);
	}

	public void skipTurn() {
		if (turnCount == numTeams - 1)
			turnCount = 0;
		else
			turnCount++;

		while (!getCurrentTeam().isAlive())
			turnCount++;

		for (int i = 0; i < numBunkers; i++)
			bunkers[i].incrementTreasury();
	}

	public boolean hasWinner() {
		int i = 0;
		int count = 0;
		while (i < numTeams) {
			if (teams[i].isAlive()) {
				count++;
			}
				i++;
		}
		return count == 1;
	}

	public String winner() {
		Team team = null;
		int i = 0;
		while (i < numTeams && team == null) {
			if (teams[i].isAlive()) {
				team = teams[i];
			}
			i++;
		}
		return team.getTeamName();
	}
	
	public int getAliveTeams() {
		int count = 0;
		for(int i=0; i<numTeams; i++) {
			if(teams[i].isAlive())
				count++;
		}
		return count;
	}
}
