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
// Create the interface for the die
public interface IDie {
    
    // this is a constant
    public static final int NUMBER_OF_SIDES = 6;
    
    public void displayLetters(); // displays the letters in the BoggleData txt file
    
    // this method will add a letter to the die
    public void addLetter(String letter);
    
    // this method will return the current letter of the die
    public int rollDie();
}
