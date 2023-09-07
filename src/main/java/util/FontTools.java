package util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class FontTools 
{
	/**
	* 
	* @param pFont
	* @return
	* @throws FontFormatException
	* @throws IOException
	*/
	public Font getFontFromFile(String pFont, Float size) throws FontFormatException, IOException 
	{
		
		  InputStream is = getClass().getResourceAsStream(pFont);
          Font font = Font.createFont(Font.TRUETYPE_FONT, is);
          font.deriveFont(size);

         return font;

	}

}
