package com.happycacti.rockets;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Rocket implements Entity {
	public static final float ROCKET_SPEED = 30.2f;
	private float x;
	private float y;
	
	private float xvel;
	private float yvel;
	
	private boolean exploding = false;
	Paint paint = new Paint();
	public boolean isExploding() {
		return exploding;
	}
	public Rocket(float x, float y, float xvel, float yvel) {
		this.x = x;
		this.y = y;
		
		this.xvel = xvel;
		this.yvel = yvel;
		
		paint.setARGB(255, 0, 40, 128);
	}
	public void explode() {
		xvel = 0;
		yvel = 0;
		exploding = true;
	}
	public float getStrongExplosionRadius() {
		return 75;
		
	}
	public float getWeakExplosionRadius() {
		return 150;
	}
	public float getStrongExplosionForce() {
		return 1.3f;
	}
	public float getWeakExplosionForce() {
		return 0.7f;
	}
	@Override
	public void update(int delta) {
		y += yvel;
		x += xvel;
		
		//Depending on if we want rockets to be affected by gravity
		yvel += World.GRAVITY_ACCELERATION;
	}
	@Override
	public void draw(Canvas canvas, Camera camera) {
		canvas.drawRect(x, camera.getRelativeY(y), x + getWidth(), camera.getRelativeY(y) + getHeight(), paint);
		
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public float getWidth() {
		return 16;
	}
	public float getHeight() {
		return 16;
	}
}
