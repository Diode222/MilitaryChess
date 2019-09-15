package global;

import java.util.HashMap;

public class ChessValue {

    static private HashMap<Integer, Integer> valueMap = new HashMap<>();
    static {
        // 棋盘chessId=0表示没有棋子，则对应0分(trick)
        valueMap.put(0, 0);
        // 先手队伍
        valueMap.put(1, 0); // 军旗不算入动态分
        valueMap.put(2, 5);
        valueMap.put(3, 5);
        valueMap.put(4, 2);
        valueMap.put(5, 2);
        valueMap.put(6, 2);
        valueMap.put(7, 2);
        valueMap.put(8, 2);
        valueMap.put(9, 2);
        valueMap.put(10, 2);
        valueMap.put(11, 2);
        valueMap.put(12, 2);
        valueMap.put(13, 2);
        valueMap.put(14, 1);
        valueMap.put(15, 1);
        valueMap.put(16, 1);
        valueMap.put(17, 1);
        valueMap.put(18, 1);
        valueMap.put(19, 1);
        valueMap.put(20, 1);
        valueMap.put(21, 1);
        valueMap.put(22, 1);
        valueMap.put(23, 1);
        valueMap.put(24, 1);
        valueMap.put(25, 1);
        // 后手队伍
        valueMap.put(26, 0);
        valueMap.put(27, 5);
        valueMap.put(28, 5);
        valueMap.put(29, 2);
        valueMap.put(30, 2);
        valueMap.put(31, 2);
        valueMap.put(32, 2);
        valueMap.put(33, 2);
        valueMap.put(34, 2);
        valueMap.put(35, 2);
        valueMap.put(36, 2);
        valueMap.put(37, 2);
        valueMap.put(38, 2);
        valueMap.put(39, 1);
        valueMap.put(40, 1);
        valueMap.put(41, 1);
        valueMap.put(42, 1);
        valueMap.put(43, 1);
        valueMap.put(44, 1);
        valueMap.put(45, 1);
        valueMap.put(46, 1);
        valueMap.put(47, 1);
        valueMap.put(48, 1);
        valueMap.put(49, 1);
        valueMap.put(50, 1);
    }

    static public int getValue(int chessId) {
        return valueMap.get(chessId);
    }
}
