package rbadia.voidspace.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Container for game flags and/or status variables.
 */
public class GameStatus {
	// game flags
	private boolean gameStarted = false;
	private boolean gameStarting = false;
	private boolean gameOver = false;

	// status variables
	private boolean newShip;
	private boolean newAsteroid;
	private long enemiesDestroyed = 0;
	private int shipsLeft;
	private int score;
	private int highscore;

	public GameStatus(){

	}

	/**
	 * Indicates if the game has already started or not.
	 * @return if the game has already started or not
	 */
	public synchronized boolean isGameStarted() {
		return gameStarted;
	}

	public synchronized void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	/**
	 * Indicates if the game is starting ("Get Ready" message is displaying) or not.
	 * @return if the game is starting or not.
	 */
	public synchronized boolean isGameStarting() {
		return gameStarting;
	}

	public synchronized void setGameStarting(boolean gameStarting) {
		this.gameStarting = gameStarting;
	}

	/**
	 * Indicates if the game has ended and the "Game Over" message is displaying.
	 * @return if the game has ended and the "Game Over" message is displaying.
	 */
	public synchronized boolean isGameOver() {
		return gameOver;
	}

	public synchronized void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	/**
	 * Indicates if a new ship should be created/drawn.
	 * @return if a new ship should be created/drawn
	 */
	public synchronized boolean isNewShip() {
		return newShip;
	}

	public synchronized void setNewShip(boolean newShip) {
		this.newShip = newShip;
	}

	/**
	 * Indicates if a new asteroid should be created/drawn.
	 * @return if a new asteroid should be created/drawn
	 */
	public synchronized boolean isNewAsteroid() {
		return newAsteroid;
	}

	public synchronized void setNewAsteroid(boolean newAsteroid) {
		this.newAsteroid = newAsteroid;
	}

	/**
	 * Returns the number of asteroid destroyed. 
	 * @return the number of asteroid destroyed
	 */
	public synchronized long getEnemiesDestroyed() {
		return enemiesDestroyed;
	}

	public synchronized void setEnemiesDestroyed(long enemiesDestroyed) {
		this.enemiesDestroyed = enemiesDestroyed;
	}
	/**
	 * Returns the number ships/lives left.
	 * @return the number ships left
	 */
	public synchronized int getShipsLeft() {
		return shipsLeft;
	}

	public synchronized void setShipsLeft(int shipsLeft) {
		this.shipsLeft = shipsLeft;
	}
	public synchronized void addScore(int enemyScore)
	{
		score+=enemyScore;
	}
	public synchronized int getScore()
	{
		return score;
	}
	public int getLevel()
	{
		return (int) ((getEnemiesDestroyed()+5)/5);
	}
	public void checkHighScore()
	{
		if(highscore<score)
		{
			PrintWriter out;
			try {
				out = new PrintWriter("highScore.txt");
				out.println(score);
				out.close();
				highscore=score;
			} catch (FileNotFoundException e) {
				
				JOptionPane.showMessageDialog(null, "High Score folder not found. Please create a text field in the game file named highScore.txt",
						"VoidSpace - Fatal Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}
	public void readHighScore()
	{
		File inFile=new File("highScore.txt");
		Scanner in;
		try {
			in = new Scanner(inFile);
			highscore=in.nextInt();
		} catch (FileNotFoundException e) {
			
			JOptionPane.showMessageDialog(null, "High Score folder not found. Please create a text field in the game file named highScore.txt",
					"VoidSpace - Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
	}
	public int getHighScore()
	{
		return highscore;
	}
}
