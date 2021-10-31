package me.cmilby.chess;

import lombok.Data;
import me.cmilby.chess.piece.ChessPiece;

@Data
public class ChessMove {

    private ChessPiece piece;
    private ChessPiece capturesPiece;

    private int pieceEndRank;
    private int pieceEndFile;

    private int capturesPieceEndRank;
    private int capturesPieceEndFile;

    private boolean canPromote;

    private String resultingFEN;

    public ChessMove ( Board board, ChessPiece piece, int pieceEndRank, int pieceEndFile ) {
        this ( board, piece, pieceEndRank, pieceEndFile, false );
    }

    public ChessMove ( Board board, ChessPiece piece, int pieceEndRank, int pieceEndFile, boolean canPromote ) {
        this.piece = piece;
        this.pieceEndRank = pieceEndRank;
        this.pieceEndFile = pieceEndFile;
        this.canPromote = canPromote;
        this.capturesPiece = null;
        this.capturesPieceEndRank = -1;
        this.capturesPieceEndFile = -1;
        this.resultingFEN = new Board ( board ).playMove ( this ).toFEN ( );
    }

    public ChessMove ( Board board, ChessPiece piece, ChessPiece capturesPiece, int pieceEndRank, int pieceEndFile, boolean canPromote ) {
        this.piece = piece;
        this.capturesPiece = capturesPiece;
        this.pieceEndRank = pieceEndRank;
        this.pieceEndFile = pieceEndFile;
        this.capturesPieceEndRank = -1;
        this.capturesPieceEndFile = -1;
        this.canPromote = canPromote;
        this.resultingFEN = new Board ( board ).playMove ( this ).toFEN ( );
    }

    public ChessMove ( Board board, ChessPiece piece, ChessPiece capturesPiece, int pieceEndRank, int pieceEndFile ) {
        this ( board, piece, capturesPiece, pieceEndRank, pieceEndFile, -1, -1 );
    }

    public ChessMove ( Board board, ChessPiece piece, ChessPiece capturesPiece, int pieceEndRank, int pieceEndFile, int capturesPieceEndRank, int capturesPieceEndFile ) {
        this.piece = piece;
        this.capturesPiece = capturesPiece;
        this.pieceEndRank = pieceEndRank;
        this.pieceEndFile = pieceEndFile;
        this.capturesPieceEndRank = capturesPieceEndRank;
        this.capturesPieceEndFile = capturesPieceEndFile;
        this.canPromote = false;
        this.resultingFEN = new Board ( board ).playMove ( this ).toFEN ( );
    }
}
