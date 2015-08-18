package Entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Animation implements Serializable {
	
	private transient BufferedImage[] frames;
	private int currentFrame;
	
	private long startTime;
	private long delay; // how long between each frame
	
	private boolean playedOnce; // has animation played already (such as attack animation)
	
	public void Animation() {
		playedOnce = false;
	}
	
	public void setFrames(BufferedImage[] frames){
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}
	
	public void setDelay(long d) { delay = d; }
	public void setFrame(int i) { currentFrame = i; }
	public void setAnimation(Animation a) {
		this.frames = a.frames; 
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}
	
	public void update() {
		if(frames.length == 1) return;
		if(delay == -1) return;
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if(elapsed > delay){
			currentFrame++;
			startTime = System.nanoTime();
		}
		if(currentFrame == frames.length){
			currentFrame = 0;
			playedOnce = true;
		}
	}
	
	public int getFrame() { return currentFrame; }
	public BufferedImage getImage() { return frames[currentFrame]; }
	public boolean hasPlayedOnce() { return playedOnce; }
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();	// Writes the rest of the data to the byte[] array
		out.writeInt(frames.length); 	// The number of frames 
		for(BufferedImage eachImage : frames) {
			ImageIO.write(eachImage, "png", out);
		}
	}
	private void readObject(ObjectInputStream in)  throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		int x = in.readInt();
		frames = new BufferedImage[x];
		for(int j = 0; j < x; j++){
			frames[j] = ImageIO.read(in);
		}
	}

}









