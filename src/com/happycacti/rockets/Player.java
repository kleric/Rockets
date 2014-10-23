package com.happycacti.rockets;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Player implements Entity {
	private static final float JUMP_VELOCITY = -0.5f;
	private float x;
	private float y;
	
	private float xvel;
	private float yvel;
	
	private boolean grounded;
	
	Paint paint = new Paint();
	
	public Player() {
		x = 500;
		y = 200;
		
		yvel = 0;
		xvel = 0;
		
		grounded = false;
		
		paint.setARGB(255, 255, 0, 0);
	}
	/**
	 * Update the player given a delta of time
	 */
	public void update(int delta) {
		y += delta * yvel;
		x += delta * xvel;
		
		yvel += delta * World.GRAVITY_ACCELERATION;
		
		if(yvel > World.PLAYER_TERMINAL_VELOCITY) {
			yvel = World.PLAYER_TERMINAL_VELOCITY;
		}
		
		if(y > 320) {
			y = 320;
			yvel = 0;
			grounded = true;
		}
		
	}
	@Override
	/** Render the player given a camera and canvas */
	public void draw(Canvas canvas, Camera camera) {
		float x = this.x;
		float y = camera.getRelativeY(this.y);
	
		int width = (int) getWidth();
		int height = (int) getHeight();
		
		canvas.drawRect(x, y, x + width, y + height, paint);
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	/** Makes the player jump (if he can) */
	public void jump() {
		if(grounded) {
			grounded = false;
			yvel = JUMP_VELOCITY;
		}
	}
	/** The player just hit a platform */
	public void landOn(Platform platform) {
		if(yvel >= 0) {
			yvel = 0; 
			y = platform.getY() - getHeight();
			grounded = true;
		}
	}
	public float getHeight() {
		return 100;
	}
	public float getWidth() {
		return 40;
	}
	/** Makes the player take action (e.g. jump or shoot) */
	public void act(World world, float x, float y) {
		if(grounded) {
			jump();
		}
		else {
			shoot(world, x, y);
		}
	}
	/** Check if the player is on a platform */
	public boolean isGrounded() {
		return grounded;
	}
	
	/** Makes the player shoot */
	public void shoot(World world, float x, float y) {
		float deltax = this.x - x;
		float deltay = y - this.y;
		
		float angle = (float) Math.atan(deltax/deltay);
		float xvel = (float) (Math.sin(angle) * Rocket.ROCKET_SPEED);
		float yvel = (float) (Math.cos(angle) * Rocket.ROCKET_SPEED);
		
		if(deltay < 0) {
			yvel *= -1;
		}
		else {
			xvel *= -1;
		}
		
		
		world.createRocket(new Rocket(this.x, this.y, xvel, yvel));
	}
}
