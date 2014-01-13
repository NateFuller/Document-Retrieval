// Work of Prof. James Allan

public class InvertedFile implements InvertedFileInterface{
	
	
	private int lastDocNumber = 0;
	private ProjectMap<String,InvertedList> allLists;
	
	public InvertedFile(){
		allLists = new ProjectMap<String,InvertedList>();
	}
	
	// For each word in the passed WordList, update the corresponding
	// InvertedList to include the passed document number.
	public boolean add(int docNum, WordList wList){
		
		// We require that the document number increase each time
		if (docNum <= lastDocNumber && !allLists.isEmpty())
			return false;
		
		for(int i = 0; i < wList.size(); i++){
			// Get the list.
			InvertedList iList = allLists.get(wList.get(i));
			
			//If it's not there, create and store an empty one
			if (iList == null){
				iList = new InvertedList(wList.get(i));
				allLists.put(wList.get(i),iList);
			}
			
			// Update in place. Since we got a pointer to the list (we hope)
			// we can just update it without replacing it.
			iList.add(docNum);
		}
		lastDocNumber = docNum;
		return true;
	}
	
	// Return an inverted list by key.
	// If not there, pass along the returned null
	public InvertedList getInvertedList( String word ){
		if (word==null)
			return null;
		return allLists.get(word);
	}
	
	// How many inverted lists are stored here?
	public int numLists(){
		return allLists.size();
	}
	
	// Total number of tokens stored across all lists.
	// Note that this could have been kept as we went along rather than
	// being calculated on the fly.
	public int numTokens(){
		int sum = 0;
		for(String key: allLists)
			sum += allLists.get(key).size();
		return sum;
	}
	
	// Display an inverted file with a summary and then a list of its lists
	public String toString(){
		String s;
		
		s = "Inverted file with " + allLists.size()
				+ " word" + (allLists.size()==1 ? "" : "s")
				+ " stored:\n";
		for (String key: allLists)
			s += allLists.get(key) + "\n";
		
		return s;
	}
	
	public ProjectMap<String,InvertedList> getMap(){
		return allLists;
	}
}
