import java.util.Scanner;
import game.*;

public class Main {

	private static final String GAME = "game - Create a new game\n";
	private static final String MOVE = "move - Move a player\n";
	private static final String CREATE = "create - Create a player in a bunker\n";
	private static final String ATTACK = "attack - Attack with all players of the current team\n";
	private static final String STATUS = "status - Show the current state of the game\n";
	private static final String MAP = "map - Show the map of the current team\n";
	private static final String BUNKERS = "bunkers - List the bunkers of the current team, by the order they were seized\n";
	private static final String PLAYERS = "players - List the active players of the current team, by the order they were created\n";
	private static final String HELP = "help - Show available commands\n";
	private static final String QUIT = "quit - End program execution";
	private static final String NORTH = "north";
	private static final String SOUTH = "south";
	private static final String EAST = "east";
	private static final String WEST = "west";
	private static final String NEXT_TURN = "%s> ";
	private static final String LIST_BUNKERS = "%d bunkers:\n";
	private static final String LIST_TEAMS = "%d teams:\n";
	private static final String FATAL_ERROR = "FATAL ERROR: Insufficient number of teams.";
	private static final String INVALID_POSITION = "Invalid position.";
	private static final String BUNKER_NOT_CREATED = "Bunker not created.";
	private static final String TEAM_NOT_CREATED = "Team not created.";
	private static final String NO_PLAYER = "No player in that position.";
	private static final String INVALID_DIRECTION = "Invalid direction.";
	private static final String INVALID_MOVE = "Invalid move.";
	private static final String POSITION_OCCUPIED = "Position occupied.";
	private static final String OFF_THE_MAP = "Trying to move off the map.";
	private static final String NEW_POSITION = "%s player in position (%d, %d)\n";
	private static final String BUNKER_SEIZED = "Bunker seized.";
	private static final String WON_FIGHT_BUNKER = "Won the fight and bunker seized.";
	private static final String WON_FIGHT = "Won the fight.";
	private static final String PLAYER_ELIMINATED = "Player eliminated.";
	private static final String INVALID_TYPE = "Non-existent player type.";
	private static final String INVALID_BUNKER = "Non-existent bunker.";
	private static final String ILLEGALY_INVADED = "Bunker illegally invaded.";
	private static final String NOT_FREE = "Bunker not free.";
	private static final String INSUFFICIENT_COINS = "Insufficient coins for recruitment.";
	private static final String PLAYER_CREATED = "%s player created in %s\n";
	private static final String BUNKERS_STATUS = "%d %d\n%d bunkers:\n";
	private static final String TEAM_INFO = "%s (%s)\n";
	private static final String TEAMS_STATUS = "%s teams:\n";
	private static final String WINNER = "Winner is %s.\n";

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		GameSystemClass game = new GameSystemClass();

		System.out.print("> ");

		do {
			executeCommand(in, game);
		} while (!game.quitGame());

		in.close();
	}

	private static void executeCommand(Scanner in, GameSystemClass game) {

		String command = in.next().toUpperCase();

		switch (command) {
		case "GAME" -> game(in, game);
		case "MOVE" -> move(in, game);
		case "CREATE" -> create(in, game);
		case "ATTACK" -> attack(game);
		case "STATUS" -> status(game);
		case "MAP" -> {
			if (game.isRunning()) {
				map(game);
				System.out.printf(NEXT_TURN, game.getCurrentTeamName());
			}
			else
				System.out.print("Invalid command.\n> ");
		}
		case "BUNKERS" -> bunkers(game);
		case "PLAYERS" -> players(game);
		case "HELP" -> help(game);
		case "QUIT" -> {
			game.changeQuit();
			System.out.println("Bye.\n");
		}
		default -> {
			in.nextLine();
			System.out.print("Invalid command.\n> ");
		}
		}
	}

	private static void game(Scanner in, GameSystemClass game) {
		game.clearGame();

		int width = in.nextInt();
		int height = in.nextInt();
		int teamsNumber = in.nextInt();
		int bunkersNumber = in.nextInt();

		game.startGame(width, height);
		collectInfo(in, game, bunkersNumber, teamsNumber, width, height);

		if (game.isRunning())
			System.out.printf("%s> ", game.getCurrentTeamName());
		else
			System.out.print("> ");

	}

	private static void collectInfo(Scanner in, GameSystemClass game, int bunkersNumber, int teamsNumber, int width, int height) {

		System.out.printf(LIST_BUNKERS, bunkersNumber);
		for (int i = 0; i < bunkersNumber; i++) {
			int x = in.nextInt();
			int y = in.nextInt();
			int initTreasure = in.nextInt();
			String bunkerName = in.nextLine().trim();

			if (x < 0 || x > width || y < 0 || y > height || initTreasure <= 0 || 
					game.hasBunker(bunkerName)|| game.isCellOccupied(x, y))
				System.out.println(BUNKER_NOT_CREATED);
			else
				game.addBunker(x, y, initTreasure, bunkerName);
		}

		System.out.printf(LIST_TEAMS, teamsNumber);
		for (int i = 0; i < teamsNumber; i++) {
			String teamName = in.next().trim();
			String bunkerName = in.nextLine().trim();

			if (game.hasTeam(teamName) || !game.hasBunker(bunkerName) || !game.isAbandoned(bunkerName))
				System.out.println(TEAM_NOT_CREATED);
			else
				game.addTeam(teamName, bunkerName);
		}
		if (game.lessThanTwoTeams()) {
			game.changeRunning();
			System.out.println(FATAL_ERROR);
		}
	}

	private static void move(Scanner in, GameSystemClass game) {
		int x = in.nextInt();
		int y = in.nextInt();

		String directions = in.nextLine().trim();
		String dir[] = directions.split(" ");

		if (game.isRunning()) {

			if (x < 0 || x > game.getWidth() || y < 0 || y > game.getHeight())
				System.out.println(INVALID_POSITION);
			else if (!game.teamPlayerExists(x, y))
				System.out.println(NO_PLAYER);
			else {
				if (game.isBlue(x, y) || game.isGreen(x, y))
					moveOther(game, x, y, dir);
				else
					moveRed(game, x, y, dir);
			}
			if (!game.isRunning()) {
				System.out.printf(WINNER, game.winner());
				System.out.print("> ");
			}
			else {
				game.skipTurn();
				System.out.printf("%s> ", game.getCurrentTeamName());
			}
		}
		else
			System.out.print("Invalid command.\n> ");
	}

	private static void moveRed(GameSystemClass game, int x, int y, String[] dir) {

		for (int i = 0; i < dir.length; i++) {
			if(game.teamPlayerExists(x, y)) {
				if (!dir[i].equals(NORTH) && !dir[i].equals(SOUTH) && !dir[i].equals(EAST) && !dir[i].equals(WEST))
					System.out.println(INVALID_DIRECTION);
				else if (!game.insideLimits(x, y, dir[i]))
					System.out.println(OFF_THE_MAP);
				else if (!game.canMove(x, y, dir[i]))
					System.out.println(POSITION_OCCUPIED);
				else {
					int newX = game.getPlayerX(x, y, dir[i]);
					int newY = game.getPlayerY(x, y, dir[i]);
					moveConditions(game, x, y, newX, newY);
					game.move(x, y, newX, newY);
					x = newX;
					y = newY;
					if (game.teamPlayerExists(newX, newY))
						System.out.printf(NEW_POSITION, game.getPlayerType(newX, newY), newX, newY);
				}
			}
		}
	}

	private static void moveOther(GameSystemClass game, int x, int y, String[] dir) {

		if (!dir[0].equals(NORTH) && !dir[0].equals(SOUTH) && !dir[0].equals(EAST) && !dir[0].equals(WEST))
			System.out.println(INVALID_DIRECTION);
		else if (dir.length > 1)
			System.out.println(INVALID_MOVE);
		else if (!game.insideLimits(x, y, dir[0]))
			System.out.println(OFF_THE_MAP);
		else if (!game.canMove(x, y, dir[0]))
			System.out.println(POSITION_OCCUPIED);
		else {
			int newX = game.getPlayerX(x, y, dir[0]);
			int newY = game.getPlayerY(x, y, dir[0]);
			moveConditions(game, x, y, newX, newY);
			game.move(x, y, newX, newY);
			if (game.teamPlayerExists(newX, newY))
				System.out.printf(NEW_POSITION, game.getPlayerType(newX, newY), newX, newY);
		}
	}

	private static void moveConditions(GameSystemClass game, int x, int y, int newX, int newY) {
		if (game.hasBunker(newX, newY) && !game.belongsToTeam(newX, newY) && !game.hasPlayer(newX, newY)) {
			game.seizeBunker(newX, newY);
			System.out.println(BUNKER_SEIZED);
		} else if (game.hasBunker(newX, newY) && !game.belongsToTeam(newX, newY) && game.hasPlayer(newX, newY)
				&& !game.teamPlayerExists(newX, newY)) {
			if (game.attackerWins(x, y, newX, newY)) {
				game.fight(x, y, newX, newY);
				System.out.println(WON_FIGHT_BUNKER);
			} else {
				game.fight(x, y, newX, newY);
				System.out.println(PLAYER_ELIMINATED);
			}
		} else if (game.hasPlayer(newX, newY)) {
			if (game.attackerWins(x, y, newX, newY)) {
				game.fight(x, y, newX, newY);
				System.out.println(WON_FIGHT);
			} else {
				game.fight(x, y, newX, newY);
				System.out.println(PLAYER_ELIMINATED);
			}
		}
	}

	private static void create(Scanner in, GameSystemClass game) {

		if (game.isRunning()) {
			String playerType = in.next().trim();
			String bunkerName = in.nextLine().trim();

			if (!game.isTypeValid(playerType))
				System.out.println(INVALID_TYPE);
			else if (!game.hasBunker(bunkerName))
				System.out.println(INVALID_BUNKER);
			else if (!game.belongsToTeam(bunkerName))
				System.out.println(ILLEGALY_INVADED);
			else if (game.occupiedByPlayer(bunkerName))
				System.out.println(NOT_FREE);
			else if (!game.hasFunds(playerType, bunkerName))
				System.out.println(INSUFFICIENT_COINS);
			else {
				game.addPlayer(playerType, bunkerName);
				System.out.printf(PLAYER_CREATED, playerType, bunkerName);
			}
			game.skipTurn();
			System.out.printf(NEXT_TURN, game.getCurrentTeamName());
		}
	}

	private static void attack(GameSystemClass game) {

		if (game.isRunning()) {
			game.attack();
			if(game.noTeamPlayers() && game.noTeamBunkers())
				System.out.println("All players eliminated.");
			if (!game.isRunning()) {
				System.out.printf(WINNER, game.winner());
				System.out.print("> ");
			} else {
				map(game);
				game.skipTurn();
				System.out.printf("%s> ", game.getCurrentTeamName());
			}
		}
		else
			System.out.print("Invalid command.\n> ");

	}

	private static void status(GameSystemClass game) {

		if (game.isRunning()) {
			TeamIterator it = game.teamIterator();
			BunkerIterator it2 = game.allBunkerIterator();

			System.out.printf(BUNKERS_STATUS, game.getWidth(), game.getHeight(), game.getNumBunkers());

			while (it2.hasNext()) {
				Bunker bunker = it2.next();
				if(game.isAbandoned(bunker.getName()))
					System.out.printf("%s (without owner)\n", bunker.getName());
				else
					System.out.printf(TEAM_INFO, bunker.getName(), bunker.getTeam().getTeamName());
			}
			System.out.printf(TEAMS_STATUS, game.getAliveTeams());

			while (it.hasNext()) {
				Team team = it.next();
				System.out.printf("%s", team.getTeamName());

				if (it.hasNext())
					System.out.print("; ");
			}
			System.out.println();

			System.out.printf(NEXT_TURN, game.getCurrentTeamName());
		}
		else
			System.out.print("Invalid command.\n> ");
	}

	private static void map(GameSystemClass game) {

		System.out.printf("%d %d\n", game.getWidth(), game.getHeight());
		System.out.printf("**");
		for (int i = 0; i < game.getWidth(); i++) {
			if(i != game.getWidth() - 1)
				System.out.printf("%d ", i + 1);
			else
				System.out.printf("%d", i + 1);
			if (i == game.getWidth() - 1)
				System.out.println();
		}

		MapIterator it = game.mapIterator();

		while (it.hasNext()) {

			boolean start = it.startOfLine();
			boolean end = it.endOfLine();
			Cell cell = it.next();

			if (start)
				System.out.print(it.getCurrentY());
			if (it.belongsToTeam(cell)) {
				if (cell.hasBunker() && cell.hasPlayer())
					System.out.print(" O");
				else if (cell.hasBunker())
					System.out.print(" B");
				else if (cell.hasPlayer())
					System.out.print(" P");
				else
					System.out.print(" .");
			} else
				System.out.print(" .");
			if (end) {
				System.out.println();
			}
		}
	}

	private static void bunkers(GameSystemClass game) {

		if (game.isRunning()) {
			BunkerIterator it = game.bunkerIterator();

			if (game.noTeamBunkers())
				System.out.println("Without bunkers.");
			else {
				System.out.printf("%d bunkers:\n", game.getTeamBunkers());

				while (it.hasNext()) {
					Bunker bunker = it.next();
					System.out.printf("%s with %d coins in position (%d, %d)\n", bunker.getName(), bunker.getTreasury(),
							bunker.getX(), bunker.getY());
				}
			}
			System.out.printf(NEXT_TURN, game.getCurrentTeamName());
		}
		else
			System.out.print("Invalid command.\n> ");
	}

	private static void players(GameSystemClass game) {

		if (game.isRunning()) {
			PlayerIterator it = game.playerIterator();

			if (game.noTeamPlayers())
				System.out.println("Without players.");
			else {
				System.out.printf("%d players:\n", game.getTeamPlayers());

				while (it.hasNext()) {
					Player player = it.next();
					System.out.printf(NEW_POSITION, Player.playerType(player), player.getX(), player.getY());
				}
			}
			System.out.printf(NEXT_TURN, game.getCurrentTeamName());
		}
		else
			System.out.print("Invalid command.\n> ");
	}

	private static void help(GameSystemClass game) {

		if (!game.isRunning()) {
			System.out.println(GAME + HELP + QUIT);
			System.out.print("> ");
		} else {
			System.out.println(GAME + MOVE + CREATE + ATTACK + STATUS + MAP + BUNKERS + PLAYERS + HELP + QUIT);
		}
		if (game.isRunning())
			System.out.printf(NEXT_TURN, game.getCurrentTeamName());
	}
}
