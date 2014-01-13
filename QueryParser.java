// Imports used only for the commented-out main() method
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

// Work of Prof. James Allan
/******
 * V1.0
 * 
 * Parse a query into an ExpTree.  For P6, the ExpTree is provided by you, the
 * student.  The query language is Boolean and recognizes the operators AND, OR,
 * and BUTNOT (or +, |, and -) as well as parentheses.  Multiple words in a row
 * without an operator are treated as if they were joined by AND.  Operator
 * precedence is left-right, so A&B|C == (A&B)|C but C|A&B == (C|A)&B.
 * 
 ******/
public class QueryParser {

	/*****
	 * Public method that parses a query into an expression tree.
	 * 
	 * @param query is the query string, with Boolean operators allowed.
	 * @return is the ExpTree that represents the query.
	 * @throws SyntaxErrorException in the case of any syntax error.
	 */
	public static ExpTree go( String query ) throws SyntaxErrorException {
		
		if (query==null || query.equals(""))
			throw new SyntaxErrorException( "Blank query cannot be processed" );
		QueryTokens qTokens = new QueryTokens( query );
		return myParser( qTokens, "" );

	}
	
	/*****
	 * Private recurse method that processes a query into an expression tree.
	 * 
	 * @param query - is the set of query tokens; it will be modified so
	 * that portions of the query used by this level of recursion are not included.
	 * 
	 * @param endToken - is a special token that signals the end of this level
	 * of recursion.  Typically this is a close paren when the method is called
	 * with an end recursion.  The empty string can be used to make the end of 
	 * string the only way to exit.
	 * 
	 * @return is the ExpTree that represents this level of the query.
	 * 
	 * @throws SyntaxErrorException
	 */
	private static ExpTree myParser( QueryTokens query, String endToken ) throws SyntaxErrorException {
		ExpTree outTree;
		
		// The query must start with a word/variable; get it.
		String token = query.nextToken();
		if (token==null)
			throw new SyntaxErrorException( "Unexpected end of query" );
		if (token.equals(endToken))
			throw new SyntaxErrorException( "Empty sub-expression in query" );
		
		// If the word as a parenthesis, get the subquery; else the word
		if (token.equals("("))
			outTree = myParser( query, ")" );
		else
			outTree = new ExpTree( token );
		
		// If nothing follows the word and we're not in deep, we're done
		token = query.nextToken();
		if (token==null) {
			if (endToken.equals(""))
				return outTree;
			throw new SyntaxErrorException( "Unexpected end of expression" );
		}
	
		// We have the first word/subquery.  We should expect a sequence of
		// "operator word/subquery" entries until the end of the string or the
		// end of this level token.  The operator is optional and is assumed
		// to be AND if it is not there.
		while (token != null && !token.equals(endToken)) {
			String operator = "AND";
			ExpTree rightOp;
			
			// If the next word is an operator, take it.  (Else assume AND.)
			// If there is an operator, there must be another word/subquery.
			if (isOperator(token)) {
				operator = token;
				token = query.nextToken();
				if (token==null || token.equals(endToken))
					throw new SyntaxErrorException( "Nothing after operator" );
			}
			
			// Get the word/subquery after the token.
			if (token.equals("("))
				rightOp = myParser( query, ")" );
			else
				rightOp = new ExpTree(token);
			
			// Pull together what we had so far with this operator.
			outTree = new ExpTree( operator, outTree, rightOp );
			
			// Get the token after that last word/subquery.
			token = query.nextToken();
		}
		
		if (!endToken.equals("") && token==null)
			throw new SyntaxErrorException( "Unexpected end of parentheses" );
		
		return outTree;

	}
	
	/*****
	 * A class for converting a string into a sequence of tokens that can
	 * be processed.  This is very much like various Java classes, but
	 * specialized for this task.
	 *
	 */
	private static class QueryTokens {

		char[] qChars;
		int nextChar;
		StringBuilder sb;
		
		/***
		 * 
		 * @param q is the query string
		 */
		QueryTokens( String q ) {
			qChars = q.toCharArray();
			nextChar = 0;
			sb = new StringBuilder();
		}
		
		/***
		 * 
		 * @return is the next token in the query language.  Tokens are broken
		 * at white space, parens, or at the query operators &, |, and -.
		 * The operators
		 * are always returned on the subsequent call.
		 */
		String nextToken() {
			
			// Advance through whitespace
			while (nextChar<qChars.length && " ".indexOf(qChars[nextChar])>=0)
				nextChar++;
			
			if (nextChar >= qChars.length)
				return null;
			
			// If the first character is a character-operator, return it.
			sb.setLength(0);
			if ("&|-()".indexOf(qChars[nextChar]) >= 0) {
				sb.append(qChars[nextChar++]);
				return sb.toString();
			}
			
			// Scan forward to find the word
			while (nextChar<qChars.length && "&|- ()".indexOf(qChars[nextChar])==-1)
				sb.append(qChars[nextChar++]);
			return sb.toString();
		}
	}
	
	
	/***
	 * A utility function to decide if something is an operator.
	 * @param t is a string that might be an operator
	 * @return true if it is one of the known operators; else false.
	 */
	private static boolean isOperator( String t ) {
		if (t.equalsIgnoreCase("AND") ||	t.equals("&") ||
			t.equalsIgnoreCase("OR") ||		t.equals("|") ||
			t.equalsIgnoreCase("BUTNOT") ||	t.equals("-"))
			return true;
		
		return false;
	}
	
	
	/*****
	 * A main method for trying out queries.
	 * 
	 * @param args
	 */

/* COMMENTING OUT THIS MAIN METHOD; USE IT IF YOU LIKE.
 * You'll also have to uncomment the import statements at the top.
 * This code assumes that the ExpTree class supports a useful toString and
 * a method printTree that prints the tree in a nicely formatted way.
 * It is all commented out because neither of those is required.*/

	public  static void main( String args[] ){
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
			String query;
			System.out.print( "Enter query: " );
			try {
				query = console.readLine();
			} catch (IOException e) {
				query = "";
			}
			
			if (query.equals("")) break;
			ExpTree qTree;
			try {
				qTree = QueryParser.go(query);
			} catch (SyntaxErrorException e) {
				System.out.println( "Error parsing query: " + e.getMessage() );
				qTree = null;
			}
			
			if (qTree != null) {
				System.out.println( qTree );
				//qTree.printTree();
			}
			System.out.println( "" );
		}
	}

}
