package me.cmilby.chess.piece;

import me.cmilby.chess.Board;
import me.cmilby.chess.ChessMove;
import me.cmilby.util.Vector2i;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Knight extends ChessPiece {

    public Knight ( char piece, int x, int y ) {
        super ( piece, x, y );
    }

    public Knight ( Knight copy ) {
        super ( copy );
    }

    public Knight ( ChessPiece copy ) {
        super ( copy );
    }

    @Override
    public Set < Vector2i > getCoveredSquares ( Board board, Color color ) {
        Set < Vector2i > moves = new HashSet <> ( );
        if ( getColor ( ) != color ) {
            return moves;
        }

        // check up & right moves
        if ( getRank ( ) + 2 < 8 && getFile ( ) + 1 < 8 ) {
            moves.add ( new Vector2i ( getRank ( ) + 2, getFile ( ) + 1 ) );
        }

        if ( getRank ( ) + 1 < 8 && getFile ( ) + 2 < 8 ) {
            moves.add ( new Vector2i ( getRank ( ) + 1, getFile ( ) + 2 ) );
        }

        // Check right & down moves
        if ( getRank ( ) + 2 < 8 && getFile ( ) - 1 >= 0 ) {
            moves.add ( new Vector2i ( getRank ( ) + 2, getFile ( ) - 1 ) );
        }

        if ( getRank ( ) + 1 < 8 && getFile ( ) - 2 >= 0 ) {
            moves.add ( new Vector2i ( getRank ( ) + 1, getFile ( ) - 2 ) );
        }

        // check down & left moves
        if ( getRank ( ) - 2 >= 0 && getFile ( ) - 1 >= 0 ) {
            moves.add ( new Vector2i ( getRank ( ) - 2, getFile ( ) - 1 ) );
        }

        if ( getRank ( ) - 1 >= 0 && getFile ( ) - 2 >= 0 ) {
            moves.add ( new Vector2i ( getRank ( ) - 1, getFile ( ) - 2 ) );
        }

        // check up & left moves
        if ( getRank ( ) - 2 >= 0 && getFile ( ) + 1 < 8 ) {
            moves.add ( new Vector2i ( getRank ( ) - 2, getFile ( ) + 1 ) );
        }

        if ( getRank ( ) - 1 >= 0 && getFile ( ) + 2 < 8 ) {
            moves.add ( new Vector2i ( getRank ( ) - 1, getFile ( ) + 2 ) );
        }

        return moves;
    }

    @Override
    public List < ChessMove > calculatePossibleMoves ( Board board ) {
        List < ChessMove > moves = new ArrayList <> ( );
        if ( board.getMove ( ) != getColor ( ) ) {
            return moves;
        }

        // check up & right moves
        if ( getRank ( ) + 2 < 8 && getFile ( ) + 1 < 8 ) {
            ChessPiece piece = board.getPiece ( getRank ( ) + 2, getFile ( ) + 1 );
            if ( piece == null || piece.getColor ( ) != getColor ( ) ) {
                moves.add ( new ChessMove ( board, this, piece, getRank ( ) + 2, getFile ( ) + 1 ) );
            }
        }

        if ( getRank ( ) + 1 < 8 && getFile ( ) + 2 < 8 ) {
            ChessPiece piece = board.getPiece ( getRank ( ) + 1, getFile ( ) + 2 );
            if ( piece == null || piece.getColor ( ) != getColor ( ) ) {
                moves.add ( new ChessMove ( board, this, piece, getRank ( ) + 1, getFile ( ) + 2 ) );
            }
        }

        // Check right & down moves
        if ( getRank ( ) + 2 < 8 && getFile ( ) - 1 >= 0 ) {
            ChessPiece piece = board.getPiece ( getRank ( ) + 2, getFile ( ) - 1 );
            if ( piece == null || piece.getColor ( ) != getColor ( ) ) {
                moves.add ( new ChessMove ( board, this, piece, getRank ( ) + 2, getFile ( ) - 1 ) );
            }
        }

        if ( getRank ( ) + 1 < 8 && getFile ( ) - 2 >= 0 ) {
            ChessPiece piece = board.getPiece ( getRank ( ) + 1, getFile ( ) - 2 );
            if ( piece == null || piece.getColor ( ) != getColor ( ) ) {
                moves.add ( new ChessMove ( board, this, piece, getRank ( ) + 1, getFile ( ) - 2 ) );
            }
        }

        // check down & left moves
        if ( getRank ( ) - 2 >= 0 && getFile ( ) - 1 >= 0 ) {
            ChessPiece piece = board.getPiece ( getRank ( ) - 2, getFile ( ) - 1 );
            if ( piece == null || piece.getColor ( ) != getColor ( ) ) {
                moves.add ( new ChessMove ( board, this, piece, getRank ( ) - 2, getFile ( ) - 1 ) );
            }
        }

        if ( getRank ( ) - 1 >= 0 && getFile ( ) - 2 >= 0 ) {
            ChessPiece piece = board.getPiece ( getRank ( ) - 1, getFile ( ) - 2 );
            if ( piece == null || piece.getColor ( ) != getColor ( ) ) {
                moves.add ( new ChessMove ( board, this, piece, getRank ( ) - 1, getFile ( ) - 2 ) );
            }
        }

        // check up & left moves
        if ( getRank ( ) - 2 >= 0 && getFile ( ) + 1 < 8 ) {
            ChessPiece piece = board.getPiece ( getRank ( ) - 2, getFile ( ) + 1 );
            if ( piece == null || piece.getColor ( ) != getColor ( ) ) {
                moves.add ( new ChessMove ( board, this, piece, getRank ( ) - 2, getFile ( ) + 1 ) );
            }
        }

        if ( getRank ( ) - 1 >= 0 && getFile ( ) + 2 < 8 ) {
            ChessPiece piece = board.getPiece ( getRank ( ) - 1, getFile ( ) + 2 );
            if ( piece == null || piece.getColor ( ) != getColor ( ) ) {
                moves.add ( new ChessMove ( board, this, piece, getRank ( ) - 1, getFile ( ) + 2 ) );
            }
        }

        return moves;
    }
}
