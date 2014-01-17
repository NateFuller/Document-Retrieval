import java.util.*;

// Nathan Fuller
public class ExpTree implements ExpressionTreeInterface {
	
	ExpTree root;
	ExpTree leftNode;
	ExpTree rightNode;
	ArrayList<String> values = new ArrayList<String>();
	String word;

	public ExpTree( String word ){
		this.word = word.trim();
		leftNode = null;
		rightNode = null;
		root = this;
	}
	
	public ExpTree( String operator, ExpTree leftNode, ExpTree rightNode ){
		word = operator.trim();
		this.leftNode = leftNode;
		this.rightNode = rightNode;
		root = this;
	}
	
	public boolean isOperator() {
		return( word.equalsIgnoreCase("AND") || word.equalsIgnoreCase("OR") || word.equalsIgnoreCase("BUTNOT") );
	}

	public boolean isWord() {
		return( !this.isOperator() && !this.isVariable() );
	}

	public boolean isVariable() {
		char firstChar = word.charAt(0);
		char lastChar = word.charAt(word.length()-1);
		return( firstChar == '[' && lastChar == ']' );
	}

	public String getOperator() {
		if( this.isOperator() )
			return word;
		return null;
	}

	public String getWord() {
		if( this.isWord() )
			return word;
		return null;
	}

	public String getVariable() {
		if( this.isVariable() )
			return word;
		return null;
	}

	public String getValue() {
		if( this == null )
			return null;
		return word;
	}

	public ExpTree left() {
		if( leftNode == null )
			return null;
		return leftNode;
	}

	public ExpTree right() {
		if( rightNode == null )
			return null;
		return rightNode;
	}
	
	private ArrayList<String> store(ExpTree tree){
		if(tree.left() != null)
			store(tree.left());
		values.add(tree.getValue());
		if(tree.right() != null)
			store(tree.right());
		return values;
	}
	
	public String toString(){
		String s = "";
		this.store(root);
		for(String value : values)
			s += value + " ";
		return s.trim();
	}
}
