package de.berlin.arzt.type;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		JFrame frame = new JFrame("typR");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(
				(int) (screenSize.width * 0.25), (int) (screenSize.height * 0.25),
				(int) (screenSize.width * 0.5), (int) (screenSize.height * 0.5));
//		String chars = "" 
//			+ "aeiuop" 
//			+ "nrtdsyb"
//			+ "eanr"
			;
//		String chars = "zhnujmik,ol.pö-üä+#";
//		String chars = "qwertzuiopasdfghjklyxcvbnm";
//		String chars = "aeiousdfjklmnbvcrtwphg";
		String chars = "1234567890ß´!\"§$%&/()=?`qwertzuiopü+*~asdfghjklöä#'<>|yxcvbnm,.-;:_ ";
//		chars += "ü+öä#<,.-";
		//		chars += "QWERTZUIOPÜÄÖLKJHGFDSAYXCVBNM";
		Type t = new Type(new DefaultCharProvider("abc"));
		t.setBackground(Color.WHITE);
		JCharProviderChooser chooser = new JCharProviderChooser();
		chooser.setCharProviderListener(t);
		frame.getContentPane().add(chooser, BorderLayout.NORTH);
		frame.getContentPane().add(t, BorderLayout.CENTER);
		t.setFont(t.getFont().deriveFont(100f));
		t.setFramesPerSecond(50);
//		t.setSleepTime(100);
//		t.setAnimationDiscount(0.998);
		t.toggleAnimation();
//		t.cheat(150);
		frame.setVisible(true);
	}
}
