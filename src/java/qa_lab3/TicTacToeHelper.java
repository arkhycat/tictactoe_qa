package qa_lab3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author venefica
 */

public final class TicTacToeHelper {
    private TicTacToeHelper() {}
    
    public enum Player {X, O, None, Draw};
    
    public static Player other(Player p) {
        if (p == Player.X) return Player.O;
        if (p == Player.O) return Player.X;
        return Player.None;
    }
    
    public static class Move {
        private int x, y;
        private Player player;
        public Move(int i, int j, Player p) { x = i; y = j; player = p; }
        public Integer getX() { return x; }
        public Integer getY() { return y; }
        public Player getPlayer() { return player; }
        public boolean equals(Move m) { return (x == m.x && y == m.y && player == m.player); }
    }
    
}
