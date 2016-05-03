package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

public class Shield extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 5;
	
	private int shieldWidth = 29;
	private int shieldHeight = 34;
	private int speed = DEFAULT_SPEED;

	private Random rand = new Random();
	
	/**
	 * Crates a new shield at a random x location at the top of the screen 
	 * @param screen the game screen
	 */
	public Shield(GameScreen screen){
		this.setLocation(
        		rand.nextInt(screen.getWidth() - shieldWidth),
        		-shieldHeight);
		this.setSize(shieldWidth, shieldHeight);
	}
	
	public int getshieldWidth() {
		return shieldWidth;
	}
	public int getshieldHeight() {
		return shieldHeight;
	}

	/**
	 * Returns the current shield speed
	 * @return the current shield speed
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * Set the current shield speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	 * Returns the default shield speed.
	 * @return the default shield speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
}
