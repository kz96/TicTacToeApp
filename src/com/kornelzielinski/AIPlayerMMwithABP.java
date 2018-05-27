package com.kornelzielinski;

import java.util.ArrayList;
import java.util.List;

public class AIPlayerMMwithABP extends AIPlayer {
    public AIPlayerMMwithABP (Board board) {
        super(board);
    }

    @Override
    int[] move() {
        int[] result = minimax(2, myContent, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return new int[] {result[1], result[2]};
    }

    // recursive minimax to maximize or minimaze player
    private int[] minimax (int depth, Content player, int alpha, int beta) {

        // list of possible moves
        List<int[]> nextMoves = generateMoves();

        int score;
        int bestRow = -1;
        int bestColumn = -1;

        if (nextMoves.isEmpty() || depth == 0) {
            score = evaluate();
            return new int[] {score, bestRow, bestColumn};
        }
        else {
            for (int[] move : nextMoves) {
                cells[move[0]][move[1]].content = player;
                if (player == myContent) {
                    score = minimax(depth-1, aiContent, alpha, beta)[0];
                    if (score > alpha) {
                        alpha = score;
                        bestRow = move[0];
                        bestColumn = move[1];
                    }
                }
                cells[move[0]][move[1]].content = Content.EMPTY;
                if (alpha >= bestRow) break;
            }
            return new int[] {(player == myContent) ? alpha : beta, bestRow, bestColumn};
        }
    }

    // trying to find all valid moves
    private List<int[]> generateMoves() {
        List<int[]> nextMoves = new ArrayList<int[]>();

        // game over
        if(hasWon(myContent) || hasWon(aiContent)) {
            return nextMoves; // empty list
        }

        // searching empty cells to add to list
        for (int rows=0; rows < ROWS; rows++) {
            for (int cols=0; cols < COLUMNS; cols++) {
                if (cells[rows][cols].content == Content.EMPTY) {
                    nextMoves.add(new int[]{rows,cols});
                }
            }
        }
        return nextMoves;
    }

    // evaluation function
    private int evaluate() {
        int score = 0;
        score += evaluateLine(0, 0, 0, 1, 0, 2);
        score += evaluateLine(1, 0, 1, 1, 1, 2);
        score += evaluateLine(2, 0, 2, 1, 2, 2);
        score += evaluateLine(0, 0, 1, 0, 2, 0);
        score += evaluateLine(0, 1, 1, 1, 2, 1);
        score += evaluateLine(0, 2, 1, 2, 2, 2);
        score += evaluateLine(0, 0, 1, 1, 2, 2);
        score += evaluateLine(0, 2, 1, 1, 2, 0);
        return score;
    }

    private int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3) {
        int score = 0;

        // First cell
        if (cells[row1][col1].content == myContent) {
            score = 1;
        }
        else if (cells[row1][col1].content == aiContent) {
            score = -1;
        }

        // Second cell
        if (cells[row2][col2].content == myContent) {
            if (score == 1) { //cell 1 is mine
                score = 10;
            }
            else if (score == -1) { // cell 1 is opponents
                return 0;
            }
            else { // cell 1 is empty
                score = 1;
            }
        }
        else if (cells[row2][col2].content == aiContent) {
            if (score == -1) { //cell 1 is opponents
                score = 10;
            }
            else if (score == 1) { // cell 1 is mine
                return 0;
            }
            else { // cell 1 is empty
                score = -1;
            }
        }

        // Third cell
        if (cells[row3][col3].content == myContent) {
            if (score > 0) { //cell1 and/or cell2 is mine
                score *= 10;
            }
            else if (score < 0) { // cell1 and/or cell2 is oppnents
                return 0;
            }
            else { // cells 1 and 2 are empty
                score = 1;
            }
        }
        else if (cells[row3][col3].content == aiContent) {
            if (score < 0) { //cell1 and/or cell2 is oppnents
                score *= 10;
            }
            else if (score > 1) { // cell1 and/or cell2 is mine
                return 0;
            }
            else { // cells 1 and 2 are empty
                score = -1;
            }
        }
        return score;
    }

    private int[] winningPatterns = {
            0b111000000, 0b000111000, 0b000000111,
            0b100100100, 0b010010010, 0b001001001,
            0b100010001, 0b001010100  };

    private boolean hasWon (Content player) {
        int pattern = 0b000000000 ; // nine bit pattern for nine cells
        for (int rows=0; rows < ROWS; rows++) {
            for (int cols=0; cols < COLUMNS; cols++) {
                if (cells[rows][cols].content == player) {
                    pattern |= (1 << (rows * COLUMNS + cols));
                }
            }
        }
        for (int winningPattern : winningPatterns) {
            if ((pattern & winningPattern) == winningPattern) {
                return true;
            }
        }
        return false;
    }
}
