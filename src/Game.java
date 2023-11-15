
public class Game {
	char[][] matrix;
	
	public Game() {
		this.matrix = new char[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.matrix[i][j] = ' ';
			}
		}
	}
	
	public boolean move(char player, int[] coords) {
		if (matrix[coords[0]][coords[1]] != ' ') {
			return false;
		}
		matrix[coords[0]][coords[1]] = player;
		return true;
	}
}
