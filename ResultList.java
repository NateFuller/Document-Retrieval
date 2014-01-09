import java.util.*;

// Nathan Fuller
public class ResultList implements ResultListInterface{
	
	private Node<Document> head,tail;
	int count = 0;
	
	private class MyIterator<T> implements Iterator<T>{
		Node<Document> n = (Node<Document>) head;
		
		public boolean hasNext() {
			return(n!=null);
		}

		public T next() {
			if(n==null)
				 throw new NoSuchElementException();
			else{
				T nextItem = (T) n.getData();
				n = n.getNext();
				return nextItem;
			}
		}

		public void remove() {
		}
	}
	
	public ResultList(){
	}
	
	public boolean add(Document d){
		Node<Document> c = new Node<Document>(d);
		Node<Document> currNode = head;
		
		if(currNode==null){
			count = 1;
			head = c;
			tail = c;
			c.setNext(null);
		}
		
		else{
			count = 1;
			while(currNode != tail){
				currNode = currNode.getNext();
				count++;
			}
			currNode.setNext(c);
			count++;
			tail = c;
		}
		return true;
	}
	
	public int size(){
		return count;
	}
	
	public String toString(){
		String s = "ResultList with size: " + this.size();
		return s;
	}
	
	public Iterator iterator() {
		MyIterator<ResultList> mI = new MyIterator<ResultList>();
		return mI;
	}
	
}
