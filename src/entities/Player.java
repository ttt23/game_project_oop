package entities;

import static entities.PlayerData.*;
import static utilz.HelpMethods.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.awt.print.Printable;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

import audio.AudioPlayer;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private int tick,aniTick, aniIndex, aniSpeed = 25;
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false, death = false, skill = false;
	private boolean left, up, right, down,jump,doubleJump, attack1=false, attack2=false, attack3=false, specialAttack=false;
	private float playerSpeed ;
	private int[][] lvlData;
	private float xDrawOffset;
	private float yDrawOffset;
	
	private float airSpeed = 0f;
	private float gravity = 0.05f*Game.SCALE;
	private float jumpSpeed = -2.8125f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5625f * Game.SCALE;
	private boolean inAir = false;
	private boolean canDoubleJump = false;
	private static final long SKILLTIMES = 16000;
	private long setSkillTime = 0;
	private boolean flame = false, speedUp = false,dash = false, healing = false,teleport = false; 
	
	private PlayerData playerData;
	private int flipX = 0;
	private int flipW =1;
	public boolean  inRight, inLeft;
	public boolean win = false, lose = false;
	public long endtime;
	
		public int i;
		public int j;
		private int flipHX = (int)(192*Game.SCALE);
		private int flipHW =-1;
		// StatusBarUI

		private BufferedImage[] healthRegen;
		private int healthRegenIndex;
		private boolean isStartGameHealthRegen;
		private BufferedImage[] healthBarAni;
		private int healthBarAniIndex;
		private int healthBarAniState;
		private int healthBarWidth = (int) (64 * 3 * Game.SCALE);
		private int healthBarHeight = (int) (16 * 3 * Game.SCALE);
		private int healthBarXStart = (int) (10 * Game.SCALE);
		private int healthBarYStart = (int) (10 * Game.SCALE);
		private float healthPercent;
		private int maxHealth;
		private int currentHealth;
		private int previousHealth;

		private BufferedImage[] manaSparkling;
		private BufferedImage[] manaRegen;
		private BufferedImage[] manaFading;
		private int powerBarRegenAniIndex;
		private int powerBarFadingAniIndex;
		private int powerBarRegenAniState;
		private int powerBarFadingAniState;
		private int powerBarSparklingIndex;
		private int powerBarWidth = (int) (64 * 3 * Game.SCALE);
		private int powerBarHeight = (int) (16 * 3 * Game.SCALE);
		private int powerBarSparklingHeight = (int) (18 * 3 * Game.SCALE);
		private int powerBarXStart = (int) (10 * Game.SCALE);
		private int powerBarYStart = (int) (powerBarHeight + 4 * Game.SCALE );
		private int powerMaxValue = 200;
		private int powerValue = 0;
		private float powerPercent;
		private int previousPowerValue;
		private boolean isPowerIncrease;
		private boolean isPowerBarSparkling;
		private boolean	isPowerBarFading;
		
		private float powerGrowSpeed = 0.5f;
		private int powerGrowTick;
	//atackbox
		private Rectangle2D.Float specialAttackBox,attackBox1,attackBox2,attackBox3;
		private Rectangle2D.Float[] shurikenBox;
		private BufferedImage shurikenImg;
		private int[] shurikenDirectton;
		private boolean[] throwShuriken;
		private int shurikenI = 0;
		private float shurikenEffect = 1;

		private boolean attackChecked = false,shurikenChecked = false,specialAttackEffect = false, knockBack = false;
		private boolean hurt;
		public Player enemy;
		private Playing playing;
		public AudioPlayer audioPlayer;

		public Player(float x, float y, PlayerData playerData, Playing playing) {
			super(x, y,playerData.width,playerData.height);
			this.playerData = playerData;
			this.xDrawOffset = this.playerData.xOffset ;
			this.yDrawOffset = this.playerData.yOffset ;
			this.playing = playing;
			this.playerSpeed = playerData.playerSpeed;
			this.isStartGameHealthRegen= true;
			this.maxHealth = playerData.playerMaxHealth;
			this.currentHealth = maxHealth;
			this.previousHealth = this.currentHealth;
			this.shurikenImg = LoadSave.GetSpriteAtlas(LoadSave.SHURIKEN);
			loadAnimations();
			initHitBox(x  , y , (int)(playerData.width / this.playerData.hitboxX ), (int)(playerData.height / this.playerData.hitboxY));
			initAttackBox();
		}

		public void initAttackBox() {
			specialAttackBox = new Rectangle2D.Float(x,y,(int)(playerData.SpecialAttackBoxWidth*Game.SCALE),(int)(playerData.SpecialAttackBoxHeight*Game.SCALE-4));
			attackBox1 = new Rectangle2D.Float(x,y,(int)(playerData.attackBox1Width*Game.SCALE),(int)(playerData.attackBox1Height*Game.SCALE-4));
			attackBox2 = new Rectangle2D.Float(x,y,(int)(playerData.attackBox2Width*Game.SCALE),(int)(playerData.attackBox2Height*Game.SCALE-4));
			attackBox3 = new Rectangle2D.Float(x,y,(int)(playerData.attackBox3Width*Game.SCALE),(int)(playerData.attackBox3Height*Game.SCALE-4));
			if(playerData==SHOUNEN_SAMURAI) {
				shurikenBox= new Rectangle2D.Float[5];
				throwShuriken = new boolean[5];
				shurikenDirectton = new int[5];
				for(int i =0;i<5;i++) {
					shurikenBox[i]=new Rectangle2D.Float(hitbox.x+hitbox.width+15*Game.SCALE, hitbox.y+hitbox.width/2+11*Game.SCALE, 11.5f*Game.SCALE, 11.5f*Game.SCALE);		
					throwShuriken[i] = false;
					}
			}
		}
		
		public void updateAttackBox() {
			if(playerData==SHOUNEN_SAMURAI) {
			for(int i = 0; i<5; i++) {
				if(throwShuriken[i]) 
					shurikenBox[i].x=shurikenBox[i].x+3.3f*shurikenDirectton[i]*Game.SCALE;
				else {
					if(inRight)
						shurikenDirectton[i]=1;
					else 
						shurikenDirectton[i]=-1;
				}
			}
		}
			
			if(inRight) {
				
				attackBox1.x = hitbox.x + hitbox.width + (int)(Game.SCALE*1);
				attackBox2.x = hitbox.x + hitbox.width + (int)(Game.SCALE*1);
				attackBox3.x = hitbox.x + hitbox.width + (int)(Game.SCALE*1);
				if(playerData!=SHOUNEN_SAMURAI)
				specialAttackBox.x = hitbox.x + hitbox.width + (int)(Game.SCALE*1);
				else 
					specialAttackBox.x = hitbox.x - specialAttackBox.width+hitbox.width + (int)(Game.SCALE*1);
				
			} 
			else if(inLeft) {
				
				attackBox1.x = hitbox.x - hitbox.width -(int)(Game.SCALE*playerData.at1i);
				attackBox2.x = hitbox.x - hitbox.width -(int)(Game.SCALE*playerData.at2i);
				attackBox3.x = hitbox.x - hitbox.width -(int)(Game.SCALE*playerData.at3i);
				if(playerData!=SHOUNEN_SAMURAI)
				specialAttackBox.x = hitbox.x - hitbox.width -(int)(Game.SCALE*playerData.sti);
				else
					specialAttackBox.x = hitbox.x + (int)(Game.SCALE*1);
				
			} 			
			attackBox1.y = hitbox.y + (Game.SCALE * playerData.at1j);
			attackBox2.y = hitbox.y + (Game.SCALE * playerData.at2j);
			attackBox3.y = hitbox.y + (Game.SCALE * playerData.at3j);
			specialAttackBox.y = hitbox.y + (Game.SCALE * (playerData.stj));
			
		}

		public void update() {
			updateHealthBar();
			updatePowerBar();
			if(traped(hitbox, lvlData)) {
				if(!hurt&&!win) {
				hurt = true;
				changeHealth(-5);		
				}
			}
			if(spiked(hitbox, lvlData)) {
				if(!hurt&&!win) {
				hurt = true;
				changeHealth(-2);		
				}
			}
			if (currentHealth <= 0) {
				if(!lose)
				endtime = System.currentTimeMillis();
				enemy.win = true;
				if(!death) {
					healthBarAniIndex = 44;
					healthBarAniState = 48;
				}
				death = true;
			}
			updateAttackBox();
			if(!lose)
			updatePos();
			if (attacking) {
				checkEnemyHit();	

			}
			if(!lose)
				updateAnimationTick();
			if(lose)
				checkGameOver();
			setAnimation();
			checkSkill();
			if(playerData==SHOUNEN_SAMURAI) {
				checkThrowShuriken();
			}
		}

		private void checkEnemyHit() {
//			if(!enemy.skill&&canHit()) {
			if((!(enemy.healing||(enemy.skill&&enemy.flame))||specialAttack)&&canHit()) {
			if(specialAttack) {
					if(playerData!=SHOUNEN_SAMURAI) {
					if (specialAttackBox.intersects(enemy.hitbox)) 
						if(!enemy.hurt&&!attackChecked) {
							enemy.changeHealth(-playerData.specialAttackDamage);
							if(flame)
								enemy.changeHealth(-5);
							specialAttackEffect = false;
							enemy.hurt=true;
//							enemy.knockBack = true;
							attackChecked=true;
							changePower(30);
						} 
					} else {
						if(aniIndex<4) {
							if (attackBox1.intersects(enemy.hitbox)) 
								if(!enemy.hurt&&!attackChecked) {
									enemy.changeHealth(-playerData.specialAttackDamage);
									specialAttackEffect = false;
									enemy.hurt=true;
//									enemy.knockBack = true;
									attackChecked=true;
									changePower(30);
						}
					} else if(aniIndex<6) {
						if (attackBox2.intersects(enemy.hitbox)) 
							if(!enemy.hurt&&!attackChecked) {
								enemy.changeHealth(-playerData.specialAttackDamage);
								specialAttackEffect = false;
								enemy.hurt=true;
//								enemy.knockBack = true;
								attackChecked=true;
								changePower(30);
							}
					} else {
						if (attackBox3.intersects(enemy.hitbox)||specialAttackBox.intersects(enemy.hitbox)) 
							if(!enemy.hurt&&!attackChecked) {
								enemy.changeHealth(-playerData.specialAttackDamage);
								specialAttackEffect = false;
								enemy.hurt=true;
//								enemy.knockBack = true;
								attackChecked=true;
								changePower(30);
							}
					}
				}
			}
			else 
				if(playerAction==ATTACK_1) {
					if (attackBox1.intersects(enemy.hitbox)) 
						if(!enemy.hurt&&!attackChecked) {
						enemy.changeHealth(-playerData.attack1Damage);
						if(flame)
							enemy.changeHealth(-5);
						enemy.hurt=true;
//						enemy.knockBack = true;
						attackChecked=true;
						changePower(10);
					}
				}
			else
				if(playerAction==ATTACK_2) {
					if (attackBox2.intersects(enemy.hitbox)) 
						if(!enemy.hurt&&!attackChecked) {
						enemy.changeHealth(-playerData.attack2Damage);
						if(flame)
							enemy.changeHealth(-5);
						enemy.hurt=true;
//						enemy.knockBack = true;
						attackChecked=true;
						changePower(10);
					}
				}
			else
				if(playerAction==ATTACK_3) {
					if (attackBox3.intersects(enemy.hitbox)||(playerData==SHOUNEN_SAMURAI&&specialAttackBox.intersects(enemy.hitbox))) 
						if(!enemy.hurt&&!attackChecked) {
						enemy.changeHealth(-playerData.attack3Damage);
						if(flame)
							enemy.changeHealth(-5);
						enemy.hurt=true;
//						enemy.knockBack = true;
						attackChecked=true;
						changePower(10);
					}
				}
			  } 
		}		
		
		private boolean checkKnockBack() {
			if(!enemy.skill&&canHit()&&enemy.attacking) {
				if(attackBox().intersects(enemy.hitbox)&&enemy.attackBox().intersects(hitbox))
					return true;
			}
			return false;
		}
		private Rectangle2D attackBox() {
			if(attack1)
				return attackBox1;
			if(attack1)
				return attackBox2;
			if(attack3)
				return attackBox3;
			else return specialAttackBox;
		}
		
		private boolean canHit() {
			
			if(specialAttack&&
				((playerData==SHOUNEN_SAMURAI&&aniIndex>=2&&aniIndex<8)||
				(playerData==DEMON_SAMURAI&&aniIndex>6&&aniIndex<=10&&!flame)||
				(playerData==DEMON_SAMURAI&&aniIndex>=6&&aniIndex<10&&flame)||
				(playerData==WOLF_SAMURAI&&aniIndex>=6)||
				(playerData==SHOGUN_SAMURAI&&aniIndex>=6)||
				(playerData==MASTER_SAMURAI&&aniIndex>=5&&aniIndex<=10)||
				(playerData==WANDERLUST_SAMURAI&&aniIndex>=4&&aniIndex<=6)))
				return true;
			else
			if(attack1&&
					((playerData==SHOUNEN_SAMURAI&&aniIndex>=2&&aniIndex<=4)||
					(playerData==DEMON_SAMURAI&&aniIndex>=3&&aniIndex<6)||
					(playerData==WOLF_SAMURAI&&aniIndex>=3)||
					(playerData==SHOGUN_SAMURAI&&aniIndex>=1)||
					(playerData==MASTER_SAMURAI&&aniIndex>=1)||
					(playerData==WANDERLUST_SAMURAI&&aniIndex>=2&&aniIndex<4)))
					return true;
			else
			if(attack2&&
					((playerData==SHOUNEN_SAMURAI&&aniIndex>0&&aniIndex<4)||
					(playerData==DEMON_SAMURAI&&aniIndex>=2)||
					(playerData==WOLF_SAMURAI&&aniIndex>=1)||
					(playerData==SHOGUN_SAMURAI&&aniIndex>=1&&aniIndex<4)||
					(playerData==MASTER_SAMURAI&&aniIndex<4)||
					(playerData==WANDERLUST_SAMURAI&&aniIndex>=2&&aniIndex<4)))
					return true;
			else
			if(attack3&&
					((playerData==SHOUNEN_SAMURAI&&aniIndex>0&&aniIndex<4)||
					(playerData==DEMON_SAMURAI&&aniIndex>=3&&aniIndex<6)||
					(playerData==WOLF_SAMURAI&&aniIndex>=1)||
					(playerData==SHOGUN_SAMURAI&&aniIndex>=2&&aniIndex<6)||
					(playerData==MASTER_SAMURAI&&aniIndex<4)||
					(playerData==WANDERLUST_SAMURAI&&aniIndex>=2&&aniIndex<4)))
					return true;
			else
			return false;
		}

		public void changeHealth(int value) {
			currentHealth += value;
			if (currentHealth <= 0)
				currentHealth = 0;
			else if (currentHealth >= maxHealth)
				currentHealth = maxHealth;
		}

		public void render(Graphics g, int lvlOffset) {
			if(this==playing.getPlayer1()) {
				g.setColor(Color.BLUE);
				g.fillRect((int)(hitbox.x-10*Game.SCALE), (int)( hitbox.y-20*Game.SCALE), (int)(50*Game.SCALE*currentHealth/maxHealth), (int)(4*Game.SCALE));
			} else {
				g.setColor(Color.RED);
				g.fillRect((int)(hitbox.x-10*Game.SCALE), (int)( hitbox.y-20*Game.SCALE), (int)(50*Game.SCALE*currentHealth/maxHealth), (int)(4*Game.SCALE));
			}
			g.setColor(Color.BLACK);
			g.drawRect((int)(hitbox.x-10*Game.SCALE), (int)( hitbox.y-20*Game.SCALE), (int)(50*Game.SCALE), (int)(4*Game.SCALE));
			if(flame&&!death&&(playerAction != SKILL)) {
				g.drawImage(animations[playerAction+11][aniIndex], (int)(hitbox.x-xDrawOffset) - lvlOffset + flipX, (int)(hitbox.y-yDrawOffset), width*flipW, height, null);
			}
			else {
				if(speedUp) {
					g.drawImage(animations[playerAction+10][aniIndex], (int)(hitbox.x-xDrawOffset) - lvlOffset + flipX, (int)(hitbox.y-yDrawOffset), width*flipW, height, null);
				}
			else
			g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x-xDrawOffset) - lvlOffset + flipX, (int)(hitbox.y-yDrawOffset), width*flipW, height, null);
			}
			if(playerData==SHOUNEN_SAMURAI)
			for(int i = 0; i<5; i++)
			if(throwShuriken[i]) {
				g.drawImage(shurikenImg, (int)(shurikenBox[i].x), (int)(shurikenBox[i].y), (int)(shurikenBox[i].width), (int)(shurikenBox[i].height), null);
//				g.drawRect((int)(shurikenBox[i].x), (int)(shurikenBox[i].y), (int)(shurikenBox[i].width), (int)(shurikenBox[i].height));
			}
//			drawHitBox(g);
//			drawAttackBox1(g,lvlOffset);
//			drawAttackBox2(g,lvlOffset);
//			drawAttackBox3(g,lvlOffset);
//			drawSpecialAttackBox(g,lvlOffset);
			drawUI(g);
			}

		
		private void drawSpecialAttackBox(Graphics g, int lvlOffsetX) {
			
			g.setColor(Color.red);
			g.drawRect((int)(specialAttackBox.x-lvlOffsetX),(int) specialAttackBox.y,(int)specialAttackBox.width,(int)specialAttackBox.height);
		}

		private void drawAttackBox1(Graphics g, int lvlOffsetX) {
			g.setColor(Color.green);
			g.drawRect((int) attackBox1.x-lvlOffsetX,(int) attackBox1.y,(int)attackBox1.width,(int)attackBox1.height);
			
		}
		private void drawAttackBox2(Graphics g, int lvlOffsetX) {
			g.setColor(Color.blue);
			g.drawRect((int) attackBox2.x-lvlOffsetX,(int) attackBox2.y,(int)attackBox2.width,(int)attackBox2.height);
			
		}
		private void drawAttackBox3(Graphics g, int lvlOffsetX) {
			g.setColor(Color.black);
			g.drawRect((int) attackBox3.x-lvlOffsetX,(int) attackBox3.y,(int)attackBox3.width,(int)attackBox3.height);
			
		}

		private void drawUI(Graphics g) {
			
			// Initialize
			if (isStartGameHealthRegen) {
				g.drawImage(healthRegen[healthRegenIndex], healthBarXStart + i * flipHX, healthBarYStart, healthBarWidth * j * flipHW, healthBarHeight, null);
			}
			
			// Health bar
			else if (healing && healthBarAniIndex == 0)
				g.drawImage(healthRegen[healthRegenIndex], healthBarXStart + i * flipHX, healthBarYStart, healthBarWidth * j * flipHW, healthBarHeight, null);
			else
				g.drawImage(healthBarAni[healthBarAniIndex], healthBarXStart + i * flipHX, healthBarYStart, healthBarWidth* j * flipHW, healthBarHeight, null);

			// Initialize
			if (powerValue == 0)
				g.drawImage(manaRegen[0], powerBarXStart+i * flipHX, powerBarYStart, powerBarWidth* j * flipHW, powerBarHeight, null);
			// Power Bar
			else if (isPowerBarSparkling)
				g.drawImage(manaSparkling[powerBarSparklingIndex], powerBarXStart+i * flipHX, powerBarYStart, powerBarWidth* j * flipHW, powerBarSparklingHeight, null);
			else if (isPowerIncrease)
				g.drawImage(manaRegen[powerBarRegenAniIndex], powerBarXStart+i * flipHX, powerBarYStart, powerBarWidth* j * flipHW, powerBarHeight, null);
			else
				g.drawImage(manaFading[powerBarFadingAniIndex], powerBarXStart+i * flipHX, powerBarYStart, powerBarWidth* j * flipHW, powerBarHeight, null);
		}
	
		private void updateHealthBar() {
			if (previousHealth == currentHealth)
				return;
			if (previousHealth > currentHealth)
				healing = false;
			else if (previousHealth < currentHealth) {
				healing = true;
				if (currentHealth == maxHealth)
					healthRegenIndex = 8;
			}
			healthPercent = currentHealth / (float) maxHealth;
			if (healthPercent > 0.125)
				healthBarAniState = 48 - (int) (healthPercent / 0.125f) * 6;
			else
				healthBarAniState = 48 - ((int)(healthPercent / 0.125f) + 1) * 6;
			previousHealth = currentHealth;
		}
		private void updatePowerBar() {

			powerGrowTick++;
			if (powerGrowTick >= powerGrowSpeed) {
				powerGrowTick = 0;
				changePower(1);
			}

			
			
			if (powerValue == previousPowerValue) {
				// Max
				if (powerValue == powerMaxValue) {
					isPowerBarSparkling = true;
				}
				
				else {
					isPowerBarSparkling = false;
					powerBarSparklingIndex = 0;
				}
				
				return;
			}
			
			
			
			// Increase
			if (powerValue - previousPowerValue > 0 && !isPowerBarFading) {
					isPowerIncrease = true;
					powerPercent = powerValue / (float) powerMaxValue;
					powerBarRegenAniState = (int) (powerPercent / 0.125f) * 5;
					powerBarFadingAniState = manaFading.length - powerBarRegenAniState;
					powerBarFadingAniIndex = manaFading.length - powerBarRegenAniIndex;
			}
			
			// Decrease 
			else {
				isPowerIncrease = false;
				isPowerBarFading = true;
				powerPercent = powerValue / (float) powerMaxValue;
				powerBarFadingAniState = manaFading.length - (int) (powerPercent / 0.125f) * 5;
				if (powerBarFadingAniIndex >= powerBarFadingAniState - 1) {
					powerBarRegenAniState = manaRegen.length - powerBarFadingAniState;
					powerBarRegenAniIndex = manaRegen.length - powerBarFadingAniIndex;
					isPowerBarFading = false;
				}
			}
			
			previousPowerValue = powerValue;
			
		}
		
		public void changePower(int value) {
			powerValue += value;
			powerValue = Math.max(Math.min(powerValue, powerMaxValue), 0);
		}

	private void updateAnimationTick() {
		aniTick++;
		tick++;
		if(tick>=aniSpeed/6) {
			if(!isPowerIncrease) {
				if (powerBarFadingAniIndex < powerBarFadingAniState - 1)
					powerBarFadingAniIndex++;
				tick = 0;
			}
		}
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (isStartGameHealthRegen)
				if (healthRegenIndex < healthRegen.length - 1)
					healthRegenIndex++;
				else 
					isStartGameHealthRegen = false;
			
			if (healing) {
				if (healthBarAniIndex > healthBarAniState && healthBarAniIndex != 0)
					healthBarAniIndex--;
				if (healthBarAniIndex == 0)
					if (healthRegenIndex < healthRegen.length - 1)
						healthRegenIndex++;
			}
			else {
				if (healthBarAniIndex < healthBarAniState)
					healthBarAniIndex++;
			}
			
			if (isPowerBarSparkling) {
				if (powerBarSparklingIndex < manaSparkling.length - 1)
					powerBarSparklingIndex++;
				else 
					powerBarSparklingIndex = 0;
			}
			
			if (isPowerIncrease) {
				if (powerBarRegenAniIndex < powerBarRegenAniState - 1)
					powerBarRegenAniIndex++;
			}
			

			if(aniIndex>=GetSpriteAmount(playerData, playerAction)-1) {
				if((playerAction == SPECIAL_ATTACK)&&(flame==true)) {
					aniIndex = 0;
					attackChecked = false;
					attacking = false;
					specialAttackEffect = false;
					specialAttack = false;
					enemy.knockBack = false;
				}	
				else if (aniIndex >= GetSpriteAmount(this.playerData,playerAction)) {
						aniIndex = 0;
						hurt = false;
						attackChecked = false;
						attacking = false;
						attack1 = false;
						attack2 = false;
						attack3 = false;
						specialAttackEffect = false;
						specialAttack = false;
						skill = false;
						dash = false;
						healing = false;
						shurikenChecked = false;
						enemy.knockBack = false;
					}
				if(playerAction == DEATH) {
					audioPlayer.playEffect(AudioPlayer.GAME_OVER);
					lose = true;
				}
			}
		}
	}
	private void checkGameOver() {
		if(System.currentTimeMillis()-endtime>4000)
			playing.setGameOver(true);
	}

	public void setSkill() {
		if(!enemy.specialAttackEffect&&(!attacking||playerData==WANDERLUST_SAMURAI)&&!hurt) {
		if(playerData == DEMON_SAMURAI||playerData == WOLF_SAMURAI) {
		if((powerValue >= powerMaxValue/2)&&!flame&&!speedUp) {
			if(playerData==DEMON_SAMURAI) {
				audioPlayer.playSkillDemonSound();
			}
			if(playerData==WOLF_SAMURAI) {
				audioPlayer.playSkillWolfSound();
			}
			skill = true;
			powerGrowSpeed = 100;
			changePower(-powerMaxValue/2);
			setSkillTime = System.currentTimeMillis();		
			
			if(playerData==DEMON_SAMURAI) {
				flame = true;
			}
			
			if(playerData==WOLF_SAMURAI) {
				speedUp = true;
				playerSpeed = playerSpeed*1.6f;
				maxHealth = maxHealth *2;
				currentHealth = currentHealth *2;
			}
		}
	}
		else if(powerValue>=powerMaxValue/5)
			if(playerData==SHOUNEN_SAMURAI&&!skill) {
				audioPlayer.playSkillShounenSound();
				powerGrowSpeed = 100;
				skill = true;
				if(inRight)
					shurikenDirectton[shurikenI] = 1;
				else 
					shurikenDirectton[shurikenI] = -1;
				if(shurikenI<4)
					shurikenI++;
				else 
					shurikenI = 0;
				changePower(-powerMaxValue/5);
			}
		else if(powerValue >= powerMaxValue/3) {
			
			powerGrowSpeed = 100;
			
			if(playerData==SHOGUN_SAMURAI&&!dash) {
				audioPlayer.playSkillShogunSound();
				skill = true;
				dash = true;
				changePower(-powerMaxValue/3);
			}
			
			if(playerData==MASTER_SAMURAI&&!healing) {
				if(currentHealth<maxHealth) {
				audioPlayer.playSkillMasterSound();
				skill = true;
				healing= true;
				changeHealth(20);
				changePower(-powerMaxValue/3);
				}
			}
			if(playerData==WANDERLUST_SAMURAI) {
				if((inLeft&&CanMoveHere(hitbox.x-enemy.hitbox.width-5*Game.SCALE,hitbox.y+hitbox.height-enemy.hitbox.height,enemy.hitbox.width, enemy.hitbox.height, lvlData))
				||(inRight&&CanMoveHere(hitbox.x+hitbox.width+enemy.hitbox.width+5*Game.SCALE,hitbox.y+hitbox.height-enemy.hitbox.height, enemy.hitbox.width, enemy.hitbox.height, lvlData))) {
				audioPlayer.playSkillWanderlustSound();
				skill = true;
				teleport = true;
				changePower(-powerMaxValue/3);
				}
			}
		}
	}
}
	private void checkThrowShuriken() {
		if((playerAction==SKILL)) {
			if(aniIndex==3&&!shurikenChecked) {
			throwShuriken[shurikenI] = true;
			shurikenChecked = true;
			}
		}else {
			for(int i =0;i<5;i++)
		if(shurikenBox[i].x>=Game.GAME_WIDTH||shurikenBox[i].x<=0)
			throwShuriken[i] = false;
			}
		checkShurikenHit();
	}

	private void checkShurikenHit() {
		for(int i=0;i<5;i++)
		if(throwShuriken[i]) {
		if (shurikenBox[i].intersects(enemy.hitbox)&&!enemy.hurt&&!enemy.healing) {
			audioPlayer.playSkillShurikenHitSound();
			enemy.hurt = true;
			enemy.changeHealth(-10);
			enemy.hurt = true;
			throwShuriken[i] = false;
			shurikenEffect = shurikenEffect * 1.2f;
			enemy.playerSpeed = enemy.playerSpeed / 1.2f;
			enemy.jumpSpeed = enemy.jumpSpeed / 1.2f;
			if(enemy.powerGrowSpeed<100)
				enemy.powerGrowSpeed = 100;
			enemy.powerGrowSpeed = enemy.powerGrowSpeed * 1.2f;
			setSkillTime = System.currentTimeMillis();
			}
		}
	}

	private void checkSkill() {
		if((playerAction==SKILL)&&flame) {
			if((Math.abs(hitbox.x-enemy.hitbox.x-enemy.hitbox.width)<108*Game.SCALE||Math.abs(hitbox.x+hitbox.width-enemy.hitbox.x)<108*Game.SCALE)&&(Math.abs(hitbox.y+hitbox.height-enemy.hitbox.y)<108*Game.SCALE||Math.abs(hitbox.y-enemy.hitbox.height-enemy.hitbox.y)<108*Game.SCALE)) {
				if(!enemy.hurt&&!enemy.healing&&(aniIndex>6)) {
					enemy.changeHealth(-5);
					enemy.hurt = true;
					if(enemy.currentHealth<=0)
						audioPlayer.gameOver();
					specialAttackEffect = true;
				} 
			}else
					specialAttackEffect = false;
		}
		if(flame||speedUp)
			if(System.currentTimeMillis() - setSkillTime - playing.extraTime >SKILLTIMES) {
				if(flame)
					flame = false;
				if(speedUp) {
					speedUp = false;
					playerSpeed = playerSpeed /1.6f;
					maxHealth = maxHealth / 2;
					currentHealth = currentHealth /2 + currentHealth % 2;
				}
			}
		if(playerData==SHOUNEN_SAMURAI) {
			if((System.currentTimeMillis() - setSkillTime - playing.extraTime >10000)&&(shurikenEffect!=1)) {
				enemy.playerSpeed = enemy.playerSpeed * shurikenEffect;
				enemy.jumpSpeed = enemy.jumpSpeed * shurikenEffect;
				enemy.powerGrowSpeed = enemy.powerGrowSpeed / shurikenEffect;
				shurikenEffect = 1;
			}
		}
		if(attacking||hurt) {
			if(playerData==SHOUNEN_SAMURAI) {
				if(throwShuriken[shurikenI])
					return;
				audioPlayer.stopEffect(10);
				audioPlayer.stopEffect(11);
			}
			if(playerData==DEMON_SAMURAI) {
				audioPlayer.stopEffect(6);
			}
			if(playerData==SHOGUN_SAMURAI) {
				audioPlayer.stopEffect(8);
			}
			if(playerData==MASTER_SAMURAI) {
				audioPlayer.stopEffect(9);
			}
			
		}
	}

	private void setAnimation() {
		int startAni = playerAction;

		if (moving&&!(enemy.win||win))
			playerAction = RUNNING;
		else
			playerAction = IDLE;
		if(inAir) {
			if(airSpeed <0)
				playerAction = JUMP;
			else {
				playerAction = FALLING;
			}
		}

		if(enemy.specialAttackEffect)
			playerAction = IDLE;
		if (specialAttack)
			playerAction = SPECIAL_ATTACK;
		else
		if(hurt)
			playerAction = HIT;
		else
		if (attack1)
			playerAction = ATTACK_1;
		else
		if (attack2)
			playerAction = ATTACK_2;
		else
		if (attack3)
			playerAction = ATTACK_3;
		else
		if(skill&&playerData!=WOLF_SAMURAI&&playerData!=WANDERLUST_SAMURAI)
			playerAction = SKILL;
		if(death)
			playerAction = DEATH;

		if (startAni != playerAction)
			resetAniTick();
	}

	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}

	private void updatePos() {
		moving = false;
		float xSpedd = 0;
		if(death&&!inAir)
			return;
		if(inLeft) {
			if(playerData == WOLF_SAMURAI) {
				flipX = 0;
				flipW = 1;
			}
			else {
			flipX = width;
			flipW = -1;
			}
		}
		if(inRight) {
			if(playerData == WOLF_SAMURAI) {
				flipX = width;
				flipW = -1;
			}
			else {
			flipX = 0;
			flipW = 1;
			}
		}
		if(knockBack) {
			if(enemy.inLeft)
				xSpedd = -12*Game.SCALE;
			if(enemy.inRight)
				xSpedd = 12*Game.SCALE;
			if(CanMoveHere(hitbox.x+xSpedd, hitbox.y, hitbox.width, hitbox.height, lvlData))
			updateXPos(xSpedd);
			if(!inAir) 
				if(!IsEntityOnFloor(hitbox, lvlData)) {
					inAir = true;		
					}
			
			if(inAir&&!dash) {
				if(CanMoveHere(hitbox.x, hitbox.y+airSpeed, hitbox.width, hitbox.height, lvlData)||CheckOnObject(hitbox, lvlData, airSpeed)) {
					hitbox.y += airSpeed;
					airSpeed += gravity;
					updateXPos(xSpedd);
				} else {
					hitbox.y = GetEntityYPosUnderRoofOrAboveFlorr(hitbox, airSpeed, playerData);
					if(airSpeed > 0)
						resetInAir();
					else 
						airSpeed = fallSpeedAfterCollision;
					updateXPos(xSpedd);
				}
			}
			knockBack = false;
		}
		if(teleport) {
			if(inRight) {
				enemy.hitbox.x = hitbox.x + hitbox.width + 5*Game.SCALE;
			}
			if(inLeft) {
				enemy.hitbox.x = hitbox.x - enemy.hitbox.width - 5*Game.SCALE;
			}
			enemy.hitbox.y = hitbox.y+hitbox.height-enemy.hitbox.height;
			if(!enemy.inAir) 
				if(!IsEntityOnFloor(enemy.hitbox, lvlData)) {
					enemy.inAir = true;		
					}
			
			if(enemy.inAir&&!enemy.dash) {
				if(CanMoveHere(enemy.hitbox.x, enemy.hitbox.y+enemy.airSpeed, enemy.hitbox.width, enemy.hitbox.height, lvlData)||CheckOnObject(enemy.hitbox, lvlData, enemy.airSpeed)) {
					enemy.hitbox.y += enemy.airSpeed;
					enemy.airSpeed += enemy.gravity;
				} else {
					enemy.hitbox.y = GetEntityYPosUnderRoofOrAboveFlorr(enemy.hitbox, enemy.airSpeed, enemy.playerData);
					if(enemy.airSpeed > 0)
						resetInAir();
					else 
						enemy.airSpeed = fallSpeedAfterCollision;
				}
			}
			teleport = false;
		}
		if (left&&!right&&!enemy.specialAttackEffect&&!(enemy.win||win)) {
			xSpedd -= playerSpeed;
			inLeft = true;
			inRight = false;
		}
		
		if (right&&!left&&!enemy.specialAttackEffect&&!(enemy.win||win)) {
			xSpedd += playerSpeed;
			inRight = true;
			inLeft = false;
		}
		
		if(playerData==SHOUNEN_SAMURAI) {
			for(int i = 0;i<5;i++) 
			if(!throwShuriken[i]){
			if(inRight)
				shurikenBox[i].x = hitbox.x+hitbox.width+20*Game.SCALE;
			if(inLeft)
				shurikenBox[i].x = hitbox.x-20*Game.SCALE;

			shurikenBox[i].y = hitbox.y+hitbox.width/2+10*Game.SCALE;
			}
		}
		
		if(dash) {
			if(right)
				xSpedd -= playerSpeed;
			if(left)
				xSpedd += playerSpeed;
			if((0<aniIndex)&&(aniIndex<5)&&!attacking&&!hurt) {
			if(inRight)
				xSpedd += 3*playerSpeed;
			if(inLeft)
				xSpedd -= 3*playerSpeed;
			}
			else {
				if(!inAir) 
					if(!IsEntityOnFloor(hitbox, lvlData)) {
						inAir = true;		
						}
				if(inAir) {
					if(CanMoveHere(hitbox.x, hitbox.y+airSpeed, hitbox.width, hitbox.height, lvlData)||CheckOnObject(hitbox, lvlData, airSpeed)) {
						hitbox.y += airSpeed;
						airSpeed += gravity;
					} else {
						hitbox.y = GetEntityYPosUnderRoofOrAboveFlorr(hitbox, airSpeed, playerData);
						if(airSpeed > 0)
							resetInAir();
						else 
							airSpeed = fallSpeedAfterCollision;
					}
				} 
			}
			updateXPos(xSpedd);
		}
		
		if(playerAction==SPECIAL_ATTACK&&(playerData==WOLF_SAMURAI||playerData==SHOGUN_SAMURAI)) {
				if(right)
					xSpedd -= playerSpeed;
				if(left)
					xSpedd += playerSpeed;
			if(aniIndex>=6&&(playerData == WOLF_SAMURAI&&aniIndex<10||playerData == SHOGUN_SAMURAI&&aniIndex<9)) {
			if(inRight)
				xSpedd += 2 *playerSpeed;
			else
				xSpedd -= 2 *playerSpeed;
			}else if((playerData == WOLF_SAMURAI&&aniIndex>=10||playerData == SHOGUN_SAMURAI&&aniIndex>=9)) {
			if(!inAir) 
				if(!IsEntityOnFloor(hitbox, lvlData)) {
					inAir = true;		
					}
			if(inAir) {
				if(CanMoveHere(hitbox.x, hitbox.y+airSpeed, hitbox.width, hitbox.height, lvlData)||CheckOnObject(hitbox, lvlData, airSpeed)) {
					hitbox.y += airSpeed;
					airSpeed += gravity;
					updateXPos(xSpedd);
				} else {
					hitbox.y = GetEntityYPosUnderRoofOrAboveFlorr(hitbox, airSpeed, playerData);
					if(airSpeed > 0)
						resetInAir();
					else 
						airSpeed = fallSpeedAfterCollision;
					updateXPos(xSpedd);
				}
			} 
		}
			updateXPos(xSpedd);
	}		

		if(jump&&!enemy.specialAttackEffect&&!(win||enemy.win)) 
			jump();
		
		if(doubleJump&&!enemy.specialAttackEffect&&!(win||enemy.win))
			doubleJump();
		
		if(!inAir&&!dash && ((attacking)||skill)
			&&(!((playerAction==SPECIAL_ATTACK&&4<aniIndex&&aniIndex<=8&&!flame||playerAction==SPECIAL_ATTACK&&4<=aniIndex&&aniIndex<8&&flame)&&(playerData == DEMON_SAMURAI)||
				playerAction==SPECIAL_ATTACK&&1<aniIndex&&aniIndex<5&&playerData==MASTER_SAMURAI)))
			return;
		
		if(left&&right&&!(inAir))
			return;

		if(!left&&!right&&!(inAir||dash))
			return;
		
		if(!inAir) 
			if(!IsEntityOnFloor(hitbox, lvlData)) {
				inAir = true;		
				}
		
		if(inAir&&!dash) {
			if(CanMoveHere(hitbox.x, hitbox.y+airSpeed, hitbox.width, hitbox.height, lvlData)||CheckOnObject(hitbox, lvlData, airSpeed)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpedd);
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFlorr(hitbox, airSpeed, playerData);
				if(airSpeed > 0)
					resetInAir();
				else 
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpedd);
			}
		} else if(!dash)
			updateXPos(xSpedd);
		moving = true;

	}

	private void doubleJump() {
		if(!canDoubleJump)
			return;
		airSpeed = jumpSpeed;
		canDoubleJump = false;
	}


	private void jump() {
		if(inAir)
			return;
		canDoubleJump = true;
		inAir = true;
		airSpeed = jumpSpeed;
	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

	private void updateXPos(float xSpedd) {
		if(CanMoveHere(hitbox.x+xSpedd, hitbox.y, hitbox.width, hitbox.height, lvlData)||CheckNextToObject(hitbox, lvlData, xSpedd)) {
		hitbox.x += xSpedd;
	    } else {
		     hitbox.x = GetEntityXPosNextToWall(hitbox, xSpedd);
	    }
    }

	private void loadAnimations() {
	
			BufferedImage temp = LoadSave.GetSpriteAtlas(playerData.img);

			animations = new BufferedImage[playerData.rowNum][playerData.columnNum];
			for (int j = 0; j < animations.length; j++)
				for (int i = 0; i < animations[j].length; i++)
					animations[j][i] = temp.getSubimage(i * playerData.imgWidth, j * playerData.imgHeight, playerData.imgWidth, playerData.imgHeight);

			temp = LoadSave.GetSpriteAtlas(LoadSave.HEALTH_BAR);
			healthBarAni = new BufferedImage[50];
			for (int i = 0; i < healthBarAni.length; i++) 
				healthBarAni[i] = temp.getSubimage(0, i * 16, 64, 16);
			
			temp = LoadSave.GetSpriteAtlas(LoadSave.HEALTH_REGEN);
			healthRegen = new BufferedImage[13];
			for (int i = 0; i < healthRegen.length; i++) 
				healthRegen[i] = temp.getSubimage(0, i * 16, 64, 16);
			
			temp = LoadSave.GetSpriteAtlas(LoadSave.MANA_REGEN);
			manaRegen = new BufferedImage[40];
			for (int i = 0; i < manaRegen.length; i++)
				manaRegen[i] = temp.getSubimage(0, i * 16, 64, 16);
			
			temp = LoadSave.GetSpriteAtlas(LoadSave.MANA_FADING);
			manaFading = new BufferedImage[40];
			for (int i = 0; i < manaFading.length; i++)
				manaFading[i] = temp.getSubimage(0, i * 16, 64, 16);
			
			temp = LoadSave.GetSpriteAtlas(LoadSave.MANA_SPARKLING);
			manaSparkling = new BufferedImage[10];
			for (int i = 0; i < manaSparkling.length; i++)
				manaSparkling[i] = temp.getSubimage(0, i * 18, 64, 18);
	
	}
	
	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if(!IsEntityOnFloor(hitbox, lvlData))
			inAir = true ;
	}

	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}

	public void setAttack1(boolean attack1) {
		if(!enemy.specialAttackEffect&&!attacking&&(!enemy.attacking||enemy.attackChecked||!enemy.attackBox().intersects(hitbox))) {
		this.attack1 = attack1;
		audioPlayer.playAttackSound();
		if(this.attack1)
			attacking = true;
		}
	}
	public void setAttack2(boolean attack2) {
		if(!enemy.specialAttackEffect&&!attacking&&(!enemy.attacking||enemy.attackChecked||!enemy.attackBox().intersects(hitbox))) {
		this.attack2 = attack2;
		audioPlayer.playAttackSound();
		if(this.attack2)
			attacking = true;
		}
	}
	public void setAttack3(boolean attack3) {
		if(!enemy.specialAttackEffect&&!attacking&&(!enemy.attacking||enemy.attackChecked||!enemy.attackBox().intersects(hitbox))) {
		this.attack3 = attack3;
		audioPlayer.playAttackSound();
		if(this.attack3)
			attacking = true;
		}
	}
	public void setSpecialAttack() {
		if((powerValue == powerMaxValue||(flame&&(currentHealth>30)||(speedUp&&currentHealth>20*2)))&&!hurt) {
			if(!enemy.specialAttackEffect&&!attacking) {
				if(enemy.attacking)
					enemy.attacking = false;
				if((Math.abs(hitbox.x-enemy.hitbox.x-enemy.hitbox.width)<108*Game.SCALE||Math.abs(hitbox.x+hitbox.width-enemy.hitbox.x)<108*Game.SCALE)&&(Math.abs(hitbox.y+hitbox.height-enemy.hitbox.y)<108*Game.SCALE||Math.abs(hitbox.y-enemy.hitbox.height-enemy.hitbox.y)<108*Game.SCALE)) {
				specialAttackEffect = true;
				}
			powerGrowSpeed = 100;
			attacking = true;
			specialAttack = true;
			if(playerData!=SHOUNEN_SAMURAI&&playerData!=DEMON_SAMURAI)
				audioPlayer.playSpecialAttackSound();
			if(playerData==SHOUNEN_SAMURAI)
				audioPlayer.playSpecialAttacksSound();
			if(playerData==DEMON_SAMURAI)
				audioPlayer.playSpecialAttackdSound();
			if(flame&&(powerValue<powerMaxValue)) {
				changeHealth(-30);
			}
			else
			if(speedUp&&(powerValue<powerMaxValue))
				changeHealth(-20*2);
			else
			changePower(-powerMaxValue/2);
			}
		}
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		if(!(win||enemy.win)) {
		this.left = left;
		if(left&&!right) {
			inLeft = true;
			inRight = false;
		}
	  }
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		if(!(win||enemy.win)) {
		this.right = right;
		if(right&&!left) {
			inRight = true;
			inLeft = false;
		}
	  }
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	public void setDoubleJump(boolean doubleJump) {
		this.doubleJump = doubleJump;
	}

	public void setJump(boolean jump) {
		this.jump = jump;		
	}
	public boolean checkInAir() {
		return inAir;
	}
	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking = false;
		specialAttack = false;
		attack1 = false;
		attack2 = false;
		attack3 = false;
		moving = false;
		hurt = false;
		death = false;
		flame = false;
		speedUp = false;
		dash = false;
		healing = false;
		teleport = false;
		specialAttackEffect = false;
		knockBack = false;
		if(playerData==SHOUNEN_SAMURAI)
		for(int i=0;i<5;i++)
		throwShuriken[i]= false;
		shurikenI = 0;
		shurikenChecked = false;
		shurikenEffect = 1;
		inLeft = false;
		inRight = false;
		setSkillTime = 0;
		playerSpeed = playerData.playerSpeed;
		jumpSpeed = -2.8125f * Game.SCALE;
		playerAction = IDLE;
		
		currentHealth = maxHealth;
		healthRegenIndex = 0;
		isStartGameHealthRegen = true;
		healthBarAniIndex = 0;
		healthBarAniState = 0;
		powerValue = 0;
		previousPowerValue = 0;
		powerBarRegenAniIndex = 0;
		powerBarFadingAniIndex = 0;
		powerBarSparklingIndex = 0;
		isPowerIncrease = false;
		isPowerBarFading = false;
		isPowerBarSparkling = false;
		powerGrowSpeed = 0.5f;
		
		attackChecked = false;
		win = false;
		lose = false;
		hitbox.x = x;
		hitbox.y = y;
		attackBox1.x=x;
		attackBox1.y = y;
		attackBox2.x=x;
		attackBox2.y = y;
		attackBox3.x=x;
		attackBox3.y = y;
		specialAttackBox.x=x;
		specialAttackBox.y=y;

		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
	}
	public int getHitboxX() {
		return (int) hitbox.x;
	}
	
	public int getHitboxY() {
		return (int) hitbox.y;
	}
	
	public void setHealthBarXStart(int x) {
		this.healthBarXStart = x;
	}
	
	public void setPowerBarXStart(int x) {
		this.powerBarXStart = x;
	}

	public Playing getPlaying() {
		return this.playing;
	}
	
}
