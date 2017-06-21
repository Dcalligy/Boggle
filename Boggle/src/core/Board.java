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
    
    // ArrayList to store the game data
    private ArrayList<String> shakeDiceGameData;
    
    // stores the letter data from the data file
    ArrayList<String> boggleData;
    
    // stores the dictionary data
    ArrayList<String> dictionaryData;
    
    // collection of dice
    ArrayList<Die> boggleDice;
    
    // collection of 16 dice with dice and letters randomized
    private ArrayList<String> gameDice;
    
    // keep track of which die has been used 
    private ArrayList<Integer> used;
    
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
        gameDice = new ArrayList();
        used = new ArrayList();
        int dieCount = 0;
        
        while(dieCount < 16){
            
            // randomly select a die
            int index = getRandomDie();
            
            if(!used.contains(index)){
                
                Die die = boggleDice.get(index);
                gameDice.add(die.rollDie());
                
                used.add(new Integer(index));
                
                dieCount++;
            }
        }
    }
    
    private int getRandomDie(){
        
        // randomly select a die from the 16 dice
        Random random = new Random();
        int value = random.nextInt(NUMBER_OF_DICE);
        return value;   
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
    
    public void displayGameData(){
        
        int nextline = 0;
        // loop through the contents of the container names letters
        for(String value : gameDice){
            
            System.out.print(value + " ");
            nextline++;
            
            if(nextline % 4 == 0)
                System.out.println();
        }
        
    }
    
}
