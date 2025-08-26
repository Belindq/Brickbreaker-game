package sun.audio;
public class AudioPlayer {
    public static final AudioPlayer player = new AudioPlayer();
    public void start(AudioStream stream) {
        try (java.io.ByteArrayInputStream bais =
                 new java.io.ByteArrayInputStream(stream.getData())) {
            javax.sound.sampled.AudioInputStream in =
                javax.sound.sampled.AudioSystem.getAudioInputStream(bais);
            javax.sound.sampled.Clip clip = javax.sound.sampled.AudioSystem.getClip();
            clip.open(in);
            clip.start();
        } catch (Exception ignored) { }
    }
}