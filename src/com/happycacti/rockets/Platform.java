package com.happycacti.rockets;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Platform implements Entity {
	private float x;
	private float y;
	
	private float xvel;
	private float yvel;
	
	Paint paint = new Paint();
	
	public Platform(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void update(int delta) {
		
	}

	public boolean collides(Rect rect) {
		Rect r = new Rect((int)x, (int)y, (int)(x + getWidth()),(int) (y + getHeight()));
		
		return rect.intersect(r);
	}
	public float getY() {
		return y;
	}
	public float getWidth() {
		return 120;
	}
	public float getHeight() {
		return 30;
	}
	@Override
	public void draw(Canvas canvas, Camera camera) {
		paint.setARGB(255, 0, 127, 0);
		canvas.drawRect(camera.getRelativeX(x), camera.getRelativeY(y), camera.getRelativeX(x) + getWidth(), camera.getRelativeY(y) + 30, paint);
	}
}
