package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class PIM_console {
	private static ArrayList<PIR> PIRs = null;
	private static String userName;
	private static String filePath;
	private static boolean debug = true;
	
	public static void main(String[] args) {
		System.out.println("Welcome to PIM, please input your username");
		Scanner myObj = new Scanner(System.in);
		userName = myObj.nextLine();
		
		if(debug) {
			userName = "Alice";
			filePath = "./src/model/Alice.pim";
			writeDefaultData(filePath);
		}
		PIRs = loadUserPIRs(userName);
		if (PIRs == null) {} // user not exist 
		
		int option = -1;
		while (option != 0) {
			System.out.println("--------------Menu--------------"
					+ "\nWhich option you want to perform?"
					+ "\nInput 1 to search PIRs"
					+ "\nInput 2 to show all PIRs"
					+ "\nInput 3 to create a new PIR"
					+ "\nInput 0 to quit");
			option = myObj.nextInt();
			
			
			if (option == 1) {
				System.out.println("Which type of PIR you want to search?"
						+ "\nInput 1 for notes"
						+ "\nInput 2 for tasks"
						+ "\nInput 3 for events"
						+ "\nInput 4 for contacts");
				option = myObj.nextInt();
				
				for(PIR pir:PIRs) {
		            System.out.println(pir);
		            System.out.println(pir.toString());
		        }

				//option 1.2, print out detailed information about a specific PIR
				//option 1.2.1, PIR found
				System.out.println("Matched PIR(s) found!"
						+ "\nPlease input the PIR's name you want to modify,"
						+ "\n or enter any char to back.");
			}
			else if (option == 2) showAllPIRs();
			else if (option == 3) {
				System.out.println("Which type of PIR you want to create?"
						+ "\nInput 1 for notes"
						+ "\nInput 2 for tasks"
						+ "\nInput 3 for events"
						+ "\nInput 4 for contacts");
				option = myObj.nextInt();
				if (option == 1) {}
				else if (option == 2) {}
				else if (option == 3) {}
				else if (option == 4) {}
			}
			else {
				if (option != 0) System.out.println("Invalid option");
			}
		}
		System.out.println("Bye");
		myObj.close();
	}
	
	private static void writeDefaultData(String filePath) {
		ArrayList<PIR> defaultPIRs = new ArrayList<PIR>();
		Note aliceNote1 = new Note("Quick notes 1");
		defaultPIRs.add(aliceNote1);
		
		try {
			File aliceFile = new File(filePath);
			aliceFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(filePath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(defaultPIRs);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static ArrayList<PIR> loadUserPIRs(String userName) {
		ArrayList<PIR> matchedPIRs = null;
		
    	File dir = new File("./src/model");
    	File [] files = dir.listFiles(new FilenameFilter() {
    	    @Override
    	    public boolean accept(File dir, String name) {
    	        return name.contains(userName) && name.endsWith(".pim");
    	    }
    	});
    	
    	for (File pimfile : files) {
		    try {
		    	if(debug) System.out.println("File name: " + pimfile);
		        FileInputStream fis = new FileInputStream(pimfile);
		        ObjectInputStream ois = new ObjectInputStream(fis);
		        matchedPIRs = (ArrayList<PIR>) ois.readObject();
		        ois.close();
		      } 
			  catch (FileNotFoundException e) {
				  e.printStackTrace();
			  }
			  catch (IOException e) {
			      e.printStackTrace();
			  }
			  catch (ClassNotFoundException e) {
			      e.printStackTrace();
			  }
    	}
		return matchedPIRs;
	}
		
	private static void showAllPIRs() {
		// print out detailed information about all PIRs
    	File dir = new File("./src/model");
    	File [] files = dir.listFiles(new FilenameFilter() {
    	    @Override
    	    public boolean accept(File dir, String name) {
    	        return name.endsWith(".pim");
    	    }
    	});
    	
    	for (File pimfile : files) {
    	    System.out.println("File name: "+pimfile);
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
}
