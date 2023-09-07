package util;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class FondoSP  extends JPanel
{

	private static final long serialVersionUID = -8570752867394377160L;
	private Image imagen;
	private ImageTools imgTools;
	
	public FondoSP() 
	{
		imgTools = new ImageTools();
	}
	
	public void paint(Graphics g) 
	{
		try {
			imagen = new ImageIcon(imgTools.getImageFromFile("softpoint.png")).getImage();
			g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
			setOpaque(false);
			super.paint(g);
			
			/*if( imagen != null ) 
			{
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
				g2d.drawImage(imagen, 0, 0, null);
				g2d.dispose();
			}
			*/
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
