package hr.fer.zemris.ooup.lab4;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.ooup.lab4.model.AddShapeState;
import hr.fer.zemris.ooup.lab4.model.DocumentModel;
import hr.fer.zemris.ooup.lab4.model.DocumentModelListener;
import hr.fer.zemris.ooup.lab4.model.GraphicalObject;
import hr.fer.zemris.ooup.lab4.model.IdleState;
import hr.fer.zemris.ooup.lab4.model.Point;
import hr.fer.zemris.ooup.lab4.model.State;

public class GUI extends JFrame {
	
	private List<GraphicalObject> objects;
	private DocumentModel model = new DocumentModel();
	private Canvas canvas = new Canvas();
	private State currentState = new IdleState() {};
	
	private class Canvas extends JComponent {
		
		private G2DRendererImpl renderer = new G2DRendererImpl();
		
		public Canvas() {
			this.setFocusable(true);
			
			initListeners();
		}
		
		private void initListeners() {
			MouseAdapter ml = new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					e.consume();
					if (!SwingUtilities.isLeftMouseButton(e))
						return;
					Point mousePoint = new Point(e.getX(), e.getY());
					boolean shiftDown = e.isShiftDown();
					boolean ctrlDown = e.isControlDown();
					currentState.mouseUp(mousePoint, shiftDown, ctrlDown);
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					e.consume();
					if (!SwingUtilities.isLeftMouseButton(e))
						return;
					Point mousePoint = new Point(e.getX(), e.getY());
					boolean shiftDown = e.isShiftDown();
					boolean ctrlDown = e.isControlDown();
					currentState.mouseDown(mousePoint, shiftDown, ctrlDown);
				}
			};
			this.addMouseListener(ml);
			
			MouseMotionAdapter mml = new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					Point point = new Point(e.getX(), e.getY());
					currentState.mouseDragged(point);
				}
			};
			
			this.addMouseMotionListener(mml);
			
			KeyAdapter kl = new KeyAdapter() {

				@Override
				public void keyPressed(KeyEvent e) {
					currentState.keyPressed(e.getKeyCode());
				}
				
			};
			
			this.addKeyListener(kl);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			//super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			renderer.setG2D(g2d);
			List<GraphicalObject> gos = model.list();
			for (var go : gos) {
				go.render(renderer);
				currentState.afterDraw(renderer, go);
			}
			this.repaint();
		}
		
	}
	
	public GUI(List<GraphicalObject> objects) {
		this.objects = objects;
		
		this.setSize(500, 500);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);

		initModel();
		initGUI();
	}
	
	private void initGUI() {
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		
		Canvas canvas = new Canvas();
		cp.add(canvas, BorderLayout.CENTER);
		
		JToolBar toolbar = new JToolBar();
		for (GraphicalObject proto : objects) {
			String name = proto.getShapeName();
			JButton btn = new JButton(new AbstractAction() {
				{
					this.putValue(Action.NAME, name);
				}
				@Override
				public void actionPerformed(ActionEvent e) {
					State newState = new AddShapeState(model, proto);
					currentState.onLeaving();
					currentState = newState;
				}
				
			});
			toolbar.add(btn);
		}
		cp.add(toolbar, BorderLayout.PAGE_START);
		
	}
	
	private void initModel() {
		model.clear();
		model.addDocumentModelListener(new DocumentModelListener() {
			@Override
			public void documentChange() {
				canvas.repaint();
			}
		});
	}
	
	
}
