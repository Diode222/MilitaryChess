package global;

import java.util.HashMap;

public class ChessValue {

    final static private HashMap<Integer, Integer> valueMap = new HashMap<>();
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

    final static private HashMap<Integer, Integer> valueMapCalculate = new HashMap<Integer, Integer>();
    static {
        // 没有棋子分数为0
        valueMapCalculate.put(0, 0);
        // 先手队伍
        valueMapCalculate.put(1, 0); // 军旗会作为胜利来加上
        valueMapCalculate.put(2, 35);
        valueMapCalculate.put(3, 26);
        valueMapCalculate.put(4, 17);
        valueMapCalculate.put(5, 17);
        valueMapCalculate.put(6, 9);
        valueMapCalculate.put(7, 9);
        valueMapCalculate.put(8, 7);
        valueMapCalculate.put(9, 7);
        valueMapCalculate.put(10, 5);
        valueMapCalculate.put(11, 5);
        valueMapCalculate.put(12, 13);
        valueMapCalculate.put(13, 13);
        valueMapCalculate.put(14, 3);
        valueMapCalculate.put(15, 3);
        valueMapCalculate.put(16, 3);
        valueMapCalculate.put(17, 2);
        valueMapCalculate.put(18, 2);
        valueMapCalculate.put(19, 2);
        valueMapCalculate.put(20, 6);
        valueMapCalculate.put(21, 6);
        valueMapCalculate.put(22, 6);
        valueMapCalculate.put(23, 2);
        valueMapCalculate.put(24, 2);
        valueMapCalculate.put(25, 2);
        // 后手队伍
        valueMapCalculate.put(26, 0);
        valueMapCalculate.put(27, 35);
        valueMapCalculate.put(28, 26);
        valueMapCalculate.put(29, 17);
        valueMapCalculate.put(30, 17);
        valueMapCalculate.put(31, 9);
        valueMapCalculate.put(32, 9);
        valueMapCalculate.put(33, 7);
        valueMapCalculate.put(34, 7);
        valueMapCalculate.put(35, 5);
        valueMapCalculate.put(36, 5);
        valueMapCalculate.put(37, 13);
        valueMapCalculate.put(38, 13);
        valueMapCalculate.put(39, 3);
        valueMapCalculate.put(40, 3);
        valueMapCalculate.put(41, 3);
        valueMapCalculate.put(42, 2);
        valueMapCalculate.put(43, 2);
        valueMapCalculate.put(44, 2);
        valueMapCalculate.put(45, 6);
        valueMapCalculate.put(46, 6);
        valueMapCalculate.put(47, 6);
        valueMapCalculate.put(48, 2);
        valueMapCalculate.put(49, 2);
        valueMapCalculate.put(50, 2);
    };

    // 用于展示的真实得分
    static public int getRealValue(int chessId) {
        return valueMap.get(chessId);
    }

    // 用于迭代计算量化的得分
    static public int getCalculateValue(int chessId) {
        return valueMapCalculate.get(chessId);
    }
}
