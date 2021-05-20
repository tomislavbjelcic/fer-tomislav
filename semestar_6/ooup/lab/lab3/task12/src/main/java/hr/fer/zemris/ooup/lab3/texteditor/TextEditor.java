package hr.fer.zemris.ooup.lab3.texteditor;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import hr.fer.zemris.ooup.lab3.texteditor.model.TextEditorModel;

public class TextEditor extends JFrame {
	
	private TextEditorModel model;
	
	public TextEditor() {
		this.model = new TextEditorModel();
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(900, 600);
		this.setLocationRelativeTo(null);
		this.setTitle("Text editor");
		this.initGUI();
	}
	
	private void initGUI() {
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(new TextEditorComponent(model), BorderLayout.CENTER);
	}
	
}
