import java.util.ArrayList;

class Model
{
	ArrayList<Sprite> sprites;
	Mario mario;
	
	int cameraPos = 0;
	int backgroundPos = 0;
	int groundHeight = 596;
	
	int screenLength = 1920;
	int screenHeight = 720;

	Model()
	{
		//create an ArrayList to hold all of the sprites
		sprites = new ArrayList<Sprite>();
		
		//add Mario
		mario = new Mario(500,0, this);
		sprites.add(mario); 
		
		//add Ground
		Ground ground = new Ground(0, 596, screenLength, 1, this);
		sprites.add(ground);
		
		//adds a couple Coin Blocks
		addCoinBlock(600, 200);
		addCoinBlock(1000,200);
		
		//adds test Bricks
		addBrick(700,500);
		addBrick(900,500);
		addBrick(300, 300);
	}
	
	//Corrects collision between Mario and a sprite
	//TO-DO: Modify correction to use previous x and y
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
			//If mario hits his head, set his vertical velocity to 0
			m.y += bDif;
			//m.prev_y = m.y;
			if(m.vert_vel < 0 )
				m.vert_vel = 0;
			
		}
		else if(tDif <= bDif && tDif <= lDif && tDif <= rDif)
		{
			//If mario is on top of a box, set his vertical velocity to 0 
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
			cameraPos -= lDif;
			m.x -= lDif;
			backgroundPos += (int) lDif * (0.5);
			
			//m.prev_x = m.x;

		}
		else if(rDif < tDif && rDif < lDif && rDif < bDif)
		{
			cameraPos += rDif;
			m.x += rDif;
			backgroundPos -= (int) rDif * (0.5);
			//m.prev_x = m.x;
			
		}		
		
	}
	

	
//	void addBrick(int x, int y, int w, int h)
	void addBrick(int x, int y)
	{
		Brick b = new Brick(x, y, this);
		sprites.add(b);		
	}
	
	void addCoinBlock(int x, int y)
	{
		CoinBlock b = new CoinBlock(x, y, this);
		sprites.add(b);
	}
	
	
	
	public void update()
	{
		for(int i = 0; i < sprites.size(); i++)
		{
			//Updates the current selected sprite starting with Mario
			Sprite s = sprites.get(i);
			
			//Update sprite and remove it if necessary
			if(!s.update())
			{
				sprites.remove(i);
				i--;
			}
			
			//Detects collision between sprites but make sure they aren't Mario
			if(Mario.detectCollision(mario, s) && !s.isMario() && s.collision)
			{ 
				//Correct collision so Mario isn't inside of the sprite
				collisionCorrection(mario, s);	
			}		
		}
	}
	
	void save(String fileName)
	{
		Json ob = this.marshal();
		ob.save(fileName);			
	}
	
	void load(String fileName)
	{
		Json ob = Json.load(fileName);
		unmarshall(ob);
	}
	
	Json marshal()
    {
        Json ob = Json.newObject();
        Json tmpList = Json.newList();
        ob.add("sprites", tmpList);
        for(int i = 0; i < sprites.size(); i++)
        {
       		tmpList.add(sprites.get(i).marshal());
        }
        
        //Add camera position
        Json cp = Json.newObject();
        cp.add("type", "CameraPos");
        cp.add("CP", cameraPos);
        tmpList.add(cp);
        
        return ob;
    }
	
	void unmarshall(Json ob)
	{
		//Clear sprites
		sprites.clear();
		
        Json spriteList = ob.get("sprites");
        for(int i = 0; i < spriteList.size(); i++)
        {
        	String name = spriteList.get(i).getString("type");
        	
        	//Adds Bricks
        	if(name.equals("Brick"))
        		sprites.add(new Brick(spriteList.get(i), this));
        	
        	//Adds Mario
        	else if(name.equals("Mario"))
        	{
        		Mario m = new Mario(spriteList.get(i), this);
        		sprites.add(m);
        		mario = m;
        	}
        	
        	//Adds CoinBlocks
        	else if(name.equals("CoinBlock"))
        		sprites.add(new CoinBlock(spriteList.get(i), this));
        	
        	//Adds Coins
        	else if(name.equals("Coin"))
           		sprites.add(new Coin(spriteList.get(i), this));
        	
        	//Adds Ground
        	else if(name.equals("Ground"))
        		sprites.add(new Ground(spriteList.get(i), this));
        	
        	else if(name.equals("CameraPos"))
        		cameraPos = (int)spriteList.get(i).getLong("CP");
        }
	}
	
}