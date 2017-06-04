/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.ArrayList;



/**
 *
 * @author Dillon
 */
// Method to create the die
public class Die implements IDie{
    
    private ArrayList<String> letters = new ArrayList<String>(); // stores dice data for the sides of the die
   

    @Override
    // Enhanced for loop to output each letter on ech of the six sides of die
    public void displayLetters() {
        
           for(String value : letters){
               System.out.print("" + value +" ");
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
