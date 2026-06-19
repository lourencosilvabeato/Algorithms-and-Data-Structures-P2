package game;

public interface Player {

	static String BLUE = "blue";
	static String GREEN = "green";
	static String RED = "red";
	static int BLUE_GREEN_COST = 2;
	static int RED_COST = 4;


	public static String playerType(Player player) {
		String type = null;
		if(player instanceof BluePlayerClass)
			type = BLUE;
		if(player instanceof GreenPlayerClass)
			type = GREEN;
		if(player instanceof RedPlayerClass)
			type = RED;
		return type;
	}
	
	 int getX();
	 
	 int getY();
	 
	 void changeX(int value);
	 
	 void changeY(int value);
	 
	 Team getTeam();
	 
	 void setTeam(Team team);
}
