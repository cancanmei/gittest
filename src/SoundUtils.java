

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * http://www.diyjava.com
 * 使用请保留http://www.diyjava.com字样
 * @author colin
 *
 */
public class SoundUtils {
 private static final int BUFFER_SIZE = 64000;

 /**
  * Constructor for SoundUtils
  */
 private SoundUtils() {
 }

 /**
  * Plaw wav file
  * 
  * @param musicFile
  * @throws IOException
  */
 public static void playMusic(File musicFile) throws IOException {
  byte[] audioData = new byte[BUFFER_SIZE];
  AudioInputStream ais = null;
  SourceDataLine line = null;
  AudioFormat baseFormat = null;
  try {
   ais = AudioSystem.getAudioInputStream(musicFile);
  } catch (UnsupportedAudioFileException ex) {
   throw new RuntimeException("can't play wav file ", ex);
  }
  if (ais != null) {
   baseFormat = ais.getFormat();
   line = getLine(baseFormat);
   if (line == null) {
    AudioFormat decodedFormat = new AudioFormat(
      AudioFormat.Encoding.PCM_SIGNED, baseFormat
        .getSampleRate(), 16, baseFormat.getChannels(),
      baseFormat.getChannels() * 2, baseFormat
        .getSampleRate(), false);
    ais = AudioSystem.getAudioInputStream(decodedFormat, ais);
    line = getLine(decodedFormat);
   }
  }
  if (line == null) {
   //can't play the wav file
   return;
  }
  line.start();
  int inBytes = 0;
  while ((inBytes != -1)) {
   inBytes = ais.read(audioData, 0, BUFFER_SIZE);
   if (inBytes >= 0) {
    line.write(audioData, 0, inBytes);
   }
  }
  line.drain();
  line.stop();
  line.close();
 }

 private static SourceDataLine getLine(AudioFormat audioFormat) {
  SourceDataLine res = null;
  DataLine.Info info = new DataLine.Info(SourceDataLine.class,
    audioFormat);
  try {
   res = (SourceDataLine) AudioSystem.getLine(info);
   res.open(audioFormat);
  } catch (LineUnavailableException ex) {
   throw new RuntimeException("can't get source data line", ex);
  }
  return res;
 }

 public static void playWav(File wavFile) throws MalformedURLException {
  AudioClip audioClip = Applet.newAudioClip(wavFile.toURL());
  audioClip.play();
 }

 public static void main(String[] args) {
  try {
   SoundUtils.playMusic(new File(
     "E:\\msg.wav"));
  } catch (IOException e) {
   e.printStackTrace();
  }
 }

}