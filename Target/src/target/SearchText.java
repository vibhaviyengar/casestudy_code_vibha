/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package target;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Vibha
 */
public class SearchText {

    /**
     * @param args the command line arguments
     */
    FileReader in = null;
    BufferedReader br = null;
    HashMap<String,Integer> wordList = null;
    
    int stringMatch(String searchTerm, String filePath){
        int count=0;
        String line=null;
        //ArrayList<String> linewords = new ArrayList<String>();
         //= new String[200];
        try{
            
            in = new FileReader(filePath);
            br = new BufferedReader(in);
            while((line=br.readLine())!=null){
                if(line.contains(searchTerm)){
                    String wordsInLine[] = line.split(searchTerm);//= new String[200];
                    count = wordsInLine.length - 1;
                    //for(int i=0; i<wordsInLine.length;i++){ 
                      //  System.out.println(wordsInLine[i]+wordsInLine.length);
                    //}                
                }

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SearchText.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SearchText.class.getName()).log(Level.SEVERE, null, ex);
        }
   
        return count;
    }
    
    int regMatch(String searchTerm, String filePath){
        int count=0;
        String line=null;
        Pattern searchTermPattern = Pattern.compile(searchTerm);
        Matcher m = null;
        try{
            
            in = new FileReader(filePath);
            br = new BufferedReader(in);
            while((line=br.readLine())!=null){
                m = searchTermPattern.matcher(line);
                int found =0;
                while(m.find(found)){
                    //System.out.println(m.start());
                    count++;
                    found = m.start() + 1;
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SearchText.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SearchText.class.getName()).log(Level.SEVERE, null, ex);
        }
   
        return count;
    }
    
    void preCompile(String filePath){
        String line=null;
        wordList = new HashMap<String, Integer>();
        try{
            in = new FileReader(filePath);
            br = new BufferedReader(in);
            while((line=br.readLine())!=null){
                String wordsInLine[] = line.split("\\W+");
                for(int i=0;i<wordsInLine.length;i++){
                    if(wordList.containsKey(wordsInLine[i])){
                        int existing = wordList.get(wordsInLine[i]);
                        wordList.replace(wordsInLine[i].toLowerCase(),existing++);
                    }
                    else
                    {
                        wordList.put(wordsInLine[i].toLowerCase(), 1);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SearchText.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SearchText.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    int preCompiledMatch(String searchTerm){
        int count=0;
        
        if(wordList.get(searchTerm.toLowerCase())!=null){
            count=wordList.get(searchTerm.toLowerCase());
        };
        
        return count;
    }
    
    
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        SearchText obj =new SearchText();     
        int count = -1;
        String filePath = "src/target/warp_drive.txt";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the phrase to be searched.");
        String searchTerm = in.readLine();
        long startTime=0, endTime=0;
        System.out.println("Enter the method number to search: \n1. String Match\t 2. Regular Expression Match\t 3. Pre-compiled File Matching");
        int methodType = Integer.parseInt(in.readLine());
        //System.out.println(methodType);
        switch(methodType){
            case 1: startTime=System.currentTimeMillis(); 
                    count = obj.stringMatch(searchTerm,filePath); 
                    endTime= System.currentTimeMillis();
                    break;
            case 2: startTime=System.currentTimeMillis();
                    count = obj.regMatch(searchTerm,filePath); 
                    endTime= System.currentTimeMillis();
                    break;
            case 3: obj.preCompile(filePath);
                    startTime=System.currentTimeMillis();
                    count = obj.preCompiledMatch(searchTerm);
                    endTime= System.currentTimeMillis();
                    break;
            default: System.out.println("Invalid search method input");
        }
        
        if(count!=-1){
            System.out.println("The number of occurences is : "+ count);
            System.out.println("Time taken for search : "+ (endTime-startTime) + " ms");
        }
        
    }
    
}
