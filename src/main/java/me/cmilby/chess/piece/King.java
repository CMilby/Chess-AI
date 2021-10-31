package me.cmilby.chess.piece;

import me.cmilby.chess.Board;
import me.cmilby.chess.ChessMove;
import me.cmilby.util.Vector2i;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class King extends ChessPiece {

    public King ( char piece, int x, int y ) {
        super ( piece, x, y );
    }

    public King ( King copy ) {
        super ( copy );
    }

    public King ( ChessPiece copy ) {
        super ( copy );
    }

    @Override
    public Set < Vector2i > getCoveredSquares ( Board board, Color color ) {
        Set < Vector2i > moves = new HashSet <> ( );
        if ( getColor ( ) != color ) {
            return moves;
        }

        if ( getRank ( ) + 1 < 8 && getFile ( ) + 1 < 8 ) {
            moves.add ( new Vector2i ( getRank ( ) + 1, getFile ( ) + 1 ) );
        }

        if ( getRank ( ) + 1 < 8 ) {
            moves.add ( new Vector2i ( getRank ( ) + 1, getFile ( ) ) );
        }

        if ( getRank ( ) + 1 < 8 && getFile ( ) - 1 >= 0 ) {
            moves.add ( new Vector2i ( getRank ( ) + 1, getFile ( ) - 1 ) );
        }

        if ( getRank ( ) - 1 >= 0 && getFile ( ) + 1 < 8 ) {
            moves.add ( new Vector2i ( getRank ( ) - 1, getFile ( ) + 1 ) );
        }

        if ( getRank ( ) - 1 >= 0 ) {
            moves.add ( new Vector2i ( getRank ( ) - 1, getFile ( ) ) );
        }

        if ( getRank ( ) - 1 >= 0 && getFile ( ) - 1 >= 0 ) {
            moves.add ( new Vector2i ( getRank ( ) - 1, getFile ( ) - 1 ) );
        }

        if ( getFile ( ) + 1 < 8 ) {
            moves.add ( new Vector2i ( getRank ( ), getFile ( ) + 1 ) );
        }

        if ( getFile ( ) - 1 >= 0 ) {
            moves.add ( new Vector2i ( getRank ( ), getFile ( ) - 1 ) );
        }

        return moves;
    }

    @Override
    public void calculatePossibleMoves ( Board board, List < ChessMove > possibleMoves, Set < Vector2i > coveredSquares, boolean doInCheckCheck ) {
        if ( board.getMove ( ) != getColor ( ) ) {
            return;
        }

        if ( isValidSquare ( board, coveredSquares, 1, 1 ) ) {
            possibleMoves.add ( new ChessMove ( board, this, getRank ( ) + 1, getFile ( ) + 1 ) );
        }

        if ( isValidSquare ( board, coveredSquares, 1, 0 ) ) {
            possibleMoves.add ( new ChessMove ( board, this, getRank ( ) + 1, getFile ( ) ) );
        }

        if ( isValidSquare ( board, coveredSquares, 1, -1 ) ) {
            possibleMoves.add ( new ChessMove ( board, this, getRank ( ) + 1, getFile ( ) - 1 ) );
        }

        if ( isValidSquare ( board, coveredSquares, -1, 1 ) ) {
            possibleMoves.add ( new ChessMove ( board, this, getRank ( ) - 1, getFile ( ) + 1 ) );
        }

        if ( isValidSquare ( board, coveredSquares, -1, 0 ) ) {
            possibleMoves.add ( new ChessMove ( board, this, getRank ( ) - 1, getFile ( ) ) );
        }

        if ( isValidSquare ( board, coveredSquares, -1, -1 ) ) {
            possibleMoves.add ( new ChessMove ( board, this, getRank ( ) - 1, getFile ( ) - 1 ) );
        }

        if ( isValidSquare ( board, coveredSquares, 0, -1 ) ) {
            possibleMoves.add ( new ChessMove ( board, this, getRank ( ), getFile ( ) - 1 ) );
        }

        if ( isValidSquare ( board, coveredSquares, 0, 1 ) ) {
            possibleMoves.add ( new ChessMove ( board, this, getRank ( ), getFile ( ) + 1 ) );
        }

        if ( board.canCastleKingside ( this, coveredSquares ) ) {
            possibleMoves.add ( new ChessMove ( board, this, board.getPiece ( getRank ( ), 7 ), getRank ( ), 6, getRank ( ), 5 ) );
        }

        if ( board.canCastleQueenside ( this, coveredSquares ) ) {
            possibleMoves.add ( new ChessMove ( board, this, board.getPiece ( getRank ( ), 0 ), getRank ( ), 2, getRank ( ), 3 ) );
        }

        if ( doInCheckCheck ) {
            for ( int i = possibleMoves.size ( ) - 1; i >= 0; i-- ) {
                ChessMove move = possibleMoves.get ( i );
                Board movedBoard = new Board ( board ).playMove ( move );
                King king = ( King ) movedBoard.findPiece ( getPiece ( ) );

                if ( king == null ) {
                    possibleMoves.remove ( move );
                    continue;
                }

                if ( king.isInCheck ( movedBoard.getCoveredSquares ( getColor ( ) == Color.WHITE ? Color.BLACK : Color.WHITE ) ) ) {
                    possibleMoves.remove ( move );
                }
            }
        }
    }

    @Override
    public List < ChessMove > calculatePossibleMoves ( Board board ) {
        return new ArrayList <> ( );
    }

    private boolean isValidSquare ( Board board, Set < Vector2i > coveredSquares, int rank, int file ) {
        return ( board.isEmpty ( getRank ( ) + rank, getFile ( ) + file ) ||
                ( board.getPiece ( getRank ( ) + rank, getFile ( ) + file ) != null &&
                        board.getPiece ( getRank ( ) + rank, getFile ( ) + file ).getColor ( ) != getColor ( ) ) ) &&
                !isSquareCoveredByAnotherMove ( coveredSquares, getRank ( ) + rank, getFile ( ) + file );
    }

    private boolean isSquareCoveredByAnotherMove ( Set < Vector2i > coveredSquares, int rank, int file ) {
        return coveredSquares.stream ( ).anyMatch ( square -> square.getRank ( ) == rank && square.getFile ( ) == file );
    }

    public boolean isInCheck ( Set < Vector2i > coveredSquares ) {
        return isSquareCoveredByAnotherMove ( coveredSquares, getRank ( ), getFile ( ) );
    }
}
