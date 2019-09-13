package junqi;

import chessPostionInfo.Position;
import mcts.Move;

import java.util.ArrayList;
import java.util.List;

public class JunQiMove implements mcts.Move {

    List<Position> points;

    public JunQiMove(List<Position> points) {
        this.points = points;
    }

    @Override
    public int compareTo(Move move) {
        // TODO
        return 0;
    }
}
