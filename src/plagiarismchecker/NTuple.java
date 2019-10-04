package plagiarismchecker;

import java.util.LinkedList;

/**
 *
 * @author darshanpatel
 */

public class NTuple {
    
    LinkedList<String> tupleList;
    int maxLength = 0;
    
    public NTuple(int l){
        tupleList = new LinkedList<String>();
        maxLength = l;
    }
    
    public NTuple(){
        tupleList = new LinkedList<String>();
        maxLength = 3;
    }
       
    public boolean add(String word){
        if(tupleList.size() < maxLength) {
            tupleList.add(word);
            return true;
        } else {
            return false;
        }
    }    
    
    public String peek(){
        if(!tupleList.isEmpty()) return tupleList.peek();
        return null;        
    }
    
    public int getSize(){
        return tupleList.size();
    }
    
    public String get(int key){
        return tupleList.get(key);
    }
    
    public NTuple removeFirst(){
        NTuple buffer = new NTuple(this.maxLength);
        for(int i = 1; i < maxLength; i++){
            buffer.add(tupleList.get(i));
        }
        return buffer;
    }
    
    public boolean isEqual(NTuple that){
        int size = this.getSize();
        if(size != that.getSize()) return false;
        
        for(int i = 0; i < size; i++){
            if(!this.tupleList.get(i).equals(that.tupleList.get(i))) return false;
        }
        return true;
    }
    
    
    
}
