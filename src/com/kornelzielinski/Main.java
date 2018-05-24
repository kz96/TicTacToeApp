package com.kornelzielinski;

import java.util.Scanner;

public class Main {

    // initializing important fields
    private Board board;
    private  GameStatus gameStatus;
    private Content currentPlayer;

    public static Scanner in = new Scanner(System.in);

    // constructor settin up the game
    public Main() {
        board = new Board();

        initGame();
        do {
            playerMove(currentPlayer);
            board.paint();
            updateGame(currentPlayer);

            // message at the end of the game
            if (gameStatus == GameStatus.C_WON) {
                System.out.println("X WON !");
            }
            else if (gameStatus == GameStatus.N_WON) {
                System.out.println("O WON !");
            }
            else if (gameStatus == GameStatus.DRAW) {
                System.out.println("DRAW !");
            }

            // switching the player
            currentPlayer = (currentPlayer == Content.CROSS) ? Content.NOUGHT : Content.CROSS;
        }
        while (gameStatus == GameStatus.PLAYING);
    }


    // Initalize game board and current status
    public void initGame() {
        board.init();
        currentPlayer = Content.CROSS;
        gameStatus = GameStatus.PLAYING;
    }

    // Player makes a move, while updating current rows and columns
    public void playerMove(Content con) {
        boolean validInput = false;
        do {
            if (con == Content.CROSS) {
                System.out.println("Player X, enter your move");
            } else {
                System.out.println("Player O, enter your move");
            }
            int row = in.nextInt() - 1;
            int col = in.nextInt() - 1;
            if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLUMNS && board.cells[row][col].content == Content.EMPTY) {
                board.cells[row][col].content = con;
                board.currentRow = row;
                board.currentCollumn = col;
                validInput = true;
            } else {
                System.out.println("This move is invalid !");
            }
        } while (!validInput);
    }

    // Updating the state after the player made a move
    public void updateGame(Content con) {
        if (board.hasWon(con)) { // check if win
            gameStatus = (con == Content.CROSS) ? GameStatus.C_WON : GameStatus.N_WON;
        }
        else if (board.isDraw()) { // check if draw
            gameStatus = GameStatus.DRAW;
        }
        // otherwise no change in state
    }

    public static void main(String[] args) {
        new Main();
    }
}
