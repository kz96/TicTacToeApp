package com.kornelzielinski;

import java.util.Scanner;

public class Main {

    // cells and its contest
    public static final int EMPTY = 0;
    public static final int CROSS = 1;
    public static final int NOUGHT = 2;

    // results of the game
    public static final int PLAYING = 0;
    public static final int DRAW = 1;
    public static final int C_WON = 2;
    public static final int N_WON = 3;

    // Game board and status
    public static final int ROWS = 3, COLUMNS = 3;
    public static int[][] board = new int[ROWS][COLUMNS];
    public static int gameStatus, currentPlayer, currentRow, currentCollumn;

    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        initGame();

            do {
                playerMove(currentPlayer);
                updateGame(currentPlayer, currentRow, currentCollumn);
                printBoard();

                // message at the end of the game
                if (gameStatus == C_WON) {
                    System.out.println(" X WON !");
                }
                else if (gameStatus == N_WON) {
                    System.out.println(" O WON !");
                }
                else if (gameStatus == DRAW) {
                    System.out.println(" DRAW ! ");
                }
                currentPlayer = (currentPlayer == CROSS) ? NOUGHT : CROSS;
            }
            while (gameStatus == PLAYING);
    }

    // Initalize game board and current status
    public static void initGame() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                board[row][col] = EMPTY;
            }
        }
        gameStatus = PLAYING;
        currentPlayer = CROSS;
    }

    // Player makes a move, while updating current rows and columns
    public static void playerMove(int content) {
        boolean validInput = false;
        do {
            if (content == CROSS) {
                System.out.println("Player X, enter your move");
            } else {
                System.out.println("Player O, enter your move");
            }
            int row = in.nextInt() - 1;
            int col = in.nextInt() - 1;
            if (row >= 0 && row < ROWS && col >= 0 && col < COLUMNS && board[row][col] == EMPTY) {
                currentRow = row;
                currentCollumn = col;
                board[currentRow][currentCollumn] = content;
                validInput = true;
            } else {
                System.out.println("This move is invalid !");
            }
        } while (!validInput);
    }

    // Updating the state after the player made a move
    public static void updateGame(int content, int currentRow, int currentCollumn) {
        if (hasWon(content, currentRow, currentCollumn)) { // check if win
            gameStatus = (content == CROSS) ? C_WON : N_WON;
        }
        else if (isDraw()) { // check if draw
            gameStatus = DRAW;
        }
        // otherwise no change in state
    }

    // if draw true, else false (no empty cells)
    public static boolean isDraw() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (board[row][col] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    // if player won true, else false
    public static boolean hasWon (int content, int currentRow, int currentCollumn) {
        return (board[currentRow][0] == content  // three in the row
                && board[currentRow][1] == content
                && board[currentRow][2] == content
                || board[0][currentCollumn] == content // three in collumn
                && board[1][currentCollumn] == content
                && board[2][currentCollumn] == content
                || currentRow == currentCollumn // three in diagonal
                && board[0][0] == content
                && board[1][1] == content
                && board[2][2] == content
                || currentRow + currentCollumn == 2 // three in reverse diagonal
                && board[0][2] == content
                && board[1][1] == content
                && board[2][0] == content
        );
    }

    // printing the board
    public static void printBoard() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLUMNS; ++col) {
                printCell(board[row][col]); // print each of the cells
                if (col != COLUMNS - 1) {
                    System.out.print("|");   // print vertical partition
                }
            }
            System.out.println();
            if (row != ROWS - 1) {
                System.out.println("-----------"); // print horizontal partition
            }
        }
        System.out.println();
    }

    // print cell with content
    public static void printCell (int content) {
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
