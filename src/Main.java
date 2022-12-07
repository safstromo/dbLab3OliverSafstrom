import java.sql.*;
import java.util.Scanner;

public class Main {
	static final String GAME = "game";
	static final String CATEGORY = "category";
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		while (true) {
			printMenu();

			switch (sc.nextLine()) {
				case "1" -> add();
				case "2" -> show();
				case "3" -> update();
				case "4" -> remove();
				case "e", "E" -> System.exit(0);
			}
		}

	}

	private static void add() {
		printAddMenu();
		switch (getInput()){
			case "1" -> addGame();
			case "2" -> addCategory();
		}
	}

	private static void addGame() {
		System.out.println("Enter game name: ");
		String gameName = getInput();

		System.out.println("Enter price: ");
		double price = sc.nextDouble();
		selectAllFrom(CATEGORY);
		System.out.println("Enter category ID: ");
		int category = sc.nextInt();

		insertGame(gameName, price, category);
		sc.nextLine();
	}

	private static void addCategory() {
		System.out.println("Enter category name: ");
		String name = getInput();
		insertCategory(name);
	}


	private static void show() {
		printShowMenu();

		switch (getInput()) {
			case "1" -> selectAllInnerJoin();
			case "2" -> searchForGame();
			case "3" -> countGames();
		}
	}

	private static void countGames() {
		String sql = "SELECT COUNT(*) FROM game";

		try (Connection conn = connect();
			 Statement query = conn.createStatement()) {
			ResultSet rs = query.executeQuery(sql);

			while (rs.next()) {
				System.out.println("Number of game in database: " + rs.getInt("COUNT(*)"));
				System.out.println("Press any key to continue.....");
				getInput();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void searchForGame() {
		String sql = "SELECT * FROM game INNER JOIN category ON game.gameCategoryId = category.categoryId WHERE gameName = ?";

		try (Connection conn = connect();
			 PreparedStatement query = conn.prepareStatement(sql)) {
			System.out.println("Enter game name: ");
			query.setString(1, getInput());
			ResultSet rs = query.executeQuery();

			while (rs.next()) {
				System.out.println("GameId: " + rs.getInt("gameId") + "\t" +
						"Name: " + rs.getString("gameName") + "\t" +
						"Price: " + rs.getInt("gamePrice") + "\t" +
						"Category: " + rs.getString("categoryName") + "\n");
				System.out.println("Press any key to continue.....");
				getInput();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void update() {
		printUpdateMenu();

		switch (getInput()) {
			case "1" -> updateCategoryName();
			case "2" -> updateGamePrice();
		}

	}

	private static void updateGamePrice() {
		String sql = "UPDATE game SET gamePrice = ? WHERE gameId = ?";

		try (Connection conn = connect();
			 PreparedStatement query = conn.prepareStatement(sql)) {
			selectAllFrom(GAME);
			System.out.println("Enter ID to update");
			String input = getInput();
			query.setInt(2, Integer.parseInt(input));
			System.out.println("Enter new price: ");
			query.setString(1, getInput());
			query.executeUpdate();
			System.out.println("ID " + input + " updated");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static void updateCategoryName() {
		String sql = "UPDATE category SET categoryName = ? WHERE categoryId = ?";

		try (Connection conn = connect();
			 PreparedStatement query = conn.prepareStatement(sql)) {
			selectAllFrom(CATEGORY);
			System.out.println("Enter ID to update");
			String input = getInput();
			query.setInt(2, Integer.parseInt(input));
			System.out.println("Enter new name: ");
			query.setString(1, getInput());
			query.executeUpdate();
			System.out.println("ID " + input + " updated");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static void remove() {
		printRemoveMenu();
		switch (getInput()) {
			case "1" -> removeFrom(GAME);
			case "2" -> removeFrom(CATEGORY);
		}

	}

	private static void removeFrom(String table) {
		String sql = "DELETE FROM " + table + " WHERE " + table + "Id = ?";

		try (Connection conn = connect();
			 PreparedStatement query = conn.prepareStatement(sql)) {
			selectAllFrom(table);
			System.out.println("Enter ID to remove");
			String input = getInput();
			query.setInt(1, Integer.parseInt(input));
			query.executeUpdate();
			System.out.println("ID " + input + " removed");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}


	}

	private static String getInput() {
		return sc.nextLine();
	}

	private static Connection connect() {
		Connection connection = null;
		try {
			String url = "jdbc:sqlite:src/db/labb3.db";
			connection = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}
		return connection;
	}

	private static void selectAllFrom(String table) {
		String sql = "SELECT * FROM " + table;

		try (Connection conn = connect();
			 Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				System.out.println("ID: " + rs.getInt(table + "Id") + "\t" +
						"Name: " + rs.getString(table + "Name")
				);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void selectAllInnerJoin() {
		String sql = "SELECT * FROM game INNER JOIN category ON game.gameCategoryId = category.categoryId";

		try (Connection conn = connect();
			 Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				System.out.println("GameId: " + rs.getInt("gameId") + "\t" +
						"Name: " + rs.getString("gameName") + "\t" +
						"Price: " + rs.getInt("gamePrice") + "\t" +
						"Category: " + rs.getString("categoryName") + "\n");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Press any key to continue.....");
		getInput();
	}

	private static void insertGame(String gameName, double price, int category) {
		String sql = "INSERT INTO game(gameName, gamePrice, gameCategoryId) VALUES(?,?,?)";

		try (Connection conn = connect();
			 PreparedStatement query = conn.prepareStatement(sql)) {
			query.setString(1, gameName);
			query.setDouble(2, price);
			query.setInt(3, category);
			query.executeUpdate();
			System.out.println(gameName + " added");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void insertCategory(String categoryName) {
		String sql = "INSERT INTO category(categoryName) VALUES(?)";

		try (Connection conn = connect();
			 PreparedStatement query = conn.prepareStatement(sql)) {
			query.setString(1, categoryName);

			query.executeUpdate();
			System.out.println(categoryName + " added");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}


	private static void printMenu() {
		System.out.println("""
				1. Add
				2. Show
				3. Update
				4. Remove
				e. Exit
				""");
	}

	private static void printAddMenu() {
		System.out.println("""
				To what table do you want to add?
				1. Game
				2. Category
				""");
	}

	private static void printGameCategory() {
		System.out.println("""
				Select category:
				1. FPS
				2. RTS
				3. MMO
				4. ADVENTURE
				5. ACTION
				""");
	}

	private static void printShowMenu() {
		System.out.println("""
				What do you want to see?
				1. Show from all tables
				2. Search for game
				3. Number of games in database
				""");
	}

	private static void printRemoveMenu() {
		System.out.println(""" 
				Do you want to remove from :
				1. Game
				2. Category""");
	}

	private static void printUpdateMenu() {
		System.out.println("""
				What do you want to update?
				1. Category name
				2. Game price
				""");
	}

}
