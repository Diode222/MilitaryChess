package chessPostionInfo;

public class Chess {

    private int chessId;
    private int value;

    public int getChessId() {
        return chessId;
    }

    public int getValue() {
        return value;
    }

    public Chess setChessId(int chessId) {
        this.chessId = chessId;
        return this;
    }

    public Chess setValue(int value) {
        this.value = value;
        return this;
    }

    static public Chess newBuilder() {
        return new Chess();
    }

    public Chess build() {
        return this;
    }
}