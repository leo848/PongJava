package io.github.leo848;

import java.awt.*;
import java.util.*;

public class Ball {
	final Vector vel;
	final Vector pos;
	final int radius;
	private final GameLoop gameLoop;
	
	public Ball(GameLoop gameLoop, int x, int y) {
		this(gameLoop, new Vector(x, y));
	}
	
	public Ball(GameLoop gameLoop, Vector pos) {
		this.gameLoop = gameLoop;
		this.pos = pos;
		this.vel = new Vector(1, 0.2f);
		this.radius = 20;
	}
	
	public void show(Graphics2D g2D) {
		g2D.setColor(Color.cyan);
		g2D.fillOval((int) pos.x - radius, (int) pos.y - radius, radius * 2, radius * 2);
		g2D.drawString(pos.x + ", " + pos.y, pos.x, pos.y);
	}
	
	public void update(ArrayList<Slider> boxes) {
		wallCollision();
		boxes.forEach(this::boxCollision);
		
		System.out.println("vel.mag() = " + vel.mag());
		vel.limit(69 * 420 * .00034506556247f); // nice
		if (vel.mag() < 1) vel.setMag(1.3f);
		pos.add(vel);
	}
	
	private void wallCollision() {
		if (pos.y - radius < 0) {
			vel.y *= -1;
			pos.y = radius + 1;
		} else if (pos.y + radius > gameLoop.pongFrame.canvas.size.y) {
			vel.y *= -1;
			pos.y = gameLoop.pongFrame.canvas.size.y - radius - 1;
		}
		
		if (pos.x - radius < 0) {
			vel.x *= -1;
			pos.x = radius + 1;
		} else if (pos.x + radius > gameLoop.pongFrame.canvas.size.x) {
			vel.x *= -1;
			pos.x = gameLoop.pongFrame.canvas.size.x - radius - 1;
		}
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
			vel.add(box.vel);
			vel.x *= -1;
		}
	}
}
