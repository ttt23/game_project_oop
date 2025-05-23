package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import audio.AudioPlayer;
import entities.Player;
import entities.PlayerData;
import main.Game;
import map.Map;
import map.MapConstants;
import map.MapManager;
import ui.GameOverOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;



public class Playing extends State implements Statemethods{
	private Player player1, player2;
	private MapManager levelManager;
	private PauseOverlay pauseOverlay;
	private GameOverOverlay gameOverOverlay;
	private boolean gameOver;
	private boolean paused = false;
	public boolean playing = false;
	public BufferedImage winningPlayerImg1,winningPlayerImg2;
	private int xLvlOffset;
	private int lefBorder = (int)(0.2 * Game.GAME_WIDTH);
	private int rightBorder = (int)(0.8 * Game.GAME_WIDTH);
	private int mapTilesWide = LoadSave.GetMapData(LoadSave.MAP1_DATA)[0].length;
	private int maxTilesOffset = mapTilesWide - Game.TILES_IN_WIDTH;
	private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;
	private long pausedTime;
	public long extraTime;

	public PlayerData player1Data, player2Data;
	public AudioPlayer audioPlayer1,audioPlayer2;
	
	public Playing(Game game) {
		super(game);
		levelManager = new MapManager(game);
		audioPlayer1 = new AudioPlayer(game);
		audioPlayer2 = new AudioPlayer(game);
	}
	
	public void initClasses() {
		switch(game.getMenu().s1) {
		case 0:
			player1Data = PlayerData.SHOUNEN_SAMURAI;
			break;
		case 1:
			player1Data = PlayerData.DEMON_SAMURAI;
			break;
		case 2:
			player1Data = PlayerData.WOLF_SAMURAI;
			break;
		case 3:
			player1Data = PlayerData.SHOGUN_SAMURAI;
			break;
		case 4:
			player1Data = PlayerData.MASTER_SAMURAI;
			break;
		case 5:
			player1Data = PlayerData.WANDERLUST_SAMURAI;
			break;
	}
		switch(game.getMenu().s2) {
		
		case 0:
			player2Data = PlayerData.SHOUNEN_SAMURAI;
			break;
		case 1:
			player2Data = PlayerData.DEMON_SAMURAI;
			break;
		case 2:
			player2Data = PlayerData.WOLF_SAMURAI;
			break;
		case 3:
			player2Data = PlayerData.SHOGUN_SAMURAI;
			break;
		case 4:
			player2Data = PlayerData.MASTER_SAMURAI;
			break;
		case 5:
			player2Data = PlayerData.WANDERLUST_SAMURAI;
			break;
	}
		player1 = new Player(180*Game.SCALE, 0*Game.SCALE, player1Data,this);
		player2 = new Player(936*Game.SCALE, 0*Game.SCALE,player2Data,this);
		player1.enemy=player2;
		player2.enemy=player1;
		player1.audioPlayer = audioPlayer1;
		player2.audioPlayer = audioPlayer2;
		player1.loadLvlData(MapConstants.map[game.getBeforePlaying().index].getMapData());
		player2.loadLvlData(MapConstants.map[game.getBeforePlaying().index].getMapData());
		player1.inRight = true;
		player2.inLeft = true;
		pauseOverlay = new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
		
		player1.i=0;
		player2.i=1;
		player1.j=-1;
		player2.j=1;
		player1.setHealthBarXStart((int) (10 * Game.SCALE));
		player2.setHealthBarXStart((int) (950 * Game.SCALE));
		player1.setPowerBarXStart((int) (30 * Game.SCALE));
		player2.setPowerBarXStart((int) (930 * Game.SCALE));

		winningPlayerImg1 = LoadSave.GetSpriteAtlas(LoadSave.PLAYER1_WIN);
		winningPlayerImg2 = LoadSave.GetSpriteAtlas(LoadSave.PLAYER2_WIN);
	}
	
	@Override
	public void update() {
		if (gameOver) {
			gameOverOverlay.update();
			player1.update();
			player2.update();
		}
		else if(game.getMenu().ingame) {
			initClasses();
			game.getMenu().ingame = false;
			playing = true;
		}
		else if(!paused&&playing) {
			levelManager.update();
			player1.update();
			player2.update();
			checkCloseToBorder();
			} else {
			pauseOverlay.update();
			extraTime = System.currentTimeMillis() - pausedTime;
		}
	}

	private void checkCloseToBorder() {
		int playerX = (int) player1.getHibox().x;
		int diff = playerX - xLvlOffset;
		if (diff > rightBorder)
			xLvlOffset += diff - rightBorder;
		else if (diff < lefBorder)
			xLvlOffset += diff - lefBorder;
		if(xLvlOffset > maxLvlOffsetX)
			xLvlOffset = maxLvlOffsetX;
		else if( xLvlOffset < 0)
			xLvlOffset = 0;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
		g.setColor(Color.WHITE);
		g.drawString("LOADING", Game.GAME_WIDTH/2, Game.GAME_HEIGHT/2);
		if(playing) {
		levelManager.draw(g, xLvlOffset, MapConstants.map[game.getBeforePlaying().index]);	
		if(player2.lose) {
			game.getAudioPlayer().stopSong();
			g.drawImage(winningPlayerImg1, Game.GAME_WIDTH/2-(int)(320*Game.SCALE), Game.GAME_HEIGHT/2-(int)(180*Game.SCALE), (int)(640*Game.SCALE), (int)(360*Game.SCALE), null);
		}
		if(player1.lose) {
			game.getAudioPlayer().stopSong();
			g.drawImage(winningPlayerImg2, Game.GAME_WIDTH/2-(int)(320*Game.SCALE), Game.GAME_HEIGHT/2-(int)(180*Game.SCALE), (int)(640*Game.SCALE), (int)(360*Game.SCALE), null);	
		}
		player1.render(g, xLvlOffset);
		player2.render(g, xLvlOffset);

		if(paused) {
			 g.setColor(new Color(0,0,0,150));
			 g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		}else if (gameOver) {
			gameOverOverlay.draw(g);
		}
	}
}

	public void resetAll() {
		player1.resetAll();
		player2.resetAll();
		player1.inRight = true;
		player2.inLeft = true;
		gameOver = false;
		paused = false;
	}
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
		game.getMenu().reset();
	}


	@Override
	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if(gameOver)
			gameOverOverlay.mousePressed(e);
		else if(paused)
			pauseOverlay.mousePressed(e);
		
	}
	public void mouseDragged(MouseEvent e) {
		if(!gameOver)
			if(paused)
				pauseOverlay.mouseDragged(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(gameOver)
			gameOverOverlay.mouseReleased(e);
		else if(paused)
			pauseOverlay.mouseReleased(e);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(gameOver)
			gameOverOverlay.mouseMoved(e);
		else if(paused)
			pauseOverlay.mouseMoved(e);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(gameOver)
			gameOverOverlay.keyPressed(e);
		else
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			player1.setLeft(true);
			break;
		case KeyEvent.VK_D:
			player1.setRight(true);
			break;
		case KeyEvent.VK_W:
			if(player1.checkInAir()) {
			player1.setDoubleJump(true);
			} else {
				player1.setJump(true);
			}
			break;	
		case KeyEvent.VK_S:
			player1.setSkill();
			break;
		case KeyEvent.VK_J:
			player1.setAttack1(true);
			break;
		case KeyEvent.VK_K:
			player1.setAttack2(true);
			break;
		case KeyEvent.VK_L:
			player1.setAttack3(true);
			break;
		case KeyEvent.VK_I:
			player1.setSpecialAttack();
			break;	
			
		case KeyEvent.VK_ESCAPE:
			paused = !paused;
			if(paused)
				pausedTime = System.currentTimeMillis();
			if(!paused)
				extraTime = 0;
			break;
			
		case KeyEvent.VK_LEFT:
			player2.setLeft(true);
			break;
		case KeyEvent.VK_RIGHT:
			player2.setRight(true);
			break;
		case KeyEvent.VK_UP:
			if(player2.checkInAir()) {
				player2.setDoubleJump(true);
				} else {
					player2.setJump(true);
				}
			break;	
		case KeyEvent.VK_DOWN:
			player2.setSkill();
			break;
		case KeyEvent.VK_NUMPAD1:
			player2.setAttack1(true);
			break;	
		case KeyEvent.VK_NUMPAD2:
			player2.setAttack2(true);
			break;	
		case KeyEvent.VK_NUMPAD3:
			player2.setAttack3(true);
			break;	
		case KeyEvent.VK_NUMPAD0:
			player2.setSpecialAttack();
			break;	
		
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(!gameOver)
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			player1.setLeft(false);
			break;
		case KeyEvent.VK_D:
			player1.setRight(false);
			break;
		case KeyEvent.VK_W:{
			player1.setJump(false);
			player1.setDoubleJump(false);
		}
			break;
			
		case KeyEvent.VK_LEFT:
			player2.setLeft(false);
			break;
		case KeyEvent.VK_RIGHT:
			player2.setRight(false);
			break;
		case KeyEvent.VK_UP:{
			player2.setJump(false);
			player2.setDoubleJump(false);
		}
			break;
	}

		
	}
	public void windowFocusLost() {
		player1.resetDirBooleans();
		player2.resetDirBooleans();

	}

	public Player getPlayer1() {
		return player1;
	}
	public Player getPlayer2() {
		return player2;
	}
	public void unpauseGame() {
		paused = false;
	}
	public Map getMap() {
		return MapConstants.map[game.getBeforePlaying().index];
	}
	public boolean getGameOver() {
		return gameOver;
	}
	//NMT CODE
		public AudioPlayer getAudioPlayer1() {
			return audioPlayer1; 
		}
		//NMT CODE
		public AudioPlayer getAudioPlayer2() {
			return audioPlayer2; 
		}
}
