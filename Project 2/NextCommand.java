// Original work of Nathan Fuller

import java.util.Scanner;

public class NextCommand implements NextCommandInterface{
	private Scanner scan;
	private String input;
	Command next;
	
	public NextCommand(){ //NextCommand constructor w/ Scanner object
		scan = new Scanner(System.in);
	}


	public Command get(){ 
		System.out.println("Please enter a command.");
		if(scan.hasNextLine()) //activates the scanner and stores input to a new Command object
			input = scan.nextLine();
		else
			input = "END"; //this checks for the end-of-input operation (ctrl-d or ctrl-z)
		next = new Command(input);
		return next;
	}
	
}
