package com.happycacti.rockets;

import java.util.ArrayList;

import android.graphics.Canvas;

public class World {
	public static final float GRAVITY_ACCELERATION = 0.001f;
	public static final float PLAYER_TERMINAL_VELOCITY = 0.8f;

	private Player player;
	private Camera camera;

	private int width, height;

	private ArrayList<Platform> platforms;
	private ArrayList<Rocket> rockets;

	public void press(float x, float y) {
		player.press(this, camera.getRealX(x), camera.getRealY(y));
	}
	public World() {
		player = new Player();
		camera = new Camera();
		platforms = new ArrayList<Platform>();
		rockets = new ArrayList<Rocket>();
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

		synchronized(rockets) {
			for(Rocket r : rockets) {
				r.draw(canvas, camera);
			}
		}
	}

	public void update(int delta) {
		//Update entities based on time change
		player.update(delta);

		synchronized(rockets) {
			for(Rocket r : rockets) {
				r.update(delta);
			}
		}

		//Point the camera at the player (sorta)
		camera.update(player);
	}
	public void createRocket(Rocket rocket) {
		synchronized(rockets) {
			rockets.add(rocket);
		}
	}
}
