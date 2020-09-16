package game.frontend;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class ScorePanel extends BorderPane {

	private Label scoreLabel;
	private Label levelLabel;
	private Label movesLabel;

	public ScorePanel() {
		setStyle("-fx-background-color: #5490ff");
		scoreLabel = new Label("0");
		scoreLabel.setAlignment(Pos.CENTER);
		scoreLabel.setStyle("-fx-font-size: 24");
		setCenter(scoreLabel);

		levelLabel = new Label(" ");
		levelLabel.setAlignment(Pos.CENTER_RIGHT);
		levelLabel.setStyle("-fx-font-size: 24");
		setRight(levelLabel);

		movesLabel = new Label(" ");
		movesLabel.setAlignment(Pos.CENTER_LEFT);
		movesLabel.setStyle("-fx-font-size: 24");
		setLeft(movesLabel);
	}
	
	public void updateScore(String text, String text2, String text3) {
		scoreLabel.setText(text);
		levelLabel.setText(text2);
		movesLabel.setText(text3);
	}

}