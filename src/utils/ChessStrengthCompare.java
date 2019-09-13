package utils;

import java.util.HashMap;

public class ChessStrengthCompare {

    final static private HashMap<Integer, Integer> strengthMap = new HashMap<Integer, Integer>() {{
        // 上半区先手
        strengthMap.put(1, -1);
        strengthMap.put(2, 100);
        strengthMap.put(3, 99);
        strengthMap.put(4, 98);
        strengthMap.put(5, 98);
        strengthMap.put(6, 97);
        strengthMap.put(7, 97);
        strengthMap.put(8, 96);
        strengthMap.put(9, 96);
        strengthMap.put(10, 95);
        strengthMap.put(11, 95);
        strengthMap.put(14, 94);
        strengthMap.put(15, 94);
        strengthMap.put(16, 94);
        strengthMap.put(17, 93);
        strengthMap.put(18, 93);
        strengthMap.put(19, 93);
        strengthMap.put(20, 92);
        strengthMap.put(21, 92);
        strengthMap.put(22, 92);
        strengthMap.put(23, 0);
        strengthMap.put(24, 0);
        strengthMap.put(25, 0);
        strengthMap.put(12, 0);
        strengthMap.put(13, 0);
        // 下半区后手
        strengthMap.put(26, -1);
        strengthMap.put(27, 100);
        strengthMap.put(28, 99);
        strengthMap.put(29, 98);
        strengthMap.put(30, 98);
        strengthMap.put(31, 97);
        strengthMap.put(32, 97);
        strengthMap.put(33, 96);
        strengthMap.put(34, 96);
        strengthMap.put(35, 95);
        strengthMap.put(36, 95);
        strengthMap.put(39, 94);
        strengthMap.put(40, 94);
        strengthMap.put(41, 94);
        strengthMap.put(42, 93);
        strengthMap.put(43, 93);
        strengthMap.put(44, 93);
        strengthMap.put(45, 92);
        strengthMap.put(46, 92);
        strengthMap.put(47, 92);
        strengthMap.put(48, 0);
        strengthMap.put(49, 0);
        strengthMap.put(50, 0);
        strengthMap.put(37, 0);
        strengthMap.put(38, 0);
    }};

    static public int getChessStrength(int chessId) {
        return strengthMap.get(chessId);
    }

    static public boolean isStrongerOrEqualThan(int chessId1, int chessId2) {
        return strengthMap.get(chessId1) >= strengthMap.get(chessId2);
    }
}
