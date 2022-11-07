package gameoflife.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import gameoflife.GameOfLife;
import gameoflife.GameOfLifeCell;
import gameoflife.rules.BigConwayRuleset;
import gameoflife.rules.ConwayRuleset;
import gameoflife.rules.DiagonalConwayRuleset;
import gameoflife.rules.UniverseRuleset;
import gameoflife.rules.VonNeumannRuleset;
import gameoflife.rules.WisdomRuleset;
/**
 * The Game Of Life JPanel GUI
 * 
 * @author anton lekedal(Doldas)
 */
public class GameOfLifePanel extends JPanel {
	private static final long serialVersionUID = 4165625793846078509L;
	private final GameOfLife gameOfLife; // the game class
	private final JSpinner delaySpinner; // the Delay adjuster
	private final int paddWidth; // Window padding
	private final int paddHeight; // window padding
	private final int cellSize; // size in pixels for each cell

	public GameOfLifePanel(int width, int height) {
		paddWidth = 10;
		paddHeight = 200;
		cellSize = 18;
		gameOfLife = new GameOfLife(new ConwayRuleset(), width, height);
		delaySpinner = new JSpinner(new SpinnerNumberModel(200, 16, 10000, 1));
		gameOfLife.setDelay((Integer) delaySpinner.getValue());
		// creating the Game of life GUI Panel
		initializeWindow();
		// Randomize and start the game
		gameOfLife.randomize();
		start();
		// starts the render engine - redraws the frame every 16ms. 16ms is 60fps
		renderEngine();
	}
	/**
	 * creates and adds the GUI elements to the panel
	 */
	private void initializeWindow() {
		JButton pauseButton = new JButton("Pause");
		pauseButton.addActionListener(e -> {
			gameOfLife.pause();
			pauseButton.setText(gameOfLife.isPaused() ? "Resume" : "Pause");
		});
		// randomize button
		JButton randomizeButton = new JButton("Randomize");
		randomizeButton.addActionListener(e -> {gameOfLife.randomize();repaint();});
		// fill button
		JButton fillButton = new JButton("Fill");
		fillButton.addActionListener(e -> {gameOfLife.fill();repaint();});
		// clear button
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(e -> {gameOfLife.clear();repaint();});
		// ruleset dropdown
		JComboBox<String> rulesetDropdown = new JComboBox<>();
		rulesetDropdown.addItem("Conway");
		rulesetDropdown.addItem("Von Neumann");
		rulesetDropdown.addItem("Diagonal Conway");
		rulesetDropdown.addItem("Big Conway");
		rulesetDropdown.addItem("Wisdom");
		rulesetDropdown.addItem("Universe");
		rulesetDropdown.addActionListener(e -> {
			String ruleset = (String) rulesetDropdown.getSelectedItem();
			switch (Objects.requireNonNull(ruleset)) {
			case "Conway" -> gameOfLife.setRuleset(new ConwayRuleset());
			case "Von Neumann" -> gameOfLife.setRuleset(new VonNeumannRuleset());
			case "Diagonal Conway" -> gameOfLife.setRuleset(new DiagonalConwayRuleset());
			case "Big Conway" -> gameOfLife.setRuleset(new BigConwayRuleset());
			case "Wisdom" -> gameOfLife.setRuleset(new WisdomRuleset());
			case "Universe" -> gameOfLife.setRuleset(new UniverseRuleset());
			}
		});
		delaySpinner.addChangeListener(e -> gameOfLife.setDelay((Integer) delaySpinner.getValue()));
		add(pauseButton);
		add(randomizeButton);
		add(fillButton);
		add(clearButton);
		add(rulesetDropdown);
		JPanel delayPanel = new JPanel();
		delayPanel.add(new JLabel("Delay:"));
		delayPanel.add(delaySpinner);
		add(delayPanel);		
	}
	
	/**
	 * Paints the Game-area with cells and information
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (isRunning()) {
			for (int i = 0; i < gameOfLife.getWidth(); i++) {
				for (int j = 0; j < gameOfLife.getHeight(); j++) {
					GameOfLifeCell cell = gameOfLife.getGrid()[i][j];
					g.setColor(cell.getColor());
					g.fillRect((i) * cellSize + paddWidth, (j) * cellSize + paddHeight, cellSize, cellSize);
					// draw cell character in the rectangle
					g.setColor(Color.BLACK);
					String c = String.valueOf(gameOfLife.getGrid()[i][j].getCharacter());
					g.drawString(c, (i) * cellSize + paddWidth + cellSize / 2 - c.length() * cellSize / 2,
							(j) * cellSize + paddHeight + cellSize / 2);
				}
			}
			// paint a grid with borders around the grid cells
			g.setColor(Color.BLACK);
			for (int i = 0; i < gameOfLife.getWidth(); i++) {
				g.drawLine(i * cellSize + paddWidth, paddHeight, i * cellSize + paddWidth,
						gameOfLife.getHeight() * cellSize + paddHeight);
			}
			for (int i = 0; i < gameOfLife.getHeight(); i++) {
				g.drawLine(paddWidth, i * cellSize + paddHeight, gameOfLife.getWidth() * cellSize + paddWidth,
						i * cellSize + paddHeight);
			}
			// paint the generation number
			g.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.BOLD, 16));
			g.drawString("Generation: " + gameOfLife.getGeneration(), paddWidth, 70);

			// paint the running state
			if (gameOfLife.isRunning()) {
				g.drawString("Running", paddWidth, 90);
			} else {
				g.drawString("Paused", paddWidth, 90);
			}

			// paint the delay
			g.drawString("Delay: " + gameOfLife.getDelay(), paddWidth, 110);
			// alive cells
			g.drawString("Alive: " + gameOfLife.countAliveCells(), paddWidth, 130);
			// dead cells
			g.drawString("Dead: " + gameOfLife.countDeadCells(), paddWidth, 150);
			// precentage of alive cells
			g.drawString("Alive %: "
					+ (int) (gameOfLife.countAliveCells() * 100.0 / (gameOfLife.getWidth() * gameOfLife.getHeight())),
					paddWidth, 170);
			// paint the time elapsed in seconds
			g.drawString("Time elapsed: " + ((float) gameOfLife.getTimeElapsed() / 1000), paddWidth, 190);
		}
	}
	/**
	 * @return is the game running?
	 */
	public boolean isRunning() {
		return gameOfLife.isRunning();
	}
	/**
	 * starts the game if not running
	 */
	public void start() {
		gameOfLife.start();
	}
	/**
	 * repaints the window every 16ms.
	 */
	private void renderEngine() {
		new Thread(() -> {
			while(true) {
				repaint();
				try {
					Thread.sleep(16);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
}
