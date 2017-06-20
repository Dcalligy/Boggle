/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;
import core.Board;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;

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

    public BoggleUi(){
        
    	initComponents();
    }

    private void initComponents(){

    	// Initialize the JFrame
    	frame = new JFrame("Boggle");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(660, 500);
    	frame.setResizable(true);

        // Initialize JMenuBar
    	createMenu();
        // Initialize current JPanel
    	setupCurrentPanel();
        // Initialize words JPanel
    	setupWordsPanel();
        // Initialize boggle JPanel
    	setupBogglePanel();

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
        exit = new JMenuItem("Exit");
        exit.setMnemonic('E');
        
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

        // add them to the wordsPanel
        wordsPanel.add(scrollPane);
        wordsPanel.add(timeLabel);
        wordsPanel.add(shakeDiceButton);
    }

    // Boggle Panel
    private void setupBogglePanel(){

    	// initialize boggle panel
        bogglePanel = new JPanel(new GridLayout(4, 4));
        bogglePanel.setMinimumSize(new Dimension(400, 400));
        bogglePanel.setPreferredSize(new Dimension(400, 400));
        bogglePanel.setBorder(BorderFactory.createTitledBorder("Boggle Board"));
        
        // Initialize dice buttons
        diceButtons = new JButton[Board.GRID][Board.GRID];
        
        // loop for col, and rows
        for(int row = 0; row < Board.GRID; row++){
            for(int col = 0; col < Board.GRID; col++){
                
                diceButtons[row][col] = new JButton();
                bogglePanel.add(diceButtons[row][col]);
            }
        }
    }
}