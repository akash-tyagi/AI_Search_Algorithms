package othello;

import java.util.Scanner;

public class PlayOthello {
	public static void main(String args[]) {
		int size = 4;// Integer.parseInt(args[0]);
		char opponent = 'B';// args[1];
		int depth = 5;// Integer.parseInt(args[2]);

		Board board = new Board(size);
		board.init();
		board.printBoard();

		board.testBoard();
		// board.makeMove('B', 4, 1);
		// System.out.println("######" + board.getScore());
		// // board.printBoard();
		// board.makeMove('W', 5, 0);
		// System.out.println("######" + board.getScore());
		// board.makeMove('B', 1, 1);
		// System.out.println("######" + board.getScore());
		// board.makeMove('B', 1, 4);
		// System.out.println("######" + board.getScore());
		// board.makeMove('W', 0, 5);
		// System.out.println("######" + board.getScore());
		//
		// List<Move> moves = board.legalMoves('W');
		// for (Move move : moves) {
		// System.out.println("x:" + move.x + " y:" + move.y + " score:"
		// + move.score);
		// }
		// board.printBoard();

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
