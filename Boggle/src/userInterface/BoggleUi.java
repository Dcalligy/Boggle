/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;
import core.Board;
import core.Die;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.Timer;

/**
 *
 * @author Dillon
 */
public class BoggleUi{

    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu game;
    private JMenuItem exit;
    private JMenuItem newGame;
    private JPanel currentPanel;
    private JButton[][] diceButtons;
    private JPanel wordsPanel;
    private JScrollPane scrollPane;
    private JTextPane wordsArea;
    private JLabel currentLabel;
    private JButton shakeDiceButton;
    private JPanel bogglePanel;
    private JLabel scoreLabel;
    private JButton currentSubmit;
    private JLabel timeLabel;
    
    // class Board reference object
    private Board board;
    
    // reset listener
    private ResetGameListener reset;
    
    // timer
    private Timer timer;
    private int minutes = 3;
    private int seconds = 0;
    
    // Player's Score
    int score = 0;
    
    // Action Listeners
    private JButtonListener jButtonListener;
    private ButtonListener buttonListener;
    
    // Style Document
    private BoggleStyleDocument document;
    
    boolean randomWords[];
    
    private ArrayList<Die> dice;
    private ArrayList<String> dictionaryWords = new ArrayList<String> ();
    private ArrayList<String> foundWords = new ArrayList<String>();
    private ArrayList <String> computersWords = new ArrayList();
    
    private final static int MAX_INDEX = 4;
    private final static int MIN_INDEX = 1;
    private final static String PLUS = "+";
    private final static String MINUS = "-";
    
    
    
    public BoggleUi(Board inBoard, ArrayList<String> dictionary){
        
        board = inBoard;
        // reset = new ResetGameListener();
        dictionaryWords = dictionary;
        initComponents();
    }

    private void initComponents(){

    	// Initialize the JFrame
    	frame = new JFrame("Boggle");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(660, 500);
    	frame.setResizable(true);
        
        document = new BoggleStyleDocument();
        
        // Initialize JMenuBar
    	createMenu();
        // Initialize current JPanel
    	setupCurrentPanel();
        // Initialize words JPanel
    	setupWordsPanel();
        // Initialize boggle JPanel
    	setupBogglePanel();
        // Initialize Timer
        setupTimer();
        
    	// add back into the frame and make it visible
    	frame.setJMenuBar(menuBar);
    	frame.add(currentPanel, BorderLayout.SOUTH);
    	frame.add(wordsPanel, BorderLayout.CENTER);
    	frame.add(bogglePanel, BorderLayout.WEST);
    	frame.setVisible(true);
    }

    // Menu Bar
    private void createMenu(){

    	// Initialize JMenuBar and add to the JFrame
        menuBar = new JMenuBar();
        
        // Initialize the file menu
        game = new JMenu("Boggle");
        game.setMnemonic('B');
        
        newGame = new JMenuItem("New Game");
        newGame.setMnemonic('N');
        newGame.addActionListener(reset);
        
        
        exit = new JMenuItem("Exit");
        exit.setMnemonic('E');
        exit.addActionListener(new ExitListener());
        
        document = new BoggleStyleDocument();
        
        game.add(newGame);
        game.add(exit);
        menuBar.add(game);
    }

    // Current Panel
    private void setupCurrentPanel(){

    	// initialize current panel
        currentPanel = new JPanel();
        currentPanel.setBorder(BorderFactory.createTitledBorder("Current Word"));
        
        // Initialize current label
        currentLabel = new JLabel();
        currentLabel.setBorder(BorderFactory.createTitledBorder("Current Word"));
        currentLabel.setMinimumSize(new Dimension(300, 50));
        currentLabel.setPreferredSize(new Dimension(300, 50));
        currentLabel.setHorizontalAlignment(SwingConstants.LEFT);
       
        
        // initialize current submit
        currentSubmit = new JButton("Submit Word");
        currentSubmit.setMinimumSize(new Dimension(200, 100));
        currentSubmit.setPreferredSize(new Dimension(200, 50));
        currentSubmit.addActionListener(new SubmitWordListener());
        
        // Initialize scoreLabel
        scoreLabel = new JLabel();
        scoreLabel.setBorder(BorderFactory.createTitledBorder("Score"));
        scoreLabel.setMinimumSize(new Dimension(100, 50));
        scoreLabel.setPreferredSize(new Dimension(100, 50));
        scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        // add them into currentPanel
        currentPanel.add(currentLabel);
        currentPanel.add(currentSubmit);
        currentPanel.add(scoreLabel);
    }

    // Words Panel
    private void setupWordsPanel(){

    	// Initialize words panel
        wordsPanel = new JPanel();
        wordsPanel.setLayout(new BoxLayout(wordsPanel, BoxLayout.Y_AXIS));
        wordsPanel.setBorder(BorderFactory.createTitledBorder("Enter Words"));
        
        // Initialize words area
        wordsArea = new JTextPane();
        scrollPane = new JScrollPane(wordsArea);
        scrollPane.setPreferredSize(new Dimension(180, 330));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Initialize Time Label
        timeLabel = new JLabel("3:00");
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setBorder(BorderFactory.createTitledBorder("Time Left"));
        timeLabel.setFont(new Font("Serif", Font.PLAIN, 48));
        timeLabel.setPreferredSize(new Dimension(240, 100));
        timeLabel.setMinimumSize(new Dimension(240, 100));
        timeLabel.setMaximumSize(new Dimension(240, 100));
        
        // Initialize shake dice button
        shakeDiceButton = new JButton("Shake Dice");
        shakeDiceButton.setPreferredSize(new Dimension(240, 100));
        shakeDiceButton.setMinimumSize(new Dimension(240, 100));
        shakeDiceButton.setMaximumSize(new Dimension(240, 100));
        // Resets the boggle board
        shakeDiceButton.addActionListener(new ResetGameListener());

         
        // add them to the wordsPanel
        wordsPanel.add(scrollPane);
        wordsPanel.add(timeLabel);
        wordsPanel.add(shakeDiceButton);
    }

    // Boggle Panel
    private void setupBogglePanel(){
        
        // counter for the ArrayList of the 16 letters
        int counter = 0;
        
        // get new letters for the game from Board.java
        board.shakeDice();

    	// initialize boggle panel
        bogglePanel = new JPanel(new GridLayout(4, 4));
        bogglePanel.setMinimumSize(new Dimension(400, 400));
        bogglePanel.setPreferredSize(new Dimension(400, 400));
        bogglePanel.setBorder(BorderFactory.createTitledBorder("Boggle Board"));
        
        // Initialize dice buttons
        diceButtons = new JButton[Board.GRID][Board.GRID];
        
        // loop for col, and rows
        for(int row = 0; row < Board.GRID; row++)
            for(int col = 0; col < Board.GRID; col++){
                
                diceButtons[row][col] = new JButton();
                // add the buttons using setText() method and the getter from Board.java
                diceButtons[row][col].setText(board.getGameDice().get(counter));
                bogglePanel.add(diceButtons[row][col]);
                counter++;
            }
        

    }
    
    private void setupTimer(){
        
        timer = new Timer(1000, new TimerListner());
        timer.start();
        
    }
    
    private void updateTextArea(String data){
        
        wordsArea.setText(wordsArea.getText() + "\n" + data);
        wordsArea.setCaretPosition(wordsArea.getDocument().getLength());
    }
    
    private void randomWordSelect(){
        
        // generating which words the computer has found
        Random random = new Random();
        int randWord = random.nextInt(foundWords.size());
        
        if(randomWords[randWord] != true){
            
            randomWords[randWord] = true;
        }
        else{
            
            randomWordSelect();
        }
    }
    
    private void modifyScore(String addSubtract, String currentWord){
        
        int wordLength = currentWord.length();
        int value = 0;
        
        switch(wordLength){
            
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                value += 1;
                break;
            case 5:
                value += 2;
                break;
            case 6:
                value += 3;
                break;
            case 7:
                value += 5;
                break;
            default:
                value += 11;
        }
        
        if(addSubtract.equals(PLUS)){
            
            score += value;
        }
        else if(addSubtract.equals(MINUS)){
            
            score -= value;
        }
        
        scoreLabel.setText(String.valueOf(score));
            
    }
    
    private void computerCompare(){
        
        // Modify user that the computer is comparing answers
        JOptionPane.showMessageDialog(null, "GAME OVER! \n   The computer is comparing words. ");
        
        Random random = new Random();
        
        // random number of words found by the computer
        int randomNum = random.nextInt(foundWords.size());
        randomWords = new boolean [foundWords.size()];
        
        JOptionPane.showMessageDialog(null, "The computer found " + randomNum + " of Player's " + foundWords.size());
        
        // only loop for the number of words the computer found
        for(int iter = 0; iter < randomNum; iter++){
            
            randomWordSelect();
        }
        
       // wordsArea.setText("");
        
        String computerWords = "";
        
        for(int j = 0; j < foundWords.size(); j++){
            
            if(randomWords[j] == true){
                
                System.out.println("Word " + j + " of the player was found by the computer ");
                StyleConstants.setStrikeThrough(document.getAttrStyle(), true);
                wordsArea.setDocument(document);
                
                computerWords += (foundWords.get(j) + "\n");
                
                modifyScore(MINUS, foundWords.get(j)); 
            }
            else{
                
                StyleConstants.setStrikeThrough(document.getAttrStyle(), false);
            }
            try{
                
                document.insertString(document.getLength(), foundWords.get(j) + '\n', null);
            }
            catch (BadLocationExeption ex){
                
                Logger.getLogger(BoggleUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        scoreLabel.setText(String.valueOf(score));
    }
    
    // functionality using the dice
    private class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
            // to add first level to currentLabel
            if(currentSubmit.isEnabled() == false){
                
                String temp; // temp is letter corresponding to button pressed
                temp = e.getActionCommand();
                currentLabel.setText(temp);
                // currentLabel is only the first letter pressed
            }
            // when there is text in currentLabel
            else{
                
                String temp2; // temp2 is letter corresponding to button pressed
                temp2 = e.getActionCommand();
                // add letter stored in temp2 to currentLabel to make a word
                currentLabel.setText(currentLabel.getText() + temp2);
            }
        }
            
    }
    
    private class SubmitWordListener implements ActionListener{
        
        
        @Override
        public void actionPerformed(ActionEvent e){
            
            if(dictionaryWords.contains(currentLabel.getText().toLowerCase()) == true){
                
                updateTextArea(currentLabel.getText());
                modifyScore(PLUS, currentLabel.getText());
                foundWords.add(currentLabel.getText());
                scoreLabel.setText(String.valueOf(score));
                currentLabel.setText("");
            }
            else{
                JOptionPane.showMessageDialog(null, "Not a valid word!");
                currentLabel.setText("");
            }
            
            // re-enable all butttons
            int tempRow = -1;
            int tempCol = -1;
            for(int row = 0; row <= MAX_INDEX; row++){
                
                for(int col = 0; col <= MAX_INDEX; col++){
                    
                    diceButtons[row][col].setEnabled(true);
                    
                    if(e.getSource().equals(diceButtons[row][col])){
                        
                        tempRow = row;
                        tempCol = col;
                    }
                    
                }
                
            }
        }
    }
    
    // innner classes
    private class BoggleStyleDocument extends DefaultStyledDocument{
        
        private Style primaryStyle;
        
        public BoggleStyleDocument(){
            
            super();
            primaryStyle = this.addStyle("Primary", null);
        }
        public Style getAttrStyle(){
            
            return primaryStyle;
        }
        
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException{
            
            super.insertString(offs, str, primaryStyle);
        }
    }
    
    private class ExitListener implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent ae){
            
            // Ask user if they want to exit game 
            // if yes exit
            // if no continue
            int response = JOptionPane.showConfirmDialog(null, "Confirm to exit Boggle?", 
                    "Exit?", JOptionPane.YES_NO_OPTION);
            if(response == JOptionPane.YES_OPTION)
                System.exit(0);
            else if(response == JOptionPane.NO_OPTION){
                System.out.println();
            }
                
                
        }    
    }
    
   /* private void changeDice(){
        
        // counter for the ArrayList of 16 letters
        int counter = 0;
        
        // get new letters for the game
        board.shakeDice();
        
        for(int row = 0; row < Board.GRID; row++)
            for(int col = 0; col < Board.GRID; col++){
                
                diceButtons[row][col].setText(board.getGameDice().get(counter));
                counter++;
            }
        
    }
*/
    
    private class TimerListner implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e){
            
            if(seconds == 0 && minutes == 0){
                timer.stop();
            }
            else{
                // makes sure the timer displays properly. i.e., 0:59
                if(seconds == 0){
                    
                    seconds = 59;
                    minutes--;
                }
                else{
                    seconds--;
                }
            }
            // if seconds is less than 10 add a 0 to the front. i.e., 2:09
            if(seconds < 10){
                String strSeconds = "0" + String.valueOf(seconds);
                timeLabel.setText(String.valueOf(minutes) + ":" + strSeconds);
            }
            else{
                
                timeLabel.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
            }
                
        }
    }

    private class ResetGameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            // Reverts the bogglePanel with new letters 
            bogglePanel.removeAll();
            setupBogglePanel();
            frame.add(bogglePanel, BorderLayout.WEST);
            bogglePanel.revalidate();
            bogglePanel.repaint();
            
            // Resets text for new game
            wordsArea.setText("");
            scoreLabel.setText("0");
            currentLabel.setText("");
            timeLabel.setText("3:00");
            shakeDiceButton.setEnabled(true);
             
            // restarts timer
            timer.stop();
            minutes = 3;
            seconds = 0;
            timer.start();
        }
    }
    
    // validate creates functionality for
    // enabling only the buttons around
    // the letter that has been clicked
    private class JButtonListener implements ActionListener{
        
        int tempRow = -1;
        int tempCol = -1;
        
        public void actionPerformed(ActionEvent e){
            
            // grid
            for(int row = 0; row <= MAX_INDEX; row++){
                
                for(int col = 0; col <= MAX_INDEX; col++){
                    
                    // de-enable
                    diceButtons[row][col].setEnabled(false);
                    if(e.getSource().equals(diceButtons[row][col])){
                        
                        tempRow = row;
                        tempCol = col;
                    }
                }
            }
            // for the button to the left
            if(tempRow - 1 >= MIN_INDEX){
                
                diceButtons[tempRow - 1][tempCol].setEnabled(true);
                if(tempCol - 1 >= MIN_INDEX){
                    
                    diceButtons[tempRow - 1][tempCol - 1].setEnabled(true);
                }
                if(tempCol + 1 <= MAX_INDEX){
                    
                    diceButtons[tempRow - 1][tempCol - 1].setEnabled(true);
                }
                // for the button to the right
                if(tempRow + 1 <= MAX_INDEX){
                    
                    diceButtons[tempRow + 1][tempCol].setEnabled(true);
                    if(tempCol - 1 >= MIN_INDEX){
                        
                        diceButtons[tempRow + 1][tempCol - 1].setEnabled(true);
                    }
                    if(tempCol + 1 <= MAX_INDEX){
                        
                        diceButtons[tempRow + 1][tempCol + 1].setEnabled(true);
                    }
                }
                // for the buttons above
                if(tempCol - 1 >= MIN_INDEX){
                    
                    diceButtons[tempRow][tempCol - 1].setEnabled(true);
                }
                if(tempCol + 1 <= MAX_INDEX){
                    
                    diceButtons[tempRow][tempCol + 1].setEnabled(true);
                }
            }
        }
    }

}
