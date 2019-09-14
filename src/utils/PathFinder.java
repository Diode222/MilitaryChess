package utils;

import chessPostionInfo.Position;
import global.BoardInfo;
import global.ChessType;
import global.PositionType;
import junqi.JunQiBoard;
import junqi.JunQiMove;
import main.Move;

import java.util.ArrayList;
import java.util.List;

public class PathFinder {

    // 司令~排长，炸弹的路径查找
    static public void getAllPathOfAChessNormalOrBoomChess(int player, int[][] board, int x, int y, int chessId,
                                                           List<Move> moves, int chessType, int positionType) {

        if (player == JunQiBoard.FIRST_HAND && chessId >= 26 || player == JunQiBoard.BACK_HAND && chessId <= 25) {
            return;
        }

        int backupPositionChessId; // 备选位置的chessId
        if (player == JunQiBoard.FIRST_HAND) {
            if (positionType == PositionType.NORMAL_POSITION || positionType == PositionType.CAMP_POSITION) {
                // 在普通位置或行营，只能走一步
                if (x - 1 >= 0 && y - 1 >= 0) {
                    backupPositionChessId = board[x - 1][y - 1];
                    if (backupPositionChessId >= 26) { // 备选位置是对面的棋子
                        if (chessType == ChessType.BOOM_CHESS) {
                            // 如果对面是一个炸弹，所有可动棋子都可以走上去
                            List<Position> positions = new ArrayList<>(); // 找到的一条路径
                            positions.add(new Position().setX(x).setY(y).build());
                            positions.add(new Position().setX(x - 1).setY(y - 1).build());
                            JunQiMove move = new JunQiMove(positions);
                            moves.add(move);
                        } else if (ChessStrengthCompare.isStrongerOrEqualThan(chessId, backupPositionChessId)) {
                            // 普通棋子就比大小，大于等于就可以走上去
                            List<Position> positions = new ArrayList<>(); // 找到的一条路径
                            positions.add(new Position().setX(x).setY(y).build());
                            positions.add(new Position().setX(x - 1).setY(y - 1).build());
                            JunQiMove move = new JunQiMove(positions);
                            moves.add(move);
                        }
                    } else if (backupPositionChessId == 0) { // 备选位置没有棋子
                        List<Position> positions = new ArrayList<>(); // 找到的一条路径
                        positions.add(new Position().setX(x).setY(y).build());
                        positions.add(new Position().setX(x - 1).setY(y - 1).build());
                        JunQiMove move = new JunQiMove(positions);
                        moves.add(move);
                    }
                    // 备选位置是自己棋子，就不能走上去
                }
                if (x + 1 < BoardInfo.LENGTH && y + 1 < BoardInfo.HEIGHT) {
                    backupPositionChessId = board[x + 1][y + 1];
                    if (backupPositionChessId >= 26) {
                        if (chessType == ChessType.BOOM_CHESS) {
                            // 如果对面是一个炸弹，所有可动棋子都可以走上去
                            List<Position> positions = new ArrayList<>(); // 找到的一条路径
                            positions.add(new Position().setX(x).setY(y).build());
                            positions.add(new Position().setX(x + 1).setY(y + 1).build());
                            JunQiMove move = new JunQiMove(positions);
                            moves.add(move);
                        } else if (ChessStrengthCompare.isStrongerOrEqualThan(chessId, backupPositionChessId)) {
                            // 如果对面是普通棋子就比大小，大于等于就可以走上去
                            List<Position> positions = new ArrayList<>(); // 找到的一条路径
                            positions.add(new Position().setX(x).setY(y).build());
                            positions.add(new Position().setX(x + 1).setY(y + 1).build());
                            JunQiMove move = new JunQiMove(positions);
                            moves.add(move);
                        }
                    } else if (backupPositionChessId == 0) {
                        List<Position> positions = new ArrayList<>(); // 找到的一条路径
                        positions.add(new Position().setX(x).setY(y).build());
                        positions.add(new Position().setX(x + 1).setY(y + 1).build());
                        JunQiMove move = new JunQiMove(positions);
                        moves.add(move);
                    }
                }
                if (x - 1 >= 0 && y + 1 < BoardInfo.HEIGHT) {
                    backupPositionChessId = board[x - 1][y + 1];
                    if (backupPositionChessId >= 26) { // 备选位置是对面的棋子
                        if (chessType == ChessType.BOOM_CHESS) {
                            // 如果对面是一个炸弹，所有可动棋子都可以走上去
                            List<Position> positions = new ArrayList<>(); // 找到的一条路径
                            positions.add(new Position().setX(x).setY(y).build());
                            positions.add(new Position().setX(x - 1).setY(y + 1).build());
                            JunQiMove move = new JunQiMove(positions);
                            moves.add(move);
                        } else if (ChessStrengthCompare.isStrongerOrEqualThan(chessId, backupPositionChessId)) {
                            // 普通棋子就比大小，大于等于就可以走上去
                            List<Position> positions = new ArrayList<>(); // 找到的一条路径
                            positions.add(new Position().setX(x).setY(y).build());
                            positions.add(new Position().setX(x - 1).setY(y + 1).build());
                            JunQiMove move = new JunQiMove(positions);
                            moves.add(move);
                        }
                    } else if (backupPositionChessId == 0) { // 备选位置没有棋子
                        List<Position> positions = new ArrayList<>(); // 找到的一条路径
                        positions.add(new Position().setX(x).setY(y).build());
                        positions.add(new Position().setX(x - 1).setY(y + 1).build());
                        JunQiMove move = new JunQiMove(positions);
                        moves.add(move);
                    }
                    // 备选位置是自己棋子，就不能走上去
                }
                if (x + 1 < BoardInfo.LENGTH && y - 1 >= 0) {
                    backupPositionChessId = board[x + 1][y - 1];
                    if (backupPositionChessId >= 26) { // 备选位置是对面的棋子
                        if (chessType == ChessType.BOOM_CHESS) {
                            // 如果对面是一个炸弹，所有可动棋子都可以走上去
                            List<Position> positions = new ArrayList<>(); // 找到的一条路径
                            positions.add(new Position().setX(x).setY(y).build());
                            positions.add(new Position().setX(x + 1).setY(y - 1).build());
                            JunQiMove move = new JunQiMove(positions);
                            moves.add(move);
                        } else if (ChessStrengthCompare.isStrongerOrEqualThan(chessId, backupPositionChessId)) {
                            // 普通棋子就比大小，大于等于就可以走上去
                            List<Position> positions = new ArrayList<>(); // 找到的一条路径
                            positions.add(new Position().setX(x).setY(y).build());
                            positions.add(new Position().setX(x + 1).setY(y - 1).build());
                            JunQiMove move = new JunQiMove(positions);
                            moves.add(move);
                        }
                    } else if (backupPositionChessId == 0) { // 备选位置没有棋子
                        List<Position> positions = new ArrayList<>(); // 找到的一条路径
                        positions.add(new Position().setX(x).setY(y).build());
                        positions.add(new Position().setX(x + 1).setY(y - 1).build());
                        JunQiMove move = new JunQiMove(positions);
                        moves.add(move);
                    }
                    // 备选位置是自己棋子，就不能走上去
                }
                if (y - 1 >= 0) {
                    backupPositionChessId = board[x][y - 1];
                    if (backupPositionChessId >= 26) { // 备选位置是对面的棋子
                        if (chessType == ChessType.BOOM_CHESS) {
                            // 如果对面是一个炸弹，所有可动棋子都可以走上去
                            List<Position> positions = new ArrayList<>(); // 找到的一条路径
                            positions.add(new Position().setX(x).setY(y).build());
                            positions.add(new Position().setX(x).setY(y - 1).build());
                            JunQiMove move = new JunQiMove(positions);
                            moves.add(move);
                        } else if (ChessStrengthCompare.isStrongerOrEqualThan(chessId, backupPositionChessId)) {
                            // 普通棋子就比大小，大于等于就可以走上去
                            List<Position> positions = new ArrayList<>(); // 找到的一条路径
                            positions.add(new Position().setX(x).setY(y).build());
                            positions.add(new Position().setX(x).setY(y - 1).build());
                            JunQiMove move = new JunQiMove(positions);
                            moves.add(move);
                        }
                    } else if (backupPositionChessId == 0) { // 备选位置没有棋子
                        List<Position> positions = new ArrayList<>(); // 找到的一条路径
                        positions.add(new Position().setX(x).setY(y).build());
                        positions.add(new Position().setX(x).setY(y - 1).build());
                        JunQiMove move = new JunQiMove(positions);
                        moves.add(move);
                    }
                    // 备选位置是自己棋子，就不能走上去
                }
                if (y + 1 < BoardInfo.HEIGHT) {
                    backupPositionChessId = board[x][y + 1];
                    if (backupPositionChessId >= 26) { // 备选位置是对面的棋子
                        if (chessType == ChessType.BOOM_CHESS) {
                            // 如果对面是一个炸弹，所有可动棋子都可以走上去
                            List<Position> positions = new ArrayList<>(); // 找到的一条路径
                            positions.add(new Position().setX(x).setY(y).build());
                            positions.add(new Position().setX(x).setY(y + 1).build());
                            JunQiMove move = new JunQiMove(positions);
                            moves.add(move);
                        } else if (ChessStrengthCompare.isStrongerOrEqualThan(chessId, backupPositionChessId)) {
                            // 普通棋子就比大小，大于等于就可以走上去
                            List<Position> positions = new ArrayList<>(); // 找到的一条路径
                            positions.add(new Position().setX(x).setY(y).build());
                            positions.add(new Position().setX(x).setY(y + 1).build());
                            JunQiMove move = new JunQiMove(positions);
                            moves.add(move);
                        }
                    } else if (backupPositionChessId == 0) { // 备选位置没有棋子
                        List<Position> positions = new ArrayList<>(); // 找到的一条路径
                        positions.add(new Position().setX(x).setY(y).build());
                        positions.add(new Position().setX(x).setY(y + 1).build());
                        JunQiMove move = new JunQiMove(positions);
                        moves.add(move);
                    }
                    // 备选位置是自己棋子，就不能走上去
                }
                if (x - 1 >= 0) {
                    backupPositionChessId = board[x - 1][y];
                    if (backupPositionChessId >= 26) { // 备选位置是对面的棋子
                        if (chessType == ChessType.BOOM_CHESS) {
                            // 如果对面是一个炸弹，所有可动棋子都可以走上去
                            List<Position> positions = new ArrayList<>(); // 找到的一条路径
                            positions.add(new Position().setX(x).setY(y).build());
                            positions.add(new Position().setX(x - 1).setY(y).build());
                            JunQiMove move = new JunQiMove(positions);
                            moves.add(move);
                        } else if (ChessStrengthCompare.isStrongerOrEqualThan(chessId, backupPositionChessId)) {
                            // 普通棋子就比大小，大于等于就可以走上去
                            List<Position> positions = new ArrayList<>(); // 找到的一条路径
                            positions.add(new Position().setX(x).setY(y).build());
                            positions.add(new Position().setX(x - 1).setY(y).build());
                            JunQiMove move = new JunQiMove(positions);
                            moves.add(move);
                        }
                    } else if (backupPositionChessId == 0) { // 备选位置没有棋子
                        List<Position> positions = new ArrayList<>(); // 找到的一条路径
                        positions.add(new Position().setX(x).setY(y).build());
                        positions.add(new Position().setX(x - 1).setY(y).build());
                        JunQiMove move = new JunQiMove(positions);
                        moves.add(move);
                    }
                    // 备选位置是自己棋子，就不能走上去
                }
                if (x + 1 < BoardInfo.LENGTH) {
                    backupPositionChessId = board[x + 1][y];
                    if (backupPositionChessId >= 26) { // 备选位置是对面的棋子
                        if (chessType == ChessType.BOOM_CHESS) {
                            // 如果对面是一个炸弹，所有可动棋子都可以走上去
                            List<Position> positions = new ArrayList<>(); // 找到的一条路径
                            positions.add(new Position().setX(x).setY(y).build());
                            positions.add(new Position().setX(x + 1).setY(y).build());
                            JunQiMove move = new JunQiMove(positions);
                            moves.add(move);
                        } else if (ChessStrengthCompare.isStrongerOrEqualThan(chessId, backupPositionChessId)) {
                            // 普通棋子就比大小，大于等于就可以走上去
                            List<Position> positions = new ArrayList<>(); // 找到的一条路径
                            positions.add(new Position().setX(x).setY(y).build());
                            positions.add(new Position().setX(x + 1).setY(y).build());
                            JunQiMove move = new JunQiMove(positions);
                            moves.add(move);
                        }
                    } else if (backupPositionChessId == 0) { // 备选位置没有棋子
                        List<Position> positions = new ArrayList<>(); // 找到的一条路径
                        positions.add(new Position().setX(x).setY(y).build());
                        positions.add(new Position().setX(x + 1).setY(y).build());
                        JunQiMove move = new JunQiMove(positions);
                        moves.add(move);
                    }
                    // 备选位置是自己棋子，就不能走上去
                }
            }
        } else {
            // 在铁道上，司令~排长，炸弹可以横竖走任意步，只要终止的地方仍在铁道上

        }
    }
}
