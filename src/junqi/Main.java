package junqi;

import chessPostionInfo.Position;
import mcts.CallLocation;
import mcts.Move;
import utils.ChessStrengthCompare;
import utils.ListUDG;

import java.util.ArrayList;
import java.util.List;

public class Main {

    static public void main(String[] args) {
        // TODO test
        ListUDG udg = ListUDG.getUdg();
        int[][] board =  {
                {1, 0, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}, // x = 0
                {13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 50}, // x = 1
                {0, 26, 27, 28, 29, 30, 31, 32, 33, 34, 46, 36}, // x = 2
                {35, 24, 39, 40, 41, 42, 0, 44, 47, 45, 0, 37}, // x = 3
                {49, 25, 0, 0, 0, 0, 2, 0, 0, 0, 38, 0}, // x = 4
        };

        JunQiBoard junQiBoard = new JunQiBoard();
        junQiBoard.setBoard(board);

        ArrayList<Move> moves = junQiBoard.getMoves(CallLocation.treePolicy);

        for (Move move: moves) {
            JunQiMove junQiMove = (JunQiMove) move;
            for (Position position: junQiMove.points) {
                System.out.println(position.getX() + " " + position.getY());
            }
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
    }
}
