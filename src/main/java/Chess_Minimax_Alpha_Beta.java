import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Chess_Minimax_Alpha_Beta {
    private final int DEPTH = 3;
    private final int MAX_PLAYER = 1;
    private final boolean DEBUG = false;

    private Board board;
    private Move bestMove;
    private Map<Move,Integer> rootValues;

    public Chess_Minimax_Alpha_Beta(Board board) {
        this.board = board;
    }

    private int evalution(String map) {
        int res = 0; // Default
        for (char i : map.toCharArray()) {
            if (i == 'B') {
                res += 30;
            }
            if (i == 'K') {
                res += 900;
            }
            if (i == 'N') {
                res += 35;
            }
            if (i == 'P') {
                res += 10;
            }
            if (i == 'R') {
                res += 50;
            }
            if (i == 'Q') {
                res += 90;
            }

            if (i == 'b') {
                res -= 30;
            }
            if (i == 'k') {
                res -= 900;
            }
            if (i == 'n') {
                res -= 35;
            }
            if (i == 'p') {
                res -= 10;
            }
            if (i == 'r') {
                res -= 50;
            }
            if (i == 'q') {
                res -= 90;
            }
        }
            return res;
    }

    public Move getBestMove(int player){
        bestMove = null;
        rootValues = new HashMap<>();
        int val = minimax(0,player,Integer.MIN_VALUE,Integer.MAX_VALUE);

        for (var node:rootValues.entrySet()){
            if (node.getValue() == val){
                bestMove = node.getKey();
            }
        }

        System.out.println(val);
        return bestMove;
    }

    public int minimax(int depth, int player, int alpha, int beta) {
        if (depth == DEPTH) {
            return evalution(board.toString().substring(0, board.toString().length() - 11));
        }

        if (DEBUG) System.out.println(evalution(board.toString().substring(0, board.toString().length() - 11)));
        if (player == MAX_PLAYER) {
            if (DEBUG) System.out.println("DEPTH:" + (depth));
            int best = Integer.MIN_VALUE;
            for (Move move : board.legalMoves()) {
                board.doMove(move);
                if (DEBUG) System.out.println(board.toString());
                int MIN_PLAYER = 0;
                int val = minimax(depth + 1, MIN_PLAYER, alpha, beta);

                if (depth==0){
                    rootValues.put(move,val);
                }

                board.undoMove();
                best = Math.max(best, val);
                alpha = Math.max(alpha, best);

                if (alpha > beta)
                    break;
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            if (DEBUG) System.out.println("DEPTH:" + (depth));
            for (Move move : board.legalMoves()) {
                board.doMove(move);
                if (DEBUG) System.out.println(board.toString());
                int val = minimax(depth + 1, MAX_PLAYER, alpha, beta);
                board.undoMove();
                best = Math.min(best, val);
                beta = Math.min(beta, best);

                if (alpha > beta)
                    break;
            }
            return best;
        }
    }

    public static void main(String args[]) {
        Board board = new Board();
        board.loadFromFen("rnb4r/ppppkpp1/4pn2/b5Np/3P4/2P1BNP1/PP3P1P/R2QKB1R w KQ h6 0 10")   ;
        Chess_Minimax_Alpha_Beta minimax = new Chess_Minimax_Alpha_Beta(board);
        Move move = minimax.getBestMove(1);
        System.out.println(move);
    }
}

