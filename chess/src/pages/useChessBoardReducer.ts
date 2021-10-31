import { ChessAiType, ChessMove } from 'generated/graphql';
import { useReducer } from 'react';
import { fenToBoard } from 'util/utils';

const FEN_STARTING_POSITION =
	'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1';

export type BoardTile = {
	piece: string;
	highlighted: string;
	rank: number;
	file: number;
};

export type ChessBoardReducerState = {
	fen: string;
	board: BoardTile[][];
	possibleMoves: ChessMove[];
	isInChekmate: boolean;
	isInStalemate: boolean;
	whiteAI: ChessAiType;
	blackAI: ChessAiType;
};

const initalState: ChessBoardReducerState = {
	fen: localStorage.getItem('FEN') ?? FEN_STARTING_POSITION,
	board: fenToBoard(localStorage.getItem('FEN') ?? FEN_STARTING_POSITION),
	possibleMoves: [],
	isInChekmate: false,
	isInStalemate: false,
	whiteAI: ChessAiType.None,
	blackAI: ChessAiType.None,
};

export type ChessBoardReducerActions =
	| { type: 'setFEN'; fen: string }
	| {
			type: 'setPossibleMoves';
			moves: ChessMove[];
	  }
	| { type: 'setBoard'; board: BoardTile[][] }
	| { type: 'setWhiteAI'; ai: ChessAiType }
	| { type: 'setBlackAI'; ai: ChessAiType }
	| { type: 'setGameState'; isInChekmate: boolean; isInStalemate: boolean }
	| { type: 'reset' };

const reducer = (
	state: ChessBoardReducerState,
	action: ChessBoardReducerActions
): ChessBoardReducerState => {
	switch (action.type) {
		case 'setFEN': {
			if (action.fen !== localStorage.getItem('FEN')) {
				localStorage.setItem('FEN', action.fen);
				localStorage.setItem(
					'HISTORY',
					localStorage.getItem('HISTORY') + '|' + action.fen
				);
			}
			return {
				...state,
				fen: action.fen,
				board: fenToBoard(action.fen),
				possibleMoves: [],
			};
		}
		case 'setPossibleMoves': {
			return { ...state, possibleMoves: action.moves };
		}
		case 'setBoard': {
			return {
				...state,
				board: action.board,
			};
		}
		case 'setWhiteAI': {
			return {
				...state,
				whiteAI: action.ai,
			};
		}
		case 'setBlackAI': {
			return {
				...state,
				blackAI: action.ai,
			};
		}
		case 'setGameState': {
			return {
				...state,
				isInChekmate: action.isInChekmate,
				isInStalemate: action.isInStalemate,
			};
		}
		case 'reset': {
			localStorage.setItem('FEN', FEN_STARTING_POSITION);
			localStorage.setItem('HISTORY', FEN_STARTING_POSITION);
			return {
				...state,
				fen: FEN_STARTING_POSITION,
				board: fenToBoard(FEN_STARTING_POSITION),
				possibleMoves: [],
				isInChekmate: false,
				isInStalemate: false,
			};
		}
	}
};

export const useChessBoardReducer = () => {
	return useReducer(reducer, initalState);
};
