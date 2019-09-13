package global;

import java.util.HashMap;

public class ChessPower {

    static private HashMap<Integer, Integer> powerMap = new HashMap<Integer, Integer>() {{
        // 先手队伍
        powerMap.put(1, -1);
        powerMap.put(2, 35);
        powerMap.put(3, 26);
        powerMap.put(4, 17);
        powerMap.put(5, 17);
        powerMap.put(6, 9);
        powerMap.put(7, 9);
        powerMap.put(8, 7);
        powerMap.put(9, 7);
        powerMap.put(10, 5);
        powerMap.put(11, 5);
        powerMap.put(12, 13);
        powerMap.put(13, 13);
        powerMap.put(14, 3);
        powerMap.put(15, 3);
        powerMap.put(16, 3);
        powerMap.put(17, 2);
        powerMap.put(18, 2);
        powerMap.put(19, 2);
        powerMap.put(20, 6);
        powerMap.put(21, 6);
        powerMap.put(22, 6);
        powerMap.put(23, 2);
        powerMap.put(24, 2);
        powerMap.put(25, 2);
        // 后手队伍
        powerMap.put(26, -1);
        powerMap.put(27, 35);
        powerMap.put(28, 26);
        powerMap.put(29, 17);
        powerMap.put(30, 17);
        powerMap.put(31, 9);
        powerMap.put(32, 9);
        powerMap.put(33, 7);
        powerMap.put(34, 7);
        powerMap.put(35, 5);
        powerMap.put(36, 5);
        powerMap.put(37, 13);
        powerMap.put(38, 13);
        powerMap.put(39, 3);
        powerMap.put(40, 3);
        powerMap.put(41, 3);
        powerMap.put(42, 2);
        powerMap.put(43, 2);
        powerMap.put(44, 2);
        powerMap.put(45, 6);
        powerMap.put(46, 6);
        powerMap.put(47, 6);
        powerMap.put(48, 2);
        powerMap.put(49, 2);
        powerMap.put(50, 2);
    }};

    static public int getPower(int chessId) {
        return powerMap.get(chessId);
    }

    static public void setPower(int chessId, int power) {
        powerMap.put(chessId, power);
    }
}
