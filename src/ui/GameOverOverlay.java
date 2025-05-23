package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import audio.AudioPlayer;
import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class GameOverOverlay {

	private Playing playing;
	private BufferedImage img;
	private int imgX, imgY, imgW, imgH;
	private GameOverButton mainMenu, restart;

	public GameOverOverlay(Playing playing) {
		this.playing = playing;
		createImg();
		createButtons();
	}

	private void createButtons() {
		int mainMenuX = (int) (300 * Game.SCALE);
		int restartX = (int) (600 * Game.SCALE);
		int y = (int) (500 * Game.SCALE);
		mainMenu = new GameOverButton(mainMenuX, y, 0);
		restart = new GameOverButton(restartX, y, 1);
		
	}

	private void createImg() {
		img = LoadSave.GetSpriteAtlas(LoadSave.GAME_OVER);
		imgW = (int) (img.getWidth() * Game.SCALE);
		imgH = (int) (img.getHeight() * Game.SCALE);
		imgX = Game.GAME_WIDTH / 2 - imgW / 2;
		imgY = (int) (10 * Game.SCALE);
		
	}
	
	public void update() {
		mainMenu.update();
		restart.update();
	}

	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
		
		g.drawImage(img, imgX, imgY, imgW, imgH, null);
		
		mainMenu.draw(g);
		restart.draw(g);
	}

	private boolean isIn(GameOverButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

	public void mouseMoved(MouseEvent e) {
		restart.setMouseOver(false);
		mainMenu.setMouseOver(false);

		if (isIn(mainMenu, e))
			mainMenu.setMouseOver(true);
		else if (isIn(restart, e))
			restart.setMouseOver(true);
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(mainMenu, e)) {
			if (mainMenu.isMousePressed()) {
				playing.getGame().getBeforePlaying().reset();
				playing.resetAll();
				playing.setGamestate(Gamestate.MENU);
			}
		} else if (isIn(restart, e))
			if (restart.isMousePressed()) {
				playing.resetAll();
				playing.getGame().getAudioPlayer().setBattleSong();
			}

		mainMenu.resetBools();
		restart.resetBools();
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(mainMenu, e))
			mainMenu.setMousePressed(true);
		else if (isIn(restart, e))
			restart.setMousePressed(true);
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			playing.getGame().getBeforePlaying().reset();
			playing.resetAll();
			playing.setGamestate(Gamestate.MENU);
		}
	}
}