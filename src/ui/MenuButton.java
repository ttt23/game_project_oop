package ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import gamestates.Gamestate;
import utilz.LoadSave;
import static utilz.Constants.UI.Buttons.*;

public class MenuButton {
	private int xPos, yPos, rowIndex, index;
	private boolean isInAnimation;
	private int xOffsetCenter = B_WIDTH / 2;
	private Gamestate state;
	private BufferedImage[] imgs;
	private boolean mouseOver, mousePressed;
	private Rectangle bounds;
	public MenuButton(int xPos, int yPos, int rowIndex, Gamestate state) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.rowIndex = rowIndex;
		this.state = state;
		loadImgs();
		iniBounds();
	}
	private void iniBounds() {
		bounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
		
	}
	private void loadImgs() {
		imgs = new BufferedImage[5];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTON);
		for(int i = 0; i < imgs.length; i++) 
			imgs[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
	}
	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);
	}
	public void update() {
		if (!isInAnimation)
			index = 0;
		
		if(mouseOver)
			index = 1;
		if(mousePressed) {
			isInAnimation = true;
			index = 2;
			index++;
			if (index == imgs.length)
				isInAnimation = false;
		}
	}
	public boolean isMouseOver() {
		return mouseOver;
	}
	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}
	public boolean isMousePressed() {
		return mousePressed;
	}
	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	public Rectangle getBounds() {
		return bounds;
	}
	public void applyGamestate() {
		Gamestate.state = state;
	}
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
		isInAnimation = false;
	}
	
	public Gamestate getState() {
		return state;
	}

}