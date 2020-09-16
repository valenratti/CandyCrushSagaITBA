package game.frontend.candyFrames;

import game.backend.CandyGame;
import game.backend.cell.Cell;
import game.backend.element.Element;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class CandyFrame1 extends CandyFrame{

    public CandyFrame1(CandyGame game) {
        super(game);
    }

    @Override
    public void refreshImages(Timeline timeLine, Duration frameTime, int finalI, int finalJ, Cell cell, Image image, Element element) {
        timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImage(finalI, finalJ, null)));
        timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImage(finalI, finalJ, image)));
    }

}
