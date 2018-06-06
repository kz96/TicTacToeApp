package com.kornelzielinski;

//import javax.imageio.ImageIO;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
//import java.awt.image.BufferedImage;
//import java.io.IOException;

public class Cell extends JButton {

    // Content of the cell
    Content content;

    // Icons of content
    ImageIcon X = new ImageIcon(this.getClass().getResource("X.png"));
    ImageIcon O = new ImageIcon(this.getClass().getResource("O.png"));


    // row and column of the cell
    int row, col;

    // constructor
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        clear();
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    // clearing the content of the cell to EMPTY
    public void clear() {
        content = Content.EMPTY;
        repaint();
    }

    // paint cell with content
    @Override
    public void paintComponent (Graphics gDC) {
        super.paintComponent(gDC);
        Graphics2D graphics2D = (Graphics2D) gDC;
        graphics2D.setStroke(new BasicStroke(5));;
        switch (content) {
            case NOUGHT:
                setIcon(O);
                break;
            case CROSS:
                setIcon(X);
                break;
            case EMPTY:
                setIcon(null);
                break;
        }
    }
}
