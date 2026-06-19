package game;

class MapIteratorClass implements MapIterator {
	private Cell[][] map;
    private Team currentTeam;
	private int height;
	private int width;
	private int nextX;
	private int nextY;

	 public MapIteratorClass(Map map, Team currentTeam, int width, int height) {
	        this.height = height;
	        this.width = width;
	        this.map = map.getGrid();
	        this.currentTeam = currentTeam;
	        nextX = 0;
	        nextY = 0;
	    }

	public boolean hasNext() {
		return (nextX < width && nextY < height);
	}

	public boolean endOfLine() {
		return nextX == width - 1;
	}

	public boolean startOfLine() {
		return nextX == 0;
	}

	public int getCurrentX() {
		return nextX;
	}

	public int getCurrentY() {
		return nextY+1;
	}

	public Cell next() {
	    Cell nextCell = map[nextY][nextX];

	    if (nextX == width - 1 && nextY != height - 1) {
	        nextY++;
	        nextX = 0;
	    } 
	    else
	        nextX++;
	    return nextCell;
	}

	public boolean belongsToTeam(Cell cell) {
		return cell.hasBunker() && cell.getBunker().getTeam() == currentTeam || 
				cell.hasPlayer() && cell.getPlayer().getTeam() == currentTeam;
	}
}
