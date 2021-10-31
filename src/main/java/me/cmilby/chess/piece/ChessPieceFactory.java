package me.cmilby.chess.piece;

public class ChessPieceFactory {

    public static ChessPiece getChessPiece ( char piece, int rank, int file ) {
        return switch ( piece ) {
            case 'r', 'R' -> new Rook ( piece, rank, file );
            case 'p', 'P' -> new Pawn ( piece, rank, file );
            case 'n', 'N' -> new Knight ( piece, rank, file );
            case 'b', 'B' -> new Bishop ( piece, rank, file );
            case 'q', 'Q' -> new Queen ( piece, rank, file );
            case 'k', 'K' -> new King ( piece, rank, file );
            default -> null;
        };
    }
}
