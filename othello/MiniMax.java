package othello;

import java.util.List;

public class MiniMax {
	public char mainChar;
	public Board2 board;
	int[][] eval_table;
	int size;

	public MiniMax(int size) {
		this.size = size;

		eval_table = new int[size][size];
		eval_table[0][0] = eval_table[0][size - 1] = eval_table[size - 1][0] = eval_table[size - 1][size - 1] = 99;
		for (int i = 0, j = size - 1; i < j; i++, j--) {
			eval_table[1][i] = eval_table[1][j] = -size + 2 * i;
			eval_table[size - 2][i] = eval_table[size - 2][j] = -size + 2 * i;
			eval_table[i][1] = eval_table[j][1] = -size + 2 * i;
			eval_table[i][size - 2] = eval_table[j][size - 2] = -size + 2 * i;
		}

		for (int x = 0; x < size; x++) {
			for (int i = 0, j = size - 1; i < j; i++, j--) {
				if (eval_table[x][i] == 0) {
					eval_table[x][i] = eval_table[x][j] = size - 2 * i;
				}
			}
		}
	}

	public Move miniMax(int limit, char P) {
		System.out.println("# Making move for " + P);
		List<Move> legalMoves = board.legalMoves(P);
		if (legalMoves.size() == 0)
			return null;

		char O = (P == board.WHITE ? board.BLACK : board.WHITE);
		int depth = 0;
		double alpha = Double.MIN_VALUE;
		double beta = Double.MAX_VALUE;
		Move max = legalMoves.get(0);

		for (Move move : legalMoves) {
			char[][] tempBoardState = board.copyBoard();
			board.makeMove(P, move.x, move.y);
			move.score = minValue(O, depth + 1, limit, alpha, beta);
			board.board = tempBoardState;
			System.out.println("# considering: (" + move.x + "," + move.y
					+ "),mm=" + move.score);

			if (move.score > max.score)
				max = move;

			if (max.score >= beta)
				return max;

			if (move.score > alpha)
				alpha = move.score;
		}
		return max;
	}

	private double minValue(char P, int depth, int limit, double alpha,
			double beta) {
		char O = (P == board.WHITE ? board.BLACK : board.WHITE);
		List<Move> legalMoves = board.legalMoves(P);
		if (depth == limit || legalMoves.size() == 0) {
			return boardEvalScore();
		}

		double min = Double.MAX_VALUE;
		for (Move move : legalMoves) {
			char[][] tempBoardState = board.copyBoard();
			board.makeMove(P, move.x, move.y);
			move.score = maxValue(O, depth + 1, limit, alpha, beta);
			board.board = tempBoardState;

			if (move.score < min)
				min = move.score;

			if (min <= alpha)
				return min;

			if (move.score < beta)
				beta = move.score;
		}

		return min;
	}

	private double maxValue(char P, int depth, int limit, double alpha,
			double beta) {
		char O = (P == board.WHITE ? board.BLACK : board.WHITE);
		List<Move> legalMoves = board.legalMoves(P);
		if (depth == limit || legalMoves.size() == 0) {
			return boardEvalScore();
		}

		double max = Double.MIN_VALUE;
		for (Move move : legalMoves) {
			char[][] tempBoardState = board.copyBoard();
			board.makeMove(P, move.x, move.y);
			move.score = minValue(O, depth + 1, limit, alpha, beta);
			board.board = tempBoardState;

			if (move.score > max)
				max = move.score;

			if (max >= beta)
				return max;

			if (move.score > alpha)
				alpha = move.score;
		}
		return max;
	}

	private double boardEvalScore() {

		// mobility
		int blackMoves = board.legalMoves('B').size();
		int whiteMove = board.legalMoves('W').size();
		int mobility = blackMoves - whiteMove;

		// diskdifference
		int diskDiff = board.getScore();

		int rating = 0;
		for (int x = 0; x < size; x++)
			for (int y = 0; y < size; y++) {
				if (board.board[x][y] == mainChar)
					rating += eval_table[x][y];
			}

		int blackStability = 0, whiteStability = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
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
		if (mainChar == 'W') {
			mobility = -1 * mobility;
			diskDiff = -1 * diskDiff;
			stability = -1 * stability;
		}

		if (mainChar == 'W')
			return rating * 100 + 100 * mobility + 10 * diskDiff + 50
					* stability;

		else
			return rating + 100 * mobility + 10 * diskDiff + 50 * stability;

	}

	private boolean stableDirection(int i, int j, int v, int h) {

		char color = board.board[i][j];
		boolean stable = true;
		while (i + v < size && j + h < size && i + v >= 0 && j + h >= 0
				&& stable) {

			if (board.board[i + v][j + h] != color) {
				stable = false;
			}
			i = i + v;
			j = j + h;
		}
		return stable;
	}

}
