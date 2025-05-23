package audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import main.Game;


public class AudioPlayer {

	public static int MENU_1 = 0;
	public static int BATTLE1 = 1;
	public static int BATTLE2 = 2;
	public static int BATTLE3 = 3;
	public static int BATTLE4 = 4;
	public static int BATTLE5 = 5;
	
	public static int ATTACK = 0;
	public static int SPECIAL_ATTACK = 1;
	public static int SPECIAL_ATTACKS = 2;
	public static int SPECIAL_ATTACKD = 3;
	public static int JUMP = 3;
	public static int GAME_OVER = 14;
	
	private Clip[] songs, effects;
	private float volume = 0.8f;
	private int currentSongId;
	private boolean songMute = false , effectMute = false;
	private Game game;
	
	public AudioPlayer(Game game) {
		this.game = game;
		loadSongs();
		loadEffects();
	}
	
	private void loadSongs() {
		String[] names = {"menu","battle1","battle2","battle3","battle4","battle5"};
		songs = new Clip[names.length];
		for (int i = 0; i < songs.length; i++)
			songs[i] = getClip(names[i]);
		updateSongVolume();
	}
	
	private void loadEffects() {
		String[] effectNames = {"attack1", "attack2", "attack3", "special_attack","special_attacks","special_attackd","skill_demon","skill_wolf","skill_shogun","skill_master","skill_shounen","skill_shounen","skill_shounenhit","skill_wanderlust","game_over"};
		effects = new Clip[effectNames.length];
		for (int i = 0; i < effectNames.length; i++)
			effects[i] = getClip(effectNames[i]);
		updateEffectsVolume();
	}
	
	private Clip getClip(String name) {
		URL url = getClass().getResource("/audio/" + name + ".wav");
		AudioInputStream audio;
		
		try {
			audio = AudioSystem.getAudioInputStream(url);
			Clip c = AudioSystem.getClip();
			c.open(audio);
			return c;
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public void setVolume(float volume) {
		this.volume = volume;
		updateSongVolume();
		updateEffectsVolume();
	}
	
	public void stopSong() {
		if (songs[currentSongId].isActive())
			songs[currentSongId].stop();
	}
	
	public void setBattleSong() {
		switch(game.getBeforePlaying().index) {
		case 0:
			playSong(BATTLE1);
			break;
		case 1:
			playSong(BATTLE2);
			break;
		case 2:
			playSong(BATTLE3);
			break;
		case 3:
			playSong(BATTLE4);
			break;
		case 4:
			playSong(BATTLE5);
			break;	
		}
	}
	
	public void gameOver() {
		stopSong();
		playEffect(GAME_OVER);
	}
	
	public void playAttackSound() {
		int start = (int)((Math.random()*100)%3);
		playEffect(start);
	}
	
	public void playSpecialAttackSound() {
		if(effects[3].isActive())
			effects[3].stop();
		int start = 3;
		playEffect(start);
	}
	public void playSpecialAttacksSound() {
		int start = 4;
		playEffect(start);
	}
	public void playSpecialAttackdSound() {
		if(effects[5].isActive())
			effects[5].stop();
		int start = 5;
		playEffect(start);
	}
	public void playSkillDemonSound() {
		if(effects[6].isActive())
			effects[6].stop();
		int start = 6;
		playEffect(start);
	}
	public void playSkillWolfSound() {
		int start = 7;
		playEffect(start);
	}
	public void playSkillShogunSound() {
		int start = 8;
		playEffect(start);
	}
	public void playSkillMasterSound() {
		int start = 9;
		playEffect(start);
	}
	public void playSkillShounenSound() {
		if(effects[10].isActive()) {
		effects[10].stop();
		playEffect(11);
		}else {
		if(effects[11].isActive())	
		effects[11].stop();	
		playEffect(10);
		}
	}
	public void playSkillShurikenHitSound() {
		if(effects[10].isActive())
		effects[10].stop();
		if(effects[11].isActive())
			effects[11].stop();
		playEffect(12);
	}
	public void playSkillWanderlustSound() {
		int start = 13;
		playEffect(start);
	}
	
	public void playEffect(int effect) {
		if(effect==1) {
			effects[effect].setMicrosecondPosition(0);
			effects[effect].start();
		} else {
		effects[effect].setMicrosecondPosition(0);
		effects[effect].start();
		}
	}
	
	public void stopEffect(int effect) {
		if(effects[effect].isActive())
			effects[effect].stop();
	}
	
	public void playSong(int song) {
		stopSong();
		currentSongId = song;
		updateSongVolume();
		songs[currentSongId].setMicrosecondPosition(0);
		songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void toggleSongMute() {
		this.songMute = !songMute;
		for (Clip c : songs) {
			BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
			booleanControl.setValue(songMute);	
		}
	}
	
	public void toggleEffectMute() {
		this.effectMute = !effectMute;
		for (Clip c : effects) {
			BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
			booleanControl.setValue(effectMute);	
		}
	}
	
	private void updateSongVolume() {
		
		FloatControl gainControl = (FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();
		gainControl.setValue(gain);
		
	}
	
		private void updateEffectsVolume() {
			for (Clip c : effects) {
				FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
				float range = gainControl.getMaximum() - gainControl.getMinimum();
				float gain = (range * volume) + gainControl.getMinimum();
				gainControl.setValue(gain);
			}
			
		}
		
		private void updateAllEffectsVolume(float volume){
			game.getPlaying().getAudioPlayer1().volume = volume; 
			game.getPlaying().getAudioPlayer2().volume = volume; 
			game.getPlaying().getAudioPlayer1().updateEffectsVolume();
			game.getPlaying().getAudioPlayer2().updateEffectsVolume();
		}
		
		public void toggleAllEffectMute() {
			game.getPlaying().getAudioPlayer1().toggleEffectMute();
			game.getPlaying().getAudioPlayer2().toggleEffectMute();
		}
}
