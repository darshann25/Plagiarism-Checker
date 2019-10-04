package plagiarismchecker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author darshanpatel
 */
public class synonymTable {
    
    /**
     * @param  fileName
     * @throws java.io.FileNotFoundException;
     * @throws java.io.FileReader;
     * @return synTable
     */
    public static HashMap<String, String> createSynonymTable(String fileName) 
            throws FileNotFoundException, IOException{
        /* createSynonymTable(...) performs following steps: 
        * 1. Put every word in synonym file to HashMap as key 
        *    and value as first word in line
        * 2. Return synTable
        */
        
        HashMap<String, String> synTable = new HashMap<String, String>();
        BufferedReader synonym;
        String line;
        String space = " ";
        StringBuffer commonWord;
        StringBuffer word; 
        boolean firstWord;
        
        synonym = new BufferedReader(new FileReader(fileName));

        while((line = synonym.readLine()) != null){
            commonWord = new StringBuffer();
            word = new StringBuffer();
            firstWord = true;

            for(int i = 0; i < line.length(); i++){
                if(line.charAt(i) != space.charAt(0)){
                    if(firstWord){
                        commonWord.append(Character.toLowerCase(line.charAt(i)));  //store first word in line
                    }
                    word.append(Character.toLowerCase(line.charAt(i)));
                } else {
                    synTable.put(word.toString(), commonWord.toString());
                    firstWord = false;
                    word = new StringBuffer();
                }
            }
            synTable.put(word.toString(), commonWord.toString());

        }
        synonym.close();
        return synTable;
    }
    
        /**
     * @param  webSynonyms 
     * @throws java.io.FileNotFoundException;
     * @throws java.io.FileReader;
     * @return synTable
     */
    public static HashMap<String, String> createSynonymTable(ArrayList<String> webSynonyms) 
            throws FileNotFoundException, IOException{
        /* createSynonymTable(...) performs following steps: 
        * 1. Put every word in synonym file to HashMap as key 
        *    and value as first word in line
        * 2. Return synTable
        */
        
        HashMap<String, String> synTable = new HashMap<String, String>();
        String line;
        String space = " ";
        StringBuffer commonWord;
        StringBuffer word; 
        boolean firstWord;
        
        for(int m = 0; m < webSynonyms.size(); m++){
            line = webSynonyms.get(m);
            commonWord = new StringBuffer();
            word = new StringBuffer();
            firstWord = true;

            for(int i = 0; i < line.length(); i++){
                if(line.charAt(i) != space.charAt(0)){
                    if(firstWord){
                        commonWord.append(Character.toLowerCase(line.charAt(i)));  //store first word in line
                    }
                    word.append(Character.toLowerCase(line.charAt(i)));
                } else {
                    synTable.put(word.toString(), commonWord.toString());
                    firstWord = false;
                    word = new StringBuffer();
                }
            }
            synTable.put(word.toString(), commonWord.toString());

        }
        return synTable;
    }
}

