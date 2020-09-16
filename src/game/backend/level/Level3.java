package game.backend.level;

import game.backend.GameState;
import game.backend.cell.CandyGeneratorCell;
import game.backend.cell.CandyGeneratorCellL3;
import game.backend.cell.Cell;
import game.backend.element.BombCandy;
import game.backend.element.Candy;
import game.backend.element.Element;
import game.backend.element.Wall;
import game.backend.move.Move;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Level3 extends AbstractLevel {

    private List<BombCandy> bombCandies = new ArrayList<>();

    private boolean bombExplode = false;
    private int leftBombs = 10;

    private static int REQUIRED_SCORE = 2000;
    private static int MAX_MOVES = 40;

    @Override
    protected GameState newState() {
        return new Level3State(MAX_MOVES,REQUIRED_SCORE);
    }

    @Override
    public boolean tryMove(int i1, int j1, int i2, int j2){
        Move move = moveMaker.getMove(i1, j1, i2, j2);
        swapContent(i1, j1, i2, j2);
        if (move.isValid()) {
            move.removeElements();
            refreshBombCandies();
            fallElements();
            state().addMove();
            return true;
        } else {
            swapContent(i1, j1, i2, j2);
            return false;
        }
    }

    @Override
    public void clearContent(int i, int j){
        Element element = get(i,j);
        if(element instanceof BombCandy){
            BombCandy candy = (BombCandy) element;
            if(candy.getBombCounter()>0){
                candy.deletedBombCandy();
            }
        }super.clearContent(i,j);
    }

    public void addBombCandy(BombCandy candy){
        bombCandies.add(candy);
    }


    public boolean canAddBombs(){
        if(leftBombs>0){
            leftBombs--;
            return true;
        }return false;
    }

    public void refreshBombCandies(){
        for(BombCandy bombCandy : bombCandies){
            if(bombCandy.isShown()) {
                bombCandy.decreaseBombCounter();
            }
            if(bombCandy.getBombCounter()==0){
                bombExplode = true;
            }
        }
    }

    @Override
    public CandyGeneratorCell createGeneratorCell(){
        return new CandyGeneratorCellL3(this);
    }

    private class Level3State extends GameState{
        private long requiredScore;

        public Level3State(int maxMoves, long requiredScore) {
            this.maxMoves = maxMoves;
            this.requiredScore = requiredScore;
        }

        @Override
        public boolean gameOver() {
            return playerWon() || bombExplode || getMoves()>maxMoves;
        }

        @Override
        public boolean playerWon() {
            return getScore() > requiredScore && bombCandies.isEmpty();
        }

        @Override
        public long getInfo() {
                int i = 0;
                while(i<bombCandies.size() && !bombCandies.get(i).isShown()) i++;
                if(i<bombCandies.size()) return bombCandies.get(i).getBombCounter();
                    else return -1;
        }
    }

}
