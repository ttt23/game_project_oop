//package ui;
//
//import java.awt.Graphics;
//import java.awt.Rectangle;
//import java.awt.event.MouseEvent;
//import java.awt.image.BufferedImage;
//
//import main.Game;
//import utilz.LoadSave;
//
//public class SelectionButton {
//	private int xPos, yPos, lf, index;
//	private BufferedImage[] imgs;
//	private boolean mouseOver, mousePressed;
//	private Rectangle bounds;
//	public SelectionButton(int xPos, int yPos, int lf) {
//		this.xPos = xPos;
//		this.yPos = yPos;
//		this.lf = lf;
//		loadImgs();
//		iniBounds();
//	}
//	private void iniBounds() {
//		bounds = new Rectangle(xPos, yPos, (int)(64*Game.SCALE), (int)(64*Game.SCALE));
//		
//	}
//	private void loadImgs() {
//		imgs = new BufferedImage[5];
//		BufferedImage temp;
//		if(lf==0)
//			temp = LoadSave.GetSpriteAtlas(LoadSave.RIGHT_BUTTON);
//		else
//			temp = LoadSave.GetSpriteAtlas(LoadSave.LEFT_BUTTON);
//		for(int i = 0; i < imgs.length; i++) 
//			imgs[i] = temp.getSubimage(i * 16, 0, 16, 16);
//	}
//	public void draw(Graphics g) {
//		g.drawImage(imgs[index], xPos , yPos, (int)(64*Game.SCALE), (int)(64*Game.SCALE), null);
//	}
//	public void update() {
//		index = 0;
//		if(mouseOver)
//			index = 1;
//		if(mousePressed)
//			index = 2;
//	}
//	public boolean isMouseOver() {
//		return mouseOver;
//	}
//	public void setMouseOver(boolean mouseOver) {
//		this.mouseOver = mouseOver;
//	}
//	public boolean isMousePressed() {
//		return mousePressed;
//	}
//	public void setMousePressed(boolean mousePressed) {
//		this.mousePressed = mousePressed;
//	}
//	public Rectangle getBounds() {
//		return bounds;
//	}
//	public void resetBools() {
//		mouseOver = false;
//		mousePressed = false;
//	}
//	public boolean isIn(MouseEvent e) {
//		return this.getBounds().contains(e.getX(), e.getY());
//	}
//}
package ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

public class SelectionButton {
	private int xPos, yPos, lf, index;
	private boolean isInAnimation = false;
	private BufferedImage[] imgs;
	private boolean mouseOver, mousePressed;
	private Rectangle bounds;
	public SelectionButton(int xPos, int yPos, int lf) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.lf = lf;
		loadImgs();
		iniBounds();
	}
	private void iniBounds() {
		bounds = new Rectangle(xPos, yPos, (int)(64*Game.SCALE), (int)(64*Game.SCALE));
		
	}
	private void loadImgs() {
		imgs = new BufferedImage[5];
		BufferedImage temp;
		if(lf==0)
			temp = LoadSave.GetSpriteAtlas(LoadSave.RIGHT_BUTTON);
		else
			temp = LoadSave.GetSpriteAtlas(LoadSave.LEFT_BUTTON);
		for(int i = 0; i < imgs.length; i++) 
			imgs[i] = temp.getSubimage(i * 16, 0, 16, 16);
	}
	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos , yPos, (int)(64*Game.SCALE), (int)(64*Game.SCALE), null);
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
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
		isInAnimation = false;
	}
	public boolean isIn(MouseEvent e) {
		return this.getBounds().contains(e.getX(), e.getY());
	}
}