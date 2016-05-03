package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

/**
 * Represents a ship/space craft.
 *
 */
public class EnemyShipHard extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 3;
	
	private int shipWidth = 33;
	private int shipHeight = 37;
	private int speed = DEFAULT_SPEED;
	
	/**
	 * Creates a new ship at the default initial location. 
	 * @param screen the game screen
	 */
	public EnemyShipHard(GameScreen screen){
		Random rand=new Random();
		this.setLocation(rand.nextInt(screen.getWidth()-shipWidth),
				 -shipHeight);
		this.setSize(shipWidth, shipHeight);
	}
	
	/**
	 * Get the default ship width
	 * @return the default ship width
	 */
	public int getEnemyShipHardWidth() {
		return shipWidth;
	}
	
	/**
	 * Get the default ship height
	 * @return the default ship height
	 */
	public int getEnemyShipHardHeight() {
		return shipHeight;
	}
	
	/**
	 * Returns the current ship speed
	 * @return the current ship speed
	 */
	public int getEnemyShipHardSpeed() {
		return speed;
	}
	
	/**
	 * Set the current ship speed
	 * @param speed the speed to set
	 */
	public void setEnemyShipHardSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	 * Returns the default ship speed.
	 * @return the default ship speed
	 */
	public int getEnemyShipHardDefaultSpeed(){
		return DEFAULT_SPEED;
	}
	
}
