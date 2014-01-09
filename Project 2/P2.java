// Original work by Nathan Fuller

public class P2 {
	public static void main(String[] args){
		boolean isRunning = true; 
		NextCommand nCmd = new NextCommand(); 
		String let="<LET>",print="<PRINT>",doCmd="<DO>",history="<HISTORY>",invalid="<INVALID>",comment="<COMMENT>";
		int command;
		String argument;
		while(isRunning){
			Command cmd = nCmd.get();
			command = cmd.getCommand(); 
			argument = cmd.getArg();
			if(command == Command.CMD_END){ //these if statements test to make sure the command from Command class is set to a proper int value
				isRunning = false; //if the int value corresponds to the END command, the loop terminates
			}
			else if(command == Command.CMD_LET)
				System.out.println(let+"<"+argument+">");
			else if(command == Command.CMD_DOC){
				Document doc;
				try{
					doc = new Document(argument);
					System.out.println("DocID: <" + doc.getDocid() + ">");
					System.out.println("Title: <" + doc.getTitle() + ">");
					System.out.println("Text: <" + doc.getWords() + ">");
					System.out.println("Length: <" + doc.numWords() + ">");
				}
				catch(ArrayIndexOutOfBoundsException e){
					String error = "ERROR: Invalid format for DOC command.";
					System.out.println(error);
				}
			}
			else if(command == Command.CMD_PRINT)
				System.out.println(print+"<"+argument+">");
			else if(command == Command.CMD_DO)
				System.out.println(doCmd+"<"+argument+">");
			else if(command == Command.CMD_HISTORY)
				System.out.println(history+"<"+argument+">");
			else if(command == Command.CMD_COMMENT)
				System.out.println(comment+"<"+argument+">");
			else
				System.out.println(invalid+"<"+argument+">");
		}
	}
}


