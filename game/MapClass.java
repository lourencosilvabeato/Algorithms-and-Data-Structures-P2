package game;

class MapClass implements Map {

	private int height;
	private int width;
	private Cell[][] map;

	public MapClass(int width, int height) {
		this.height = height;
		this.width = width;
		this.map = new CellClass[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				map[i][j] = new CellClass();
			}
		}
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public Cell[][] getGrid() {
		return map;
	}

	public Cell getCell(int x, int y) {
		return map[y - 1][x - 1];
	}

	public boolean hasBunker(int x, int y) {
		return map[y - 1][x - 1].hasBunker();
	}

	public boolean hasPlayer(int x, int y) {
		return map[y - 1][x - 1].hasPlayer();
	}
}
