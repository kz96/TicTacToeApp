package com.kornelzielinski;

//import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.IOException;

public class Cell extends JPanel {

    // Content of the cell
    Content content;
//    private BufferedImage image;

    // row and column of the cell
    int row, col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        clear();
//        try {
//            image = ImageIO.read(getClass().getResourceAsStream("X.png"));
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        repaint();
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
                graphics2D.setColor(Color.BLACK);
                int x2 = (col + 1) * Main.CELL_SIZE - Main.CELL_PADDING;
                int y2 = (row + 1) * Main.CELL_SIZE - Main.CELL_PADDING;
                graphics2D.drawLine(x1,y1,x2,y2);
                graphics2D.drawLine(x2,y1,x1,y2);
//                gDC.drawImage(image, x2-x1, y2-y1, x2-x1, y2-y1, null);
            }
            else if (content == Content.NOUGHT) {
                graphics2D.setColor(Color.BLACK);
                graphics2D.drawOval(x1, y1, Main.SYMBOL_SIZE, Main.SYMBOL_SIZE);
            }
    }
}
