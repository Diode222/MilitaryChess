package junqi;

import chessPostionInfo.Position;
import global.BoardLayout;
import main.CallLocation;
import main.FinalSelectionPolicy;
import main.MCTS;
import main.Move;
import utils.ListUDG;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    static public void main(String[] args) {
//        // TODO test
//        ListUDG udg = ListUDG.getUdg();
////        int[][] board =  {
//////                {1, 0, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}, // x = 0
//////                {13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 50}, // x = 1
//////                {0, 26, 27, 28, 29, 30, 31, 32, 33, 34, 46, 36}, // x = 2
//////                {35, 24, 39, 40, 41, 42, 0, 44, 47, 45, 0, 37}, // x = 3
//////                {49, 25, 0, 0, 0, 0, 2, 0, 0, 0, 38, 0}, // x = 4
//////                {50 ,0 ,0, 0, 0 ,0 ,31, 0 ,6, 0 ,20 ,0},
//////                {26, 49 ,0, 0, 0, 0 ,16 ,0, 0, 5 ,10 ,17},
//////                {48 ,0, 0 ,0, 0, 0, 0 ,0 ,0 ,0 ,11, 23},
//////                {42, 0, 0, 30, 0 ,0, 0 ,0 ,0 ,0 ,24, 1},
//////                {0 ,45 ,0, 27, 0 ,0 ,0 ,0, 0 ,0, 0 ,25}
////                {50   ,  0    , 0    , 0    , 0    , 0    , 0     ,0   ,  0   ,  0   ,  0   ,  0},
////
////                {26   , 22   ,  0   ,  0    , 0 ,    0  ,   0 ,    0    , 0  ,   0  ,   0  ,  17},
////
////                {48  ,   0   ,  0    , 0  ,   0    , 0    , 0    , 0    , 0   ,  0  ,  11  ,  23},
////
////                {42   ,  0    , 0   ,  0   ,  0 ,    0  ,   0   ,  0    , 0    , 0  ,  24   ,  1},
////
////                {45  ,   8     ,0   ,  0    , 0,     0   ,  0   ,  0   ,  0    , 0   ,  0   , 25}
////        };
//        int[][] board = new BoardLayout().getTest_layout();
//
//        JunQiBoard junQiBoard = new JunQiBoard();
//        junQiBoard.initBoard(board);
//        junQiBoard.currentPlayer = 0;
//
//        ArrayList<Move> moves = new ArrayList<>();
//
////        ListUDG.canMoveTo(board, 0, 3, 1, 2, moves);
//        moves = junQiBoard.getMoves(CallLocation.treePolicy);
//
//        for (Move move: moves) {
//            JunQiMove junQiMove = (JunQiMove) move;
//            for (Position position: junQiMove.points) {
//                System.out.println(position.getX() + " " + position.getY());
//            }
//            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//        }
//        System.out.println(moves.size() + " " + junQiBoard.gameOver() + " " + junQiBoard.winner);
//        junQiBoard.makeMove(moves.get(0));
//        junQiBoard.bPrint();

        MCTS mcts = new MCTS();
        mcts.setExplorationConstant(1.4d); // TODO 可调节参数，越大代表越倾向于找访问次数较少的节点，会降低搜索深度提高宽度
        mcts.setTimeDisplay(true);
        Move move;
        mcts.setOptimisticBias(0.0d);
        mcts.setPessimisticBias(0.0d);
        mcts.setMoveSelectionPolicy(FinalSelectionPolicy.maxChild);
        int []eachWinsCount = new int[3];

        for (int i = 0; i < 100; i++) {
            JunQiBoard junqi = new JunQiBoard();
            junqi.currentPlayer = 0;
            junqi.initBoard(new BoardLayout().getNormalLayoutFirst());
            while (!junqi.gameOver()) {
                try {
                    move = mcts.runMCTS_UCT(junqi, 0, false);
                } catch (Exception e) {
                    e.printStackTrace();
                    move = null;
                }
                junqi.makeMove(move);
            }

            System.out.println("----------------");
            junqi.bPrint();

            double []score = junqi.getScoreToShow();
            if (score[0] > score[1]) {
                eachWinsCount[0]++;
            } else if (score[1] > score[0]) {
                eachWinsCount[1]++;
            } else {
                eachWinsCount[2]++;
            }

            System.out.println(Arrays.toString(score));
            System.out.println(Arrays.toString(eachWinsCount));
        }
    }
}
