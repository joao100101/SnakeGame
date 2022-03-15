package com.gmail.joaovictormundel;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.FloatControl.Type;

public class Utils {

	public static void playSound(Sons s) {
		try {
			File wavFile = null;
			if (s == Sons.COIN) {
				wavFile = new File("sons/coin2.wav");
			} else if (s == Sons.GAME_OVER) {
				wavFile = new File("sons/gover.wav");
			}
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(wavFile));
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-10.0f);
			clip.start();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
