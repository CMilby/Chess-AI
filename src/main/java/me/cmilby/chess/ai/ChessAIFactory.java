package me.cmilby.chess.ai;

import me.cmilby.chess.ai.ai_types.RandomMoveChessAI;

public class ChessAIFactory {

    public static ChessAI getChessAI ( ChessAIType aiType ) {
        return switch ( aiType ) {
            case NONE -> null;
            case RANDOM_MOVE -> new RandomMoveChessAI ( );
        };
    }
}
