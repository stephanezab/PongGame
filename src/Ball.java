import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Ball  extends Rectangle{
	
	Random random;
	int xVelocity;
	int yVelocity;
	int speedx = 2;
	int speedy = 1;
	
	Ball(int x, int y, int width, int height){
		super(x, y, width, height);
		random = new Random();
		int randomXDirection = random.nextInt(2);
		if(randomXDirection == 0) {
			randomXDirection--;
		}
		setXDirection(randomXDirection);
		
		int randomYDirection = random.nextInt(2);
		if(randomYDirection == 0) {
			randomYDirection--;
		}
		setYDirection(randomYDirection);
		
		
	}
	
	public void setXDirection(int randomXDirection) {
		xVelocity = randomXDirection;
	}

	public void setYDirection(int randomYDirection) {
		yVelocity = randomYDirection;
	}
	
	public void move() {
		x += xVelocity * speedx;
		y += yVelocity * speedy;
		
	}
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillOval(x, y, width, height);
		
	}

}
