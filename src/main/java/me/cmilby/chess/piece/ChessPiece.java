package me.cmilby.chess.piece;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.cmilby.chess.Board;
import me.cmilby.chess.ChessMove;
import me.cmilby.util.Vector2i;

import java.util.List;
import java.util.Set;

@Slf4j
@Data
public abstract class ChessPiece {

    public enum Color {
        BLACK,
        WHITE
    }

    private final char piece;
    private final Color color;
    private int rank;
    private int file;

    public ChessPiece ( char piece, int x, int y ) {
        this.piece = piece;
        this.color = Character.isUpperCase ( piece ) ? Color.WHITE : Color.BLACK;
        this.rank = x;
        this.file = y;
    }

    public ChessPiece ( ChessPiece copy ) {
        this.piece = copy.piece;
        this.color = copy.color;
        this.rank = copy.rank;
        this.file = copy.file;
    }

    public abstract List < ChessMove > calculatePossibleMoves ( Board board );

    public void calculatePossibleMoves ( Board board, List < ChessMove > possibleMoves, Set < Vector2i > coveredSquares, boolean doInCheckCheck ) {

    }

    public abstract Set < Vector2i > getCoveredSquares ( Board board, Color color );

    public char getPiece ( ) {
        return this.piece;
    }

    public Color getColor ( ) {
        return this.color;
    }

    public int getRank ( ) {
        return this.rank;
    }

    public int getFile ( ) {
        return this.file;
    }

    public String getColorString ( ) {
        return this.color.toString ( );
    }

    public ChessPiece clone ( ) {
        try {
            return getClass ( ).getDeclaredConstructor ( ChessPiece.class ).newInstance ( this );
        } catch ( Exception ex ) {
            log.error ( ex.getMessage ( ) );
        }

        return null;
    }

    @Override
    public int hashCode ( ) {
        return Character.hashCode ( this.piece ) ^ this.color.hashCode ( );
    }

    @Override
    public boolean equals ( Object obj ) {
        if ( !( obj instanceof ChessPiece ) ) {
            return false;
        }

        ChessPiece other = ( ChessPiece ) obj;
        return ( this.piece == other.piece && this.color == other.color );
    }

    @Override
    public String toString ( ) {
        return String.format ( "%s%s", this.piece, this.color.toString ( ) );
    }
}
