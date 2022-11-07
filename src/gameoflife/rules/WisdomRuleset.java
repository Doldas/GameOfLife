package gameoflife.rules;

import java.awt.Color;

import gameoflife.GameOfLifeCell;
/**
 * Follows the Conway ruleset.
 * With age comes experience. Cells older than 5 generations it gets old and changes color to cyan.
 * If the cell is older than 10 generations, it gets the character 'E' (for Elder)
 * An elder cell can become a prime elder if its the oldest of them all.
 * @author anton lekedal(doldas)
 *
 */
public class WisdomRuleset implements IRuleset {
    private GameOfLifeCell primeElder;
    private final char oldCellValue;
    private final Color oldColor;
    private final Color elderColor;

    public WisdomRuleset() {
        this.oldCellValue = 'E';
        this.oldColor = Color.cyan;
        this.elderColor = Color.MAGENTA;
        this.primeElder = new GameOfLifeCell(false);
    }

    @Override
    public GameOfLifeCell execute(GameOfLifeCell current, GameOfLifeCell[][] cells, int i, int j, int width, int height) {
        GameOfLifeCell future = new GameOfLifeCell(current.getAge(), current.isAlive(), current.getCharacter(), current.getColor());
        int aliveNeighbors = countAliveNeighborsAllDirections(cells, i, j, width, height);

        // applying conway rules
        if (current.isAlive()) {
            if (aliveNeighbors < 2 || aliveNeighbors > 3) {
                if(current.getColor() == elderColor) {
                    primeElder = new GameOfLifeCell(false);
                    primeElder.setAge(0);
                }
                future = new GameOfLifeCell(false);
            }
        } else {
            if (aliveNeighbors == 3) {
                future = new GameOfLifeCell(true);
            }
        }
        // change the state of the cell based on its age.
        elderfy(future);	

        return future;
    }

    /**
     * transform the cell based on its age.
     * @param cell
     */
    private void elderfy(GameOfLifeCell cell){
        if (cell.isAlive()) {
            if(cell.getAge() > 5){
                if(cell.getColor() != elderColor)
                    cell.setColor(oldColor);
            }
            if(cell.getAge() > 10)
                cell.setCharacter(oldCellValue);

            if (cell.getAge() > primeElder.getAge() && cell.getAge() > 10) {
                primeElder.setColor(oldColor);
                primeElder = cell;
                primeElder.setColor(elderColor);
            }
        }
    }
}
