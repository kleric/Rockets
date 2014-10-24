package com.happycacti.rockets;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameActivity extends Activity implements SurfaceHolder.Callback, View.OnTouchListener {
	
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

		//set the view properly
		setContentView(canvasView);
		canvasView.setOnTouchListener(this);
		
		canvasView.getHolder().addCallback(this);
		
	}
	@Override
	public void onStart() {
		super.onStart();
		
		//start the game thread
		gameThread = new Thread(game);
		gameThread.start();
	}
	@Override
	public void onStop() {
		super.onStop();
		game.stop();
	}
	/**
	 * Game class is literally the game
	 * deal with it.
	 * @author clark
	 *
	 */
	class Game implements Runnable {
		boolean running;
		long lastTime;
		
		/** World that holds all of our entities */
		private World world;
		
		/** Width of the SurfaceView */
		private int canvasWidth;
		
		/** Height of the SurfaceView */
		private int canvasHeight;
		
		public Game() {
			world = new World();
		}
		/** Initialize a new game! */
		public void setup() {
			if(!world.isInitialized())
				world.setup();
		}
		
		@Override
		/** Handles the running the game (this should be handled by threads, not called) */
		public void run() {
			//game loop
			lastTime = System.currentTimeMillis();
			running = true;
			while(running) {
				long currentTime = System.currentTimeMillis();
				int delta = (int)(currentTime - lastTime);
				
				if(delta > UPDATE_DELAY) {
					lastTime = currentTime;
					tryDraw();
					update(delta);
				}
			}
		}
		/** Stops execution of the game. */
		public void stop() {
			running = false;
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
			world.draw(canvas);
		}
		
		/** Update the state of the game (e.g. processing frame) */
		private void update(int delta) {
			world.update(delta);
		}
		
		/** Update what the size of the canvas is */
		public void updateCanvasSize(int width, int height) {
			canvasHeight = height;
			canvasWidth = width;
			
			world.updateViewSize(canvasWidth, canvasHeight);
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
		game.updateCanvasSize(width, height);
		game.setup();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			game.world.press(event.getX(), event.getY());
			return true;
		}
		return false;
	}
}
