package rbadia.voidspace.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.Timer;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.EnemyShipHard;
import rbadia.voidspace.model.Life;
import rbadia.voidspace.model.Shield;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.model.SuicideBomber;
import rbadia.voidspace.model.ThreeBulletPU;
import rbadia.voidspace.sounds.SoundManager;


/**
 * Handles general game logic and status.
 */
public class GameLogic {
	private GameScreen gameScreen;
	private GameStatus status;
	private SoundManager soundMan;
	
	private Ship ship;
	private EnemyShipHard enemyShipHard;
	private SuicideBomber enemySuicideBomber;
	private List<EnemyShip> enemyShip;
	private List<Asteroid> asteroid;
	private List<Bullet> enemyHardBullet;
	private List<Bullet> bullets;
	private List<Bullet> bullets1;
	private List<Bullet> bullets2;
	private List<Bullet> enemyBullet;
	private List<Life> life;
	private List<Shield> shield;
	private List<ThreeBulletPU> triBullet;
	
	/**
	 * Craete a new game logic handler
	 * @param gameScreen the game screen
	 */
	public GameLogic(GameScreen gameScreen){
		this.gameScreen = gameScreen;
		
		// initialize game status information
		status = new GameStatus();
		// initialize the sound manager
		soundMan = new SoundManager();
		
	}

	/**
	 * Returns the game status
	 * @return the game status 
	 */
	public GameStatus getStatus() {
		return status;
	}

	public SoundManager getSoundMan() {
		return soundMan;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	/**
	 * Prepare for a new game.
	 */
	public void newGame(){
		status.setGameStarting(true);
		
		//init game variables
		bullets = new ArrayList<Bullet>();
		bullets1=new ArrayList<Bullet>();
		bullets2=new ArrayList<Bullet>();
		asteroid= new ArrayList<Asteroid>();
		enemyShip=new ArrayList<EnemyShip>();
		enemyBullet=new ArrayList<Bullet>();
		life=new ArrayList<Life>();
		shield=new ArrayList<Shield>();
		triBullet =new ArrayList<ThreeBulletPU>();
		enemyHardBullet=new ArrayList<Bullet>();

		status.setShipsLeft(3);
		status.setGameOver(false);
		status.setEnemiesDestroyed(0);
		status.addScore(-status.getScore());
		status.setNewAsteroid(false);
				
		// init the ship and the asteroid
        newShip(gameScreen);
        newEnemyShipHard(gameScreen);
        newSuicideShip(gameScreen);
        //newAsteroid(gameScreen);
        
        // prepare game screen
        gameScreen.doNewGame();
        
        // delay to display "Get Ready" message for 1.5 seconds
		Timer timer = new Timer(1500, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameStarting(false);
				status.setGameStarted(true);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	/**
	 * Check game or level ending conditions.
	 */
	public void checkConditions(){
		// check game over conditions
		if(!status.isGameOver() && status.isGameStarted()){
			if(status.getShipsLeft() == 0){
				gameOver();
			}
		}
	}
	
	/**
	 * Actions to take when the game is over.
	 * @throws FileNotFoundException 
	 */
	public void gameOver(){
		status.setGameStarted(false);
		status.setGameOver(true);
		gameScreen.doGameOver();
		soundMan.gameOverSound();
		
        // delay to display "Game Over" message for 3 seconds
		Timer timer = new Timer(3000, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameOver(false);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	/**
	 * Fire a bullet from ship.
	 */
	public void fireBullet(){
		Bullet bullet = new Bullet(ship);
		Bullet bullet1= new Bullet(ship);
		Bullet bullet2= new Bullet(ship);
		bullets.add(bullet);
		if(gameScreen.checkThreeBullet())
		{
		bullets1.add(bullet1);
		bullets2.add(bullet2);
		}
		soundMan.stopBulletSound();
		soundMan.playBulletSound();
	}
	public void fireEnemyBullet(EnemyShip ship){
		Bullet bullet=new Bullet(ship);
		enemyBullet.add(bullet);
		soundMan.stopEnemyBulletSound();
		soundMan.playEnemyBulletSound();
	}
	public void fireEnemyHardBullet(){
		Bullet bullet=new Bullet(enemyShipHard);
		enemyHardBullet.add(bullet);
		soundMan.stopEnemyBulletSound();
		soundMan.playEnemyBulletSound();
	}
	
	
	/**
	 * Move a bullet once fired.
	 * @param bullet the bullet to move
	 * @return if the bullet should be removed from screen
	 */
	public boolean moveBullet(Bullet bullet){
		if(bullet.getY() - bullet.getSpeed() >= 0){
			bullet.translate(0, -bullet.getSpeed());
			return false;
		}
		else{
			return true;
		}
	}
	public boolean moveBullet1(Bullet bullet1){
		if(bullet1.getY() - bullet1.getSpeed() >= 0){
			bullet1.translate(5, -bullet1.getSpeed());
			return false;
		}
		else{
			return true;
		}
	}
	public boolean moveBullet2(Bullet bullet2){
		if(bullet2.getY() - bullet2.getSpeed() >= 0){
			bullet2.translate(-5, -bullet2.getSpeed());
			return false;
		}
		else{
			return true;
		}
	}
	
	public boolean moveEnemyBullet(Bullet enemyBullet)
	{
		if(enemyBullet.getY() + enemyBullet.getSpeed() < gameScreen.getHeight()){
			enemyBullet.translate(0, enemyBullet.getSpeed());
			return false;
		}
		else{
			return true;
		}
	}
	
	//Move the life
	public boolean moveLife(Life life)
	{
		if(life.getY() + life.getSpeed() < gameScreen.getHeight()){
			life.translate(0, life.getSpeed());
			return false;
		}
		else{
			return true;
		}
	}
	
	//Move the shield
		public boolean moveShield(Shield shield)
		{
			if(shield.getY() + shield.getSpeed() < gameScreen.getHeight()){
				shield.translate(0, shield.getSpeed());
				return false;
			}
			else{
				return true;
			}
		}
		
		//Move the three bullet
		public boolean moveThreeBulletPU(ThreeBulletPU triBullet)
		{
			if(triBullet.getY() + triBullet.getSpeed() < gameScreen.getHeight()){
				triBullet.translate(0, triBullet.getSpeed());
				return false;
			}
			else{
				return true;
			}
		}

	/**
	 * Create a new ship (and replace current one).
	 */
	public Ship newShip(GameScreen screen){
		this.ship = new Ship(screen);
		return ship;
	}
	public void newEnemyShip(GameScreen screen){
		this.enemyShip.add(new EnemyShip(screen));
	}
	
	public void newLife(GameScreen screen){
		this.life.add(new Life(screen));
	}
	
	public void newShield(GameScreen screen){
		this.shield.add(new Shield(screen));
	}
	
	public void newThreeBulletPU(GameScreen screen){
		this.triBullet.add(new ThreeBulletPU(screen));
	}
	
	/**
	 * Create a new asteroid.
	 */
	public void newAsteroid(GameScreen screen){
		
		asteroid.add(new Asteroid(screen));
	}
	public EnemyShipHard newEnemyShipHard(GameScreen screen){
		this.enemyShipHard=new EnemyShipHard(screen);
		return enemyShipHard;
	}
	public SuicideBomber newSuicideShip(GameScreen screen){
		this.enemySuicideBomber=new SuicideBomber(screen);
		return enemySuicideBomber;
	}
	/**
	 * Returns the ship.
	 * @return the ship
	 */
	public Ship getShip() {
		return ship;
	}

	/**
	 * Returns the asteroid.
	 * @return the asteroid
	 */
	public List<Asteroid> getAsteroid() {
		return asteroid;
	}
	
	public List<EnemyShip> getEnemyShip(){
		return enemyShip;
	}
	public EnemyShipHard getEnemyShipHard(){
		return enemyShipHard;
	}
	public SuicideBomber getSuicideBomber(){
		return enemySuicideBomber;
	}
	
	public List<Life> getLife(){
		return life;
	}
	
	public List<Shield> getShield(){
		return shield;
	}

	public List<ThreeBulletPU> getThreeBullet(){
		return triBullet;
	}
	/**
	 * Returns the list of bullets.
	 * @return the list of bullets
	 */
	public List<Bullet> getBullets() {
		return bullets;
	}
	public List<Bullet> getBullets1()
	{
		return bullets1;
	}
	public List<Bullet> getBullets2()
	{
		return bullets2;
	}
	public List<Bullet> getEnemyBullet()
	{
		return enemyBullet;
	}
	public List<Bullet> getEnemyHardBullet()
	{
		return enemyHardBullet;
	}
}
