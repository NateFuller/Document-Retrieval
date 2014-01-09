// Original work by Nathan Fuller

public class Node { 
	
	private String data; 
	private Node next; 
	
	public Node(String d){ 
		data = d; 
		next = null; 
	}
	
	public Node(String d, Node n){ 
		data = d; 
		next = n; 
	}
	
	public void setData(String d){
		data = d;
	} 
	
	public String getData(){
		return data;
	} 
	
	public void setNext(Node n){
		next = n;
	} 
	
	public Node getNext(){
		return next;
	} 
	
}