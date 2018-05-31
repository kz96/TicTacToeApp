package com.kornelzielinski;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

public class Main extends JFrame {
    // constants for the game
    public static final int COLUMNS = 3, ROWS = 3;

    // initializing important fields
    private Board board;
    private  GameStatus gameStatus;
    private Content currentPlayer;
    private AIPlayer aiPlayer;
    private JMenuItem miClose, miNew;
    private ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == miClose){
                dispose();
            }
            else if (e.getSource() == miNew) {
                initGame();
                repaint();
            }
        }
    };

    // constructor setting up the game
    public Main() {
        board = new Board((ActionEvent e) -> {
            currentPlayer = Content.CROSS;
            Cell c = (Cell) e.getSource();
                c.content = currentPlayer;
                updateGame(currentPlayer, c.getRow(), c.getCol());
                if (gameStatus == GameStatus.C_WON) {
                    repaint();
                    board.hasWon(currentPlayer, c.getRow(), c.getCol());
                }
                else if (gameStatus == GameStatus.DRAW) {
                    repaint();
                    board.isDraw();
                }
                else {
                    currentPlayer = (currentPlayer == Content.CROSS) ? Content.NOUGHT : Content.CROSS;
                    AIMove();
                    updateGame(currentPlayer, c.getRow(), c.getCol());
                    repaint();
                }
                repaint();
                if (gameStatus != GameStatus.PLAYING) {
                    initGame();
                }
        });
        this.add(board);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("TIC-TAC-TOE");
        this.setSize(400, 400);
        this.setVisible(true);
        JMenuBar jMenuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        miNew = new JMenuItem("New Game");
        menu.add(miNew);
        menu.addSeparator();
        miClose = new JMenuItem("Close");
        menu.add(miClose);
        jMenuBar.add(menu);
        setJMenuBar(jMenuBar);
        miClose.addActionListener(al);
        initGame();

    }

    // Initalize game board and current status
    public void initGame() {
        for (int rows = 0; rows < ROWS; rows++) {
            for (int cols = 0; cols < COLUMNS; cols++) {
                board.cells[rows][cols].content = Content.EMPTY;
            }
        }
        aiPlayer = new AIPlayerMMwithABP(board);
        aiPlayer.setContent(Content.NOUGHT);
        gameStatus = GameStatus.PLAYING;
        currentPlayer = Content.CROSS;
    }


    // Updating the state after the player made a move
    public void updateGame(Content con, int rows, int cols) {
        if (board.hasWon(con, rows, cols)) { // check if win
            gameStatus = (con == Content.CROSS) ? GameStatus.C_WON : GameStatus.N_WON;
            getGameStatus();
        }
        else if (board.isDraw()) { // check if draw
            gameStatus = GameStatus.DRAW;
            getGameStatus();
        }
        // otherwise no change in state
    }


    public void getGameStatus() {
        if (gameStatus == GameStatus.PLAYING) {
            gameStatus = (currentPlayer == Content.CROSS) ? GameStatus.C_WON : GameStatus.N_WON;
        }
        else if (gameStatus == GameStatus.C_WON) {
            JOptionPane.showMessageDialog(null, "X WON !");
            initGame();
        }
        else if (gameStatus == GameStatus.N_WON) {
            JOptionPane.showMessageDialog(null, "O WON !");
            initGame();
        }
        else if (gameStatus == GameStatus.DRAW) {
            JOptionPane.showMessageDialog(null, "DRAW !");
            initGame();
        }
    }

    public void AIMove() {
        int[] generatedMove = aiPlayer.move();
        board.cells[generatedMove[0]][generatedMove[1]].content = currentPlayer;
        updateGame(currentPlayer, generatedMove[0], generatedMove[1]);
        repaint();
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}
