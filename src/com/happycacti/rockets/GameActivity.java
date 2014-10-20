package com.happycacti.rockets;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameActivity extends Activity implements SurfaceHolder.Callback {
	
	/**
	 * Limits how often we update
	 * 
	 * in other words we only update every UPDATE_DELAY 
	 * milliseconds.
	 * 
	 * It's a bad name, 
	 */
	private static final int UPDATE_DELAY = 10;
	
	/** SurfaceView that we will be doing the drawing on */
	private SurfaceView canvasView;
	
	private SurfaceHolder canvasViewHolder;
	
	/** Thread that we are running our game on */
	private Thread gameThread;
	
	/** Runnable that handles the game logic and drawing */
	private Game game;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//let's get things started
		canvasView = new SurfaceView(this);
		game = new Game();
		gameThread = new Thread(game);

		//set the view properly
		setContentView(canvasView);
		
		canvasView.getHolder().addCallback(this);
	}
	@Override
	public void onStart() {
		super.onStart();
		
		//start the game thread
		gameThread.start();
	}
	class Game implements Runnable {
		boolean running = true;
		long lastTime;
		
		@Override
		public void run() {
			//game loop
			lastTime = System.currentTimeMillis();
			while(running) {
				long currentTime = System.currentTimeMillis();
				int delta = (int)(currentTime - lastTime);
				
				if(delta > UPDATE_DELAY) {
					tryDraw();
					update(delta);
				}
				//TODO game loop.
			}
		}
		/**
		 * Attempted to get a lock on the SurfaceHolder
		 * then if is successful begins the drawing
		 * process by locking the SurfaceHolder and
		 * then drawing.
		 */
		private void tryDraw() {
			if(canvasViewHolder == null) return;
			
			Canvas c = canvasViewHolder.lockCanvas();
			
			if(c == null) return;
			
			draw(c);
			canvasViewHolder.unlockCanvasAndPost(c);
		}
		/**
		 * Given a proper canvas, will try the current
		 * state of the game.
		 * 
		 * @param canvas
		 * The canvas that you will draw on. Must not be null.
		 */
		private void draw(Canvas canvas) {
			
		}
		
		private void update(int delta) {
			
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		canvasViewHolder = holder;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		canvasViewHolder = holder;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {}
}
