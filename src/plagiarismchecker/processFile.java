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
public class processFile {
    
    /**
     * @param  file
     * @param  tupleSize
     * @param  synonymSet
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException;
     * @return simplifiedMap
     */
    public static HashMap<String, ArrayList<NTuple>> simplifyFile(String file, int tupleSize, 
            HashMap<String, String> synonymSet) throws FileNotFoundException, IOException{
        /* simplifyFile(...) performs following steps: 
        * 1. Assess input file for tuples 
        * 2. Search for synonyms and modify tuple
        * 3. Place modify tuple in HashMap with first word as key
        */
        
        HashMap<String, ArrayList<NTuple>> simplifiedMap = new HashMap<String, ArrayList<NTuple>>(); 
        BufferedReader input;
        
        NTuple buffer;
        String line;
        String space = " ";
        StringBuffer word;
        int counter;
        int tupleCount = 0;
        
        input = new BufferedReader(new FileReader(file));

        while((line = input.readLine()) != null){
            word = new StringBuffer();  //use of StringBuffer to avoid concatenation
            buffer = new NTuple(tupleSize);
            counter = 0;

            StringBuffer lineBuffer = new StringBuffer();
            lineBuffer.append(line);
            lineBuffer.append(space); //add buffer space at the end of last word
            lineBuffer.append(space); //add buffer space at the end of line
            line = lineBuffer.toString();

            for(int i = 0; i < line.length(); i++){
                //check if char is alphanumeric or space
                if(Character.isAlphabetic(line.charAt(i)) || Character.isDigit(line.charAt(i)) 
                        || Character.isSpaceChar(line.charAt(i))){
                    
                    if(!Character.isSpaceChar(line.charAt(i)) && counter < tupleSize){
                        word.append(Character.toLowerCase(line.charAt(i)));
                    } else {
                        //check for synonym replacement
                        String temp = word.toString();
                        if(synonymSet.containsKey(temp)){
                            word = new StringBuffer();
                            word.append(synonymSet.get(temp));
                        }

                        if(counter < tupleSize){
                            buffer.add(word.toString());
                            counter++;

                        } else {
                                //put NTuple inside HashMap
	                        	ArrayList<NTuple> set;  //use of ArrayList to keep track of duplicates in HashMap
	                            String firstWord = buffer.peek();   
	                            if(!simplifiedMap.containsKey(firstWord)){
	                                set = new ArrayList<NTuple>();
	                                simplifiedMap.put(firstWord, set);
	                            }
	                            set = simplifiedMap.get(firstWord);
	                            NTuple checkBuffer;
	                            boolean tupleExists = false;
	                            for(int n1 = 0; n1 < set.size() ; n1++){
	                            	checkBuffer = set.get(n1);
	                            	if(checkBuffer.isEqual(buffer)) tupleExists = true;
	                            }
	                            NTuple newBuffer = buffer;
	                            if(!tupleExists){
	                                set.add(newBuffer);
	                                simplifiedMap.put(firstWord, set);
	                            }
	                            tupleCount++;
	                            buffer = newBuffer.removeFirst();
	                            counter--;
	                            if(i + 1 < line.length()) i--;
                        }
                        word = new StringBuffer();
                    }
                }
            }    
        }
        input.close();
        System.out.println("Number of tuples in " + file + " : " + tupleCount);
        return simplifiedMap;
    }
 
    /**
     * @param  file
     * @param  tupleSize
     * @param  synonymSet
     * @param  simplifiedMap
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException;
     * @return score
     */
    public static double checkFile(String file, int tupleSize, HashMap<String, String> synonymSet, 
            HashMap<String, ArrayList<NTuple>> simplifiedMap) throws FileNotFoundException, IOException{
        /* checkFile(...) performs following steps: 
        * 1. Assess input file for tuples 
        * 2. Search for synonyms and modify tuple
        * 3. Search HashMap for matches and update count
        */
        
        BufferedReader input;
        
        NTuple buffer;
        String line;
        String space = " ";
        StringBuffer word;
        int counter;
        double flags = 0;
        double tupleCount = 0;
        double score;
        
        input = new BufferedReader(new FileReader(file));

        while((line = input.readLine()) != null){
            word = new StringBuffer();
            buffer = new NTuple(tupleSize);
            counter = 0;

            StringBuffer lineBuffer = new StringBuffer();
            lineBuffer.append(line);
            lineBuffer.append(space); //add buffer space at the end of last word
            lineBuffer.append(space); //add buffer space at the end of line
            line = lineBuffer.toString();

            for(int i = 0; i < line.length(); i++){
                if(Character.isAlphabetic(line.charAt(i)) || Character.isDigit(line.charAt(i)) 
                        || Character.isSpaceChar(line.charAt(i))){

                    if(!Character.isSpaceChar(line.charAt(i)) && counter < tupleSize){
                        word.append(Character.toLowerCase(line.charAt(i)));
                    } else {
                        String temp = word.toString();
                        if(synonymSet.containsKey(temp)){
                            word = new StringBuffer();
                            word.append(synonymSet.get(temp));
                        }

                        if(counter < tupleSize){
                            buffer.add(word.toString());
                            counter++;

                        } else {
                            //check if NTuple exists in HashMap
                            if(simplifiedMap.containsKey(buffer.peek())){
                                ArrayList<NTuple> set = simplifiedMap.get(buffer.peek());
                                NTuple helper;
                                for(int n = 0; n < set.size(); n++){
                                    helper = set.get(n);
                                    if(helper.isEqual(buffer)) {
                                    	flags++;  //match found, so update flag
                                    	break;
                                    }
                                }
                            }
                            tupleCount++;
                            buffer = buffer.removeFirst();
                            counter--;
                            if(i + 1 < line.length()) i--;                          
                        }
                        word = new StringBuffer();
                    }
                }    
            }
        }       
        input.close();
        score = (flags / tupleCount) * 100;
        if(tupleCount == 0) score = 0;
        System.out.println("Number of tuples in " + file + " : " + (int) tupleCount);
        System.out.println("Number of matching tuples" + " : " + (int) flags);
        System.out.println();
        return score;
    }
    
    
    
    /**
     * @param  webData
     * @param  tupleSize
     * @param  synonymSet
     * @return simplifiedMap
     */
    public static HashMap<String, ArrayList<NTuple>> simplifyFile(ArrayList<String> webData, int tupleSize, 
            HashMap<String, String> synonymSet) {
        /* simplifyFile(...) performs following steps: 
        * 1. Assess input file for tuples 
        * 2. Search for synonyms and modify tuple
        * 3. Place modify tuple in HashMap with first word as key
        */
        
        HashMap<String, ArrayList<NTuple>> simplifiedMap = new HashMap<String, ArrayList<NTuple>>(); 
        
        NTuple buffer;
        String line;
        String space = " ";
        StringBuffer word;
        int counter;
        int tupleCount = 0;

        for(int n = 0; n < webData.size(); n++){
            line = webData.get(n);
            word = new StringBuffer();  //use of StringBuffer to avoid concatenation
            buffer = new NTuple(tupleSize);
            counter = 0;

            StringBuffer lineBuffer = new StringBuffer();
            lineBuffer.append(line);
            lineBuffer.append(space); //add buffer space at the end of last word
            lineBuffer.append(space); //add buffer space at the end of line
            line = lineBuffer.toString();

            for(int i = 0; i < line.length(); i++){
                //check if char is aplhanumeric or space
                if(Character.isAlphabetic(line.charAt(i)) || Character.isDigit(line.charAt(i)) 
                        || Character.isSpaceChar(line.charAt(i))){
                    
                    if(!Character.isSpaceChar(line.charAt(i)) && counter < tupleSize){
                        word.append(Character.toLowerCase(line.charAt(i)));
                    } else {
                        //check for synonym replacement
                        String temp = word.toString();
                        if(synonymSet.containsKey(temp)){
                            word = new StringBuffer();
                            word.append(synonymSet.get(temp));
                        }

                        if(counter < tupleSize){
                            buffer.add(word.toString());
                            counter++;

                        } else {
                                //put NTuple inside HashMap
                                ArrayList<NTuple> set;  //use of ArrayList to keep track of duplicates in HashMap
                                String firstWord = buffer.peek();   
                                if(!simplifiedMap.containsKey(firstWord)){
                                    set = new ArrayList<NTuple>();
                                    simplifiedMap.put(firstWord, set);
                                }
                                set = simplifiedMap.get(firstWord);
                                NTuple checkBuffer;
                                boolean tupleExists = false;
                                for(int n1 = 0; n1 < set.size() ; n1++){
                                	checkBuffer = set.get(n1);
                                	if(checkBuffer.isEqual(buffer)) tupleExists = true;
                                }
                                NTuple newBuffer = buffer;
                                if(!tupleExists){
	                                set.add(newBuffer);
	                                simplifiedMap.put(firstWord, set);
                                }
                                tupleCount++;
                                buffer = newBuffer.removeFirst();
                                counter--;
                                if(i + 1 < line.length()) i--;
                        }
                        word = new StringBuffer();
                    }
                }
            }    
        }
        System.out.println("Number of tuples in file" + " : " + tupleCount);
        return simplifiedMap;
    }
 
    /**
     * @param  webData
     * @param  tupleSize
     * @param  synonymSet
     * @param  simplifiedMap
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException;
     * @return score
     */
    public static double checkFile(ArrayList<String> webData, int tupleSize, HashMap<String, String> synonymSet, 
            HashMap<String, ArrayList<NTuple>> simplifiedMap) throws FileNotFoundException, IOException{
        /* checkFile(...) performs following steps: 
        * 1. Assess input file for tuples 
        * 2. Search for synonyms and modify tuple
        * 3. Search HashMap for matches and update count
        */
        
        NTuple buffer;
        String line;
        String space = " ";
        StringBuffer word;
        int counter;
        double flags = 0;
        double tupleCount = 0;
        double score;
        
        for(int m = 0; m < webData.size(); m++){
            line = webData.get(m);
            word = new StringBuffer();
            buffer = new NTuple(tupleSize);
            counter = 0;

            StringBuffer lineBuffer = new StringBuffer();
            lineBuffer.append(line);
            lineBuffer.append(space); //add buffer space at the end of last word
            lineBuffer.append(space); //add buffer space at the end of line
            line = lineBuffer.toString();

            for(int i = 0; i < line.length(); i++){
                if(Character.isAlphabetic(line.charAt(i)) || Character.isDigit(line.charAt(i)) 
                        || Character.isSpaceChar(line.charAt(i))){

                    if(!Character.isSpaceChar(line.charAt(i)) && counter < tupleSize){
                        word.append(Character.toLowerCase(line.charAt(i)));
                    } else {
                        String temp = word.toString();
                        if(synonymSet.containsKey(temp)){
                            word = new StringBuffer();
                            word.append(synonymSet.get(temp));
                        }

                        if(counter < tupleSize){
                            buffer.add(word.toString());
                            counter++;

                        } else {
                            //check if NTuple exists in HashMap
                            if(simplifiedMap.containsKey(buffer.peek())){
                                ArrayList<NTuple> set = simplifiedMap.get(buffer.peek());
                                NTuple helper;
                                for(int n = 0; n < set.size(); n++){
                                    helper = set.get(n);
                                    if(helper.isEqual(buffer)) {
                                    	flags++;  //match found, so update flag
                                    	break;
                                    }
                                }
                            }
                            tupleCount++;
                            buffer = buffer.removeFirst();
                            counter--;
                            if(i + 1 < line.length()) i--;                          
                        }
                        word = new StringBuffer();
                    }
                }    
            }
        }        
        score = (flags / tupleCount) * 100;
        if(tupleCount == 0) score = 0;
        System.out.println("Number of tuples in file" + " : " + (int) tupleCount);
        System.out.println("Number of matching tuples" + " : " + (int) flags);
        return score;
    }
}

