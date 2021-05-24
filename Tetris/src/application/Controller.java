package application;

import java.util.Random;
import javafx.scene.shape.Rectangle;

public class Controller {
	public static int MOVE = Main.MOVE;
	public static final int SIZE = Main.SIZE;
	public static int XMAX = Main.XMAX;
	public static int YMAX = Main.YMAX;
	public static int[][] GRID = Main.GRID;

	public static void moveRight(Form form) {
		movePiece(form, 1, 0, 1, 0, 1, 0, 1, 0);
	}

	public static void moveLeft(Form form) {
		movePiece(form, -1, 0, -1, 0, -1, 0, -1, 0);
	}

	public static void moveDown(Form form) {
		double moveCheck = form.a.getY();
		movePiece(form, 0, 1, 0, 1, 0, 1, 0, 1);
		if (form.a.getY() == moveCheck) {
			Main.lockForm(form);
		}
	}

	public static void rotation(Form form) {
		// rotates the piece according their shape
		int shape = form.shape;
		switch (form.getName()) {
		case "j":
			if (shape == 1) {
				movePiece(form, 1, -1, 0, 0, -1, 1, -2, 0, true);
				break;
			}
			if (shape == 2) {
				movePiece(form, 1, 1, 0, 0, -1, -1, 0, -2, true);
				break;
			}
			if (shape == 3) {
				movePiece(form, -1, 1, 0, 0, 1, -1, 2, 0, true);
				break;
			}
			if (shape == 4) {
				movePiece(form, -1, -1, 0, 0, 1, 1, 0, 2, true);
				break;
			}
			break;
		case "l":
			if (shape == 1) {
				movePiece(form, -1, 1, 0, 0, 1, -1, 0, -2, true);
				break;
			}
			if (shape == 2) {
				movePiece(form, -1, -1, 0, 0, 1, 1, 2, 0, true);
				break;
			}
			if (shape == 3) {
				movePiece(form, 1, -1, 0, 0, -1, 1, 0, 2, true);
				break;
			}
			if (shape == 4) {
				movePiece(form, 1, 1, 0, 0, -1, -1, -2, 0, true);
				break;
			}
			break;
		case "o":
			break;
		case "s":
			if (shape == 1 || shape == 3) {
				movePiece(form, -1, 1, 0, 0, -1, -1, 0, -2, true);
				break;
			}
			if (shape == 2 || shape == 4) {
				movePiece(form, 1, -1, 0, 0, 1, 1, 0, 2, true);
				break;
			}
			break;
		case "t":
			if (shape == 1) {
				movePiece(form, 1, -1, 0, 0, -1, 1, -1, -1, true);
				break;
			}
			if (shape == 2) {
				movePiece(form, 1, 1, 0, 0, -1, -1, 1, -1, true);
				break;
			}
			if (shape == 3) {
				movePiece(form, -1, 1, 0, 0, 1, -1, 1, 1, true);
				break;
			}
			if (shape == 4) {
				movePiece(form, -1, -1, 0, 0, 1, 1, -1, 1, true);
				break;
			}
			break;
		case "z":
			if (shape == 1 || shape == 3) {
				movePiece(form, 1, -1, 0, 0, -1, -1, -2, 0, true);
				break;
			}
			if (shape == 2 || shape == 4) {
				movePiece(form, -1, 1, 0, 0, 1, 1, 2, 0, true);
				break;
			}
			break;
		case "i":
			if (shape == 1 || shape == 3) {
				movePiece(form, 1, -1, 0, 0, -1, 1, -2, 2, true);
				break;
			}
			if (shape == 2 || shape == 4) {
				movePiece(form, -1, 1, 0, 0, 1, -1, 2, -2, true);
				break;
			}
			break;
		}

	}

	public static void movePiece(Form form, int moveXA, int moveYA, int moveXB, int moveYB, int moveXC, int moveYC,
			int moveXD, int moveYD) {
		// check if rectangles are in the scene after movement
		if (form.a.getX() + moveXA * MOVE >= 0 && form.a.getX() + moveXA * MOVE <= XMAX - SIZE
				&& form.a.getY() + moveYA * MOVE >= 0 && form.a.getY() + moveYA * MOVE <= YMAX - SIZE
				&& form.b.getX() + moveXB * MOVE >= 0 && form.b.getX() + moveXB * MOVE <= XMAX - SIZE
				&& form.b.getY() + moveYB * MOVE >= 0 && form.b.getY() + moveYB * MOVE <= YMAX - SIZE
				&& form.c.getX() + moveXC * MOVE >= 0 && form.c.getX() + moveXC * MOVE <= XMAX - SIZE
				&& form.c.getY() + moveYC * MOVE >= 0 && form.c.getY() + moveYC * MOVE <= YMAX - SIZE
				&& form.d.getX() + moveXD * MOVE >= 0 && form.d.getX() + moveXD * MOVE <= XMAX - SIZE
				&& form.d.getY() + moveYD * MOVE >= 0 && form.d.getY() + moveYD * MOVE <= YMAX - SIZE) {

			// check if rectangles collide with the grid
			if (!collision(form.a, moveXA, moveYA) && !collision(form.b, moveXB, moveYB)
					&& !collision(form.c, moveXC, moveYC) && !collision(form.d, moveXD, moveYD)) {
				form.a.setX(form.a.getX() + MOVE * moveXA);
				form.a.setY(form.a.getY() + MOVE * moveYA);
				form.b.setX(form.b.getX() + MOVE * moveXB);
				form.b.setY(form.b.getY() + MOVE * moveYB);
				form.c.setX(form.c.getX() + MOVE * moveXC);
				form.c.setY(form.c.getY() + MOVE * moveYC);
				form.d.setX(form.d.getX() + MOVE * moveXD);
				form.d.setY(form.d.getY() + MOVE * moveYD);
			}

		}

	}

	public static void movePiece(Form form, int moveXA, int moveYA, int moveXB, int moveYB, int moveXC, int moveYC,
			int moveXD, int moveYD, boolean changeShape) {
		// check if rectangles are in the scene after movement
		if (form.a.getX() + moveXA * MOVE >= 0 && form.a.getX() + moveXA * MOVE <= XMAX - SIZE
				&& form.a.getY() + moveYA * MOVE >= 0 && form.a.getY() + moveYA * MOVE <= YMAX - SIZE
				&& form.b.getX() + moveXB * MOVE >= 0 && form.b.getX() + moveXB * MOVE <= XMAX - SIZE
				&& form.b.getY() + moveYB * MOVE >= 0 && form.b.getY() + moveYB * MOVE <= YMAX - SIZE
				&& form.c.getX() + moveXC * MOVE >= 0 && form.c.getX() + moveXC * MOVE <= XMAX - SIZE
				&& form.c.getY() + moveYC * MOVE >= 0 && form.c.getY() + moveYC * MOVE <= YMAX - SIZE
				&& form.d.getX() + moveXD * MOVE >= 0 && form.d.getX() + moveXD * MOVE <= XMAX - SIZE
				&& form.d.getY() + moveYD * MOVE >= 0 && form.d.getY() + moveYD * MOVE <= YMAX - SIZE) {

			// check if rectangles collide with the grid
			if (!collision(form.a, moveXA, moveYA) && !collision(form.b, moveXB, moveYB)
					&& !collision(form.c, moveXC, moveYC) && !collision(form.d, moveXD, moveYD)) {
				form.a.setX(form.a.getX() + MOVE * moveXA);
				form.a.setY(form.a.getY() + MOVE * moveYA);
				form.b.setX(form.b.getX() + MOVE * moveXB);
				form.b.setY(form.b.getY() + MOVE * moveYB);
				form.c.setX(form.c.getX() + MOVE * moveXC);
				form.c.setY(form.c.getY() + MOVE * moveYC);
				form.d.setX(form.d.getX() + MOVE * moveXD);
				form.d.setY(form.d.getY() + MOVE * moveYD);
				if (changeShape) {
					form.changeShape();
				}
			}

		}

	}

	public static void forcedMovePiece(Form form, int moveXA, int moveYA, int moveXB, int moveYB, int moveXC,
			int moveYC, int moveXD, int moveYD) {

		// move Piece without checking
		form.a.setX(form.a.getX() + MOVE * moveXA);
		form.a.setY(form.a.getY() + MOVE * moveYA);
		form.b.setX(form.b.getX() + MOVE * moveXB);
		form.b.setY(form.b.getY() + MOVE * moveYB);
		form.c.setX(form.c.getX() + MOVE * moveXC);
		form.c.setY(form.c.getY() + MOVE * moveYC);
		form.d.setX(form.d.getX() + MOVE * moveXD);
		form.d.setY(form.d.getY() + MOVE * moveYD);

	}

	public static boolean collision(Rectangle rectangle, int moveX, int moveY) {

		// checks if the space a rectangle wants to move is free
		if (GRID[((int) rectangle.getX()) / SIZE + moveX][((int) rectangle.getY()) / SIZE + moveY] == 1) {
			return true;
		} else {
			return false;
		}
	}

	public static Form makePiece() {
		// generates one of the 7 pieces randomly
		Random rand = new Random();
		int pieceNumber = rand.nextInt(7);
		String name = "";
		Rectangle a = new Rectangle(SIZE - 1, SIZE - 1), b = new Rectangle(SIZE - 1, SIZE - 1),
				c = new Rectangle(SIZE - 1, SIZE - 1), d = new Rectangle(SIZE - 1, SIZE - 1);
		int setX = XMAX + 2 * SIZE;
		int setY = 2 * SIZE;
		switch (pieceNumber) {
		case 0:
			a.setX(setX - SIZE);
			a.setY(setY);
			b.setX(setX);
			b.setY(setY);
			c.setX(setX + SIZE);
			c.setY(setY);
			d.setX(setX + SIZE);
			d.setY(setY + SIZE);
			name = "j";
			break;
		case 1:
			a.setX(setX + SIZE);
			a.setY(setY);
			b.setX(setX);
			b.setY(setY);
			c.setX(setX - SIZE);
			c.setY(setY);
			d.setX(setX - SIZE);
			d.setY(setY + SIZE);
			name = "l";
			break;
		case 2:
			a.setX(setX);
			a.setY(setY);
			b.setX(setX + SIZE);
			b.setY(setY);
			c.setX(setX);
			c.setY(setY + SIZE);
			d.setX(setX + SIZE);
			d.setY(setY + SIZE);
			name = "o";
			break;
		case 3:
			a.setX(setX + SIZE);
			a.setY(setY);
			b.setX(setX);
			b.setY(setY);
			c.setX(setX);
			c.setY(setY + SIZE);
			d.setX(setX - SIZE);
			d.setY(setY + SIZE);
			name = "s";
			break;
		case 4:
			a.setX(setX - SIZE);
			a.setY(setY);
			b.setX(setX);
			b.setY(setY);
			c.setX(setX + SIZE);
			c.setY(setY);
			d.setX(setX);
			d.setY(setY + SIZE);
			name = "t";
			break;
		case 5:
			a.setX(setX - SIZE);
			a.setY(setY);
			b.setX(setX);
			b.setY(setY);
			c.setX(setX);
			c.setY(setY + SIZE);
			d.setX(setX + SIZE);
			d.setY(setY + SIZE);
			name = "z";
			break;
		case 6:
			a.setX(setX - SIZE);
			a.setY(setY);
			b.setX(setX);
			b.setY(setY);
			c.setX(setX + SIZE);
			c.setY(setY);
			d.setX(setX + 2 * SIZE);
			d.setY(setY);
			name = "i";
			break;
		}
		return new Form(a, b, c, d, name);

	}

}
