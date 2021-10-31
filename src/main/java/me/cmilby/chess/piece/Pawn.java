package me.cmilby.chess.piece;

import me.cmilby.chess.Board;
import me.cmilby.chess.ChessMove;
import me.cmilby.util.Vector2i;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pawn extends ChessPiece {

    public Pawn ( char piece, int x, int y ) {
        super ( piece, x, y );
    }

    public Pawn ( Pawn copy ) {
        super ( copy );
    }

    public Pawn ( ChessPiece copy ) {
        super ( copy );
    }

    @Override
    public Set < Vector2i > getCoveredSquares ( Board board, Color color ) {
        Set < Vector2i > moves = new HashSet <> ( );
        if ( getColor ( ) != color ) {
            return moves;
        }

        if ( getColor ( ).equals ( Color.BLACK ) ) {
            if ( getRank ( ) - 1 >= 0 && getFile ( ) + 1 < 8 ) {
                moves.add ( new Vector2i ( getRank ( ) - 1, getFile ( ) + 1 ) );
            }

            if ( getRank ( ) - 1 >= 0 && getFile ( ) - 1 >= 0 ) {
                moves.add ( new Vector2i ( getRank ( ) - 1, getFile ( ) - 1 ) );
            }
        } else {
            if ( getRank ( ) + 1 < 8 && getFile ( ) + 1 < 8 ) {
                moves.add ( new Vector2i ( getRank ( ) + 1, getFile ( ) + 1 ) );
            }

            if ( getRank ( ) + 1 < 8 && getFile ( ) - 1 >= 0 ) {
                moves.add ( new Vector2i ( getRank ( ) + 1, getFile ( ) - 1 ) );
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

        if ( getColor ( ).equals ( Color.BLACK ) ) {
            // Check forward moves
            if ( board.isEmpty ( getRank ( ) - 1, getFile ( ) ) ) {
                moves.add ( new ChessMove ( board, this, getRank ( ) - 1, getFile ( ), getRank ( ) - 1 == 0 ) );
                if ( getRank ( ) == 6 && board.isEmpty ( getRank ( ) - 2, getFile ( ) ) ) {
                    moves.add ( new ChessMove ( board, this, getRank ( ) - 2, getFile ( ) ) );
                }
            }

            // Check for captures
            if ( !board.isEmpty ( getRank ( ) - 1, getFile ( ) + 1 ) && board.getPiece ( getRank ( ) - 1, getFile ( ) + 1 ) != null ) {
                if ( board.getPiece ( getRank ( ) - 1, getFile ( ) + 1 ).getColor ( ) != getColor ( ) ) {
                    moves.add ( new ChessMove ( board, this, board.getPiece ( getRank ( ) - 1, getFile ( ) + 1 ),
                            getRank ( ) - 1, getFile ( ) + 1, getRank ( ) - 1 == 0 ) );
                }
            }

            if ( !board.isEmpty ( getRank ( ) - 1, getFile ( ) - 1 ) && board.getPiece ( getRank ( ) - 1, getFile ( ) - 1 ) != null ) {
                if ( board.getPiece ( getRank ( ) - 1, getFile ( ) - 1 ).getColor ( ) != getColor ( ) ) {
                    moves.add ( new ChessMove ( board, this, board.getPiece ( getRank ( ) - 1, getFile ( ) - 1 ),
                            getRank ( ) - 1, getFile ( ) - 1, getRank ( ) - 1 == 0 ) );
                }
            }

            // Check for en passant
            if ( board.getEnPassantFile ( ) >= 0 && getRank ( ) == 3 && ( getFile ( ) - 1 == board.getEnPassantFile ( ) || getFile ( ) + 1 == board.getEnPassantFile ( ) ) ) {
                moves.add ( new ChessMove ( board, this, board.getPiece ( 3, board.getEnPassantFile ( ) ), getRank ( ) - 1, board.getEnPassantFile ( ) ) );
            }
        } else if ( getColor ( ).equals ( Color.WHITE ) ) {
            // check forward moves
            if ( board.isEmpty ( getRank ( ) + 1, getFile ( ) ) ) {
                moves.add ( new ChessMove ( board, this, getRank ( ) + 1, getFile ( ), getRank ( ) + 1 == 7 ) );
                if ( getRank ( ) == 1 && board.isEmpty ( getRank ( ) + 2, getFile ( ) ) ) {
                    moves.add ( new ChessMove ( board, this, getRank ( ) + 2, getFile ( ) ) );
                }
            }

            // Check captures
            if ( !board.isEmpty ( getRank ( ) + 1, getFile ( ) + 1 ) && board.getPiece ( getRank ( ) + 1, getFile ( ) + 1 ) != null ) {
                if ( board.getPiece ( getRank ( ) + 1, getFile ( ) + 1 ).getColor ( ) != getColor ( ) ) {
                    moves.add ( new ChessMove ( board, this, board.getPiece ( getRank ( ) + 1, getFile ( ) + 1 ),
                            getRank ( ) + 1, getFile ( ) + 1, getRank ( ) + 1 == 7 ) );
                }
            }

            if ( !board.isEmpty ( getRank ( ) + 1, getFile ( ) - 1 ) && board.getPiece ( getRank ( ) + 1, getFile ( ) - 1 ) != null ) {
                if ( board.getPiece ( getRank ( ) + 1, getFile ( ) - 1 ).getColor ( ) != getColor ( ) ) {
                    moves.add ( new ChessMove ( board, this, board.getPiece ( getRank ( ) + 1, getFile ( ) - 1 ),
                            getRank ( ) + 1, getFile ( ) - 1, getRank ( ) + 1 == 7 ) );
                }
            }

            // Check for en passant
            if ( board.getEnPassantFile ( ) >= 0 && getRank ( ) == 4 && ( getFile ( ) - 1 == board.getEnPassantFile ( ) || getFile ( ) + 1 == board.getEnPassantFile ( ) ) ) {
                moves.add ( new ChessMove ( board, this, board.getPiece ( 4, board.getEnPassantFile ( ) ), getRank ( ) + 1, board.getEnPassantFile ( ) ) );
            }
        }

        return moves;
    }
}
