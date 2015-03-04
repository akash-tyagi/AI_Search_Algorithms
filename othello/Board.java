package othello;

import java.util.ArrayList;
import java.util.List;

public class Board {
	public int size;
	char[][] board;
	public static char WHITE = 'W';
	public static char BLACK = 'B';
	public static char EMPTY = '\0';

	public Board(int size) {
		this.size = size;
		board = new char[size][size];
	}

	public void init() {
		reset();
		int mid = (size - 1) / 2;
		board[mid][mid] = WHITE;
		board[mid][mid + 1] = BLACK;
		board[mid + 1][mid] = BLACK;
		board[mid + 1][mid + 1] = WHITE;
	}

	public void reset() {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				board[row][col] = EMPTY;
			}
		}
	}

	public void printBoard() {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				if (board[row][col] == WHITE)
					System.out.print(WHITE);
				else if (board[row][col] == BLACK)
					System.out.print(BLACK);
				else
					System.out.print('.');
			}
			System.out.println("");
		}
		System.out.println("");
	}

	public int getScore() {
		int blackStones = 0, whiteStones = 0;
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				if (board[row][col] == WHITE)
					whiteStones++;
				else if (board[row][col] == BLACK)
					blackStones++;
			}
		}
		return blackStones - whiteStones;
	}

	public List<Move> legalMoves(char P) {
		List<Move> legalMoves = new ArrayList<Move>();

		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				if (board[row][col] != EMPTY)
					continue;

				if (isOpponent(P, row, col - 1)
						|| isOpponent(P, row - 1, col - 1)
						|| isOpponent(P, row - 1, col)
						|| isOpponent(P, row - 1, col + 1)
						|| isOpponent(P, row, col + 1)
						|| isOpponent(P, row + 1, col + 1)
						|| isOpponent(P, row + 1, col)
						|| isOpponent(P, row + 1, col - 1)) {
					char[][] temp = copyBoard();
					int currScore = getScore();
					makeMove(P, row, col);
					if (Math.abs(currScore - getScore()) > 1) {
						Move move = new Move(row, col, getScore());
						legalMoves.add(move);
					}
					board = temp;
				}
			}
		}
		return legalMoves;
	}

	private char[][] copyBoard() {
		char[][] temp = new char[size][size];
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				temp[row][col] = board[row][col];
			}
		}
		return temp;
	}

	private boolean isOpponent(char P, int x, int y) {
		if (x >= size || y >= size || x < 0 || y < 0)
			return false;
		char opponent = (P == WHITE ? BLACK : WHITE);
		return board[x][y] == opponent;

	}

	public boolean makeMove(char P, int x, int y) {
		if (x >= size || y >= size || x < 0 || y < 0 || board[x][y] != EMPTY) {
			return false;
		}
		board[x][y] = P;

		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {

				if (board[row][col] != P)
					continue;

				if (row == x) {
					flipRow(P, row, y, col);
				} else if (col == y) {
					flipCol(P, col, x, row);
				} else if (Math.abs(row - x) == Math.abs(col - y)) {
					flipDiag(P, row, col, x, y);
				}
			}
		}
		return true;
	}

	private void flipDiag(char P, int row1, int col1, int row2, int col2) {
		char opponent = (P == WHITE ? BLACK : WHITE);
		boolean legal = true;
		int row, col;
		row = row1;
		col = col1;
		while (true) {
			row = row > row2 ? row - 1 : row + 1;
			col = col > col2 ? col - 1 : col + 1;
			if (row == row2)
				break;
			else if (board[row][col] != opponent) {
				legal = false;
				break;
			}
		}

		if (legal) {
			row = row1;
			col = col1;
			while (row != row2 && col != col2) {
				row = row > row2 ? row - 1 : row + 1;
				col = col > col2 ? col - 1 : col + 1;
				if (row == row2)
					break;
				board[row][col] = P;
				// System.out.println("Flipping " + row + " " + col + " To:" +
				// P);
			}
		}

	}

	private void flipCol(char P, int col, int row1, int row2) {
		char opponent = (P == WHITE ? BLACK : WHITE);
		int min = row1 > row2 ? row2 : row1;
		int max = row1 > row2 ? row1 : row2;

		boolean legal = true;
		for (int i = min + 1; i < max; i++) {
			if (board[i][col] != opponent) {
				legal = false;
				break;
			}
		}

		if (legal) {
			for (int i = min + 1; i < max; i++) {
				// System.out.println("Flipping " + i + " " + col + " To:" + P);
				board[i][col] = P;
			}
		}
	}

	private void flipRow(char P, int row, int col1, int col2) {
		char opponent = (P == WHITE ? BLACK : WHITE);
		int min = col2 > col1 ? col1 : col2;
		int max = col2 > col1 ? col2 : col1;

		boolean legal = true;
		for (int j = min + 1; j < max; j++) {
			if (board[row][j] != opponent) {
				legal = false;
				break;
			}
		}

		if (legal) {
			for (int j = min + 1; j < max; j++) {
				board[row][j] = P;
				// System.out.println("Flipping " + row + " " + j + " To:" + P);
				printBoard();
			}
		}
	}
}
