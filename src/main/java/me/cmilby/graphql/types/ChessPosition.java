package me.cmilby.graphql.types;

import lombok.Builder;
import lombok.Data;
import me.cmilby.chess.ChessMove;

import java.util.List;

@Data
@Builder
public class ChessPosition {
    String FEN;
    List < ChessMove > possibleMoves;
    boolean isInCheckmate;
    boolean isStalemate;
}
