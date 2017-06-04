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
// Method, where we create the board of our game
public class Board implements IBoard {
    // stores the letter data from the data file
    ArrayList<String> boggleData;
    
    // stores the dictionary data
    ArrayList<String> dictionaryData;
    
    // collection of dice
    ArrayList<Die> boggleDice;
    
    public Board(ArrayList<String> diceData, ArrayList<String> dictionary){
        
        boggleData = diceData;
        dictionaryData = dictionary;
        boggleDice = new ArrayList<Die>();
    }
    
    
    @Override
    public void shakeDice(){
        throw new UnsupportedOperationException("Not supported yet."); // To change the body of generated methods, choose Tools | Templates.
        
    }

    @Override
    public void populateDice() {
        
        Die die;// new data type Die
        int counter = 0;
        
        // Create an instance of class String, locally called value
        //loop through the contents of container names letters
        // loop 16 times for die
        for(int dice = 0; dice < NUMBER_OF_DICE; dice++){
            
            // create instance of die
            die = new Die();
            
            // add 6 times of the array list to populate the die sides
            for(int side = 0; side < die.NUMBER_OF_SIDES; side++){
                
                die.addLetter(boggleData.get(counter).toString());
                counter++;
            }
            
            // temp
            System.out.print("Die " + dice + ": ");
            die.displayLetters();
            System.out.println();
            
            boggleDice.add(die);
                   
        }
    }
    
}
