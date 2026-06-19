package game;

public interface MapIterator {

	
	boolean hasNext();
	
	boolean endOfLine();
	
	boolean startOfLine();
	
	int getCurrentY();
	
	 int getCurrentX();
	
	Cell next();
	
	boolean belongsToTeam(Cell cell);
}
