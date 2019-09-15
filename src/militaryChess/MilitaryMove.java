package militaryChess;

import chessPostionInfo.Position;
import mcts.Move;

import java.util.List;

public class MilitaryMove implements mcts.Move {

    List<Position> points;

    public MilitaryMove(List<Position> points) {
        this.points = points;
    }

    @Override
    public int compareTo(Move move) {
        // TODO
        return 0;
    }
}
