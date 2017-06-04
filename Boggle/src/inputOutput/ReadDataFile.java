/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputOutput;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Dillon
 */
// Method to read in our text files
public class ReadDataFile implements IReadDataFile {
    
    // instance variables
    private Scanner inputFile;
    private String dataFileName;
    private ArrayList<String> data;
    
    public ReadDataFile(String fileName){
        
        dataFileName = fileName;
        data = new ArrayList<String>();
    }
    
    @Override
    public void populateData(){
        
        try{
            
            URL url = getClass().getResource(dataFileName);
            File file = new File(url.toURI());
            
            inputFile = new Scanner(file);
            
            while(inputFile.hasNext()){
                
                data.add(inputFile.next());
            }
        }
        catch(IOException | URISyntaxException ex){
            
            System.out.println(ex.toString());
            ex.printStackTrace();
        }
        finally{
            
            if(inputFile != null)
                inputFile.close();
        }
    }
    
    // @return the data
    public ArrayList<String> getData(){
        return data;
    }
    
}
