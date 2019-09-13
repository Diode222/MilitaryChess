package junqi;

import global.BoardInfo;
import global.ChessType;
import global.PositionType;
import mcts.Board;
import mcts.CallLocation;
import mcts.Move;
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
    public List<Move> getMoves(CallLocation location) {
        List<Move> moves = new ArrayList<>();
        for (int i = 0; i < BoardInfo.LENGTH; i++) {
            for (int j = 0; j < BoardInfo.HEIGHT; j++) {
                int chessId = board[i][j];
                if(chessId == 0) {
                    continue;
                }

                int chessType = ChessType.getType(chessId);
                int positionType = PositionType.getType(i, j);

                if (chessType == ChessType.MINE_CHESS || chessType == ChessType.FLAG_CHESS
                        || positionType == PositionType.FLAG_POSITION) {
                    // 地雷和军旗不能动，大本营位置的棋子不能动
                    continue;
                } else if (chessType == ChessType.NORMAL_CHESS || chessType == ChessType.BOOM_CHESS) {
                    // 司令~排长，炸弹不能拐弯
                    PathFinder.getAllPathOfAChessNormalOrBoomChess(getCurrentPlayer(), board, i, j, chessId,
                                                                moves, chessType, positionType);
                } else {
                    // 工兵可以拐弯

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
