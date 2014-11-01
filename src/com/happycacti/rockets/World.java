package com.happycacti.rockets;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Rect;

public class World {
	public static final float GRAVITY_ACCELERATION = 0.002f;
	public static final float PLAYER_TERMINAL_VELOCITY = 2.0f;

	private Player player;
	private Camera camera;
	private LevelGenerator generator;
	
	private int score;
	
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
		generator = new LevelGenerator();
	}
	public boolean isInitialized() {
		return initialized;
	}
	public void setup() {
		score = 0;
		generator.reset();
		generator.generate_start(platforms, height);
		player.setX(width/2 - player.getWidth()/2);
		player.setY(platforms.get(0).getY() - player.getHeight());
		/*platforms.add(new Platform(130, -600, 1));
		platforms.add(new Platform(830, -800, 2));
		platforms.add(new Platform(830, -1200, 3));*/
		camera.reset();
		initialized = true;
	}
	/** The size of the screen has changed! Update accordingly */
	public void updateViewSize(int w, int h) {
		// Update the size the camera thinks the world is
		camera.updateSize(w, h);
		generator.updateWidth(w);
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
		if(!initialized) return;
		// Update entities based on time change
		
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
					if(r.isExploding() || !p.collides(rect)) continue;
					
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
						player.setYVel(yvel);
						player.setXVel(xvel);
					}
					rockets.remove(i);
					break;
					// TODO EXPLOSION
				}
			}
		}

		float x = player.getX();
		float y = player.getY() + player.getHeight();

		Rect playerRect = new Rect((int) x, (int) (y),
				(int) (x + player.getWidth()), (int) y + 1);
		for (Platform p : platforms) {
			if ((oldPlayerY + player.getHeight()) <= p.getY() && p.collides(playerRect)) {
				player.landOn(p);
			}
		}
		if(!camera.inBounds(player)) {
			setup();
		}
		// Point the camera at the player (sorta)
		int oldScore = score;
		camera.update(player);
		score = (int) (camera.getRealY(0) - height);
		if(score != oldScore) {
			generator.update(platforms, score);
		}
	}

	public void createRocket(Rocket rocket) {
		synchronized (rockets) {
			rockets.add(rocket);
		}
	}
}
