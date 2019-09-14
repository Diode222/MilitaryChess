package junqi;

import chessPostionInfo.Position;
import global.BoardInfo;
import global.ChessType;
import global.PositionType;
import mcts.Board;
import mcts.CallLocation;
import mcts.Move;
import utils.ChessStrengthCompare;
import utils.ListUDG;

import java.util.ArrayList;

public class JunQiBoard implements Board {

    final static public int FIRST_HAND = 0;
    final static public int BACK_HAND = 1;

    int [][]board;
    int currentPlayer;
    int winner;
    boolean draw;
    boolean gameOver;
    int turnsHasNoEatOtherSide;

    // 双方棋子剩余可移动数目，用于判断局势和胜负
    int firstHandRemainMovableChessNum;
    int backHandRemainMovableChessNum;

    public JunQiBoard() {
        board = new int[BoardInfo.LENGTH][BoardInfo.HEIGHT];
        currentPlayer = 1;
        turnsHasNoEatOtherSide = 0;
        firstHandRemainMovableChessNum = 25;
        backHandRemainMovableChessNum = 25;
    }

    public void setBoard(int[][] board) {
        this.board = board;
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
        JunQiMove move = (JunQiMove) m;
        Position startPosition = move.points.get(0);
        Position endPosition = move.points.get(move.points.size() - 1);

        // move起始点和目标点的相关信息
        int startChessId = board[startPosition.getX()][startPosition.getY()];
        int startChessType = ChessType.getType(startChessId);
        int startPositionType = PositionType.getType(startPosition.getX(), startPosition.getY());
        int endChessId = board[endPosition.getX()][endPosition.getY()];
        int endChessType = ChessType.getType(endChessId);
        int endPositionType = PositionType.getType(endPosition.getX(), endPosition.getY());

        /*
            更新棋盘
        */
        // 当前位置棋子走出去，当前位置应该置为0表示当前位置没有棋子
        board[startPosition.getX()][startPosition.getY()] = 0;
        // 目标位置分情况讨论
        if (endChessType == ChessType.BOOM_CHESS) {
            // 1.目标位置是炸弹，则直接置0表示位置为空
            board[endPosition.getX()][endPosition.getY()] = 0;
            // 棋子与炸弹同归于尽，两方可移动棋子各减1（一定可移动，炸弹可移动，己方棋子能走也可移动）
            firstHandRemainMovableChessNum--;
            backHandRemainMovableChessNum--;
        } else if (endChessType == ChessType.MINE_CHESS) {
            // 2.目标位置是地雷，判断当前位置是否是工兵
            if (startChessType == ChessType.SOLDIER_CHESS) {
                // 当前位置是工兵可以排地雷，则目标位置更新为当前位置的工兵
                board[endPosition.getX()][endPosition.getY()] = startChessId;
                // 对方地雷不可移动，所以可移动棋子数不减1
            } else {
                // 当前位置不是工兵，则与目标位置地雷同归于尽
                board[endPosition.getX()][endPosition.getY()] = 0;
                // 棋子与地雷同归于尽，己方可移动棋子数减1
                if (currentPlayer == 0) {
                    firstHandRemainMovableChessNum--;
                } else {
                    backHandRemainMovableChessNum--;
                }
            }
        } else if (endChessType == ChessType.FLAG_CHESS) {
            // 3.目标位置是军旗，除炸弹以外的棋子都可以吃（炸弹会同归于尽）
            if (startChessType == ChessType.BOOM_CHESS) {
                // 当前位置是炸弹炸掉对方军旗，更新己方可移动棋子数目（用于训练）
                board[endPosition.getX()][endPosition.getY()] = 0;
                if (currentPlayer == 0) {
                    firstHandRemainMovableChessNum--;
                } else {
                    backHandRemainMovableChessNum--;
                }
            } else {
                // 当前不是炸弹，则可以吃掉军旗
                board[endPosition.getX()][endPosition.getY()] = startChessId;
            }
        } else {
            // 4.其它类型（包括司令~排长和工兵）直接比较大小即可，
            // 但是若当前位置棋子是炸弹，会与目标位置棋子同归于尽，则直接将起始和终止点都置为0表示空
            if (startChessType == ChessType.BOOM_CHESS) {
                // 当前位置是炸弹，会炸掉对方，双方可移动棋子数各减1
                board[endPosition.getX()][endPosition.getY()] = 0;
                firstHandRemainMovableChessNum--;
                backHandRemainMovableChessNum--;
            } else {
                // 当前位置不是炸弹，是司令~排长或士兵，而目标位置也是司令~排长或士兵，则直接比较大小即可
                if (ChessStrengthCompare.getChessStrength(startChessId)
                        == ChessStrengthCompare.getChessStrength(endChessId)) {
                    // 当前位置和目标位置一样大，则同归于尽
                    board[endPosition.getX()][endPosition.getY()] = 0;
                    // 同归于尽，两方可移动棋子各减1
                    firstHandRemainMovableChessNum--;
                    backHandRemainMovableChessNum--;
                } else if (ChessStrengthCompare.isStrongerOrEqualThan(startChessId, endChessId)) {
                    // 当前位置大于目标位置可以吃掉，则更新目标位置的棋子为当前位置棋子
                    board[endPosition.getX()][endPosition.getY()] = startChessId;
                    // 对方棋子被吃，对方可移动棋子数减1
                    if (currentPlayer == 0) {
                        backHandRemainMovableChessNum--;
                    } else {
                        firstHandRemainMovableChessNum--;
                    }
                }
                // 当前位置小于目标位置，现在的逻辑里面并不会这么走，可能需要优化（TODO 待考虑）
            }
        }

        /*
            判断游戏是否结束
         */
        // TODO
    }

    @Override
    public ArrayList<Move> getMoves(CallLocation location) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i < BoardInfo.LENGTH; i++) {
            for (int j = 0; j < BoardInfo.HEIGHT; j++) {
                int nowChessId = board[i][j];
                // 当前位置没有棋子不能移动，当前位置棋子是对方的，不能移动（0代表先手方，1代表后手方）
                if(nowChessId == 0
                        || currentPlayer == 0 && nowChessId >= 26 || currentPlayer == 1 && nowChessId <= 25) {
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
                        // 目标位置棋子是本方的，不能移动，也包含了不能从自己移动到自己的情况
                        if (currentPlayer == 0 && targetChessId <= 25 && targetChessId != 0
                                || currentPlayer == 1 && targetChessId >= 26) {
                            continue;
                        }

                        // 目标棋子比当前棋子大时，不能移动（本方棋子为炸弹除外）TODO（存疑,需要考虑是否合理）
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
