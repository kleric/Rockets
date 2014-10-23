package com.happycacti.rockets;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Player extends Entity {
	Paint paint = new Paint();
	
	public Player() {
		setY(-60);
	}
	@Override
	public void draw(Canvas canvas, Camera camera) {
		paint.setARGB(255, 255, 0, 0);
		
		float x = getX();
		float y = camera.getRelativeY(getY());
	
		int width = 40;
		int height = 100;
		
		canvas.drawRect(x, y, x + width, y + height, paint);
	}
}
