package game;

public interface Map {
	
	int getHeight();
	
	int getWidth();
	
	Cell[][] getGrid();
	
	Cell getCell(int x, int y);
	
	boolean hasBunker(int x, int y);
	
	boolean hasPlayer(int x, int y);

}
