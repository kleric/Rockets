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
		y = 0;
		
		yvel = 0;
		xvel = 0;
		
		grounded = false;
		
		paint.setARGB(255, 255, 0, 0);
	}
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
	public void draw(Canvas canvas, Camera camera) {
		float x = this.x;
		float y = camera.getRelativeY(this.y);
	
		int width = 40;
		int height = 100;
		
		canvas.drawRect(x, y, x + width, y + height, paint);
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public void jump() {
		if(grounded) {
			grounded = false;
			yvel = JUMP_VELOCITY;
		}
	}
	public void press(World world, float x, float y) {
		if(grounded) {
			jump();
		}
		else {
			shoot(world, x, y);
		}
	}
	public boolean isGrounded() {
		return grounded;
	}
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
