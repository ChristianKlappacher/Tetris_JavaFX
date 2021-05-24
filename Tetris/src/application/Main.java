package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.scene.Scene;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.util.Duration;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {

	// variables
	public static final int MOVE = 25;
	public static final int SIZE = 25;
	public static int XMAX = SIZE * 12;
	public static int YMAX = SIZE * 24;
	public static int[][] GRID = new int[XMAX / SIZE][YMAX / SIZE];
	private static Pane pane = new Pane();
	private static Scene scene = new Scene(pane, XMAX + 150, YMAX);

	private static Form nextPiece = Controller.makePiece();
	private static Form controlledPiece;

	private static boolean game = true;

	private static Media music = new Media(Main.class.getClassLoader().getResource("tetris.mp3").toString());
	private static MediaPlayer mediaPlayer = new MediaPlayer(music);

	private static Integer score = 0;
	private static Integer linesNo = 0;
	private static Integer level = 1;
	private static Integer timerLevel = 1;

	private static Text levelText = new Text("Level: ");
	private static Text scoreText = new Text("Score: ");
	private static Text linesText = new Text("Lines: ");
	private static Text instructions = new Text("M: mute/unmute \nUP: Rotate \nDOWN: Down \nLEFT: Left \nRIGHT: Right");

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		for (int[] row : GRID) {
			Arrays.fill(row, 0);
		}
		// generate first piece
		controlledPiece = nextPiece;
		Controller.forcedMovePiece(controlledPiece, -8, -2, -8, -2, -8, -2, -8, -2);
		nextPiece = Controller.makePiece();
		pane.getChildren().addAll(controlledPiece.a, controlledPiece.b, controlledPiece.c, controlledPiece.d);
		pane.getChildren().addAll(nextPiece.a, nextPiece.b, nextPiece.c, nextPiece.d);
		moveOnKeyPress(controlledPiece);

		// Initialise the stage with all texts
		linesText.setStyle("-fx-font: 24 arial;");
		linesText.setX(XMAX + 5);
		linesText.setY(YMAX / 3 + 2 * SIZE);

		scoreText.setStyle("-fx-font: 24 arial;");
		scoreText.setX(XMAX + 5);
		scoreText.setY(YMAX / 3 - 2 * SIZE);

		Line vline = new Line(XMAX, 0, XMAX, YMAX);

		instructions.setStyle("-fx-font: 16 arial;");
		instructions.setX(XMAX + 5);
		instructions.setY(YMAX / 1.2);

		levelText.setStyle("-fx-font: 24 arial;");
		levelText.setX(XMAX + 5);
		levelText.setY(YMAX / 3);

		pane.getChildren().addAll(linesText, scoreText, vline, instructions, levelText);

		primaryStage.setTitle("TETRIS");
		primaryStage.setScene(scene);
		primaryStage.show();

		startTimer();

		// play audio and repeat
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				mediaPlayer.seek(Duration.ZERO);
			}
		});
		mediaPlayer.play();
	}

	private void startTimer() {
		// Initialise a timer for fall down, score etc.
		Timer timer = new Timer("Timer");
		TimerTask timerTask = new TimerTask() {
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						if (game) {
							// restart timer with shorter interval every level
							if (timerLevel != level) {
								timer.cancel();
								startTimer();
								timerLevel += 1;
							}
							// fall down and update score
							Controller.moveDown(controlledPiece);
							scoreText.setText("Score: " + score);
							linesText.setText("Lines: " + linesNo);
							levelText.setText("Level: " + level);

							// check if game is over
							if (Controller.collision(controlledPiece.a, 0, 0)
									|| Controller.collision(controlledPiece.b, 0, 0)
									|| Controller.collision(controlledPiece.b, 0, 0)
									|| Controller.collision(controlledPiece.b, 0, 0)) {
								Text gameOver = new Text("GAME OVER");
								gameOver.setFill(Color.RED);
								gameOver.setStyle("-fx-font: 70 arial;");
								gameOver.setY(YMAX / 2);
								gameOver.setX(10);
								pane.getChildren().add(gameOver);
								Main.game = false;
								mediaPlayer.stop();
							}

						}
					}
				});
			}

		};
		timer.schedule(timerTask, 0, 1000 / level);
	}

	private static void moveOnKeyPress(Form form) {
		// sets key bindings
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case RIGHT:
					if (game) {
						Controller.moveRight(form);
					}
					break;
				case DOWN:
					if (game) {
						Controller.moveDown(form);
						score += 1;
						Main.scoreText.setText("Score: " + score);
					}
					break;
				case LEFT:
					if (game) {
						Controller.moveLeft(form);
					}
					break;
				case UP:
					if (game) {
						Controller.rotation(form);
					}
					break;
				case M:
					mediaPlayer.setMute(!mediaPlayer.isMute());
					break;
				default:
					break;
				}
			}
		});
	}

	public static void lockForm(Form form) {
		// locks the piece in place and gives new piece
		GRID[(int) form.a.getX() / SIZE][(int) form.a.getY() / SIZE] = 1;
		GRID[(int) form.b.getX() / SIZE][(int) form.b.getY() / SIZE] = 1;
		GRID[(int) form.c.getX() / SIZE][(int) form.c.getY() / SIZE] = 1;
		GRID[(int) form.d.getX() / SIZE][(int) form.d.getY() / SIZE] = 1;
		checkLine();

		controlledPiece = nextPiece;
		Controller.forcedMovePiece(controlledPiece, -8, -2, -8, -2, -8, -2, -8, -2);
		nextPiece = Controller.makePiece();
		pane.getChildren().addAll(nextPiece.a, nextPiece.b, nextPiece.c, nextPiece.d);
		moveOnKeyPress(controlledPiece);

	}

	public static void checkLine() {
		// checks if line is completely filled and removes the line
		for (int row = 0; row < GRID[0].length; row++) {
			int checkline = 0;

			for (int column = 0; column < GRID.length; column++) {
				if (GRID[column][row] == 1) {
					checkline++;
				}

				if (checkline == GRID.length) {
					linesNo++;
					score += level * 50;
					for (int reverse = row; reverse >= 0; reverse--) {
						for (int reverseColumn = 0; reverseColumn < GRID.length; reverseColumn++) {
							if (reverse == 0) {
								GRID[reverseColumn][reverse] = 0;
							} else {
								GRID[reverseColumn][reverse] = GRID[reverseColumn][reverse - 1];
							}
						}
					}
					ArrayList<Node> removeRectangles = new ArrayList<Node>();
					pane.getChildren().removeAll(nextPiece.a, nextPiece.b, nextPiece.c, nextPiece.d);
					for (Node node : pane.getChildren()) {
						if (node instanceof Rectangle) {
							Rectangle rectangle = (Rectangle) node;
							if (GRID[(int) rectangle.getX() / SIZE][(int) rectangle.getY() / SIZE] == 0) {
								removeRectangles.add(node);
							}
						}
					}
					for (Node rectangle : removeRectangles) {
						pane.getChildren().remove(rectangle);
					}
					pane.getChildren().addAll(nextPiece.a, nextPiece.b, nextPiece.c, nextPiece.d);
				}
			}
			level = linesNo / 10 + 1;
		}
	}

}
