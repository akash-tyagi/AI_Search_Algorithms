package othello;

import java.util.Scanner;

public class PlayOthello {
	public static void main(String args[]) {
		int size = 8;// Integer.parseInt(args[0]);
		char opponent = 'W';// args[1];
		int depth = 5;// Integer.parseInt(args[2]);

		Board2 board = new Board2(size, opponent);
		board.init();
		board.printBoard();
		board.testBoard();

		// String command;
		// Scanner scanIn = new Scanner(System.in);
		//
		// while (true) {
		// command = scanIn.nextLine();
		// if (command.equals("quit")) {
		// break;
		// } else if (command.equals("init")) {
		// board.init();
		// } else if (command.equals("reset")) {
		// board.reset();
		// } else {
		// String[] parse = command.split(" ");
		// if (parse[0].equals("move")) {
		// board.move(parse[1].charAt(0), depth);
		// } else if (parse[0].equals("put")) {
		// System.out.println("Make Move");
		// board.makeMove(parse[1].charAt(0),
		// Integer.parseInt(parse[2]),
		// Integer.parseInt(parse[3]));
		// board.printBoard();
		// }
		// }
		//
		// }
		// scanIn.close();
	}

}
