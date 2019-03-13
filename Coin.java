import java.awt.Graphics;
import java.util.Random;
import java.awt.image.BufferedImage;

public class Coin extends Sprite 
{
	static BufferedImage coinImage = null;
	double hor_vel = 0;
	double vert_vel = -10;
	
	static Random randomInt = new Random();
	
	Coin(int _x, int _y, Model _m)
	{
		x = _x;
		y = _y;
		model = _m;
		collision = false;
		
		if(coinImage == null)
			coinImage = loadImage("images/Coin.png");
		
		//Sets seed to clock time and creates a random horizontal speed
		randomInt.setSeed(System.currentTimeMillis());
		hor_vel = randomInt.nextDouble() * 25.0 - 12.5;
		
		w = coinImage.getWidth();
		h = coinImage.getHeight();
	}


	boolean update() 
	{
		x += hor_vel;
		
		vert_vel += 1.2;
		y += vert_vel;
		
		if(detectCollision(model.mario, this))
			return false;
		if(y > model.screenHeight)
			return false;
		
		return true;
	}

	void draw(Graphics g, int cameraPos) 
	{
		g.drawImage(coinImage,x - cameraPos, y, null);
	}

	boolean isMario()
	{
		return false;
	}

	boolean isCoinBlock() 
	{
		return false;
	}
	
	boolean isCoin()
	{
		return false;
	}
	


}
