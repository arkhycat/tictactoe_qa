package qa_lab3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import qa_lab3.TicTacToeHelper.*;

/**
 *
 * @author venefica
 */
public class Board {
    
    private TicTacToeHelper.Player[][] board;

    public Board(int size) {
        board = new TicTacToeHelper.Player[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = TicTacToeHelper.Player.None;
            }
        }
    }

    public Board(Board b) {
        board = new TicTacToeHelper.Player[b.size()][b.size()];
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                board[i][j] = b.board[i][j];
            }
        }
    }

    public Integer size() {
        return board.length;
    }

    public boolean makeMove(TicTacToeHelper.Move m) {
        if (board[m.getX()][m.getY()] != TicTacToeHelper.Player.None) {
            return false;
        }
        board[m.getX()][m.getY()] = m.getPlayer();
        return true;
    }

    public boolean checkOneDirection(int winLen, int i, int j, int dir_i, int dir_j) {
        if ((i + dir_i * winLen > board.length) || (j + dir_j * winLen > board.length) || (i + dir_i * winLen < -1) || (j + dir_j * winLen < -1)) {
            return false;
        }
        boolean foundWinningCombination = true;
        for (int k = 0; k < winLen; k++) {
            if (board[i][j] != board[i + dir_i * k][j + dir_j * k]) {
                foundWinningCombination = false;
                break;
            }
        }
        return foundWinningCombination;
    }

    public TicTacToeHelper.Player checkWhoWins(int winLen) {
        boolean isDraw = true;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != TicTacToeHelper.Player.None) {
                    for (int dir_i = -1; dir_i < 2; dir_i++) {
                        for (int dir_j = -1; dir_j < 2; dir_j++) {
                            if (dir_i == 0 && dir_j == 0) {
                                continue;
                            }
                            if (checkOneDirection(winLen, i, j, dir_i, dir_j)) {
                                return board[i][j];
                            }
                        }
                    }
                } else {
                    isDraw = false;
                }
            }
        }
        if (isDraw) {
            return TicTacToeHelper.Player.Draw;
        }
        return TicTacToeHelper.Player.None;
    }
    
    public TicTacToeHelper.Player getCellPlayer(int i, int j) {
        return board[i][j];
    }
}
