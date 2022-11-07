package gameoflife.rules;

import gameoflife.GameOfLifeCell;
/**
 * Follows the Conway ruleset, but only cardinals directions will be checked.
 * @author anton lekedal(doldas)
 */
public class VonNeumannRuleset implements IRuleset{
    @Override
    public GameOfLifeCell execute(GameOfLifeCell current, GameOfLifeCell[][] cells, int i, int j, int width, int height) {
        GameOfLifeCell future = new GameOfLifeCell(current.getAge(), current.isAlive(), current.getCharacter(), current.getColor());

        int aliveNeighbors = countAliveNeighborsCardinalsDirections(cells, i, j, width, height);

        // apply rules
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
