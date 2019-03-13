//import javax.swing.JPanel;
//import java.awt.Graphics;
import java.awt.image.BufferedImage;
//import javax.imageio.ImageIO;
//import java.io.IOException;
//import java.util.Iterator;
//import java.io.File;
//import javax.swing.JButton;
//import java.awt.Color;
//import java.awt.Image;
import java.util.ArrayList;
import java.awt.Graphics;

class Mario extends Sprite
{
	
	int prev_x;
	int prev_y;

	int marioAnimation = 0;
	double vert_vel = 0;
	int framesGround = 0;
	boolean jumpInteraction = false;
	
	static ArrayList<BufferedImage> images = null;

	
	Mario(int _x, int _y, Model _m)
	{
		x = _x;
		y = _y;	
		model = _m;
		
		// Load images
		images = new ArrayList<BufferedImage>();
		images.add(loadImage("images/mario1.png"));
		images.add(loadImage("images/mario2.png"));
		images.add(loadImage("images/mario3.png"));
		images.add(loadImage("images/mario4.png"));
		images.add(loadImage("images/mario5.png"));
		images.add(loadImage("images/mario1r.png"));
		images.add(loadImage("images/mario2r.png"));
		images.add(loadImage("images/mario3r.png"));
		images.add(loadImage("images/mario4r.png"));
		images.add(loadImage("images/mario5r.png"));

		//Get starting width and height
		w = images.get(0).getWidth();
		h = images.get(0).getHeight();
	}
	
	Mario(Json ob, Model _m)
    {        
        x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		vert_vel = (int)ob.getDouble("vert_vel");
		model = _m;
    }
	
	
	public void updateMarioAnimation()
	{

	}
	
	public void updateReverseMarioAnimation()
	{

	}
	

	boolean update()
	{
		//Gravity
		vert_vel += 1.2;
		y += vert_vel;
		
		//Width and Height change for each Mario image
		w = images.get(marioAnimation).getWidth();
		h = images.get(marioAnimation).getHeight();
		
		//Increment number of frames since Mario last touched the ground
		framesGround++;
		
		//Floor
		if(y + h >= model.groundHeight)
		{
			vert_vel = 0.0;
			y = model.groundHeight - h; // snap back to the ground
			framesGround = 0;
			jumpInteraction = false;
		}
		
		return true;
	}
	
	void collisionCorrection(Mario m, Sprite b)
	{		
		int marioTop = m.y;
		int marioBottom = m.y + m.h;		
		int marioLeft = m.x;
		int marioRight = m.x + m.w;
		
		int boxTop = b.y;
		int boxBottom = b.y + b.h;		
		int boxLeft = b.x;
		int boxRight = b.x + b.w;
		
		int bDif = Math.abs(boxBottom - marioTop);
		int tDif = Math.abs(marioBottom - boxTop);
		int lDif = Math.abs(marioRight - boxLeft);
		int rDif = Math.abs(boxRight - marioLeft);
		
		if(bDif <= tDif && bDif <= lDif && bDif <= rDif)
		{
			//If Mario hits his head, set his vertical velocity to 0
			m.y += bDif;
			//m.prev_y = m.y;
			if(m.vert_vel < 0 )
				m.vert_vel = 0;
			
		}
		else if(tDif <= bDif && tDif <= lDif && tDif <= rDif)
		{
			//If Mario is on top of a box, set his vertical velocity to 0 
			m.y -= tDif;
			//m.prev_y = m.y;
			if(m.vert_vel > 0)
			{
				m.vert_vel = 0;
				m.framesGround = 0;
				m.jumpInteraction = false;
			}
		}
		else if(lDif < tDif && lDif < bDif && lDif < rDif)
		{
			model.cameraPos -= lDif;
			m.x -= lDif;
			model.backgroundPos += (int) lDif * (0.5);
			//m.prev_x = m.x;
		}
		else if(rDif < tDif && rDif < lDif && rDif < bDif)
		{
			model.cameraPos += rDif;
			m.x += rDif;
			model.backgroundPos -= (int) rDif * (0.5);
			//m.prev_x = m.x;
		}		
		
	}
	
	void draw(Graphics g, int cameraPos)
	{
		g.drawImage(Mario.images.get(marioAnimation), x - cameraPos, y, null);
	}
	
	boolean isMario()
	{
		return true;
	}
	
	boolean isCoinBlock()
	{
		return false;
	}
	
	boolean isCoin()
	{
		return false;
	}
	
	Json marshal()
	{
		Json ob = Json.newObject();
		ob.add("type", "Mario");
        ob.add("x", x);
		ob.add("y", y);
		ob.add("w", w);
		ob.add("h", h);
		ob.add("vert_vel", vert_vel);
        return ob;
	}
	
	
	
}

