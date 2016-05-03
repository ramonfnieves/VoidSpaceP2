package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

/**
 * Represents a ship/space craft.
 *
 */
public class SuicideBomber extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 5;
	
	private int shipWidth = 21;
	private int shipHeight = 25;
	private int speed = DEFAULT_SPEED;
	
	/**
	 * Creates a new ship at the default initial location. 
	 * @param screen the game screen
	 */
	public SuicideBomber(GameScreen screen){
		Random rand=new Random();
		this.setLocation(rand.nextInt(screen.getWidth()-shipWidth),
				 -shipHeight);
		this.setSize(shipWidth, shipHeight);
	}
	
	/**
	 * Get the default ship width
	 * @return the default ship width
	 */
	public int getSuicideShipWidth() {
		return shipWidth;
	}
	
	/**
	 * Get the default ship height
	 * @return the default ship height
	 */
	public int getSuicideShipHeight() {
		return shipHeight;
	}
	
	/**
	 * Returns the current ship speed
	 * @return the current ship speed
	 */
	public int getSuicideSpeed() {
		return speed;
	}
	
	/**
	 * Set the current ship speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	 * Returns the default ship speed.
	 * @return the default ship speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
	
}
