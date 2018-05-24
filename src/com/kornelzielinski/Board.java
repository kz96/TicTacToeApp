package com.kornelzielinski;

public class Board {
    public static final int ROWS = 3, COLUMNS = 3;

    Cell[][] cells;
    int currentRow, currentCollumn;

    // initializing the game board
    public Board() {
        cells = new Cell[ROWS][COLUMNS];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                cells[row][col] = new Cell(row,col);
            }
        }
    }

    // (re)initializing content of the game board
    public void init() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                cells[row][col].clear();
            }
        }
    }

    // if draw true, else false (no empty cells)
    public boolean isDraw() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (cells[row][col].content == Content.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    // if player won true, else false
    public boolean hasWon (Content con) {
        return (cells[currentRow][0].content == con  // three in the row
                && cells[currentRow][1].content == con
                && cells[currentRow][2].content == con
                || cells[0][currentCollumn].content == con // three in collumn
                && cells[1][currentCollumn].content == con
                && cells[2][currentCollumn].content == con
                || currentRow == currentCollumn // three in diagonal
                && cells[0][0].content == con
                && cells[1][1].content == con
                && cells[2][2].content == con
                || currentRow + currentCollumn == 2 // three in reverse diagonal
                && cells[0][2].content == con
                && cells[1][1].content == con
                && cells[2][0].content == con);
    }

    // painting the board
    public void paint() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                cells[row][col].paint(); // print each of the cells
                if (col < COLUMNS - 1) {
                    System.out.print("|");   // print vertical partition
                }
            }
            System.out.println();
            if (row < ROWS - 1) {
                System.out.println("-----------"); // print horizontal partition
            }
        }
    }
}
