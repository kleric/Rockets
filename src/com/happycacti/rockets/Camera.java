package com.happycacti.rockets;

public class Camera {
	private static float SPACE_ABOVE = 0.3f;
	private float x;
	private float y; 
	
	private int width;
	private int height;
	
	public Camera() {
		x = 0;
		y = 0;
		width = 0;
		height = 0;
	}
	public boolean inBounds(Entity e) {
		float rely = getRelativeY(e.getY());
		float relx = getRelativeX(e.getX());
		if(relx < e.getWidth()/2 || relx > (width - e.getWidth()/2) || rely < 0 || rely > height) {
			return false;
		}
		return true;
	}
	public void reset() {
		x = 0;
		y = -height;
	}
	public Camera(int w, int h) {
		x = 0;
		y = 0;
		
		width = w;
		height = h;
	}
	public float getRelativeX(float x) {
		return x - this.x;
	}
	public float getRelativeY(float y) {
		return y - this.y;
	}
	public float getRealX(float x) {
		return x + this.x;
	}
	public float getRealY(float y) {
		return y + this.y;
	}
	public void updateSize(int w, int h) {
		width = w;
		height = h;
	}
	
	public void update(Player p) {
		//Check if it has passed region where we start
		//Moving the camera
		float void_height = SPACE_ABOVE * height;
		if(p.getY() < y + void_height) {
			//yes.
			y = p.getY() - void_height;
		}
	}
}
