package gameoflife;

import java.awt.*;
/**
 * The Cell Class
 * 
 * @author anton lekedal(Doldas)
 */
public class GameOfLifeCell {
	// The State of the Cell
    public enum CellState {
        DEAD,
        ALIVE;

        public Color getColor() {
            return switch (this) {
                case DEAD -> Color.WHITE;
                case ALIVE -> Color.ORANGE;
            };
        }
    }
    public static char DEFAULT_CHARACTER = ' '; // stores a default character representation
    
    private int age; // The age of the cell
    private boolean alive; // is it alive?
    private char character; // the character representation
    private Color color; // The color of the cell

    /**
     * Constructor
     */
    public GameOfLifeCell() {
        this(0,false,DEFAULT_CHARACTER,CellState.DEAD.getColor());
    }
    /**
     * Constructor with parameters
     * @param age the age of the cell
     * @param alive is it alive?
     * @param character the character representation for the cell
     * @param color the color of the cell
     */
    public GameOfLifeCell(int age, boolean alive, char character, Color color) {
        this.age = age;
        this.alive = alive;
        this.character = character;
        this.color = color;
    }

    /**
     * Constructor with parameters
     * @param age
     * @param alive
     * @param character
     */
    public GameOfLifeCell(int age, boolean alive, char character) {
        if(alive) {
            this.age = age;
            this.color = CellState.ALIVE.getColor();
        }
        else {
            this.age = 0;
            this.color = CellState.DEAD.getColor();
        }


        this.alive = alive;
        this.character = character;

    }
    /**
     * Constructor
     * @param alive
     */
    public GameOfLifeCell(boolean alive) {
        this(0,alive,' ');
    }
    /**
     * @return the age of the cell
     */
    public int getAge() {
        return age;
    }
    /**
     * sets the age
     * @param age of the cell
     */
    public void setAge(int age) {
        this.age = age;
    }
    /**
     * @return is the cell alive?
     */
    public boolean isAlive() {
        return alive;
    }
    /**
     * sets the alive state
     * @param alive
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    /**
     * @return the character representation
     */
    public char getCharacter() {
        return character;
    }
    /**
     * sets the character representation
     * @param character
     */
    public void setCharacter(char character) {
        this.character = character;
    }
    /**
     * @return the color of the cell
     */
    public Color getColor() {
        return color;
    }
    /**
     * Sets the color representation of the cell
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
    }
    /**
     * Increments the age by 1
     */
    public void incrementAge() {
        this.age++;
    }
    /**
     * Reduce the age by 1
     */
    public void decrementAge() {
        if(this.age > 0)
            this.age--;
    }
}
