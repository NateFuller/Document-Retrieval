// Nathan Fuller
public class WordList implements WordListInterface{
	
	private Node<String> head,tail;
	private int count = 0;

	public WordList(){
	}
	
	public boolean add(String word){
		Node<String> c = new Node<String>(word.trim());
		Node<String> currNode = head;
		
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
	
	public int size() {
		return count;
	}
	
	public String get(int idx){
		Node<String> currNode = head;
		
		if(currNode == null)
			return "No words present.";
		else if(idx < 0)
			return "ERROR: Invalid index.";
		else if(idx == 0)
			return currNode.getData();
		else{
			int nodeCount = -1;
			while(currNode != tail && nodeCount < idx-1){
				currNode = currNode.getNext();
				nodeCount++;
			}
			if(nodeCount == idx-1){
				return currNode.getData();
			}
			else
				return "ERROR: That index doesn't exist.";
			}
	}
	
	public String toString(){
		Node<String> currNode = head;
		String s = "";
		s += currNode.getData();
		while(currNode != tail){
			currNode = currNode.getNext();
			s += " " + currNode.getData();
		}
		return s;
	}
}
