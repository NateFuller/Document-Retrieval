// Nathan Fuller
public class Node2<K,V> { 
	
	private K key;
	private V value;
	private Node2<K,V> next; 
	
	public Node2(K k,V v){ 
		key = k; 
		value = v;
		next = null; 
	}
	
	public Node2(){
		key = null;
		value = null;
		next = null;
	}
	
	public void setKey(K k){
		key = k;
	} 
	
	public void setValue(V v){
		value = v;
	}
	
	public K getKey(){
		return key;
	} 
	
	public V getValue(){
		return value;
	}
	
	public void setNext(Node2<K,V> n){
		next = n;
	} 
	
	public Node2<K,V> getNext(){
		return next;
	} 
	
	public String toString(){
		return (String) this.getKey();
	}
	
	public boolean equals( Node2<K,V> node ){
		if( this.key == node.key)
			return true;
		else 
			return false;
	}
}