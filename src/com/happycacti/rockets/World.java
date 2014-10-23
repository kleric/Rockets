package com.happycacti.rockets;

import android.graphics.Canvas;

public class World {
	private Player player;
	private Camera camera;
	
	private int width, height;
	
	public World() {
		player = new Player();
		camera = new Camera();
	}
	/** The size of the screen has changed! Update accordingly */
	public void updateViewSize(int w, int h) {
		//Update the size the camera thinks the world is
		camera.updateSize(w, h);
		width = w;
		height = h;
	}
	/** render the world onto the canvas */
	public void draw(Canvas canvas) {
		canvas.drawRGB(127, 127, 127);
		player.draw(canvas, camera);
	}
	
	public void update(int delta) {
		//Update entities based on time change
		player.update(delta);
		
		//Point the camera at the player (sorta)
		camera.update(player);
	}
}
