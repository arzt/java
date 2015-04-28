package de.berlin.arzt.type;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import de.berlin.arzt.type.interfaces.CharProvider;
import de.berlin.arzt.type.interfaces.CharProviderListener;

public class Type extends JComponent implements CharProviderListener {
	private boolean isAnimated;
	private int rightPos;
	private int leftPos;
	private int wrongFill;
	private int hitsPerMinute;
	private static final long serialVersionUID = 8702333780416680582L;
	protected long sleep = 10;
	CharProvider charProvider;
	private double currentCharXOffset = 0.5;
	private double currentCharYOffset;
	private double shiftOffset;
	private double lastWidth;
	private double animationDiscount;
	private double charSpace;
	char[] c = new char[1];
	char[] wrong;
	char[] left;
	char[] right;
	public static final char BACKSPACE = (char) 8;
	private Component me;

	private Thread animationThread;

	public Type(CharProvider provider) {
		super();
		this.charProvider = provider;
		setFocusable(true);
		charSpace = 10;
		int rightLength = 30;
		int leftLength = 30;
		int wrongLength = 50;
		animationDiscount = 0.999;
		left = new char[leftLength];
		right = new char[rightLength];
		wrong = new char[wrongLength];
		isAnimated = false;
		for (int i = 0; i < rightLength; i++) {
			char c = charProvider.getNextChar();
			right[i] = c;
		}
		me = this;
	}

	public double getAnimationDiscount() {
		return animationDiscount;
	}

	public double getCharSpace() {
		return charSpace;
	}
	
	public void setSlidingSpeed(double speed) {
		
	}
	
	public void setAnimationDiscount(double animationDiscount) {
		this.animationDiscount = animationDiscount;
	}

	public void setCharSpace(double charSpace) {
		this.charSpace = charSpace;
	}

	
	
	public boolean isAnimated() {
		return isAnimated;
	}
	
	protected  void	paintComponent(Graphics gOld) {
		Graphics2D g = (Graphics2D) gOld;
		Color old = g.getColor();
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(old);
		currentCharYOffset = getHeight()/2.;
		g.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING
				, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.BLACK);
		Font f = getFont();
		double lastX = currentCharXOffset * getWidth();
		g.drawString(Integer.toString(hitsPerMinute), (float) currentCharXOffset, 100f);
		g.setFont(f);

		c[0] = right[(rightPos) % right.length];
		GlyphVector glyph = f.createGlyphVector(g.getFontRenderContext(), c);
		Rectangle2D bounds = glyph.getOutline().getBounds2D();
		lastWidth = bounds.getWidth() + charSpace;
		g.setColor(Color.BLACK);
		g.drawGlyphVector(glyph, (float) (lastX - bounds.getX() + shiftOffset), (float) currentCharYOffset);
		lastX += bounds.getWidth() + charSpace;
		
		for (int i = 0; i < wrongFill; i++) {
			c[0] = wrong[i];
			glyph = f.createGlyphVector(g.getFontRenderContext(), c);
			bounds.setFrame(glyph.getOutline().getBounds2D());
			g.setColor(Color.RED);
			g.drawGlyphVector(glyph, (float) (lastX - bounds.getX() + shiftOffset), (float) currentCharYOffset);
			lastX += bounds.getWidth() + charSpace;
		}
		
		for (int i = 1; i < right.length; i++) {
			c[0] = right[(rightPos + i) % right.length];
			glyph = f.createGlyphVector(g.getFontRenderContext(), c);
			bounds.setFrame(glyph.getOutline().getBounds2D());
			g.setColor(Color.LIGHT_GRAY);
			g.drawGlyphVector(glyph, (float) (lastX - bounds.getX() + shiftOffset), (float) currentCharYOffset);
			lastX += bounds.getWidth() + charSpace;
		}
		
		lastX = currentCharXOffset*getWidth() - charSpace;
		g.setColor(Color.LIGHT_GRAY);
		for (int i = 0; i < left.length; i++) {
			c[0] = left[(leftPos - i - 1 + left.length) % left.length];
			glyph = f.createGlyphVector(g.getFontRenderContext(), c);
			bounds.setFrame(glyph.getOutline().getBounds2D());
			float glyphX = (float) (lastX - bounds.getWidth() - bounds.getX() + shiftOffset);
			g.drawGlyphVector(glyph, glyphX, (float) currentCharYOffset);
			lastX = lastX - bounds.getWidth() - charSpace;
		}
	}
	
	protected void processKeyEvent(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) {
		} else if (e.getID() == KeyEvent.KEY_TYPED) {
//			System.out.println((int) e.getKeyChar());
			char c = e.getKeyChar();
			System.out.println("Char: " + c + " " + (int) c);
			if (c == right[rightPos] && wrongFill == 0) {
				hitsPerMinute++;
//				System.out.println("hits: " + hitsPerMinute);
				startHitPerMinuteThread(60000);
				charProvider.reward(c);
				c = charProvider.getNextChar();
				char cOld = right[rightPos];
				right[rightPos] = c;
				rightPos = (rightPos + 1) % right.length;
				if (left.length > 0) {
					left[leftPos] = cOld;
					leftPos = (leftPos + 1) % left.length;
				}
				if (isAnimated) {
					shiftOffset += lastWidth;
				} else {
					repaint();
				}
			} else {
				if (c == BACKSPACE) {
					if (wrongFill > 0) {
						wrongFill--;
						repaint();
					}
				} else {
					wrong[wrongFill] = c;
					wrongFill++;
					charProvider.penalize(right[rightPos]);
					repaint();
				}
			}
		}
	}
	
	private void startHitPerMinuteThread(final int time) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				hitsPerMinute--;
				if (!isAnimated || shiftOffset == 0) {
					repaint();
				}
//				System.out.println("hits: " + hitsPerMinute);
			}
		}).start();
	}

	public void setFramesPerSecond(int fps) {
		this.sleep = (long) (1000./fps);
	}
	
	private void runAnimation() {
		Thread currentThread = Thread.currentThread();
		while (currentThread == animationThread) {
			currentThread = Thread.currentThread();
			try {
				Thread.sleep(sleep);
				if (shiftOffset > 1) {
					shiftOffset *= Math.pow(animationDiscount, sleep);
				} else {
					shiftOffset = 0;
				}
				repaint(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	
	public void setAnimated(boolean flag) {
		if (isAnimated != flag) {
			toggleAnimation();
		}
	}
	
	public void toggleAnimation() {
		if (!isAnimated) {
			animationThread = new Thread(new Runnable() {
				@Override
				public void run() {
					runAnimation();
				}
			});
			animationThread.start();
		} else {
			animationThread = null;
			shiftOffset = 0;
		}
		repaint();
		isAnimated = !isAnimated;
	}
	
	public void cheat(final long millis) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(millis);
						char c = right[rightPos];
						KeyEvent e = new KeyEvent(me, KeyEvent.KEY_TYPED, 0, 0, 0, c);
						processKeyEvent(e);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
						throw new RuntimeException(e1);
					}
				}
				
			}
		}).start();
	}
	
	public void setSleepTime(long sleep) {
		if (sleep > 0) {
			this.sleep = sleep;
		}
	}

	@Override
	public void setCharProvider(CharProvider provider) {
		this.charProvider = provider;
		rightPos = 0;
		int nChars = right.length;
		for (int i = 0; i < nChars; i++) {
			char c = charProvider.getNextChar();
			right[i] = c;
		}
	}
	
//	public void setLeftSize(int size) {
//		char[] newLeft = new char[size];
//		int leftOffset = Math.max(0, newLeft.length - left.length);
//		for (int i = 0; i < Math.min(newLeft.length, left.length); i++) {
//			newLeft[i + leftOffset] = left[(leftPos + i) % left.length];
//		}
//		leftPos = leftOffset;
//		left = newLeft;
//	}
}