package com.happycacti.rockets;

import android.graphics.Canvas;

public interface Entity {
	public void update(int delta);
	public abstract void draw(Canvas canvas, Camera camera);
	public float getX();
	public float getY();
}
