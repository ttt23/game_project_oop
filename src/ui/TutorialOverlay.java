package ui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import gamestates.Gamestate;
import main.Game;
import utilz.LoadSave;

public class TutorialOverlay {
	private SelectionButton[] selectButtons = new SelectionButton[2];
	MenuButton backButton;
	private int index = 0;
	private Game game;
	public boolean openTutorial = false;
	BufferedImage tutorial0,tutorial1,tutorial2,tutorial3,v,tutorial4,tutorial5,tutorial6;
	
	public TutorialOverlay(Game game) {
		this.game = game; 
		loadImg();
		loadButtons();
	}
	
	
	private void loadImg() {
		 tutorial0 = LoadSave.GetSpriteAtlas(LoadSave.TUTORIAL0);
		 tutorial1 = LoadSave.GetSpriteAtlas(LoadSave.TUTORIAL1);
		 tutorial2 = LoadSave.GetSpriteAtlas(LoadSave.TUTORIAL2);
		 tutorial3 = LoadSave.GetSpriteAtlas(LoadSave.TUTORIAL3);
		 tutorial4 = LoadSave.GetSpriteAtlas(LoadSave.TUTORIAL4);
		 tutorial5 = LoadSave.GetSpriteAtlas(LoadSave.TUTORIAL5);
		 tutorial6 = LoadSave.GetSpriteAtlas(LoadSave.TUTORIAL6);
		
	}


	private void loadButtons() {
		selectButtons[0] = new SelectionButton((int)(1070*Game.SCALE), (int)(566*Game.SCALE), 0);  
		selectButtons[1] = new SelectionButton((int)(18*Game.SCALE), (int)(566*Game.SCALE), 1);  
		backButton = new MenuButton(Game.GAME_WIDTH/2, (int)(580*Game.SCALE), 4, null);
	}


	public void update() {
		for(SelectionButton sb : selectButtons)
			sb.update(); 	
		backButton.update();
	}

	public void draw(Graphics g) {
		switch(index) {
		case 0:
			g.drawImage(tutorial0, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
			break;
		case 1:
			g.drawImage(tutorial1, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
			break;
		case 2:
			g.drawImage(tutorial2, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
			break;
		case 3:
			g.drawImage(tutorial3, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
			break;
		case 4:
			g.drawImage(tutorial4, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
			break;
		case 5:
			g.drawImage(tutorial5, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
			break;
		case 6:
			g.drawImage(tutorial6, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
			break;
		}
		for(SelectionButton sb: selectButtons) {
			sb.draw(g); 
		}
		backButton.draw(g);
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		for(SelectionButton sb : selectButtons) {
			if(sb.isIn(e)) {
				sb.setMousePressed(true);
				break;
			}
		}
		if(game.getMenu().isIn(e,backButton))
			backButton.setMousePressed(true);
	}
	
	private void resetButtons() {
		for(SelectionButton sb : selectButtons)
			sb.resetBools();
		backButton.resetBools();
	}

	public void mouseReleased(MouseEvent e) {
		if(selectButtons[0].isMousePressed()) {
			if(index == 6) 
				index = 6; 
			else 
				index++; 
		}
		if (selectButtons[1].isMousePressed()) {	
			if(index == 0) 
				index = 0; 
			else 
				index--; 
		}
		if(backButton.isMousePressed()) {
			openTutorial = false;
			resetAll();
		}
		resetButtons();
	}

	public void mouseMoved(MouseEvent e) {
		for(SelectionButton sb : selectButtons) 
			sb.setMouseOver(false);
		for(SelectionButton sb : selectButtons) 
			if(sb.isIn(e)) {
				sb.setMouseOver(true);
				break;
			}
		backButton.setMouseOver(false);
		if(game.getMenu().isIn(e, backButton))
			backButton.setMouseOver(true);
	}
	
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			openTutorial = false; 
			resetAll(); 
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(index == 0) 
				index = 0; 
			else 
				index--;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(index == 6) 
				index = 6; 
			else 
				index++; 
		}
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void reset() {
		index = 0; 
	}
	public void resetAll() {
		reset(); 
		resetButtons();	
	}
}
