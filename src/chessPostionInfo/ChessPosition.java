package chessPostionInfo;

public class ChessPosition {

    public Position point;
    public Chess chess;

    static public ChessPosition newBuilder() {
        return new ChessPosition();
    }

    public ChessPosition setPosition(Position point) {
        this.point = point;
        return this;
    }

    public ChessPosition setChess(Chess chess) {
        this.chess = chess;
        return this;
    }

    public ChessPosition build() {
        return this;
    }
}

