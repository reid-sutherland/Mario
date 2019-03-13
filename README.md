### Read this!
Using the tutorial from the Pokemon game, download this project and set it up on IntelliJ.
Click [here](https://github.com/reid-sutherland/Pokemon) to see the tutorial.

### Try playing the game
Once you've got it running on your computer, try playing the game

#### Controls
The arrow keys will let you walk forward and backward.
Spacebar will let you jump
Left-clicking anywhere on the map will add a **Brick** object to the map.
Shift-clicking (hold down shift and left-click the mouse) anywhere on the map will add a **Coin Block** object to the map
Pressing **s** will save the map you have created.
Pressing **l** will load your last-saved map.

### Time for animation!
Notice that the Mario image doesn't change, so it doesn't really look like he's walking. Let's fix that!

First look at the Mario images we have inside of the images directory. 
Look in your "Project navigation" window on the left side of the screen.
Double click on the "images" directory to expand it, and notice the mario images we have.

![alt text](https://github.com/reid-sutherland/Mario/tree/master/extra_images/image1.png "mario images")

Mostly we will be looking at the code in the Mario class, and the Controller class

![image](https://github.com/reid-sutherland/Mario/tree/master/extra_images/image2.png "mario controller")

Open up the Mario class, and look for the "contructor", which looks like this:

![image](https://github.com/reid-sutherland/Mario/tree/master/extra_images/loadimages.png "load images")

The code in the red box is where we create an "array" (or list) of different mario images.

Also, look for the "marioAnimation" integer.

![image](https://github.com/reid-sutherland/Mario/tree/master/extra_images/marioAnimation.png "mario animation")

Recall that this line of code means we are creating an Integer, called "marioAnimation", and initializing it to 0.

Now, scroll down and look for a function called "draw" in the Mario class:

![image](https://github.com/reid-sutherland/Mario/tree/master/extra_images/draw.png "draw")

This function draws the mario image every time the screen refreshes.
Notice the marioAnimation variable, which is underlined.
This line of code means that every time the screen refreshes, it's going to pick an image out of the list, based on the index that is passed in the parentheses.
In this case, we pass in marioAnimation to pick out image.
Right now, marioAnimation is always 0, so the draw() function will always draw the first Mario image in the list.
This is why our Mario doesn't change on screen.
