package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class PauseOverlay {
	private Playing playing;
	private UrmButton menuB, replayB, unpauseB;
	private AudioOptions audioOptions; 

	public PauseOverlay(Playing playing) {
		this.playing = playing;
		audioOptions = playing.getGame().getAudioOptions(); 
		createUrmButtons();
	}
	
	private void createUrmButtons() {
		int menuY = (int)(334 * Game.SCALE);
		int replayY = (int)(394 * Game.SCALE);
		int unpauseY = (int)(454 * Game.SCALE);
		int bx = (int)(448 * Game.SCALE);
		menuB = new UrmButton(bx, menuY, (int)(256*Game.SCALE), (int)(64*Game.SCALE), 0);
		replayB = new UrmButton(bx, replayY, (int)(256*Game.SCALE), (int)(64*Game.SCALE), 1);
		unpauseB = new UrmButton(bx, unpauseY, (int)(256*Game.SCALE), (int)(64*Game.SCALE), 2);
	}

	public void update() {
		audioOptions.update(); 
		menuB.update();
		replayB.update();
		unpauseB.update();
	}
	
	public void draw(Graphics g) {
		g.drawImage(LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND), (int)(441 * Game.SCALE), (int)(128 * Game.SCALE), (int)(270 * Game.SCALE), (int)(400 * Game.SCALE), null);
		audioOptions.draw(g); 
		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);
	}
	public void mouseDragged(MouseEvent e) {
		audioOptions.mouseDragged(e); 
	}
	
	public void mousePressed(MouseEvent e) {
		
		if(isIn(e, menuB))
			menuB.setMousePressed(true);
		else if(isIn(e, replayB))
			replayB.setMousePressed(true);
		else if(isIn(e, unpauseB))
			unpauseB.setMousePressed(true);
		else 
			audioOptions.mousePressed(e); 
	}

	
	public void mouseReleased(MouseEvent e) {
		if(isIn(e, menuB)) {
			if (menuB.isMousePressed()) {
				playing.resetAll();
				playing.getGame().getMenu().reset();
				playing.setGamestate(Gamestate.MENU);
				playing.unpauseGame();
				playing.getGame().getBeforePlaying().reset();
			}
		}
		else if(isIn(e, replayB)) {
			if(replayB.isMousePressed()) {
				playing.resetAll();
				playing.getGame().getAudioPlayer().setBattleSong();
			}
		}
		else if(isIn(e, unpauseB)) {
			if(unpauseB.isMousePressed())
				playing.unpauseGame();
		}else 
			audioOptions.mouseReleased(e); 

	menuB.resetBools();
	replayB.resetBools();
	unpauseB.resetBools();
	}

	
	public void mouseMoved(MouseEvent e) {
		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);
		if(isIn(e, menuB))
			menuB.setMouseOver(true);
		else if(isIn(e, replayB))
			replayB.setMouseOver(true);
		else if(isIn(e, unpauseB))
			unpauseB.setMouseOver(true);
		else 
			audioOptions.mouseMoved(e);  
		
	}
	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

}
