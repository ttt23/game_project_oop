package main;

import java.awt.Graphics;

import audio.AudioPlayer;
import gamestates.Menu;
import gamestates.BeforePlaying;
import gamestates.Gamestate;
import gamestates.Playing;
import ui.AudioOptions;


public class Game implements Runnable {

	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	private Playing playing;
	private Menu menu;
	private AudioPlayer audioPlayer;
	private BeforePlaying beforePlaying;   
	private AudioOptions audioOptions; 
	public final static int TILES_DEFAUL_SIZE = 36 ;
	public final static float SCALE = 1.2f;
	public final static int TILES_IN_WIDTH = 32;
	public final static int TILES_IN_HEIGHT = 18;
	public final static int TILES_SIZE=(int)(TILES_DEFAUL_SIZE*SCALE);
	public final static int GAME_WIDTH = TILES_SIZE*TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE*TILES_IN_HEIGHT;



	public Game() {
		System.out.println("size: " + GAME_WIDTH + " : " + GAME_HEIGHT);
		initClasses();
		gamePanel = new GamePanel(this);
		new GameWindow(gamePanel);
		gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
		startGameLoop();
	}

	private void initClasses() {
		audioOptions = new AudioOptions(this); 
		menu = new Menu(this);
		beforePlaying = new BeforePlaying(this);
		playing = new Playing(this);
		audioPlayer = new AudioPlayer(this);
		audioPlayer.playSong(AudioPlayer.MENU_1);
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void update() {
		switch(Gamestate.state) {
		case MENU:
			menu.update();
			break;
		case BEFOREPLAYING: 
			beforePlaying.update(); 
			break; 
		case PLAYING:
			playing.update();
			break;
		case QUIT:	
			System.exit(0);
		default:
			break;
		
		}
	}

	public void render(Graphics g) {
		switch(Gamestate.state) {
		case MENU:
			menu.draw(g);
			break;
		case BEFOREPLAYING: 
			beforePlaying.draw(g); 
			break; 
		case PLAYING:
			playing.draw(g);
			break;
		default:
			break;
		
		}
	}

	@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while (true) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}

			if (deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);

				frames = 0;
				updates = 0;

			}
		}

	}

	public void windowFocusLost() {
		if(Gamestate.state == Gamestate.PLAYING) {
			playing.getPlayer1().resetDirBooleans();
			playing.getPlayer2().resetDirBooleans();
		}
	}
	public Menu getMenu(){
		return menu;
	}
	
	public BeforePlaying getBeforePlaying() {
		return beforePlaying; 
	}
	
	public Playing getPlaying() {
		return playing;
	}
	
	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}

	public AudioOptions getAudioOptions() {
		return audioOptions;
	}
}