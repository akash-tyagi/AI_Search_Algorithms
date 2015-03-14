package othello;

import java.util.Scanner;

public class PlayOthello {
	public static void main(String args[]) {
		int size = Integer.parseInt(args[0]);
		char opponent = args[1].charAt(0);
		int depth = Integer.parseInt(args[2]);

		Board board = new Board(size, opponent);

		String command;
		Scanner scanIn = new Scanner(System.in);

		while (true) {
			command = scanIn.nextLine();
			if (command.equals("quit")) {
				break;
			} else if (command.equals("init")) {
				board.init();
			} else if (command.equals("reset")) {
				board.reset();
			} else {
				String[] parse = command.split(" ");
				if (parse[0].equals("move")) {
					board.tryMove(parse[1].charAt(0), depth);
					System.out.println("# score:" + board.getScore());
				} else if (parse[0].equals("put")) {
					board.makeMove(parse[1].charAt(0),
							Integer.parseInt(parse[2]),
							Integer.parseInt(parse[3]));
					board.totalPieces++;
				}
			}
			System.out.flush();
			board.printBoard();
			if (board.totalPieces >= size * size
					|| (board.totalPieces > 0
							&& board.legalMoves('W').size() == 0 && board
							.legalMoves('B').size() == 0)) {
				System.out.println("game over");
				break;
			}
		}
		scanIn.close();
	}
}
