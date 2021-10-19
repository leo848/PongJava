package io.github.leo848;

import javax.swing.*;

public class PongFrame extends JFrame {
	
	final PongCanvas canvas;
	
	public PongFrame(GameLoop gameLoop) {
		canvas = new PongCanvas(gameLoop, new Vector(500,500));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(canvas);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		addKeyListener(canvas);
	}
	
	public void repaintCanvas() {
		canvas.repaint();
	}
}
