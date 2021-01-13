# Java-Pool
====================

An implementation of pool in Java.

The .zip file contains an eclipse project which you can execute yourself.

![Alt text](https://raw.githubusercontent.com/graham-ryan/Pool/main/Gameplay%20Screenshot.PNG "The game on startup")
![Alt text](https://raw.githubusercontent.com/graham-ryan/Pool/main/Gameplay%20Screenshot%202.PNG "The end of a game")

# How does it work?

For the GUI, I used JPanel/JFrame from Java's Swing interface. All objects are drawn on this GUI using Swing's Graphics2D class.

Thank you to youtuber GamesWithGabe for his tutorial on game loops. His channel: https://www.youtube.com/c/GamesWithGabe/videos

When the game starts, the GUI is created and ball objects are placed on a table. The user can then aim their mouse around the cue ball, and click to shoot. This was implemented through a MouseListener.

Collisions are checked for each ball by seeing if there are any other balls contained in its radius, or if the ball is hitting an edge of the table. Each ball contains a velocity vector, and if a collision does occur between two balls, simple vector math and physics are used to determine the final velocity of both. If a ball hits the edge of the table, the velocity is simply inverted.

For calculating final velocities after two balls collide, I was inspired by this post on Stack Exchange: https://gamedev.stackexchange.com/a/7901
