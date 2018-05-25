package com.kornelzielinski;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;

public class Main extends JPanel {
    // constants for the game
    public static final int COLUMNS = 3, ROWS = 3;
    public static final String HEAD = "Tic Tac Toe";

    //dimension for graphics drawing
    public static final int CELL_SIZE=100;
    public static final int CANVAS_WIDTH = CELL_SIZE * COLUMNS;
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    public static final int GRID_WIDTH = 8;
    public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;
    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static final int SYMBOL_STROKE_WIDTH = 8;

    // initializing important fields
    private Board board;
    private  GameStatus gameStatus;
    private Content currentPlayer;
    private JLabel statusLabel;

    public static Scanner in = new Scanner(System.in);

    // constructor setting up the game
    public Main() {

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mX = e.getX();
                int mY = e.getY();

                int rowSel = mY / CELL_SIZE;
                int colSel = mX / CELL_SIZE;

                if (gameStatus == GameStatus.PLAYING) {
                    if (rowSel >= 0 && rowSel < ROWS
                            && colSel >= 0 && colSel < COLUMNS
                            && board.cells[rowSel][colSel].content == Content.EMPTY) {
                        board.cells[rowSel][colSel].content = currentPlayer;
                        updateGame(currentPlayer, rowSel, colSel);
                        // swaping players
                        currentPlayer = (currentPlayer == Content.CROSS) ? Content.NOUGHT : Content.CROSS;
                    }
                    } else {
                        initGame();
                    }
                    repaint();
            }
        });

        statusLabel = new JLabel("        ");
        statusLabel.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(2,5,4,5));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(Color.LIGHT_GRAY);

        setLayout(new BorderLayout());
        add(statusLabel, BorderLayout.PAGE_END);
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT+30));

        board = new Board();
        initGame();
    }



    // Initalize game board and current status
    public void initGame() {
        for (int rows = 0; rows < ROWS; rows++) {
            for (int cols = 0; cols < COLUMNS; cols++) {
                board.cells[rows][cols].content = Content.EMPTY;
            }
        }
        currentPlayer = Content.CROSS;
        gameStatus = GameStatus.PLAYING;
    }

    // Player makes a move, while updating current rows and columns
//    public void playerMove(Content con) {
//        boolean validInput = false;
//        do {
//            if (con == Content.CROSS) {
//                System.out.println("Player X, enter your move");
//            } else {
//                System.out.println("Player O, enter your move");
//            }
//            int row = in.nextInt() - 1;
//            int col = in.nextInt() - 1;
//            if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLUMNS && board.cells[row][col].content == Content.EMPTY) {
//                board.cells[row][col].content = con;
//                board.currentRow = row;
//                board.currentCollumn = col;
//                validInput = true;
//            } else {
//                System.out.println("This move is invalid !");
//            }
//        } while (!validInput);
//    }

    // Updating the state after the player made a move
    public void updateGame(Content con, int rows, int cols) {
        if (board.hasWon(con, rows, cols)) { // check if win
            gameStatus = (con == Content.CROSS) ? GameStatus.C_WON : GameStatus.N_WON;
        }
        else if (board.isDraw()) { // check if draw
            gameStatus = GameStatus.DRAW;
        }
        // otherwise no change in state
    }

    public void paintComponent(Graphics gDC) {
        super.paintComponent(gDC);
        setBackground(Color.WHITE);
        board.paint(gDC);

        if (gameStatus == GameStatus.PLAYING) {
            statusLabel.setForeground(Color.BLACK);
            if (currentPlayer == Content.CROSS) {
                statusLabel.setText("X make a move !");
            }
            else {
                statusLabel.setText("O make a move !");
            }
        }
        else if (gameStatus == GameStatus.DRAW){
            statusLabel.setForeground(Color.RED);
            statusLabel.setText("DRAW !");
        }
        else if (gameStatus == GameStatus.C_WON) {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText("X WON ! Click to restart");
        }
        else if (gameStatus == GameStatus.N_WON) {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText("O WON ! Click to restart");
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame(HEAD);
                frame.setContentPane(new Main());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
