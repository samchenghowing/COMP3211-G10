package model;

public class PIM_console {

	public static void main(String[] args) {
		System.out.println("Welcome to PIM, please input your username");
		// get user name 
		
		
		System.out.println("Welcome, which option you want to perform?"
				+ "\nInput 1 to search PIRs"
				+ "\nInput 2 to create a new PIR");
		
		
		//option 1
		System.out.println(""
				+ "\nInput 1 to show all PIRs"
				+ "\nInput 2 to create a new PIR");
		
		
		//option 1.1, print out detailed information about all PIRs
		

		//option 1.2, print out detailed information about a specific PIR
		//option 1.2.1, PIR found
		System.out.println("Matched PIR(s) found!"
				+ "\nPlease input the PIR's name you want to modify,"
				+ "\n or enter any char to back.");
		
		
		
		// option 2
		System.out.println("Please select target PIR's type:"
				+ "\nInput 1 for notes"
				+ "\nInput 2 for tasks"
				+ "\nInput 3 for events"
				+ "\nInput 4 for contacts");
		
		
		
	}

}
