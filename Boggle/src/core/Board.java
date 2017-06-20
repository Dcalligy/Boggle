/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;
import java.util.Random;

import java.util.ArrayList;

/**
 *
 * @author Dillon
 */
// Method, where we create the board of our game
public class Board implements IBoard {
    
    int nextline = 0;
    Random randomNum = new Random();
    
    // ArrayList to store the game data
    private ArrayList<String> shakeDiceGameData = new ArrayList<String>();
    
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
        
        // loop through the 16 dice
        // randomly select one die
        // keep track of which die was selected
        // roll the die by calling method rollDie in class Die
        // store that value in our new member variable that has the game data
        for(int i = 0; i < NUMBER_OF_DICE; i++){
            Die die = new Die();
            int randomdie = randomNum.nextInt(16);
            int dieletter = die.rollDie();
            
            int counterdie = (randomdie * 6) + dieletter;
            
            getShakeDiceGameData().add(boggleData.get(counterdie).toString());
            
        }
    }
    
    @Override
    public void RanLetters(){
        
        for(String letter : getShakeDiceGameData()){
            
            System.out.print( letter + " "); // print out the letter plus space
            nextline++;
            if((nextline % 4 == 0))
                System.out.println();
        }
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

    /**
     * @return the shakeDiceGameData
     */
    public ArrayList<String> getShakeDiceGameData() {
        return shakeDiceGameData;
    }
    
}
