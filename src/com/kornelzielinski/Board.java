package com.kornelzielinski;

import java.awt.*;

public class Board {
//    public static final int ROWS = 5, COLUMNS = 5;

    Cell[][] cells;
//    int currentRow, currentCollumn;

    // initializing the game board
    public Board() {
        cells = new Cell[Main.ROWS][Main.COLUMNS];
        for (int row = 0; row < Main.ROWS; row++) {
            for (int col = 0; col < Main.COLUMNS; col++) {
                cells[row][col] = new Cell(row,col);
            }
        }
    }

    // (re)initializing content of the game board
    public void init() {
        for (int row = 0; row < Main.ROWS; row++) {
            for (int col = 0; col < Main.COLUMNS; col++) {
                cells[row][col].clear();
            }
        }
    }

    // if draw true, else false (no empty cells)
    public boolean isDraw() {
        for (int row = 0; row < Main.ROWS; row++) {
            for (int col = 0; col < Main.COLUMNS; col++) {
                if (cells[row][col].content == Content.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    // if player won true, else false
    public boolean hasWon (Content con, int conRow, int conCol) {
        return (cells[conRow][0].content == con  // three in the row
                && cells[conRow][1].content == con
                && cells[conRow][2].content == con
                || cells[0][conCol].content == con // three in collumn
                && cells[1][conCol].content == con
                && cells[2][conCol].content == con
                || conRow == conCol // three in diagonal
                && cells[0][0].content == con
                && cells[1][1].content == con
                && cells[2][2].content == con
                || conRow + conCol == 2 // three in reverse diagonal
                && cells[0][2].content == con
                && cells[1][1].content == con
                && cells[2][0].content == con);
    }

    // painting the board
    public void paint(Graphics gDC) {
        gDC.setColor(Color.GRAY);
        for (int row = 0; row < Main.ROWS; row++) {
            gDC.fillRoundRect(0, Main.CELL_SIZE * row - Main.GRID_WIDTH_HALF,
                    Main.CANVAS_WIDTH-1, Main.GRID_WIDTH,
                    Main.GRID_WIDTH, Main.GRID_WIDTH);
        }
        for (int col = 0; col < Main.COLUMNS; col++) {
            gDC.fillRoundRect(Main.CELL_SIZE * col - Main.GRID_WIDTH_HALF, 0,
                    Main.GRID_WIDTH, Main.CANVAS_HEIGHT - 1,
                    Main.GRID_WIDTH, Main.GRID_WIDTH);
        }

        for (int row = 0; row < Main.ROWS; row++) {
            for (int col = 0; col < Main.COLUMNS; col++) {
                cells[row][col].paint(gDC);
            }
        }
    }
}
