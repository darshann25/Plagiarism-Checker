package plagiarismchecker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static plagiarismchecker.synonymTable.createSynonymTable;
import static plagiarismchecker.processFile.simplifyFile;
import static plagiarismchecker.processFile.checkFile;

/**
 *
 * @author darshanpatel
 */
public class plagiarismChecker {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        if(args.length < 3 || args.length > 4){
            printIntroduction();
            return;
        }
        
        final String SYNONYM_FILE = args[0];
        final String INPUT_FILE1 = args[1];
        final String INPUT_FILE2 = args[2];
        int NTUPLE = 3; // DEFAULT VALUE
        if(args.length == 4 && Integer.parseInt(args[3]) > 0) NTUPLE = Integer.parseInt(args[3]);
        

        System.out.println("\nBegin execution...");
        double result = calculatePlagiarismScore(INPUT_FILE1, INPUT_FILE2, SYNONYM_FILE, NTUPLE);
        System.out.println("Plagiarism Score = " + new DecimalFormat("#0.00").format(result) + "%");
        System.out.println("Execution Completed!");
        
    }
    
    
    public static void printIntroduction(){
        	final String PRODUCT = "PRODUCT:\n\tPlagiarism Checker";
            final String USAGE = "EXECUTE:\n\tjava -jar execute.java synonymsFile inputFile1 inputFile2 [tupleSize]";
            final String DESCRIP = "EXPLANATION:\n\tThis project is used to compute the degree of plagiarism between two text files using synonym matching and fixed tuple length";
            final String ARGS0 = "\tsynonymsFile:\tGroups of synonyms contained on separate lines (*.txt)";
            final String ARGS1 = "\tinputFile1:\tTextfile that will be checked for plagiarism (*.txt)";
            final String ARGS2 = "\tinputFile2:\tTextfile that will be checked for plagiarism (*.txt)";
            final String ARGS3 = "\ttupleSize:\tSize of the tuple used for comparison (Optional)";

            String[] intro = {PRODUCT, USAGE, DESCRIP, ARGS0, ARGS1, ARGS2, ARGS3};

            for (String i : intro) {
                System.out.println(i);
            }
    }
    
    /**
     * @param  file1
     * @param  file2
     * @param  syn
     * @param  ntuple
     * @return plagiarismScore
     */
    public static double calculatePlagiarismScore(String file1, String file2, String syn, int ntuple){
    	/* calculatePlagiarismScore(...) performs following steps: 
         * 1. Prints the synonyms and the files being processed 
         * 2. Creates a HashMap of all the tuples in file2
         * 3. Checks the matches for all tuples in file1 in the HashMap
         * 4. Prints the score (score = (matches in file1 and file2)/(tuples in file1))%
         */
    	
    	HashMap<String, String> synonymSet; 
        HashMap<String, ArrayList<NTuple>> simplifiedMap;
        double result = 0;

        
        try{
        	printFile(syn);
        	printFile(file1);
        	printFile(file2);
            synonymSet = createSynonymTable(syn);
            simplifiedMap = simplifyFile(file2, ntuple, synonymSet);
            result = checkFile(file1, ntuple, synonymSet, simplifiedMap);
        } catch (FileNotFoundException ex) {
        	System.out.println("Execution Failed. :(\nCheck file location");
            Logger.getLogger(processFile.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        } catch (IOException ex) {
        	System.out.println("Execution Failed. :(");
        	Logger.getLogger(processFile.class.getName()).log(Level.SEVERE, null, ex);
        	System.exit(0);
        }
        
        return result;
    }
    
    /**
     * @param  file1
     * @param  file2
     * @param  syn
     * @param  ntuple
     * @return plagiarismScore
     */
    public static double calculatePlagiarismScore(ArrayList<String> file1, ArrayList<String> file2, ArrayList<String> syn, int ntuple){
    	/* calculatePlagiarismScore(...) performs following steps: 
         * 1. Prints the synonyms and the files being processed 
         * 2. Creates a HashMap of all the tuples in file2
         * 3. Checks the matches for all tuples in file1 in the HashMap
         * 4. Prints the score (score = (matches in file1 and file2)/(tuples in file1))%
         */
    	
    	HashMap<String, String> synonymSet; 
        HashMap<String, ArrayList<NTuple>> simplifiedMap;
        double result = 0;

        
        try{
            synonymSet = createSynonymTable(syn);
            simplifiedMap = simplifyFile(file2, ntuple, synonymSet);
            result = checkFile(file1, ntuple, synonymSet, simplifiedMap);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(processFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(processFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    /**
     * @param  file
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException;
     */
    public static void printFile(String file) throws FileNotFoundException, IOException{
    	BufferedReader reader = new BufferedReader(new FileReader(file));
    	
    	String line = "";  	
    	
    	System.out.println(file + ":");
    	while((line = reader.readLine()) != null){
    		System.out.println(line);
    	}
    	System.out.println();
    	reader.close();
    }
    
    /**
     * @param  file
     */
    public static void printFile(ArrayList<String> syn, ArrayList<String> file1, ArrayList<String> file2) {
    	String line = "";  	
    	
    	//print the synonyms
    	System.out.println("Synonyms:");
    	for(int i = 0; i < syn.size(); i++){
    		line = syn.get(i);
    		System.out.println(line);
    	}
    	System.out.println();
    	
    	//print inputFile1
    	System.out.println("InputFile1:");
    	for(int i = 0; i < file1.size(); i++){
    		line = file1.get(i);
    		System.out.println(line);
    	}
    	System.out.println();
    	
    	//print inputFile2
    	System.out.println("InputFile2:");
    	for(int i = 0; i < file2.size(); i++){
    		line = file2.get(i);
    		System.out.println(line);
    	}
    	System.out.println();
    	
    	
    }
    
}

