package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Form {
	// defines a piece out of four rectangles
	Rectangle a;
	Rectangle b;
	Rectangle c;
	Rectangle d;
	Color color;
	private String name;
	public int shape = 1;

	public Form(Rectangle a, Rectangle b, Rectangle c, Rectangle d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	public Form(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String name) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.name = name;

		switch (name) {
		case "j":
			color = Color.BLUE;
			break;
		case "l":
			color = Color.GOLDENROD;
			break;
		case "o":
			color = Color.LIGHTGREEN;
			break;
		case "s":
			color = Color.DARKVIOLET;
			break;
		case "t":
			color = Color.ORANGERED;
			break;
		case "z":
			color = Color.YELLOWGREEN;
			break;
		case "i":
			color = Color.BISQUE;
			break;
		}
		this.a.setFill(color);
		this.b.setFill(color);
		this.c.setFill(color);
		this.d.setFill(color);
	}

	public String getName() {
		return name;
	}

	public void changeShape() {
		if (shape != 4) {
			shape++;
		} else {
			shape = 1;
		}
	}
}
