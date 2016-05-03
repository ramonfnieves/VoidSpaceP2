package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

public class Life extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 5;
	
	private int lifeWidth = 17;
	private int lifeHeight = 15;
	private int speed = DEFAULT_SPEED;

	private Random rand = new Random();
	
	/**
	 * Crates a new life at a random x location at the top of the screen 
	 * @param screen the game screen
	 */
	public Life(GameScreen screen){
		this.setLocation(
        		rand.nextInt(screen.getWidth() - lifeWidth),
        		-lifeHeight);
		this.setSize(lifeWidth, lifeHeight);
	}
	
	public int getlifeWidth() {
		return lifeWidth;
	}
	public int getlifeHeight() {
		return lifeHeight;
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
