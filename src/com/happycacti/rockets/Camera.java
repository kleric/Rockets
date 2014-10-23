package com.happycacti.rockets;

public class Camera {
	private static float SPACE_ABOVE = 0.4f;
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
