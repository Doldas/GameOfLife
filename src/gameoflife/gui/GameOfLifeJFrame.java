package gameoflife.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
/**
 * The Game Of Life Application with JFrame Window GUI
 * 
 * @author anton lekedal(Doldas)
 */
public class GameOfLifeJFrame extends JFrame{
	private static final long serialVersionUID = 5899149978102219092L;
	/**
	 * Constructor
	 * @param title
	 * @param cellWidth
	 * @param cellHeight
	 * @param width
	 * @param height
	 */
	public GameOfLifeJFrame(String title,int cellWidth, int cellHeight,int width, int height) {
		initialize(title,cellWidth,cellHeight,width, height);
	}
	/**
	 * Initialize the game session and GUI.
	 * @param title
	 * @param cellWidth
	 * @param cellHeight
	 * @param width
	 * @param height
	 */
	private void initialize(String title,int cellWidth, int cellHeight,int width, int height) {
		setTitle(title);
		setLayout(new BorderLayout());
		setSize(new Dimension(width,height));
		add(new GameOfLifePanel(cellWidth,cellHeight),BorderLayout.CENTER);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
