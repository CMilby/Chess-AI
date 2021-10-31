import { useEffect, useState } from 'react';
import { ChessAiType, useGetPositionQuery } from 'generated/graphql';
import { Board } from 'components/Board';
import { useChessBoardReducer } from './useChessBoardReducer';
import { css } from '@emotion/css';

export const ChessBoardPage = () => {
	const [moveDelayMillis, setMoveDelayMillis] = useState(500);
	const [maxMoveDepth, setMaxMoveDepth] = useState(20);
	const [pause, setPause] = useState(false);

	const [state, dispatch] = useChessBoardReducer();
	const [{ data }] = useGetPositionQuery({
		variables: {
			fen: state.fen,
			whiteAI: state.whiteAI,
			blackAI: state.blackAI,
			moveDelay: moveDelayMillis,
			maxMoveDepth: maxMoveDepth,
		},
		requestPolicy: 'network-only',
		pause: state.isInChekmate || state.isInStalemate || pause,
	});

	useEffect(() => {
		dispatch({
			type: 'setGameState',
			isInChekmate: data?.position?.isInCheckmate ?? false,
			isInStalemate: data?.position?.isStalemate ?? false,
		});

		if (data?.position?.possibleMoves !== undefined) {
			dispatch({
				type: 'setPossibleMoves',
				moves: data?.position?.possibleMoves,
			});
		}

		if (
			state.whiteAI !== ChessAiType.None &&
			data?.position?.FEN !== undefined &&
			data.position?.FEN !== state.fen
		) {
			dispatch({ type: 'setFEN', fen: data?.position?.FEN });
		}

		if (
			state.blackAI !== ChessAiType.None &&
			data?.position?.FEN !== undefined &&
			data.position?.FEN !== state.fen
		) {
			dispatch({ type: 'setFEN', fen: data?.position?.FEN });
		}
	}, [data]);

	return (
		<div
			className={css`
				display: grid;
				grid-template-columns: 820px auto;
			`}
		>
			<Board state={state} dispatch={dispatch} />
			<div
				className={css`
					display: grid;
					grid-template-rows: repeat(7, 2rem) 4rem auto;
				`}
			>
				<div>
					<label htmlFor='white-ai-select'>
						Choose an AI for White:
					</label>
					<select
						id='white-ai-select'
						value={state.whiteAI}
						onChange={(e) =>
							dispatch({
								type: 'setWhiteAI',
								ai: e.target.value as ChessAiType,
							})
						}
					>
						<option value={ChessAiType.None}>None</option>
						<option value={ChessAiType.RandomMove}>
							Random Move
						</option>
					</select>
				</div>
				<div>
					<label htmlFor='black-ai-select'>
						Choose an AI for Black:
					</label>
					<select
						id='black-ai-select'
						value={state.blackAI}
						onChange={(e) =>
							dispatch({
								type: 'setBlackAI',
								ai: e.target.value as ChessAiType,
							})
						}
					>
						<option value={ChessAiType.None}>None</option>
						<option value={ChessAiType.RandomMove}>
							Random Move
						</option>
					</select>
				</div>
				<div>
					<label htmlFor='move-delay-input'>Max Move Depth:</label>
					<input
						id='move-delay-input'
						value={maxMoveDepth}
						onChange={(e) =>
							setMaxMoveDepth(Number(e.target.value))
						}
					/>
				</div>
				<div>
					<label htmlFor='move-delay-input'>Move Delay Millis:</label>
					<input
						id='move-delay-input'
						value={moveDelayMillis}
						onChange={(e) =>
							setMoveDelayMillis(Number(e.target.value))
						}
					/>
				</div>
				<div>
					<button onClick={() => setPause(!pause)}>
						{pause ? 'Resume Game' : 'Pause Game'}
					</button>
				</div>
				<div>
					<button
						onClick={() => navigator.clipboard.writeText(state.fen)}
					>
						Copy FEN to Clipboard
					</button>
				</div>
				<div>
					<button onClick={() => dispatch({ type: 'reset' })}>
						New Game
					</button>
				</div>
				<div>
					<div>
						<b>Game State</b>
					</div>
					<div>
						Checkmate: {state.isInChekmate ? 'true' : 'false'}
					</div>
					<div>
						Stalemate: {state.isInStalemate ? 'true' : 'false'}
					</div>
				</div>
				<div>
					{localStorage
						.getItem('HISTORY')
						?.split('|')
						.map((fen) => {
							return <div key={fen}>{fen}</div>;
						})}
				</div>
			</div>
		</div>
	);
};
