package com.happycacti.rockets;

import android.graphics.Canvas;

public abstract class Entity {
	private float x;
	private float y;
	
	private float xvel;
	private float yvel;
	
	private float xaccel;
	private float yaccel;
	
	public void update(int delta) {
		x += xvel;
		y += yvel;
		
		xvel += xaccel;
		yvel += yaccel;
	}
	public abstract void draw(Canvas canvas, Camera camera);
	
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public void setX(float x) {
		this.x = x;
	}
	public void setY(float y) {
		this.y = y;
	}
}
