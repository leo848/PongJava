package io.github.leo848;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import static io.github.leo848.Directions.*;
import static java.awt.event.KeyEvent.*;

public class PongCanvas extends JPanel implements KeyListener {
	final Vector size;
	final HashMap<Integer, Boolean> keysDown = new HashMap<>();
	private final GameLoop gameLoop;
	Ball ball;
	ArrayList<Slider> sliders = new ArrayList<>();
	
	public PongCanvas(GameLoop gameLoop, Vector size) {
		setPreferredSize(new Dimension((int) size.x, (int) size.y));
		this.size = size;
		this.gameLoop = gameLoop;
		
		
		ball = new Ball(gameLoop, 250, 250);
		
		sliders.add(new Slider(25, 50, 25, 100, RIGHT));
		sliders.add(new Slider((int) (size.x - 50), 50, 25, 100, LEFT));
		
		addKeyListener(this);
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		
		g2D.setColor(new Color(0x0));
		g2D.fillRect(0, 0, (int) size.x, (int) size.y);
		
		if (ball.checkForGameOver(sliders)){
			g2D.setColor(new Color(0xffffff));
			g2D.drawString("Game over keine ahnung wer gewonen hat", 100, 100);
			// gameLoop.stop();
			
			g2D.setColor(new Color(0xf));
		}
		
		
		ball.update(sliders);
		sliders.forEach(Slider::update);
		
		sliders.get(0).updateKeys(keysDown, VK_W, VK_S, ball.speed/3);
		sliders.get(1).updateKeys(keysDown, VK_UP, VK_DOWN, ball.speed/3);
		
		ball.show(g2D);
		sliders.forEach(b -> b.show(g2D));
	}
	
	@Override
	public void keyTyped(KeyEvent ignored) {
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keysDown.put(e.getKeyCode(), true);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		keysDown.put(e.getKeyCode(), false);
	}
}
