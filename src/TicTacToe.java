public class TicTacToe {
    char[][] matrix;
    char winner;
    boolean full;

    public TicTacToe() {
        this.full = false;
        this.winner = Constants.NO_PLAYER;
        this.matrix = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.matrix[i][j] = Constants.NO_PLAYER;
            }
        }
    }

    public boolean move(char player, int[] coords) {
        if (matrix[coords[0]][coords[1]] != Constants.NO_PLAYER) {
            return false;
        }

        matrix[coords[0]][coords[1]] = player;
        updateWinner();
        updateFull();
        return true;
    }

    public void updateFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.matrix[i][j] == Constants.NO_PLAYER) {
                    this.full = false;
                    return;
                }
            }
        }

        this.full = true;
    }

    public void updateWinner() {
        char player;
        int playerScore = 0;

        // Check rows
        for (int i = 0; i < 3; i++) {
            player = this.matrix[i][0];
            if (player == Constants.NO_PLAYER) {
                continue;
            }
            for (int j = 0; j < 3; j++) {
                if (this.matrix[i][j] == player) {
                    playerScore++;
                }
            }
            if (playerScore == 3) {
                this.winner = player;
                return;
            }
            playerScore = 0;
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            player = this.matrix[0][i];
            if (player == Constants.NO_PLAYER) {
                continue;
            }
            for (int j = 0; j < 3; j++) {
                if (this.matrix[j][i] == player) {
                    playerScore++;
                }
            }
            if (playerScore == 3) {
                this.winner = player;
                return;
            }
            playerScore = 0;
        }

        //Check diagonals
        if (this.matrix[1][1] != Constants.NO_PLAYER &&
                ((this.matrix[0][0] == this.matrix[1][1] && this.matrix[1][1] == this.matrix[2][2]) ||
                        (this.matrix[0][2] == this.matrix[1][1] && this.matrix[1][1] == this.matrix[2][0]))) {
            winner = this.matrix[1][1];
        }
    }
}
