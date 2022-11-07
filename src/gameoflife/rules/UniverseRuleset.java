package gameoflife.rules;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.javatuples.Quartet;

import gameoflife.GameOfLifeCell;
/**
 * This ruleset is inspired by the universe.
 * Each alive cell can only have 1 neighbor that are alive, and it must be on the cardinals. They shall live hand in hand
 * A cell that are alone or have too many neighbors is sent to death row. 
 * It will die after 30 generations, if it doesn't meet the requirement of having 1 alive cell.
 * The cell that are dead have a chance of 1 in 4000 to be resurrected. The dice of the universe can grant it a new life.
 * 
 * The cell becomes pink if it survived the death-row
 * The cell becomes red if its on the death-row list
 * The cell becomes green of it gets resurrected by random chance
 * 
 * @author anton lekedal(Doldas)
 */
public class UniverseRuleset implements IRuleset{
    List<Quartet<GameOfLifeCell,Integer,Integer,Integer>> deathRow;
    private final Color deathrowColor = Color.RED;
    private final Color leavingDeathrowColor = Color.pink;
    private final Color resurrectedColor = Color.GREEN;
    private final int deathRowTime = 30;
    private final int resurrectionRandomness = 4000;
    
    public UniverseRuleset() {
        this.deathRow = new ArrayList<>();
    }

    @Override
    public GameOfLifeCell execute(GameOfLifeCell current, GameOfLifeCell[][] cells, int i, int j, int width, int height) {
        int aliveNeighbors = countAliveNeighborsCardinalsDirections(cells, i, j, width, height);
        GameOfLifeCell future = new GameOfLifeCell(current.getAge(), current.isAlive(), current.getCharacter(), current.getColor());

        // apply rules
        if (current.isAlive()) {
            if (aliveNeighbors < 1) {
                if(deathRow.size() == 0){
                    int age = future.getAge();
                    future.setAge(age-deathRowTime);
                    deathRow.add(new Quartet<>(future,i,j, age));
                }
                else{
                    boolean existsOnDeathrow = false;
                    for(Quartet<GameOfLifeCell,Integer,Integer,Integer> candidate : deathRow){
                        if(candidate.getValue1() == i && candidate.getValue2() == j){
                            existsOnDeathrow = true;
                            break;
                        }
                    }
                    if(!existsOnDeathrow){
                        int age = future.getAge();
                        future.setAge(age-deathRowTime);
                        future.setColor(deathrowColor);
                        deathRow.add(new Quartet<>(future,i,j, age));
                    } else{
                        for(int x = 0; x < deathRow.size(); x++){
                            Quartet<GameOfLifeCell,Integer,Integer,Integer> candidate = deathRow.get(x);
                            if(candidate.getValue1() == i && candidate.getValue2() == j){
                                if(candidate.getValue3() < future.getAge()){
                                    future = new GameOfLifeCell(false);
                                    future.setColor(GameOfLifeCell.CellState.DEAD.getColor());
                                    deathRow.remove(x);
                                }
                                break;
                            }
                        }

                        }
                    }
            }
            else if(aliveNeighbors > 1){
                // kill the cell
                future = new GameOfLifeCell(false);
                // remove from deathrow if it exists
                for(int x = 0; x < deathRow.size(); x++){
                    Quartet<GameOfLifeCell,Integer,Integer,Integer> candidate = deathRow.get(x);
                    if(candidate.getValue1() == i && candidate.getValue2() == j){
                        deathRow.remove(x);
                        break;
                    }
                }
            }
            else {
                for(int x = 0; x < deathRow.size(); x++){
                    Quartet<GameOfLifeCell,Integer,Integer,Integer> candidate = deathRow.get(x);
                    if(candidate.getValue1() == i && candidate.getValue2() == j){
                        deathRow.remove(x);
                        future.setColor(leavingDeathrowColor);
                        break;
                    }
                }
            }
        }
        else {
            int random = (int) (Math.random() * resurrectionRandomness);
            if (random <= 2) {
                future = new GameOfLifeCell(true);
                future.setColor(resurrectedColor);
            }
        }
        return future;
    }
}
