package me.cmilby.chess.ai;

import me.cmilby.chess.Board;

public abstract class ChessAI {

    private final ChessAIType type;

    public ChessAI ( ChessAIType type ) {
        this.type = type;
    }

    public abstract Board makeMove ( Board board, Integer moveDelay );
}
