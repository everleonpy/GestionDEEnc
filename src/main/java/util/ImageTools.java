package util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;


/**
* ClassLoader classLoader = getClass().getClassLoader();
* InputStream inputStream = classLoader.getResourceAsStream("nombre_de_la_imagen.png");
* @author eleon
*
*/
public class ImageTools 
{
	private ClassLoader classLoader;
	private InputStream inputStream;
	
	
	public ImageTools()
	{
		classLoader = getClass().getClassLoader();
		
	}
	
	
	/**
	* REcupera un imagen desde la carpeta resources
	* @param fileName
	* @return
	* @throws IOException 
	*/
	public BufferedImage getImageFromFile(String fileName) throws IOException 
	{
		inputStream = classLoader.getResourceAsStream(fileName);
		BufferedImage imagen = ImageIO.read(inputStream);
		return imagen;
	}
	
}
