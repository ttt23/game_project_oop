package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;
import static utilz.Constants.UI.VolumeButtons.*;

public class VolumeButton extends PauseButton{

	private BufferedImage imgs;
	private BufferedImage slider;
	private boolean mouseOver, mousePressed;
	private int buttonX, minX, maxX;
	private float floatValue = 0f; 
	public VolumeButton(int x, int y, int width, int height) {
		super(x + width / 2, y, VOLUME_WIDTH, height);
		bounds.x -= VOLUME_WIDTH / 2;
		buttonX = x + width / 2;
		this.x = x;
		this.width = width;
		minX = x + VOLUME_WIDTH / 2 + (int)(28*Game.SCALE);
		maxX = x + width - VOLUME_WIDTH / 2-(int)(20*Game.SCALE);
		changeX((int)(minX * 0.2f+maxX * 0.8f));
		loadImgs();
	}

	private void loadImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.VOLUME_BUTTONS);
		imgs = temp.getSubimage(31, 0, 2, 16);
		slider = LoadSave.GetSpriteAtlas(LoadSave.SLIDER);
		}

	public void update() {

		
	}
	public void draw(Graphics g) {
		g.drawImage(slider, x, y, width, height, null);
		g.drawImage(imgs, buttonX - VOLUME_WIDTH / 2, y, VOLUME_WIDTH, height, null);
	}
	public void changeX(int x) {
		if(x < minX)
			buttonX = minX;
		else if(x > maxX)
			buttonX = maxX;
		else 
			buttonX = x;
		updateFloatValue(); 
		bounds.x = buttonX - VOLUME_WIDTH / 2;
	}
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
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
	
	private void updateFloatValue() {
		float range = maxX - minX; 
		float value = buttonX - minX; 
		floatValue = value/range; 
	}
	
	public float getFloatValue() {
		return floatValue; 
	}
	
}

