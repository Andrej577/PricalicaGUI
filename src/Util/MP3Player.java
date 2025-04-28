package Util;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import POCO.WavFilePOCO;


public class MP3Player {
	
	private Clip clip;
	private AudioInputStream audioStream;
    private long pausedPosition = 0; 
    public boolean pokrenutoSwitch = false;
    WavFilePOCO wavPoco = new WavFilePOCO();
    
    private String FormatMsSMh()
    {
    	long microseconds = clip.getMicrosecondLength();
    	long totalSeconds = microseconds / 1_000_000;

    	long hours = totalSeconds / 3600;
    	long minutes = (totalSeconds % 3600) / 60;
    	long seconds = totalSeconds % 60;
    	
    	return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    
    public WavFilePOCO getFileInfo(File fileName)
    {
    	ucitaj(fileName);
    	
    	String ime = fileName.getName();
    	if (ime.endsWith(".wav")) {
    	    ime = ime.substring(0, ime.length() - 4);
    	}
    	
    	wavPoco.Ime = ime;
    	wavPoco.Duzina = FormatMsSMh();
    	
    	return wavPoco;
    }
    
    public void ucitaj(File fileName) {
        try {
            audioStream = AudioSystem.getAudioInputStream(fileName);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void ucitajAsync(File fileName) {
        new Thread(() -> 
        {
            try {
                audioStream = AudioSystem.getAudioInputStream(fileName);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void pokreni() {
        new Thread(() -> 
        {
            if (clip != null) {
                clip.setMicrosecondPosition(pausedPosition);
                clip.start();
                pokrenutoSwitch = true;
            }
        }).start();  

    }

    public void pauziraj() {
        if (clip != null && clip.isRunning()) {
            pausedPosition = clip.getMicrosecondPosition();
            clip.stop();
            pokrenutoSwitch = false;
        }
    }

    public void zaustavi() {
        if (clip != null) {
            clip.stop();
            pausedPosition = 0;
            clip.setMicrosecondPosition(0);
            pokrenutoSwitch = false;
        }
    }

}
