package junqi;

import chessPostionInfo.Position;
import main.Move;

import java.util.List;

public class JunQiMove implements main.Move {

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
