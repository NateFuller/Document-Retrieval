import java.io.*;
import java.util.Stack;

//Nathan Fuller
public class P3 {
	public static void main(String[] args){
		
		//Start off assuming we're reading from the console and create a stack
		//for storing "pushed" readers
		NextCommand reader = new NextCommand();
		Stack<NextCommand> cmdStack = new Stack<NextCommand>();
		
		//The command we get and a flag to indicate whether it's time to exit
		Command c;
		boolean userRequestsExit = false;
		
		//For storing the history. We keep track of the depth of the stack, too.
		History h = new History();
		int nestingLevel = 0;
		
		do{
			//Get a command
			c = reader.get();
			h.add(c,nestingLevel);

			switch(c.getCommand()){

			//Do nothing in response to a comment
			case Command.CMD_COMMENT:
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
					else
						System.out.println("Added document" + d.getDocid());
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
			
			//Other commands
			default:
				System.out.println(c);
			}
		}while(!userRequestsExit);
		
		//Couresy exit message.
		System.out.println("Thank you for playing.");
		System.out.println("You processed " 
							+ h.numCommands()
							+ " command" 
							+ (h.numCommands()==1 ? "" : "s") 
							+ " before you exited.");
		
	}
}