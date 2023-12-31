import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Panel extends JPanel implements Runnable{

	static final int GAME_WIDTH = 1000;
	static final int GAME_HEIGHT = (int)(GAME_WIDTH *(0.5555));
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
	static final int BALL_DIAMETER = 20;
	static final int PADDLE_WIDTH = 25;
	static final int PADDLE_HEIGHT = 100;
	
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	Score score;

	
	Panel(){ 
		 newBall();
		 newPaddles();
		 score = new Score(GAME_WIDTH,GAME_HEIGHT );
		 this.setFocusable(true); // Detect key stroke
		 this.addKeyListener(new AL()); // actionlistener for keys
		 this.setPreferredSize(SCREEN_SIZE);
		 

	
		 gameThread = new Thread(this);
		 gameThread.start();
		 
	}
	
	public void newBall() {
		ball = new Ball((GAME_HEIGHT/2)-(BALL_DIAMETER/2), (GAME_HEIGHT/2)-(BALL_DIAMETER/2), BALL_DIAMETER, BALL_DIAMETER);
	}
	
	public void newPaddles() {
		paddle1 = new Paddle(0, (GAME_HEIGHT/2)-(PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 1 );
		paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT/2)-(PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 2 );
		
	}
	
	public void paint(Graphics g) {
		
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image,0,0,this);
		// need more understanding here
		
	}
	public void draw(Graphics g) {
		if(score.player1 == 3 || score.player2 == 3) {
			Gameover(g); // GAME OVER
		}
		else if(score.player1 == 4 || score.player2 == 4) {
			// A way to restart the game
			newBall();    
			newPaddles();
			score = new Score(GAME_WIDTH, GAME_HEIGHT );
		}
		else {
			paddle1.draw(g);
			paddle2.draw(g);
			ball.draw(g);
			score.draw(g);
		}
			
		

	}
	public void move() {
		paddle1.move();
		paddle2.move();
		ball.move();
		
	}
	
	
	
	public void Gameover(Graphics g) {
		String player = "";
		if (score.player1 >= 3) {
			player = "Blue";
		}
		if (score.player2 >= 3) {
			player = "Red";
			
		}
		

		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString(player + " wins", (GAME_WIDTH - metrics1.stringWidth( player + " wins"))/2, g.getFont().getSize());

		
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (GAME_WIDTH - metrics2.stringWidth("Game Over"))/2, GAME_HEIGHT/2);
	}
	
	public void checkCollision() {
		// for the ball to bounce at window up and down
		if (ball.y <= 0)
			ball.setYDirection(-ball.yVelocity);
		if(ball.y >= GAME_HEIGHT - BALL_DIAMETER)
			ball.setYDirection(-ball.yVelocity);
		
		// for the ball to bounce on paddles
		if(ball.intersects(paddle1)) {
			ball.xVelocity = - ball.xVelocity;
			ball.xVelocity++;
			if(ball.yVelocity++ > 0)
				ball.yVelocity++;
			else
				ball.yVelocity--;
					
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		if(ball.intersects(paddle2)) {
			ball.xVelocity = - ball.xVelocity;
			ball.xVelocity--;
			
			if(ball.yVelocity++ > 0)
				ball.yVelocity++;
			else
				ball.yVelocity--;
			
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		
		
		// for the paddles up and down bounds
		if(paddle1.y <= 0)
			paddle1.y = 0;
		if(paddle1.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
			paddle1.y = (GAME_HEIGHT-PADDLE_HEIGHT);
		
		if(paddle2.y <= 0)
			paddle2.y = 0;
		if(paddle2.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
			paddle2.y = (GAME_HEIGHT-PADDLE_HEIGHT);
		
		
		// give a 1 point to player
		if(ball.x <= 0) {
			score.player2++;
			newBall();
			newPaddles();
			
		}
		
		if (ball.x >= GAME_WIDTH) {
			score.player1++;
			newBall();
			newPaddles();
			
		}
		
	}
	public class AL extends KeyAdapter{
		
		public void keyPressed(KeyEvent e) {
			paddle1.keyPressed(e);
			paddle2.keyPressed(e);
			
		}
		public void keyReleased(KeyEvent e) {
			paddle1.keyReleased(e);
			paddle2.keyReleased(e);

		}
	}
	
	@Override
	public void run() {
		// game loop don't understand !!!!
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while(true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				move();
				checkCollision();
				//win();
				repaint();
				delta--;
			}
		}
		
		
	}
}
