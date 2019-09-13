package junqi;

import mcts.Board;
import mcts.CallLocation;
import mcts.Move;

import java.util.ArrayList;

public class JunQiBoard implements Board {

    int [][]board;
    int currentPlayer;
    int winner;
    boolean draw;
    boolean gameOver;
    int turnsHasNoEatOtherSide;

    public JunQiBoard() {
        board = new int[4][11];
        turnsHasNoEatOtherSide = 0;
    }

    @Override
    public Board duplicate() {
        JunQiBoard newBoard = new JunQiBoard();
        newBoard.winner = winner;
        newBoard.currentPlayer = currentPlayer;
        newBoard.draw = draw;
        newBoard.gameOver = gameOver;
        newBoard.turnsHasNoEatOtherSide = turnsHasNoEatOtherSide;
        newBoard.board = new int[4][11];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 11; j++) {
                newBoard.board[i][j] = board[i][j];
            }
        }
        return newBoard;
    }

    @Override
    public void makeMove(Move m) {

    }

    @Override
    public ArrayList<Move> getMoves(CallLocation location) {
        return null;
    }

    @Override
    public boolean gameOver() {
        return gameOver;
    }

    @Override
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public int getQuantityOfPlayers() {
        return 2;
    }

    @Override
    public double[] getScore() {
        double []score;
        score = new double[2];
        if (!draw) {
            score[winner] = 1.0d;
        } else {
            score[0] = 0.5d;
            score[1] = 0.5d;
        }
        return score;
    }

    @Override
    public double[] getMoveWeights() {
        return null;
    }

    @Override
    public void bPrint() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.println(board[i][j] + " ");
            }
            System.out.println("");
        }
    }
}
