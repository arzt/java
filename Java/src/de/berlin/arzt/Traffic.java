package de.berlin.arzt;
import java.awt.*;
import javax.swing.*;

public class Traffic extends JPanel implements Runnable {
	static final long serialVersionUID = 5;
	
	private int width;
	private int carWidth;
	private int carLength;
	private double progress = 0;
	private int xOffset = 4;
	private int yOffset = 4;
	
	
	public Traffic(int width, double carLength, double carWidth) {
		this.width = width;
		this.carLength = (int) (width*carLength);
		this.carWidth = (int) (width*carWidth);
	}
	
	public Traffic() {
		this(50, 0.63, 0.32);
	}
	
	protected void paintComponent(Graphics g2) {
		int w = getWidth();
		int h = getHeight();
		Graphics2D g = (Graphics2D) g2;
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		g.setColor(g.getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		for (int i = 0; i < w+4*width; i = i + 2*width) {
			g.fillRect((int) (i-2*width*progress)-2*width -carLength/2, yOffset*width-carWidth/2, carLength, carWidth);
			g.fillRect((int) (i+2*width*progress)-carLength/2, yOffset*width+2*width -carWidth/2, carLength, carWidth);
		}
		for (int i = 0; i < h+2*width; i = i + 2*width) {
			g.fillRect(xOffset*width-carWidth/2, (int) (i+2*width*progress)-width -carLength/2, carWidth, carLength);
			g.fillRect(xOffset*width+2*width-carWidth/2, (int) (i-2*width*progress)+width-carLength/2, carWidth, carLength);			
		}
	}
	
	public void run() {
		while(true) {
			progress = (progress + 0.1) % 1;
			this.paintImmediately(0, 0, getWidth(), getHeight());
			try {
				Thread.sleep(20);
			}
			catch (Exception w) {}
		}
	}

	
	public static void main(String[] args) {
		JFrame test = new JFrame("Hallo du");
		Traffic hui = new Traffic();
		test.add(hui);
		test.setSize(800,600);
		Thread nanu = new Thread(hui);
		nanu.start();
		test.setVisible(true);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
