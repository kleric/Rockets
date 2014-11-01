/**
 * @author Clark
 * 
 * Level generation class
 * 
 * Manages all the level generation and creates the platforms
 */
package com.happycacti.rockets;

import java.util.ArrayList;

public class LevelGenerator {
	private int nextPlatform;
	private int height;
	
	public static final int MAX_PLATFORM_DISTANCE = 400;
	public static final int MIN_PLATFORM_DISTANCE = 300;
	
	private int width;
	
	public LevelGenerator() {
		nextPlatform = 0;
		height = 0;
	}
	public void updateWidth(int width) {
		this.width = width;
	}
	public void generate_start(ArrayList<Platform> platforms, int screen_height) {
		platforms.clear();
		Platform ground = new Platform(0, 0, 0);

		float x, y;
		x = (width/2) - ground.getWidth()/2;
		y = -ground.getHeight() - (screen_height * 0.1f);
		ground.move(x, y);
		platforms.add(ground);
		
		plan_next_platform();
		
		while(height > -2 * screen_height) {
			update(platforms, -nextPlatform + height);
		}
		height = 0;
	}
	public void reset() {
		height = 0;
	}
	private void plan_next_platform() {
		nextPlatform = (int) ((Math.random() * (MAX_PLATFORM_DISTANCE - MIN_PLATFORM_DISTANCE)) + MIN_PLATFORM_DISTANCE);
	}
	public void update(ArrayList<Platform> platforms, int new_height) {
		if(new_height < height)
			nextPlatform += new_height - height;
		
		height = new_height;
		
		if(nextPlatform <= 0) {
			plan_next_platform();
			
			int platformType = 3;
			
			Platform p = new Platform(0, 0, platformType);
			
			float x = (float) (Math.random() * (width - p.getWidth()));
			p.move(x, height);
			platforms.add(p);
		}
	}
	
}
