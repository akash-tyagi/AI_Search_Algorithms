package othello;

import java.util.List;

public class MiniMax {
	char main;
	Board2 board;

	public MiniMax(Board2 board, char main) {
		this.board = board;
		this.main = main;
	}

	public Move miniMax(int limit, char P) {
		List<Move> legalMoves = board.legalMoves(P);
		if (legalMoves.size() == 0)
			return null;

		char O = (P == board.WHITE ? board.BLACK : board.WHITE);
		int depth = 0;

		for (Move move : legalMoves) {
			char[][] tempBoardState = board.copyBoard();
			System.out.println("\n\nConsidering State: #######");
			board.makeMove(P, move.x, move.y);
			board.printBoard();
			move.score = minValue(O, depth + 1, limit);
			System.out.println("move:(" + move.x + "," + move.y + ")  Score:"
					+ move.score);
			board.board = tempBoardState;
		}
		Move maxMove = getMaxMove(legalMoves);
		return maxMove;
	}

	private Move getMaxMove(List<Move> legalMoves) {
		Move maxMove = legalMoves.get(0);
		for (Move move : legalMoves) {
			System.out.println("value:" + move.score);
			if (maxMove.score < move.score) {
				maxMove = move;
			}
		}
		System.out.println("Max Value:" + maxMove.score);
		return maxMove;
	}

	private double minValue(char P, int depth, int limit) {
		char O = (P == board.WHITE ? board.BLACK : board.WHITE);
		List<Move> legalMoves = board.legalMoves(P);
		if (depth == limit || legalMoves.size() == 0) {
			return boardEvalScore();
		}

		for (Move move : legalMoves) {
			char[][] tempBoardState = board.copyBoard();
			board.makeMove(P, move.x, move.y);
			System.out.println(" MIN LEVEL-----------" + depth);
			board.printBoard();
			move.score = maxValue(O, depth + 1, limit);
			System.out.println("move: MINI:" + move.x + ":" + move.y
					+ "  Score:" + move.score);
			board.board = tempBoardState;
		}

		double min = Double.MAX_VALUE;
		for (Move move : legalMoves) {
			if (min > move.score) {
				min = move.score;
			}
		}
		return min;
	}

	private double maxValue(char P, int depth, int limit) {
		char O = (P == board.WHITE ? board.BLACK : board.WHITE);
		List<Move> legalMoves = board.legalMoves(P);
		if (depth == limit || legalMoves.size() == 0) {
			return boardEvalScore();
		}

		for (Move move : legalMoves) {
			char[][] tempBoardState = board.copyBoard();
			board.makeMove(P, move.x, move.y);
			System.out.println("MAX LEVEL --------" + depth);
			board.printBoard();
			move.score = minValue(O, depth + 1, limit);
			System.out.println("move: MINI:" + move.x + ":" + move.y
					+ "  Score:" + move.score);
			board.board = tempBoardState;
		}

		Move maxMove = getMaxMove(legalMoves);
		return maxMove.score;
	}

	private double boardEvalScore() {

		// mobility
		int blackMoves = board.legalMoves('B').size();
		int whiteMove = board.legalMoves('W').size();
		int mobility = blackMoves - whiteMove;

		// diskdifference
		int diskDiff = board.getScore();

		int rating = 0;
		int[][] eval_table = { { 99, -8, 8, 6, 6, 8, -8, 99 },
				{ -8, -24, -4, -3, -3, -4, -24, -8 },
				{ 8, -4, 7, 4, 4, 7, -4, 8 }, { 6, -3, 4, 0, 0, 4, -3, 6 },
				{ 6, -3, 4, 0, 0, 4, -3, 6 }, { 8, -4, 7, 4, 4, 7, -4, 8 },
				{ -8, -24, -4, -3, -3, -4, -24, -8 },
				{ 99, -8, 8, 6, 6, 8, -8, 99 } };

		for (int x = 0; x < board.size; x++)
			for (int y = 0; y < board.size; y++) {
				if (board.board[x][y] == main)
					rating += eval_table[x][y];
			}

		int blackStability = 0, whiteStability = 0;
		for (int i = 0; i < board.size; i++) {
			for (int j = 0; j < board.size; j++) {
				if (board.board[i][j] != board.EMPTY) {
					if ((stableDirection(i, j, 1, 0) || stableDirection(i, j,
							-1, 0))
							&& (stableDirection(i, j, 0, 1) || stableDirection(
									i, j, 0, -1))
							&& (stableDirection(i, j, 1, 1) || stableDirection(
									i, j, -1, -1))
							&& (stableDirection(i, j, 1, -1) || stableDirection(
									i, j, -1, 1))) {

						if (board.board[i][j] == 'B') {
							blackStability++;
						} else {
							whiteStability++;
						}
					}
				}
			}
		}
		int stability = blackStability - whiteStability;

		if (main == 'W') {
			mobility = -1 * mobility;
			diskDiff = -1 * diskDiff;
			stability = -1 * stability;
		}

		int score = rating + mobility + diskDiff;

		if (main == 'W')
			return score;
		return score;
	}

	private boolean stableDirection(int i, int j, int v, int h) {

		char color = board.board[i][j];
		boolean stable = true;
		while (i + v < board.size && j + h < board.size && i + v >= 0
				&& j + h >= 0 && stable) {

			if (board.board[i + v][j + h] != color) {
				stable = false;
			}
			i = i + v;
			j = j + h;
		}
		return stable;
	}

}
