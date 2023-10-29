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
			filePath = "./";
			writeDefaultData(filePath, userName);
		}
		PIRs = loadPIRs(filePath, userName);
		if (PIRs == null) {} // user not exist 
		
		int option = -1;
		while (option != 0) {
			System.out.println("--------------Menu--------------"
					+ "\nWhich option you want to perform?"
					+ "\nInput 1 to search PIRs"
					+ "\nInput 2 to create a new PIR"
					+ "\nInput 3 to manage PIR"
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
		            pir.search(filePath);
		        }

				//option 1.2, print out detailed information about a specific PIR
				//option 1.2.1, PIR found
				System.out.println("Matched PIR(s) found!"
						+ "\nPlease input the PIR's name you want to modify,"
						+ "\n or enter any char to back.");
			}
			else if (option == 2) {
				System.out.println("Which type of PIR you want to create?"
						+ "\nInput 1 for notes"
						+ "\nInput 2 for tasks"
						+ "\nInput 3 for events"
						+ "\nInput 4 for contacts");
				option = myObj.nextInt();
				if (option == 1) {
					Note tempNote = new Note("Quick notes 1");

					PIRs.add(tempNote);
					savePIRs(filePath, userName, PIRs);
				}
				else if (option == 2) {}
				else if (option == 3) {}
				else if (option == 4) {}
			}
			else if (option == 3) {
				
			}
			else {
				if (option != 0) System.out.println("Invalid option");
			}
		}
		System.out.println("Bye");
		myObj.close();
	}
	
	private static void savePIRs(String filePath, String userName, ArrayList<PIR> currentPIRs) {
		try {
			filePath = filePath.concat(userName).concat(".pim");
			File f = new File(filePath);
			if (!f.getParentFile().exists()) f.getParentFile().mkdirs();
			if (!f.exists()) f.createNewFile();

			FileOutputStream fos = new FileOutputStream(filePath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(currentPIRs);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	private static ArrayList<PIR> loadPIRs(String filePath, String userName) {
		ArrayList<PIR> matchedPIRs = null;
		
    	File dir = new File(filePath);
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

	private static void writeDefaultData(String filePath, String userName) {
		ArrayList<PIR> defaultPIRs = new ArrayList<PIR>();
		Note note1 = new Note("Quick notes 1");
		Task task1 = new Task("Task 1", 20231101);
		defaultPIRs.add(note1);
		defaultPIRs.add(task1);

		savePIRs(filePath, userName, defaultPIRs);
	}

}
