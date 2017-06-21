/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.ArrayList;
import java.util.Random;



/**
 *
 * @author Dillon
 */
// Method to create the die
public class Die implements IDie{
    
    private ArrayList<String> letters = new ArrayList<String>(); // stores dice data for the sides of the die
    private String letter;
    int die;
    
    @Override
    // Enhanced for loop to output each letter on ech of the six sides of die
    public void displayLetters() {
        
           for(String value : letters){
               System.out.print("" + value +" ");// prints out the toimes
           }
    }

    @Override
    // Add parameter to the ArrayList 
    // represnting the letters on the die
    public void addLetter(String letter) {
        
        letters.add(letter);
    }
    
    @Override
    public String rollDie() {
       
        Random random = new Random();
        die = random.nextInt(NUMBER_OF_SIDES);
        letter = letters.get(die);
        return letter;
        
    }   
}
