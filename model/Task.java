package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class Task implements PIR {
	private Note taskNote;
	private Date deadline; 
	private static int TasksCount=0; 

	public Task(String description, String deadlineStr) {
		createTask(description, deadlineStr);
		TasksCount++;
	}
	public Task(String description, Date deadlineStr) {
		createTask(description, deadlineStr);
		TasksCount++;
	}

	public void createTask(String description, String dateStr) {
		taskNote = new Note(description);
		deadline = parseDateStr(dateStr);
	}

	public void createTask(String description, Date date) {
		taskNote = new Note(description);
		deadline = date;
	}
	
/* if (comparisonResult < 0) System.out.println("The PIR time is before the specified time.");
 * else if (comparisonResult > 0) System.out.println("The PIR time is after the specified time.");
 * else System.out.println("The PIR time is equal to the specified time.");
*/
	public int compareTime(String dateTimeStr) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        int comparisonResult = -1;
        
        try {
            Date dateTime = ft.parse(dateTimeStr);
            comparisonResult = deadline.compareTo(dateTime);
            return comparisonResult;
            
        } catch (ParseException e) {
            System.out.println("Invalid date format!");
        }
        return comparisonResult;
    }
	
	
	public boolean checkTimeCondition(String timeCondition) {
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
            //System.out.println("Invalid operator in time condition.");
            return false;
        }

        int comparisonResult = compareTime(dateTimeStr);

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
            //System.out.println("Invalid operator in time condition.");
            return false;
        }
    }

	public Date parseDateStr(String dateStr) {
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

	public static int getTasksCount() {
		return TasksCount;
	}

	@Override
	public String toString() {
		return "Task [taskNote=" + taskNote + ", deadline=" + deadline + "]";
	}
	
}
