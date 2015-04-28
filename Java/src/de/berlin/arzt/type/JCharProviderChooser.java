package de.berlin.arzt.type;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.berlin.arzt.type.interfaces.CharProvider;
import de.berlin.arzt.type.interfaces.CharProviderListener;

public class JCharProviderChooser extends JPanel implements ActionListener {

	private static final String SMALL_LETTERS = "abcdeftghijklmnopqrstuvwxyz";
	private static final String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String ALPHANUMERIC_LETTERS = "Alphanumeric Letters";
	private static final String NUMBERS = "0123456789";
	private static final String SPECIAL_CHARACTERS_1 = ".,-+*!?";
	private static final String SPECIAL_CHARACTERS_2 = "\\/{}[]()=:;#$|~"; 
	public static final String ALPHA_SMALL_LETTERS = "Small Letters";
	private static final String[] templates = {
		ALPHA_SMALL_LETTERS, 
		CAPITAL_LETTERS, 
		NUMBERS, 
		ALPHANUMERIC_LETTERS};
	private JComboBox<String> charChooser = new JComboBox<String>(templates);
	private CharProviderListener listener = null;
	private HashMap<String, String> charMap;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3760984976147748426L;
	
	public JCharProviderChooser() {
		charMap = new HashMap<String, String>();
		charMap.put("Numbers", NUMBERS);
		charMap.put("Letters (small)", SMALL_LETTERS);
		charMap.put("Letters (capital)", CAPITAL_LETTERS);
		charMap.put("Letters", SMALL_LETTERS + CAPITAL_LETTERS);
		charMap.put("Alphanumeric", SMALL_LETTERS + CAPITAL_LETTERS + NUMBERS);
		charMap.put("Special Characters I", SPECIAL_CHARACTERS_1);
		charMap.put("Special Characters II", SPECIAL_CHARACTERS_2);
		charMap.put("Special Characters", SPECIAL_CHARACTERS_1 + SPECIAL_CHARACTERS_2);
		charMap.put("Everything (small letters)", NUMBERS + SMALL_LETTERS + SPECIAL_CHARACTERS_1 + SPECIAL_CHARACTERS_2);
		charMap.put("Everything", NUMBERS + SMALL_LETTERS + CAPITAL_LETTERS + SPECIAL_CHARACTERS_1 + SPECIAL_CHARACTERS_2);
		Set<String> keys = charMap.keySet();
		String[] items = new String[keys.size()];
		charMap.keySet().toArray(items);
		Arrays.sort(items);
		charChooser = new JComboBox<String>(items);
		charChooser.setEditable(true);
		add(charChooser);
		charChooser.addActionListener(this);
	}
	
	public void setCharProviderListener(CharProviderListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String s = (String) charChooser.getSelectedItem();
		if (listener != null) {
			String value = charMap.get(s);
			System.out.println("Key: " + s + " Value: " + value);
			if (value == null) {
				value = s;
			}
			DefaultCharProvider newProvider = new DefaultCharProvider(value);
			listener.setCharProvider(newProvider);
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("typR");
		frame.setSize(1000, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JCharProviderChooser comp = new JCharProviderChooser();
		CharProviderListener hui =  new CharProviderListener() {
			
			@Override
			public void setCharProvider(CharProvider provider) {
				// TODO Auto-generated method stub
				
			}
		};
		comp.setCharProviderListener(hui);
		frame.getContentPane().add(comp);
		frame.setVisible(true);
	}
}
