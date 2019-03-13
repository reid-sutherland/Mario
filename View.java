import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
//import java.io.IOException;
import java.io.File;
//import javax.swing.JButton;
import java.awt.Color;

class View extends JPanel
{
	Model model;	
	BufferedImage background;
	
	
	View(Controller c, Model m)
	{
		//Sets model
		model = m;	
		
		//Allows controller and view to reference each other
		c.setView(this);
		
		//Load background image
		background = new BufferedImage(1920, 2600, BufferedImage.TYPE_INT_ARGB);
		background = loadImage("images/scrollingBackground.png");

		

	}
	
	public BufferedImage loadImage(String imageName) 
	{
		BufferedImage picture = null;
		try
		{
			picture =
				ImageIO.read(new File(imageName));
		} catch(Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		} 
		return picture;		
	}
	
	
	public void paintComponent(Graphics g)
	{
		//Draw background
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		//Adds enough images for infinite scroll
		//TO-DO add to sprites class
		g.drawImage(background, model.backgroundPos % (background.getWidth()), 0, null);
		g.drawImage(background, background.getWidth() + model.backgroundPos % (background.getWidth()), 0, null);
		g.drawImage(background, 2 * background.getWidth() + model.backgroundPos % (background.getWidth()), 0, null);
		g.drawImage(background, 0 - background.getWidth() + model.backgroundPos % (background.getWidth()), 0, null);
		
		//Draw ground
		//TO-DO add to sprites class
		/*g.setColor(Color.gray);
		g.drawLine(0, model.groundHeight, 2000, model.groundHeight);
		g.setColor(Color.black);
		g.fillRect(0, model.groundHeight, model.screenLength, model.screenHeight - model.groundHeight);*/

		//Draw all of the sprites
		for(int i = 0; i < model.sprites.size(); i++)
		{
			Sprite s = model.sprites.get(i);
			s.draw(g, model.cameraPos);
		}
		
	}
}
