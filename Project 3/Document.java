import java.util.StringTokenizer;

public class Document implements DocumentInterface{
	private String title;
	private String docid;
	private WordList words;
	
	//Create a new document. We tokenize the words.
	public Document(String docid, String title, String text){
		if (docid==null || title==null || text==null){
			this.docid = null;
			this.title = "Null values passed in to Document constructor";
		}
		else
			makeDocument(docid,title,text);
	}
	
	//Parse the line and then create things
	public Document(String wholeLine){
		String[] docParts = wholeLine.split("\"",3);
		if(docParts.length != 3){
			this.docid = null; //signals an error
			this.words = null;
			if(docParts.length < 3)
				this.title = "Need two quotation marks";
			else
				this.title = "Unexplained error";
		}
		else
			makeDocument(docParts[0].trim(),docParts[1].trim(),docParts[2].trim());
	}
	
	//Given the three values, set us up.
	private void makeDocument(String docid, String title, String text){
		this.title = title;
		this.docid = docid;
		
		//Words are whitespace separated
		words = new WordList();
		StringTokenizer st = new StringTokenizer(text);
		while(st.hasMoreTokens())
			words.add(st.nextToken());
	}
	
	//Accessor functions
	public String getTitle() {
		return title;
	}

	public String getDocid() {
		return docid;
	}

	public WordList getWords() {
		return words;
	}

	public int numWords() {
		return words.size();
	}
	
	//Print format is "DOCID (title): words"
	public String toString(){
		return docid + " (" + title + ") " + words;
	}

}
