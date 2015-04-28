package de.berlin.arzt.awt;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;


public class MouseMover {
	public static void main(String[] args) throws AWTException, InterruptedException {
		while (true) {			
			Point p = MouseInfo.getPointerInfo().getLocation();
			int x = p.x;
			int y = p.y;
			System.out.println(x + ":" + y);
			Robot robot = new Robot();
			x = x + (int) (Math.random()*20 - 10);
			y = y + (int) (Math.random()*20 - 10);
		    robot.mouseMove(x, y);
		    Thread.sleep(100);
		}
	}
}
