package game.frontend.candyFrames;

import game.backend.CandyGame;
import game.backend.cell.Cell;
import game.backend.element.BombCandy;
import game.backend.element.Candy;
import game.backend.element.Element;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class CandyFrame3 extends CandyFrame {


    public CandyFrame3(CandyGame game) {
        super(game);
    }

    @Override
    public void refreshImages(Timeline timeLine, Duration frameTime, int finalI, int finalJ, Cell cell, Image image, Element element) {
        if(element instanceof BombCandy){
            int bombCounter = ((BombCandy)element).getBombCounter();
            String text = ((Integer)bombCounter).toString();
            timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImageWithBomb(finalI, finalJ, null,text)));
            timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImageWithBomb(finalI, finalJ, image,text)));
        }else{
            timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImage(finalI, finalJ, null)));
            timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImage(finalI, finalJ, image)));
        }
    }
}
