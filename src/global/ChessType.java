package global;

import java.util.HashMap;

public class ChessType {

    final static public int NORMAL_CHESS = 0; // 司令~炸弹~排长
    final static public int SOLDIER_CHESS = 1; // 工兵
    final static public int MINE_CHESS = 2; // 地雷
    final static public int FLAG_CHESS = 3; // 军旗
    final static public int BOOM_CHESS = 4; // 炸弹（炸弹和普通棋子NORMAL_CHESS用一样的走法）
    final static public int NO_CHESS = 5; // 表示棋盘当前位置没有棋子

    final static private HashMap<Integer, Integer> typeMap = new HashMap<Integer, Integer>();
    static {
        // 棋盘chessId=0表示没有棋子，则对应NO_CHESS
        typeMap.put(0, NO_CHESS);
        // 先手队伍
        typeMap.put(1, FLAG_CHESS);
        typeMap.put(2, NORMAL_CHESS);
        typeMap.put(3, NORMAL_CHESS);
        typeMap.put(4, NORMAL_CHESS);
        typeMap.put(5, NORMAL_CHESS);
        typeMap.put(6, NORMAL_CHESS);
        typeMap.put(7, NORMAL_CHESS);
        typeMap.put(8, NORMAL_CHESS);
        typeMap.put(9, NORMAL_CHESS);
        typeMap.put(10, NORMAL_CHESS);
        typeMap.put(11, NORMAL_CHESS);
        typeMap.put(12, BOOM_CHESS);
        typeMap.put(13, BOOM_CHESS);
        typeMap.put(14, NORMAL_CHESS);
        typeMap.put(15, NORMAL_CHESS);
        typeMap.put(16, NORMAL_CHESS);
        typeMap.put(17, NORMAL_CHESS);
        typeMap.put(18, NORMAL_CHESS);
        typeMap.put(19, NORMAL_CHESS);
        typeMap.put(20, SOLDIER_CHESS);
        typeMap.put(21, SOLDIER_CHESS);
        typeMap.put(22, SOLDIER_CHESS);
        typeMap.put(23, MINE_CHESS);
        typeMap.put(24, MINE_CHESS);
        typeMap.put(25, MINE_CHESS);

        // 后手队伍
        typeMap.put(26, FLAG_CHESS);
        typeMap.put(27, NORMAL_CHESS);
        typeMap.put(28, NORMAL_CHESS);
        typeMap.put(29, NORMAL_CHESS);
        typeMap.put(30, NORMAL_CHESS);
        typeMap.put(31, NORMAL_CHESS);
        typeMap.put(32, NORMAL_CHESS);
        typeMap.put(33, NORMAL_CHESS);
        typeMap.put(34, NORMAL_CHESS);
        typeMap.put(35, NORMAL_CHESS);
        typeMap.put(36, NORMAL_CHESS);
        typeMap.put(37, BOOM_CHESS);
        typeMap.put(38, BOOM_CHESS);
        typeMap.put(39, NORMAL_CHESS);
        typeMap.put(40, NORMAL_CHESS);
        typeMap.put(41, NORMAL_CHESS);
        typeMap.put(42, NORMAL_CHESS);
        typeMap.put(43, NORMAL_CHESS);
        typeMap.put(44, NORMAL_CHESS);
        typeMap.put(45, SOLDIER_CHESS);
        typeMap.put(46, SOLDIER_CHESS);
        typeMap.put(47, SOLDIER_CHESS);
        typeMap.put(48, MINE_CHESS);
        typeMap.put(49, MINE_CHESS);
        typeMap.put(50, MINE_CHESS);
    };

    static public int getType(int chessId) {
        return typeMap.get(chessId);
    }
}
