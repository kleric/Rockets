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
	private boolean alive;
	
	Paint paint = new Paint();
	public void setYVel(float yvel) {
		this.yvel = yvel;
	}
	public void setXVel(float xvel) {
		this.xvel = xvel;
	}
	public Player() {
		x = 500;
		y = 900;
		
		alive = true;
		
		yvel = 0;
		xvel = 0;
		
		grounded = false;
		
		paint.setARGB(255, 255, 0, 0);
	}
	public boolean isAlive() {
		return alive;
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
		if(platform == null) {
			yvel = 0;
			xvel = 0;
			grounded = true;
			return;
		}
		if(yvel >= 0) {
			yvel = 0; 
			xvel = 0;
			y = platform.getY() - getHeight();
			grounded = true;
		}
	}
	public void kill() {
		alive = false;
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
	public void wrap(float screenWidth) {
		if(x > screenWidth - (getWidth()/2)) {
			x = -getWidth()/2;
		}
		if(x < -getWidth()/2) {
			x = screenWidth - (getWidth()/2);
		}
	}
	public void push(float xvel, float yvel) {
		this.xvel += xvel;
		this.yvel += yvel;
	}
	/** Makes the player shoot */
	public void shoot(World world, float x, float y) {
		float deltax = (this.x + this.getWidth()/2) - x;
		float deltay = y - (this.y + this.getHeight()/2);
		
		float angle = (float) Math.atan(deltax/deltay);
		float xvel = (float) (Math.sin(angle) * Rocket.ROCKET_SPEED) + this.xvel;
		float yvel = (float) (Math.cos(angle) * Rocket.ROCKET_SPEED) + this.yvel;
		
		if(deltay < 0) {
			yvel *= -1;
		}
		else {
			xvel *= -1;
		}
		this.xvel -= xvel * 0.005f;
		this.yvel -= yvel * 0.005f;
		
		world.createRocket(new Rocket((this.x + this.getWidth()/2), (this.y + this.getHeight()/2), xvel, yvel));
	}
	public void setX(float x) {
		this.x = x;
	}
	public void setY(float y) {
		this.y = y;
	}
}
