package com.borasoft.radio.lab2018.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.junit.Test;

public class DataLineTest {
	@Test public void testTargetDataLine() throws LineUnavailableException, InterruptedException {
		AudioFormat format=new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,44100,16,2,4,44100,false);
		DataLine.Info info=new DataLine.Info(TargetDataLine.class,format);
		final TargetDataLine line=(TargetDataLine)AudioSystem.getLine(info);
		Thread thread=new Thread() {
			@Override public void run() {
				AudioInputStream stream=new AudioInputStream(line);
				File out=new File("sound.wav");
				try {
					AudioSystem.write(stream,AudioFileFormat.Type.WAVE,out);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		line.open(format);
		line.start();
		System.out.println("Recording started.");
		thread.start();
		Thread.sleep(5000);
		System.out.println("Recording stopped.");
		line.stop();
		line.close();
	}
}
