package game.backend.element;

public class BombCandy extends Candy {
    private int bombCounter;
    private boolean isShown = true;

    public BombCandy(CandyColor color, int bombCounter) {
        super(color);
        this.bombCounter = bombCounter;
    }

    @Override
    public boolean equals(Object obj) {
        boolean aux = super.equals(obj);
        if(aux){
            BombCandy other = (BombCandy)obj;
            return bombCounter == other.bombCounter;
        }return false;
    }

    @Override
    public boolean equalsColor(Object obj){
        return super.equals(obj);
    }

    public boolean isShown() {
        return isShown;
    }

    public int getBombCounter() {
        return bombCounter;
    }

    public void deletedBombCandy(){
        isShown = false;
    }

    public void decreaseBombCounter() {
        if(bombCounter>0) bombCounter--;
    }


}

