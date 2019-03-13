import java.awt.Graphics;
import java.awt.image.BufferedImage;

class CoinBlock extends Sprite 
{
	static BufferedImage blockFull = null;
	static BufferedImage blockEmpty = null;
	
	int coinsDropped = 0;
	int maxCoinsDropped = 5;
	
	CoinBlock(int _x, int _y, Model _m)
	{
		x = _x;
		y = _y;
		model = _m;
		collision = true;
		
		//Lazy load images
		if(blockFull == null)
			blockFull = loadImage("images/CoinBlock.png");
		
		if(blockEmpty == null)
			blockEmpty = loadImage("images/EmptyCoinBlock.png");
		
		//Get starting width and height
		w = blockFull.getWidth();
		h = blockFull.getHeight();
	}
	
	CoinBlock(Json ob, Model _m)
    {        
        x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		model = _m;
		
		//Get starting width and height
		w = blockFull.getWidth();
		h = blockFull.getHeight();
		
		collision = true;
    }
	
	public boolean fromBottom(Sprite s1, Sprite s2)
	{
		int s1_rightSide = s1.x + s1.w;
		int s2_leftSide = s2.x;
		
		int s1_leftSide = s1.x;
		int s2_rightSide = s2.x + s2.w;
		
		int s1_topSide = s1.y;
		int s2_botSide = s2.y + s2.h;
		
		if(s1.x < s2.x)
		{
			if(Math.abs(s1_rightSide - s2_leftSide) > Math.abs(s2_botSide - s1_topSide))
				return true;
		}
		else
		{
			if(Math.abs(s2_rightSide - s1_leftSide) > Math.abs(s2_botSide - s1_topSide))
				return true;
		}
		return false;
	}

	boolean update() 
	{
		//System.out.println(m.mario.y + " " + m.mario.prev_y);
		if(detectCollision(model.mario, this) && model.mario.vert_vel < 0 && !model.mario.jumpInteraction && fromBottom(model.mario, this))
		{	
			//Prevents multiple coins from being dispensed from the block
			model.mario.jumpInteraction = true;
			
			//Allows coins to be dropped up until the max amount
			if(coinsDropped < maxCoinsDropped)
			{
				coinsDropped++;
				addCoin(x , y - h);
			}
		}
		return true;

	}

	void draw(Graphics g, int cameraPos) 
	{
		//If the max number of coins are dropped, change the image
		if(coinsDropped >= maxCoinsDropped)
			g.drawImage(blockEmpty,x - cameraPos, y, null);
		else
			g.drawImage(blockFull, x - cameraPos, y, null);
	}
	
	void addCoin(int x, int y)
	{
		Coin c = new Coin(x, y, model);
		model.sprites.add(c);
	}

	boolean isMario() 
	{
		return false;
	}
	
	boolean isCoinBlock()
	{
		return true;
	}
	
	boolean isCoin()
	{
		return false;
	}
	

	Json marshal() 
	{
		Json ob = Json.newObject();
		ob.add("type", "CoinBlock");
        ob.add("x", x);
		ob.add("y", y);
        return ob;
	}

}
