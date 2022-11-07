package gameoflife.rules;

import gameoflife.GameOfLifeCell;
/**
 * The rule set interface for all rule sets.
 * @author anton lekedal(Doldas)
 */
public interface IRuleset {
	/**
	 * Applies the rules on the cell
	 * @param current
	 * @param cells
	 * @param i x coordinate
	 * @param j y coordinate
	 * @param width
	 * @param height
	 * @return updated version of the cell
	 */
    GameOfLifeCell execute(GameOfLifeCell current,GameOfLifeCell[][] cells, int i, int j, int width, int height);
    /**
     * Counts the neighbors on all directions
     * @param cells
     * @param i
     * @param j
     * @param width
     * @param height
     * @return amount of alive cell neighbors
     */
    default int countAliveNeighborsAllDirections(GameOfLifeCell[][] cells, int i, int j, int width, int height) {
        int aliveNeighbors = 0;
        for (int x = i - 1; x <= i + 1; x++) {
            for (int y = j - 1; y <= j + 1; y++) {
                if (x == i && y == j) {
                    continue;
                }
                if (x < 0 || x >= width || y < 0 || y >= height) {
                    continue;
                }
                if (cells[x][y].isAlive()) {
                    aliveNeighbors++;
                }
            }
        }
        return aliveNeighbors;
    }
    /**
     * Counts the neighbors on all directions but 2 rows and columns instead of 1
     * @param cells
     * @param i
     * @param j
     * @param width
     * @param height
     * @return amount of alive cell neighbors
     */
    default int countAliveNeighborsAllDirectionsTwoRows(GameOfLifeCell[][] cells, int i, int j, int width, int height) {
        int aliveNeighbors = 0;
        for (int x = i - 2; x <= i + 2; x++) {
            for (int y = j - 2; y <= j + 2; y++) {
                if (x == i && y == j) {
                    continue;
                }
                if (x < 0 || x >= width || y < 0 || y >= height) {
                    continue;
                }
                if (cells[x][y].isAlive()) {
                    aliveNeighbors++;
                }
            }
        }
        return aliveNeighbors;
    }
    /**
     * Counts the neighbors on the cardinals
     * @param cells
     * @param i
     * @param j
     * @param width
     * @param height
     * @return amount of alive cell neighbors
     */
    default int countAliveNeighborsCardinalsDirections(GameOfLifeCell[][] cells, int i, int j, int width, int height) {
        int aliveNeighbors = 0;
        // north
        if (j > 0 && cells[i][j-1].isAlive()) {
            aliveNeighbors++;
        }
        // east
        if (i < width-1 && cells[i+1][j].isAlive()) {
            aliveNeighbors++;
        }
        // south
        if (j < height-1 && cells[i][j+1].isAlive()) {
            aliveNeighbors++;
        }
        // west
        if (i > 0 && cells[i-1][j].isAlive()) {
            aliveNeighbors++;
        }

        return aliveNeighbors;
    }
    /**
     * Counts the neighbors on the diagonals
     * @param cells
     * @param i
     * @param j
     * @param width
     * @param height
     * @return amount of alive cell neighbors
     */
    default int countAliveNeighborsDiagonalsDirections(GameOfLifeCell[][] cells, int i, int j, int width, int height) {
        int aliveNeighbors = 0;
        // count alive neighbors but only on the diagonal directions {north-east, south-east, south-west, north-west}
        // north-east
        if (i < width-1 && j > 0 && cells[i+1][j-1].isAlive()) {
            aliveNeighbors++;
        }
        // south-east
        if (i < width-1 && j < height-1 && cells[i+1][j+1].isAlive()) {
            aliveNeighbors++;
        }
        // south-west
        if (i > 0 && j < height-1 && cells[i-1][j+1].isAlive()) {
            aliveNeighbors++;
        }
        // north-west
        if (i > 0 && j > 0 && cells[i-1][j-1].isAlive()) {
            aliveNeighbors++;
        }

        return aliveNeighbors;
    }
}
