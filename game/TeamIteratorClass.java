package game;

 class TeamIteratorClass implements TeamIterator {


	Team[] teams;
	private int size;
	private int nextIdx;

	public TeamIteratorClass(Team[] teams, int size) {
		this.size = size;
		this.teams = teams;
		nextIdx = 0;
	}

	public boolean hasNext() {
		return nextIdx < size;
	}

	public Team next() {
		return teams[nextIdx++];
	}
}
