package global;

public class BoardLayout {

    // TODO （test）普通棋盘开局布局
    private int[][] NORMAL_LAYOUT_FIRST = {
            {25 + 25, 18 + 25, 19 + 25, 15 + 25, 16 + 25, 9 + 25, 4, 8, 13, 12, 2, 20},
            {1 + 25, 24 + 25, 0, 7 + 25, 0, 14 + 25, 3, 0, 6, 0, 10, 17},
            {23 + 25, 11 + 25, 5 + 25, 0, 21 + 25, 22 + 25, 22, 21, 0, 5, 11, 23},
            {17 + 25, 10 + 25, 0, 6 + 25, 0, 3 + 25, 14, 0, 7, 0, 24, 1},
            {20 + 25, 2 + 25, 12 + 25, 13 + 25, 8 + 25, 4 + 25, 9, 16, 15, 19, 18, 25}
    };

    // 不能用static方式，不然下一次游戏开始时布局不会更新
    public int[][] getNormalLayoutFirst() {
        return NORMAL_LAYOUT_FIRST;
    }
}
