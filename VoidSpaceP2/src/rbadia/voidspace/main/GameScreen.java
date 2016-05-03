package rbadia.voidspace.main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import rbadia.voidspace.graphics.GraphicsManager;
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
 * Main game screen. Handles all game graphics updates and some of the game logic.
 */
public class GameScreen extends JPanel {
	private static final long serialVersionUID = 1L;

	private BufferedImage backBuffer;
	private Graphics2D g2d;

	private static final int NEW_SHIP_DELAY = 500;
	private static final int NEW_ASTEROID_DELAY = 500;
	private static final int NEW_LEVEL_DELAY=1000;
	private static final int FIRE_DELAY=1000;
	private static final int THREE_BULLET_TIME = 5000;
	private static final int DEAD_DELAY = 2000;
	private static final int FIRE_DELAY_HARD=1200;
	private static final int SCORE_SHIP=500;
	private static final int SCORE_ASTEROID=100;
	private static final int SCORE_BOMBER=600;
	private static final int SCORE_HARD_SHIP=700;

	private long lastShipTime;
	private long lastAsteroidTime;
	private long lastBulletTime;
	private long lastEnemyShip;
	private long lastBulletHardTime;
	private long lastSuicideBomber;


	private Rectangle asteroidExplosion;
	private Rectangle shipExplosion;
	private Rectangle enemyShipExplosion;


	private JLabel shipsValueLabel;
	private JLabel destroyedValueLabel;
	private JLabel scoreValueLabel;
	private JLabel highScoreValueLabel;

	private Random rand;

	private Font originalFont;
	private Font bigFont;
	private Font biggestFont;

	private GameStatus status;
	private SoundManager soundMan;
	private GraphicsManager graphicsMan;
	private GameLogic gameLogic;

	private long timeForLevel;
	private long timeForBulletPU;
	private long deadTime;

	private boolean threeBulletsPowerUp=false;
	private boolean armorShield=false;
	private boolean pass=false;
	private boolean shipHard=false;
	private boolean suicideBomber=false;

	static boolean thruster=false;

	private int actualLevel=0;
	private int counter=0;
	private int counter2=0;
	private int moveHardShip=3;
	private double x=0;

	private Rectangle shipHardExplosion;
	private long lastHardShip;
	private Rectangle suicideBomberExplosion;
	private ArrayList<Boolean> isDestroyed=new ArrayList<Boolean>();
	private ArrayList<Boolean> enemyShipDestroyed=new ArrayList<Boolean>();

	/**
	 * This method initializes 
	 * 
	 */
	public GameScreen() {
		super();
		// initialize random number generator
		rand = new Random();

		initialize();

		// init graphics manager
		graphicsMan = new GraphicsManager();

		// init back buffer image
		backBuffer = new BufferedImage(500, 400, BufferedImage.TYPE_INT_RGB);
		g2d = backBuffer.createGraphics();
	}

	/**
	 * Initialization method (for VE compatibility).
	 */
	private void initialize() {
		// set panel properties
		this.setSize(new Dimension(500, 400));
		this.setPreferredSize(new Dimension(500, 400));
		this.setBackground(Color.BLACK);
	}

	/**
	 * Update the game screen.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// draw current backbuffer to the actual game screen
		g.drawImage(backBuffer, 0, 0, this);
	}

	/**
	 * Update the game screen's backbuffer image.
	 */
	public void updateScreen(){
		Ship ship = gameLogic.getShip();
		EnemyShipHard enemyShipHard= gameLogic.getEnemyShipHard();
		SuicideBomber enemySuicideShip =gameLogic.getSuicideBomber();
		List<Asteroid> asteroid = gameLogic.getAsteroid();
		List<EnemyShip> enemyShip=gameLogic.getEnemyShip();
		List<Bullet> bullets = gameLogic.getBullets();
		List<Bullet> bullets1= gameLogic.getBullets1();
		List<Bullet> bullets2= gameLogic.getBullets2();
		List<Bullet> enemyBullet= gameLogic.getEnemyBullet();
		List<Life> life = gameLogic.getLife();
		List<Shield> shield = gameLogic.getShield();
		List<ThreeBulletPU> triBullet = gameLogic.getThreeBullet();
		List<Bullet> enemyHardBullet=gameLogic.getEnemyHardBullet();


		// set orignal font - for later use
		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		// erase screen
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0, 0, getSize().width, getSize().height);

		// draw 15 random stars
		drawStars(15);

		// if the game is starting, draw "Get Ready" message
		if(status.isGameStarting()){
			drawGetReady();
			return;
		}

		// if the game is over, draw the "Game Over" message
		if(status.isGameOver()){
			// draw the message
			drawGameOver();

			long currentTime = System.currentTimeMillis();
			// draw the explosions until their time passes
			if((currentTime - lastAsteroidTime) < NEW_ASTEROID_DELAY){
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
			if((currentTime - lastShipTime) < NEW_SHIP_DELAY){
				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
			}
			if((currentTime - lastEnemyShip) < NEW_SHIP_DELAY){
				graphicsMan.drawShipExplosion(enemyShipExplosion, g2d, this);
			}
			return;
		}

		// the game has not started yet
		if(!status.isGameStarted()){
			// draw game title screen
			initialMessage();
			return;
		}

		//Checks level and adds asteroids and enemy ships to the array
		if(status.getLevel()!=actualLevel)
		{

			timeForLevel=System.currentTimeMillis();
			actualLevel=status.getLevel();

			int abilityLife= rand.nextInt(7);
			int abilityShield= rand.nextInt(5);
			int abilityThreeBullet= rand.nextInt(8);
			if(abilityLife==3)
			{
				gameLogic.newLife(this);
			}
			if(abilityShield==3){
				gameLogic.newShield(this);
			}
			if(abilityThreeBullet==3)
			{
				gameLogic.newThreeBulletPU(this);
			}
		}
		else
		{
			if(asteroid.size()<actualLevel && asteroid.size()<=5)
			{
				gameLogic.newAsteroid(this);
				isDestroyed.add(false);
			}
			if(enemyShip.size()<actualLevel && enemyShip.size()<2)
			{
				gameLogic.newEnemyShip(this);
				enemyShipDestroyed.add(false);
			}
			long nowTime=System.currentTimeMillis();
			if((nowTime -timeForLevel)<NEW_LEVEL_DELAY)
			{
				drawNewLevel();
				if(actualLevel!=1)
				{
					soundMan.playNewLevelSound();
				}
			}
		}

		//		 draw asteroids
		for(int i=0; i<asteroid.size();i++)
		{
			Asteroid ast = asteroid.get(i);

			if(i<2)
			{
				if(isDestroyed.get(i))
				{
					long currentTime = System.currentTimeMillis();
					if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
						// draw a new asteroid
						lastAsteroidTime = currentTime;
						ast.setLocation(rand.nextInt(getWidth() - ast.width), (int) -ast.getHeight());
						isDestroyed.set(i, false);
					}
					else{
						// draw explosion
						graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
					}
				}
				else{if(ast.getY() + ast.getSpeed() < this.getHeight()){
					ast.translate(0, ast.getSpeed());
					graphicsMan.drawAsteroid(asteroid.get(i), g2d, this);
				}
				else{
					ast.setLocation(rand.nextInt(400 - 50 + 1) + 50, (int) -ast.getHeight());
				}
				}
			}
			else if(i==2){
				if(isDestroyed.get(i))
				{
					long currentTime = System.currentTimeMillis();
					if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
						// draw a new asteroid
						lastAsteroidTime = currentTime;
						ast.setLocation(rand.nextInt(getWidth() - ast.width), (int) -ast.getHeight());
						isDestroyed.set(i,false);
					}
					else{
						// draw explosion
						graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
					}
				}
				else{
					if(ast.getY() + ast.getSpeed() < this.getHeight()){
						if(counter==0)
						{
							ast.translate(2, ast.getSpeed());
							graphicsMan.drawAsteroid(ast, g2d, this);
						}
						else{
							ast.translate(-2, ast.getSpeed());
							graphicsMan.drawAsteroid(ast, g2d, this);
						}
					}
					else{
						counter=rand.nextInt(2);
						ast.setLocation(rand.nextInt(getWidth() - ast.width), (int) -ast.getHeight());
					}
				}
			}
			else if(i==3){
				if(isDestroyed.get(i))
				{
					long currentTime = System.currentTimeMillis();
					if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
						// draw a new asteroid
						lastAsteroidTime = currentTime;
						ast.setLocation(rand.nextInt(getWidth() - ast.width), (int) -ast.getHeight());
						isDestroyed.set(i, false);
					}
					else{
						// draw explosion
						graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
					}
				}
				else{
					if(ast.getY() + ast.getSpeed() < this.getHeight()){
						if(counter2==0)
						{
							ast.translate(-3, ast.getSpeed());
							graphicsMan.drawAsteroid(ast, g2d, this);
						}
						else{
							ast.translate(3, ast.getSpeed());
							graphicsMan.drawAsteroid(ast, g2d, this);
						}
					}
					else{
						if(counter2==0)
						{
							counter2=rand.nextInt(2);
							ast.setLocation(-ast.getAsteroidWidth(), rand.nextInt(200));
						}
						else{
							counter2=rand.nextInt(2);
							ast.setLocation(getWidth(), rand.nextInt(200));
						}
					}
				}
			}
			else if(i==4)
			{
				if(isDestroyed.get(i))
				{
					long currentTime = System.currentTimeMillis();
					if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
						// draw a new asteroid
						lastAsteroidTime = currentTime;
						ast.setLocation(rand.nextInt(getWidth() - ast.width), (int) -ast.getHeight());
						isDestroyed.set(i, false);
					}
					else{
						// draw explosion
						graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
					}
				}
				else{
					if(ast.getY() + ast.getSpeed() < this.getHeight()){
						ast.translate(0, ast.getSpeed());
						graphicsMan.drawAsteroid(asteroid.get(i), g2d, this);
					}
					else{
						ast.setLocation(rand.nextInt(400 - 50 + 1) + 50, (int) -ast.getHeight());
					}
				}
			}
			if(i>=2)
			{
				if(ast.getX() > this.getWidth())
				{
					ast.setLocation(0, (int) ast.getY());
				}
				else if(ast.getX() < -ast.getWidth())
				{
					ast.setLocation(getWidth(), (int) ast.getY());
				}
			}
		} 
		//Draw enemy Ship
		for(int i=0;i<enemyShip.size();i++)
		{
			EnemyShip eShip=enemyShip.get(i);
			if(enemyShipDestroyed.get(i))
			{
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastEnemyShip) > NEW_ASTEROID_DELAY){
					// draw a new asteroid
					lastEnemyShip = currentTime;
					enemyShipDestroyed.set(i, false);
				}
				else{
					// draw explosion
					graphicsMan.drawAsteroidExplosion(enemyShipExplosion, g2d, this);
				}
			}
			else{
				if(eShip.getY()+eShip.getEnemyShipSpeed()<this.getHeight())
				{
					eShip.translate(0, eShip.getEnemyShipSpeed());
					graphicsMan.drawEnemyShip(eShip, g2d, this);
				}
				else{
					eShip.setLocation(rand.nextInt(getWidth() - eShip.width), (int) -eShip.getHeight());
				}
			}
		}

		//initialize enemy bullets
		for(int i=0;i<enemyShip.size();i++)
		{
			int r=rand.nextInt(enemyShip.size());
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastBulletTime) > FIRE_DELAY){
				if(enemyShip.get(r).getX()>0)
				{
					lastBulletTime = currentTime;
					gameLogic.fireEnemyBullet(enemyShip.get(r));
				}
			}
		}

		//draw enemy bullet
		for(int i=0;i<enemyBullet.size();i++){
			Bullet bullet = enemyBullet.get(i);
			graphicsMan.drawEnemyBullet(bullet, g2d, this);

			boolean remove = gameLogic.moveEnemyBullet(bullet);
			if(remove){
				enemyBullet.remove(i);
				i--;
			}
		}

		long currentTimeDead = System.currentTimeMillis();
		if((currentTimeDead - deadTime) > DEAD_DELAY)
		{
			//check enemy life-ship collisions And enemyShip-ship collision
			checkCollision(triBullet,ship);
			//check enemy life-ship collisions And enemyShip-ship collision
			checkCollision(shield,ship);
			//check enemy life-ship collisions And enemyShip-ship collision
			checkCollision(life,ship);
			//check enemy bullet-ship collisions And enemyShip-ship collision
			checkCollision(enemyShip,ship);
			//check enemyBullet-ship collision
			checkCollision(enemyBullet,ship);
			// check ship-asteroid collisions
			checkCollision(asteroid,ship);
		}
		else{graphicsMan.drawShipShield(ship, g2d, this);}


		//check bullet-enemy ship collisions
		checkCollision(bullets,enemyShip,SCORE_SHIP);
		// check bullet-asteroid collisions
		checkCollision(bullets,asteroid,SCORE_ASTEROID);

		// draw bullets
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			graphicsMan.drawBullet(bullet, g2d, this);

			boolean remove = gameLogic.moveBullet(bullet);
			if(remove){
				bullets.remove(i);
				i--;
			}
		}

		//draw life
		for(int i=0;i<life.size();i++){
			Life lifes = life.get(i);
			graphicsMan.drawLife(lifes, g2d, this);

			boolean remove = gameLogic.moveLife(lifes);

			if(remove){
				life.remove(i);
				i--;
			}
		}

		//draw shield
		for(int i=0;i<shield.size();i++){
			Shield shields = shield.get(i);
			graphicsMan.drawShield(shields, g2d, this);

			boolean remove = gameLogic.moveShield(shields);

			if(remove){
				shield.remove(i);
				i--;
			}
		}



		//draw ThreeBulletPU
		for(int i=0;i<triBullet.size();i++){
			ThreeBulletPU triBullets = triBullet.get(i);
			graphicsMan.drawThreeBullet(triBullets, g2d, this);

			boolean remove = gameLogic.moveThreeBulletPU(triBullets);

			if(remove){
				triBullet.remove(i);
				i--;
			}
		}

		long currentTimePU = System.currentTimeMillis();
		if((currentTimePU - timeForBulletPU) > THREE_BULLET_TIME){
			threeBulletsPowerUp=false;
		}
		//If you PowerUp for three bullet, this will check if each bullet collided with an asteroid
		if(threeBulletsPowerUp)
		{

			//check bullet-enemy ship collisions
			checkCollision(bullets1,enemyShip,SCORE_SHIP);
			// check bullet-asteroid collisions
			checkCollision(bullets1,asteroid,SCORE_ASTEROID);
			//check bullet-enemy ship collisions
			checkCollision(bullets2,enemyShip,SCORE_SHIP);
			// check bullet-asteroid collisions
			checkCollision(bullets2,asteroid,SCORE_ASTEROID);

			checkCollisions(bullets1,enemyShipHard,SCORE_HARD_SHIP);

			checkCollisions(bullets2,enemyShipHard,SCORE_HARD_SHIP);

			checkCollisions(bullets1,enemySuicideShip,SCORE_BOMBER);

			checkCollisions(bullets2,enemySuicideShip,SCORE_BOMBER);

			for(int i=0; i<bullets1.size(); i++){
				Bullet bullet = bullets1.get(i);
				graphicsMan.drawBullet(bullet, g2d, this);

				boolean remove = gameLogic.moveBullet1(bullet);
				if(remove){
					bullets1.remove(i);
					i--;
				}
			}
			for(int i=0; i<bullets2.size(); i++){
				Bullet bullet = bullets2.get(i);
				graphicsMan.drawBullet(bullet, g2d, this);

				boolean remove = gameLogic.moveBullet2(bullet);
				if(remove){
					bullets2.remove(i);
					i--;
				}
			}
		}

		// draw ship
		if(!status.isNewShip()){
			// draw it in its current location
			//draw thruster if shift is pressed
			if(InputHandler.shiftPressed())
			{
				if(!armorShield)
				{graphicsMan.drawShipThruster(ship, g2d, this);}
				else
				{graphicsMan.drawShipThrusterShield(ship, g2d, this);}
			}
			else{
				if(!armorShield)
				{graphicsMan.drawShip(ship, g2d, this);}
				else
				{graphicsMan.drawShipShield(ship, g2d, this);}
			}
		}
		else{
			// draw a new one
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastShipTime) > NEW_SHIP_DELAY){
				lastShipTime = currentTime;
				status.setNewShip(false);
				ship = gameLogic.newShip(this);
			}
			else{
				// draw explosion
				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);

			}
		}
		long currentTime = System.currentTimeMillis();
		if(actualLevel>8)
		{
			if(shipHard)
			{
				int spawn=rand.nextInt(100);
				if(spawn==25){
					shipHard=false;
				}
				if((currentTime - lastHardShip) < NEW_ASTEROID_DELAY)
				{
					graphicsMan.drawAsteroidExplosion(shipHardExplosion, g2d, this);
				}
			}
			else{
				if(enemyShipHard.getY()+enemyShipHard.getEnemyShipHardSpeed()<enemyShipHard.getEnemyShipHardHeight()-20)
				{
					enemyShipHard.translate(0,enemyShipHard.getEnemyShipHardSpeed());
					graphicsMan.drawEnemyShipHard(enemyShipHard, g2d, this);
				}
				else{
					if(enemyShipHard.getX()+enemyShipHard.getEnemyShipHardSpeed()+enemyShipHard.getWidth()>getWidth())
					{
						moveHardShip=-enemyShipHard.getEnemyShipHardSpeed();
					}
					if(enemyShipHard.getX()-enemyShipHard.getEnemyShipHardSpeed()<0){
						moveHardShip=enemyShipHard.getEnemyShipHardSpeed();
					}
					enemyShipHard.translate(moveHardShip,0);
					graphicsMan.drawEnemyShipHard(enemyShipHard, g2d, this);
				}
			}

			if(((currentTime - lastBulletHardTime) > FIRE_DELAY_HARD) && enemyShipHard.getY()+enemyShipHard.getEnemyShipHardSpeed()>0){
				lastBulletHardTime = currentTime;
				gameLogic.fireEnemyHardBullet();
			}
			for(int i=0; i<enemyHardBullet.size(); i++){
				Bullet bullet = enemyHardBullet.get(i);
				graphicsMan.drawEnemyBullet(bullet, g2d, this);
				boolean remove = gameLogic.moveEnemyBullet(bullet);
				if(remove){
					enemyHardBullet.remove(i);
					i--;
				}
			}
			for(int i=0; i<enemyHardBullet.size(); i++){
				Bullet bullet = enemyHardBullet.get(i);
				if(bullet.intersects(ship))
				{
					if(!armorShield)
					{
						// "remove" ship
						shipExplosion = new Rectangle(
								ship.x,
								ship.y,
								ship.width,
								ship.height);
						ship.setLocation(this.getWidth() + ship.width, -ship.height);
						status.setNewShip(true);
						lastShipTime = System.currentTimeMillis();
						// decrease number of ships left
						status.setShipsLeft(status.getShipsLeft() - 1);
						// play ship explosion sound
						soundMan.stopShipExplosion();
						soundMan.playShipExplosionSound();
					}
					if(armorShield)
					{
						soundMan.playShieldDown();
					}
					else{deadTime = System.currentTimeMillis();}
					armorShield=false;
					enemyHardBullet.remove(i);
				}
			}
			if(enemyShipHard.intersects(ship)){
				// "remove" asteroid
				shipHardExplosion = new Rectangle(
						enemyShipHard.x,
						enemyShipHard.y,
						enemyShipHard.width,
						enemyShipHard.height);
				enemyShipHard.setLocation(-enemyShipHard.width, -enemyShipHard.height);
				shipHard=true;
				lastHardShip=System.currentTimeMillis();
				if(!armorShield)
				{
					// "remove" ship
					shipExplosion = new Rectangle(
							ship.x,
							ship.y,
							ship.width,
							ship.height);
					ship.setLocation(this.getWidth() + ship.width, -ship.height);
					status.setNewShip(true);
					lastShipTime = System.currentTimeMillis();
					// decrease number of ships left
					status.setShipsLeft(status.getShipsLeft() - 1);
				}
				if(armorShield)
				{
					soundMan.playShieldDown();
				}
				else{deadTime = System.currentTimeMillis();}
				armorShield=false;

				// play ship explosion sound
				soundMan.playShipExplosionSound();
			}

			checkCollisions(bullets,enemyShipHard,SCORE_HARD_SHIP);
			int spawn=rand.nextInt(100);
			if(suicideBomber)
			{
				if(spawn==25){
					// draw a new suicide Bomber
					suicideBomber=false;
				}
				if((currentTime - lastSuicideBomber) < NEW_ASTEROID_DELAY)
				{
					graphicsMan.drawAsteroidExplosion(suicideBomberExplosion, g2d, this);
				}
			}
			else{
				if(enemySuicideShip.getY() + enemySuicideShip.getSuicideSpeed() +enemySuicideShip.getSuicideShipHeight() < this.getHeight()+enemySuicideShip.getHeight()){
					if(enemySuicideShip.getY()+enemySuicideShip.getSuicideSpeed()*4<ship.getY() && !pass)
					{
						double slope=((ship.getY()-enemySuicideShip.getY())/(ship.getX()-enemySuicideShip.getX()));
						x=enemySuicideShip.getSuicideSpeed()/slope;
						if(x>5)
						{
							x=5;
						}
						if(x<-5)
						{
							x=-5;
						}
						enemySuicideShip.translate((int) x, enemySuicideShip.getSuicideSpeed());
						graphicsMan. drawEnemyShipSuicide(enemySuicideShip, g2d, this);					}
					else{
						enemySuicideShip.translate((int)x, enemySuicideShip.getSuicideSpeed());
						graphicsMan. drawEnemyShipSuicide(enemySuicideShip, g2d, this);
						pass=true;
					}
				}
				else{
					pass=false;
					enemySuicideShip.setLocation(this.getWidth(),this.getHeight());
					if(spawn==25)
					{
						enemySuicideShip.setLocation(rand.nextInt(getWidth() - enemySuicideShip.width), (int) -enemySuicideShip.getHeight());
					}
				}
			}
			if(enemySuicideShip.intersects(ship)){
				// "remove" asteroid
				suicideBomberExplosion = new Rectangle(
						enemySuicideShip.x,
						enemySuicideShip.y,
						enemySuicideShip.width,
						enemySuicideShip.height);
				enemySuicideShip.setLocation(-enemySuicideShip.width, -enemySuicideShip.height);
				suicideBomber=true;
				lastSuicideBomber=System.currentTimeMillis();
				if(!armorShield)
				{
					// "remove" ship
					shipExplosion = new Rectangle(
							ship.x,
							ship.y,
							ship.width,
							ship.height);
					ship.setLocation(this.getWidth() + ship.width, -ship.height);
					status.setNewShip(true);
					lastShipTime = System.currentTimeMillis();
					// decrease number of ships left
					status.setShipsLeft(status.getShipsLeft() - 1);
				}
				if(armorShield)
				{
					soundMan.playShieldDown();
				}
				else{deadTime = System.currentTimeMillis();}
				armorShield=false;

				// play ship explosion sound
				soundMan.playShipExplosionSound();
			}
			checkCollisions(bullets,enemySuicideShip,SCORE_BOMBER);
		}


		// update asteroids destroyed label
		destroyedValueLabel.setText(Long.toString(status.getEnemiesDestroyed()));

		//update score label
		scoreValueLabel.setText(Integer.toString(status.getScore()));

		// update ships left label
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));

		status.checkHighScore();

		highScoreValueLabel.setText(Integer.toString(status.getHighScore()));

	}

	/**
	 * Draws the "Game Over" message.
	 */
	private void drawGameOver() {
		String gameOverStr = "GAME OVER";
		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameOverStr);
		if(strWidth > this.getWidth() - 10){
			biggestFont = currentFont;
			bigFont = biggestFont;
			fm = g2d.getFontMetrics(bigFont);
			strWidth = fm.stringWidth(gameOverStr);
		}
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setFont(bigFont);
		g2d.setPaint(Color.WHITE);
		g2d.drawString(gameOverStr, strX, strY);
	}

	/**
	 * Draws the initial "Get Ready!" message.
	 */
	private void drawGetReady() {
		String readyStr = "Get Ready!";
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(readyStr, strX, strY);
	}
	private void drawNewLevel(){
		String readyStr = "Level "+status.getLevel()+"!";
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(readyStr, strX, strY);
	}

	/**
	 * Draws the specified number of stars randomly on the game screen.
	 * @param numberOfStars the number of stars to draw
	 */
	private void drawStars(int numberOfStars) {
		g2d.setColor(Color.WHITE);
		for(int i=0; i<numberOfStars; i++){
			int x = (int)(Math.random() * this.getWidth());
			int y = (int)(Math.random() * this.getHeight());
			g2d.drawLine(x, y, x, y);
		}
	}

	/**
	 * Display initial game title screen.
	 */
	private void initialMessage() {
		String gameTitleStr = "Void Space";

		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD).deriveFont(Font.ITALIC);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameTitleStr);
		if(strWidth > this.getWidth() - 10){
			bigFont = currentFont;
			biggestFont = currentFont;
			fm = g2d.getFontMetrics(currentFont);
			strWidth = fm.stringWidth(gameTitleStr);
		}
		g2d.setFont(bigFont);
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2 - ascent;
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(gameTitleStr, strX, strY);

		g2d.setFont(originalFont);
		fm = g2d.getFontMetrics();
		String newGameStr = "Press <Space> to Start a New Game.";
		strWidth = fm.stringWidth(newGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = (this.getHeight() + fm.getAscent())/2 + ascent + 16;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(newGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String exitGameStr = "Press <Esc> to Exit the Game.";
		strWidth = fm.stringWidth(exitGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = strY + 16;
		g2d.drawString(exitGameStr, strX, strY);
		status.readHighScore();
		highScoreValueLabel.setText(Integer.toString(status.getHighScore()));
	}

	/**
	 * Prepare screen for game over.
	 */
	public void doGameOver() {
		shipsValueLabel.setForeground(new Color(128, 0, 0));
	}

	/**
	 * Prepare screen for a new game.
	 */
	public void doNewGame(){		
		lastAsteroidTime = -NEW_ASTEROID_DELAY;
		lastShipTime = -NEW_SHIP_DELAY;

		bigFont = originalFont;
		biggestFont = null;

		// set labels' text
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));
		destroyedValueLabel.setText(Long.toString(status.getEnemiesDestroyed()));
		scoreValueLabel.setText("0");
		shipsValueLabel.setForeground(Color.WHITE);
		highScoreValueLabel.setText(Integer.toString(status.getHighScore()));


	}

	/**
	 * Sets the game graphics manager.
	 * @param graphicsMan the graphics manager
	 */
	public void setGraphicsMan(GraphicsManager graphicsMan) {
		this.graphicsMan = graphicsMan;
	}

	/**
	 * Sets the game logic handler
	 * @param gameLogic the game logic handler
	 */
	public void setGameLogic(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
		this.status = gameLogic.getStatus();
		this.soundMan = gameLogic.getSoundMan();
	}

	/**
	 * Sets the label that displays the value for asteroids destroyed.
	 * @param destroyedValueLabel the label to set
	 */
	public void setDestroyedValueLabel(JLabel destroyedValueLabel) {
		this.destroyedValueLabel = destroyedValueLabel;
	}

	/**
	 * Sets the label that displays the value for ship (lives) left
	 * @param shipsValueLabel the label to set
	 */
	public void setShipsValueLabel(JLabel shipsValueLabel) {
		this.shipsValueLabel = shipsValueLabel;
	}
	public void setScoreValueLabel(JLabel scoreValueLabel){
		this.scoreValueLabel=scoreValueLabel;
	}
	public void setHighScoreValueLabel(JLabel highScoreValueLabel){
		this.highScoreValueLabel=highScoreValueLabel;
	}


	public void checkCollision(List<Bullet> aBullet,List<?> aList, int score)
	{
		List<Rectangle> anEnemy = (List<Rectangle>)aList;
		for(int i=0;i<aBullet.size();i++)
		{
			Bullet bullet = aBullet.get(i);
			for(int j=0;j<anEnemy.size();j++)
			{
				if(anEnemy.get(j).intersects(bullet)){
					// increase enemies destroyed count
					status.setEnemiesDestroyed(status.getEnemiesDestroyed()+1);
					status.addScore(score);

					// "remove" enemy
					if(anEnemy.get(j) instanceof EnemyShip)
					{
						enemyShipExplosion = new Rectangle(
								anEnemy.get(j).x,
								anEnemy.get(j).y,
								anEnemy.get(j).width,
								anEnemy.get(j).height);
						anEnemy.get(j).setLocation(-anEnemy.get(j).width, -anEnemy.get(j).height);
						lastEnemyShip= System.currentTimeMillis();

						// play asteroid explosion sound
						soundMan.stopShipExplosion();
						soundMan.playShipExplosionSound();

						enemyShipDestroyed.set(j, true);
						// remove bullet
						aBullet.remove(i);
						break;
					}
					else 
					{
						asteroidExplosion = new Rectangle(
								anEnemy.get(j).x,
								anEnemy.get(j).y,
								anEnemy.get(j).width,
								anEnemy.get(j).height);
						anEnemy.get(j).setLocation(-anEnemy.get(j).width, -anEnemy.get(j).height);
						lastAsteroidTime= System.currentTimeMillis();

						// play asteroid explosion sound
						soundMan.stopAsteroidExplosion();
						soundMan.playAsteroidExplosionSound();

						isDestroyed.set(j,true);
						// remove bullet
						aBullet.remove(i);
						break;
					}
				}
			}
		}	
	}


	//Method to check Collision
	public void checkCollision(List<?> aList,Ship ship)
	{
		List<Rectangle> a = (List<Rectangle>)aList;
		for(int i=0;i<a.size();i++)
		{

			if(a.get(i).intersects(ship))
			{
				if(!armorShield && !(a.get(i) instanceof Life) && !(a.get(i) instanceof Shield) && !(a.get(i) instanceof ThreeBulletPU))
				{
					// "remove" ship
					shipExplosion = new Rectangle(
							ship.x,
							ship.y,
							ship.width,
							ship.height);
					ship.setLocation(this.getWidth() + ship.width, -ship.height);
					status.setNewShip(true);
					lastShipTime = System.currentTimeMillis();
					// decrease number of ships left
					status.setShipsLeft(status.getShipsLeft() - 1);
					// play ship explosion sound
					soundMan.playShipExplosionSound();
				}
				if(a.get(i) instanceof Bullet)
				{
					a.remove(i);
					if(armorShield)
					{
						soundMan.playShieldDown();
					}
					else{deadTime = System.currentTimeMillis();}
					armorShield=false;
				}
				else if(a.get(i) instanceof Asteroid){
					// "remove" asteroid
					asteroidExplosion = new Rectangle(
							a.get(i).x,
							a.get(i).y,
							a.get(i).width,
							a.get(i).height);
					a.get(i).setLocation(a.get(i).width, a.get(i).height);
					lastAsteroidTime=System.currentTimeMillis();
					isDestroyed.set(i,true);
					//Stop asteroid explosion to play it again.
					soundMan.stopAsteroidExplosion();
					// play asteroid explosion sound
					soundMan.playAsteroidExplosionSound();
					if(armorShield)
					{
						soundMan.playShieldDown();
					}
					else{deadTime = System.currentTimeMillis();}
					armorShield=false;
					break;
				}
				else if (a.get(i) instanceof EnemyShip){
					// "enemyShip" asteroid
					enemyShipExplosion = new Rectangle(
							a.get(i).x,
							a.get(i).y,
							a.get(i).width,
							a.get(i).height);
					a.get(i).setLocation(-a.get(i).width, -a.get(i).height);
					lastEnemyShip = System.currentTimeMillis();
					soundMan.stopShipExplosion();
					soundMan.playShipExplosionSound();
					enemyShipDestroyed.set(i, true);
					if(armorShield)
					{
						soundMan.playShieldDown();
					}
					else{deadTime = System.currentTimeMillis();}
					armorShield=false;
					break;
				}
				else if (a.get(i) instanceof Shield)
				{
					a.remove(i);
					armorShield=true;
					soundMan.playPowerUpSound();
				}
				else if (a.get(i) instanceof Life)
				{
					a.remove(i);
					status.setShipsLeft(status.getShipsLeft() + 1);
					soundMan.playPowerUpSound();
				}
				else
				{
					a.remove(i);
					threeBulletsPowerUp=true;
					long currentTime = System.currentTimeMillis();
					timeForBulletPU=currentTime;
					soundMan.playPowerUpSound();
				}

			}
		}
	}
	public void checkCollisions(List<Bullet> bullets, Object anObject,int score)
	{
		for(int i=0;i<bullets.size();i++)
		{
			Bullet bullet = bullets.get(i);
			if(anObject instanceof EnemyShipHard)
			{
				EnemyShipHard enemyShipHard=(EnemyShipHard) anObject;
				if(enemyShipHard.intersects(bullet)){
					// increase asteroids destroyed count
					status.setEnemiesDestroyed(status.getEnemiesDestroyed()+1);
					status.addScore(score);
					// "remove" asteroid
					shipHardExplosion = new Rectangle(
							enemyShipHard.x,
							enemyShipHard.y,
							enemyShipHard.width,
							enemyShipHard.height);
					enemyShipHard.setLocation(rand.nextInt(getWidth() - enemyShipHard.width), -enemyShipHard.height);
					shipHard=true;
					lastHardShip = System.currentTimeMillis();

					// play asteroid explosion sound
					soundMan.playShipExplosionSound();

					// remove bullet
					bullets.remove(i);
					break;
				}
			}
			else if(anObject instanceof SuicideBomber)
			{
				SuicideBomber enemyBomber=(SuicideBomber) anObject;
				if(enemyBomber.intersects(bullet)){

					if(enemyBomber.intersects(bullet)){
						// increase asteroids destroyed count
						status.setEnemiesDestroyed(status.getEnemiesDestroyed()+1);
						status.addScore(score);
						// "remove" asteroid
						suicideBomberExplosion = new Rectangle(
								enemyBomber.x,
								enemyBomber.y,
								enemyBomber.width,
								enemyBomber.height);
						enemyBomber.setLocation(-enemyBomber.width, -enemyBomber.height);
						suicideBomber=true;
						lastSuicideBomber = System.currentTimeMillis();

						// play asteroid explosion sound
						soundMan.playShipExplosionSound();

						// remove bullet
						bullets.remove(i);
						break;
					}
				}
			}
		}
	}
	public boolean checkThreeBullet(){
		return threeBulletsPowerUp;
	}

}