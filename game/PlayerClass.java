package game;

class PlayerClass implements Player {
	
	private Team team;
	private int x;
	private int y;

	public PlayerClass(int x, int y) {

		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void changeX(int value) {
		x = value;
	}

	public void changeY(int value) {
		y = value;

	}
	
	public Team getTeam() {
		return team;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}

}
