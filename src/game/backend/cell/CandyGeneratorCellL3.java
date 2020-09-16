package game.backend.cell;

import game.backend.Grid;
import game.backend.element.BombCandy;
import game.backend.element.Candy;
import game.backend.element.CandyColor;
import game.backend.element.Element;
import game.backend.level.Level3;

public class CandyGeneratorCellL3 extends CandyGeneratorCell {

    private static final int BOMB_COUNTER = 10;
    Level3 level3;

    public CandyGeneratorCellL3(Grid grid) {
        super(grid);
        level3 = (Level3)grid;
    }

    @Override
    public Element getContent() {
        int i = (int)(Math.random() * CandyColor.values().length);
        boolean isBomb = (int)((Math.random() *18)%18) == 4;
        if(isBomb && level3.canAddBombs()) {
            BombCandy candy = new BombCandy(CandyColor.values()[i], BOMB_COUNTER);
            level3.addBombCandy(candy);
            return candy;
        }else return new Candy(CandyColor.values()[i]);
    }

}
