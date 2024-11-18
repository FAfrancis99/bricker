# bricker
Bricker Game
A Java-based brick-breaking arcade game inspired by classic games like Breakout and Arkanoid. The game features a paddle, a ball, and rows of bricks. Players use the paddle to bounce the ball and break all the bricks to win the game.

Table of Contents
Overview
Features
Controls
Gameplay
Getting Started
Technologies Used
Acknowledgments
Overview
The Bricker Game provides an engaging experience with:

Multiple rows of bricks.
Paddle and ball physics.
A life counter to track the player's attempts.
Beautiful visual effects with customizable backgrounds and graphics.
The game dynamically adjusts based on user input, allowing flexibility in the number of rows and bricks.

Features
Dynamic Brick Layout:
Customize the number of rows and bricks via command-line arguments.
Paddle Control:
Use the paddle to control the ball's direction.
Realistic Ball Physics:
The ball bounces realistically off bricks, walls, and the paddle.
Life System:
Players start with 3 lives. Losing the ball reduces a life.
Win/Lose Conditions:
Clear all bricks to win.
Lose all lives to end the game.
Audio Feedback:
Sound effects play on collisions for enhanced immersion.
Controls
Move Paddle: Use the left and right arrow keys (← and →) to control the paddle.
Pause Game: Press P to pause.
Restart Game: Press W after losing or winning to restart the game.
Gameplay
The game begins with the ball placed at the center.
Use the paddle to bounce the ball and break all bricks.
Bricks can be of different types, with some requiring multiple hits or triggering special effects.
Keep the ball in play to avoid losing lives.
Getting Started
Prerequisites
Java Development Kit (JDK) 11 or higher.
A Java IDE like IntelliJ IDEA, Eclipse, or any IDE of your choice.
Running the Game
Clone the repository:
bash
Copy code
git clone https://github.com/yourusername/bricker-game.git
Compile and run the BrickerGameManager class:
bash
Copy code
javac bricker/main/BrickerGameManager.java
java bricker.main.BrickerGameManager
Command-Line Arguments
You can specify the number of rows and bricks per row when running the game:

bash
Copy code
java bricker.main.BrickerGameManager <numRows> <numBricksInRow>
Example:

bash
Copy code
java bricker.main.BrickerGameManager 10 8
If no arguments are provided, the game defaults to 8 rows and 7 bricks per row.

Technologies Used
Java: Core programming language.
Danogl Framework: Lightweight 2D game framework.
Object-Oriented Design: For extensibility and maintainability.
Acknowledgments
This project was developed using the Danogl Framework, which provides tools for game rendering, physics, and object management.

Feel free to contribute or provide feedback! 🎮
![image](https://github.com/user-attachments/assets/aaa2b7a9-efd1-43c1-8f56-3a9dab4574a0)

