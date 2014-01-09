import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class ProjectMap<K,V> implements MapInterface<K,V>, Iterable<K> {

	private class Pair<Ke,Va> {
		Ke key;
		Va value;
	}
	
	private Pair<K,V> htable[];
	private int count, capacity;
	private Pair<K,V> DELETED_SLOT;
	
	public ProjectMap() {
		capacity = 11;
		htable = new Pair[capacity];
		count = 0;

		DELETED_SLOT = new Pair<K,V>();
		DELETED_SLOT.key = null;
		DELETED_SLOT.value = null;
	}
	
	// Convert a key into a hash value.  This must return something
	// that is in the range 0..capacity-1 or other parts of the code
	// will crash.
	private int h(K key) {
	    int hashval = key.hashCode() % capacity;;
	    
	    // Should always be okay, but Java has a weird implementation
	    // of modulus that might right a negative number.
	    if (hashval<0)
		return -hashval;
	    else
		return hashval;

	}

	@SuppressWarnings("unchecked") // for the cast of htable
	private void expandTable() {
		Pair<K,V>[] oldTable = htable;
		
		capacity *= 2;
		htable = new Pair[capacity];
		
		for (Pair<K,V> p: oldTable) {
			if (p==DELETED_SLOT || p==null) continue;
			int elt = findSpot(p.key);
			htable[elt] = p;
		}
	}
	
	
	// Finds the key value
	private int find(K key) {
		int hkey = h(key);
		
		// Loop until we find an empty slot (which means the key isn't there)
		// or a match (which means we're done).  A deleted slot doesn't count
		// as empty, so we have to continue from there.
		while (htable[hkey] != null
				&& (htable[hkey] == DELETED_SLOT
					|| !htable[hkey].key.equals(key))) {
			hkey = (hkey+1)%capacity;
		}
		
		if (htable[hkey] != null && htable[hkey] != DELETED_SLOT)
			return hkey;
		else
			return -1;
	}
	
	// Find the first empty slot that can hold this key
	private int findSpot(K key) {
		int hkey = h(key);
		
		// Loop until we find an empty slot or a deleted slot (that we can
		// refill).
		while (htable[hkey] != null
				&& htable[hkey] != DELETED_SLOT) {
			hkey = (hkey+1)%capacity;
		}
		
		return hkey;
	}
	
	public boolean containsKey(K key) {
		return find(key)>=0;
	}

	public V get(K key) {
		int elt = find( key );
		if (elt<0)
			return null;
		else
			return htable[elt].value;
	}

	public boolean isEmpty() {
		return count==0;
	}

	public V put(K key, V value) {
		// really should throw an exception if key is null
		
		int elt = find( (K) key );
		V oldValue;
		Pair<K,V> newPair = new Pair<K,V>();
		newPair.key = key;
		newPair.value = value;
		
		if (elt<0) {
			if (count>capacity/2)
				expandTable();
			oldValue = null;
			elt = findSpot(key);
		}
		else
			oldValue = htable[elt].value;

		htable[elt] = newPair;
		if (oldValue == null) count++;
		return oldValue;
	}

	public V remove(K key) {
		int elt = find( key );
		if (elt<0)
			return null;
		
		V oldValue = htable[elt].value;
		htable[elt] = DELETED_SLOT; // mark as deleted
		count--;
		return oldValue;
	}

	public int size() {
		return count;
	}

	/**
	 * Iterator over the keys in the hash table.  Implements remove, too.
	 * 
	 * @author Sample solution
	 *
	 */
	private class PMIterator implements Iterator<K>{

		int nextElt = 0;
		int numSeen = 0;
		int prevElt = -1;

		public boolean hasNext() {
			return numSeen<count;
		}

		public K next() {
			if (numSeen >= count) throw new NoSuchElementException( "already returned full list" );
			
			while (nextElt<capacity) {
				if (htable[nextElt] != null && htable[nextElt] != DELETED_SLOT) {
					prevElt = nextElt;
					nextElt++;
					numSeen++;
					return htable[prevElt].key;
				}
				nextElt++;
			}
			throw new NoSuchElementException( "no remaining items found" );
		}

		public void remove() {
			if (prevElt>=0) {
				htable[prevElt] = DELETED_SLOT;
				count--;
				numSeen--;    // hack because that's how hasNext checks
				prevElt = -1; // disallow two removes in a row
			}
		}
		
	}
	
	public Iterator<K> iterator() {
		return new PMIterator();
	}

	
}

