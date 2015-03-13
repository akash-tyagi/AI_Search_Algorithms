package othello;

import java.util.List;

public class MiniMax {
	char maxChar;
	Board2 board;

	public MiniMax(Board2 board, char main) {
		this.board = board;
		this.maxChar = main;
	}

	public Move miniMax(int limit, char P) {
		System.out.println("# Making move for " + P);
		List<Move> legalMoves = board.legalMoves(P);
		if (legalMoves.size() == 0)
			return null;

		char O = (P == board.WHITE ? board.BLACK : board.WHITE);
		int depth = 0;

		for (Move move : legalMoves) {
			char[][] tempBoardState = board.copyBoard();
			board.makeMove(P, move.x, move.y);
			move.score = minValue(O, depth + 1, limit);
			board.board = tempBoardState;
			System.out.println("# considering: (" + move.x + "," + move.y
					+ "),mm=" + move.score);
		}
		Move maxMove = getMaxMove(legalMoves);
		return maxMove;
	}

	private Move getMaxMove(List<Move> legalMoves) {
		Move maxMove = legalMoves.get(0);
		for (Move move : legalMoves) {
			if (maxMove.score < move.score) {
				maxMove = move;
			}
		}
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
			move.score = maxValue(O, depth + 1, limit);
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
			move.score = minValue(O, depth + 1, limit);
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
				if (board.board[x][y] == maxChar)
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

		if (maxChar == 'W') {
			mobility = -1 * mobility;
			diskDiff = -1 * diskDiff;
			stability = -1 * stability;
		}

		if (maxChar == 'W')
			return 7 * rating + mobility + 2 * diskDiff + 10 * stability;

		else
			return 10 * rating + 4 * mobility + diskDiff;

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
