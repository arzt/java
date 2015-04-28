package de.berlin.arzt.type;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JApplet;

public class TypeRApplet extends JApplet {

	private static final long serialVersionUID = -6580373967700755872L;
	public void init() {
	    try {
	        javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
	            public void run() {
	                createGUI();
	            }
	        });
	    } catch (Exception e) {
	        System.err.println("createGUI didn't successfully complete");
	    }
	}

	private void createGUI() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		String chars = "<yxcvbasdfgqwert";
//		String chars = "zhnujmik,ol.pö-üä+#";
//		String chars = "qwertzuiopasdfghjklyxcvbnm";
		String chars = "aeiousdfjklmnbvcrtwphg";
		chars += "ü+öä#<,.-";
		//		chars += "QWERTZUIOPÜÄÖLKJHGFDSAYXCVBNM";
		Type t = new Type(new DefaultCharProvider(chars));
		t.setBackground(Color.WHITE);
		getContentPane().add(t);
		t.setFont(t.getFont().deriveFont(100f));
		t.setFramesPerSecond(40);
//		t.setSleepTime(100);
//		t.setAnimationDiscount(0.998);
		t.toggleAnimation();
	}
}
