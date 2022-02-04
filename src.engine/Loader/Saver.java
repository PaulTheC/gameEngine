package Loader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Saver {
	
	private final static int MAX_PIXEL_COLOUR = 256 * 256 * 256;
	
	
	public static void saveHeightMap(float[][] heightMap, int MAX_HEIGHT, String fileName, int xArea, int yArea) {
		BufferedImage heightMapIamge = null;
		try {
			heightMapIamge = ImageIO.read(new File("res/"+ fileName + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int x = 0; x < heightMapIamge.getWidth(); x++) {
			for(int y = 0; y < heightMapIamge.getHeight(); y ++) {
				
				float height = heightMap[x][y] / MAX_HEIGHT;
				height *= MAX_PIXEL_COLOUR / 2f;
				height -= MAX_PIXEL_COLOUR / 2f;
				
				heightMapIamge.setRGB(x, y, (int) height);
				
			}
		}
		
		try {
			ImageIO.write(heightMapIamge, "PNG", new File("res/map/area["+xArea+"]["+yArea+"].png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
