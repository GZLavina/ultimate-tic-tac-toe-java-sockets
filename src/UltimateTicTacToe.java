
public class UltimateTicTacToe {
	Game[][] board;
	Game currentGame;
	int[] score;
	
	public UltimateTicTacToe() {
		this.board = new Game[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.board[i][j] = new Game();
			}
		}
		
		this.currentGame = null;
		this.score = new int[2];
	}
	
	public boolean makeMove(char player, int[] coords) {
		if (this.currentGame.move(player, coords)) {
			this.currentGame = board[coords[0]][coords[1]];
			return true;
		}
		
		return false;
	}
	
	public void pickGame(int[] coords) {
		this.currentGame = board[coords[0]][coords[1]];
	}
}
