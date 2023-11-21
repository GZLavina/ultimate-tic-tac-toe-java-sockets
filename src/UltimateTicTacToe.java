
public class UltimateTicTacToe {
	public TicTacToe[][] board;
	public TicTacToe currentGame;
	public int[] lastMove;
	public volatile char winner;

	public UltimateTicTacToe() {
		this.board = new TicTacToe[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.board[i][j] = new TicTacToe();
			}
		}

		this.currentGame = null;
		this.winner = Constants.NO_PLAYER;
		this.lastMove = null;
	}

	public boolean makeMove(char player, int[] coords) {
		if (this.currentGame.move(player, coords)) {
			this.currentGame = board[coords[0]][coords[1]].winner == Constants.NO_PLAYER ?
					board[coords[0]][coords[1]] : null;
			this.lastMove = coords;
			this.updateWinner();
			return true;
		}

		return false;
	}

	public boolean pickGame(int[] coords) {
		if (board[coords[0]][coords[1]].winner != Constants.NO_PLAYER || board[coords[0]][coords[1]].full) {
			return false;
		}

		this.currentGame = board[coords[0]][coords[1]];
		return true;
	}

	public void updateWinner() {
		int ties = 0;
		int xScore = 0;
		int oScore = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.board[i][j].winner == Constants.PLAYER_X) {
					xScore++;
				} else if (this.board[i][j].winner == Constants.PLAYER_O) {
					oScore++;
				} else if (this.board[i][j].full) {
					ties++;
				}
			}
		}
		if (xScore >= 5) {
			this.winner = Constants.PLAYER_X;
		} else if (oScore >= 5) {
			this.winner = Constants.PLAYER_O;
		} else if (ties + xScore + oScore >= 9) {
			if (xScore > oScore) {
				this.winner = Constants.PLAYER_X;
			} else if (oScore > xScore) {
				this.winner = Constants.PLAYER_O;
			} else {
				this.winner = Constants.TIE;
			}
		}
	}

	public String getBoardView() {
		StringBuilder str = new StringBuilder();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					TicTacToe game = this.board[i][k];
					for (int l = 0; l < 3; l++) {
						str.append(game.matrix[j][l]).append(' ');
					}
					if (k < 2) {
						str.append('|').append(' ');
					}
				}
				str.append('\n');
			}
			if (i < 2) {
				str.append("===================== \n");
			}
		}

		if (this.lastMove != null && this.currentGame != null) {
			str.append("Next move will be played in game ").append((char) (this.lastMove[0] + 65))
					.append(this.lastMove[1] + 1);
		}

		return str.toString();
	}
}
