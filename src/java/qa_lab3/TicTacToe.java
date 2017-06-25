package qa_lab3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import qa_lab3.TicTacToeHelper.*;

/**
 *
 * @author venefica
 */
public class TicTacToe {
    private int winningLen;
    private GameStateTree gameState;
    private Board board;
    
    public TicTacToe(Integer wL, Integer boardSize, Player firstPlayer) throws ImpossibleGameStateException {
        winningLen = wL;
        board = new Board(boardSize);
        Move fakeLastMove;
        switch (firstPlayer) {
        case X:
            fakeLastMove = new Move(-1, -1, Player.O);
            break;
        case O:
            fakeLastMove = new Move(-1, -1, Player.X);
            break;
        default:
            throw new ImpossibleGameStateException();
            }
        gameState = new GameStateTree(board, fakeLastMove);
    }
    
    public void makeMove(Move m) throws ImpossibleGameStateException {
        if (!board.makeMove(m))
            throw new ImpossibleGameStateException();
        
        boolean foundMoveState = false;
        for (GameStateTree nextST : gameState.leafes) {
            if (nextST.lastMove.equals(m)) {
                gameState = nextST;
                return;
            }
        }
        
        throw new ImpossibleGameStateException();
    }
    
    public Move getWinningMove(Player player) {
        for (GameStateTree nextGS : gameState.leafes) {
            if (nextGS.winner == player)
                return nextGS.lastMove;
        }
        
        return null;
    }
    
    public Board getBoard() {return board;}
    
    public class ImpossibleGameStateException extends Exception {}
    
    private class GameStateTree {
        public Player winner;
        public ArrayList<GameStateTree> leafes;
        Move lastMove;
        
        public GameStateTree(Board curBoard, Move lM) throws ImpossibleGameStateException{
            lastMove = lM;
            leafes = new ArrayList<>();
            winner = curBoard.checkWhoWins(winningLen);
            if (winner != Player.None) {
                return;
            }
            
            
            Player nextPlayer;
            if (lastMove.getPlayer() == Player.X) {
                nextPlayer = Player.O;
            } else {
                nextPlayer = Player.X;
            }
            
            winner = lastMove.getPlayer();
            for (int i = 0; i < curBoard.size(); i++) {
                for (int j = 0; j < curBoard.size(); j++) {
                    Board nextBoard = new Board(curBoard);
                    Move nextMove = new Move(i, j, nextPlayer);
                    if (nextBoard.makeMove(nextMove)) {
                        GameStateTree nextGameState = new GameStateTree(nextBoard, nextMove);
                        leafes.add(nextGameState);
                        
                        if (nextGameState.winner == nextPlayer) {
                            winner = nextPlayer;
                        }
                        if (winner != nextPlayer) {
                            if (nextGameState.winner == Player.Draw) {
                                winner = Player.Draw;
                            }
                        }
                    }
                }
            }
        }
    }
}
