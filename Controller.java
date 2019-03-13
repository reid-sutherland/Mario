import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.lang.Math;

class Controller implements ActionListener, MouseListener, KeyListener
{
	Model model;
	View view;
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean keySpace;
	boolean keyShift;
	boolean keyC;
	boolean jumpTrigger = false;
	
	
	int clickX;
	int clickY;
	double jumpHeight = 6.1;
	int marioSpeed = 10;
	//Scroll Speed = Mario Speed
	int backgroundSpeed = 5;
	
	Controller(Model m)
	{
		model = m;
	}
	
	void setView(View v)
	{
		view = v;
	}

	public void actionPerformed(ActionEvent e)
	{
	}
	
	public void mousePressed(MouseEvent e)
	{
		clickX = e.getX() + model.cameraPos;
		clickY = e.getY();
	}

	public void mouseReleased(MouseEvent e) 
	{
		if(keyShift)
		{
			model.addCoinBlock(clickX, clickY);
		}
//		else
//		{
//		int Left = Math.min(clickX, e.getX() + model.cameraPos);
//		int Right = Math.max(clickX, e.getX() + model.cameraPos);
//		int Top = Math.min(clickY, e.getY());
//		int Bottom = Math.max(clickY, e.getY());
//
//		model.addBrick(Left, Top, Right-Left, Bottom-Top);
//		}
		else {
			model.addBrick(clickX, clickY);
		}
	}
	
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) 
	{    
		System.out.println("Break here");
	}
	
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			case KeyEvent.VK_UP: keyUp = true; break;
			case KeyEvent.VK_DOWN: keyDown = true; break;
			case KeyEvent.VK_SHIFT: keyShift = true; break;
			
			//If space is pressed, Mario jumps
			//Jump trigger makes sure that it doesn't miss the jump
			case KeyEvent.VK_SPACE:
				keySpace = true;
				jumpTrigger = true;				
				break;
				
			case KeyEvent.VK_S:	
				//model.save("map.json");
				System.out.println("Saved");
				break;
				
			case KeyEvent.VK_L: 
				//model.load("map.json");
				System.out.println("Loaded");
				break;
				
			case KeyEvent.VK_C:
				//model.addCoinBlock(a.getX(), a.getY());
				break;
			
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;
			case KeyEvent.VK_SPACE: keySpace = false; break;
			case KeyEvent.VK_SHIFT: keyShift = false; break;
		}
	}

	public void keyTyped(KeyEvent e){	}

	void update()
	{
		model.mario.prev_x = model.mario.x;
		model.mario.prev_y = model.mario.y;
		
		if(keyRight)
		{
			model.cameraPos += marioSpeed;
			model.mario.x += marioSpeed;
			model.backgroundPos -= backgroundSpeed;


			// Update Mario's animation
			model.mario.updateMarioAnimation();
		}
		
		if(keyLeft)
		{
			// Scrolls camera, moves Mario, updates Mario animation
			model.cameraPos -= marioSpeed;
			model.mario.x -= marioSpeed;
			model.backgroundPos += backgroundSpeed;


			//TODO: Update Mario's reverse animation
		}
		
		//If space bar was pressed and it has been 5 frames since he has touched the ground, jump
		if(jumpTrigger && model.mario.framesGround < 5|| keySpace && model.mario.framesGround < 5)
		{
			model.mario.vert_vel -= jumpHeight;			
		}
		
		//Reset jump trigger back to false
		jumpTrigger = false;


	}
	
}
