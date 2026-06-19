package game;

public interface Bunker {

	String getName();
	
	Team getTeam();
	
	int getX();
	
	int getY();
	
	int getTreasury();
	
	void setTeam(Team team);
	
	void incrementTreasury();
			
	void changeTreasury(String type);
}
