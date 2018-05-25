package com.kornelzielinski.AI;

import com.kornelzielinski.Board;
import com.kornelzielinski.Cell;
import com.kornelzielinski.Content;
import com.kornelzielinski.Main;

public abstract class AIPlayer {
    protected int ROWS = Main.ROWS;
    protected int COLUMNS = Main.COLUMNS;

    protected Cell[][] cells;
    protected Content myContent;
    protected Content aiContent;

    public AIPlayer(Board board) {
        cells = board.cells;
    }

    public void setContent(Content content) {
        this.myContent = content;
        aiContent = (myContent == Content.CROSS) ? Content.NOUGHT : Content.CROSS;
    }

    abstract int[] move();
}
