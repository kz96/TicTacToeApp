package com.kornelzielinski;

import java.awt.*;

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
    public void paint (Graphics gDC) {
        Graphics2D graphics2D;
        graphics2D = (Graphics2D) gDC;
        graphics2D.setStroke(new BasicStroke(Main.SYMBOL_STROKE_WIDTH,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            int x1 = col * Main.CELL_SIZE + Main.CELL_PADDING;
            int y1 = row * Main.CELL_SIZE + Main.CELL_PADDING;
            if (content == Content.CROSS) {
                graphics2D.setColor(Color.RED);
                int x2 = (col + 1) * Main.CELL_SIZE - Main.CELL_PADDING;
                int y2 = (row + 1) * Main.CELL_SIZE - Main.CELL_PADDING;
                graphics2D.drawLine(x1,y1,x2,y2);
                graphics2D.drawLine(x2,y1,x1,y2);
            }
            else if (content == Content.NOUGHT) {
                graphics2D.setColor(Color.BLUE);
                graphics2D.drawOval(x1, y1, Main.SYMBOL_SIZE, Main.SYMBOL_SIZE);
            }
    }
}
