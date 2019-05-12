package wtf.sheashaa.mario.gfx;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.paint.Color;

public class SpriteSheet {

	private BufferedImage sheet;
	
	public int size;
	public int edge = 1;
	
	
	public SpriteSheet(String path, int size) {
		try {
//			sheet = ImageIO.read(getClass().getResource(path));
			BufferedImage imgBuf = ImageIO.read(getClass().getResource(path));
			sheet = new BufferedImage(imgBuf.getWidth(), imgBuf.getHeight(), BufferedImage.TYPE_INT_ARGB);
			sheet.getGraphics().drawImage(imgBuf, 0, 0, null);
			
			this.size = size;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SpriteSheet(String path, int size, int edge) {
		try {
			sheet = ImageIO.read(getClass().getResource(path));
			this.size = size;
			this.edge = edge;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getSprite(int x, int y) {
//		return sheet.getSubimage(x * 32 - 32, y * 32 - 32, 32, 32);
//		return sheet.getSubimage(edge * x + x * size - size, edge * y + y * size - size, size, size);
		BufferedImage imgBuf = sheet.getSubimage(edge * x + x * size - size, edge * y + y * size - size, size, size);
//		System.out.println(imgBuf.getType());
		int[] rgb = imgBuf.getRGB(0, 0, imgBuf.getWidth(), imgBuf.getHeight(), null, 0, imgBuf.getWidth());
		for (int i = 0; i < rgb.length; i++) if (rgb[i] == 0xff93bbec) rgb[i] = 0x00ffffff;
		imgBuf.setRGB(0, 0, imgBuf.getWidth(), imgBuf.getHeight(), rgb, 0, imgBuf.getWidth());
		return imgBuf;
	}
}
