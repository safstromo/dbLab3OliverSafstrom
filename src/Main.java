import java.sql.*;
import java.util.Scanner;

public class Main {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		while (true) {
			printMenu();

			switch (sc.nextLine()) {
				case "1" -> addGame();
				case "2" -> selectAllFromGame();
				case "3" -> update();
				case "4" -> remove();
				case "e","E" -> System.exit(0);
			}
		}

	}

	private static void addGame() {
		System.out.println("Enter game name: ");
		String gameName = sc.nextLine();

		System.out.println("Enter price: ");
		double price = sc.nextDouble();

		printGameCategory();
		insertGame(gameName, price,getCategorySwitch());


	}

	private static int getCategorySwitch() {
		int category = 0;
		switch (sc.nextInt()){
			case 1 -> category = 1;
			case 2 -> category = 2;
			case 3 -> category = 3;
			case 4 -> category = 4;
			case 5 -> category = 5;

		}
		return category;
	}


	private static void show() {
	}

	private static void update() {
	}

	private static void remove() {
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

	private static void selectAllFromGame() {
		String sql = "SELECT * FROM game";

		try (Connection conn = connect();
			 Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(sql);

			// loop through the result set
			while (rs.next()) {
				System.out.println(rs.getInt("gameId") + "\t" +
						rs.getString("gameName"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void insertStudio(String studioName) {
		String sql = "INSERT INTO studio(studioName) VALUES(?)";

		try (Connection conn = connect();
			 PreparedStatement querty = conn.prepareStatement(sql)) {
			querty.setString(1, studioName);
			querty.executeUpdate();
			System.out.println(studioName + " added");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
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


	private static void printMenu() {
		System.out.println("""
				1. Add
				2. Show
				3. Update
				4. Remove
				e. Exit
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

}
