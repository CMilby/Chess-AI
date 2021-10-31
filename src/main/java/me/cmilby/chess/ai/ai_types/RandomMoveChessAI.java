package me.cmilby.chess.ai.ai_types;

import me.cmilby.chess.Board;
import me.cmilby.chess.ChessMove;
import me.cmilby.chess.ai.ChessAI;
import me.cmilby.chess.ai.ChessAIType;

import java.util.List;
import java.util.Random;

public class RandomMoveChessAI extends ChessAI {

    public RandomMoveChessAI ( ) {
        super ( ChessAIType.RANDOM_MOVE );
    }

    @Override
    public Board makeMove ( Board board, Integer moveDelay ) {
        List < ChessMove > possibleMoves = board.getPossibleMoves ( );

        try {
            Thread.sleep ( moveDelay );
        } catch ( Exception ignored ) {
        }

        if ( possibleMoves.size ( ) == 0 ) {
            return board;
        }
        
        return new Board ( board ).playMove ( possibleMoves.get ( new Random ( ).nextInt ( possibleMoves.size ( ) ) ) );
    }
}
