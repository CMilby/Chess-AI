import gql from 'graphql-tag';
import * as Urql from 'urql';
export type Maybe<T> = T | null;
export type Exact<T extends { [key: string]: unknown }> = { [K in keyof T]: T[K] };
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]?: Maybe<T[SubKey]> };
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]: Maybe<T[SubKey]> };
export type Omit<T, K extends keyof T> = Pick<T, Exclude<keyof T, K>>;
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: string;
  String: string;
  Boolean: boolean;
  Int: number;
  Float: number;
};

export enum ChessAiType {
  None = 'NONE',
  RandomMove = 'RANDOM_MOVE'
}

export type ChessMove = {
  __typename?: 'ChessMove';
  canPromote: Scalars['Boolean'];
  capturesPiece?: Maybe<ChessPiece>;
  capturesPieceEndFile: Scalars['Int'];
  capturesPieceEndRank: Scalars['Int'];
  piece: ChessPiece;
  pieceEndFile: Scalars['Int'];
  pieceEndRank: Scalars['Int'];
  resultingFEN: Scalars['String'];
};

export type ChessPiece = {
  __typename?: 'ChessPiece';
  color: Scalars['String'];
  file: Scalars['Int'];
  piece: Scalars['String'];
  rank: Scalars['Int'];
};

export type ChessPosition = {
  __typename?: 'ChessPosition';
  FEN: Scalars['String'];
  isInCheckmate: Scalars['Boolean'];
  isStalemate: Scalars['Boolean'];
  possibleMoves: Array<ChessMove>;
};

export type Query = {
  __typename?: 'Query';
  position: ChessPosition;
  possibleMoves: Array<ChessMove>;
};


export type QueryPositionArgs = {
  blackAI: ChessAiType;
  fen: Scalars['String'];
  maxMoveDepth?: Maybe<Scalars['Int']>;
  moveDelay?: Maybe<Scalars['Int']>;
  whiteAI: ChessAiType;
};


export type QueryPossibleMovesArgs = {
  fen: Scalars['String'];
};

export type GetPositionQueryVariables = Exact<{
  fen: Scalars['String'];
  whiteAI: ChessAiType;
  blackAI: ChessAiType;
  moveDelay: Scalars['Int'];
  maxMoveDepth: Scalars['Int'];
}>;


export type GetPositionQuery = { __typename?: 'Query', position: { __typename?: 'ChessPosition', FEN: string, isInCheckmate: boolean, isStalemate: boolean, possibleMoves: Array<{ __typename?: 'ChessMove', pieceEndRank: number, pieceEndFile: number, capturesPieceEndRank: number, capturesPieceEndFile: number, canPromote: boolean, resultingFEN: string, piece: { __typename?: 'ChessPiece', piece: string, color: string, rank: number, file: number }, capturesPiece?: { __typename?: 'ChessPiece', piece: string, color: string, rank: number, file: number } | null | undefined }> } };


export const GetPositionDocument = gql`
    query getPosition($fen: String!, $whiteAI: ChessAIType!, $blackAI: ChessAIType!, $moveDelay: Int!, $maxMoveDepth: Int!) {
  position(
    fen: $fen
    whiteAI: $whiteAI
    blackAI: $blackAI
    moveDelay: $moveDelay
    maxMoveDepth: $maxMoveDepth
  ) {
    FEN
    possibleMoves {
      piece {
        piece
        color
        rank
        file
      }
      capturesPiece {
        piece
        color
        rank
        file
      }
      pieceEndRank
      pieceEndFile
      capturesPieceEndRank
      capturesPieceEndFile
      canPromote
      resultingFEN
    }
    isInCheckmate
    isStalemate
  }
}
    `;

export function useGetPositionQuery(options: Omit<Urql.UseQueryArgs<GetPositionQueryVariables>, 'query'> = {}) {
  return Urql.useQuery<GetPositionQuery>({ query: GetPositionDocument, ...options });
};