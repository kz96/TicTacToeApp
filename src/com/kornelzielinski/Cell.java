package com.kornelzielinski;

public class Cell {

    // Content of the cell
    Content content;

    // row and column of the cell
    int row, col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        clear();
    }

    // clearing the content of the cell to EMPTY
    public void clear() {
        content = Content.EMPTY;
    }

    // paint cell with content
    public void paint () {
        switch (content){
            case EMPTY:
                System.out.print("   ");
                break;
            case NOUGHT:
                System.out.print(" O ");
                break;
            case CROSS:
                System.out.print(" X ");
                break;
        }
    }
}
