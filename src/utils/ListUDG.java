package utils;

import chessPostionInfo.Position;
import global.BoardInfo;
import global.ChessType;
import global.PositionType;
import junqi.JunQiMove;
import mcts.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListUDG {

    // 棋盘邻接表
    static private ListUDG udg;

    static public ListUDG getUdg() {
        return udg;
    }

    // 邻接表中链表的数据结构
    static private class ENode {
        int ivex; //
        ENode nextEdge;
    }

    // 邻接表中顶点的数据结构
    static private class VNode {
        int x;
        int y;
        int positionType; // 该顶点对应位置的类型（普通、行营、铁路、大本营）
        ENode edgeHead; // 邻接链表头
    }

    // 顶点对应的数组
    static private VNode[] mVexs = new VNode[60];

    // 顶点数组各个点位置快速查找
    static private HashMap<String, Integer> mVexsMap;

    static {
        for (int i = 0; i < BoardInfo.LENGTH; i++) {
            for (int j = 0; j < BoardInfo.HEIGHT; j++) {
                int index = i * BoardInfo.LENGTH + j;
                mVexs[index] = new VNode();
                mVexs[index].x = i;
                mVexs[index].y = j;
                mVexs[index].positionType = PositionType.getType(i, j);
                mVexs[index].edgeHead = null;
                mVexsMap.put(i + "," + j, index);
            }
        }
    }

    static {
        String[][] edges = new String[][] {
                {"0,0", "0,1"},
                {"0,0", "1,0"},

                {"1,0", "0,0"},
                {"1,0", "1,1"},
                {"1,0", "2,0"},

                {"2,0", "1,0"},
                {"2,0", "2,1"},
                {"2,0", "3,0"},

                {"3,0", "2,0"},
                {"3,0", "3,1"},
                {"3,0", "4,0"},

                {"4,0", "3,0"},
                {"4,0", "4,1"},

                {"0,1", "0,2"},
                {"0,1", "1,2"},
                {"0,1", "1,1"},
                {"0,1", "0,0"},

                {"1,1", "0,1"},
                {"1,1", "1,2"},
                {"1,1", "2,1"},
                {"1,1", "1,0"},

                {"2,1", "1,1"},
                {"2,1", "1,2"},
                {"2,1", "2,2"},
                {"2,1", "3,2"},
                {"2,1", "3,1"},
                {"2,1", "2,0"},

                {"3,1", "2,1"},
                {"3,1", "3,2"},
                {"3,1", "4,1"},
                {"3,1", "3,0"},

                {"4,1", "3,1"},
                {"4,1", "3,2"},
                {"4,1", "4,2"},
                {"4,1", "4,0"},

                {"0,2", "0,3"},
                {"0,2", "1,2"},
                {"0,2", "0,1"},

                {"1,2", "0,2"},
                {"1,2", "0,3"},
                {"1,2", "1,3"},
                {"1,2", "2,3"},
                {"1,2", "2,2"},
                {"1,2", "2,1"},
                {"1,2", "1,1"},
                {"1,2", "0,1"},

                {"2,2", "1,2"},
                {"2,2", "2,3"},
                {"2,2", "3,2"},
                {"2,2", "2,1"},

                {"3,2", "2,2"},
                {"3,2", "2,3"},
                {"3,2", "3,3"},
                {"3,2", "4,3"},
                {"3,2", "4,2"},
                {"3,2", "4,1"},
                {"3,2", "3,1"},
                {"3,2", "2,1"},

                {"4,2", "3,2"},
                {"4,2", "4,3"},
                {"4,2", "4,1"},

                {"0,3", "0,4"},
                {"0,3", "1,4"},
                {"0,3", "1,3"},
                {"0,3", "1,2"},
                {"0,3", "0,2"},

                {"1,3", "0,3"},
                {"1,3", "1,4"},
                {"1,3", "2,3"},
                {"1,3", "1,2"},

                {"2,3", "1,3"},
                {"2,3", "1,4"},
                {"2,3", "2,4"},
                {"2,3", "3,4"},
                {"2,3", "3,3"},
                {"2,3", "3,2"},
                {"2,3", "2,2"},
                {"2,3", "1,2"},

                {"3,3", "2,3"},
                {"3,3", "3,4"},
                {"3,3", "4,3"},
                {"3,3", "3,2"},

                {"4,3", "3,3"},
                {"4,3", "3,4"},
                {"4,3", "4,4"},
                {"4,3", "4,2"},
                {"4,3", "3,2"},

                {"0,4", "0,5"},
                {"0,4", "1,4"},
                {"0,4", "0,3"},

                {"1,4", "0,4"},
                {"1,4", "0,5"},
                {"1,4", "1,5"},
                {"1,4", "2,5"},
                {"1,4", "2,4"},
                {"1,4", "2,3"},
                {"1,4", "1,3"},
                {"1,4", "0,3"},

                {"2,4", "1,4"},
                {"2,4", "2,5"},
                {"2,4", "3,4"},
                {"2,4", "2,3"},

                {"3,4", "2,4"},
                {"3,4", "2,5"},
                {"3,4", "3,5"},
                {"3,4", "4,5"},
                {"3,4", "4,4"},
                {"3,4", "4,3"},
                {"3,4", "3,3"},
                {"3,4", "2,3"},

                {"4,4", "3,4"},
                {"4,4", "4,5"},
                {"4,4", "4,3"},

                {"0,5", "0,6"},
                {"0,5", "1,5"},
                {"0,5", "1,4"},
                {"0,5", "0,4"},

                {"1,5", "0,5"},
                {"1,5", "2,5"},
                {"1,5", "1,4"},

                {"2,5", "1,5"},
                {"2,5", "2,6"},
                {"2,5", "3,5"},
                {"2,5", "3,4"},
                {"2,5", "2,4"},
                {"2,5", "1,4"},

                {"3,5", "2,5"},
                {"3,5", "4,5"},
                {"3,5", "3,4"},

                {"4,5", "3,5"},
                {"4,5", "4,6"},
                {"4,5", "4,4"},
                {"4,5", "3,4"},

                {"0,6", "0,7"},
                {"0,6", "1,7"},
                {"0,6", "1,6"},
                {"0,6", "0,5"},

                {"1,6", "0,6"},
                {"1,6", "2,6"},
                {"1,6", "1,7"},

                {"2,6", "1,6"},
                {"2,6", "2,7"},
                {"2,6", "3,6"},
                {"2,6", "3,7"},
                {"2,6", "2,5"},
                {"2,6", "1,7"},

                {"3,6", "2,6"},
                {"3,6", "4,6"},
                {"3,6", "3,7"},

                {"4,6", "3,6"},
                {"4,6", "3,7"},
                {"4,6", "4,7"},
                {"4,6", "4,5"},

                {"0,7", "0,8"},
                {"0,7", "1,7"},
                {"0,7", "0,6"},

                {"1,7", "0,7"},
                {"1,7", "0,8"},
                {"1,7", "1,8"},
                {"1,7", "2,8"},
                {"1,7", "2,7"},
                {"1,7", "2,6"},
                {"1,7", "1,6"},
                {"1,7", "0,6"},

                {"2,7", "1,7"},
                {"2,7", "2,8"},
                {"2,7", "3,7"},
                {"2,7", "2,6"},

                {"3,7", "2,7"},
                {"3,7", "2,8"},
                {"3,7", "3,8"},
                {"3,7", "4,8"},
                {"3,7", "4,7"},
                {"3,7", "4,6"},
                {"3,7", "3,6"},
                {"3,7", "2,6"},

                {"4,7", "3,7"},
                {"4,7", "4,8"},
                {"4,7", "4,6"},

                {"0,8", "0,9"},
                {"0,8", "1,9"},
                {"0,8", "1,8"},
                {"0,8", "1,7"},
                {"0,8", "0,7"},

                {"1,8", "0,8"},
                {"1,8", "1,9"},
                {"1,8", "2,8"},
                {"1,8", "1,7"},

                {"2,8", "1,8"},
                {"2,8", "1,9"},
                {"2,8", "2,9"},
                {"2,8", "3,9"},
                {"2,8", "3,8"},
                {"2,8", "3,7"},
                {"2,8", "2,7"},
                {"2,8", "1,7"},

                {"3,8", "2,8"},
                {"3,8", "3,9"},
                {"3,8", "4,8"},
                {"3,8", "3,7"},

                {"4,8", "3,8"},
                {"4,8", "3,9"},
                {"4,8", "4,9"},
                {"4,8", "4,7"},
                {"4,8", "3,7"},

                {"0,9", "0,10"},
                {"0,9", "1,9"},
                {"0,9", "0,8"},

                {"1,9", "0,9"},
                {"1,9", "0,10"},
                {"1,9", "1,10"},
                {"1,9", "2,10"},
                {"1,9", "2,9"},
                {"1,9", "2,8"},
                {"1,9", "1,8"},
                {"1,9", "0,8"},

                {"2,9", "1,9"},
                {"2,9", "2,10"},
                {"2,9", "3,9"},
                {"2,9", "2,8"},

                {"3,9", "2,9"},
                {"3,9", "2,10"},
                {"3,9", "3,10"},
                {"3,9", "4,10"},
                {"3,9", "4,9"},
                {"3,9", "4,8"},
                {"3,9", "3,8"},
                {"3,9", "2,8"},

                {"4,9", "3,9"},
                {"4,9", "4,10"},
                {"4,9", "4,8"},

                {"0,10", "0,9"},
                {"0,10", "1,9"},
                {"0,10", "1,10"},
                {"0,10", "0,11"},

                {"1,10", "0,10"},
                {"1,10", "1,11"},
                {"1,10", "2,10"},
                {"1,10", "1,9"},

                {"2,10", "1,10"},
                {"2,10", "2,11"},
                {"2,10", "3,10"},
                {"2,10", "3,9"},
                {"2,10", "2,9"},
                {"2,10", "1,9"},

                {"3,10", "2,10"},
                {"3,10", "3,11"},
                {"3,10", "4,10"},
                {"3,10", "3,9"},

                {"4,10", "3,10"},
                {"4,10", "4,11"},
                {"4,10", "4,9"},
                {"4,10", "3,9"},

                {"0,11", "1,11"},
                {"0,11", "0,10"},

                {"1,11", "0,11"},
                {"1,11", "2,11"},
                {"1,11", "1,10"},

                {"2,11", "1,11"},
                {"2,11", "3,11"},
                {"2,11", "2,10"},

                {"3,11", "2,11"},
                {"3,11", "4,11"},
                {"3,11", "3,10"},

                {"4,11", "3,11"},
                {"4,11", "4,10"}
        };

        udg = new ListUDG(edges);
    }

    // 初始化棋盘连接图（是一个无向图）
    private ListUDG(String[][] edges) {

        // 顶点mVexs已经初始化过了

        // 处理边edges
        for (int i = 0; i < edges.length; i++) {
            // 读取边的起始顶点和结束顶点
            int indexStart = mVexsMap.get(edges[i][0]);
            int indexEnd = mVexsMap.get(edges[i][1]);
            // 处理链表（由于初始化时正反节点都有边的输入，所以只需处理当前正向节点的链表，不用管反向节点）
            ENode node = new ENode();
            node.ivex = indexEnd;
            // 将node1链接到"indexStart所在链表的末尾"
            if(mVexs[indexStart].edgeHead == null)
                mVexs[indexStart].edgeHead = node;
            else {
                node.nextEdge = mVexs[indexStart].edgeHead;
                mVexs[indexStart].edgeHead = node;
            }
        }
    }

    // 判断棋盘上能否从一个点到另一个点（需要比较棋子大小，当目标位置棋子比当前位置棋子大时，则算不能移动到达，
    // 路径中间有棋子挡住时，则不能到达）
    static public void canMoveTo(int[][] board, int currentPlayer, int nowX, int nowY,
                                 int targetX, int targetY, List<Move> moves) {
        int nowChessId = board[nowX][nowY];
        int nowChessType = ChessType.getType(nowChessId);
        int nowPositionType = PositionType.getType(nowX, nowY);

        int targetChessId = board[targetX][targetY];
        int targetChessType = ChessType.getType(targetChessId);
        int targetPositionType = PositionType.getType(targetX, targetY);

        // 目标位置棋子为己方棋子，则不能够移动
        if (currentPlayer == 0 && targetChessId <= 25 && targetChessId != 0
            || currentPlayer == 1 && targetChessId >= 26) {
            return;
        }

        // 目标棋子比当前棋子大，不能移动（炸弹除外）
        if (!ChessStrengthCompare.isStrongerOrEqualThan(nowChessId, targetChessId)
                && nowChessType != ChessType.BOOM_CHESS) {
            return;
        }

        // （优化点）当前位置在铁路上，目标位置不是铁路，如果不直接相邻则不能移动到达，
        if (nowPositionType == PositionType.RAILWAY_POSITION && targetPositionType != PositionType.RAILWAY_POSITION) {
            int index = nowX * BoardInfo.LENGTH + nowY;
            ENode head = mVexs[index].edgeHead;
            while (head != null) {
                if (targetX == mVexs[head.ivex].x && targetY == mVexs[head.ivex].y) {
                    List<Position> positions = new ArrayList<>(); // 找到的一条路径
                    positions.add(new Position().setX(nowX).setY(nowY).build());
                    positions.add(new Position().setX(targetX).setY(targetY).build());
                    JunQiMove move = new JunQiMove(positions);
                    moves.add(move);
                    head = null;
                    return;
                }
                head = head.nextEdge;
            }
            return;
        }

        // 当前在普通位置或行营位置，则只能移动到直接相邻处（判断语句里面逻辑和上面一样）
        if (nowPositionType == PositionType.NORMAL_POSITION || nowPositionType == PositionType.CAMP_POSITION) {
            int index = nowX * BoardInfo.LENGTH + nowY;
            ENode head = mVexs[index].edgeHead;
            while (head != null) {
                if (targetX == mVexs[head.ivex].x && targetY == mVexs[head.ivex].y) {
                    List<Position> positions = new ArrayList<>(); // 找到的一条路径
                    positions.add(new Position().setX(nowX).setY(nowY).build());
                    positions.add(new Position().setX(targetX).setY(targetY).build());
                    JunQiMove move = new JunQiMove(positions);
                    moves.add(move);
                    head = null;
                    return;
                }
                head = head.nextEdge;
            }
            return;
        }

        // 大本营位置已经在外面进行了过滤

        // 只剩下当前位置在铁路，且目标位置也在铁路的情况（判断语句用来方便看清逻辑）
        if (nowPositionType == PositionType.RAILWAY_POSITION && targetPositionType == PositionType.RAILWAY_POSITION) {
            if (nowChessType == ChessType.SOLDIER_CHESS) {
                // 当前是工兵，可以拐弯（最复杂的路径判断）
                
            } else {
                // 当前是司令~排长，或炸弹，不可以拐弯（地雷和军旗已经在外面过滤掉了）
                if (nowX != targetX && nowY != targetY) {
                    // 不在同一条直线上，肯定不能移动（ps：由于都在铁路，斜对角是不可能的）
                    return;
                } else if (nowX != targetX) {
                    int start;
                    int end;
                    if (nowX < targetX) {
                        start = nowX;
                        end = targetX;
                    } else {
                        start = targetX;
                        end = nowX;
                    }
                    for (int i = start + 1; i < end; i++) {
                        // 路径上存在其它棋子，不能移动
                        if (board[i][nowY] != 0) {
                            return;
                        }
                    }
                    // 路径畅通，可以移动
                    List<Position> positions = new ArrayList<>(); // 找到的一条路径
                    positions.add(new Position().setX(nowX).setY(nowY).build());
                    positions.add(new Position().setX(targetX).setY(targetY).build());
                    JunQiMove move = new JunQiMove(positions);
                    moves.add(move);
                    return;
                } else if (nowY != targetY) {
                    int start;
                    int end;
                    if (nowY < targetY) {
                        start = nowY;
                        end = targetY;
                    } else {
                        start = targetY;
                        end = nowY;
                    }
                    for (int i = start + 1; i < end; i++) {
                        // 路径上存在其它棋子，不能移动
                        if (board[nowX][i] != 0) {
                            return;
                        }
                    }
                    // 路径畅通，可以移动
                    List<Position> positions = new ArrayList<>(); // 找到的一条路径
                    positions.add(new Position().setX(nowX).setY(nowY).build());
                    positions.add(new Position().setX(targetX).setY(targetY).build());
                    JunQiMove move = new JunQiMove(positions);
                    moves.add(move);
                    return;
                }
                // 相等的情况在外面已经做了过滤
            }
        }
    }
}
