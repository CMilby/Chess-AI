import React from 'react';
import { css } from '@emotion/css';
import { ChessMove } from 'generated/graphql';
import {
	ChessBoardReducerActions,
	ChessBoardReducerState,
} from 'pages/useChessBoardReducer';

interface TileProps {
	tileColor: string;
	piece: string;
	possibleMoves: ChessMove[] | undefined;
	rank: number;
	file: number;
	draggingPiece: {
		rank: number;
		file: number;
		possibleMoves: ChessMove[] | undefined;
	};
	state: ChessBoardReducerState;
	dispatch: React.Dispatch<ChessBoardReducerActions>;
	setHighlightedTile: (
		possibleMoves: ChessMove[] | undefined,
		rank: number,
		file: number
	) => void;
	setDraggingPiece: (piece: {
		rank: number;
		file: number;
		possibleMoves: ChessMove[] | undefined;
	}) => void;
}

export const Tile: React.FC<TileProps> = ({
	tileColor,
	piece,
	possibleMoves,
	rank,
	file,
	draggingPiece,
	state,
	dispatch,
	setHighlightedTile,
	setDraggingPiece,
}) => {
	const getPiecesURL = (piece: string): string => {
		if (piece === undefined) {
			return '';
		}

		const color = piece === piece.toUpperCase() ? 'light' : 'dark';
		switch (piece) {
			case 'p':
			case 'P':
				return `${process.env.PUBLIC_URL}/pieces/pawn_${color}.png`;
			case 'r':
			case 'R':
				return `${process.env.PUBLIC_URL}/pieces/rook_${color}.png`;
			case 'k':
			case 'K':
				return `${process.env.PUBLIC_URL}/pieces/king_${color}.png`;
			case 'n':
			case 'N':
				return `${process.env.PUBLIC_URL}/pieces/knight_${color}.png`;
			case 'b':
			case 'B':
				return `${process.env.PUBLIC_URL}/pieces/bishop_${color}.png`;
			case 'q':
			case 'Q':
				return `${process.env.PUBLIC_URL}/pieces/queen_${color}.png`;
		}

		return '';
	};

	const onDragEnd = (e: React.DragEvent<HTMLDivElement>) => {
		e.preventDefault();

		let move = draggingPiece.possibleMoves?.filter(
			(move) => move.pieceEndFile === file && move.pieceEndRank === rank
		);

		if (!move || move.length === 0) {
			return;
		}

		setDraggingPiece(
			{} as {
				possibleMoves: ChessMove[] | undefined;
				rank: number;
				file: number;
			}
		);

		dispatch({ type: 'setFEN', fen: move[0].resultingFEN });
	};

	return (
		<div
			className={css`
				background-color: ${tileColor === 'current_piece'
					? '#f6f669'
					: tileColor === 'possible_move'
					? '#e00'
					: tileColor === 'possible_move_capture'
					? '#0f0'
					: tileColor === 'none' && (rank + file) % 2 === 0
					? '#769656'
					: '#eeeed2'};
				width: 100%;
				height: 100%;
			`}
			onDrop={onDragEnd}
			onDragOver={(e) => e.preventDefault()}
		>
			{getPiecesURL(piece) !== '' ? (
				<img
					src={getPiecesURL(piece)}
					alt={piece}
					width='100%'
					height='100%'
					draggable
					onClick={() => {
						setHighlightedTile(possibleMoves, rank, file);
						setDraggingPiece({ rank, file, possibleMoves });
					}}
					onDragStart={() => {
						setHighlightedTile(possibleMoves, rank, file);
						setDraggingPiece({ rank, file, possibleMoves });
					}}
				/>
			) : null}
		</div>
	);
};
