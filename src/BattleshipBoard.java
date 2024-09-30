import java.util.ArrayList;

public class BattleshipBoard {

	public static void main(String[] args) {

		char[][] board = new char[10][10];
		char[][] nullBoard = null;

		// Add chars to board array
		for(char i = 0; i < board.length; i++){

			for(char j = 0; j < board.length; j++) {

				board[i][j] = '.';
			}
		}

		board[3][0] = 'S';
		board[3][1] = 'S';

		board[1][0] = '*';
		board[1][1] = '*';

		board[5][0] = 'S';
		board[5][1] = 'S';

		board[3][7] = 'S';
		board[3][8] = '*';

		board[3][3] = '*';
		board[3][4] = '*';
		board[3][5] = 'S';

		board[0][7] = '*';
		board[0][8] = '*';
		board[0][9] = '*';

		board[8][6] = 'S';
		board[8][7] = 'S';
		board[8][8] = '*';
		board[8][9] = '*';

		board[9][0] = '*';
		board[9][1] = '*';

		board[6][0] = 'S';
		board[6][1] = 'S';
		board[6][2] = 'S';
		board[6][3] = 'S';

		board[4][4] = '*';
		board[4][5] = 'S';
		board[4][6] = '*';
		board[4][7] = '*';
		board[4][8] = '*';		

		board[7][1] = 'S';
		board[7][2] = 'S';
		board[7][3] = 'S';
		board[7][4] = 'S';
		board[7][5] = 'S';	
		
		board[5][6] = '*';
		board[5][7] = '*';
		board[5][8] = '*';
		board[5][9] = '*';
		
		board[1][8] = '*';
		board[1][7] = '*';
		board[1][6] = '*';
		board[1][5] = '*';
		board[1][4] = '*';
		
		board[7][7] = '*';
		board[7][8] = 'S';
		board[7][9] = '*';
		
		board[8][0] = '*';
		board[8][1] = '*';
		board[8][2] = 'S';

		// Print board
		for(char i = 0; i < board.length; i++){

			for(char j = 0; j < board.length; j++) {

				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}

		// Task 1.1 - testing
		char Char = '.';
		System.out.println("\n" + Char + " is a valid board character: " + validChar(Char));

		// Task 1.2 - testing
		System.out.println("\nThe board is valid: " + validateBoard(board));

		// Task 1.3 - testing
		System.out.println("\nThe number of ships sunk is: " + numberSunk(board));

		// Task 1.4 - testing
		System.out.println("\n1) Invalid row\n2) Invalid Column\n3) Hit\n4) Miss"
				+ "\n5) Repeated hit\nOUTCOME: " + hit(board, 2, 'A'));

		// Task 1.5 - testing
		System.out.println();

		String shipType = "4";
		String damageType = "sunk";
		System.out.println("Ship type: " + shipType +
				"\nDamage type: " + damageType + 
				"\nNumber of ships matching description = " + countShips(board, shipType, damageType));

		// Bonus Question - testing
		System.out.println();
		//System.out.println(bonusQuestion(board));
	}

	// Task 1.1 Valid Board Square
	static boolean validChar(char character) {

		if(character == '.' || character == '*' || character == 'S') {
			return true;
		}
		else
			return false;
	}

	// Task 1.2 Valid Board
	static int validateBoard(char[][] boardUsed) {

		//null = 4011
		//wrong size = 5153
		//invalid value in the board = 78
		//valid = 100

		// Check for null
		if(boardUsed == null) return 4011;

		// Check board length
		if(boardUsed.length != 10) return 5153; //rows
		if(boardUsed[0].length != 10) return 5153; //columns

		// Check character by character for valid values
		for(char i = 0; i < boardUsed.length; i++){

			for(char j = 0; j < boardUsed.length; j++) {

				if(!(boardUsed[i][j] == '.' || boardUsed[i][j] == '*' || boardUsed[i][j] == 'S')) {
					return 78;
				}		
			}

		}

		// Board is valid
		return 100;
	}

	// Task 1.3 Number Sunk
	static short numberSunk(char[][] board) {

		boolean emptyBoard = false;
		short noShipsBoard = 9999;
		
		// Check for empty board
		for (char i = 0; i < board.length; i++) {

			for(char j = 0; j < board.length; j++) {

				if (board[i][j] == 'S' || board[i][j] == '*') {
					emptyBoard = true;
				}
			}
		}
		if (emptyBoard == false) return noShipsBoard; 
		
		int ship = 0;
		short shipSunk = 0;
		boolean isPreviousS = false; // Boolean to check if previous char is 'S' in case
		//a 5/4/3 row ship is only half destroyed 

		for(char i = 0; i < board.length; i++){
			ship = 0; // Restart value for new row
			isPreviousS = false; // Restart value for new row
			for(char j = 0; j < board.length; j++) {
				if (isPreviousS == true) ship = 0;

				if (board[i][j] == '*') {
					ship++;
				}
				// Check in case there's more than one ship in a row
				else if (board[i][j] == '.' || board[i][j] == 'S') {
					if (board[i][j] == 'S') {
						isPreviousS = true;
						ship = 0; // Restart ship value in case S comes after * before checking for ships
						//sunk, S right before * means the ship is not fully sunk
					}
					if (board[i][j] == '.') isPreviousS = false;
					// Check if it's a whole ship sunk
					switch (ship) {
					case 2: case 3: case 4: case 5: 
						shipSunk++;
						break;				
					}
					ship = 0;
				}	
			}	
			// Check if it's a whole ship sunk for the new row
			switch (ship) {
			case 2: case 3: case 4: case 5:
				shipSunk++;
				break;
			}
		}

		return shipSunk;
	}

	// Task 1.4 Hit
	static int hit(char[][] board, int a, char b) {

		// Changes to index of column
		int c = 0;
		switch (b) {
		case 'A': c = 0; break;
		case 'B': c = 1; break;
		case 'C': c = 2; break;
		case 'D': c = 3; break;
		case 'E': c = 4; break;
		case 'F': c = 5; break;
		case 'G': c = 6; break;
		case 'H': c = 7; break;
		case 'I': c = 8; break;
		case 'J': c = 9; break;
		}

		// Changes to index of row
		int r = 0;
		switch (a) {
		case 1: r = 0; break;
		case 2: r = 1; break;
		case 3: r = 2; break;
		case 4: r = 3; break;
		case 5: r = 4; break;
		case 6: r = 5; break;
		case 7: r = 6; break;
		case 8: r = 7; break;
		case 9: r = 8; break;
		case 10: r = 9; break;
		}

		// Declare values for different outcomes
		int invalidRow = 1;
		int invalidColumn = 2;
		int hit = 3;
		int miss = 4;
		int repeatedHit = 5;

		// Check for outcome
		if (a > 10 || a < 1) {
			return invalidRow;
		}
		else if (b < 'A' || b > 'J') {
			return invalidColumn;
		}
		else if (board[r][c] == 'S') {
			return hit;
		}
		else if (board[r][c] == '*') {
			return repeatedHit;
		}
		else if (board[r][c] == '.') {
			return miss;
		}
		else return 0;
	}

	// Task 1.5 Count Ships
	static int countShips(char[][] board, String shipType, String damageType) {

		int invalidShipType = 999;	
		int invalidDamageType = 22999;
		int noShipsBoard = 111;
		boolean emptyBoard = false;

		if (shipType == null) return invalidShipType;
		if (damageType == null) return invalidDamageType;

		// Check for empty board
		for (char i = 0; i < board.length; i++) {

			for(char j = 0; j < board.length; j++) {

				if (board[i][j] == 'S' || board[i][j] == '*') {
					emptyBoard = true;
				}
			}
		}
		if (emptyBoard == false) return noShipsBoard; 

		switch (damageType.toLowerCase()) {
		case "undamaged": break;
		case "damaged": break;
		case "sunk": break;
		case "all types": break;
		default: return invalidDamageType; // Value to return in case of wrong damage type
		}

		// shipType needs to check for ID and Name
		String shipTypeLC = shipType.toLowerCase();
		int shipTypeID = 0;
		if (shipTypeLC == "carrier" || shipTypeLC == "battleship" || 
				shipTypeLC == "cruiser" || shipTypeLC == "destroyer") {
			switch (shipTypeLC) {
			case "carrier":
				shipTypeID = 1;
				break;
			case "battleship":
				shipTypeID = 2;
				break;
			case "cruiser":
				shipTypeID = 3;
				break;
			case "destroyer":
				shipTypeID = 4;
				break;
			}
		}
		else if (shipType == "1" || shipType == "2" || shipType == "3" || shipType == "4") {
			shipTypeID = Integer.parseInt(shipType);
		}
		else {
			return invalidShipType;
		}

		int shipSize = 0;
		switch (shipTypeID) {
		case 1: shipSize = 5; break;
		case 2: shipSize = 4; break;
		case 3: shipSize = 3; break;
		case 4: shipSize = 2; break;
		}

		int shipCount = 0;

		int shipDam = 0;
		int shipUn = 0;
		int shipSunk = 0;
		int ship = 0;
		
		boolean isPreviousS = false;

		if (damageType.equals("sunk")) {
			for(char i = 0; i < board.length; i++){
				ship = 0; // Restart value for new row
				isPreviousS = false; // Restart value for new row
				for(char j = 0; j < board.length; j++) {
					if (isPreviousS == true) ship = 0;

					if (board[i][j] == '*') {
						ship++;
					}
					// Check in case there's more than one ship in a row
					else if (board[i][j] == '.' || board[i][j] == 'S') {
						if (board[i][j] == 'S') {
							isPreviousS = true;
							ship = 0; // Restart ship value in case S comes after * before checking for ships
							//sunk, S right before * means the ship is not fully sunk
						}
						if (board[i][j] == '.') isPreviousS = false;
						// Check it's a whole ship sunk
						switch (ship) {
						case 2: case 3: case 4: case 5: 
							if (ship == shipSize) {
								shipSunk++;
							}
							break;				
						}
						ship = 0;
					}	
				}	
				// Needed to check in case a new row of ships start
				switch (ship) {
				case 2: case 3: case 4: case 5:
					if (ship == shipSize) {
						shipSunk++;
					}
					break;
				}
			}
			// 1/4 sunk
		}
		else if (damageType.equals("damaged")) {
			ArrayList<Character> damagedShipArr = new ArrayList<Character>();

			for(char i = 0; i < board.length; i++){
				ship = 0;
				damagedShipArr.clear();
				for(char j = 0; j < board.length; j++) {

					if (board[i][j] == '*' || board[i][j] == 'S') {
						ship++;
						damagedShipArr.add(board[i][j]);
					}
					// Check in case there's more than one ship in a row
					else if (board[i][j] == '.') {
						switch (ship) {
						case 2: case 3: case 4: case 5: 
							if (ship == shipSize) {
								if (damagedShipArr.contains('*') && damagedShipArr.contains('S')) {
									shipDam++;
								}
							}
							break;				
						}
						ship = 0;
						damagedShipArr.clear();
					}	
				}	
				// Needed to check in case a new row of ships start
				switch (ship) {
				case 2: case 3: case 4: case 5:
					if (ship == shipSize) {
						if (damagedShipArr.contains('*') && damagedShipArr.contains('S')) {
							shipDam++;
						}
					}
					break;
				}
			}
			// 2/4 damaged
		}
		else if (damageType.equals("undamaged")) {
			for(char i = 0; i < board.length; i++){
				ship = 0;
				for(char j = 0; j < board.length; j++) {

					if (board[i][j] == 'S') {
						ship++;
					}
					// Check in case there's more than one ship in a row
					else if (board[i][j] == '.') {
						switch (ship) {
						case 2: case 3: case 4: case 5: 
							if (ship == shipSize) {
								shipUn++;
							}
							break;				
						}
						ship = 0;
					}
					else if (board[i][j] == '*') {
						ship = 0;
					}
				}	
				// Needed to check in case a new row of ships start
				switch (ship) {
				case 2: case 3: case 4: case 5:
					if (ship == shipSize) {
						shipUn++;
					}
					break;
				}
			}
			// 3/4 undamaged
		}
		else if (damageType.equals("all types")) {
			for(char i = 0; i < board.length; i++){
				ship = 0;
				for(char j = 0; j < board.length; j++) {

					if (board[i][j] == '*' || board[i][j] == 'S') {
						ship++;
					}
					// Check in case there's more than one ship in a row
					else if (board[i][j] == '.') {
						switch (ship) {
						case 2: case 3: case 4: case 5: 
							if (ship == shipSize) {
								shipCount++;
							}
							break;				
						}
						ship = 0;
					}	
				}	
				// Needed to check in case a new row of ships start
				switch (ship) {
				case 2: case 3: case 4: case 5:
					if (ship == shipSize) {
						shipCount++;
					}
					break;
				}
			}
			// 4/4 all types
		}

		if (damageType.equals("undamaged")) return shipUn;
		if (damageType.equals("damaged")) return shipDam;
		if (damageType.equals("sunk")) return shipSunk;
		if (damageType.equals("all types")) return shipCount;
		else return 0;

	}

	// Bonus Question
	/*static ArrayList<String> bonusQuestion(char[][] board) {

		ArrayList<String> shipsOnBoard = new ArrayList<String> ();
		ArrayList<Integer> shipDamage = new ArrayList<Integer>();

		int ship = 0;
		for(char i = 0; i < board.length; i++){
			ship = 0;
			for(char j = 0; j < board.length; j++) {

				if (board[i][j] == '*' || board[i][j] == 'S') {
					ship++;
				}
				// Check in case there's more than one ship in a row
				else if (board[i][j] == '.') {
					switch (ship) {
					case 2:
						shipsOnBoard.add("Destroyer");
						break;	
					case 3:
						shipsOnBoard.add("Cruiser");
						break;	
					case 4:
						shipsOnBoard.add("Battleship");
						break;	
					case 5:
						shipsOnBoard.add("Carrier");
						break;				
					}
					ship = 0;
				}	
			}	
			// Needed to check in case a new row of ships start
			switch (ship) {
			case 2:
				shipsOnBoard.add("Destroyer");
				break;	
			case 3:
				shipsOnBoard.add("Cruiser");
				break;	
			case 4:
				shipsOnBoard.add("Battleship");
				break;	
			case 5:
				shipsOnBoard.add("Carrier");
				break;				
			}
		}
		
		ArrayList<Character> damagedShipArr = new ArrayList<Character>();
		int shipSize = 0;
		String shipType = "";
		
		for(char i = 0; i < board.length; i++){
			ship = 0;
			damagedShipArr.clear();
			for(char j = 0; j < board.length; j++) {

				if (board[i][j] == '*' || board[i][j] == 'S') {
					ship++;
					damagedShipArr.add(board[i][j]);
				}
				// Check in case there's more than one ship in a row
				else if (board[i][j] == '.') {
					switch (ship) {
					case 2: 
						shipSize = 2;
						shipType = "destroyer";
						break;
					case 3: 
						shipSize = 3;
						shipType = "cruiser";
						break;
					case 4: 
						shipSize = 4;
						shipType = "battleship";
						break;
					case 5: 
						shipSize = 5;
						shipType = "carrier";
						break;				
					}
				
				if (shipSize >= 2 || shipSize <= 5) {
					int asterisk = 0;
					int s = 0;
					for (Character c : damagedShipArr) {
						if (c == '*') asterisk++;
						if (c == 'S') s++;
					}
					
					int result = (asterisk/(s + asterisk))*100;
					shipDamage.add(result);
						ship = 0;
						damagedShipArr.clear();
					}	
				}

			}	
			// Needed to check in case a new row of ships start
			switch (ship) {
			case 2: 
				shipSize = 2;
				shipType = "destroyer";
				break;
			case 3: 
				shipSize = 3;
				shipType = "cruiser";
				break;
			case 4: 
				shipSize = 4;
				shipType = "battleship";
				break;
			case 5: 
				shipSize = 5;
				shipType = "carrier";
				break;				
			}
			
			if (shipSize >= 2 || shipSize <= 5) {
				int asterisk = 0;
				int s = 0;
				for (Character c : damagedShipArr) {
					if (c == '*') asterisk++;
					if (c == 'S') s++;
				}
				
				int result = (asterisk/(s + asterisk))*100;
				shipDamage.add(result);
					ship = 0;
					damagedShipArr.clear();
				}
		}
		
		System.out.println(shipDamage);
		return shipsOnBoard;





	}*/
}
