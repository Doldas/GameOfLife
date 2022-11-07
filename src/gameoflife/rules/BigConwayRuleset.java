package gameoflife.rules;

import gameoflife.GameOfLifeCell;
/**
 * Follows the Conway ruleset, but its 5x5 grid instead of 3x3
 * @author anton lekedal(Doldas)
 */
public class BigConwayRuleset implements IRuleset {
    @Override
    public GameOfLifeCell execute(GameOfLifeCell current, GameOfLifeCell[][] cells, int i, int j, int width, int height) {
        GameOfLifeCell future = 
        		new GameOfLifeCell(current.getAge(), current.isAlive(), current.getCharacter(), current.getColor());

        int aliveNeighbors = countAliveNeighborsAllDirectionsTwoRows(cells, i, j, width, height);

        // applying the rules
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
