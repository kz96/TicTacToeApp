package com.kornelzielinski.AI;

import com.kornelzielinski.Board;
import com.kornelzielinski.Content;

public class AIPlayerStrategy extends AIPlayer {
    private int[][] possibleMoves = {
            {1, 1}, {0, 0}, {0, 2}, {2, 0}, {2, 2},
            {0, 1}, {1, 0}, {1, 2}, {2, 1}};

    public AIPlayerStrategy(Board board) {
        super(board);
    }

    @Override
    public int[] move() {
        for (int[] move : possibleMoves) {
            if (cells[move[0]][move[1]].content == Content.EMPTY) {
                return move;
            }
        }
        assert false : "No empty cell ?!";
        return null;
    }
}
