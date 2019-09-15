package militaryChess;

import chessPostionInfo.Position;
import global.BoardLayout;
import mcts.CallLocation;
import mcts.FinalSelectionPolicy;
import mcts.MCTS;
import mcts.Move;
import utils.ListUDG;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    static public void main(String[] args) {
//        // TODO test
//        ListUDG udg = ListUDG.getUdg();
//        int[][] board = new BoardLayout().getTest_layout();
//
//        MilitaryBoard junQiBoard = new MilitaryBoard();
//        junQiBoard.initBoard(board);
//        junQiBoard.currentPlayer = 1;
//        junQiBoard.backHandRemainMovableChessNum = 1;
//
//        ArrayList<Move> moves = new ArrayList<>();
//
////        ListUDG.canMoveTo(board, 0, 3, 1, 2, moves);
//        moves = junQiBoard.getMoves(CallLocation.treePolicy);
//
//        for (Move move: moves) {
//            MilitaryMove junQiMove = (MilitaryMove) move;
//            for (Position position: junQiMove.points) {
//                System.out.println(position.getX() + " " + position.getY());
//            }
//            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//        }
//        System.out.println(moves.size() + " " + junQiBoard.gameOver() + " " + junQiBoard.winner);
//        junQiBoard.makeMove(moves.get(0));
//        junQiBoard.bPrint();

        MCTS mcts = new MCTS();
        mcts.setExplorationConstant(10.0d); // TODO 可调节参数，越大代表越倾向于找访问次数较少的节点，会降低搜索深度提高宽度
        mcts.setTimeDisplay(true);
        Move move;
        mcts.setOptimisticBias(0.0d);
        mcts.setPessimisticBias(0.0d);
        mcts.setMoveSelectionPolicy(FinalSelectionPolicy.maxChild);
        int []eachWinsCount = new int[3];

        for (int i = 0; i < 100; i++) {
            MilitaryBoard junqi = new MilitaryBoard();
            junqi.currentPlayer = 0;
//            // TODO remove after test
//            junqi.firstHandRemainMovableChessNum = 1;
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
