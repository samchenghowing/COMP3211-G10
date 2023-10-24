package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Scanner;

public class PIM_console {
	public static ArrayList<PIR> PIRs;
	
	public static void main(String[] args) {
		// initialize and load all PIRs to the arraylist
		
		System.out.println("Welcome to PIM, please input your username");
		Scanner myObj = new Scanner(System.in);
		String name = myObj.nextLine();
		
		int option = -1;
		
		while (option != 0) {
			System.out.println("Which option you want to perform?"
					+ "\nInput 1 to search PIRs"
					+ "\nInput 2 to show all PIRs"
					+ "\nInput 3 to create a new PIR"
					+ "\nInput 0 to quit");
			option = myObj.nextInt();
			
			if (option == 1) {
				
			}
			else if (option == 2) {
				// print out detailed information about all PIRs
		    	File dir = new File("./src/model");
		    	File [] files = dir.listFiles(new FilenameFilter() {
		    	    @Override
		    	    public boolean accept(File dir, String name) {
		    	        return name.endsWith(".pim");
		    	    }
		    	});
		    	
		    	for (File pimfile : files) {
		    	    System.out.println(pimfile);
				    try {
				        Scanner myReader = new Scanner(pimfile);
				        while (myReader.hasNextLine()) {
				          String data = myReader.nextLine();
				          System.out.println(data);
				        }
				        myReader.close();
				      } catch (FileNotFoundException e) {
				        System.out.println("An error occurred.");
				        e.printStackTrace();
				      }
		    	}
			}
			else {
				System.out.println("Invalid option");
			}
		}
		System.out.println("Bye");
		

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
