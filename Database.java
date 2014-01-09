import java.util.*;

// Nathan Fuller
public class Database implements DatabaseInterface{
	
	private ArrayList<Document> docList;
	private ArrayList<String> titles;
	private ArrayList<String> docids;
	private ResultList rList;
	private InvertedFile iFile;
	private InvertedList iList;
	private int count;
	

	// Initialize all of our variables.
	public Database(){
		count = 0;
		titles = new ArrayList<String>();
		docids = new ArrayList<String>();
		iFile = new InvertedFile();
		rList = new ResultList();
		docList = new ArrayList<Document>();
	}
	
	// We add a document by storing the components.
	// For the sake of the project requirements, we also stash the full Document.
	// Note that document number X is stored at index X-1
	public int add(Document d) throws DuplicateDocumentException{
		if(docids.contains(d.getDocid())){
			throw new DuplicateDocumentException( "A document with the same id has already been stored!" );
		}
		int docNum = count+1;
		titles.add(docNum-1, d.getTitle());
		docids.add(docNum-1, d.getDocid());
		iFile.add(docNum-1, d.getWords());
		docList.add(docNum-1, d);
		
		count++;
		
		return docNum;
	}
	
	// Our accession number is the count of documents.
	public int numDocuments(){
		return count;
	}
	
	// We ask the inverted file to tell us the number of words stored.
	// (This is actually something of a lie since duplicated words
	// are ignored at this point.)
	public int numWords(){
		return iFile.numTokens();
	}
	
	// Return a document if the index is in bounds.
	// NOTE: this returns things by an *index*, not by doc number.
	// Indexes are 0-based whereas docids are 1-based, so docidX is at index X-1
	public Document getDocument(int index) {
		if(count == 0 || index < 0 || index >= count){ 
			//The docNumber is not in range of the list.
			return null;
		}
		else
			return docList.get(index);
	}
	
	public int getDocNumber(String docid) {
		if(docids.contains(docid))
			return docids.indexOf(docid) + 1;
		return 0;
	}
	
	
	private InvertedList processTree(ExpTree tree){
		if(tree.isWord())
			iList = new InvertedList(tree.getWord());
		else if (tree.isOperator()){
			if(tree.getValue().equalsIgnoreCase("AND"))
				iList = InvertedList.intersection( processTree(tree.left()), processTree(tree.right()) );
			if(tree.getValue().equalsIgnoreCase("OR"))
				iList = InvertedList.union( processTree(tree.left()), processTree(tree.right()) );
			if(tree.getValue().equalsIgnoreCase("BUTNOT"))
				iList = InvertedList.difference( processTree(tree.left()), processTree(tree.right()) );
		}
		return iList;
		
	}
	
	// Run a search. At this point the query language is a single token which
	// is either a word or a variable name.
	public ResultList runQuery(String query, Assignments varValues) throws SyntaxErrorException{

		ExpTree expTree = QueryParser.go(query);
		
		iList = this.processTree(expTree);
		
		// Have to translate from internal format (docids) to ResultList (Documents)
		ResultList rList = new ResultList();
		for (Integer index: iList){
			rList.add( getDocument(index) );
		}
		return rList;

	}
	
	// A string version of the Database is a summary and a copy of the inverted file.
	public String toString(){
		String s;
		s = "Database containing " + count
				+ " document"
				+ (count==1?"":"s") + "\n";
		s += iFile;
		return s;
	}
}