package com.kornelzielinski;

public abstract class AIPlayer {
    protected int ROWS = Main.ROWS;
    protected int COLUMNS = Main.COLUMNS;

    protected Cell[][] cells;
    protected Content oppContent;
    protected Content myContent;

    public AIPlayer(Board board) {
        cells = board.cells;
    }

    public void setContent(Content content) {
        this.myContent = content;
        oppContent = (myContent == Content.CROSS) ? Content.NOUGHT : Content.CROSS;
    }

    abstract int[] move();
}
