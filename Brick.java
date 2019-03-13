import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Brick extends Sprite
{
	static BufferedImage brickImage = null;

	Brick(int _x, int _y, Model _m)
	{
		x = _x;
		y = _y;
		model = _m;
		collision = true;

		if (brickImage == null)
			brickImage = loadImage("images/Brick.png");

		w = brickImage.getWidth();
		h = brickImage.getHeight();
	}

	Brick(Json ob, Model _m)
	{
		x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		model = _m;
		collision = true;

		w = brickImage.getWidth();
		h = brickImage.getHeight();
	}
	
	void draw(Graphics g, int cameraPos)
	{
		g.drawImage(brickImage,x - cameraPos, y, null);
	}
	
	boolean update()
	{	
		return true;
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
	
	Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("type", "Brick");
        ob.add("x", x);
		ob.add("y", y);
        return ob;
    }
	
}