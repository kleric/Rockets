package com.happycacti.rockets;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Platform implements Entity {
	private float x;
	private float y;

	private float xvel;
	private float yvel;

	/** which platform it is 0 is the starting platform*/
	private int platform;

	Paint paint = new Paint();

	public Platform(float x, float y) {
		this.x = x;
		this.y = y;
		platform = 1;
	}
	public Platform(float x, float y, int type) {
		this.x = x;
		this.y = y;
		platform = type;
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
		switch(platform) {
		case 0:
			return 2000;
		case 1:
			return 160;
		case 2:
			return 200;
		case 3:
			return 300;
		}
		return 0;
	}
	public float getHeight() {
		return 100;
	}
	@Override
	public void draw(Canvas canvas, Camera camera) {
		switch(platform) {
		case 3:
			paint.setARGB(255, 0, 127, 255);
			break;
		case 1:
			paint.setARGB(255, 0, 127, 0);
			break;
		case 0:
			paint.setARGB(255,0, 255, 50);
			break;
		}
		canvas.drawRect(camera.getRelativeX(x), camera.getRelativeY(y), camera.getRelativeX(x) + getWidth(), camera.getRelativeY(y) + getHeight(), paint);
	}

	@Override
	public float getX() {
		return x;
	}
	public void move(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
