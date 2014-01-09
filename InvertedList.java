// Nathan Fuller
import java.util.*;

public class InvertedList implements InvertedListInterface{
	
	private String word;
	private int counter;
	private int temp = -1;
	private ArrayList<Integer> docNums = new ArrayList<Integer>();

	// ITERATOR
	private class MyIterator<T> implements Iterator<T>{
		private int stepper;
		
		private MyIterator(){
			stepper = 0;
		}
		
		public boolean hasNext() {
			return stepper < docNums.size();
		}


		public T next() {
			if(docNums.isEmpty() || stepper >= docNums.size()){
				throw new NoSuchElementException();
			}
			else{
				return (T) docNums.get(stepper++);
			}
		}


		public void remove() {
		}
		
	}
	
	// CLASS METHODS
	public InvertedList(String word){
		this.word = word;
	}
	
	public String getWord() {
		return word;
	}

	public boolean add(int docNumber){
		if(!docNums.contains(docNumber) && docNumber>temp){
			temp = docNumber;
			docNums.add(docNumber);
			counter++;
			return true;
		}
		else
			return false;
		
	}

	public int size() {
		return counter;
	}

	public int getDocNumber(int index) {
		if(index<0 || index>counter-1 || counter==0)
			return -1;
		else
			return docNums.get(index);
	}


	public Iterator<Integer> iterator() {
		MyIterator<Integer> m = new MyIterator<Integer>();
		return m;
	}
	
	public ProjectMap<String,InvertedList> getMap(){
		return null;
	}
	
	
	public static InvertedList intersection( InvertedList a, InvertedList b ){
		ArrayList<Integer> holder = new ArrayList<Integer>();
		InvertedList intersect = new InvertedList( a.word + " AND " + b.word );
		for( int i = 0; i < a.size(); i++ )
			holder.add( a.docNums.get(i) );
		for( int i = 0; i < b.size(); i++ )
			if( holder.contains(b.docNums.get(i)) )
				intersect.add( b.docNums.get(i) );
		return intersect;
	}
    
	public static InvertedList union( InvertedList a, InvertedList b ){// **** MIGHT NEED FIXING ****
		if( a == b || b == null )
			return a;
		if( a == null && b != null )
			return b;
		InvertedList u = new InvertedList( a.word + " OR " + b.word );
		InvertedList listToAddFirst;
		InvertedList second;
		if( a.size() >= b.size()){
			listToAddFirst = a;
			second = b;
		}
		else{
			listToAddFirst = b;
			second = a;
		}
		for(int i = 0; i < listToAddFirst.size(); i++ )
			u.add(listToAddFirst.getDocNumber(i));
		for( int i = 0; i < second.size(); i++ ){
			if( !u.docNums.contains(second.getDocNumber(i)) );
				u.add(second.getDocNumber(i));
		}
		return u;
			
	}
    
	public static InvertedList difference( InvertedList a, InvertedList b ){
		ArrayList<Integer> holder = new ArrayList<Integer>();
		InvertedList diff = new InvertedList( a.word + " BUTNOT " + b.word );
		for( int i = 0; i < b.size(); i++ )
			holder.add( b.docNums.get(i) );
		for( int i = 0; i < a.size(); i++ )
			if( !holder.contains(a.docNums.get(i)) )
				diff.add( a.docNums.get(i) );
		return diff;
	}
	
	public String toString(){
		String s = this.getWord() + ": ";
		int step = 0;
		while( step < this.size() ){
			s += this.getDocNumber(step) + " ";
			step++;
		}
		return s;
	}
}
