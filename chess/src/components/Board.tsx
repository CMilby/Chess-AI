import React, { useState } from 'react';
import { css } from '@emotion/css';
import {
	ChessBoardReducerActions,
	ChessBoardReducerState,
} from 'pages/useChessBoardReducer';
import { Tile } from './Tile';
import { ChessMove } from 'generated/graphql';

interface BoardProps {
	state: ChessBoardReducerState;
	dispatch: React.Dispatch<ChessBoardReducerActions>;
}

export const Board: React.FC<BoardProps> = ({ state, dispatch }) => {
	const [showingMoves, setShowingMoves] = useState(false);
	const [draggingPiece, setDraggingPiece] = useState(
		{} as {
			possibleMoves: ChessMove[] | undefined;
			rank: number;
			file: number;
		}
	);

	const setPossibleMoveTiles = (
		possibleMoves: ChessMove[] | undefined,
		rank: number,
		file: number
	) => {
		if (!possibleMoves) {
			return;
		}

		const newBoard = state.board.map((rank) => {
			return rank.map((tile) => {
				return {
					...tile,
					highlighted: 'none',
				};
			});
		});

		if (
			draggingPiece.rank !== rank ||
			draggingPiece.file !== file ||
			!showingMoves
		) {
			setShowingMoves(true);

			possibleMoves.forEach((move) => {
				newBoard[7 - move.piece.rank][move.piece.file].highlighted =
					'current_piece';
				newBoard[7 - move.pieceEndRank][move.pieceEndFile].highlighted =
					'possible_move';
			});
		} else {
			setShowingMoves(false);
		}

		dispatch({ type: 'setBoard', board: newBoard });
	};

	return (
		<div
			className={css`
				display: grid;
				grid-template-columns: repeat(8, 100px);
				grid-template-rows: repeat(8, 100px);
			`}
		>
			{state.board.map((row) => {
				return row.map((tile) => {
					return (
						<Tile
							key={`${tile.rank}_${tile.file}`}
							piece={tile.piece}
							tileColor={tile.highlighted}
							possibleMoves={state.possibleMoves?.filter(
								(move) =>
									move.piece.piece ===
										state.board[7 - tile.rank][tile.file]
											.piece &&
									move.piece.rank === tile.rank &&
									move.piece.file === tile.file
							)}
							rank={tile.rank}
							file={tile.file}
							draggingPiece={draggingPiece}
							state={state}
							dispatch={dispatch}
							setHighlightedTile={setPossibleMoveTiles}
							setDraggingPiece={setDraggingPiece}
						/>
					);
				});
			})}
		</div>
	);
};
