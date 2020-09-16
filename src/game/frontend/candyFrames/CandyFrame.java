package game.frontend.candyFrames;

import game.backend.CandyGame;
import game.backend.GameListener;
import game.backend.cell.Cell;
import game.backend.element.Element;

import game.frontend.*;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Optional;


public abstract class CandyFrame extends VBox {

	private static final int CELL_SIZE = 65;

	protected BoardPanel boardPanel;
	private ScorePanel scorePanel;
	private ImageManager images;
	private Point2D lastPoint;
	private CandyGame game;

	public CandyFrame(CandyGame game) {
		this.game = game;
		getChildren().add(new AppMenu());
		images = new ImageManager();
		boardPanel = new BoardPanel(game.getSize(), game.getSize(), CELL_SIZE);
		getChildren().add(boardPanel);
		scorePanel = new ScorePanel();
		getChildren().add(scorePanel);
		game.initGame();
		GameListener listener;
		game.addGameListener(listener = new GameListener() {
			@Override
			public void gridUpdated() {
				Timeline timeLine = new Timeline();
				Duration frameGap = Duration.millis(100);
				Duration frameTime = Duration.ZERO;
				for (int i = game().getSize() - 1; i >= 0; i--) {
					for (int j = game().getSize() - 1; j >= 0; j--) {
						int finalI = i;
						int finalJ = j;
						Cell cell = CandyFrame.this.game.get(i, j);
						Element element = cell.getContent();
						Image image = images.getImage(element);
						refreshImages(timeLine, frameTime, finalI, finalJ, cell, image, element);
					}
					frameTime = frameTime.add(frameGap);
				}
				timeLine.play();
			}
			@Override
			public void cellExplosion(Element e) {
				//
			}
		});

		listener.gridUpdated();
		initializeScorePanel();

		addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			if (lastPoint == null) {
				lastPoint = translateCoords(event.getX(), event.getY());
				System.out.println("Get first = " +  lastPoint);
			} else {
				Point2D newPoint = translateCoords(event.getX(), event.getY());
				if (newPoint != null) {
					System.out.println("Get second = " +  newPoint);
					game().tryMove((int)lastPoint.getX(), (int)lastPoint.getY(), (int)newPoint.getX(), (int)newPoint.getY());
					String message = ((Long)game().getScore()).toString();
					String message2 = game().getInfo() == -1 ? " " : ((Long)game().getInfo()).toString();
					String moves = ((Long)(game().getMovesLeft())).toString();
					if (game().isFinished()) {
						if (game().playerWon()) {
							message = message + " Finished - Player Won!";
						} else {
							message = message + " Finished - Loser !";
						}
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setTitle("Regresar al menu");
						alert.setHeaderText("Volver al Menu Principal");
						alert.setContentText("La partida termino. Desea volver al menu principal?");
						Optional<ButtonType> result = alert.showAndWait();
						if(result.isPresent()) {
							if (result.get() == ButtonType.OK) {
								((Stage)this.getScene().getWindow()).close();
								GameApp refreshMenu = new GameApp();
								try {
									refreshMenu.start(new Stage());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}if(result.get() == ButtonType.CANCEL){
								Platform.exit();
							}
						}
					}
					scorePanel.updateScore(message, message2, moves);
					lastPoint = null;
				}
			}
		});

	}

	private void initializeScorePanel(){
		String info = game().getInfo() == -1 ? " " : ((Long)game().getInfo()).toString();
		String moves = ((Long)(game().getMovesLeft())).toString();
		scorePanel.updateScore("0", info, moves);
	}

	private CandyGame game() {
		return game;
	}

	private Point2D translateCoords(double x, double y) {
		double i = x / CELL_SIZE;
		double j = y / CELL_SIZE;
		return (i >= 0 && i < game.getSize() && j >= 0 && j < game.getSize()) ? new Point2D(j, i) : null;
	}

	public abstract void refreshImages(Timeline timeLine, Duration frameTime, int finalI, int finalJ,Cell cell, Image image, Element element);

}
