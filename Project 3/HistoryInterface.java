// Provided by Prof. Allan
public interface HistoryInterface {

	// expected constructor: History()
	
	public void add(Command c, int value);
	public void add(Command c);
	
	public int numCommands();
	
	public Command pastCommand(int index);
	public int pastValue(int index);
	
	public String toString();
	
}
