package main;

import javax.swing.SwingUtilities;

import gameoflife.gui.GameOfLifeJFrame;

/**
 * Starting the game of life application
 * @author anton lekedal(doldas)
 */
public class GameOfLifeApp {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() ->{			
			new GameOfLifeJFrame("Game of Life",40,30,745,790);
		});
	}

}
