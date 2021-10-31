package me.cmilby.graphql.data_fetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.Value;
import me.cmilby.chess.Board;
import me.cmilby.chess.ChessMove;
import me.cmilby.chess.ai.ChessAIFactory;
import me.cmilby.chess.ai.ChessAIType;
import me.cmilby.chess.piece.ChessPiece;
import me.cmilby.graphql.types.ChessPosition;

import java.util.List;

import static me.cmilby.graphql.GraphQLUtil.getArguments;

public class ChessDataFetcher {

    @Value
    private static class MoveArgs {
        String fen;
    }

    public static DataFetcher < List < ChessMove > > getPossibleMoves = ( DataFetchingEnvironment env ) -> {
        MoveArgs args = getArguments ( env, MoveArgs.class );
        Board board = new Board ( args.getFen ( ) );
        return board.getPossibleMoves ( );
    };

    @Value
    private static class GetPositionArgs {
        String fen;
        ChessAIType whiteAI;
        ChessAIType blackAI;
        Integer maxMoveDepth;
        Integer moveDelay;
    }

    public static DataFetcher < ChessPosition > getPosition = ( DataFetchingEnvironment env ) -> {
        GetPositionArgs args = getArguments ( env, GetPositionArgs.class );
        Board board = new Board ( args.getFen ( ) );

        if ( board.getMove ( ) == ChessPiece.Color.WHITE && args.getWhiteAI ( ) != ChessAIType.NONE ) {
            board = ChessAIFactory.getChessAI ( args.getWhiteAI ( ) ).makeMove ( board, args.getMoveDelay ( ) );
        } else if ( board.getMove ( ) == ChessPiece.Color.BLACK && args.getBlackAI ( ) != ChessAIType.NONE ) {
            board = ChessAIFactory.getChessAI ( args.getBlackAI ( ) ).makeMove ( board, args.getMoveDelay ( ) );
        }

        return ChessPosition.builder ( )
                .FEN ( board.toFEN ( ) )
                .possibleMoves ( board.getPossibleMoves ( ) )
                .isInCheckmate ( board.isInCheckmate ( ) )
                .isStalemate ( board.isInStalemate ( ) )
                .build ( );
    };
}
