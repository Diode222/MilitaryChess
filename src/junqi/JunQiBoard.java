package junqi;

import chessPostionInfo.Position;
import global.BoardInfo;
import global.ChessType;
import global.ChessValue;
import global.PositionType;
import main.Board;
import main.CallLocation;
import main.Move;
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

    @Override
    public int getTurnsHasNEaten() {
        return turnsCountChessHasNoEat;
    }

    // 用于判断平局的信息，表示已有多少轮没有棋子阵亡，初始为0
    int turnsCountChessHasNoEat;

    // 双方军旗是否已经被夺掉，用来判断胜负
    boolean firstHandFlagBeTaken;
    boolean backtHandFlagBeTaken;

    // 双方棋子剩余可移动数目，用于判断局势和胜负
    int firstHandRemainMovableChessNum;
    int backHandRemainMovableChessNum;

    // 双方目前得分，每吃掉一个棋子可以得到对应的分数
    int firstHandScore;
    int backHandScore;

    public JunQiBoard() {
        board = new int[BoardInfo.LENGTH][BoardInfo.HEIGHT];
      
        turnsCountChessHasNoEat = 0;
        firstHandFlagBeTaken = false;
        backtHandFlagBeTaken = false;
      
        /*
         TODO 正常情况下，游戏伊始双方各有20个可移动棋子（非正常情况下，若把地雷放到大本营中，
          则有21个，需要根据网络得到的棋盘返回值判断，但我自己训练棋盘就只考虑20个）
        */
        firstHandRemainMovableChessNum = 20; // 初始有20个可移动棋子
        backHandRemainMovableChessNum = 20;

        firstHandScore = 0;
        backHandScore = 0;
    }

    public void initBoard(int[][] board) {
        this.board = board;
    }

    @Override
    public Board duplicate() {
        JunQiBoard newBoard = new JunQiBoard();
        newBoard.winner = winner;
        newBoard.currentPlayer = currentPlayer;
        newBoard.draw = draw;
        newBoard.gameOver = gameOver;
        newBoard.turnsCountChessHasNoEat = turnsCountChessHasNoEat;
        newBoard.firstHandFlagBeTaken = firstHandFlagBeTaken;
        newBoard.backtHandFlagBeTaken = backtHandFlagBeTaken;
        newBoard.firstHandRemainMovableChessNum = firstHandRemainMovableChessNum;
        newBoard.backHandRemainMovableChessNum = backHandRemainMovableChessNum;
        newBoard.firstHandScore = firstHandScore;
        newBoard.backHandScore = backHandScore;
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
        // 当获取到的getMoves.size() == 0时，这里的Move m会被异常捕获置为null，由于无棋可下，因此直接判当前选手输
        if (m == null) {
            gameOver = true;
            if (currentPlayer == 0) {
                winner = 1;
                currentPlayer = 1;
            } else {
                winner = 0;
                currentPlayer = 0;
            }
            return;
        }

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
            } else if (startChessType == ChessType.BOOM_CHESS) {
                // 当前位置是炸弹，可以炸掉对方地雷
                board[endPosition.getX()][endPosition.getY()] = 0;
                // 己方炸弹与对方地雷同归于尽，己方可移动棋子减1
                if (currentPlayer == 0) {
                    firstHandRemainMovableChessNum--;
                } else {
                    backHandRemainMovableChessNum--;
                }
            }
            // 由于规则表示除了工兵和炸弹都不能排掉地雷，所以会过滤掉所有非工兵炸弹撞地雷的move
        } else if (endChessType == ChessType.FLAG_CHESS) {
            // 3.目标位置是军旗，除炸弹以外的棋子都可以吃（炸弹会同归于尽）
            if (startChessType == ChessType.BOOM_CHESS) {
                // 当前位置是炸弹可炸掉对方军旗，更新己方可移动棋子数目（用于训练）
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
            // 由于军旗被夺掉，需要更新军旗夺掉的bool值用于判断胜负
            if (currentPlayer == 0) {
                backtHandFlagBeTaken = true;
            } else {
                firstHandFlagBeTaken = true;
            }
        } else if (endChessType == ChessType.NO_CHESS) {
            // 4.若当前目标位置没有棋子，则直接更新目标位置的棋子id
            board[endPosition.getX()][endPosition.getY()] = startChessId;
        } else {
            // 5.其它类型（包括司令~排长和工兵）直接比较大小即可，
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
            更新分数
            由于起始棋子必定可以走到目标位置，则当前选手可以得到目标位置的棋子分数，
            现在更新了move后的棋盘，如果目标位置有棋子（移动后的起始棋子），表示
            目标位置棋子被吃掉了，不得分，如果目标位置没有棋子，则表示目标位置棋子和
            当前棋子同归于尽了，可以得分，加上起始棋子的分数
            FIXME 这样写的坏处是后续不能扩展去主动撞死的逻辑
        */
        if (currentPlayer == 0) {
            firstHandScore += ChessValue.getValue(endChessId);
            // 目标位置没有棋子，说明同归于尽了，得分
            if (board[endPosition.getX()][endPosition.getY()] == 0) {
                backHandScore += ChessValue.getValue(startChessId);
            }
        } else {
            backHandScore += ChessValue.getValue(endChessId);
            if (board[endPosition.getX()][endPosition.getY()] == 0) {
                firstHandScore += ChessValue.getValue(startChessId);
            }
        }

        /*
            平局信息更新
            根据目标位置是否有棋子，判断当前这一步棋是否有棋子被吃掉，
            如果有棋子被吃掉，则turnsCountHasNoEatOtherSide置为0重新计算
         */
        if (endChessId != 0) {
            turnsCountChessHasNoEat = 0;
        } else {
            turnsCountChessHasNoEat++;
        }

        /*
            判断游戏是否结束，需判断给出胜负平局状态，以及赢家
        */
        // 夺掉军旗，由于当前轮次选手只能夺掉对方军旗，因此若有军旗被夺掉，则本轮选手获胜
        // TODO（这里需要问一下军旗和可移动数哪个获胜优先级更高，现在的逻辑是军旗优先级更高）
        if (firstHandFlagBeTaken || backtHandFlagBeTaken) {
            gameOver = true;
            winner = currentPlayer;
        }

        // 连续20步双方无棋子阵亡，则比较动态分来分胜负平
        if (turnsCountChessHasNoEat >= 20) {
            gameOver = true;
            if (firstHandScore > backHandScore) {
                winner = 0;
            } else if (backHandScore > firstHandScore) {
                winner = 1;
            } else {
                draw = true;
            }
        }

        if (firstHandRemainMovableChessNum == 0 && backHandRemainMovableChessNum == 0) {
            // 两方都没有可移动棋子，判断动态分
            gameOver = true;
            if (firstHandScore > backHandScore) {
                winner = 0;
            } else if (backHandScore > firstHandScore) {
                winner = 1;
            } else {
                draw = true;
            }
        } else if (firstHandRemainMovableChessNum == 0)  {
            // 先手方可移动棋子数为0，则后手方胜
            gameOver = true;
            winner = 1;
        } else if (backHandRemainMovableChessNum == 0) {
            // 后手方可移动棋子数为0， 则先手方胜
            gameOver = true;
            winner = 0;
        }

        if (currentPlayer == 0) {
            currentPlayer = 1;
        } else {
            currentPlayer = 0;
        }
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
                        // 目标棋子是本方的，不能移动，也包含了不能从自己移动到自己的情况
                        if (currentPlayer == 0 && targetChessId <= 25 && targetChessId != 0
                                || currentPlayer == 1 && targetChessId >= 26) {
                            continue;
                        }

                        // 目标棋子比当前棋子大时，不能移动（本方棋子为炸弹除外）TODO（存疑,需要考虑是否合理）
                        if (!ChessStrengthCompare.isStrongerOrEqualThan(nowChessId, targetChessId)
                                && nowChessType != ChessType.BOOM_CHESS) {
                            continue;
                        }

                        // 目标棋子是地雷，本方棋子不是工兵或炸弹时，不可移动（这种情况下本方棋子死亡，地雷不会被排掉）
                        int targetChessType = ChessType.getType(targetChessId);
                        if (targetChessType == ChessType.MINE_CHESS
                                && !(nowChessType == ChessType.SOLDIER_CHESS || nowChessType == ChessType.BOOM_CHESS)) {
                            continue;
                        }

                        // 本方只剩一个可移动棋子时，只吃普通棋子，工兵和军旗，不要去碰炸弹和同样大的同归于尽
                        // 目标位置是空位置时，如果走上去会被吃，则不要走上去
                        if (currentPlayer == 0 && firstHandRemainMovableChessNum == 1
                            || currentPlayer == 1 && backHandRemainMovableChessNum == 1
                            || targetChessId == 0) {
                            // 跳过炸弹
                            if (targetChessType == ChessType.BOOM_CHESS) {
                                continue;
                            }

                            // 跳过同样大的
                            if (ChessStrengthCompare.getChessStrength(nowChessId)
                                    == ChessStrengthCompare.getChessStrength(targetChessId)) {
                                continue;
                            }

                            /*
                                跳过一些极端情况，比如吃了目标点会被吃，则跳过，
                                需要判断棋盘所有对方棋子是否可以吃掉当前棋子，如果可以吃掉，
                                再判断能否能到达目标位置，只有确认安全才去吃掉对方棋子或走到空位
                            */
                            // duplicate一个新棋盘用来表示吃后下一步的状态（部分需要的状态）
                            if (targetChessType != ChessType.FLAG_CHESS) {
                                // 若目标点为军旗，则不跳过
                                JunQiBoard tmpBoard = (JunQiBoard) this.duplicate();
                                if (currentPlayer == 0) {
                                    tmpBoard.currentPlayer = 1;
                                    if (firstHandRemainMovableChessNum != 1) {
                                        // 说明走的是targetChessId == 0的逻辑
                                        tmpBoard.backHandRemainMovableChessNum = backHandRemainMovableChessNum;
                                    } else {
                                        // 说明走的是firstHandRemainMovableChessNum == 1的逻辑
                                        tmpBoard.backHandRemainMovableChessNum = 20; // 剩一个棋子时需要设置需要设置，不然陷入死循环
                                    }
                                } else {
                                    tmpBoard.currentPlayer = 0;
                                    if (backHandRemainMovableChessNum != 1) {
                                        // 说明走的是targetChessId == 0的逻辑
                                        tmpBoard.firstHandRemainMovableChessNum = firstHandRemainMovableChessNum;
                                    } else {
                                        // 说明走的是backHandRemainMovableChessNum == 1的逻辑
                                        tmpBoard.firstHandRemainMovableChessNum = 20;
                                    }
                                }
                                tmpBoard.board[u][v] = nowChessId;
                                tmpBoard.board[i][j] = 0;
                                ArrayList<Move> tmpMoves = new ArrayList<>();
                                // 不用担心这段逻辑影响效率，当前只剩一个棋子，计算资源是过剩的(FIXME)
                                for (int row = 0; row < BoardInfo.LENGTH; row++) {
                                    for (int col = 0; col < BoardInfo.HEIGHT; col++) {
                                        int tmpNowChessId = tmpBoard.board[row][col];
                                        // 当前位置没有棋子不能移动，当前位置棋子是对方的，不能移动（0代表先手方，1代表后手方）
                                        if(tmpNowChessId == 0
                                           || tmpBoard.currentPlayer == 0 && tmpNowChessId >= 26
                                           || tmpBoard.currentPlayer == 1 && tmpNowChessId <= 25) {
                                            continue;
                                        }

                                        int tmpNowChessType = ChessType.getType(tmpNowChessId);
                                        int tmpNowPositionType = PositionType.getType(row, col);

                                        // 地雷和军旗不能动，大本营位置的棋子不能动
                                        if (tmpNowChessType == ChessType.MINE_CHESS
                                            || tmpNowChessType == ChessType.FLAG_CHESS
                                            || tmpNowPositionType == PositionType.FLAG_POSITION) {
                                            continue;
                                        }

                                        // 备选的棋子比当前棋子小，且当前棋子不是炸弹时，跳过
                                        if (!ChessStrengthCompare.isStrongerOrEqualThan(tmpNowChessId, nowChessId)
                                                && nowChessType != ChessType.BOOM_CHESS) {
                                            continue;
                                        }

                                        ListUDG.canMoveTo(tmpBoard.board, row, col, u, v, tmpMoves);
                                    }
                                }

                                if (tmpMoves.size() != 0) {
                                    continue;
                                }
                            }
                        }

                        int targetPositionType = PositionType.getType(u, v);
                        // 目标位置是大本营且里面棋子不是军旗，则不进去
                        if (targetPositionType == PositionType.FLAG_POSITION
                                && targetChessType != ChessType.FLAG_CHESS) {
                            continue;
                        }

                        // 若目标位置是一个行营且已有棋子，则不用比较大小，直接判断不能进去
                        if (targetPositionType == PositionType.CAMP_POSITION && targetChessId != 0) {
                            continue;
                        }

                        // 判断能够从(i, j)移动棋子到(u, v)，若能移动，将移动路径存入moves
                        ListUDG.canMoveTo(board, i, j, u, v, moves);
                    }
                }
            }
        }

        // 若当前选手没有可以移动的选择，即moves为空，则判当前选手输这里逻辑和makeMove开头判null逻辑重复，
        // 但两者目的不同，这里是为了博弈树迭代过程中的判空，makeMove处是为了最后返回move结果的判空
        if (moves.size() == 0) {
            gameOver = true;
            winner = (currentPlayer == 0) ? 1 : 0;
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
            // 赢家加(100+动态分)，输家加(动态分)
            if (winner == 0) {
                score[0] = 100 + firstHandScore;
                score[1] = backHandScore;
            } else {
                score[0] = firstHandScore;
                score[1] = 100 + backHandScore;
            }
        } else {
            // 平局各加(50+动态分)
            score[0] = 50 + firstHandScore;
            score[1] = 50 + backHandScore;
        }
        return score;
    }

    @Override
    public double[] getMoveWeights() {
        return null;
    }

    @Override
    // 用来做数据的输出
//    public void bPrint() {
//        for (int i = 0; i < BoardInfo.LENGTH; i++) {
//            for (int j = 0; j < BoardInfo.HEIGHT; j++) {
//                System.out.print(board[i][j] + " ");
//            }
//            System.out.println("");
//        }
////        System.out.println(">>>>>>>>>>>>>>>>>>>>");
//    }

    // 用来肉眼看的输出
    public void bPrint() {
        for (int i = 0; i < BoardInfo.LENGTH; i++) {
            for (int j = 0; j < BoardInfo.HEIGHT; j++) {
                String tmp = "";
                if (board[i][j] < 10) {
                    tmp = tmp + " " + board[i][j];
                } else {
                    tmp += board[i][j];
                }
                System.out.print(tmp + "    ");
            }
            System.out.println("");
            System.out.println("");
        }
//        System.out.println(">>>>>>>>>>>>>>>>>>>>");
    }
}
