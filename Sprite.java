import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
abstract class Sprite 
{
	int x;
	int y;
	int w;
	int h;
	
	//Determines whether or not the sprite should collide with Mario
	boolean collision;
	
	//Adds a reference to the model for all sprite
	//Should be declared in each constructor that needs it
	Model model;
		
	//If false, delete the sprite
	abstract boolean update();
	
	abstract void draw(Graphics g, int cameraPos);
	
	abstract boolean isMario();
	
	abstract boolean isCoinBlock();
	
	abstract boolean isCoin();
	
	abstract Json marshal();
	
	//Detects collision between two Sprites
	//Returns true if there is collision
	static boolean detectCollision(Sprite s1, Sprite s2)
	{
		int s1_left_side = s1.x;
		int s1_right_side = s1.x + s1.w;
		int s1_top_side = s1.y;
		int s1_bottom_side = s1.y + s1.h;
		
		int s2_left_side = s2.x;
		int s2_right_side = s2.x + s2.w;
		int s2_top_side = s2.y;
		int s2_bottom_side = s2.y + s2.h;
		
		if(s1_right_side <= s2_left_side)
			return false;
		if(s1_left_side >= s2_right_side)
			return false;
		if(s1_bottom_side <= s2_top_side) // assumes bigger is downward
			return false;
		if(s1_top_side >= s2_bottom_side) // assumes bigger is downward
			return false;
		return true;
	}
	
	
	//Used for loading sprite images
	static BufferedImage loadImage(String imageName)
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
	
}
