import java.util.Scanner;

public class PracticeSplit {
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		String[] array = input.split(" ",2);
		System.out.println(array[1].toUpperCase());
	}

}
