package rbadia.voidspace.sounds;

import java.applet.Applet;
import java.applet.AudioClip;

import rbadia.voidspace.main.GameScreen;

/**
 * Manages and plays the game's sounds.
 */
public class SoundManager {
	private static final boolean SOUND_ON = true;

    private AudioClip shipExplosionSound = Applet.newAudioClip(GameScreen.class.getResource(
    "/rbadia/voidspace/sounds/shipExplosion1.wav"));
    private AudioClip shieldDownSound = Applet.newAudioClip(GameScreen.class.getResource(
    	    "/rbadia/voidspace/sounds/end.wav"));
    private AudioClip powerUpSound = Applet.newAudioClip(GameScreen.class.getResource(
    	    "/rbadia/voidspace/sounds/Powerup.wav"));
    private AudioClip asteroidExplosionSound=Applet.newAudioClip(GameScreen.class.getResource(
    	    "/rbadia/voidspace/sounds/asteroidExplosion2.wav"));
    private AudioClip gameOverSound = Applet.newAudioClip(GameScreen.class.getResource(
    	    "/rbadia/voidspace/sounds/gameOver.wav"));
    private AudioClip bulletSound = Applet.newAudioClip(GameScreen.class.getResource(
    "/rbadia/voidspace/sounds/laser.wav"));
    private AudioClip newLevelSound=Applet.newAudioClip(GameScreen.class.getResource(
    	    "/rbadia/voidspace/sounds/newLevel2.wav"));
    private AudioClip bulletEnemySound=Applet.newAudioClip(GameScreen.class.getResource(
    	    "/rbadia/voidspace/sounds/BulletEnemySound.wav"));
    private AudioClip beginThrusterSound = Applet.newAudioClip(GameScreen.class.getResource(
    	    "/rbadia/voidspace/sounds/BeginThrusterSound.wav"));
    private AudioClip thrusterSound = Applet.newAudioClip(GameScreen.class.getResource(
    	    "/rbadia/voidspace/sounds/ThrusterSound.wav"));
    
    /**
     * Plays sound for bullets fired by the ship.
     */
    public void playBulletSound(){
    	if(SOUND_ON){
    		new Thread(new Runnable(){
    			public void run() {
    				bulletSound.play();
    			}
    		}).start();
    	}
    }
    
    /**
     * Plays sound for ship explosions.
     */
    public void playShipExplosionSound(){
    	if(SOUND_ON){
    		new Thread(new Runnable(){
    			public void run() {
    				shipExplosionSound.play();
    			}
    		}).start();
    	}
    }
    public void playEnemyBulletSound(){
    	if(SOUND_ON){
    		new Thread(new Runnable(){
    			public void run() {
    				bulletEnemySound.play();
    			}
    		}).start();
    	}
    }
    public void gameOverSound(){
    	if(SOUND_ON){
    		new Thread(new Runnable(){
    			public void run() {
    				gameOverSound.play();
    			}
    		}).start();
    	}
    }
    
    /**
     * Plays sound for asteroid explosions.
     */
    public void playAsteroidExplosionSound(){
		// play sound for asteroid explosions
    	if(SOUND_ON){
    		new Thread(new Runnable(){
    			public void run() {
    				asteroidExplosionSound.play();
    			}
    		}).start();
    	}
    }
    /**
     * Plays sound for the start sound of the thrusters
     */
    public void playBeginThrusterSound(){
		// play sound for asteroid explosions
    	if(SOUND_ON){
    		new Thread(new Runnable(){
    			public void run() {
    				beginThrusterSound.play();
    			}
    		}).start();
    	}
    }
    public void playNewLevelSound(){
		// play sound for asteroid explosions
    	if(SOUND_ON){
    		new Thread(new Runnable(){
    			public void run() {
    				newLevelSound.play();
    			}
    		}).start();
    	}
    }
    /**
     * Plays sound for thruster
     */
    public void playThrusterSound(){
		// play sound for asteroid explosions
    	if(SOUND_ON){
    		new Thread(new Runnable(){
    			public void run() {
    				thrusterSound.loop();
    			}
    		}).start();
    	}
    }
    public void playPowerUpSound(){
		// play sound for asteroid explosions
    	if(SOUND_ON){
    		new Thread(new Runnable(){
    			public void run() {
    				powerUpSound.play();
    			}
    		}).start();
    	}
    }
    public void playShieldDown(){
		// play sound for asteroid explosions
    	if(SOUND_ON){
    		new Thread(new Runnable(){
    			public void run() {
    				shieldDownSound.play();
    			}
    		}).start();
    	}
    }
    public void stopAsteroidExplosion(){
    	asteroidExplosionSound.stop();
    }
    public void stopThrusterSound(){
    	thrusterSound.stop();
    }
    public void stopBeginThrusterSound(){
    	beginThrusterSound.stop();
    }

	public void stopBulletSound() {
		bulletSound.stop();
		
	}
	public void stopEnemyBulletSound() {
		bulletEnemySound.stop();
	}

	public void stopShipExplosion() {
		shipExplosionSound.stop();
		
	}
}
