import java.util.*;
import java.io.*;

// Provided by Prof. Allan
public class NextCommand implements NextCommandInterface  {

	private Scanner inStream;
	private boolean promptUser;
	private String nameOfFile;
	
	// Default constructor uses system input and requests prompting
	public NextCommand() {
		inStream = new Scanner( System.in );
		promptUser = true;
		nameOfFile = null;
	}
	
	// Alternate constructor to allow input from a file (no prompting)
	public NextCommand(String filename) throws FileNotFoundException {
		inStream = new Scanner( new File(filename));
		promptUser = false;
		nameOfFile = filename;
	}
	
	// Ask user for a command and process it to return a Command
	public Command get() {
		String inLine;
		
		// If we have no way to get input, pretend we hit the end
		if (inStream==null)
			return new Command( "END" );

		// Optionally prompt for the command
		if (promptUser)
			System.out.print( "Command? ");
		
		// Get a command (have to make sure EOF wasn't pressed)
		if (!inStream.hasNextLine())
			return new Command( "END" );
		
		inLine = inStream.nextLine();

		// Clean it up and return the results
		return new Command( inLine );
		
	}
	
	// A whimsical way to print this object out.
	public String toString() {
		if (nameOfFile == null)
			return "Commands being accepted from standard input";
		else
			return "Commands being accepted from " + nameOfFile;
	}
}

