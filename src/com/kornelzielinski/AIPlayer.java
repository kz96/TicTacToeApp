package com.kornelzielinski;

public abstract class AIPlayer {
    protected int ROWS = Main.ROWS;
    protected int COLUMNS = Main.COLUMNS;

    protected Cell[][] cells;
    protected Content userCon; // users content
    protected Content aiCon; // ais content

    public AIPlayer(Board board) {
        cells = board.cells;
    }

    public void setContent(Content content) {
        this.aiCon = content;
        userCon = (aiCon == Content.CROSS) ? Content.NOUGHT : Content.CROSS;
    }

    abstract int[] move();
}
