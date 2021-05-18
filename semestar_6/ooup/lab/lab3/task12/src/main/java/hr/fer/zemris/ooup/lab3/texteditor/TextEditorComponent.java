package hr.fer.zemris.ooup.lab3.texteditor;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.Objects;

import javax.swing.JComponent;

import hr.fer.zemris.ooup.lab3.texteditor.model.CursorObserver;
import hr.fer.zemris.ooup.lab3.texteditor.model.Location;
import hr.fer.zemris.ooup.lab3.texteditor.model.TextEditorModel;
import hr.fer.zemris.ooup.lab3.texteditor.model.TextObserver;

import static java.awt.event.KeyEvent.*;

public class TextEditorComponent extends JComponent {
	
	private static final Font FONT = makeFont();
	private static final int LEFT_PADDING = 2;
	private static final Color CURSOR_COLOR = Color.RED;
	
	private static Font makeFont() {
		int style = Font.PLAIN;
		int size = 20;
		String name = Font.MONOSPACED;
		
		Font f = new Font(name, style, size);
		return f;
	}
	
	private TextEditorModel model;
	private Location cursor;
	
	private TextObserver to = () -> this.repaint();
	private CursorObserver co = loc -> {
		this.cursor = loc;
		this.repaint();
	};
	private KeyListener kl = new KeyAdapter() {

		@Override
		public void keyTyped(KeyEvent e) {
			char c = e.getKeyChar();
			switch(c) {
			case '\b' -> model.deleteBefore();
			case '\u007F' -> model.deleteAfter();
			case '\n' -> model.insert(model.getLineSeparator());
			default -> model.insert(c);
			}
			
		}
		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			switch(keyCode) {
			case VK_UP -> model.moveCursorUp();
			case VK_LEFT -> model.moveCursorLeft();
			case VK_DOWN -> model.moveCursorDown();
			case VK_RIGHT -> model.moveCursorRight();
			}
			
		}
		@Override
		public void keyReleased(KeyEvent e) {}
		
	};
	
	private void attachObservers() {
		model.addTextObserver(to);
		model.addCursorObserver(co);
		this.addKeyListener(kl);
		
	}
	
	public TextEditorComponent(TextEditorModel model) {
		this.model = Objects.requireNonNull(model);
		this.cursor = model.getCursorLocation();
		this.setFocusable(true);
		this.attachObservers();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setFont(FONT);
		FontMetrics fm = g.getFontMetrics();
		int asc = fm.getAscent();
		int desc = fm.getDescent();
		
		int row = cursor.rowIndex;
		int col = cursor.colIndex;
		
		Iterator<String> it = model.allLines();
		int i = 0;
		while (it.hasNext()) {
			String line = it.next();
			int x = 0 + LEFT_PADDING;
			int y = asc*(i+1) + desc*i;
			
			if (i == row) {
				// nacrtaj kursor
				String pref = line.substring(0, col);
				int cw = fm.stringWidth(pref);
				
				Color old = g.getColor();
				g.setColor(CURSOR_COLOR);
				g.drawRect(x+cw, y-asc, 0, asc+desc);
				g.setColor(old);
				
			}
			
			g.drawString(line, x, y);
			i++;
			
		}
		
		
		
	}
	
}
