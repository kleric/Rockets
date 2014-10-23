package com.happycacti.rockets;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Rocket implements Entity {
	public static final float ROCKET_SPEED = 20.2f;
	private float x;
	private float y;
	
	private float xvel;
	private float yvel;
	
	Paint paint = new Paint();
	
	public Rocket(float x, float y, float xvel, float yvel) {
		this.x = x;
		this.y = y;
		
		this.xvel = xvel;
		this.yvel = yvel;
		
		paint.setARGB(255, 0, 40, 128);
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
		canvas.drawRect(x, camera.getRelativeY(y), x + 16, camera.getRelativeY(y) + 16, paint);
		
	}
	
	
}
