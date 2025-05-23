package ui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import main.Game;
import utilz.LoadSave;

public class SettingsOverlay {
	private Game game; 
	private AudioOptions audioOptions; 
	private MenuButton menuB; 
	private BufferedImage settingBackgroundImg;
	
	public SettingsOverlay(Game game){
		this.game = game; 
		audioOptions = game.getAudioOptions(); 
		menuB = new MenuButton((int)(576 * Game.SCALE), (int)(340 * Game.SCALE), 4, Gamestate.MENU);
		settingBackgroundImg = LoadSave.GetSpriteAtlas(LoadSave.SETTING_BACKGROUND);
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(0,0,0,150));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
		g.drawImage(settingBackgroundImg, (int)(441 * Game.SCALE), (int)(128 * Game.SCALE), (int)(270 * Game.SCALE), (int)(288 * Game.SCALE), null);
		audioOptions.draw(g);
		menuB.draw(g); 
	}
	
	public void update() {
		audioOptions.update();
		menuB.update();
	}
	
	public void mouseDragged(MouseEvent e) {
		audioOptions.mouseDragged(e);
	}
	
	public void mousePressed(MouseEvent e) {
		audioOptions.mousePressed(e);
		if(isIn(e, menuB))
			menuB.setMousePressed(true);
	}
	
	public void mouseReleased(MouseEvent e) {
		audioOptions.mouseReleased(e);
		if(isIn(e, menuB)) {
			if(menuB.isMousePressed()) {
				game.getMenu().isSettingsOverlay = false;
			}
		}
		menuB.resetBools();
	}
	
	public void mouseMoved(MouseEvent e) {
		audioOptions.mouseMoved(e);
		menuB.setMouseOver(false);
		if(isIn(e, menuB))
			menuB.setMouseOver(true);
	}
	
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			game.getMenu().isSettingsOverlay = false;  
		}
	}
	
	public AudioOptions getAudioOptions() {
		return audioOptions; 
	}
	
	private boolean isIn(MouseEvent e, MenuButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
}