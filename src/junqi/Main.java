package junqi;

import chessPostionInfo.Position;
import mcts.Move;
import utils.ListUDG;

import java.util.ArrayList;
import java.util.List;

public class Main {

    static public void main(String[] args) {
        // TODO test
        ListUDG udg = ListUDG.getUdg();
        int[][] board =  {
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}, // x = 0
                {13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24}, // x = 1
                {25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36}, // x = 2
                {37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48}, // x = 3
                {49, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // x = 4
        };

        List<Move> moves = new ArrayList<>();
        ListUDG.canMoveTo(board, 0, 0, 0, 2, moves);

        for (Move move: moves) {
            JunQiMove junQiMove = (JunQiMove) move;
            for (Position position: junQiMove.points) {
                System.out.println(position.getX() + " " + position.getY());
            }
        }
    }
}
