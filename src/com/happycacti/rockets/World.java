package com.happycacti.rockets;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Rect;

public class World {
	public static final float GRAVITY_ACCELERATION = 0.002f;
	public static final float PLAYER_TERMINAL_VELOCITY = 2.0f;

	private Player player;
	private Camera camera;

	private int width, height;

	private ArrayList<Platform> platforms;
	private ArrayList<Rocket> rockets;
	
	private boolean initialized = false;

	public void press(float x, float y) {
		player.act(this, camera.getRealX(x), camera.getRealY(y));
	}

	public World() {
		player = new Player();
		camera = new Camera();
		platforms = new ArrayList<Platform>();
		rockets = new ArrayList<Rocket>();
	}
	public boolean isInitialized() {
		return initialized;
	}
	public void setup() {
		initialized = true;
		addStartingPlatform();
		
		player.setX(width/2 - player.getWidth()/2);
		player.setY(platforms.get(0).getY() - player.getHeight());
		platforms.add(new Platform(130, -600, 1));
		platforms.add(new Platform(830, -800, 2));
		platforms.add(new Platform(830, -1200, 3));
		camera.reset();
	}
	private void addStartingPlatform() {
		platforms.clear();
		Platform ground = new Platform(0, 0, 0);
		
		float x, y;
		x = (width/2) - ground.getWidth()/2;
		y = -ground.getHeight() - (height * 0.1f);
		ground.move(x, y);
		platforms.add(ground);
	}
	/** The size of the screen has changed! Update accordingly */
	public void updateViewSize(int w, int h) {
		// Update the size the camera thinks the world is
		camera.updateSize(w, h);
		width = w;
		height = h;
	}

	/** render the world onto the canvas */
	public void draw(Canvas canvas) {
		canvas.drawRGB(127, 127, 127);
		player.draw(canvas, camera);

		synchronized (rockets) {
			for (Rocket r : rockets) {
				r.draw(canvas, camera);
			}
		}
		for (Platform p : platforms) {
			p.draw(canvas, camera);
		}
	}

	public void update(int delta) {
		// Update entities based on time change
		float oldPlayerX = player.getX();
		float oldPlayerY = player.getY();
		
		player.update(delta);
		player.wrap(width);
		synchronized (rockets) {
			for (int i = rockets.size() - 1; i >= 0; i--) {
				Rocket r = rockets.get(i);
				r.update(delta);
				if(!camera.inBounds(r)) {
					rockets.remove(i);
					continue;
				}
				Rect rect = new Rect((int) r.getX(), (int) r.getY(),
						(int) (r.getX() + r.getWidth()),
						(int) (r.getY() + r.getHeight()));

				for (Platform p : platforms) {
					if (!r.isExploding() && p.collides(rect)) {
						float dx = r.getX() - (player.getX() + player.getWidth()/2);
						float dy = player.getY() + player.getHeight() - r.getY();

						float dist = (float) Math.sqrt(dx * dx + dy * dy);
						if(dist < r.getWeakExplosionRadius()) {
							float expforce = (dist < r.getStrongExplosionRadius()) ? r.getStrongExplosionForce() : r.getWeakExplosionForce();
							float force = (float) expforce;

							float angle = (float) Math.atan(dx/dy);
							float xvel = (float) (Math.sin(angle) * force);
							float yvel = (float) (Math.cos(angle) * force);

							if(dy < 0) {
								yvel *= -1;
							}
							else {
								xvel *= -1;
							}
							//player.push(xvel, yvel);
							player.setYVel(yvel);
							player.setXVel(xvel);
						}
						rockets.remove(i);
						// TODO EXPLOSION
					}
				}
			}
		}

		float x = player.getX();
		float y = player.getY() + player.getHeight();
		Rect oldPlayerRect = new Rect((int) oldPlayerX, (int) oldPlayerY, (int) (oldPlayerX + player.getWidth()), (int)(oldPlayerY + player.getHeight()));
		Rect playerRect = new Rect((int) x, (int) (y - 1),
				(int) (x + player.getWidth()), (int) y);
		for (Platform p : platforms) {
			if (!p.collides(oldPlayerRect) && p.collides(playerRect)) {
				player.landOn(p);
			}
		}
		if(!camera.inBounds(player)) {
			//player = new Player();
			player.kill();
			//camera.reset();
		}
		// Point the camera at the player (sorta)
		camera.update(player);
	}
	
	public void createRocket(Rocket rocket) {
		synchronized (rockets) {
			rockets.add(rocket);
		}
	}
}
