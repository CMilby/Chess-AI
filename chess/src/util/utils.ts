import { BoardTile } from 'pages/useChessBoardReducer';

export function fenToBoard(fen: string): BoardTile[][] {
	const board: BoardTile[][] = [];
	const fenTokens = fen.split(' ');
	const rows = fenTokens[0].split('/');

	for (let i = 0; i < 8; i++) {
		board.push([]);
		for (let j = 0; j < 8; j++) {
			board[i][j] = {
				piece: '',
				highlighted: 'none',
				rank: 7 - i,
				file: j,
			};
		}
	}

	for (let i = 0; i < 8; i++) {
		let index = 0;
		for (let j = 0; j < rows[i].length; j++, index++) {
			if (!isNaN(parseInt(rows[i][j]))) {
				index += parseInt(rows[i][j]) - 1;
				continue;
			}

			board[i][index].piece = rows[i][j];
		}
	}

	return board;
}

// export function boardToFen(board: BoardTile[][]): string {
// 	let fen = '';

// 	for (let rank = 0; rank < 8; rank++) {
// 		let numEmpty = 0;
// 		for (let file = 0; file < 8; file++) {
// 			const piece = board[rank][file];
// 			if (piece.piece === '') {
// 				numEmpty++;
// 			} else {
// 				if (numEmpty > 0) {
// 					fen += String(numEmpty);
// 					numEmpty = 0;
// 				}

// 				fen += piece.piece;
// 			}
// 		}

// 		if (numEmpty > 0) {
// 			fen += String(numEmpty);
// 		}

// 		if (rank !== 7) {
// 			fen += '/';
// 		}
// 	}

// 	return fen + ' w KQkq - 0 1';
// }
