package com.kornelzielinski;

import java.util.ArrayList;
import java.util.List;

public class AIPlayerMMwithABP extends AIPlayer {
    public AIPlayerMMwithABP (Board board) {
        super(board);
    }

    @Override
    int[] move() {
        int[] result = minimax(2, userCon, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return new int[] {result[1], result[2]};
    }

    // recursive minimax to maximize or minimaze player
    private int[] minimax (int depth, Content player, int alpha, int beta) {

        // list of possible moves
        List<int[]> nextMoves = generateMoves();

        int score;
        int bestR = -1;
        int bestC = -1;

        if (nextMoves.isEmpty() || depth == 0) {
            score = evaluate();
            return new int[]{score, bestR, bestC};
        }
        else {
            for (int[] move : nextMoves) {
                cells[move[0]][move[1]].content = player;
                if (player == aiCon) {
                    score = minimax(depth - 1, userCon, alpha, beta)[0];
                    if (score > alpha) {
                        alpha = score;
                        bestR = move[0];
                        bestC = move[1];
                    }
                } else {
                    score = minimax(depth - 1, aiCon, alpha, beta)[0];
                    if (score < beta) {
                        beta = score;
                        bestR = move[0];
                        bestC = move[1];
                    }
                }
                cells[move[0]][move[1]].content = Content.EMPTY;
                if (alpha >= beta) break; // cut
            }
            return new int[]{(player == aiCon) ? alpha : beta, bestR, bestC};
        }
    }

    // trying to find all valid moves
    private List<int[]> generateMoves() {
        List<int[]> nextMoves = new ArrayList<int[]>();

        // game over
        if(hasWon(aiCon) || hasWon(userCon)) {
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
        score += evaluateLine(0, 0, 0, 1, 0, 2);  // row 0
        score += evaluateLine(1, 0, 1, 1, 1, 2);  // row 1
        score += evaluateLine(2, 0, 2, 1, 2, 2);  // row 2
        score += evaluateLine(0, 0, 1, 0, 2, 0);  // col 0
        score += evaluateLine(0, 1, 1, 1, 2, 1);  // col 1
        score += evaluateLine(0, 2, 1, 2, 2, 2);  // col 2
        score += evaluateLine(0, 0, 1, 1, 2, 2);  // diagonal
        score += evaluateLine(0, 2, 1, 1, 2, 0);
        return score;
    }

    private int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3) {
        int score = 0;

        // First cell
        if (cells[row1][col1].content == aiCon) {
            score = 1;
        }
        else if (cells[row1][col1].content == userCon) {
            score = -1;
        }

        // Second cell
        if (cells[row2][col2].content == aiCon) {
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
        else if (cells[row2][col2].content == userCon) {
            if (score == -1) { //cell 1 is opponents
                score = -10;
            }
            else if (score == 1) { // cell 1 is mine
                return 0;
            }
            else { // cell 1 is empty
                score = -1;
            }
        }

        // Third cell
        if (cells[row3][col3].content == aiCon) {
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
        else if (cells[row3][col3].content == userCon) {
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

    private int[] winPattern = {
            0b111000000, 0b000111000, 0b000000111,
            0b100100100, 0b010010010, 0b001001001,
            0b100010001, 0b001010100  };

    // function checking who won

    private boolean hasWon (Content player) {
        int pattern = 0b000000000 ; // nine bit pattern for nine cells
        for (int rows=0; rows < ROWS; rows++) {
            for (int cols=0; cols < COLUMNS; cols++) {
                if (cells[rows][cols].content == player) {
                    pattern |= (1 << (rows * COLUMNS + cols));
                }
            }
        }
        for (int winningPattern : winPattern) {
            if ((pattern & winningPattern) == winningPattern) {
                return true;
            }
        }
        return false;
    }
}