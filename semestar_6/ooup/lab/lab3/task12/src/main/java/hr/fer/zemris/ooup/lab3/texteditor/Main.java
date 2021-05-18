package hr.fer.zemris.ooup.lab3.texteditor;

import javax.swing.SwingUtilities;

public class Main {
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> {
			TextEditor editor = new TextEditor();
			editor.setVisible(true);
		});
		
	}
	
}