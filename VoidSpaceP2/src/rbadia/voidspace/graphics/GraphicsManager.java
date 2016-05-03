package rbadia.voidspace.graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.EnemyShipHard;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.model.SuicideBomber;

/**
 * Manages and draws game graphics and images.
 */
public class GraphicsManager {
	private BufferedImage shipImg;
	private BufferedImage shipThrusterImg;
	private BufferedImage bulletImg;
	private BufferedImage asteroidImg;
	private BufferedImage asteroidExplosionImg;
	private BufferedImage shipExplosionImg;
	private BufferedImage enemyShipImg;
	private BufferedImage enemyBulletImg;
	private BufferedImage lifeImg;
	private BufferedImage shieldImg;
	private BufferedImage shipShieldImg;
	private BufferedImage shipThrusterShieldImg;
	private BufferedImage triBulletImg;
	private BufferedImage enemyShipHardImg;
	private BufferedImage enemySuicideShipImg;
	
	/**
	 * Creates a new graphics manager and loads the game images.
	 */
	public GraphicsManager(){
    	// load images
		try {
			this.shipImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/ship.png"));
			this.shipThrusterImg=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/shipThruster.png"));
			this.enemyShipImg=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/enemyShip.png"));
			this.asteroidImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroid.png"));
			this.asteroidExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroidExplosion.png"));
			this.shipExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/shipExplosion.png"));
			this.bulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bullet.png"));
			this.enemyShipHardImg=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/enemyShipHard.png"));
			this.enemyBulletImg=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/enemybullet.png"));
			this.lifeImg=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/Life.png"));
			this.shieldImg=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/shield.png"));
			this.shipShieldImg=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/shipShield.png"));
			this.shipThrusterShieldImg=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/shipShieldThruster.png"));
			this.triBulletImg=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/tribullet.png"));
			this.enemySuicideShipImg=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/SuicideBomber.png"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "The graphic files are either corrupt or missing.",
					"VoidSpace - Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Draws a ship image to the specified graphics canvas.
	 * @param ship the ship to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawShip(Ship ship, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(shipImg, ship.x, ship.y, observer);
	}
	public void drawShipThruster(Ship ship, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(shipThrusterImg, ship.x, ship.y, observer);
	}
	
	public void drawEnemyShip(EnemyShip enemyShip, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(enemyShipImg, enemyShip.x, enemyShip.y, observer);
	}
	public void drawEnemyShipHard(EnemyShipHard enemyShipHard, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(enemyShipHardImg, enemyShipHard.x, enemyShipHard.y, observer);
	}
	public void drawEnemyShipSuicide(SuicideBomber enemyShipSuicide, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(enemySuicideShipImg, enemyShipSuicide.x, enemyShipSuicide.y, observer);
	}
	/**
	 * Draws a bullet image to the specified graphics canvas.
	 * @param bullet the bullet to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBullet(Bullet bullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bulletImg, bullet.x, bullet.y, observer);
	}
	
	public void drawEnemyBullet(Bullet bullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(enemyBulletImg, bullet.x, bullet.y, observer);
	}
	/**
	 * Draws an asteroid image to the specified graphics canvas.
	 * @param asteroid the asteroid to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawAsteroid(Asteroid asteroid, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidImg, asteroid.x, asteroid.y, observer);
	}

	/**
	 * Draws a ship explosion image to the specified graphics canvas.
	 * @param shipExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawShipExplosion(Rectangle shipExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(shipExplosionImg, shipExplosion.x, shipExplosion.y, observer);
	}

	/**
	 * Draws an asteroid explosion image to the specified graphics canvas.
	 * @param asteroidExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawAsteroidExplosion(Rectangle asteroidExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidExplosionImg, asteroidExplosion.x, asteroidExplosion.y, observer);
	}
	
	/**
	 * Draws a life image to the specified graphics canvas.
	 * @param life the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawLife(Rectangle life, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(lifeImg, life.x, life.y, observer);
	}
	
	public void drawShield(Rectangle shield, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(shieldImg, shield.x, shield.y, observer);
	}
	
	public void drawThreeBullet(Rectangle triBullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(triBulletImg, triBullet.x, triBullet.y, observer);
	}
	
	public void drawShipShield(Ship ship, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(shipShieldImg, ship.x, ship.y, observer);
	}
	public void drawShipThrusterShield(Ship ship, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(shipThrusterShieldImg, ship.x, ship.y, observer);
	}
	
	
}
