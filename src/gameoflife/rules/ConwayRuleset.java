package gameoflife.rules;

import gameoflife.GameOfLifeCell;
/**
 * Follows the Conway ruleset
 * Less than 2 or greater than 3 alive neighbors the cell die, else it lives.
 * A dead cell can become alive if it have 3 neighbors that are alive
 * All Directions
 * @author anton lekedal(Doldas)
 */
public class ConwayRuleset implements IRuleset {
    @Override
    public GameOfLifeCell execute(GameOfLifeCell current, GameOfLifeCell[][] cells,int i, int j, int width,int height) {
        int aliveNeighbors = countAliveNeighborsAllDirections(cells, i, j, width, height);
        GameOfLifeCell future = new GameOfLifeCell(current.getAge(), current.isAlive(), current.getCharacter(), current.getColor());
        if (current.isAlive()) {
            if (aliveNeighbors < 2 || aliveNeighbors > 3) {
                future = new GameOfLifeCell(false);
            }
        } else {
            if (aliveNeighbors == 3) {
                future = new GameOfLifeCell(true);
            }
        }

        return future;
    }

}
