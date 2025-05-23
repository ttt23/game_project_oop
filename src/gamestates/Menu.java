package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import ui.SelectionButton;
import ui.SettingsOverlay;
import ui.TutorialOverlay;
import utilz.LoadSave;

public class Menu extends State implements Statemethods {

	private MenuButton[] buttons = new MenuButton[4];
	private SelectionButton[] selectButtons = new SelectionButton[4];
	private BufferedImage backgroundImg,player1SbgImg,player2SbgImg,playerSelectionImg;
	private BufferedImage[] SelectionImg;
	public boolean ingame=false;
	public int s1=0,s2=0;
	private TutorialOverlay tutorialOverlay;
	private SettingsOverlay settingsOverlay; 
	public boolean isSettingsOverlay = false; 
	
	public Menu(Game game) {
		super(game);
		tutorialOverlay = new TutorialOverlay(game);
		settingsOverlay = new SettingsOverlay(game); 
		loadButtons();
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
		player1SbgImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYER1_SBG);
		player2SbgImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYER2_SBG);
		playerSelectionImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYERSELECTION);
		SelectionImg = new BufferedImage[6];
		for(int i=0; i<6;i++)
			SelectionImg[i] = playerSelectionImg.getSubimage(i*224, 0, 224, 224);
	}

	private void loadButtons() {
		buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int)(222 * Game.SCALE), 0, Gamestate.BEFOREPLAYING);
		buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int)(292 * Game.SCALE), 1, Gamestate.MENU);
		buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int)(362 * Game.SCALE), 2, Gamestate.MENU);
		buttons[3] = new MenuButton(Game.GAME_WIDTH / 2, (int)(432 * Game.SCALE), 3, Gamestate.QUIT);
		selectButtons[0] = new SelectionButton((int)(273*Game.SCALE), (int)(440*Game.SCALE), 0);
		selectButtons[1] = new SelectionButton((int)(994*Game.SCALE), (int)(440*Game.SCALE), 0);
		selectButtons[2] = new SelectionButton((int)(96*Game.SCALE), (int)(440*Game.SCALE), 1);
		selectButtons[3] = new SelectionButton((int)(817*Game.SCALE), (int)(440*Game.SCALE), 1);

	}

	@Override
	public void update() {
		if(!tutorialOverlay.openTutorial && !isSettingsOverlay) { 
			for(MenuButton mb : buttons)
				mb.update();
			for(SelectionButton rb : selectButtons)
				rb.update();
		}
		else if(tutorialOverlay.openTutorial)  
			tutorialOverlay.update();
		else 
			settingsOverlay.update();
	}

	@Override
	public void draw(Graphics g) {
		if(!tutorialOverlay.openTutorial) {
			g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
			for(MenuButton mb : buttons)
				mb.draw(g);
			for(SelectionButton rb : selectButtons)
				rb.draw(g);
			g.drawImage(player1SbgImg,(int)(100*Game.SCALE), (int)(200*Game.SCALE), (int)(230*Game.SCALE),  (int)(230*Game.SCALE), null);
			g.drawImage(player2SbgImg, (int)(822*Game.SCALE), (int)(200*Game.SCALE), (int)(230*Game.SCALE), (int)(230*Game.SCALE), null);
			g.drawImage(SelectionImg[s1],(int)(103*Game.SCALE), (int)(203*Game.SCALE), (int)(224*Game.SCALE),  (int)(224*Game.SCALE), null);
			switch(s1) {
			case 0:
				g.setColor(Color.CYAN);
				g.drawString("Shounen Samurai", (int)(245*Game.SCALE), (int)(215*Game.SCALE));
				break;
			case 1:
				g.setColor(Color.BLUE);
				g.drawString("Demon Samurai", (int)(252*Game.SCALE), (int)(215*Game.SCALE));
				break;
			case 2:
				g.setColor(Color.BLACK);
				g.drawString("Wolf Samurai", (int)(264*Game.SCALE), (int)(215*Game.SCALE));
				break;
			case 3:
				g.setColor(Color.RED);
				g.drawString("Shogun Samurai", (int)(250*Game.SCALE), (int)(215*Game.SCALE));
				break;
			case 4:
				g.setColor(Color.WHITE);
				g.drawString("Master Samurai", (int)(254*Game.SCALE), (int)(215*Game.SCALE));
				break;
			case 5:
				g.setColor(Color.GRAY);
				g.drawString("Wanderlust Samurai", (int)(234*Game.SCALE), (int)(215*Game.SCALE));
				break;	
			}
			g.drawImage(SelectionImg[s2],(int)(1049*Game.SCALE), (int)(203*Game.SCALE), (int)(-224*Game.SCALE),  (int)(224*Game.SCALE), null);
			switch(s2) {
			case 0:
				g.setColor(Color.CYAN);
				g.drawString("Shounen Samurai", (int)(829*Game.SCALE), (int)(215*Game.SCALE));
				break;
			case 1:
				g.setColor(Color.BLUE);
				g.drawString("Demon Samurai", (int)(829*Game.SCALE), (int)(215*Game.SCALE));
				break;
			case 2:
				g.setColor(Color.BLACK);
				g.drawString("Wolf Samurai", (int)(829*Game.SCALE), (int)(215*Game.SCALE));
				break;
			case 3:
				g.setColor(Color.RED);
				g.drawString("Shogun Samurai", (int)(829*Game.SCALE), (int)(215*Game.SCALE));
				break;
			case 4:
				g.setColor(Color.WHITE);
				g.drawString("Master Samurai", (int)(829*Game.SCALE), (int)(215*Game.SCALE));
				break;
			case 5:
				g.setColor(Color.GRAY);
				g.drawString("Wanderlust Samurai", (int)(829*Game.SCALE), (int)(215*Game.SCALE));
				break;	
		}
		if(isSettingsOverlay)  
			settingsOverlay.draw(g);
		} 
		else  
			tutorialOverlay.draw(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	if(!tutorialOverlay.openTutorial && !isSettingsOverlay) { 
		for(MenuButton mb : buttons) {
			if(isIn(e, mb)) {
				mb.setMousePressed(true);
				break;
			}
		}
			for(SelectionButton rb : selectButtons) {
				if(rb.isIn(e)) {
					rb.setMousePressed(true);
					break;
				}
		}
	}
	else if(tutorialOverlay.openTutorial)  
		tutorialOverlay.mousePressed(e);
	else //NMT CODE
		settingsOverlay.mousePressed(e);
	}
	
	public void mouseDragged(MouseEvent e) { 
		if(isSettingsOverlay) 
			settingsOverlay.mouseDragged(e);
	}

	private void resetButtons(MouseEvent e) {
		for(MenuButton mb : buttons) 
			mb.resetBools();
		for(SelectionButton rb : selectButtons) 
			rb.resetBools();
		for(SelectionButton rb : selectButtons) {
			if(rb.isIn(e)) {
				rb.setMouseOver(true);
				break;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(!tutorialOverlay.openTutorial && !isSettingsOverlay) {  
			for(MenuButton mb : buttons) {
				if(isIn(e, mb)) {
					if(buttons[2].isMousePressed())
						tutorialOverlay.openTutorial = true;
					else if (buttons[1].isMousePressed())  
						isSettingsOverlay = true; 
					else if (mb.isMousePressed())
						mb.applyGamestate();
				}
			}
		
			for(int i = 0;i<4;i++) {
				if(selectButtons[i].isIn(e)) {
					if(selectButtons[i].isMousePressed()) {
						if(i==0) {
							if(s1<5)
								s1++;
							else 
								s1 = 0;
						}
						if(i==1) {
							if(s2<5)
								s2++;
							else 
								s2 = 0;
						}
						if(i==2) {
							if(s1>0)
								s1--;
							else 
								s1 = 5;
						}
						if(i==3) {
							if(s2>0)
								s2--;
							else 
								s2 = 5;
						}
					}
					break;
				}
			}
			resetButtons(e); 
		}
		
		else if(tutorialOverlay.openTutorial) 
			tutorialOverlay.mouseReleased(e);
		else 
			settingsOverlay.mouseReleased(e);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!tutorialOverlay.openTutorial && !isSettingsOverlay) { 
			for(MenuButton mb : buttons) 
				mb.setMouseOver(false);
			for(MenuButton mb : buttons) 
				if(isIn(e, mb)) {
					mb.setMouseOver(true);
					break;
				}
			for(SelectionButton rb : selectButtons) 
				rb.setMouseOver(false);
			for(int i=0;i<4;i++) 
				if(selectButtons[i].isIn(e)) {
					selectButtons[i].setMouseOver(true);
					break;
				}
		}
		else if(tutorialOverlay.openTutorial) 
			tutorialOverlay.mouseMoved(e);
		else
			settingsOverlay.mouseMoved(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!tutorialOverlay.openTutorial && !isSettingsOverlay) {  
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				ingame = true;
				Gamestate.state = Gamestate.BEFOREPLAYING;
			}
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				tutorialOverlay.openTutorial = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_A) {
				if(s1 == 0) 
					s1 = 5; 
				else 
					s1--;
			}
			if(e.getKeyCode() == KeyEvent.VK_D) {
				if(s1 == 5) 
					s1 = 0; 
				else 
					s1++; 
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				if(s2 == 0) 
					s2 = 5; 
				else 
					s2--;
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if(s2 == 5) 
					s2 = 0; 
				else 
					s2++; 
			}
		}
		else if(tutorialOverlay.openTutorial) 
			tutorialOverlay.keyPressed(e);
		else 
			settingsOverlay.keyPressed(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void reset() {
		s1=0;
		s2=0;
		tutorialOverlay.resetAll();
	}

}