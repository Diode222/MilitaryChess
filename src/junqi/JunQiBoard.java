package junqi;

import global.BoardInfo;
import global.ChessType;
import global.PositionType;
import mcts.Board;
import mcts.CallLocation;
import mcts.Move;
import utils.ChessStrengthCompare;
import utils.ListUDG;
import utils.PathFinder;

import java.util.ArrayList;
import java.util.List;

public class JunQiBoard implements Board {

    final static public int FIRST_HAND = 0;
    final static public int BACK_HAND = 1;

    int [][]board;
    int currentPlayer;
    int winner;
    boolean draw;
    boolean gameOver;
    int turnsHasNoEatOtherSide;

    public JunQiBoard() {
        board = new int[BoardInfo.LENGTH][BoardInfo.HEIGHT];
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
        newBoard.board = new int[BoardInfo.LENGTH][BoardInfo.HEIGHT];
        for (int i = 0; i < BoardInfo.LENGTH; i++) {
            for (int j = 0; j < BoardInfo.HEIGHT; j++) {
                newBoard.board[i][j] = board[i][j];
            }
        }
        return newBoard;
    }

    @Override
    public void makeMove(Move m) {
        // TODO
    }

    @Override
    public ArrayList<Move> getMoves(CallLocation location) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i < BoardInfo.LENGTH; i++) {
            for (int j = 0; j < BoardInfo.HEIGHT; j++) {
                int nowChessId = board[i][j];
                if(nowChessId == 0 || currentPlayer == 0 && nowChessId >= 26 || currentPlayer == 1 && nowChessId <= 25) {
                    continue;
                }

                int nowChessType = ChessType.getType(nowChessId);
                int nowPositionType = PositionType.getType(i, j);

                if (nowChessType == ChessType.MINE_CHESS || nowChessType == ChessType.FLAG_CHESS
                        || nowPositionType == PositionType.FLAG_POSITION) {
                    // 地雷和军旗不能动，大本营位置的棋子不能动
                    continue;
                }

                for (int u = 0; u < BoardInfo.LENGTH; u++) {
                    for (int v = 0; v < BoardInfo.HEIGHT; v++) {
                        int targetChessId = board[u][v];
                        if (nowChessId == targetChessId) {
                            continue;
                        }

                        // 目标棋子比当前棋子大时，不能移动（本方棋子为炸弹除外）TODO（存疑）
                        if (!ChessStrengthCompare.isStrongerOrEqualThan(nowChessId, targetChessId)
                                && nowChessType != ChessType.BOOM_CHESS) {
                            continue;
                        }

                        // 判断能够从(i, j)移动棋子到(u, v)，若能移动，将移动路径存入moves
                        ListUDG.canMoveTo(board, i, j, u, v, moves);
                    }
                }
            }
        }
        return moves;
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
        for (int i = 0; i < BoardInfo.LENGTH; i++) {
            for (int j = 0; j < BoardInfo.HEIGHT; j++) {
                System.out.println(board[i][j] + " ");
            }
            System.out.println("");
        }
    }
}
