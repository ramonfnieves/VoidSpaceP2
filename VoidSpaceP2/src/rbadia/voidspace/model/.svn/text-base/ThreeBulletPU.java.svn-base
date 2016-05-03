package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

public class ThreeBulletPU extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 5;
	
	private int threeBulletWidth = 29;
	private int threeBulletHeight = 34;
	private int speed = DEFAULT_SPEED;

	private Random rand = new Random();
	
	/**
	 * Crates a new life at a random x location at the top of the screen 
	 * @param screen the game screen
	 */
	public ThreeBulletPU(GameScreen screen){
		this.setLocation(
        		rand.nextInt(screen.getWidth() - threeBulletHeight),
        		-threeBulletWidth);
		this.setSize(threeBulletWidth, threeBulletHeight);
	}
	
	public int getthreeBulletHeight() {
		return threeBulletHeight;
	}
	public int getthreeBulletWidth() {
		return threeBulletWidth;
	}

	/**
	 * Returns the current life speed
	 * @return the current life speed
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * Set the current life speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	 * Returns the default life speed.
	 * @return the default life speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
}
