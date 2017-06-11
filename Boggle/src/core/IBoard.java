/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;



/**
 *
 * @author Dillon
 */
// UI for the boggle board
public interface IBoard {
    
    // each board has 16 dice in 4x4 layout
    public static final int NUMBER_OF_DICE = 16;
    public static final int GRID = 4;
    
    // This method will invoke the rollDie method for each of the 16 dice in the game
    public void shakeDice();
    
    public void RanLetters();
    
    // This method will add the data to the dice
    public void populateDice();
}
