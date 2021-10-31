package me.cmilby.chess.piece;

import me.cmilby.chess.Board;
import me.cmilby.chess.ChessMove;
import me.cmilby.util.Vector2i;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bishop extends ChessPiece {

    public Bishop ( char piece, int x, int y ) {
        super ( piece, x, y );
    }

    public Bishop ( Bishop copy ) {
        super ( copy );
    }

    public Bishop ( ChessPiece copy ) {
        super ( copy );
    }

    @Override
    public Set < Vector2i > getCoveredSquares ( Board board, Color color ) {
        Set < Vector2i > moves = new HashSet <> ( );
        if ( getColor ( ) != color ) {
            return moves;
        }

        // Check up right
        for ( int i = 1; i < 8 - Math.max ( getRank ( ), getFile ( ) ); i++ ) {
            if ( board.isEmpty ( getRank ( ) + i, getFile ( ) + i ) ) {
                moves.add ( new Vector2i ( getRank ( ) + i, getFile ( ) + i ) );
            } else {
                moves.add ( new Vector2i ( getRank ( ) + i, getFile ( ) + i ) );
                break;
            }
        }

        // Check down right
        for ( int i = -1, j = 1; getRank ( ) + i >= 0 && j < 8 - getFile ( ); i--, j++ ) {
            if ( board.isEmpty ( getRank ( ) + i, getFile ( ) + j ) ) {
                moves.add ( new Vector2i ( getRank ( ) + i, getFile ( ) + j ) );
            } else {
                moves.add ( new Vector2i ( getRank ( ) + i, getFile ( ) + j ) );
                break;
            }
        }

        // Check down left
        for ( int i = -1; Math.min ( getRank ( ), getFile ( ) ) + i >= 0; i-- ) {
            if ( board.isEmpty ( getRank ( ) + i, getFile ( ) + i ) ) {
                moves.add ( new Vector2i ( getRank ( ) + i, getFile ( ) + i ) );
            } else {
                moves.add ( new Vector2i ( getRank ( ) + i, getFile ( ) + i ) );
                break;
            }
        }

        // Check up left
        for ( int i = 1, j = -1; getRank ( ) + i < 8 && getFile ( ) + j >= 0; i++, j-- ) {
            if ( board.isEmpty ( getRank ( ) + i, getFile ( ) + j ) ) {
                moves.add ( new Vector2i ( getRank ( ) + i, getFile ( ) + j ) );
            } else {
                moves.add ( new Vector2i ( getRank ( ) + i, getFile ( ) + j ) );
                break;
            }
        }

        return moves;
    }

    @Override
    public List < ChessMove > calculatePossibleMoves ( Board board ) {
        List < ChessMove > moves = new ArrayList <> ( );
        if ( board.getMove ( ) != getColor ( ) ) {
            return moves;
        }

        // Check up right
        for ( int i = 1; i < 8 - Math.max ( getRank ( ), getFile ( ) ); i++ ) {
            if ( board.isEmpty ( getRank ( ) + i, getFile ( ) + i ) ) {
                moves.add ( new ChessMove ( board, this, getRank ( ) + i, getFile ( ) + i ) );
            } else {
                ChessPiece piece = board.getPiece ( getRank ( ) + i, getFile ( ) + i );
                if ( piece.getColor ( ) != getColor ( ) ) {
                    moves.add ( new ChessMove ( board, this, piece, getRank ( ) + i, getFile ( ) + i ) );
                }

                break;
            }
        }

        // Check down right
        for ( int i = -1, j = 1; getRank ( ) + i >= 0 && j < 8 - getFile ( ); i--, j++ ) {
            if ( board.isEmpty ( getRank ( ) + i, getFile ( ) + j ) ) {
                moves.add ( new ChessMove ( board, this, getRank ( ) + i, getFile ( ) + j ) );
            } else {
                ChessPiece piece = board.getPiece ( getRank ( ) + i, getFile ( ) + j );
                if ( piece.getColor ( ) != getColor ( ) ) {
                    moves.add ( new ChessMove ( board, this, piece, getRank ( ) + i, getFile ( ) + j ) );
                }

                break;
            }
        }

        // Check down left
        for ( int i = -1; Math.min ( getRank ( ), getFile ( ) ) + i >= 0; i-- ) {
            if ( board.isEmpty ( getRank ( ) + i, getFile ( ) + i ) ) {
                moves.add ( new ChessMove ( board, this, getRank ( ) + i, getFile ( ) + i ) );
            } else {
                ChessPiece piece = board.getPiece ( getRank ( ) + i, getFile ( ) + i );
                if ( piece.getColor ( ) != getColor ( ) ) {
                    moves.add ( new ChessMove ( board, this, piece, getRank ( ) + i, getFile ( ) + i ) );
                }

                break;
            }
        }

        // Check up left
        for ( int i = 1, j = -1; getRank ( ) + i < 8 && getFile ( ) + j >= 0; i++, j-- ) {
            if ( board.isEmpty ( getRank ( ) + i, getFile ( ) + j ) ) {
                moves.add ( new ChessMove ( board, this, getRank ( ) + i, getFile ( ) + j ) );
            } else {
                ChessPiece piece = board.getPiece ( getRank ( ) + i, getFile ( ) + j );
                if ( piece.getColor ( ) != getColor ( ) ) {
                    moves.add ( new ChessMove ( board, this, piece, getRank ( ) + i, getFile ( ) + j ) );
                }

                break;
            }
        }

        return moves;
    }
}
