package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class PIM_console {
	private static ArrayList<PIR> PIRs = null;
	private static String userName;
	private static String filePath;
	private static boolean debug = true;
	
	public static void main(String[] args) {
		System.out.println("Welcome to PIM, please input your username");
		Scanner scanner = new Scanner(System.in);
		userName = scanner.nextLine();
		
		if(debug) {
			userName = "Alice";
			filePath = "./";
			writeDefaultData(filePath, userName);
		}
		PIRs = loadPIRs(filePath, userName);
		if (PIRs == null) {
			// user not exist 
		}
		
		String option = "";
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
						+ "\nInput 4 for contacts"
						+ "\nInput 0 to back to menu");
				option = scanner.nextLine();
				
				System.out.println("--------------search(String)--------------"
						+ "\nInput the text you are looking for "
						+ "(input blank space to show all PIRs): ");
				String searchString = scanner.nextLine();
				
				if (!(option.equals("1") || option.equals("4"))) {
					System.out.println("--------------search(Date/time)--------------"
						+ "\nInput the time condition (in format 'operator date:Time'): "
						+ "For example, you might enter '= 2023-11-07 09:30' to show "
						+ "PIR's time equals to the given time, and '> 2023-11-07 09:30'"
						+ "to show PIR's time larger than the given time and"
						+ "'< 2023-11-07 09:30' for time smaller than the given time"
						+ "(input blank space to show all PIRs): ");
					String timeString = scanner.nextLine();
				}
				
				
				// delete/ modify
				boolean foundPIR = false;
				for(PIR pir:PIRs) {
					if (pir.toString().contains(searchString)) {
						foundPIR = true;
						System.out.println("index: " + PIRs.indexOf(pir)+ " " + pir);
					}
		        }
				if (foundPIR) {
					System.out.println("Matched PIR(s) found!"
									+ "\nPlease input the PIR's index you want "
									+ "to delete, or input blank space to menu.");
					option = scanner.nextLine();
					try {
						int i = Integer.parseInt(option);
						if (i < PIRs.size() && i > 0) {
							PIRs.remove(i);
							System.out.println("PIR" + i + " deleted!");
						}
						System.out.println("Please input vaild index!");
					} catch (NumberFormatException e) {
						System.out.println("Please input integer index!");
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
				scanner.nextLine();
				
				if (option.equals("1")) {
					System.out.println("--------------create(notes)--------------"
							+ "\nInput the text for your quick notes: ");
					String noteString = scanner.nextLine();
					Note tempNote = new Note(noteString);
					PIRs.add(tempNote);
				}
				else if (option.equals("2")) {
					System.out.println("--------------create(tasks)--------------"
							+ "\nInput the descriptions for your task: ");
					String noteString = scanner.nextLine();
					
					Note tempNote = new Note(noteString);
					PIRs.add(tempNote);
				}
				else if (option.equals("3")) {
					System.out.println("--------------create(events)--------------"
							+ "\nInput the descriptions for your task: ");
					String noteString = scanner.nextLine();
					
					Note tempNote = new Note(noteString);
					PIRs.add(tempNote);
				}
				else if (option.equals("4")) {
					System.out.println("--------------create(contacts)--------------"
							+ "\nInput the descriptions for your task: ");
					String noteString = scanner.nextLine();
					
					Note tempNote = new Note(noteString);
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
	
    public static void searchTasks(String searchString) {
        String[] conditions = new String[2];
        
        if (searchString.contains("&&")) conditions = searchString.split("&&");
		else if (searchString.contains("||")) conditions = searchString.split("\\|\\|"); 
		else System.out.println("Invalid search condition.");

        String targetDescription = conditions[0].trim();
        String timeCondition = conditions[1].trim();

        for (PIR pir : PIRs) {
            // if (pir.getTaskNote().getDescription().equals(targetDescription) && checkTimeCondition(task, timeCondition)) {
            //     System.out.println(task.toString());
            // }
        }
    }
	
	private static boolean checkTimeCondition(Task task, String timeCondition) {
        String operator;
        String dateTimeStr;

        if (timeCondition.contains(">=")) {
            operator = ">=";
            dateTimeStr = timeCondition.substring(2).trim();
        } else if (timeCondition.contains("<=")) {
            operator = "<=";
            dateTimeStr = timeCondition.substring(2).trim();
        } else if (timeCondition.contains("!=")) {
            operator = "!=";
            dateTimeStr = timeCondition.substring(2).trim();
        } else if (timeCondition.contains(">")) {
            operator = ">";
            dateTimeStr = timeCondition.substring(1).trim();
        } else if (timeCondition.contains("<")) {
            operator = "<";
            dateTimeStr = timeCondition.substring(1).trim();
        } else if (timeCondition.contains("=")) {
            operator = "=";
            dateTimeStr = timeCondition.substring(1).trim();
        } else {
            System.out.println("Invalid operator in time condition.");
            return false;
        }

        int comparisonResult = task.compareTime(dateTimeStr);

        if (operator.equals(">=")) {
            return comparisonResult >= 0;
        } else if (operator.equals("<=")) {
            return comparisonResult <= 0;
        } else if (operator.equals("!=")) {
            return comparisonResult != 0;
        } else if (operator.equals(">")) {
            return comparisonResult > 0;
        } else if (operator.equals("<")) {
            return comparisonResult < 0;
        } else if (operator.equals("=")) {
            return comparisonResult == 0;
        } else {
            System.out.println("Invalid operator in time condition.");
            return false;
        }
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
