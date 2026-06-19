package game;

public class BunkerClass implements Bunker {

	private Team team;
	private int x;
	private int y;
	private int treasury;
	private String bunkerName;

	public BunkerClass(int x, int y, int initTreasure, String bunkerName) {
		this.x = x;
		this.y = y;
		this.treasury = initTreasure;
		this.bunkerName = bunkerName;
	}

	public String getName() {
		return bunkerName;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getTreasury() {
		return treasury;
	}

	public void incrementTreasury() {
		treasury += 1;
	}

	public void changeTreasury(String type) {

		if (type.equals(Player.BLUE) || type.equals(Player.GREEN))
			treasury -= Player.BLUE_GREEN_COST;
		else
			treasury -= Player.RED_COST;
	}

}
