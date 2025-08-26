package sun.audio;
public class AudioStream {
    final byte[] data;
    public AudioStream(java.io.InputStream in) throws java.io.IOException {
        this.data = in.readAllBytes();
    }
    byte[] getData() { return data; }
}