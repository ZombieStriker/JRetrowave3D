package me.zombie_striker.jretrowave3d.v1.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;

public class AudioPlayer {

	public static synchronized Sound playSound(String path) {
		return playSound(path, false);
	}

	public static synchronized Sound playLoop(String path) {
		return playSound(path, true);
	}
	public static synchronized Sound playSound(InputStream is) {
		return playSound(is, false);
	}

	public static synchronized Sound playLoop(InputStream is) {
		return playSound(is, true);
	}


	public static synchronized Sound playSound(String path, boolean loop) {
		Sound sound = new Sound(AudioPlayer.class.getResourceAsStream(path),loop);
		new Thread(sound).start();
		return sound;
	}
	public static synchronized Sound playSound(InputStream is, boolean loop) {
		Sound sound = new Sound(is,loop);
		new Thread(sound).start();
		return sound;
	}

	public static class Sound implements Runnable {

		public InputStream inputstream;
		public boolean loop;
		private Clip clip;

		public Sound(InputStream is, boolean loop) {
			this.inputstream = is;
			this.loop = loop;
		}

		// The wrapper thread is unnecessary, unless it blocks on the
		// Clip finishing; see comments.
		public void run() {
			try {
				clip = AudioSystem.getClip();
				AudioInputStream inputStream = AudioSystem.getAudioInputStream(inputstream);
				clip.open(inputStream);
				clip.start();
				if (loop) {
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}

		public void stop() {
			clip.stop();
		}
	}
}
