package io.github.leo848;

import java.awt.*;
import java.util.*;

public class Ball {
	final Vector vel;
	final Vector pos;
	final int radius;
	private final GameLoop gameLoop;
	private final Vector acc;
	
	float speed = 1;
	
	public Ball(GameLoop gameLoop, int x, int y) {
		this(gameLoop, new Vector(x, y));
	}
	
	public Ball(GameLoop gameLoop, Vector pos) {
		this.gameLoop = gameLoop;
		
		this.pos = pos;
		this.vel = new Vector(1, 0.2f);
		this.acc = new Vector();
		
		this.radius = 20;
	}
	
	public boolean checkForGameOver(ArrayList<Slider> sliders) {
		for (Slider slider: sliders) {
			if ((pos.x < (slider.pos.x + slider.size.x / 2) && slider.dir.equals(Directions.RIGHT)) ||
			    (pos.x > (slider.pos.x + slider.size.x / 2) && slider.dir.equals(Directions.LEFT)))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void show(Graphics2D g2D) {
		g2D.setColor(Color.cyan);
		g2D.fillOval((int) pos.x - radius, (int) pos.y - radius, radius * 2, radius * 2);
		// g2D.drawString(pos.x + ", " + pos.y, pos.x, pos.y);
	}
	
	public void update(ArrayList<Slider> sliders) {
		wallCollision();
		sliders.forEach(this::boxCollision);
		
		vel.add(acc);
		
		System.out.println("speed = " + speed);
		
		vel.limit(69 * 420 * .00034506556247f * speed); // nice
		if (vel.mag() < 1) vel.setMag(1.3f * speed);
		pos.add(vel.copy().mult(speed));
	}
	
	private void wallCollision() {
		if (pos.y - radius < 0) { // y collision
			vel.y *= -1;
			pos.y = radius + 1;
			
			increaseSpeed(1);
		} else if (pos.y + radius > gameLoop.pongFrame.canvas.size.y) {
			vel.y *= -1;
			pos.y = gameLoop.pongFrame.canvas.size.y - radius - 1;
			increaseSpeed(1);
		}
		
		if (pos.x - radius < 0) { // x collision, partially unnecessary
			vel.x *= -1;
			pos.x = radius + 1;
		} else if (pos.x + radius > gameLoop.pongFrame.canvas.size.x) {
			vel.x *= -1;
			pos.x = gameLoop.pongFrame.canvas.size.x - radius - 1;
		}
	}
	
	private void increaseSpeed(int percent) {
		speed *= ((float) (100 + percent) / 100);
	}
	
	void boxCollision(Slider box) {
		if (pos.y + radius > box.pos.y &&
		    pos.y - radius < box.pos.y + box.size.y &&
		    pos.x + radius > box.pos.x &&
		    pos.x - radius < box.pos.x + box.size.x) // box collision
		{
			pos.x = box.pos.x +
			        ((box.dir.x + 1) / 2) * box.size.x +
			        (radius + 1) * box.dir.x; // calc direction to place ball
			acc.set(box.vel.copy().div(500));
			
			vel.x *= -1;
			increaseSpeed(2);
		}
	}
}
