package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class PIM_console {
	private static ArrayList<PIR> PIRs;
	private static String userName;
	private static String filePath = "./";
	private static boolean debug = true;
	
	public static void main(String[] args) {
		System.out.println("Welcome to PIM, please input your username");
		Scanner scanner = new Scanner(System.in);
		userName = scanner.nextLine();
		
		if(debug) {
			userName = "Alice";
			writeDefaultData(filePath, userName);
		}
		while (userName.contains(" ")) {
			System.out.println("Please input a vaild username (not contain any spaces)");
			userName = scanner.nextLine();
		}
		PIRs = loadPIRs(filePath, userName);
		System.out.println("Welcome to PIM, " + userName);
		
		String option = "", searchString = "", indexString = "";
		boolean foundPIR = false;
		while (!option.equals("q")) {
			System.out.println("--------------Menu--------------"
					+ "\nWhich option you want to perform?"
					+ "\nInput 1 to search a PIR"
					+ "\nInput 2 to create a new PIR"
					+ "\nInput q to quit");
			option = scanner.nextLine();
			
			if (option.equals("1")) {
				System.out.println("--------------search--------------"
						+ "\nWhich type of PIR you want to search?"
						+ "\nInput 1 for notes"
						+ "\nInput 2 for tasks"
						+ "\nInput 3 for events"
						+ "\nInput 4 for contacts");
				option = scanner.nextLine();

				foundPIR = false;
				if (option.equals("1") || option.equals("4")) {
					System.out.println("--------------search(String)--------------"
							+ "\nInput the text you are looking for "
							+ "(input blank space to show all PIRs): ");
				}
				else if (option.equals("2") || option.equals("3")) {
					System.out.println("--------------search(Date)--------------"
						+ "\nInput the text you are looking for and the time condition "
						+ "(in format 'searchStr logicalOps compareOps date:Time'): "
						+ "\nFor example, you might enter 'abc && = 2023-11-07 09:30' to show "
						+ "PIR's string contains 'abc' and time equals to the given time, "
						+ "\nand 'abc || >= 2023-11-07 09:30' to show PIR's string contains 'abc' or"
						+ " time larger than or equal to the given time (input blank space to show all PIRs): ");
				}
				searchString = scanner.nextLine();
				foundPIR = searchPIRs(searchString);
				
				if (foundPIR) {
					System.out.println("Matched PIR(s) found!"
									+ "\nPlease input the PIR's index together with option (1=edit, 2=delete) "
									+ "you want to perform. \nFor example, you might enter '1 3' to edit PIR "
									+ "with index no.3 or you can input blank space to back to menu.");
					option = scanner.nextLine();
					
					if (option.startsWith("1 ")) {
						// modify
						indexString = option.substring(2).trim();
						if (isNumeric(indexString)) {
							int i = Integer.parseInt(indexString);
							if (i < PIRs.size() && i >= 0) {
								System.out.println("Current info:"+PIRs.get(i));
								if (PIRs.get(i) instanceof ContactNote) {
									ContactNote tempNote = setContact("edit");
									PIRs.set(i, tempNote);
								}
								else if (PIRs.get(i) instanceof Note) {
									Note tempNote = setNote("edit");
									PIRs.set(i, tempNote);
								}
								else if (PIRs.get(i) instanceof Task) {
									Task tempTask = setTask("edit");
									PIRs.set(i, tempTask);
								}
								else if (PIRs.get(i) instanceof Event) {
									Event tempEvent = setEvent("edit");
									PIRs.set(i, tempEvent);
								}
								System.out.println("PIR" + i + " modified!");
							}
							else System.out.println("Please input vaild index!");
						}
					}
					else if (option.startsWith("2 ")) {
						// delete
						indexString = option.substring(2).trim();
						if (isNumeric(indexString)) {
							int i = Integer.parseInt(indexString);
							if (i < PIRs.size() && i >= 0) {
								PIRs.remove(i);
								System.out.println("PIR" + i + " deleted!");
							}
							else System.out.println("Please input vaild index!");
						}
					}
					else {
						System.out.println("Invalid option");
					}
				}
				else System.out.println("No matched PIR found");
			}
			else if (option.equals("2")) {
				System.out.println("--------------create--------------"
						+ "\nWhich type of PIR you want to create?"
						+ "\nInput 1 for notes"
						+ "\nInput 2 for tasks"
						+ "\nInput 3 for events"
						+ "\nInput 4 for contacts"
						+ "\nInput 0 to back to menu");
				option = scanner.nextLine();
				
				if (option.equals("1")) {
					Note tempNote = setNote("create");
					PIRs.add(tempNote);
				}
				else if (option.equals("2")) {
					Task tempTask = setTask("create");
					PIRs.add(tempTask);
				}
				else if (option.equals("3")) {
					Event tempEvent = setEvent("create");
					PIRs.add(tempEvent);
				}
				else if (option.equals("4")) {
					ContactNote tempNote = setContact("create");
					PIRs.add(tempNote);
				}
				savePIRs(filePath, userName, PIRs);
			}
			else {
				if (!option.equals("q")) System.out.println("Invalid option");
			}
		}
		System.out.println("Bye");
		scanner.close();
	}

	public static Event setEvent(String mode) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("--------------"+mode+"(events)--------------"
				+ "\nInput the descriptions for your event: ");
		String noteString = scanner.nextLine();
		
		System.out.println("\nInput the starting Time for your event in format (\"yyyy-MM-dd HH:mm\"): ");
		String startingTimeStr = scanner.nextLine();
		while (parseDateStr(startingTimeStr) == null) {
			System.out.println("\nThe starting Time format is not correct, please input again: (\"yyyy-MM-dd HH:mm\"): ");
			startingTimeStr = scanner.nextLine();
		}
		Date startingTime = parseDateStr(startingTimeStr);
		
		System.out.println("\nInput the alarm: ");
		String alarm = scanner.nextLine();
		while (!isNumeric(alarm)) {
			System.out.println("\nThe alarm should be integer, please input again: ");
			alarm = scanner.nextLine();
		}
		Event tempEvent = new Event(noteString, startingTime, alarm);
		return tempEvent;
	}
	
	public static ContactNote setContact(String mode) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("--------------create(contacts)--------------"
				+ "\nInput the name for your contact: ");
		String noteString = scanner.nextLine();
		System.out.println("\nInput the address: ");
		String address = scanner.nextLine();
		System.out.println("\nInput the mobile number: ");
		String mobileNumbers = scanner.nextLine();
		while (!isNumeric(mobileNumbers)) {
			System.out.println("\nThe mobile number should be integer, please input again: ");
			mobileNumbers = scanner.nextLine();
		}
		ContactNote tempNote = new ContactNote(noteString, address, mobileNumbers);
		return tempNote;
	}
	
	public static Task setTask(String mode) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("--------------"+mode+"(tasks)--------------"
				+ "\nInput the descriptions for your task: ");
		String noteString = scanner.nextLine();
		
		System.out.println("\nInput the deadline for your task in format (\"yyyy-MM-dd HH:mm\"): ");
		String deadlineStr = scanner.nextLine();
		while (parseDateStr(deadlineStr) == null) {
			System.out.println("\nThe deadline format is not correct, please input again: (\"yyyy-MM-dd HH:mm\"): ");
			deadlineStr = scanner.nextLine();
		}
		Date deadline = parseDateStr(deadlineStr);
		Task tempTask = new Task(noteString, deadline);
		
		return tempTask;
	}
	
	public static Note setNote(String mode) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("--------------"+mode+"(notes)--------------"
				+ "\nInput the text for your quick notes: ");
		String noteString = scanner.nextLine();
		Note tempNote = new Note(noteString);
		
		return tempNote;
	}
	
	public static Date parseDateStr(String dateStr) {
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
		Date t = null;
		
		try { 
			t = ft.parse(dateStr);
			return t;
		} catch (ParseException e) { 
//			System.out.println("Unparseable date string: " + ft.toString());
		}
		return t;
	}	
	
	public static boolean isNumeric(String str) { 
		try {  
			Integer.parseInt(str);  
			return true;
		} catch(NumberFormatException e){  
			return false;  
		}  
	}
	
    public static boolean searchPIRs(String searchString) {
        String[] conditions = new String[2];
        boolean found = false, operator = false;
        
        if (searchString.contains("&&")) conditions = searchString.split("&&");
		else if (searchString.contains("||")) {
			operator = true;
			conditions = searchString.split("\\|\\|"); 
		}
		else {
			operator = true;
			conditions[0] = searchString;
			conditions[1] = "";
		}
        
        String targetDescription = conditions[0].trim();
        String timeCondition = conditions[1].trim();

        for (PIR pir : PIRs) {
        	if (operator) {
        		if (pir.toString().contains(targetDescription) || pir.checkTimeCondition(timeCondition)) {
	                 System.out.println("index: " + PIRs.indexOf(pir)+ " " + pir.toString());
	                 found = true;
        		}
        	}
        	else {
        		if (pir.toString().contains(targetDescription) && pir.checkTimeCondition(timeCondition)) {
	                 System.out.println("index: " + PIRs.indexOf(pir)+ " " +pir.toString());
	                 found = true;
        		}
        	}
        }
		return found;
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

	@SuppressWarnings("unchecked")
	private static ArrayList<PIR> loadPIRs(String filePath, String userName) {
		ArrayList<PIR> matchedPIRs = new ArrayList<PIR>();
		
		// create user data file not exist
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
		Note note2 = new Note("Quick notes 2");
		Note note3 = new Note("YOOOOO");
		Task task1 = new Task("assignment 1", "2023-11-07 09:30");
		Task task2 = new Task("quiz 2", "2023-11-14 11:45");
		Event event1 = new Event("meeting 1", "2023-11-07 09:30", "1");
		Event event2 = new Event("party 1", "2023-11-17 19:30", "2");
		ContactNote contactNote1 = new ContactNote("Bob", "1 Kowloon street", "12345678");
		ContactNote contactNote2 = new ContactNote("Cive", "2 Hong Kong street", "23456789");

		defaultPIRs.add(note1);
		defaultPIRs.add(note2);
		defaultPIRs.add(note3);
		defaultPIRs.add(task1);
		defaultPIRs.add(task2);
		defaultPIRs.add(event1);
		defaultPIRs.add(event2);
		defaultPIRs.add(contactNote1);
		defaultPIRs.add(contactNote2);

		savePIRs(filePath, userName, defaultPIRs);
	}

}
