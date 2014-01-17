// Work of Prof. James Allan

import java.io.*;
import java.util.*;

public class P7 {
public static void main(String[] args){
		
		//Start off assuming we're reading from the console and create a stack
		//for storing "pushed" readers
		NextCommand reader = new NextCommand();
		Stack<NextCommand> cmdStack = new Stack<NextCommand>();
		
		//The command we get and a flag to indicate whether it's time to exit.
		Command c;
		boolean userRequestsExit = false;
		
		//For storing the history. We keep track of the depth of the stack, too.
		History h = new History();
		int nestingLevel = 0;
		
		// For storing created documents.
		Database db = new Database();
		
		// And a place for storing the LET command assignments.
		Assignments letStorage = new Assignments();
		
		do{
			//Get a command
			c = reader.get();
			h.add(c,nestingLevel);

			switch(c.getCommand()){

			//Do nothing in response to a comment
			case Command.CMD_COMMENT:
				System.out.println("COMMENT");
				break;

			//DO filename. See if we can start a reader up from the file.
			//If not, then print an error and continue from where we left off.
			//If so, push the current reader onto a stack and continue with the new one.
			case Command.CMD_DO:
				NextCommand tmpReader;
				try{
					tmpReader = new NextCommand(c.getArg());
				}
				catch(FileNotFoundException e){
					tmpReader = null;
					System.out.println("Unable to open file:");
					System.out.println("  " + e.getMessage());
				}
				//Success. Save current reader and switch to new one. We are
				//now nested one level deeper in DO files...
				if(tmpReader!=null){
					cmdStack.push(reader);
					reader = tmpReader;
					nestingLevel++;
				}
				break;
			
			//DOC id "title" text
			case Command.CMD_DOC:
				String[] docParts = c.getArg().split("\"");
				if(docParts.length != 3){
					System.out.println("ERROR: Invalid format: use DOC docid \"title\" text");
				}
				else{
					Document d = new Document(docParts[0].trim(),docParts[1].trim(),docParts[2].trim());
					if(d==null || d.getDocid()==null)
						System.out.println("ERROR: Invalid DOC: " + d.getTitle());
					else{
						try{
							db.add(d);
						}
						catch(DuplicateDocumentException e){
							System.out.println(e.getMessage());
							break;
						}
						System.out.println(d);
						System.out.println("Added document " + d.getDocid());
					}
				}
				break;
				
			//HISTORY
			case Command.CMD_HISTORY:
				System.out.println(h);
				break;
				
			//END. If the command-source stack is empty, we're done.
			//Otherwise, revert to the previous one and note that we are now
			//one level back up in the nesting of DO files.
			case Command.CMD_END:
				if(cmdStack.empty())
					userRequestsExit = true;
				else{
					reader = cmdStack.pop();
					nestingLevel--;
				}
				break;
				
			// PRINT expression
			// Run the expression as a query and print out the resulting
			// ranked list.
			case Command.CMD_PRINT:{ 
				ResultList rList = null;
				try{
					rList = db.runQuery(c.getArg(), letStorage);
				}
				catch(SyntaxErrorException e){
					System.out.println( e.getMessage() );
				}
				if(rList == null || rList.size() == 0)
					System.out.println( "No matching documents" );
				else
					for (Document d : rList)
						System.out.println( d );
				break;
			}
				
			// Let var = expression
			// Run the expression as  a query and store the results in the
			// indicated variable (in "letStorage").
			case Command.CMD_LET:{ 
				String[] letParts = c.getArg().split( "=" );
				if(letParts.length != 2){
					System.out.println( "Correct syntax is: LET variable =" +
							" expression");
				}
				else{
					ResultList rList = null;
					try{
						rList = db.runQuery( letParts[1].trim(), letStorage);
					}
					catch(SyntaxErrorException e){
						System.out.println( e.getMessage() );
					}
					
					if(rList == null || rList.size() == 0)
						System.out.println( "No matching documents; not saved.");
					else{
						letStorage.add( letParts[0].trim(), rList);
						System.out.println( "Stored " + rList.size() + " "
								+ (rList.size() == 1 ? "document":"documents")
								+ " in '" 
								+ letParts[0].trim() + "'" );
					}
				}
				break;
			}
			
			//Other commands
			default:
				System.out.println( "Unrecognized command; try DOC, DO, HISTORY, END, "
						+ "LET, or PRINT (or # for a comment)" );
			}
		}while(!userRequestsExit);
		
		//Courtesy exit message.
		System.out.println("Thank you for playing.");
		System.out.println("You processed " 
							+ h.numCommands()
							+ " command" 
							+ (h.numCommands()==1 ? "" : "s") 
							+ " and stored "
							+ db.numDocuments()
							+ " document"
							+ (db.numDocuments()==1 ? "" : "s")
							+ " before you exited.");
		
	}
}
