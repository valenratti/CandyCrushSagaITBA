package game.backend.level;

import game.backend.GameState;
import game.backend.cell.Cell;
import game.backend.move.Move;
import game.backend.move.MoveMaker;

public class Level2 extends AbstractLevel{


    public int cellsLeft = SIZE*SIZE;
    private boolean[] rows = new boolean[SIZE];
    private boolean[] cols = new boolean[SIZE];
    protected static int MAX_MOVES = 25;

    @Override
    protected GameState newState() {
        return new Level2State(MAX_MOVES);
    }

    @Override
    public boolean tryMove(int i1, int j1, int i2, int j2){
        Move move = moveMaker.getMove(i1, j1, i2, j2);
        swapContent(i1, j1, i2, j2);
        if (move.isValid()) {
            updateYellow(i1,j1,i2,j2);
            move.removeElements();
            fallElements();
            state().addMove();
            return true;
        } else {
            swapContent(i1, j1, i2, j2);
            return false;
        }
    }

    private void updateYellow(int i1, int j1, int i2, int j2){
        int rowOrCol = moveType(i1, j1, i2, j2);
        if (rowOrCol == 0 && !rows[i1]) {
            rows[i1] =  true;
            for (int j = 0; j < SIZE; j++) {
                setYellow(getCell(i1,j));
            }
        } else if(!cols[j1]){
            cols[j1] = true;
            for (int i = 0; i < SIZE; i++) {
                setYellow(getCell(i,j1));
            }
        }
    }

    private void setYellow(Cell cell){
        if (!cell.isYellow()) {
            cell.setYellow();
            cellsLeft--;
        }
    }

    // Devuelve 0 si el intercambio fue horizontal, la 1 si fue vertical.
    private int moveType(int i1, int j1, int i2, int j2){
        if(i1 == i2) return 0;
        return 1;
    }

    private class Level2State extends GameState{

        public Level2State(int maxMoves) {
            this.maxMoves = maxMoves;
        }

        @Override
        public boolean gameOver() {
            return playerWon() || getMoves() >= maxMoves;
        }

        @Override
        public boolean playerWon() {
            return cellsLeft == 0;
        }

        @Override
        public long getInfo() {
            return cellsLeft;
        }


    }
}
