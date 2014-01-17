// Original work of Nathan Fuller
public class History implements HistoryInterface{
	
	private class Node{
		Command c;
		int v;
		Node next;
		
		Node(Command cmd, int val){
			c = cmd;
			v = val;
			next = null;
		}
		void setNext(Node nextNode){
			next = nextNode;
		}
		Node getNext(){
			return next;
		}
		Command getCommand(){
			return c;
		}
		int getValue(){
			return v;
		}
	}
	
	//For keeping track of the linked list
	private Node head,tail;
	private int count;
	
	//Constructor. No list and nothing stored yet.
	public History(){
		head = null;
		tail = null;
		count = 0;
	}
	
	//Add a (Command, value) pair to the list.
	public void add(Command c){
		add(c,0);
	}
	public void add(Command c, int value) {
		Node n = new Node(c,value);
		if(head == null)
			head = n;
		if (tail!=null)
			tail.setNext(n);
		tail=n;
		count++;
	}

	//Counts
	public int numCommands() {
		return count;
	}
	
	//Get stored information
	public Command pastCommand(int index) {
		if (index<0 || index>=count)
			return null;
		Node n = head;
		for(int i = 0; i<index; i++)
			n = n.getNext();
		return n.getCommand();
	}
	
	public int pastValue(int index) {
		if(index <= 0 || index >= count)
			return 0;
		Node n = head;
		for(int i = 0; i<index; i++)
			n = n.getNext();
		return n.getValue();
	}
	
	public String toString(){
		Node n= head;
		StringBuilder s = new StringBuilder();
		
		while(n != null){
			for (int i=0; i<n.getValue(); i++)
				s.append( "  " );
			s.append(n.getCommand());
			s.append("\n");
			n = n.getNext();
		}
		return s.toString();
	}

}
