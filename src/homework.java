import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class homework {

	private static Scanner in;
	private static PrintWriter out;

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub

		File inputFile = new File("./input.txt");
		in = new Scanner(inputFile);
		out = new PrintWriter("./output.txt");

		
//		for(int i = 1; i <= 10; i++) {
//			String inputPath = "Test" + i +"/input.txt";
//			File inputFile = new File(inputPath);
//			in = new Scanner(inputFile);
//			String outputPath = "./output" + i + ".txt";
//			out = new PrintWriter(outputPath);			
//		}
		
		int n = 0;
		String mode = "";
		String youplay = "";
		int depth = 0;
		
		n = in.nextInt();
		in.nextLine();
		
		int[][] cellValues= new int[n][n];
		String[][] boardStates = new String[n][n];
		
		mode = in.nextLine();
		youplay = in.nextLine();
		depth = in.nextInt();
		
//		System.out.println(n);
//		System.out.println(mode);
//		System.out.println(youplay);
//		System.out.println(depth);
		
		//Set<String> openSpaces = new HashSet<>();
		ArrayList<String> openSpaces = new ArrayList<>();
		
		int curVal = 0;
		
		for(int row = 0; row < n; row++) { // for each row
			
			in.nextLine();
			for(int col = 0; col < n; col++) {
				
				cellValues[row][col] = in.nextInt();
				//System.out.print(cellValues[row][col] + " ");
			}
			//System.out.println();
		}
		in.nextLine();
		
		for(int row = 0; row < n; row++) { // for boardStates
			
			String line = in.nextLine();
			
			for(int col = 0; col < n; col++) {

				boardStates[row][col] = Character.toString(line.charAt(col));
				//System.out.print(boardStates[row][col]);
				
				if(boardStates[row][col].equals("X") || boardStates[row][col].equals("O")) {
					
					if(boardStates[row][col].equals("X")) {
						curVal += cellValues[row][col];
					} else {// "O"
						curVal -= cellValues[row][col];
					}
				} else { // boardStates[row][col].equals(".")
					
					openSpaces.add(row + " " + col);
				}
			}
			//System.out.println();
		}
		
		//System.out.println("End of Input\n");
		
		//Test: maxValue()
		//System.out.println(maxValue(cellValues, boardStates, 0, depth, curVal, openSpaces, Integer.MIN_VALUE, Integer.MAX_VALUE));	
		//System.out.println(minValue(cellValues, boardStates, 0, depth, curVal, openSpaces));
		
		
		//Test: minimaxDecision()
		//System.out.println(minimaxDecision(cellValues, boardStates, 0, depth, curVal, youplay, openSpaces));
		//System.out.println(alphaBetaSearch(cellValues, boardStates, 0, depth, curVal, youplay, openSpaces));
		
		if(mode.equals("MINIMAX")) {
			
			String moveStr = minimaxDecision(cellValues, boardStates, 0, depth, curVal, youplay, openSpaces);
			//System.out.println(decode(moveStr));
			out.print(decode(moveStr));
			String[] splitted = moveStr.split(" ");
			int row = Integer.valueOf(splitted[0]);
			int col = Integer.valueOf(splitted[1]);
			String move = splitted[2];
			
			boardStates[row][col] = youplay;
			
			if(move.equals("Raid")) {
				boardStates = result(boardStates, row, col);
//				String[][] tmp;
//				if((tmp = result(boardStates, row, col)) != null) {
//					boardStates = tmp;
//				}
			}
			
		} else if(mode.equals("ALPHABETA")) {
			
			String moveStr = alphaBetaSearch(cellValues, boardStates, 0, depth, curVal, youplay, openSpaces);
			//System.out.println(decode(moveStr));
			out.print(decode(moveStr));
			String[] splitted = moveStr.split(" ");
			int row = Integer.valueOf(splitted[0]);
			int col = Integer.valueOf(splitted[1]);
			String move = splitted[2];
			
			boardStates[row][col] = youplay;
			
			if(move.equals("Raid")) {
				
				boardStates = result(boardStates, row, col);
//				String[][] tmp;
//				if((tmp = result(boardStates, row, col)) != null) {
//					boardStates = tmp;
//				}
			}
		}
		
		//output the board state
		for(int i = 0; i < n; i++) {	
			out.println();
			for(int j = 0; j < n; j++) {
				//System.out.print(boardStates[i][j]);
				out.print(boardStates[i][j]);
			}
			//System.out.println();
		}
		out.close();
	} //main
	
	// decode my returned result 
	// PRE: str has the format: row col move
	private static String decode(String str) {
		
		String[] splitted = str.split(" ");
		
		int col = Integer.valueOf(splitted[1]);
		char first = (char)('A' + col);
		String second = Integer.valueOf(splitted[0])+1 + "";
		
		return first + second + " " + splitted[2];
	}
	
	
	//return the copy of the state
	private static String[][] copyState(String[][] state) {
		int n = state.length;
		String[][] copy = new String[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				copy[i][j] = state[i][j];
			}
		}
		return copy;
	}
	
	// return the Raid() result after changing state[row][col], null if Stake()
	// PRE: state[row][col] is "X" or "O"
	private static String[][] result(String[][] state, int row, int col) {
		
		int n = state.length;
		String ourGang = state[row][col];
		boolean isRaid = false;
		//Stake() or Raid() checking state[row-1][col]...
		if(ourGang.equals("X")) {
			
			// Raid
			if((row-1 >= 0 && state[row-1][col].equals("X")) || (row+1 < n && state[row+1][col].equals("X")) || (col-1 >= 0 && state[row][col-1].equals("X")) || (col+1 < n && state[row][col+1].equals("X"))) {

				//state = raid(state, row, col);
				if(row-1 >= 0 && state[row-1][col].equals("O")) {
					state[row-1][col] = "X";
					isRaid = true;
				}
				if(row+1 < n && state[row+1][col].equals("O")) {
					state[row+1][col] = "X";
					isRaid = true;
				}
				if(col-1 >= 0 && state[row][col-1].equals("O")) {
					state[row][col-1] = "X";
					isRaid = true;
				}
				if(col+1 < n && state[row][col+1].equals("O")) {
					state[row][col+1] = "X";
					isRaid = true;
				}				
			}
			
		} else { // ourGang.equals("O")
			// Raid
			if((row-1 >= 0 && state[row-1][col].equals("O")) || (row+1 < n && state[row+1][col].equals("O")) || (col-1 >= 0 && state[row][col-1].equals("O")) || (col+1 < n && state[row][col+1].equals("O"))) {

				//state = raid(state, row, col);
				if(row-1 >= 0 && state[row-1][col].equals("X")) {
					state[row-1][col] = "O";
					isRaid = true;
				}
				if(row+1 < n && state[row+1][col].equals("X")) {
					state[row+1][col] = "O";
					isRaid = true;
				}
				if(col-1 >= 0 && state[row][col-1].equals("X")) {
					state[row][col-1] = "O";
					isRaid = true;
				}
				if(col+1 < n && state[row][col+1].equals("X")) {
					state[row][col+1] = "O";
					isRaid = true;
				}	
			}
		}
		
		if(isRaid) {
			return state;
		}
		return null; //Stake()
	}
	
	
	// return the optimal move represented by a String, e.g. "2 3"
	private static String minimaxDecision(int[][] cellValues, String[][] state, int plys, int depth, int curVal, String youplay, ArrayList<String> openSpaces) {
		
		String res = "";
		
		
		if(youplay.equals("X")) {// next move is "X"
			
			int max = Integer.MIN_VALUE;
			
			for(int i = 0; i < openSpaces.size(); i++) {
				
				String move = "Stake"; // default
				
				String loc = openSpaces.get(i);
				String[] splited = loc.split(" ");
				int row = Integer.valueOf(splited[0]);
				int col = Integer.valueOf(splited[1]);				

				String[][] tmpState = copyState(state);
				int oldLocalValue = getLocalValue(cellValues, tmpState, row, col);
				
				tmpState[row][col] = "X";
				ArrayList<String> tmpOpenSpaces = new ArrayList<>(openSpaces);
				tmpOpenSpaces.remove(i);
				
				// Stake Move
				int newLocalValue = getLocalValue(cellValues, tmpState, row, col);
				int outcome = minValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces);				
				if(outcome > max) {
					max = outcome;
					res = loc + " " + move;
				} else if(outcome == max) {
					String[] arr = res.split(" ");
					if(arr[2].equals("Raid")) { // previous best move is Raid
						res = loc + " " + move;
					}
				}
	
				// Raid Move
				String[][] raidResult;
				if((raidResult = result(tmpState, row, col)) != null) {
					tmpState = raidResult;
					move = "Raid";
					newLocalValue = getLocalValue(cellValues, tmpState, row, col);
					outcome = minValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces);
					if(outcome > max) {
						max = outcome;
						res = loc + " " + move;
					}					
				}
				
				//tmpState = result(tmpState, row, col);
//				int newLocalValue = getLocalValue(cellValues, tmpState, row, col);
//				int outcome = minValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces);
//				if(outcome > max) {
//					max = outcome;
//					res = loc + " " + move;
//				} else if(outcome == max) { //
//					String[] arr = res.split(" ");
//					if(move.equals("Stake") && arr[2].equals("Raid")) {
//						res = loc + " " + move;
//					}
//				}
				
			} // for all open spaces...
			
		} else {// youplay is "O"
			
			int min = Integer.MAX_VALUE;

			for(int i = 0; i < openSpaces.size(); i++) {
				String move = "Stake"; // default
				
				String loc = openSpaces.get(i);
				String[] splited = loc.split(" ");
				int row = Integer.valueOf(splited[0]);
				int col = Integer.valueOf(splited[1]);				

				String[][] tmpState = copyState(state);
				int oldLocalValue = getLocalValue(cellValues, tmpState, row, col);
				
				tmpState[row][col] = "O";
				ArrayList<String> tmpOpenSpaces = new ArrayList<>(openSpaces);
				tmpOpenSpaces.remove(i);
				
				// Stake Move
				int newLocalValue = getLocalValue(cellValues, tmpState, row, col);
				int outcome = maxValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces);
				if(outcome < min) {
					min = outcome;
					res = loc + " " + move;
				} else if(outcome == min) {
					String[] arr = res.split(" ");
					if(arr[2].equals("Raid")) { // previous best move is Raid
						res = loc + " " + move;
					}
				}
				
				// Raid Move
				String[][] raidResult;
				if((raidResult = result(tmpState, row, col)) != null) {
					tmpState = raidResult;
					move = "Raid";
					newLocalValue = getLocalValue(cellValues, tmpState, row, col);
					///Found it!!!!
					outcome = maxValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces);
					if(outcome < min) {
						min = outcome;
						res = loc + " " + move;
					}
				}
				
//				int newLocalValue = getLocalValue(cellValues, tmpState, row, col);
//				///Found it!!!!
//				int outcome = maxValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces);
//				if(outcome < min) {
//					min = outcome;
//					res = loc + " " + move;
//				} else if(outcome == min) { //
//					String[] arr = res.split(" ");
//					if(move.equals("Stake") && arr[2].equals("Raid")) {
//						res = loc + " " + move;
//					}
//				}
			} // for each empty spaces			
		}
		return res;
	}
	
	// move is X
	private static int maxValue(int[][] cellValues, String[][] state, int plys, int depth, int curVal, ArrayList<String> openSpaces) {
		
		if(plys >= depth || openSpaces.size() == 0) { //openSpaces isEmpty()?
			return curVal;
		}
		
		int v = Integer.MIN_VALUE;

		for(int i = 0; i < openSpaces.size(); i++) {
			
			String loc = openSpaces.get(i);
			String[] splited = loc.split(" ");
			int row = Integer.valueOf(splited[0]);
			int col = Integer.valueOf(splited[1]);
			
			String[][] tmpState = copyState(state);
			int oldLocalValue = getLocalValue(cellValues, tmpState, row, col);
			
			tmpState[row][col] = "X";
			ArrayList<String> tmpOpenSpaces = new ArrayList<>(openSpaces);
			tmpOpenSpaces.remove(i);
			
			// Stake Move
			int newLocalValue = getLocalValue(cellValues, tmpState, row, col);
			
			v = Integer.max(v, minValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces));			
			
			//tmpState = result(tmpState, row, col);
			String[][] raidResult;
			if((raidResult = result(tmpState, row, col)) != null) {
				tmpState = raidResult;
				newLocalValue = getLocalValue(cellValues, tmpState, row, col);
				v = Integer.max(v, minValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces));				
			}
			// int newLocalValue = getLocalValue(cellValues, tmpState, row, col);
			
			// v = Integer.max(v, minValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces));
			
		}		
		
		return v;
	}
	
	
	// Move is O
	private static int minValue(int[][] cellValues, String[][] state, int plys, int depth, int curVal, ArrayList<String> openSpaces) {
		
		if(plys >= depth || openSpaces.size() == 0) { //openSpaces isEmpty()?
			return curVal;
		}
		
		int v = Integer.MAX_VALUE;		
		
		for(int i = 0; i < openSpaces.size(); i++) {
			
			String loc = openSpaces.get(i);
			String[] splited = loc.split(" ");
			int row = Integer.valueOf(splited[0]);
			int col = Integer.valueOf(splited[1]);
			
			String[][] tmpState = copyState(state);
			int oldLocalValue = getLocalValue(cellValues, tmpState, row, col);
			
			tmpState[row][col] = "O";
			ArrayList<String> tmpOpenSpaces = new ArrayList<>(openSpaces);
			tmpOpenSpaces.remove(i);
			
			// Stake Move
			int newLocalValue = getLocalValue(cellValues, tmpState, row, col);
			v = Integer.min(v, maxValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces));
			
			// Raid Move
			//tmpState = result(tmpState, row, col);
			String[][] raidResult;
			if((raidResult = result(tmpState, row, col)) != null) {
				tmpState = raidResult;
				newLocalValue = getLocalValue(cellValues, tmpState, row, col);
				
				v = Integer.min(v, maxValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces));				
			}
//			int newLocalValue = getLocalValue(cellValues, tmpState, row, col);
//			v = Integer.min(v, maxValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces));
			
		}

		return v;
	}
	
	
	
	// return the local value of state[row][col], the five spaces
	// PRE: 0 <= row < n, 0 <= col < n
	private static int getLocalValue(int[][] cellValues, String[][] state, int row, int col) {
		
		int n = state.length;
		int res = 0;
		
		if(state[row][col].equals("X")) {
			res += cellValues[row][col];
		} else if(state[row][col].equals("O")) {
			res -= cellValues[row][col];
		}
		
		if(row-1 >= 0) {
			
			if(state[row-1][col].equals("X")) {
				res += cellValues[row-1][col];
			} else if(state[row-1][col].equals("O")) {
				res -= cellValues[row-1][col];
			}
		}
		if(row+1 < n) {
			
			if(state[row+1][col].equals("X")) {
				res += cellValues[row+1][col];
			} else if(state[row+1][col].equals("O")) {
				res -= cellValues[row+1][col];
			}
		}
		if(col-1 >= 0) {
			
			if(state[row][col-1].equals("X")) {
				res += cellValues[row][col-1];
			} else if(state[row][col-1].equals("O")) {
				res -= cellValues[row][col-1];
			}
		}
		if(col+1 < n) {
			
			if(state[row][col+1].equals("X")) {
				res += cellValues[row][col+1];
			} else if(state[row][col+1].equals("O")) {
				res -= cellValues[row][col+1];
			}
		}
		return res;
	}
	
	// Alpha Beta Pruning
	// return the optimal move represented by a String, e.g. "2 3"
	private static String alphaBetaSearch(int[][] cellValues, String[][] state, int plys, int depth, int curVal, String youplay, ArrayList<String> openSpaces) {
		
		String res = "";
		
		if(youplay.equals("X")) {// next move is "X"
			
			
			int max = Integer.MIN_VALUE;
			
			for(int i = 0; i < openSpaces.size(); i++) {
				
				String move = "Stake"; // default
				
				String loc = openSpaces.get(i);
				String[] splited = loc.split(" ");
				int row = Integer.valueOf(splited[0]);
				int col = Integer.valueOf(splited[1]);
				
				String[][] tmpState = copyState(state);
				int oldLocalValue = getLocalValue(cellValues, tmpState, row, col);
				
				tmpState[row][col] = "X";
				ArrayList<String> tmpOpenSpaces = new ArrayList<>(openSpaces);
				tmpOpenSpaces.remove(i);
				
				// Stake Move
				int newLocalValue = getLocalValue(cellValues, tmpState, row, col);
				int outcome = minValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces, Integer.MIN_VALUE, Integer.MAX_VALUE);				
				if(outcome > max) {
					max = outcome;
					res = loc + " " + move;
				} else if(outcome == max) {
					String[] arr = res.split(" ");
					if(arr[2].equals("Raid")) { // previous best move is Raid
						res = loc + " " + move;
					}
				}
	
				// Raid Move
				String[][] raidResult;
				if((raidResult = result(tmpState, row, col)) != null) {
					tmpState = raidResult;
					move = "Raid";
					newLocalValue = getLocalValue(cellValues, tmpState, row, col);
					outcome = minValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces, Integer.MIN_VALUE, Integer.MAX_VALUE);
					if(outcome > max) {
						max = outcome;
						res = loc + " " + move;
					}
				}
				
				
				//tmpState = result(tmpState, row, col);
//				String[][] raidResult;
//				if((raidResult = result(tmpState, row, col)) != null) {
//					tmpState = raidResult;
//					move = "Raid";
//				}		
//				int newLocalValue = getLocalValue(cellValues, tmpState, row, col);
//				// O's move
//				int outcome = minValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces, Integer.MIN_VALUE, Integer.MAX_VALUE);
//				if(outcome > max) {
//					max = outcome;
//					res = loc + " " + move;
//				} else if(outcome == max) { //
//					String[] arr = res.split(" ");
//					if(move.equals("Stake") && arr[2].equals("Raid")) {
//						res = loc + " " + move;
//					}
//				}
			}		
			
		} else {// youplay is "O"
			
			int min = Integer.MAX_VALUE;

			for(int i = 0; i < openSpaces.size(); i++) {
				
				String move = "Stake"; // default
				
				String loc = openSpaces.get(i);
				String[] splited = loc.split(" ");
				int row = Integer.valueOf(splited[0]);
				int col = Integer.valueOf(splited[1]);
				
				String[][] tmpState = copyState(state);
				int oldLocalValue = getLocalValue(cellValues, tmpState, row, col);
				
				tmpState[row][col] = "O";
				ArrayList<String> tmpOpenSpaces = new ArrayList<>(openSpaces);
				tmpOpenSpaces.remove(i);
				
				// Stake Move
				int newLocalValue = getLocalValue(cellValues, tmpState, row, col);
				int outcome = maxValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces, Integer.MIN_VALUE, Integer.MAX_VALUE);
				if(outcome < min) {
					min = outcome;
					res = loc + " " + move;
				} else if(outcome == min) {
					String[] arr = res.split(" ");
					if(arr[2].equals("Raid")) { // previous best move is Raid
						res = loc + " " + move;
					}
				}
				
				// Raid Move
				String[][] raidResult;
				if((raidResult = result(tmpState, row, col)) != null) {
					tmpState = raidResult;
					move = "Raid";
					newLocalValue = getLocalValue(cellValues, tmpState, row, col);
					///Found it!!!!
					outcome = maxValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces, Integer.MIN_VALUE, Integer.MAX_VALUE);
					if(outcome < min) {
						min = outcome;
						res = loc + " " + move;
					}
				}
				
				//tmpState = result(tmpState, row, col);
//				String[][] raidResult;
//				if((raidResult = result(tmpState, row, col)) != null) {
//					tmpState = raidResult;
//					move = "Raid";
//				}
//				int newLocalValue = getLocalValue(cellValues, tmpState, row, col);
//				//X's move
//				int outcome = maxValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces, Integer.MIN_VALUE, Integer.MAX_VALUE);
//				if(outcome < min) {
//					min = outcome;
//					res = loc + " " + move;
//				} else if(outcome == min) { //
//					String[] arr = res.split(" ");
//					if(move.equals("Stake") && arr[2].equals("Raid")) {
//						res = loc + " " + move;
//					}
//				}			
			}			
		}
		return res;			
	}
	
	
	// Alpha Beta Pruning
	// move is X
	private static int maxValue(int[][] cellValues, String[][] state, int plys, int depth, int curVal, ArrayList<String> openSpaces,int alpha, int beta) {
		
		if(plys >= depth || openSpaces.size() == 0) { //openSpaces isEmpty()?
			return curVal;
		}
		
		int v = Integer.MIN_VALUE;

		for(int i = 0; i < openSpaces.size(); i++) {
			
			String loc = openSpaces.get(i);
			String[] splited = loc.split(" ");
			int row = Integer.valueOf(splited[0]);
			int col = Integer.valueOf(splited[1]);
			
			String[][] tmpState = copyState(state);
			int oldLocalValue = getLocalValue(cellValues, tmpState, row, col);
			
			tmpState[row][col] = "X";
			ArrayList<String> tmpOpenSpaces = new ArrayList<>(openSpaces);
			tmpOpenSpaces.remove(i);
			
			// Stake Move
			int newLocalValue = getLocalValue(cellValues, tmpState, row, col);
			
			v = Integer.max(v, minValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces, alpha, beta));			
			
			//tmpState = result(tmpState, row, col);
			String[][] raidResult;
			if((raidResult = result(tmpState, row, col)) != null) {
				tmpState = raidResult;
				newLocalValue = getLocalValue(cellValues, tmpState, row, col);
				
				v = Integer.max(v, minValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces, alpha, beta));				
			}
//			int newLocalValue = getLocalValue(cellValues, tmpState, row, col);
//			
//			v = Integer.max(v, minValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces, alpha, beta));
			if(v >= beta) {
				return v;
			}
			alpha = Integer.max(alpha, v);
		}
		return v;
	}
	
	// Alpha Beta Pruning
	// Move is O
	private static int minValue(int[][] cellValues, String[][] state, int plys, int depth, int curVal, ArrayList<String> openSpaces,int alpha, int beta) {
		
		if(plys >= depth || openSpaces.size() == 0) { //openSpaces isEmpty()?
			return curVal;
		}
		
		int v = Integer.MAX_VALUE;
		
		for(int i = 0; i < openSpaces.size(); i++) {
			
			String loc = openSpaces.get(i);
			String[] splited = loc.split(" ");
			int row = Integer.valueOf(splited[0]);
			int col = Integer.valueOf(splited[1]);
			
			String[][] tmpState = copyState(state);
			int oldLocalValue = getLocalValue(cellValues, tmpState, row, col);
			
			tmpState[row][col] = "O";
			ArrayList<String> tmpOpenSpaces = new ArrayList<>(openSpaces);
			tmpOpenSpaces.remove(i);
			
			// Stake Move
			int newLocalValue = getLocalValue(cellValues, tmpState, row, col);
			
			v = Integer.min(v, maxValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces, alpha, beta));			
			
			
			// Raid Move
			//tmpState = result(tmpState, row, col);
			String[][] raidResult;
			if((raidResult = result(tmpState, row, col)) != null) {
				tmpState = raidResult;
				newLocalValue = getLocalValue(cellValues, tmpState, row, col);
				
				v = Integer.min(v, maxValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces, alpha, beta));				
			}			
//			int newLocalValue = getLocalValue(cellValues, tmpState, row, col);
//			
//			v = Integer.min(v, maxValue(cellValues, tmpState, plys+1, depth, curVal - oldLocalValue + newLocalValue, tmpOpenSpaces, alpha, beta));
			if(v <= alpha) {
				return v;
			}
			beta = Integer.min(beta, v);
		}
		return v;
	}
}


