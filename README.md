# Java-Pool
====================

An implementation of pool in Java.

The .zip file contains an eclipse project which you can execute yourself.

# Q: Why did you make this?
I started working on this after I finished my second Java class at OSU, as I became confident with OOP concepts and Java programming. When beginning this project, I was interested in learning about GUIs, game loops, events, 2D graphics, and collisions - and a pool game seemed to apply all of these concepts.

# Q: How does it work?

For the GUI, I used JPanel/JFrame from Java's Swing interface. All objects are drawn on this GUI using Swing's Graphics2D class.

Thank you to youtuber GamesWithGabe for his tutorial on game loops. His channel: https://www.youtube.com/c/GamesWithGabe/videos

When the game starts, the GUI is created and ball objects are placed on a table. The user can then aim their mouse around the cue ball, and click to shoot. This was implemented through a MouseListener.

Collisions are checked for each ball by seeing if there are any other balls contained in its radius, or if the ball is hitting an edge of the table. Each ball contains a velocity vector, and if a collision does occur between two balls, simple vector math and physics are used to determine the final velocity of both. If a ball hits the edge of the table, the velocity is simply inverted.

For calculating final velocities after two balls collide, I was inspired by this post on Stack Exchange: https://gamedev.stackexchange.com/a/7901

# Q: What else would you like to do with this project?
There are many ways I could have cleaned up the code. Though, overall I was really happy with the result and brevity of a lot of it.

As a feature, it would be really fun to implement this in a web application with multiplayer!
