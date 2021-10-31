package me.cmilby.chess;

import me.cmilby.chess.piece.ChessPiece;
import me.cmilby.chess.piece.ChessPieceFactory;
import me.cmilby.chess.piece.King;
import me.cmilby.chess.piece.Queen;
import me.cmilby.util.Vector2i;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {

    public static final String FEN_STARTING_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    private ChessPiece[][] board;
    private int enPassantFile;
    private int enPassantRank;
    private int halfMove;
    private int fullMove;
    private ChessPiece.Color move;
    private String castle;
    private final String fen;

    private List < ChessMove > moves;
    private Set < Vector2i > coveredSquares;

    public Board ( ) {
        this ( FEN_STARTING_POSITION );
    }

    public Board ( String fen ) {
        this.fen = fen;
        loadFEN ( fen );
    }

    public Board ( Board copy ) {
        this.fen = copy.fen;
        loadFEN ( copy.fen );
    }

    public void setBoard ( String fen ) {
        loadFEN ( fen );
    }

    public List < ChessMove > getPossibleMoves ( ) {
        return getPossibleMoves ( true );
    }

    public Set < Vector2i > getCoveredSquares ( ) {
        return getCoveredSquares ( getMove ( ) == ChessPiece.Color.WHITE ? ChessPiece.Color.BLACK : ChessPiece.Color.WHITE );
    }

    public Set < Vector2i > getCoveredSquares ( ChessPiece.Color byColor ) {
        if ( coveredSquares == null ) {
            Set < Vector2i > coveredSquares = new HashSet <> ( );
            for ( int rank = 0; rank < 8; rank++ ) {
                for ( int file = 0; file < 8; file++ ) {
                    ChessPiece piece = getPiece ( rank, file );
                    if ( piece == null ) {
                        continue;
                    }

                    coveredSquares.addAll ( piece.getCoveredSquares ( this, byColor ) );
                }
            }

            this.coveredSquares = coveredSquares;
        }

        return coveredSquares;
    }

    public List < ChessMove > getPossibleMoves ( boolean doInCheckCheck ) {
        if ( moves == null ) {
            List < ChessMove > possibleMoves = new ArrayList <> ( );
            for ( int rank = 0; rank < 8; rank++ ) {
                for ( int file = 0; file < 8; file++ ) {
                    ChessPiece piece = getPiece ( rank, file );
                    if ( piece == null ) {
                        continue;
                    }

                    possibleMoves.addAll ( piece.calculatePossibleMoves ( this ) );
                }
            }

            findPiece ( getMove ( ) == ChessPiece.Color.WHITE ? 'K' : 'k' ).calculatePossibleMoves ( this, possibleMoves, getCoveredSquares ( ), doInCheckCheck );
            moves = possibleMoves;
        }

        return moves;
    }

    public boolean isInCheckmate ( ) {
        return ( ( King ) findPiece ( getMove ( ) == ChessPiece.Color.WHITE ? 'K' : 'k' ) ).isInCheck ( coveredSquares ) && moves.size ( ) == 0;
    }

    public boolean isInStalemate ( ) {
        return ( ( !( ( King ) findPiece ( getMove ( ) == ChessPiece.Color.WHITE ? 'K' : 'k' ) ).isInCheck ( coveredSquares ) ) && moves.size ( ) == 0 ) || halfMove == 50;
    }

    public ChessPiece findPiece ( char piece ) {
        for ( int rank = 0; rank < 8; rank++ ) {
            for ( int file = 0; file < 8; file++ ) {
                ChessPiece chessPiece = getPiece ( rank, file );
                if ( chessPiece != null && chessPiece.getPiece ( ) == piece ) {
                    chessPiece = chessPiece.clone ( );
                    chessPiece.setRank ( rank );
                    chessPiece.setFile ( file );
                    return chessPiece;
                }
            }
        }

        return null;
    }

    public Board playMove ( ChessMove move ) {
        // Delete the pieces old position
        board[ move.getPiece ( ).getRank ( ) ][ move.getPiece ( ).getFile ( ) ] = null;
        board[ move.getPieceEndRank ( ) ][ move.getPieceEndFile ( ) ] = move.getPiece ( );

        // Automatically promote pawns to queens
        if ( move.isCanPromote ( ) ) {
            board[ move.getPieceEndRank ( ) ][ move.getPieceEndFile ( ) ] = new Queen ( move.getPiece ( ).getColor ( ) == ChessPiece.Color.WHITE ? 'Q' : 'q',
                    move.getPieceEndRank ( ), move.getPieceEndFile ( ) );
        }

        // Castling
        if ( move.getCapturesPieceEndRank ( ) != -1 && move.getCapturesPieceEndFile ( ) != -1 ) {
            board[ move.getCapturesPiece ( ).getRank ( ) ][ move.getCapturesPiece ( ).getFile ( ) ] = null;
            board[ move.getCapturesPieceEndRank ( ) ][ move.getCapturesPieceEndFile ( ) ] = move.getCapturesPiece ( );
        }

        // Check if lost castling privilege
        if ( move.getPiece ( ).getPiece ( ) == 'r' || move.getPiece ( ).getPiece ( ) == 'R' ) {
            if ( move.getPiece ( ).getRank ( ) == 0 && move.getPiece ( ).getFile ( ) == 0 && castle.contains ( "Q" ) ) {
                castle = castle.replace ( "Q", "" );
            }

            if ( move.getPiece ( ).getRank ( ) == 7 && move.getPiece ( ).getFile ( ) == 0 && castle.contains ( "q" ) ) {
                castle = castle.replace ( "q", "" );
            }

            if ( move.getPiece ( ).getRank ( ) == 0 && move.getPiece ( ).getFile ( ) == 7 && castle.contains ( "K" ) ) {
                castle = castle.replace ( "K", "" );
            }

            if ( move.getPiece ( ).getRank ( ) == 7 && move.getPiece ( ).getFile ( ) == 7 && castle.contains ( "k" ) ) {
                castle = castle.replace ( "k", "" );
            }
        } else if ( move.getCapturesPiece ( ) != null ) {
            if ( move.getCapturesPiece ( ).getPiece ( ) == 'R' ) {
                if ( move.getCapturesPieceEndRank ( ) == 0 && move.getCapturesPieceEndFile ( ) == 7 ) {
                    castle = castle.replace ( "K", "" );
                }

                if ( move.getCapturesPieceEndRank ( ) == 0 && move.getCapturesPieceEndFile ( ) == 0 ) {
                    castle = castle.replace ( "Q", "" );
                }
            } else if ( move.getCapturesPiece ( ).getPiece ( ) == 'r' ) {
                if ( move.getCapturesPieceEndRank ( ) == 7 && move.getCapturesPieceEndFile ( ) == 7 ) {
                    castle = castle.replace ( "k", "" );
                }

                if ( move.getCapturesPieceEndRank ( ) == 7 && move.getCapturesPieceEndFile ( ) == 0 ) {
                    castle = castle.replace ( "q", "" );
                }
            }
        }

        if ( move.getPiece ( ).getPiece ( ) == 'k' ) {
            castle = castle.replace ( "k", "" ).replace ( "q", "" );
        }

        if ( move.getPiece ( ).getPiece ( ) == 'K' ) {
            castle = castle.replace ( "K", "" ).replace ( "Q", "" );
        }

        if ( castle.isEmpty ( ) ) {
            castle = "-";
        }

        // Capture en passant
        if ( move.getPiece ( ).getPiece ( ) == 'P' && move.getPiece ( ).getRank ( ) == 4 && move.getCapturesPiece ( ) != null &&
                move.getCapturesPiece ( ).getPiece ( ) == 'p' && move.getCapturesPiece ( ).getRank ( ) == 4 ) {
            board[ move.getCapturesPiece ( ).getRank ( ) ][ move.getCapturesPiece ( ).getFile ( ) ] = null;
        }

        if ( move.getPiece ( ).getPiece ( ) == 'p' && move.getPiece ( ).getRank ( ) == 3 && move.getCapturesPiece ( ) != null &&
                move.getCapturesPiece ( ).getPiece ( ) == 'P' && move.getCapturesPiece ( ).getRank ( ) == 3 ) {
            board[ move.getCapturesPiece ( ).getRank ( ) ][ move.getCapturesPiece ( ).getFile ( ) ] = null;
        }

        // Check if en passant eligible
        enPassantFile = -1;
        enPassantRank = -1;

        if ( move.getPiece ( ).getPiece ( ) == 'P' ) {
            if ( move.getPiece ( ).getRank ( ) == 1 && move.getPieceEndRank ( ) == 3 ) {
                enPassantFile = move.getPieceEndFile ( );
                enPassantRank = 2;
            }
        }

        if ( move.getPiece ( ).getPiece ( ) == 'p' ) {
            if ( move.getPiece ( ).getRank ( ) == 6 && move.getPieceEndRank ( ) == 4 ) {
                enPassantFile = move.getPieceEndFile ( );
                enPassantRank = 5;
            }
        }

        this.move = move.getPiece ( ).getColor ( ) == ChessPiece.Color.WHITE ? ChessPiece.Color.BLACK : ChessPiece.Color.WHITE;

        if ( getMove ( ) == ChessPiece.Color.WHITE ) {
            fullMove++;
        }

        // If no captures or pawn moves, increment half move
        if ( move.getCapturesPiece ( ) == null || move.getPiece ( ).getPiece ( ) != 'p' || move.getPiece ( ).getPiece ( ) != 'P' ) {
            halfMove++;
        }

        if ( move.getCapturesPiece ( ) != null || move.getPiece ( ).getPiece ( ) == 'p' || move.getPiece ( ).getPiece ( ) == 'P' ) {
            halfMove = 0;
        }

        return this;
    }

    public ChessPiece getPiece ( int rank, int file ) {
        if ( rank < 0 || rank > 7 || file < 0 || file > 7 ) {
            return null;
        }

        return this.board[ rank ][ file ];
    }

    public boolean isEmpty ( int rank, int file ) {
        if ( rank < 0 || rank > 7 || file < 0 || file > 7 ) {
            return false;
        }

        return this.board[ rank ][ file ] == null;
    }

    public int getEnPassantFile ( ) {
        return enPassantFile;
    }

    public boolean canCastleKingside ( ChessPiece chessPiece, Set < Vector2i > coveredSquares ) {
        return castle.contains ( chessPiece.getColor ( ) == ChessPiece.Color.WHITE ? "K" : "k" ) &&
                getPiece ( chessPiece.getRank ( ), 5 ) == null && getPiece ( chessPiece.getRank ( ), 6 ) == null &&
                !coveredSquares.contains ( new Vector2i ( chessPiece.getRank ( ), 5 ) ) &&
                !coveredSquares.contains ( new Vector2i ( chessPiece.getRank ( ), 6 ) );
    }

    public boolean canCastleQueenside ( ChessPiece chessPiece, Set < Vector2i > coveredSquares ) {
        return castle.contains ( chessPiece.getColor ( ) == ChessPiece.Color.WHITE ? "Q" : "q" ) &&
                getPiece ( chessPiece.getRank ( ), 1 ) == null && getPiece ( chessPiece.getRank ( ), 2 ) == null &&
                getPiece ( chessPiece.getRank ( ), 3 ) == null &&
                !coveredSquares.contains ( new Vector2i ( chessPiece.getRank ( ), 1 ) ) &&
                !coveredSquares.contains ( new Vector2i ( chessPiece.getRank ( ), 2 ) ) &&
                !coveredSquares.contains ( new Vector2i ( chessPiece.getRank ( ), 3 ) );
    }

    public ChessPiece.Color getMove ( ) {
        return this.move;
    }

    public String toFEN ( ) {
        StringBuilder fen = new StringBuilder ( );

        for ( int rank = 7; rank >= 0; rank-- ) {
            int numEmpty = 0;
            for ( int file = 0; file < 8; file++ ) {
                ChessPiece piece = board[ rank ][ file ];
                if ( piece == null ) {
                    numEmpty++;
                } else {
                    if ( numEmpty > 0 ) {
                        fen.append ( numEmpty );
                        numEmpty = 0;
                    }

                    fen.append ( piece.getPiece ( ) );
                }
            }

            if ( numEmpty > 0 ) {
                fen.append ( numEmpty );
            }

            if ( rank != 0 ) {
                fen.append ( "/" );
            }
        }

        fen.append ( String.format ( " %s ", getMove ( ) == ChessPiece.Color.WHITE ? "w" : "b" ) );
        fen.append ( castle );
        fen.append ( enPassantFile < 0 ? " - " : String.format ( " %s%d ", ( char ) ( enPassantFile + 97 ), enPassantRank ) );
        fen.append ( String.format ( "%d %d", halfMove, fullMove ) );

        return fen.toString ( );
    }

    private void loadFEN ( String fen ) {
        this.board = new ChessPiece[ 8 ][ 8 ];

        String[] fenTokens = fen.split ( " " );
        this.move = fenTokens[ 1 ].equals ( "w" ) ? ChessPiece.Color.WHITE : ChessPiece.Color.BLACK;
        this.castle = fenTokens[ 2 ];
        this.enPassantFile = ( ( int ) fenTokens[ 3 ].charAt ( 0 ) ) - 97;
        if ( enPassantFile >= 0 ) {
            this.enPassantRank = ( ( int ) fenTokens[ 3 ].charAt ( 1 ) ) - 48;
        }
        this.halfMove = Integer.parseInt ( fenTokens[ 4 ] );
        this.fullMove = Integer.parseInt ( fenTokens[ 5 ] );

        String[] boardTokens = fenTokens[ 0 ].split ( "/" );
        for ( int i = 0; i < 8; i++ ) {
            String rank = boardTokens[ i ];
            int index = 0;
            for ( int j = 0; j < rank.length ( ); j++, index++ ) {
                char piece = rank.charAt ( j );
                if ( Character.isDigit ( piece ) ) {
                    index += piece - 48 - 1;
                    continue;
                }

                board[ 7 - i ][ index ] = ChessPieceFactory.getChessPiece ( piece, 7 - i, index );
            }
        }
    }
}
