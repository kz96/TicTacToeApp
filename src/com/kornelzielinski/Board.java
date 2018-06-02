package com.kornelzielinski;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JPanel {

    Cell[][] cells;

    public void setCell(Content content, int row, int column) throws  IllegalArgumentException{
        if (cells[row][column].getContent() ==  Content.EMPTY) {
            cells[row][column].setContent(content);
        }
        else throw new IllegalArgumentException("Taken");
    }

    public Cell getCell (int row, int column) {
        return cells[row][column];
    }


    // initializing the game board
    public Board(ActionListener actionListener) {
        cells = new Cell[Main.ROWS][Main.COLUMNS];
        this.setLayout(new GridLayout(Main.ROWS, Main.COLUMNS));
        for (int row = 0; row < Main.ROWS; row++) {
            for (int col = 0; col < Main.COLUMNS; col++) {
                cells[row][col] = new Cell(row,col);
                this.add(cells[row][col]);
                cells[row][col].addActionListener(actionListener);
            }
        }
        init();
    }

    // (re)initializing content of the game board
    public void init() {
        for (int row = 0; row < Main.ROWS; row++) {
            for (int col = 0; col < Main.COLUMNS; col++) {
                cells[row][col].content = Content.EMPTY;
                repaint();
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

}
