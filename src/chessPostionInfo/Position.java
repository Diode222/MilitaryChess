package chessPostionInfo;

public class Position {

    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position setX(int x) {
        this.x = x;
        return this;
    }

    public Position setY(int y) {
        this.y = y;
        return this;
    }

    static public Position newBuilder() {
        return new Position();
    }

    public Position build() {
        return this;
    }
}
