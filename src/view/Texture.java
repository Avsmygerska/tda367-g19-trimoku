package view;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import com.sun.opengl.util.BufferUtil;

public class Texture {
	private int width = 0,height = 0;
	private ByteBuffer pixels;

	public boolean loadTexture(String filename) {		
		try {
			BufferedImage img = ImageIO.read(new FileInputStream(filename));
			width = img.getWidth();
			height = img.getHeight();
			int[] packedPixels = new int[width * height];

			// Retrieve the pixels from the image into packedPixels
			PixelGrabber pixelgrabber = new PixelGrabber(img, 0, 0, width, height, packedPixels, 0, width);
			pixelgrabber.grabPixels();
			
			// Put the pixels into the ByteBuffer we have prepared.
			pixels = BufferUtil.newByteBuffer(packedPixels.length * 3);			
			for (int row = height - 1; row >= 0; row--) {
				for (int col = 0; col < width; col++) {
					int packedPixel = packedPixels[row *width + col];
					pixels.put((byte) ((packedPixel >> 16) & 0xFF));
					pixels.put((byte) ((packedPixel >> 8)  & 0xFF));
					pixels.put((byte) ((packedPixel >> 0)  & 0xFF));
				}
			}			
			pixels.flip(); // Ready the pixels for reading.
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			return false;
		} catch (Exception e) {
			System.out.println("Something broke.");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public int getWidth()         { return width; }		
	public int getHeight()        { return height; }
	public ByteBuffer getPixels() { return pixels; }
}
