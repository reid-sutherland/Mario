import javax.swing.JFrame;
import java.awt.Toolkit;

public class Main extends JFrame
{
	Model model;
	View view;
	Controller controller;
	
	public Main()
	{
		model = new Model();
		controller = new Controller(model);
		view = new View(controller, model);
		
		view.addMouseListener(controller);
		this.addKeyListener(controller);
		
		this.setTitle("Mario Main");
		this.setSize(model.screenLength, model.screenHeight);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}

	public static void main(String[] args)
	{
		Main g = new Main();
		g.run();
	}
	
	public void run()
	{
		while(true)
		{
			controller.update();
			model.update();
			view.repaint(); // Indirectly calls View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for 50 milliseconds
			try
			{
				Thread.sleep(40);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
