package io.github.leo848;

import java.awt.*;
import java.util.*;

public class Slider {
	final Vector pos;
	final Vector vel;
	final Vector size;
	final Vector dir;
	
	public Slider(int x, int y, int width, int height, Vector dir) {
		this.pos = new Vector(x, y);
		this.vel = new Vector();
		
		this.dir = dir;
		this.size = new Vector(width, height);
	}
	
	public void show(Graphics2D g2D) {
		g2D.setStroke(new BasicStroke(5));
		g2D.setColor(new Color(16777215));
		
		g2D.drawRect((int) pos.x, (int) pos.y, (int) size.x, (int) size.y);
	}
	
	public void update() {
		vel.limit(9.8f).mult(.98f);
		pos.add(vel);
	}
	
	public void updateKeys(HashMap<Integer, Boolean> keysDown, int keyUp, int keyDown) {
		if (keysDown.get(keyUp) != null && keysDown.get(keyUp)) {
			vel.add(0, -.1f);
		}
		if (keysDown.get(keyDown) != null && keysDown.get(keyDown)) {
			vel.add(0, .1f);
		}
	}
}
