package gameoflife;

import gameoflife.rules.IRuleset;
import utils.reflection.ObjectCreatorUtil;
/**
 * Game Of Life - Cellular Automaton
 * A zero player game devised by the British mathematician John Horton Conway in 1970.
 * 
 * @author anton lekedal(Doldas)
 */
public class GameOfLife {
    private GameOfLifeCell[][] grid; // game-area
    private int width; // amount of columns
    private int height; // amount of rows
    private int generation; // keeping track on current generation
    private int delay; // update delay
    private boolean running; // is the game running?
    private boolean paused; // is the game paused?
    private long startTime; // keeping track of when the game session started
    private IRuleset ruleset; // rule-set to be applied on the cells

    /**
     * Constructor
     * @param ruleset cellular rules
     * @param width cell columns
     * @param height cell rows
     */
    public GameOfLife(IRuleset ruleset,int width, int height) {
        this.ruleset = ruleset;
        this.width = width;
        this.height = height;
        grid = new GameOfLifeCell[width][height];
        generation = 0;
        delay = 1000;
        running = false;
    }
    /**
     * Start a game session
     */
    public void start() {
    	if(!running) {
            running = true;
            paused = false;
            this.startTime = System.currentTimeMillis();
            new Thread(() -> {
                while (running) {
                    if (!paused) {
                        generation++;
                        updateGame(); // update the game area
                        try {
                            Thread.sleep(delay); // wait a set amount of delay.
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();	
    	}
    }

    /**
     * Update the game-area
     */
    private void updateGame() {
        GameOfLifeCell[][] newGrid = new GameOfLifeCell[width][height];
    	// executing the rules on each cell,
        // keep track on their age.
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                newGrid[i][j] = ruleset.execute(grid[i][j],grid, i, j, width, height);
                if(newGrid[i][j].isAlive())
                    newGrid[i][j].incrementAge();
                else
                    newGrid[i][j].setAge(0);
            }
        }
        setGrid(newGrid);
    }
    /**
     * pause the game
     */
    public void pause() {
        this.paused = !this.paused;
    }
    /**
     * randomize the game-area with alive cells
     */
    public void randomize() {
        clear();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new GameOfLifeCell( Math.random() < 0.5);
            }
        }
    }
    /**
     * Fill the game-area with alive cells
     */
    public void fill() {
        clear();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new GameOfLifeCell(true);
            }
        }
    }
    /**
     * clear the game session and game-area
     */
    public void clear(){
        this.generation = 0;
        this.grid = new GameOfLifeCell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new GameOfLifeCell(false);
            }
        }
        ruleset = ObjectCreatorUtil.createObject(ruleset.getClass().getName());
        startTime = System.currentTimeMillis();
    }
    /**
     * set the update delay in milliseconds
     * @param delay milliseconds
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }
    /**
     * @return update delay
     */
    public int getDelay() {
        return this.delay;
    }
    /**
     * @return The current cell generation
     */
    public int getGeneration() {
        return this.generation;
    }
    /** 
     * @return is the game running?
     */
    public boolean isRunning() {
        return this.running;
    }
    /**
     * @return is the game session paused?
     */
    public boolean isPaused() {
        return this.paused;
    }
    /**
     * @return the game-area
     */
    public GameOfLifeCell[][] getGrid() {
        return this.grid;
    }
    /**
     * sets the game area with alive/dead cells
     * @param grid the game-area
     */
    public void setGrid(GameOfLifeCell[][] grid) {
        this.grid = grid;
    }
    /**
     * get specific cell by its x and y coordinate
     * @param x 
     * @param y
     * @return The cell
     */
    public GameOfLifeCell getCell(int x, int y) {
        return this.grid[x][y];
    }
    /**
     * @return the width of the game-area
     */
    public int getWidth() {
        return this.width;
    }
    /**
     * @return the height of the game-area
     */
    public int getHeight() {
        return this.height;
    }
    /**
     * sets the width of the game-area
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }
    /**
     * sets the height of the game-area
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }
    /**
     * @return amount of cells that are currently alive
     */
    public int countAliveCells() {
        int count = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[i][j].isAlive()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * @return amount of cells that are currently dead.
     */
    public int countDeadCells() {
        int count = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (!grid[i][j].isAlive()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * @return returns the runtime
     */
    public long getTimeElapsed() {
        return System.currentTimeMillis() - this.startTime;
    }

    /**
     * sets the rule-set for the cells to follow
     * @param ruleset The set of rules for the cells to follow
     */
    public void setRuleset(IRuleset ruleset) {
        this.ruleset = ruleset;
        // reset color of all cells
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(grid[i][j].isAlive())
                    grid[i][j].setColor(GameOfLifeCell.CellState.ALIVE.getColor());
                else
                    grid[i][j].setColor(GameOfLifeCell.CellState.DEAD.getColor());
                grid[i][j].setCharacter(GameOfLifeCell.DEFAULT_CHARACTER);
            }
        }
    }
}
