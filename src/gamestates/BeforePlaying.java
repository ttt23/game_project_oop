package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import map.MapConstants;
import map.MapManager;
import ui.SelectionButton;
import utilz.LoadSave;

public class BeforePlaying extends State implements Statemethods{
	
	private MapManager levelManager;
	private SelectionButton[] selectButtons = new SelectionButton[2]; //2 buttons trái-phải
	private BufferedImage pickYourMap; 
	public int index = 0; //Chỉ số để chọn map; 
	
	public BeforePlaying(Game game) {
		super(game); 
		levelManager = new MapManager(game); 
		pickYourMap = LoadSave.GetSpriteAtlas(LoadSave.PICKYOURMAP);
		loadButtons();
	}
	
	
	private void loadButtons() {
		selectButtons[0] = new SelectionButton((int)(5*Game.SCALE + 99*Game.SCALE), (int)(620), 0); // nút bấm bên phải; 
		selectButtons[1] = new SelectionButton((int)(5*Game.SCALE), (int)(620), 1); // nút bấm bên trái; 
		
	}


	@Override
	public void update() {
		for(SelectionButton sb : selectButtons)
			sb.update(); 
		levelManager.update(); 
		
	}

	@Override
	public void draw(Graphics g) {
		levelManager.draw(g, 0, MapConstants.map[index]);
		g.setColor(new Color(0,0,0,100));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT); 
//		g.drawImage(pickYourMap, (int)(Game.GAME_WIDTH/2 - 769*Game.SCALE/2), 10, (int)(769*Game.SCALE), (int)(75*Game.SCALE), null); 
		g.setColor(Color.WHITE);
		g.drawString("Press ENTER to play the game", (int)(10*Game.SCALE), (int)((700)));
		g.drawString("Press ESC to return the home screen",(int)(10*Game.SCALE), (int)((715)));
		for(SelectionButton sb: selectButtons) {
			sb.draw(g); 
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(SelectionButton sb : selectButtons) {
			if(sb.isIn(e)) {
				sb.setMousePressed(true);
				break;
			}
		}	
	}
	
	private void resetButtons() {
		for(SelectionButton sb : selectButtons)
			sb.resetBools();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(selectButtons[0].isMousePressed()) {
			if(index == 4) 
				index = 0; 
			else 
				index++; 
		}
		if (selectButtons[1].isMousePressed()) {	
			if(index == 0) 
				index = 4; 
			else 
				index--; 
		}
			
		resetButtons();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for(SelectionButton sb : selectButtons) 
			sb.setMouseOver(false);
		for(SelectionButton sb : selectButtons) 
			if(sb.isIn(e)) {
				sb.setMouseOver(true);
				break;
			}
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			Gamestate.state = Gamestate.PLAYING;
			game.getMenu().ingame = true; 
			game.getAudioPlayer().setBattleSong();
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Gamestate.state = Gamestate.MENU; 
			resetAll(); 
			game.getMenu().reset(); 
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(index == 0) 
				index = 4; 
			else 
				index--;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(index == 4) 
				index = 0; 
			else 
				index++; 
		}
		
	}

	@Override
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