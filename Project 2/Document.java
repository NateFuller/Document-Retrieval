// Original work of Nathan Fuller

public class Document implements DocumentInterface{
	private String completeLine;
	private String id, title, text;
	private String[] docArg;
	WordList words;
		
	public Document(String docid, String theTitle, String theText){
		words = new WordList();
		id = docid;
		title = theTitle;
		text = theText;
	}
	
	public Document(String input){
		completeLine = input.trim();
		words = new WordList();
		docArg = completeLine.split("\"",3);
		id = docArg[0];
		title = docArg[1];
		text = docArg[2].trim();
	}
	
	public String getTitle() {
		return title.trim();
	}

	public String getDocid() {
		return id.trim();
	}

	public WordList getWords() {
		String[] array = text.split(" ");
		if(array.length>0){
			for(String word : array)
				words.add(word);
		}
		else
			words.add(text);
		return words;
	}

	public int numWords() {
		int sizeOf = words.size();
		return sizeOf;
	}

}
