// Original work of Nathan Fuller

public class Command{
	private String input,cmd;
	private String arg; 
	private int finCmd;
	public static final int CMD_LET=1,CMD_DOC=2,CMD_PRINT=3,CMD_DO=4,CMD_HISTORY=5,CMD_END=6,CMD_COMMENT=7,CMD_INVALID=0;

	public Command(String fromScan){
		input = fromScan; 
		arg = ""; //initialized as "" in case of user input being "do" so it prints out <DO><>
		String[] array = input.split(" ",2); //splits user input into 2 strings, divided by the first " " in the input
		cmd = array[0]; //our command is assumed to be the first word
		if(array.length>1) //if the input actually CAN be split, the half of the string AFTER the " " is the argument
			arg = array[1];
		if(cmd.equalsIgnoreCase("END")){finCmd = CMD_END;} //these if statements assign a static variable the int value...
		else if(cmd.equalsIgnoreCase("LET")){finCmd = CMD_LET;} //that corresponds to each command
		else if(cmd.equalsIgnoreCase("DOC")){finCmd = CMD_DOC;}
		else if(cmd.equalsIgnoreCase("PRINT")){finCmd = CMD_PRINT;}
		else if(cmd.equalsIgnoreCase("DO")){finCmd = CMD_DO;}
		else if(cmd.equalsIgnoreCase("HISTORY")){finCmd = CMD_HISTORY;}
		else if(cmd.equalsIgnoreCase("#") || cmd.equalsIgnoreCase("COMMENT")){finCmd = CMD_COMMENT;}
		else{ 
			if(array.length>1) //if input actually has a space, assign the entire invalid input to the string
				arg = input;
			else if(array.length==1) //if the input is only one word (no space), assign the first half of the array input to the string
				arg = array[0];
			finCmd = CMD_INVALID;
		}



	}

	public int getCommand(){
		return finCmd; //returns the int variable stored within the Command constructor
	}

	public String getArg(){
		return arg; //returns the string variable stored within the Command constructor
	}

}



