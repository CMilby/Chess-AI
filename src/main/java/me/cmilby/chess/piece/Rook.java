package me.cmilby.chess.piece;

import me.cmilby.chess.Board;
import me.cmilby.chess.ChessMove;
import me.cmilby.util.Vector2i;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Rook extends ChessPiece {

    public Rook ( char piece, int x, int y ) {
        super ( piece, x, y );
    }

    public Rook ( Rook copy ) {
        super ( copy );
    }

    public Rook ( ChessPiece copy ) {
        super ( copy );
    }

    @Override
    public Set < Vector2i > getCoveredSquares ( Board board, Color color ) {
        Set < Vector2i > moves = new HashSet <> ( );
        if ( getColor ( ) != color ) {
            return moves;
        }

        // Check files positive
        for ( int i = getRank ( ) + 1; i < 8; i++ ) {
            if ( board.isEmpty ( i, getFile ( ) ) ) {
                moves.add ( new Vector2i ( i, getFile ( ) ) );
            } else {
                moves.add ( new Vector2i ( i, getFile ( ) ) );
                break;
            }
        }

        // Check files negative
        for ( int i = getRank ( ) - 1; i >= 0; i-- ) {
            if ( board.isEmpty ( i, getFile ( ) ) ) {
                moves.add ( new Vector2i ( i, getFile ( ) ) );
            } else {
                moves.add ( new Vector2i ( i, getFile ( ) ) );
                break;
            }
        }

        // Check ranks positive
        for ( int i = getFile ( ) + 1; i < 8; i++ ) {
            if ( board.isEmpty ( getRank ( ), i ) ) {
                moves.add ( new Vector2i ( getRank ( ), i ) );
            } else {
                moves.add ( new Vector2i ( getRank ( ), i ) );
                break;
            }
        }

        // Check ranks positive
        for ( int i = getFile ( ) - 1; i >= 0; i-- ) {
            if ( board.isEmpty ( getRank ( ), i ) ) {
                moves.add ( new Vector2i ( getRank ( ), i ) );
            } else {
                moves.add ( new Vector2i ( getRank ( ), i ) );
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

        // Check files positive
        for ( int i = getRank ( ) + 1; i < 8; i++ ) {
            if ( board.isEmpty ( i, getFile ( ) ) ) {
                moves.add ( new ChessMove ( board, this, i, getFile ( ) ) );
            } else {
                ChessPiece chessPiece = board.getPiece ( i, getFile ( ) );
                if ( chessPiece.getColor ( ) != getColor ( ) ) {
                    moves.add ( new ChessMove ( board, this, chessPiece, i, getFile ( ) ) );
                }
                break;
            }
        }

        // Check files negative
        for ( int i = getRank ( ) - 1; i >= 0; i-- ) {
            if ( board.isEmpty ( i, getFile ( ) ) ) {
                moves.add ( new ChessMove ( board, this, i, getFile ( ) ) );
            } else {
                ChessPiece chessPiece = board.getPiece ( i, getFile ( ) );
                if ( chessPiece.getColor ( ) != getColor ( ) ) {
                    moves.add ( new ChessMove ( board, this, chessPiece, i, getFile ( ) ) );
                }
                break;
            }
        }

        // Check ranks positive
        for ( int i = getFile ( ) + 1; i < 8; i++ ) {
            if ( board.isEmpty ( getRank ( ), i ) ) {
                moves.add ( new ChessMove ( board, this, getRank ( ), i ) );
            } else {
                ChessPiece chessPiece = board.getPiece ( getRank ( ), i );
                if ( chessPiece.getColor ( ) != getColor ( ) ) {
                    moves.add ( new ChessMove ( board, this, chessPiece, getRank ( ), i ) );
                }
                break;
            }
        }

        // Check ranks positive
        for ( int i = getFile ( ) - 1; i >= 0; i-- ) {
            if ( board.isEmpty ( getRank ( ), i ) ) {
                moves.add ( new ChessMove ( board, this, getRank ( ), i ) );
            } else {
                ChessPiece chessPiece = board.getPiece ( getRank ( ), i );
                if ( chessPiece.getColor ( ) != getColor ( ) ) {
                    moves.add ( new ChessMove ( board, this, chessPiece, getRank ( ), i ) );
                }
                break;
            }
        }

        return moves;
    }
}
