import java.awt.Color;
import java.awt.Graphics;

public class Ground extends Sprite {

	int groundHeight;
	
	Ground(int _x, int _y, int _w, int _h, Model _m)
	{
		model = _m;
		
		x = _x;
		y = _y;
		w = _w;
		h = _h;

		collision = true;
	}

	
	boolean update() 
	{
		return true;
	}

	void draw(Graphics g, int cameraPos) 
	{
		g.setColor(Color.gray);
		g.drawLine(0, y, 2000, y);
		g.setColor(Color.black);
		g.fillRect(0, y, model.screenLength, model.screenHeight - groundHeight);

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
